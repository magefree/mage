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

        cards.add(new SetCardInfo("Adrix and Nev, Twincasters", 9, Rarity.MYTHIC, mage.cards.a.AdrixAndNevTwincasters.class));
        cards.add(new SetCardInfo("Ancient Den", 276, Rarity.COMMON, mage.cards.a.AncientDen.class));
        cards.add(new SetCardInfo("Angel of the Ruins", 11, Rarity.RARE, mage.cards.a.AngelOfTheRuins.class));
        cards.add(new SetCardInfo("Arcane Signet", 234, Rarity.COMMON, mage.cards.a.ArcaneSignet.class));
        cards.add(new SetCardInfo("Audacious Reshapers", 47, Rarity.RARE, mage.cards.a.AudaciousReshapers.class));
        cards.add(new SetCardInfo("Boros Charm", 210, Rarity.UNCOMMON, mage.cards.b.BorosCharm.class));
        cards.add(new SetCardInfo("Boros Locket", 236, Rarity.COMMON, mage.cards.b.BorosLocket.class));
        cards.add(new SetCardInfo("Bosh, Iron Golem", 237, Rarity.RARE, mage.cards.b.BoshIronGolem.class));
        cards.add(new SetCardInfo("Bronze Guardian", 13, Rarity.RARE, mage.cards.b.BronzeGuardian.class));
        cards.add(new SetCardInfo("Burnished Hart", 238, Rarity.UNCOMMON, mage.cards.b.BurnishedHart.class));
        cards.add(new SetCardInfo("Chain Reaction", 161, Rarity.RARE, mage.cards.c.ChainReaction.class));
        cards.add(new SetCardInfo("Cleansing Nova", 86, Rarity.RARE, mage.cards.c.CleansingNova.class));
        cards.add(new SetCardInfo("Combustible Gearhulk", 163, Rarity.MYTHIC, mage.cards.c.CombustibleGearhulk.class));
        cards.add(new SetCardInfo("Commander's Sphere", 239, Rarity.COMMON, mage.cards.c.CommandersSphere.class));
        cards.add(new SetCardInfo("Daretti, Scrap Savant", 164, Rarity.MYTHIC, mage.cards.d.DarettiScrapSavant.class));
        cards.add(new SetCardInfo("Darksteel Citadel", 285, Rarity.UNCOMMON, mage.cards.d.DarksteelCitadel.class));
        cards.add(new SetCardInfo("Darksteel Mutation", 87, Rarity.UNCOMMON, mage.cards.d.DarksteelMutation.class));
        cards.add(new SetCardInfo("Deekah, Fractal Theorist", 26, Rarity.RARE, mage.cards.d.DeekahFractalTheorist.class));
        cards.add(new SetCardInfo("Digsite Engineer", 15, Rarity.RARE, mage.cards.d.DigsiteEngineer.class));
        cards.add(new SetCardInfo("Dispatch", 88, Rarity.UNCOMMON, mage.cards.d.Dispatch.class));
        cards.add(new SetCardInfo("Dispeller's Capsule", 89, Rarity.COMMON, mage.cards.d.DispellersCapsule.class));
        cards.add(new SetCardInfo("Duplicant", 242, Rarity.RARE, mage.cards.d.Duplicant.class));
        cards.add(new SetCardInfo("Excavation Technique", 16, Rarity.RARE, mage.cards.e.ExcavationTechnique.class));
        cards.add(new SetCardInfo("Faithless Looting", 168, Rarity.COMMON, mage.cards.f.FaithlessLooting.class));
        cards.add(new SetCardInfo("Feldon of the Third Path", 169, Rarity.MYTHIC, mage.cards.f.FeldonOfTheThirdPath.class));
        cards.add(new SetCardInfo("Fiery Encore", 51, Rarity.RARE, mage.cards.f.FieryEncore.class));
        cards.add(new SetCardInfo("Great Furnace", 292, Rarity.COMMON, mage.cards.g.GreatFurnace.class));
        cards.add(new SetCardInfo("Hedron Archive", 244, Rarity.UNCOMMON, mage.cards.h.HedronArchive.class));
        cards.add(new SetCardInfo("Hellkite Igniter", 171, Rarity.RARE, mage.cards.h.HellkiteIgniter.class));
        cards.add(new SetCardInfo("Hellkite Tyrant", 172, Rarity.MYTHIC, mage.cards.h.HellkiteTyrant.class));
        cards.add(new SetCardInfo("Hoard-Smelter Dragon", 173, Rarity.RARE, mage.cards.h.HoardSmelterDragon.class));
        cards.add(new SetCardInfo("Ichor Wellspring", 245, Rarity.COMMON, mage.cards.i.IchorWellspring.class));
        cards.add(new SetCardInfo("Inferno Project", 52, Rarity.RARE, mage.cards.i.InfernoProject.class));
        cards.add(new SetCardInfo("Jor Kadeen, the Prevailer", 220, Rarity.RARE, mage.cards.j.JorKadeenThePrevailer.class));
        cards.add(new SetCardInfo("Key to the City", 248, Rarity.RARE, mage.cards.k.KeyToTheCity.class));
        cards.add(new SetCardInfo("Meteor Golem", 250, Rarity.UNCOMMON, mage.cards.m.MeteorGolem.class));
        cards.add(new SetCardInfo("Mind Stone", 251, Rarity.COMMON, mage.cards.m.MindStone.class));
        cards.add(new SetCardInfo("Monologue Tax", 19, Rarity.RARE, mage.cards.m.MonologueTax.class));
        cards.add(new SetCardInfo("Myr Battlesphere", 253, Rarity.RARE, mage.cards.m.MyrBattlesphere.class));
        cards.add(new SetCardInfo("Phyrexia's Core", 309, Rarity.UNCOMMON, mage.cards.p.PhyrexiasCore.class));
        cards.add(new SetCardInfo("Pia Nalaar", 177, Rarity.RARE, mage.cards.p.PiaNalaar.class));
        cards.add(new SetCardInfo("Pilgrim's Eye", 257, Rarity.COMMON, mage.cards.p.PilgrimsEye.class));
        cards.add(new SetCardInfo("Quicksmith Genius", 178, Rarity.UNCOMMON, mage.cards.q.QuicksmithGenius.class));
        cards.add(new SetCardInfo("Return to Dust", 100, Rarity.UNCOMMON, mage.cards.r.ReturnToDust.class));
        cards.add(new SetCardInfo("Rout", 101, Rarity.RARE, mage.cards.r.Rout.class));
        cards.add(new SetCardInfo("Sanctum Gargoyle", 102, Rarity.COMMON, mage.cards.s.SanctumGargoyle.class));
        cards.add(new SetCardInfo("Scrap Trawler", 260, Rarity.RARE, mage.cards.s.ScrapTrawler.class));
        cards.add(new SetCardInfo("Sculpting Steel", 261, Rarity.RARE, mage.cards.s.SculptingSteel.class));
        cards.add(new SetCardInfo("Slayers' Stronghold", 318, Rarity.RARE, mage.cards.s.SlayersStronghold.class));
        cards.add(new SetCardInfo("Sol Ring", 263, Rarity.UNCOMMON, mage.cards.s.SolRing.class));
        cards.add(new SetCardInfo("Solemn Simulacrum", 264, Rarity.RARE, mage.cards.s.SolemnSimulacrum.class));
        cards.add(new SetCardInfo("Steel Hellkite", 266, Rarity.RARE, mage.cards.s.SteelHellkite.class));
        cards.add(new SetCardInfo("Steel Overseer", 267, Rarity.RARE, mage.cards.s.SteelOverseer.class));
        cards.add(new SetCardInfo("Sun Titan", 106, Rarity.MYTHIC, mage.cards.s.SunTitan.class));
        cards.add(new SetCardInfo("Sunhome, Fortress of the Legion", 319, Rarity.UNCOMMON, mage.cards.s.SunhomeFortressOfTheLegion.class));
        cards.add(new SetCardInfo("Temple of Triumph", 327, Rarity.RARE, mage.cards.t.TempleOfTriumph.class));
        cards.add(new SetCardInfo("Thopter Engineer", 181, Rarity.UNCOMMON, mage.cards.t.ThopterEngineer.class));
        cards.add(new SetCardInfo("Thousand-Year Elixir", 271, Rarity.RARE, mage.cards.t.ThousandYearElixir.class));
        cards.add(new SetCardInfo("Unstable Obelisk", 272, Rarity.UNCOMMON, mage.cards.u.UnstableObelisk.class));
        cards.add(new SetCardInfo("Veyran, Voice of Duality", 3, Rarity.MYTHIC, mage.cards.v.VeyranVoiceOfDuality.class));
        cards.add(new SetCardInfo("Willowdusk, Essence Seer", 6, Rarity.MYTHIC, mage.cards.w.WillowduskEssenceSeer.class));
    }
}
