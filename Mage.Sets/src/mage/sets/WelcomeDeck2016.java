package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 *
 * @author escplan9
 */
public final class WelcomeDeck2016 extends ExpansionSet {

    private static final WelcomeDeck2016 instance = new WelcomeDeck2016();

    public static WelcomeDeck2016 getInstance() {
        return instance;
    }

    private WelcomeDeck2016() {
        super("Welcome Deck 2016", "W16", ExpansionSet.buildDate(2016, 3, 8), SetType.SUPPLEMENTAL_STANDARD_LEGAL);
        this.hasBasicLands = false;
        this.hasBoosters = false;
        cards.add(new SetCardInfo("Aegis Angel", 1, Rarity.RARE, mage.cards.a.AegisAngel.class));
        cards.add(new SetCardInfo("Air Servant", 4, Rarity.UNCOMMON, mage.cards.a.AirServant.class));
        cards.add(new SetCardInfo("Borderland Marauder", 11, Rarity.COMMON, mage.cards.b.BorderlandMarauder.class));
        cards.add(new SetCardInfo("Cone of Flame", 12, Rarity.UNCOMMON, mage.cards.c.ConeOfFlame.class));
        cards.add(new SetCardInfo("Disperse", 5, Rarity.COMMON, mage.cards.d.Disperse.class));
        cards.add(new SetCardInfo("Incremental Growth", 14, Rarity.UNCOMMON, mage.cards.i.IncrementalGrowth.class));
        cards.add(new SetCardInfo("Marked by Honor", 2, Rarity.COMMON, mage.cards.m.MarkedByHonor.class));
        cards.add(new SetCardInfo("Mind Rot", 7, Rarity.COMMON, mage.cards.m.MindRot.class));
        cards.add(new SetCardInfo("Nightmare", 8, Rarity.RARE, mage.cards.n.Nightmare.class));
        cards.add(new SetCardInfo("Oakenform", 15, Rarity.COMMON, mage.cards.o.Oakenform.class));
        cards.add(new SetCardInfo("Sengir Vampire", 9, Rarity.UNCOMMON, mage.cards.s.SengirVampire.class));
        cards.add(new SetCardInfo("Serra Angel", 3, Rarity.UNCOMMON, mage.cards.s.SerraAngel.class));
        cards.add(new SetCardInfo("Shivan Dragon", 13, Rarity.RARE, mage.cards.s.ShivanDragon.class));
        cards.add(new SetCardInfo("Soul of the Harvest", 16, Rarity.RARE, mage.cards.s.SoulOfTheHarvest.class));
        cards.add(new SetCardInfo("Sphinx of Magosi", 6, Rarity.RARE, mage.cards.s.SphinxOfMagosi.class));
        cards.add(new SetCardInfo("Walking Corpse", 10, Rarity.COMMON, mage.cards.w.WalkingCorpse.class));
    }
}
