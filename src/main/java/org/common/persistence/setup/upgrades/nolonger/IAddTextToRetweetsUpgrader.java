package org.common.persistence.setup.upgrades.nolonger;

/**
 * Iterates though all the <b>local</b> Retweets <br/>
 * Ads text (from <b>live</b> corresponding tweet) if the <b>local</b> retweet doesn't have text <br/>
 * - <b>why no longer</b>: all Retweets now have text (plus constraint)
 */
public interface IAddTextToRetweetsUpgrader {

    void addTextOfRetweets();

    boolean addTextOfRetweetsOnAccount(final String twitterAccount);

}
