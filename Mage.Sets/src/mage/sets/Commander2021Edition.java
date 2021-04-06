package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class Commander2021Edition extends ExpansionSet {

    private static final Commander2021Edition instance = new Commander2021Edition();

    public static Commander2021Edition getInstance() {
        return instance;
    }

    private Commander2021Edition() {
        super("Commander 2021 Edition", "C21", ExpansionSet.buildDate(2021, 4, 23), SetType.SUPPLEMENTAL);
        this.blockName = "Command Zone";
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Ancient Den", 276, Rarity.SPECIAL, mage.cards.a.AncientDen.class));
        cards.add(new SetCardInfo("Angel of the Ruins", 11, Rarity.RARE, mage.cards.a.AngelOfTheRuins.class));
        cards.add(new SetCardInfo("Arcane Signet", 234, Rarity.COMMON, mage.cards.a.ArcaneSignet.class));
        cards.add(new SetCardInfo("Boros Locket", 236, Rarity.COMMON, mage.cards.b.BorosLocket.class));
        cards.add(new SetCardInfo("Bosh, Iron Golem", 237, Rarity.RARE, mage.cards.b.BoshIronGolem.class));
        cards.add(new SetCardInfo("Combustible Gearhulk", 163, Rarity.MYTHIC, mage.cards.c.CombustibleGearhulk.class));
        cards.add(new SetCardInfo("Commander's Sphere", 239, Rarity.COMMON, mage.cards.c.CommandersSphere.class));
        cards.add(new SetCardInfo("Daretti, Scrap Savant", 164, Rarity.MYTHIC, mage.cards.d.DarettiScrapSavant.class));
        cards.add(new SetCardInfo("Darksteel Citadel", 285, Rarity.UNCOMMON, mage.cards.d.DarksteelCitadel.class));
        cards.add(new SetCardInfo("Duplicant", 242, Rarity.RARE, mage.cards.d.Duplicant.class));
        cards.add(new SetCardInfo("Excavation Technique", 16, Rarity.RARE, mage.cards.e.ExcavationTechnique.class));
        cards.add(new SetCardInfo("Faithless Looting", 168, Rarity.COMMON, mage.cards.f.FaithlessLooting.class));
        cards.add(new SetCardInfo("Feldon of the Third Path", 169, Rarity.MYTHIC, mage.cards.f.FeldonOfTheThirdPath.class));
        cards.add(new SetCardInfo("Great Furnace", 292, Rarity.COMMON, mage.cards.g.GreatFurnace.class));
        cards.add(new SetCardInfo("Hedron Archive", 244, Rarity.UNCOMMON, mage.cards.h.HedronArchive.class));
        cards.add(new SetCardInfo("Hellkite Tyrant", 172, Rarity.MYTHIC, mage.cards.h.HellkiteTyrant.class));
        cards.add(new SetCardInfo("Ichor Wellspring", 245, Rarity.COMMON, mage.cards.i.IchorWellspring.class));
        cards.add(new SetCardInfo("Meteor Golem", 250, Rarity.UNCOMMON, mage.cards.m.MeteorGolem.class));
        cards.add(new SetCardInfo("Mind Stone", 252, Rarity.COMMON, mage.cards.m.MindStone.class));
        cards.add(new SetCardInfo("Monologue Tax", 19, Rarity.RARE, mage.cards.m.MonologueTax.class));
        cards.add(new SetCardInfo("Myr Battlesphere", 253, Rarity.COMMON, mage.cards.m.MyrBattlesphere.class));
        cards.add(new SetCardInfo("Sculpting Steel", 261, Rarity.RARE, mage.cards.s.SculptingSteel.class));
        cards.add(new SetCardInfo("Sol Ring", 263, Rarity.UNCOMMON, mage.cards.s.SolRing.class));
        cards.add(new SetCardInfo("Solemn Simulacrum", 264, Rarity.RARE, mage.cards.s.SolemnSimulacrum.class));
        cards.add(new SetCardInfo("Steel Hellkite", 266, Rarity.RARE, mage.cards.s.SteelHellkite.class));
        cards.add(new SetCardInfo("Steel Overseer", 267, Rarity.RARE, mage.cards.s.SteelOverseer.class));
        cards.add(new SetCardInfo("Thopter Engineer", 181, Rarity.UNCOMMON, mage.cards.t.ThopterEngineer.class));
        cards.add(new SetCardInfo("Thousand-Year Elixir", 271, Rarity.RARE, mage.cards.t.ThousandYearElixir.class));
    }
}
