package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author fireshoes
 */
public final class FromTheVaultTwenty extends ExpansionSet {

    private static final FromTheVaultTwenty instance = new FromTheVaultTwenty();

    public static FromTheVaultTwenty getInstance() {
        return instance;
    }

    private FromTheVaultTwenty() {
        super("From the Vault: Twenty", "V13", ExpansionSet.buildDate(2013, 8, 23), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Akroma's Vengeance", 11, Rarity.MYTHIC, mage.cards.a.AkromasVengeance.class));
        cards.add(new SetCardInfo("Chainer's Edict", 10, Rarity.MYTHIC, mage.cards.c.ChainersEdict.class));
        cards.add(new SetCardInfo("Chameleon Colossus", 16, Rarity.MYTHIC, mage.cards.c.ChameleonColossus.class));
        cards.add(new SetCardInfo("Char", 14, Rarity.MYTHIC, mage.cards.c.Char.class));
        cards.add(new SetCardInfo("Cruel Ultimatum", 17, Rarity.MYTHIC, mage.cards.c.CruelUltimatum.class));
        cards.add(new SetCardInfo("Dark Ritual", 1, Rarity.MYTHIC, mage.cards.d.DarkRitual.class));
        cards.add(new SetCardInfo("Fact or Fiction", 9, Rarity.MYTHIC, mage.cards.f.FactOrFiction.class));
        cards.add(new SetCardInfo("Fyndhorn Elves", 4, Rarity.MYTHIC, mage.cards.f.FyndhornElves.class));
        cards.add(new SetCardInfo("Gilded Lotus", 12, Rarity.MYTHIC, mage.cards.g.GildedLotus.class));
        cards.add(new SetCardInfo("Green Sun's Zenith", 19, Rarity.MYTHIC, mage.cards.g.GreenSunsZenith.class));
        cards.add(new SetCardInfo("Hymn to Tourach", 3, Rarity.MYTHIC, mage.cards.h.HymnToTourach.class));
        cards.add(new SetCardInfo("Impulse", 5, Rarity.MYTHIC, mage.cards.i.Impulse.class));
        cards.add(new SetCardInfo("Ink-Eyes, Servant of Oni", 13, Rarity.MYTHIC, mage.cards.i.InkEyesServantOfOni.class));
        cards.add(new SetCardInfo("Jace, the Mind Sculptor", 18, Rarity.MYTHIC, mage.cards.j.JaceTheMindSculptor.class));
        cards.add(new SetCardInfo("Kessig Wolf Run", 20, Rarity.MYTHIC, mage.cards.k.KessigWolfRun.class));
        cards.add(new SetCardInfo("Swords to Plowshares", 2, Rarity.MYTHIC, mage.cards.s.SwordsToPlowshares.class));
        cards.add(new SetCardInfo("Tangle Wire", 8, Rarity.MYTHIC, mage.cards.t.TangleWire.class));
        cards.add(new SetCardInfo("Thran Dynamo", 7, Rarity.MYTHIC, mage.cards.t.ThranDynamo.class));
        cards.add(new SetCardInfo("Venser, Shaper Savant", 15, Rarity.MYTHIC, mage.cards.v.VenserShaperSavant.class));
        cards.add(new SetCardInfo("Wall of Blossoms", 6, Rarity.MYTHIC, mage.cards.w.WallOfBlossoms.class));
    }
}
