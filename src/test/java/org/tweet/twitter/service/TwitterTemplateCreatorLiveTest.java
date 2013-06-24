package org.tweet.twitter.service;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.stackexchange.util.SimpleTwitterAccount;
import org.tweet.spring.TwitterConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TwitterConfig.class })
public class TwitterTemplateCreatorLiveTest {

    @Autowired
    private TwitterTemplateCreator twitterTemplateCreator;

    // API

    @Test(expected = RuntimeException.class)
    public final void givenInvalidAccount_whenRetrievingTwitterClient_thenException() {
        twitterTemplateCreator.getTwitterTemplate(randomAlphabetic(6));
    }

    @Test
    public final void givenValidAccount_whenRetrievingTwitterClient_thenNoException() {
        for (final SimpleTwitterAccount account : SimpleTwitterAccount.values()) {
            twitterTemplateCreator.getTwitterTemplate(account.name());
        }
    }

}
