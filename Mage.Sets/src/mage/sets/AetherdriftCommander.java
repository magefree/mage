package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class AetherdriftCommander extends ExpansionSet {

    private static final AetherdriftCommander instance = new AetherdriftCommander();

    public static AetherdriftCommander getInstance() {
        return instance;
    }

    private AetherdriftCommander() {
        super("Aetherdrift Commander", "DRC", ExpansionSet.buildDate(2025, 1, 21), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Academy Ruins", 58, Rarity.COMMON, mage.cards.a.AcademyRuins.class));
        cards.add(new SetCardInfo("Aether Hub", 145, Rarity.UNCOMMON, mage.cards.a.AetherHub.class));
        cards.add(new SetCardInfo("Aethersquall Ancient", 68, Rarity.RARE, mage.cards.a.AethersquallAncient.class));
        cards.add(new SetCardInfo("Aethertide Whale", 69, Rarity.RARE, mage.cards.a.AethertideWhale.class));
        cards.add(new SetCardInfo("Aetherwind Basker", 107, Rarity.MYTHIC, mage.cards.a.AetherwindBasker.class));
        cards.add(new SetCardInfo("Aetherworks Marvel", 123, Rarity.MYTHIC, mage.cards.a.AetherworksMarvel.class));
        cards.add(new SetCardInfo("Arcane Denial", 70, Rarity.COMMON, mage.cards.a.ArcaneDenial.class));
        cards.add(new SetCardInfo("Arcane Signet", 52, Rarity.COMMON, mage.cards.a.ArcaneSignet.class));
        cards.add(new SetCardInfo("Architect of the Untamed", 108, Rarity.RARE, mage.cards.a.ArchitectOfTheUntamed.class));
        cards.add(new SetCardInfo("Attune with Aether", 109, Rarity.COMMON, mage.cards.a.AttuneWithAether.class));
        cards.add(new SetCardInfo("Bespoke Battlewagon", 71, Rarity.UNCOMMON, mage.cards.b.BespokeBattlewagon.class));
        cards.add(new SetCardInfo("Blasphemous Act", 101, Rarity.RARE, mage.cards.b.BlasphemousAct.class));
        cards.add(new SetCardInfo("Bootleggers' Stash", 110, Rarity.MYTHIC, mage.cards.b.BootleggersStash.class));
        cards.add(new SetCardInfo("Chain Reaction", 47, Rarity.RARE, mage.cards.c.ChainReaction.class));
        cards.add(new SetCardInfo("Chaos Warp", 48, Rarity.RARE, mage.cards.c.ChaosWarp.class));
        cards.add(new SetCardInfo("Chromatic Lantern", 53, Rarity.COMMON, mage.cards.c.ChromaticLantern.class));
        cards.add(new SetCardInfo("Combustible Gearhulk", 102, Rarity.MYTHIC, mage.cards.c.CombustibleGearhulk.class));
        cards.add(new SetCardInfo("Command Tower", 60, Rarity.COMMON, mage.cards.c.CommandTower.class));
        cards.add(new SetCardInfo("Commander's Sphere", 125, Rarity.COMMON, mage.cards.c.CommandersSphere.class));
        cards.add(new SetCardInfo("Confiscation Coup", 73, Rarity.RARE, mage.cards.c.ConfiscationCoup.class));
        cards.add(new SetCardInfo("Conjurer's Closet", 126, Rarity.RARE, mage.cards.c.ConjurersCloset.class));
        cards.add(new SetCardInfo("Cultivator's Caravan", 127, Rarity.RARE, mage.cards.c.CultivatorsCaravan.class));
        cards.add(new SetCardInfo("Decoction Module", 128, Rarity.UNCOMMON, mage.cards.d.DecoctionModule.class));
        cards.add(new SetCardInfo("Disallow", 74, Rarity.RARE, mage.cards.d.Disallow.class));
        cards.add(new SetCardInfo("Druid of Purification", 49, Rarity.COMMON, mage.cards.d.DruidOfPurification.class));
        cards.add(new SetCardInfo("Duplicant", 54, Rarity.RARE, mage.cards.d.Duplicant.class));
        cards.add(new SetCardInfo("Elder Gargaroth", 111, Rarity.MYTHIC, mage.cards.e.ElderGargaroth.class));
        cards.add(new SetCardInfo("Era of Innovation", 75, Rarity.UNCOMMON, mage.cards.e.EraOfInnovation.class));
        cards.add(new SetCardInfo("Evolving Wilds", 154, Rarity.COMMON, mage.cards.e.EvolvingWilds.class));
        cards.add(new SetCardInfo("Exotic Orchard", 155, Rarity.RARE, mage.cards.e.ExoticOrchard.class));
        cards.add(new SetCardInfo("Explosive Vegetation", 112, Rarity.UNCOMMON, mage.cards.e.ExplosiveVegetation.class));
        cards.add(new SetCardInfo("Frontier Bivouac", 157, Rarity.UNCOMMON, mage.cards.f.FrontierBivouac.class));
        cards.add(new SetCardInfo("Frostboil Snarl", 158, Rarity.RARE, mage.cards.f.FrostboilSnarl.class));
        cards.add(new SetCardInfo("Glimmer of Genius", 78, Rarity.UNCOMMON, mage.cards.g.GlimmerOfGenius.class));
        cards.add(new SetCardInfo("Hinterland Harbor", 160, Rarity.RARE, mage.cards.h.HinterlandHarbor.class));
        cards.add(new SetCardInfo("Karplusan Forest", 163, Rarity.RARE, mage.cards.k.KarplusanForest.class));
        cards.add(new SetCardInfo("Lightning Greaves", 55, Rarity.COMMON, mage.cards.l.LightningGreaves.class));
        cards.add(new SetCardInfo("Lightning Runner", 103, Rarity.MYTHIC, mage.cards.l.LightningRunner.class));
        cards.add(new SetCardInfo("Loyal Apprentice", 104, Rarity.UNCOMMON, mage.cards.l.LoyalApprentice.class));
        cards.add(new SetCardInfo("Midnight Clock", 79, Rarity.RARE, mage.cards.m.MidnightClock.class));
        cards.add(new SetCardInfo("One with the Machine", 80, Rarity.RARE, mage.cards.o.OneWithTheMachine.class));
        cards.add(new SetCardInfo("Ornithopter of Paradise", 133, Rarity.COMMON, mage.cards.o.OrnithopterOfParadise.class));
        cards.add(new SetCardInfo("Overflowing Basin", 165, Rarity.RARE, mage.cards.o.OverflowingBasin.class));
        cards.add(new SetCardInfo("Panharmonicon", 135, Rarity.RARE, mage.cards.p.Panharmonicon.class));
        cards.add(new SetCardInfo("Path of Ancestry", 166, Rarity.COMMON, mage.cards.p.PathOfAncestry.class));
        cards.add(new SetCardInfo("Peema Aether-Seer", 113, Rarity.UNCOMMON, mage.cards.p.PeemaAetherSeer.class));
        cards.add(new SetCardInfo("Pia and Kiran Nalaar", 105, Rarity.RARE, mage.cards.p.PiaAndKiranNalaar.class));
        cards.add(new SetCardInfo("Reality Shift", 39, Rarity.UNCOMMON, mage.cards.r.RealityShift.class));
        cards.add(new SetCardInfo("Reckless Fireweaver", 106, Rarity.COMMON, mage.cards.r.RecklessFireweaver.class));
        cards.add(new SetCardInfo("Retrofitter Foundry", 136, Rarity.RARE, mage.cards.r.RetrofitterFoundry.class));
        cards.add(new SetCardInfo("Rogue Refiner", 118, Rarity.UNCOMMON, mage.cards.r.RogueRefiner.class));
        cards.add(new SetCardInfo("Rootbound Crag", 168, Rarity.RARE, mage.cards.r.RootboundCrag.class));
        cards.add(new SetCardInfo("Saheeli, Sublime Artificer", 119, Rarity.UNCOMMON, mage.cards.s.SaheeliSublimeArtificer.class));
        cards.add(new SetCardInfo("Sai, Master Thopterist", 82, Rarity.RARE, mage.cards.s.SaiMasterThopterist.class));
        cards.add(new SetCardInfo("Servant of the Conduit", 114, Rarity.UNCOMMON, mage.cards.s.ServantOfTheConduit.class));
        cards.add(new SetCardInfo("Sheltered Thicket", 169, Rarity.RARE, mage.cards.s.ShelteredThicket.class));
        cards.add(new SetCardInfo("Shivan Reef", 170, Rarity.RARE, mage.cards.s.ShivanReef.class));
        cards.add(new SetCardInfo("Slagwoods Bridge", 171, Rarity.COMMON, mage.cards.s.SlagwoodsBridge.class));
        cards.add(new SetCardInfo("Sol Ring", 57, Rarity.UNCOMMON, mage.cards.s.SolRing.class));
        cards.add(new SetCardInfo("Solar Transformer", 137, Rarity.UNCOMMON, mage.cards.s.SolarTransformer.class));
        cards.add(new SetCardInfo("Solemn Simulacrum", 138, Rarity.RARE, mage.cards.s.SolemnSimulacrum.class));
        cards.add(new SetCardInfo("Soul-Guide Lantern", 139, Rarity.UNCOMMON, mage.cards.s.SoulGuideLantern.class));
        cards.add(new SetCardInfo("Spire of Industry", 172, Rarity.RARE, mage.cards.s.SpireOfIndustry.class));
        cards.add(new SetCardInfo("Sulfur Falls", 173, Rarity.RARE, mage.cards.s.SulfurFalls.class));
        cards.add(new SetCardInfo("Talisman of Curiosity", 140, Rarity.UNCOMMON, mage.cards.t.TalismanOfCuriosity.class));
        cards.add(new SetCardInfo("Tanglepool Bridge", 175, Rarity.COMMON, mage.cards.t.TanglepoolBridge.class));
        cards.add(new SetCardInfo("Temmet, Naktamun's Will", 4, Rarity.MYTHIC, mage.cards.t.TemmetNaktamunsWill.class));
        cards.add(new SetCardInfo("Temple of Epiphany", 177, Rarity.RARE, mage.cards.t.TempleOfEpiphany.class));
        cards.add(new SetCardInfo("Thopter Spy Network", 83, Rarity.RARE, mage.cards.t.ThopterSpyNetwork.class));
        cards.add(new SetCardInfo("Treasure Vault", 180, Rarity.RARE, mage.cards.t.TreasureVault.class));
        cards.add(new SetCardInfo("Triplicate Titan", 143, Rarity.RARE, mage.cards.t.TriplicateTitan.class));
        cards.add(new SetCardInfo("Vineglimmer Snarl", 183, Rarity.RARE, mage.cards.v.VineglimmerSnarl.class));
        cards.add(new SetCardInfo("Whirler Rogue", 85, Rarity.UNCOMMON, mage.cards.w.WhirlerRogue.class));
        cards.add(new SetCardInfo("Whirler Virtuoso", 122, Rarity.UNCOMMON, mage.cards.w.WhirlerVirtuoso.class));
        cards.add(new SetCardInfo("Yavimaya Coast", 184, Rarity.RARE, mage.cards.y.YavimayaCoast.class));
    }
}
