package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/f14
 */
public class FridayNightMagic2014 extends ExpansionSet {

    private static final FridayNightMagic2014 instance = new FridayNightMagic2014();

    public static FridayNightMagic2014 getInstance() {
        return instance;
    }

    private FridayNightMagic2014() {
        super("Friday Night Magic 2014", "F14", ExpansionSet.buildDate(2014, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Banisher Priest", 3, Rarity.RARE, mage.cards.b.BanisherPriest.class));
        cards.add(new SetCardInfo("Banishing Light", 9, Rarity.RARE, mage.cards.b.BanishingLight.class));
        cards.add(new SetCardInfo("Bile Blight", 8, Rarity.RARE, mage.cards.b.BileBlight.class));
        cards.add(new SetCardInfo("Brain Maggot", 11, Rarity.RARE, mage.cards.b.BrainMaggot.class));
        cards.add(new SetCardInfo("Dissolve", 6, Rarity.RARE, mage.cards.d.Dissolve.class));
        cards.add(new SetCardInfo("Elvish Mystic", 2, Rarity.RARE, mage.cards.e.ElvishMystic.class));
        cards.add(new SetCardInfo("Encroaching Wastes", 4, Rarity.RARE, mage.cards.e.EncroachingWastes.class));
        cards.add(new SetCardInfo("Fanatic of Xenagos", 10, Rarity.RARE, mage.cards.f.FanaticOfXenagos.class));
        cards.add(new SetCardInfo("Magma Spray", 7, Rarity.RARE, mage.cards.m.MagmaSpray.class));
        cards.add(new SetCardInfo("Stoke the Flames", 12, Rarity.RARE, mage.cards.s.StokeTheFlames.class));
        cards.add(new SetCardInfo("Tormented Hero", 5, Rarity.RARE, mage.cards.t.TormentedHero.class));
        cards.add(new SetCardInfo("Warleader's Helix", 1, Rarity.RARE, mage.cards.w.WarleadersHelix.class));
     }
}
