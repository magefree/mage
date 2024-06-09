package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pths
 */
public class TherosPromos extends ExpansionSet {

    private static final TherosPromos instance = new TherosPromos();

    public static TherosPromos getInstance() {
        return instance;
    }

    private TherosPromos() {
        super("Theros Promos", "PTHS", ExpansionSet.buildDate(2013, 9, 21), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Abhorrent Overlord", "75*", Rarity.RARE, mage.cards.a.AbhorrentOverlord.class));
        cards.add(new SetCardInfo("Anthousa, Setessan Hero", "149*", Rarity.RARE, mage.cards.a.AnthousaSetessanHero.class));
        cards.add(new SetCardInfo("Bident of Thassa", "42*", Rarity.RARE, mage.cards.b.BidentOfThassa.class));
        cards.add(new SetCardInfo("Celestial Archon", "3*", Rarity.RARE, mage.cards.c.CelestialArchon.class));
        cards.add(new SetCardInfo("Ember Swallower", "120*", Rarity.RARE, mage.cards.e.EmberSwallower.class));
        cards.add(new SetCardInfo("Karametra's Acolyte", 160, Rarity.UNCOMMON, mage.cards.k.KarametrasAcolyte.class));
        cards.add(new SetCardInfo("Nighthowler", 98, Rarity.RARE, mage.cards.n.Nighthowler.class));
        cards.add(new SetCardInfo("Phalanx Leader", 26, Rarity.UNCOMMON, mage.cards.p.PhalanxLeader.class));
        cards.add(new SetCardInfo("Shipbreaker Kraken", "63*", Rarity.RARE, mage.cards.s.ShipbreakerKraken.class));
        cards.add(new SetCardInfo("Sylvan Caryatid", "180*", Rarity.RARE, mage.cards.s.SylvanCaryatid.class));
    }
}
