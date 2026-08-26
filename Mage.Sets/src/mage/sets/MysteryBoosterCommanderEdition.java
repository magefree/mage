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

        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Arzakon", 33, Rarity.RARE, mage.cards.a.Arzakon.class));
        cards.add(new SetCardInfo("Autumn Willow, Harmony", 27, Rarity.RARE, mage.cards.a.AutumnWillowHarmony.class));
        cards.add(new SetCardInfo("Balefang the Unslayable", 20, Rarity.RARE, mage.cards.b.BalefangTheUnslayable.class));
        cards.add(new SetCardInfo("Blor the Impervious", 28, Rarity.RARE, mage.cards.b.BlorTheImpervious.class));
        cards.add(new SetCardInfo("Boss Uramon, Shadow's Reach", 13, Rarity.RARE, mage.cards.b.BossUramonShadowsReach.class));
        cards.add(new SetCardInfo("Chaos Warp", 72, Rarity.RARE, mage.cards.c.ChaosWarp.class));
        cards.add(new SetCardInfo("Chromatic Lantern", 73, Rarity.RARE, mage.cards.c.ChromaticLantern.class));
        cards.add(new SetCardInfo("Davvol, Evincar of Rath", 36, Rarity.RARE, mage.cards.d.DavvolEvincarOfRath.class));
        cards.add(new SetCardInfo("Dust to Dust", 71, Rarity.UNCOMMON, mage.cards.d.DustToDust.class));
        cards.add(new SetCardInfo("Dyfed, the Guiding Hand", 7, Rarity.RARE, mage.cards.d.DyfedTheGuidingHand.class));
        cards.add(new SetCardInfo("Emerald Collector", 63, Rarity.UNCOMMON, mage.cards.e.EmeraldCollector.class));
        cards.add(new SetCardInfo("Exotic Orchard", 79, Rarity.RARE, mage.cards.e.ExoticOrchard.class));
        cards.add(new SetCardInfo("Fellwar Stone", 74, Rarity.UNCOMMON, mage.cards.f.FellwarStone.class));
        cards.add(new SetCardInfo("Feroz, Ulgrotha's Warden", 38, Rarity.RARE, mage.cards.f.FerozUlgrothasWarden.class));
        cards.add(new SetCardInfo("Grandmother Goby", 9, Rarity.RARE, mage.cards.g.GrandmotherGoby.class));
        cards.add(new SetCardInfo("Greensleeves", 30, Rarity.RARE, mage.cards.g.Greensleeves.class));
        cards.add(new SetCardInfo("Istvan, Butcher of Eln", 14, Rarity.RARE, mage.cards.i.IstvanButcherOfEln.class));
        cards.add(new SetCardInfo("Jandor, Fortuned Traveler", 42, Rarity.RARE, mage.cards.j.JandorFortunedTraveler.class));
        cards.add(new SetCardInfo("Jeweled Amulet", 75, Rarity.UNCOMMON, mage.cards.j.JeweledAmulet.class));
        cards.add(new SetCardInfo("Joven and Chandler", 24, Rarity.RARE, mage.cards.j.JovenAndChandler.class));
        cards.add(new SetCardInfo("Meatsqueak, Hoard Lord", 32, Rarity.RARE, mage.cards.m.MeatsqueakHoardLord.class));
        cards.add(new SetCardInfo("Mind Stone", 76, Rarity.UNCOMMON, mage.cards.m.MindStone.class));
        cards.add(new SetCardInfo("Olag and Miau, New Friends", 48, Rarity.RARE, mage.cards.o.OlagAndMiauNewFriends.class));
        cards.add(new SetCardInfo("Oracle of the Alpha", 64, Rarity.UNCOMMON, mage.cards.o.OracleOfTheAlpha.class));
        cards.add(new SetCardInfo("Overcooked", 67, Rarity.UNCOMMON, mage.cards.o.Overcooked.class));
        cards.add(new SetCardInfo("Path of Ancestry", 80, Rarity.COMMON, mage.cards.p.PathOfAncestry.class));
        cards.add(new SetCardInfo("Ruby Collector", 61, Rarity.UNCOMMON, mage.cards.r.RubyCollector.class));
        cards.add(new SetCardInfo("Swiftfoot Boots", 77, Rarity.UNCOMMON, mage.cards.s.SwiftfootBoots.class));
        cards.add(new SetCardInfo("Thought Vessel", 78, Rarity.UNCOMMON, mage.cards.t.ThoughtVessel.class));
        cards.add(new SetCardInfo("Tsagan, Raider Warlord", 53, Rarity.RARE, mage.cards.t.TsaganRaiderWarlord.class));
        cards.add(new SetCardInfo("Worzel, the Protector", 5, Rarity.RARE, mage.cards.w.WorzelTheProtector.class));
    }
}
