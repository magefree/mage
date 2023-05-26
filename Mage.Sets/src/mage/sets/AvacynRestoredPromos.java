package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pavr
 */
public class AvacynRestoredPromos extends ExpansionSet {

    private static final AvacynRestoredPromos instance = new AvacynRestoredPromos();

    public static AvacynRestoredPromos getInstance() {
        return instance;
    }

    private AvacynRestoredPromos() {
        super("Avacyn Restored Promos", "PAVR", ExpansionSet.buildDate(2012, 4, 28), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Killing Wave", 111, Rarity.RARE, mage.cards.k.KillingWave.class));
        cards.add(new SetCardInfo("Latch Seeker", 63, Rarity.UNCOMMON, mage.cards.l.LatchSeeker.class));
        cards.add(new SetCardInfo("Moonsilver Spear", "217*", Rarity.RARE, mage.cards.m.MoonsilverSpear.class));
        cards.add(new SetCardInfo("Restoration Angel", "32*", Rarity.RARE, mage.cards.r.RestorationAngel.class));
        cards.add(new SetCardInfo("Silverblade Paladin", "36*", Rarity.RARE, mage.cards.s.SilverbladePaladin.class));
    }
}
