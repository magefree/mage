package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.SetType;

import java.util.GregorianCalendar;

public class MagicPlayerRewards extends ExpansionSet {
    private static final MagicPlayerRewards fINSTANCE = new MagicPlayerRewards();

    public static MagicPlayerRewards getInstance() {
        return fINSTANCE;
    }

    private MagicPlayerRewards() {
        super("Magic Player Rewards", "MPR", "mage.sets.playerrewards", new GregorianCalendar(1990, 1, 1).getTime(), SetType.EXPANSION);
        this.hasBoosters = false;
        this.hasBasicLands = false;
    }
}
