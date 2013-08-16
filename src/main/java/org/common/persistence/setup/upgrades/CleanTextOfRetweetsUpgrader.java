package org.common.persistence.setup.upgrades;

import java.util.List;

import org.common.persistence.setup.AfterSetupEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.stackexchange.util.TwitterAccountEnum;
import org.tweet.meta.persistence.dao.IRetweetJpaDAO;
import org.tweet.meta.persistence.model.Retweet;
import org.tweet.spring.util.SpringProfileUtil;
import org.tweet.twitter.service.TweetService;

@Component
@Profile(SpringProfileUtil.DEPLOYED)
class CleanTextOfRetweetsUpgrader implements ApplicationListener<AfterSetupEvent>, ICleanTextOfRetweetsUpgrader {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Environment env;

    @Autowired
    private IRetweetJpaDAO retweetDao;

    @Autowired
    private TweetService tweetService;

    public CleanTextOfRetweetsUpgrader() {
        super();
    }

    // API

    @Override
    @Async
    public void onApplicationEvent(final AfterSetupEvent event) {
        if (env.getProperty("setup.upgrade.cleanretweettext.do", Boolean.class)) {
            logger.info("Starting to execute the CleanTextOfRetweetsUpgrader Upgrader");
            cleanTextOfRetweets();
            logger.info("Finished executing the CleanTextOfRetweetsUpgrader Upgrader");
        }
    }

    @Override
    public void cleanTextOfRetweets() {
        logger.info("Executing the AdsdTextToRetweets Upgrader");
        for (final TwitterAccountEnum twitterAccount : TwitterAccountEnum.values()) {
            if (twitterAccount.isRt()) {
                try {
                    logger.info("Upgrading (adding text) to retweets of twitterAccount= " + twitterAccount.name());
                    final boolean success = cleanTextOfRetweetsOnAccount(twitterAccount.name());
                    if (!success) {
                        logger.info("Done upgrading (adding text) to retweets of twitterAccount= " + twitterAccount.name() + "; sleeping for 2 secs...");
                        Thread.sleep(1000 * 2 * 1); // 2 sec
                    }
                } catch (final RuntimeException ex) {
                    logger.error("Unable to add text to retweets of twitterAccount= " + twitterAccount.name(), ex);
                } catch (final InterruptedException threadEx) {
                    logger.error("Unable to add text to retweets of twitterAccount= " + twitterAccount.name(), threadEx);
                }
            }
        }
    }

    @Override
    public boolean cleanTextOfRetweetsOnAccount(final String twitterAccount) {
        final List<Retweet> allRetweetsForAccount = retweetDao.findAllByTwitterAccount(twitterAccount);
        cleanTextOfRetweets(allRetweetsForAccount);
        return !allRetweetsForAccount.isEmpty();
    }

    // util

    private final void cleanTextOfRetweets(final List<Retweet> allRetweetsForAccount) {
        logger.info("Starting to clean text for {} retweets ", allRetweetsForAccount.size());

        for (final Retweet retweet : allRetweetsForAccount) {
            cleanTextOfRetweet(retweet);
        }
    }

    private final void cleanTextOfRetweet(final Retweet retweet) {
        try {
            cleanTextOfRetweetInternal(retweet);
        } catch (final RuntimeException ex) {
            logger.error("Unable to add text to retweet: " + retweet);
        }
    }

    private final void cleanTextOfRetweetInternal(final Retweet retweet) {
        final String textRaw = retweet.getText();
        final String preProcessedText = tweetService.processPreValidity(textRaw);
        final String postProcessedText = tweetService.postValidityProcessTweetTextWithUrl(preProcessedText, retweet.getTwitterAccount());

        if (!postProcessedText.equals(textRaw)) {
            retweet.setText(postProcessedText);
            retweetDao.save(retweet);

            logger.info("Upgraded retweet from \ntext= {} to \ntext= {}", textRaw, postProcessedText);
        }
    }

}
