package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author muz
 */

public final class MysteryBoosterCommanderEdition extends ExpansionSet {

    private static final MysteryBoosterCommanderEdition instance = new MysteryBoosterCommanderEdition();

    public static MysteryBoosterCommanderEdition getInstance() {
        return instance;
    }

    private MysteryBoosterCommanderEdition() {
        super("Mystery Booster Commander Edition", "MBC", ExpansionSet.buildDate(2027, 10, 1), SetType.SUPPLEMENTAL);
        this.blockName = "Mystery Booster Commander Edition";

        this.hasBasicLands = false; // TODO: Confirm once more information is released about the set
        // this.enableSetBooster(Integer.MAX_VALUE);

        cards.add(new SetCardInfo("Blor the Impervious", 28, Rarity.RARE, mage.cards.b.BlorTheImpervious.class));
        cards.add(new SetCardInfo("Chaos Warp", 72, Rarity.RARE, mage.cards.c.ChaosWarp.class));
        cards.add(new SetCardInfo("Chromatic Lantern", 73, Rarity.RARE, mage.cards.c.ChromaticLantern.class));
        cards.add(new SetCardInfo("Dust to Dust", 71, Rarity.UNCOMMON, mage.cards.d.DustToDust.class));
        cards.add(new SetCardInfo("Exotic Orchard", 79, Rarity.RARE, mage.cards.e.ExoticOrchard.class));
        cards.add(new SetCardInfo("Fellwar Stone", 74, Rarity.UNCOMMON, mage.cards.f.FellwarStone.class));
        cards.add(new SetCardInfo("Jeweled Amulet", 75, Rarity.UNCOMMON, mage.cards.j.JeweledAmulet.class));
        cards.add(new SetCardInfo("Joven and Chandler", 24, Rarity.RARE, mage.cards.j.JovenAndChandler.class));
        cards.add(new SetCardInfo("Mind Stone", 76, Rarity.UNCOMMON, mage.cards.m.MindStone.class));
        cards.add(new SetCardInfo("Oracle of the Alpha", 64, Rarity.UNCOMMON, mage.cards.o.OracleOfTheAlpha.class));
        cards.add(new SetCardInfo("Path of Ancestry", 80, Rarity.COMMON, mage.cards.p.PathOfAncestry.class));
        cards.add(new SetCardInfo("Swiftfoot Boots", 77, Rarity.UNCOMMON, mage.cards.s.SwiftfootBoots.class));
        cards.add(new SetCardInfo("Thought Vessel", 78, Rarity.UNCOMMON, mage.cards.t.ThoughtVessel.class));
        cards.add(new SetCardInfo("Tsagan, Raider Warlord", 53, Rarity.RARE, mage.cards.t.TsaganRaiderWarlord.class));
    }
}
