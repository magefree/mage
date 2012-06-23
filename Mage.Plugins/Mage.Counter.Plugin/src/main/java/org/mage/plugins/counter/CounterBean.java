package org.mage.plugins.counter;

import java.io.Serializable;

/**
 * Class for storing plugin data.
 * 
 * @version 1.0 int version & int gamesPlayed fields
 * @author nantuko
 */
public class CounterBean implements Serializable {

    private static final long serialVersionUID = 6382055182568871761L;

    private int gamesPlayed;

    private int version = 1;

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public final int getVersion() {
        return version;
    }
}
