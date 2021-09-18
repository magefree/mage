package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class MidnightHuntCommander extends ExpansionSet {

    private static final MidnightHuntCommander instance = new MidnightHuntCommander();

    public static MidnightHuntCommander getInstance() {
        return instance;
    }

    private MidnightHuntCommander() {
        super("Midnight Hunt Commander", "MIC", ExpansionSet.buildDate(2021, 9, 24), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Abzan Falconer", 77, Rarity.UNCOMMON, mage.cards.a.AbzanFalconer.class));
        cards.add(new SetCardInfo("Aetherspouts", 97, Rarity.RARE, mage.cards.a.Aetherspouts.class));
        cards.add(new SetCardInfo("Ainok Bond-Kin", 78, Rarity.COMMON, mage.cards.a.AinokBondKin.class));
        cards.add(new SetCardInfo("Angel of Glory's Rise", 79, Rarity.RARE, mage.cards.a.AngelOfGlorysRise.class));
        cards.add(new SetCardInfo("Arcane Signet", 157, Rarity.COMMON, mage.cards.a.ArcaneSignet.class));
        cards.add(new SetCardInfo("Army of the Damned", 106, Rarity.MYTHIC, mage.cards.a.ArmyOfTheDamned.class));
        cards.add(new SetCardInfo("Avacyn's Memorial", 31, Rarity.MYTHIC, mage.cards.a.AvacynsMemorial.class));
        cards.add(new SetCardInfo("Avacyn's Pilgrim", 132, Rarity.COMMON, mage.cards.a.AvacynsPilgrim.class));
        cards.add(new SetCardInfo("Bastion Protector", 80, Rarity.RARE, mage.cards.b.BastionProtector.class));
        cards.add(new SetCardInfo("Beast Within", 133, Rarity.UNCOMMON, mage.cards.b.BeastWithin.class));
        cards.add(new SetCardInfo("Bestial Menace", 134, Rarity.UNCOMMON, mage.cards.b.BestialMenace.class));
        cards.add(new SetCardInfo("Biogenic Upgrade", 135, Rarity.UNCOMMON, mage.cards.b.BiogenicUpgrade.class));
        cards.add(new SetCardInfo("Blighted Woodland", 166, Rarity.UNCOMMON, mage.cards.b.BlightedWoodland.class));
        cards.add(new SetCardInfo("Bojuka Bog", 167, Rarity.COMMON, mage.cards.b.BojukaBog.class));
        cards.add(new SetCardInfo("Butcher of Malakir", 107, Rarity.RARE, mage.cards.b.ButcherOfMalakir.class));
        cards.add(new SetCardInfo("Canopy Vista", 168, Rarity.RARE, mage.cards.c.CanopyVista.class));
        cards.add(new SetCardInfo("Celebrate the Harvest", 24, Rarity.RARE, mage.cards.c.CelebrateTheHarvest.class));
        cards.add(new SetCardInfo("Celestial Judgment", 5, Rarity.RARE, mage.cards.c.CelestialJudgment.class));
        cards.add(new SetCardInfo("Cemetery Reaper", 108, Rarity.RARE, mage.cards.c.CemeteryReaper.class));
        cards.add(new SetCardInfo("Champion of Lambholt", 136, Rarity.RARE, mage.cards.c.ChampionOfLambholt.class));
        cards.add(new SetCardInfo("Charcoal Diamond", 158, Rarity.COMMON, mage.cards.c.CharcoalDiamond.class));
        cards.add(new SetCardInfo("Choked Estuary", 169, Rarity.RARE, mage.cards.c.ChokedEstuary.class));
        cards.add(new SetCardInfo("Citadel Siege", 81, Rarity.RARE, mage.cards.c.CitadelSiege.class));
        cards.add(new SetCardInfo("Cleansing Nova", 82, Rarity.RARE, mage.cards.c.CleansingNova.class));
        cards.add(new SetCardInfo("Command Tower", 170, Rarity.COMMON, mage.cards.c.CommandTower.class));
        cards.add(new SetCardInfo("Commander's Sphere", 159, Rarity.COMMON, mage.cards.c.CommandersSphere.class));
        cards.add(new SetCardInfo("Corpse Augur", 109, Rarity.UNCOMMON, mage.cards.c.CorpseAugur.class));
        cards.add(new SetCardInfo("Crowded Crypt", 17, Rarity.RARE, mage.cards.c.CrowdedCrypt.class));
        cards.add(new SetCardInfo("Custodi Soulbinders", 83, Rarity.RARE, mage.cards.c.CustodiSoulbinders.class));
        cards.add(new SetCardInfo("Dark Salvation", 110, Rarity.RARE, mage.cards.d.DarkSalvation.class));
        cards.add(new SetCardInfo("Darkwater Catacombs", 171, Rarity.RARE, mage.cards.d.DarkwaterCatacombs.class));
        cards.add(new SetCardInfo("Dearly Departed", 84, Rarity.RARE, mage.cards.d.DearlyDeparted.class));
        cards.add(new SetCardInfo("Death Baron", 111, Rarity.RARE, mage.cards.d.DeathBaron.class));
        cards.add(new SetCardInfo("Death's Presence", 137, Rarity.RARE, mage.cards.d.DeathsPresence.class));
        cards.add(new SetCardInfo("Dimir Aqueduct", 172, Rarity.UNCOMMON, mage.cards.d.DimirAqueduct.class));
        cards.add(new SetCardInfo("Diregraf Captain", 148, Rarity.UNCOMMON, mage.cards.d.DiregrafCaptain.class));
        cards.add(new SetCardInfo("Diregraf Colossus", 112, Rarity.RARE, mage.cards.d.DiregrafColossus.class));
        cards.add(new SetCardInfo("Distant Melody", 98, Rarity.COMMON, mage.cards.d.DistantMelody.class));
        cards.add(new SetCardInfo("Dread Summons", 113, Rarity.RARE, mage.cards.d.DreadSummons.class));
        cards.add(new SetCardInfo("Dreadhorde Invasion", 114, Rarity.RARE, mage.cards.d.DreadhordeInvasion.class));
        cards.add(new SetCardInfo("Drown in Dreams", 13, Rarity.RARE, mage.cards.d.DrownInDreams.class));
        cards.add(new SetCardInfo("Eater of Hope", 115, Rarity.RARE, mage.cards.e.EaterOfHope.class));
        cards.add(new SetCardInfo("Elite Scaleguard", 85, Rarity.UNCOMMON, mage.cards.e.EliteScaleguard.class));
        cards.add(new SetCardInfo("Eloise, Nephalia Sleuth", 3, Rarity.MYTHIC, mage.cards.e.EloiseNephaliaSleuth.class));
        cards.add(new SetCardInfo("Endless Ranks of the Dead", 116, Rarity.RARE, mage.cards.e.EndlessRanksOfTheDead.class));
        cards.add(new SetCardInfo("Enduring Scalelord", 149, Rarity.UNCOMMON, mage.cards.e.EnduringScalelord.class));
        cards.add(new SetCardInfo("Eternal Skylord", 99, Rarity.UNCOMMON, mage.cards.e.EternalSkylord.class));
        cards.add(new SetCardInfo("Eternal Witness", 138, Rarity.UNCOMMON, mage.cards.e.EternalWitness.class));
        cards.add(new SetCardInfo("Exotic Orchard", 173, Rarity.RARE, mage.cards.e.ExoticOrchard.class));
        cards.add(new SetCardInfo("Feed the Swarm", 117, Rarity.COMMON, mage.cards.f.FeedTheSwarm.class));
        cards.add(new SetCardInfo("Fleshbag Marauder", 118, Rarity.UNCOMMON, mage.cards.f.FleshbagMarauder.class));
        cards.add(new SetCardInfo("Forgotten Creation", 100, Rarity.RARE, mage.cards.f.ForgottenCreation.class));
        cards.add(new SetCardInfo("Fortified Village", 174, Rarity.RARE, mage.cards.f.FortifiedVillage.class));
        cards.add(new SetCardInfo("Gisa and Geralf", 150, Rarity.MYTHIC, mage.cards.g.GisaAndGeralf.class));
        cards.add(new SetCardInfo("Gleaming Overseer", 151, Rarity.UNCOMMON, mage.cards.g.GleamingOverseer.class));
        cards.add(new SetCardInfo("Go for the Throat", 119, Rarity.UNCOMMON, mage.cards.g.GoForTheThroat.class));
        cards.add(new SetCardInfo("Gravespawn Sovereign", 120, Rarity.RARE, mage.cards.g.GravespawnSovereign.class));
        cards.add(new SetCardInfo("Growth Spasm", 139, Rarity.COMMON, mage.cards.g.GrowthSpasm.class));
        cards.add(new SetCardInfo("Gyre Sage", 140, Rarity.RARE, mage.cards.g.GyreSage.class));
        cards.add(new SetCardInfo("Havengul Runebinder", 101, Rarity.RARE, mage.cards.h.HavengulRunebinder.class));
        cards.add(new SetCardInfo("Herald of War", 86, Rarity.RARE, mage.cards.h.HeraldOfWar.class));
        cards.add(new SetCardInfo("Heron's Grace Champion", 152, Rarity.RARE, mage.cards.h.HeronsGraceChampion.class));
        cards.add(new SetCardInfo("Hour of Eternity", 102, Rarity.RARE, mage.cards.h.HourOfEternity.class));
        cards.add(new SetCardInfo("Hour of Reckoning", 87, Rarity.RARE, mage.cards.h.HourOfReckoning.class));
        cards.add(new SetCardInfo("Inspiring Call", 141, Rarity.UNCOMMON, mage.cards.i.InspiringCall.class));
        cards.add(new SetCardInfo("Juniper Order Ranger", 153, Rarity.UNCOMMON, mage.cards.j.JuniperOrderRanger.class));
        cards.add(new SetCardInfo("Kessig Cagebreakers", 142, Rarity.RARE, mage.cards.k.KessigCagebreakers.class));
        cards.add(new SetCardInfo("Knight of the White Orchid", 88, Rarity.RARE, mage.cards.k.KnightOfTheWhiteOrchid.class));
        cards.add(new SetCardInfo("Krosan Verge", 175, Rarity.UNCOMMON, mage.cards.k.KrosanVerge.class));
        cards.add(new SetCardInfo("Kyler, Sigardian Emissary", 4, Rarity.MYTHIC, mage.cards.k.KylerSigardianEmissary.class));
        cards.add(new SetCardInfo("Leinore, Autumn Sovereign", 1, Rarity.MYTHIC, mage.cards.l.LeinoreAutumnSovereign.class));
        cards.add(new SetCardInfo("Lifecrafter's Bestiary", 160, Rarity.RARE, mage.cards.l.LifecraftersBestiary.class));
        cards.add(new SetCardInfo("Liliana's Devotee", 122, Rarity.UNCOMMON, mage.cards.l.LilianasDevotee.class));
        cards.add(new SetCardInfo("Liliana's Mastery", 123, Rarity.RARE, mage.cards.l.LilianasMastery.class));
        cards.add(new SetCardInfo("Liliana, Death's Majesty", 121, Rarity.MYTHIC, mage.cards.l.LilianaDeathsMajesty.class));
        cards.add(new SetCardInfo("Lord of the Accursed", 124, Rarity.UNCOMMON, mage.cards.l.LordOfTheAccursed.class));
        cards.add(new SetCardInfo("Midnight Reaper", 125, Rarity.RARE, mage.cards.m.MidnightReaper.class));
        cards.add(new SetCardInfo("Mikaeus, the Lunarch", 89, Rarity.MYTHIC, mage.cards.m.MikaeusTheLunarch.class));
        cards.add(new SetCardInfo("Mortuary Mire", 176, Rarity.COMMON, mage.cards.m.MortuaryMire.class));
        cards.add(new SetCardInfo("Myriad Landscape", 177, Rarity.UNCOMMON, mage.cards.m.MyriadLandscape.class));
        cards.add(new SetCardInfo("Odric, Master Tactician", 90, Rarity.RARE, mage.cards.o.OdricMasterTactician.class));
        cards.add(new SetCardInfo("Open the Graves", 126, Rarity.RARE, mage.cards.o.OpenTheGraves.class));
        cards.add(new SetCardInfo("Orzhov Advokist", 91, Rarity.UNCOMMON, mage.cards.o.OrzhovAdvokist.class));
        cards.add(new SetCardInfo("Overseer of the Damned", 127, Rarity.RARE, mage.cards.o.OverseerOfTheDamned.class));
        cards.add(new SetCardInfo("Path of Ancestry", 178, Rarity.COMMON, mage.cards.p.PathOfAncestry.class));
        cards.add(new SetCardInfo("Ravenous Rotbelly", 22, Rarity.RARE, mage.cards.r.RavenousRotbelly.class));
        cards.add(new SetCardInfo("Return to Dust", 92, Rarity.UNCOMMON, mage.cards.r.ReturnToDust.class));
        cards.add(new SetCardInfo("Riders of Gavony", 93, Rarity.RARE, mage.cards.r.RidersOfGavony.class));
        cards.add(new SetCardInfo("Rogue's Passage", 179, Rarity.UNCOMMON, mage.cards.r.RoguesPassage.class));
        cards.add(new SetCardInfo("Rooftop Storm", 103, Rarity.RARE, mage.cards.r.RooftopStorm.class));
        cards.add(new SetCardInfo("Ruthless Deathfang", 154, Rarity.UNCOMMON, mage.cards.r.RuthlessDeathfang.class));
        cards.add(new SetCardInfo("Selesnya Sanctuary", 180, Rarity.UNCOMMON, mage.cards.s.SelesnyaSanctuary.class));
        cards.add(new SetCardInfo("Shamanic Revelation", 143, Rarity.RARE, mage.cards.s.ShamanicRevelation.class));
        cards.add(new SetCardInfo("Sigarda's Vanguard", 8, Rarity.RARE, mage.cards.s.SigardasVanguard.class));
        cards.add(new SetCardInfo("Sigarda, Heron's Grace", 155, Rarity.MYTHIC, mage.cards.s.SigardaHeronsGrace.class));
        cards.add(new SetCardInfo("Sigardian Zealot", 29, Rarity.RARE, mage.cards.s.SigardianZealot.class));
        cards.add(new SetCardInfo("Sky Diamond", 161, Rarity.COMMON, mage.cards.s.SkyDiamond.class));
        cards.add(new SetCardInfo("Sol Ring", 162, Rarity.UNCOMMON, mage.cards.s.SolRing.class));
        cards.add(new SetCardInfo("Somberwald Beastmaster", 30, Rarity.RARE, mage.cards.s.SomberwaldBeastmaster.class));
        cards.add(new SetCardInfo("Somberwald Sage", 144, Rarity.RARE, mage.cards.s.SomberwaldSage.class));
        cards.add(new SetCardInfo("Spark Reaper", 128, Rarity.COMMON, mage.cards.s.SparkReaper.class));
        cards.add(new SetCardInfo("Stalwart Pathlighter", 9, Rarity.RARE, mage.cards.s.StalwartPathlighter.class));
        cards.add(new SetCardInfo("Stitcher Geralf", 104, Rarity.MYTHIC, mage.cards.s.StitcherGeralf.class));
        cards.add(new SetCardInfo("Sungrass Prairie", 181, Rarity.RARE, mage.cards.s.SungrassPrairie.class));
        cards.add(new SetCardInfo("Sunken Hollow", 182, Rarity.RARE, mage.cards.s.SunkenHollow.class));
        cards.add(new SetCardInfo("Swiftfoot Boots", 163, Rarity.UNCOMMON, mage.cards.s.SwiftfootBoots.class));
        cards.add(new SetCardInfo("Swords to Plowshares", 94, Rarity.UNCOMMON, mage.cards.s.SwordsToPlowshares.class));
        cards.add(new SetCardInfo("Syphon Flesh", 129, Rarity.UNCOMMON, mage.cards.s.SyphonFlesh.class));
        cards.add(new SetCardInfo("Tainted Isle", 183, Rarity.UNCOMMON, mage.cards.t.TaintedIsle.class));
        cards.add(new SetCardInfo("Talisman of Dominance", 164, Rarity.UNCOMMON, mage.cards.t.TalismanOfDominance.class));
        cards.add(new SetCardInfo("Talisman of Unity", 165, Rarity.UNCOMMON, mage.cards.t.TalismanOfUnity.class));
        cards.add(new SetCardInfo("Temple of Deceit", 184, Rarity.RARE, mage.cards.t.TempleOfDeceit.class));
        cards.add(new SetCardInfo("Temple of Plenty", 185, Rarity.RARE, mage.cards.t.TempleOfPlenty.class));
        cards.add(new SetCardInfo("Temple of the False God", 186, Rarity.UNCOMMON, mage.cards.t.TempleOfTheFalseGod.class));
        cards.add(new SetCardInfo("Tomb Tyrant", 23, Rarity.RARE, mage.cards.t.TombTyrant.class));
        cards.add(new SetCardInfo("Trostani's Summoner", 156, Rarity.UNCOMMON, mage.cards.t.TrostanisSummoner.class));
        cards.add(new SetCardInfo("Unbreakable Formation", 95, Rarity.RARE, mage.cards.u.UnbreakableFormation.class));
        cards.add(new SetCardInfo("Unclaimed Territory", 187, Rarity.UNCOMMON, mage.cards.u.UnclaimedTerritory.class));
        cards.add(new SetCardInfo("Undead Alchemist", 105, Rarity.RARE, mage.cards.u.UndeadAlchemist.class));
        cards.add(new SetCardInfo("Undead Augur", 130, Rarity.UNCOMMON, mage.cards.u.UndeadAugur.class));
        cards.add(new SetCardInfo("Verdurous Gearhulk", 145, Rarity.MYTHIC, mage.cards.v.VerdurousGearhulk.class));
        cards.add(new SetCardInfo("Victory's Envoy", 96, Rarity.RARE, mage.cards.v.VictorysEnvoy.class));
        cards.add(new SetCardInfo("Visions of Duplicity", 33, Rarity.RARE, mage.cards.v.VisionsOfDuplicity.class));
        cards.add(new SetCardInfo("Visions of Glory", 32, Rarity.RARE, mage.cards.v.VisionsOfGlory.class));
        cards.add(new SetCardInfo("Wild Beastmaster", 146, Rarity.RARE, mage.cards.w.WildBeastmaster.class));
        cards.add(new SetCardInfo("Wilhelt, the Rotcleaver", 2, Rarity.MYTHIC, mage.cards.w.WilheltTheRotcleaver.class));
        cards.add(new SetCardInfo("Yavimaya Elder", 147, Rarity.COMMON, mage.cards.y.YavimayaElder.class));
        cards.add(new SetCardInfo("Zombie Apocalypse", 131, Rarity.RARE, mage.cards.z.ZombieApocalypse.class));
    }
}
