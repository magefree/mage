package mage.sets;

import mage.Constants;
import mage.cards.ExpansionSet;

import java.util.GregorianCalendar;

public class MagicPlayerRewards extends ExpansionSet {
    private static final MagicPlayerRewards fINSTANCE = new MagicPlayerRewards();

    public static MagicPlayerRewards getInstance() {
        return fINSTANCE;
    }

    private MagicPlayerRewards() {
        super("Magic Player Rewards", "MPR", "", "mage.sets.playerrewards", new GregorianCalendar(1990, 1, 1).getTime(), Constants.SetType.EXPANSION);
        this.hasBoosters = false;
    }
}
