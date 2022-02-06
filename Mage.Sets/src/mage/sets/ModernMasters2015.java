package mage.sets;

import mage.cards.ExpansionSet;
import mage.collation.BoosterCollator;
import mage.collation.BoosterStructure;
import mage.collation.CardRun;
import mage.collation.RarityConfiguration;
import mage.constants.Rarity;
import mage.constants.SetType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fireshoes
 */
public final class ModernMasters2015 extends ExpansionSet {

    private static final ModernMasters2015 instance = new ModernMasters2015();

    public static ModernMasters2015 getInstance() {
        return instance;
    }

    private ModernMasters2015() {
        super("Modern Masters 2015", "MM2", ExpansionSet.buildDate(2015, 5, 22), SetType.SUPPLEMENTAL_MODERN_LEGAL);
        this.blockName = "Reprint";
        this.hasBasicLands = false;
        this.hasBoosters = true;
        this.numBoosterLands = 0;
        this.numBoosterCommon = 11;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        cards.add(new SetCardInfo("Aethersnipe", 39, Rarity.COMMON, mage.cards.a.Aethersnipe.class));
        cards.add(new SetCardInfo("Agony Warp", 170, Rarity.UNCOMMON, mage.cards.a.AgonyWarp.class));
        cards.add(new SetCardInfo("Air Servant", 40, Rarity.UNCOMMON, mage.cards.a.AirServant.class));
        cards.add(new SetCardInfo("Algae Gharial", 137, Rarity.UNCOMMON, mage.cards.a.AlgaeGharial.class));
        cards.add(new SetCardInfo("All Is Dust", 1, Rarity.RARE, mage.cards.a.AllIsDust.class));
        cards.add(new SetCardInfo("Alloy Myr", 201, Rarity.COMMON, mage.cards.a.AlloyMyr.class));
        cards.add(new SetCardInfo("All Suns' Dawn", 138, Rarity.RARE, mage.cards.a.AllSunsDawn.class));
        cards.add(new SetCardInfo("Ant Queen", 139, Rarity.RARE, mage.cards.a.AntQueen.class));
        cards.add(new SetCardInfo("Apocalypse Hydra", 171, Rarity.RARE, mage.cards.a.ApocalypseHydra.class));
        cards.add(new SetCardInfo("Apostle's Blessing", 8, Rarity.COMMON, mage.cards.a.ApostlesBlessing.class));
        cards.add(new SetCardInfo("Aquastrand Spider", 140, Rarity.COMMON, mage.cards.a.AquastrandSpider.class));
        cards.add(new SetCardInfo("Argent Sphinx", 41, Rarity.RARE, mage.cards.a.ArgentSphinx.class));
        cards.add(new SetCardInfo("Arrest", 9, Rarity.COMMON, mage.cards.a.Arrest.class));
        cards.add(new SetCardInfo("Artisan of Kozilek", 2, Rarity.UNCOMMON, mage.cards.a.ArtisanOfKozilek.class));
        cards.add(new SetCardInfo("Ashenmoor Gouger", 190, Rarity.UNCOMMON, mage.cards.a.AshenmoorGouger.class));
        cards.add(new SetCardInfo("Azorius Chancery", 235, Rarity.UNCOMMON, mage.cards.a.AzoriusChancery.class));
        cards.add(new SetCardInfo("Banefire", 104, Rarity.RARE, mage.cards.b.Banefire.class));
        cards.add(new SetCardInfo("Battlegrace Angel", 10, Rarity.RARE, mage.cards.b.BattlegraceAngel.class));
        cards.add(new SetCardInfo("Bestial Menace", 141, Rarity.UNCOMMON, mage.cards.b.BestialMenace.class));
        cards.add(new SetCardInfo("Bitterblossom", 71, Rarity.MYTHIC, mage.cards.b.Bitterblossom.class));
        cards.add(new SetCardInfo("Blades of Velis Vel", 105, Rarity.COMMON, mage.cards.b.BladesOfVelisVel.class));
        cards.add(new SetCardInfo("Blinding Souleater", 202, Rarity.COMMON, mage.cards.b.BlindingSouleater.class));
        cards.add(new SetCardInfo("Blinkmoth Nexus", 236, Rarity.RARE, mage.cards.b.BlinkmothNexus.class));
        cards.add(new SetCardInfo("Blood Ogre", 106, Rarity.COMMON, mage.cards.b.BloodOgre.class));
        cards.add(new SetCardInfo("Bloodshot Trainee", 107, Rarity.UNCOMMON, mage.cards.b.BloodshotTrainee.class));
        cards.add(new SetCardInfo("Bloodthrone Vampire", 72, Rarity.COMMON, mage.cards.b.BloodthroneVampire.class));
        cards.add(new SetCardInfo("Bone Splinters", 73, Rarity.COMMON, mage.cards.b.BoneSplinters.class));
        cards.add(new SetCardInfo("Boros Garrison", 237, Rarity.UNCOMMON, mage.cards.b.BorosGarrison.class));
        cards.add(new SetCardInfo("Boros Swiftblade", 172, Rarity.UNCOMMON, mage.cards.b.BorosSwiftblade.class));
        cards.add(new SetCardInfo("Brute Force", 108, Rarity.COMMON, mage.cards.b.BruteForce.class));
        cards.add(new SetCardInfo("Burst Lightning", 109, Rarity.COMMON, mage.cards.b.BurstLightning.class));
        cards.add(new SetCardInfo("Cathodion", 203, Rarity.COMMON, mage.cards.c.Cathodion.class));
        cards.add(new SetCardInfo("Celestial Purge", 11, Rarity.UNCOMMON, mage.cards.c.CelestialPurge.class));
        cards.add(new SetCardInfo("Chimeric Mass", 204, Rarity.RARE, mage.cards.c.ChimericMass.class));
        cards.add(new SetCardInfo("Cloud Elemental", 42, Rarity.COMMON, mage.cards.c.CloudElemental.class));
        cards.add(new SetCardInfo("Combust", 110, Rarity.UNCOMMON, mage.cards.c.Combust.class));
        cards.add(new SetCardInfo("Comet Storm", 111, Rarity.MYTHIC, mage.cards.c.CometStorm.class));
        cards.add(new SetCardInfo("Commune with Nature", 142, Rarity.COMMON, mage.cards.c.CommuneWithNature.class));
        cards.add(new SetCardInfo("Conclave Phalanx", 12, Rarity.COMMON, mage.cards.c.ConclavePhalanx.class));
        cards.add(new SetCardInfo("Copper Carapace", 205, Rarity.COMMON, mage.cards.c.CopperCarapace.class));
        cards.add(new SetCardInfo("Court Homunculus", 13, Rarity.COMMON, mage.cards.c.CourtHomunculus.class));
        cards.add(new SetCardInfo("Cranial Plating", 206, Rarity.UNCOMMON, mage.cards.c.CranialPlating.class));
        cards.add(new SetCardInfo("Creakwood Liege", 191, Rarity.RARE, mage.cards.c.CreakwoodLiege.class));
        cards.add(new SetCardInfo("Cryptic Command", 43, Rarity.RARE, mage.cards.c.CrypticCommand.class));
        cards.add(new SetCardInfo("Culling Dais", 207, Rarity.UNCOMMON, mage.cards.c.CullingDais.class));
        cards.add(new SetCardInfo("Cytoplast Root-Kin", 143, Rarity.UNCOMMON, mage.cards.c.CytoplastRootKin.class));
        cards.add(new SetCardInfo("Daggerclaw Imp", 74, Rarity.UNCOMMON, mage.cards.d.DaggerclawImp.class));
        cards.add(new SetCardInfo("Dark Confidant", 75, Rarity.MYTHIC, mage.cards.d.DarkConfidant.class));
        cards.add(new SetCardInfo("Darksteel Axe", 208, Rarity.UNCOMMON, mage.cards.d.DarksteelAxe.class));
        cards.add(new SetCardInfo("Darksteel Citadel", 238, Rarity.COMMON, mage.cards.d.DarksteelCitadel.class));
        cards.add(new SetCardInfo("Daybreak Coronet", 14, Rarity.RARE, mage.cards.d.DaybreakCoronet.class));
        cards.add(new SetCardInfo("Death Denied", 76, Rarity.COMMON, mage.cards.d.DeathDenied.class));
        cards.add(new SetCardInfo("Deathmark", 77, Rarity.UNCOMMON, mage.cards.d.Deathmark.class));
        cards.add(new SetCardInfo("Devouring Greed", 78, Rarity.UNCOMMON, mage.cards.d.DevouringGreed.class));
        cards.add(new SetCardInfo("Dimir Aqueduct", 239, Rarity.UNCOMMON, mage.cards.d.DimirAqueduct.class));
        cards.add(new SetCardInfo("Dimir Guildmage", 192, Rarity.UNCOMMON, mage.cards.d.DimirGuildmage.class));
        cards.add(new SetCardInfo("Dismember", 79, Rarity.UNCOMMON, mage.cards.d.Dismember.class));
        cards.add(new SetCardInfo("Dispatch", 15, Rarity.UNCOMMON, mage.cards.d.Dispatch.class));
        cards.add(new SetCardInfo("Dragonsoul Knight", 112, Rarity.COMMON, mage.cards.d.DragonsoulKnight.class));
        cards.add(new SetCardInfo("Dread Drone", 80, Rarity.COMMON, mage.cards.d.DreadDrone.class));
        cards.add(new SetCardInfo("Drooling Groodion", 173, Rarity.UNCOMMON, mage.cards.d.DroolingGroodion.class));
        cards.add(new SetCardInfo("Duskhunter Bat", 81, Rarity.COMMON, mage.cards.d.DuskhunterBat.class));
        cards.add(new SetCardInfo("Eldrazi Temple", 240, Rarity.UNCOMMON, mage.cards.e.EldraziTemple.class));
        cards.add(new SetCardInfo("Electrolyze", 174, Rarity.UNCOMMON, mage.cards.e.Electrolyze.class));
        cards.add(new SetCardInfo("Elesh Norn, Grand Cenobite", 16, Rarity.MYTHIC, mage.cards.e.EleshNornGrandCenobite.class));
        cards.add(new SetCardInfo("Emrakul, the Aeons Torn", 3, Rarity.MYTHIC, mage.cards.e.EmrakulTheAeonsTorn.class));
        cards.add(new SetCardInfo("Endrek Sahr, Master Breeder", 82, Rarity.RARE, mage.cards.e.EndrekSahrMasterBreeder.class));
        cards.add(new SetCardInfo("Etched Champion", 209, Rarity.RARE, mage.cards.e.EtchedChampion.class));
        cards.add(new SetCardInfo("Etched Monstrosity", 210, Rarity.RARE, mage.cards.e.EtchedMonstrosity.class));
        cards.add(new SetCardInfo("Etched Oracle", 211, Rarity.UNCOMMON, mage.cards.e.EtchedOracle.class));
        cards.add(new SetCardInfo("Ethercaste Knight", 175, Rarity.UNCOMMON, mage.cards.e.EthercasteKnight.class));
        cards.add(new SetCardInfo("Everflowing Chalice", 212, Rarity.UNCOMMON, mage.cards.e.EverflowingChalice.class));
        cards.add(new SetCardInfo("Evolving Wilds", 241, Rarity.COMMON, mage.cards.e.EvolvingWilds.class));
        cards.add(new SetCardInfo("Expedition Map", 213, Rarity.UNCOMMON, mage.cards.e.ExpeditionMap.class));
        cards.add(new SetCardInfo("Eye of Ugin", 242, Rarity.RARE, mage.cards.e.EyeOfUgin.class));
        cards.add(new SetCardInfo("Faerie Mechanist", 44, Rarity.COMMON, mage.cards.f.FaerieMechanist.class));
        cards.add(new SetCardInfo("Fiery Fall", 113, Rarity.COMMON, mage.cards.f.FieryFall.class));
        cards.add(new SetCardInfo("Flashfreeze", 45, Rarity.UNCOMMON, mage.cards.f.Flashfreeze.class));
        cards.add(new SetCardInfo("Flayer Husk", 214, Rarity.COMMON, mage.cards.f.FlayerHusk.class));
        cards.add(new SetCardInfo("Fortify", 17, Rarity.COMMON, mage.cards.f.Fortify.class));
        cards.add(new SetCardInfo("Frogmite", 215, Rarity.COMMON, mage.cards.f.Frogmite.class));
        cards.add(new SetCardInfo("Fulminator Mage", 193, Rarity.RARE, mage.cards.f.FulminatorMage.class));
        cards.add(new SetCardInfo("Ghost Council of Orzhova", 176, Rarity.RARE, mage.cards.g.GhostCouncilOfOrzhova.class));
        cards.add(new SetCardInfo("Ghostly Changeling", 83, Rarity.COMMON, mage.cards.g.GhostlyChangeling.class));
        cards.add(new SetCardInfo("Glassdust Hulk", 177, Rarity.UNCOMMON, mage.cards.g.GlassdustHulk.class));
        cards.add(new SetCardInfo("Glint Hawk Idol", 216, Rarity.COMMON, mage.cards.g.GlintHawkIdol.class));
        cards.add(new SetCardInfo("Gnarlid Pack", 144, Rarity.COMMON, mage.cards.g.GnarlidPack.class));
        cards.add(new SetCardInfo("Goblin Fireslinger", 114, Rarity.COMMON, mage.cards.g.GoblinFireslinger.class));
        cards.add(new SetCardInfo("Goblin War Paint", 115, Rarity.COMMON, mage.cards.g.GoblinWarPaint.class));
        cards.add(new SetCardInfo("Golgari Rot Farm", 243, Rarity.UNCOMMON, mage.cards.g.GolgariRotFarm.class));
        cards.add(new SetCardInfo("Gorehorn Minotaurs", 116, Rarity.COMMON, mage.cards.g.GorehornMinotaurs.class));
        cards.add(new SetCardInfo("Grim Affliction", 84, Rarity.COMMON, mage.cards.g.GrimAffliction.class));
        cards.add(new SetCardInfo("Gruul Turf", 244, Rarity.UNCOMMON, mage.cards.g.GruulTurf.class));
        cards.add(new SetCardInfo("Guile", 46, Rarity.RARE, mage.cards.g.Guile.class));
        cards.add(new SetCardInfo("Gust-Skimmer", 217, Rarity.COMMON, mage.cards.g.GustSkimmer.class));
        cards.add(new SetCardInfo("Gut Shot", 117, Rarity.COMMON, mage.cards.g.GutShot.class));
        cards.add(new SetCardInfo("Hearthfire Hobgoblin", 194, Rarity.UNCOMMON, mage.cards.h.HearthfireHobgoblin.class));
        cards.add(new SetCardInfo("Helium Squirter", 47, Rarity.COMMON, mage.cards.h.HeliumSquirter.class));
        cards.add(new SetCardInfo("Hellkite Charger", 118, Rarity.RARE, mage.cards.h.HellkiteCharger.class));
        cards.add(new SetCardInfo("Hikari, Twilight Guardian", 18, Rarity.UNCOMMON, mage.cards.h.HikariTwilightGuardian.class));
        cards.add(new SetCardInfo("Horde of Notions", 178, Rarity.RARE, mage.cards.h.HordeOfNotions.class));
        cards.add(new SetCardInfo("Hurkyl's Recall", 48, Rarity.RARE, mage.cards.h.HurkylsRecall.class));
        cards.add(new SetCardInfo("Incandescent Soulstoke", 119, Rarity.UNCOMMON, mage.cards.i.IncandescentSoulstoke.class));
        cards.add(new SetCardInfo("Indomitable Archangel", 19, Rarity.RARE, mage.cards.i.IndomitableArchangel.class));
        cards.add(new SetCardInfo("Inexorable Tide", 49, Rarity.RARE, mage.cards.i.InexorableTide.class));
        cards.add(new SetCardInfo("Inner-Flame Igniter", 120, Rarity.COMMON, mage.cards.i.InnerFlameIgniter.class));
        cards.add(new SetCardInfo("Instill Infection", 85, Rarity.COMMON, mage.cards.i.InstillInfection.class));
        cards.add(new SetCardInfo("Iona, Shield of Emeria", 20, Rarity.MYTHIC, mage.cards.i.IonaShieldOfEmeria.class));
        cards.add(new SetCardInfo("Izzet Boilerworks", 245, Rarity.UNCOMMON, mage.cards.i.IzzetBoilerworks.class));
        cards.add(new SetCardInfo("Kami of Ancient Law", 21, Rarity.COMMON, mage.cards.k.KamiOfAncientLaw.class));
        cards.add(new SetCardInfo("Karn Liberated", 4, Rarity.MYTHIC, mage.cards.k.KarnLiberated.class));
        cards.add(new SetCardInfo("Karplusan Strider", 145, Rarity.UNCOMMON, mage.cards.k.KarplusanStrider.class));
        cards.add(new SetCardInfo("Kavu Primarch", 146, Rarity.COMMON, mage.cards.k.KavuPrimarch.class));
        cards.add(new SetCardInfo("Kiki-Jiki, Mirror Breaker", 121, Rarity.MYTHIC, mage.cards.k.KikiJikiMirrorBreaker.class));
        cards.add(new SetCardInfo("Kitesail", 218, Rarity.COMMON, mage.cards.k.Kitesail.class));
        cards.add(new SetCardInfo("Kor Duelist", 22, Rarity.UNCOMMON, mage.cards.k.KorDuelist.class));
        cards.add(new SetCardInfo("Kozilek, Butcher of Truth", 5, Rarity.MYTHIC, mage.cards.k.KozilekButcherOfTruth.class));
        cards.add(new SetCardInfo("Kozilek's Predator", 147, Rarity.COMMON, mage.cards.k.KozileksPredator.class));
        cards.add(new SetCardInfo("Leyline of Sanctity", 23, Rarity.RARE, mage.cards.l.LeylineOfSanctity.class));
        cards.add(new SetCardInfo("Lightning Bolt", 122, Rarity.UNCOMMON, mage.cards.l.LightningBolt.class));
        cards.add(new SetCardInfo("Lodestone Golem", 219, Rarity.RARE, mage.cards.l.LodestoneGolem.class));
        cards.add(new SetCardInfo("Lodestone Myr", 220, Rarity.RARE, mage.cards.l.LodestoneMyr.class));
        cards.add(new SetCardInfo("Long-Forgotten Gohei", 221, Rarity.RARE, mage.cards.l.LongForgottenGohei.class));
        cards.add(new SetCardInfo("Lorescale Coatl", 179, Rarity.UNCOMMON, mage.cards.l.LorescaleCoatl.class));
        cards.add(new SetCardInfo("Mana Leak", 50, Rarity.COMMON, mage.cards.m.ManaLeak.class));
        cards.add(new SetCardInfo("Matca Rioters", 148, Rarity.COMMON, mage.cards.m.MatcaRioters.class));
        cards.add(new SetCardInfo("Midnight Banshee", 86, Rarity.RARE, mage.cards.m.MidnightBanshee.class));
        cards.add(new SetCardInfo("Mighty Leap", 24, Rarity.COMMON, mage.cards.m.MightyLeap.class));
        cards.add(new SetCardInfo("Mirran Crusader", 25, Rarity.RARE, mage.cards.m.MirranCrusader.class));
        cards.add(new SetCardInfo("Mirror Entity", 26, Rarity.RARE, mage.cards.m.MirrorEntity.class));
        cards.add(new SetCardInfo("Moonlit Strider", 27, Rarity.COMMON, mage.cards.m.MoonlitStrider.class));
        cards.add(new SetCardInfo("Mortarpod", 222, Rarity.UNCOMMON, mage.cards.m.Mortarpod.class));
        cards.add(new SetCardInfo("Mox Opal", 223, Rarity.MYTHIC, mage.cards.m.MoxOpal.class));
        cards.add(new SetCardInfo("Mulldrifter", 51, Rarity.UNCOMMON, mage.cards.m.Mulldrifter.class));
        cards.add(new SetCardInfo("Mutagenic Growth", 149, Rarity.UNCOMMON, mage.cards.m.MutagenicGrowth.class));
        cards.add(new SetCardInfo("Myr Enforcer", 224, Rarity.COMMON, mage.cards.m.MyrEnforcer.class));
        cards.add(new SetCardInfo("Myrsmith", 28, Rarity.UNCOMMON, mage.cards.m.Myrsmith.class));
        cards.add(new SetCardInfo("Mystic Snake", 180, Rarity.RARE, mage.cards.m.MysticSnake.class));
        cards.add(new SetCardInfo("Nameless Inversion", 87, Rarity.COMMON, mage.cards.n.NamelessInversion.class));
        cards.add(new SetCardInfo("Narcolepsy", 52, Rarity.COMMON, mage.cards.n.Narcolepsy.class));
        cards.add(new SetCardInfo("Necrogenesis", 181, Rarity.UNCOMMON, mage.cards.n.Necrogenesis.class));
        cards.add(new SetCardInfo("Necroskitter", 88, Rarity.RARE, mage.cards.n.Necroskitter.class));
        cards.add(new SetCardInfo("Nest Invader", 150, Rarity.COMMON, mage.cards.n.NestInvader.class));
        cards.add(new SetCardInfo("Niv-Mizzet, the Firemind", 182, Rarity.RARE, mage.cards.n.NivMizzetTheFiremind.class));
        cards.add(new SetCardInfo("Nobilis of War", 195, Rarity.RARE, mage.cards.n.NobilisOfWar.class));
        cards.add(new SetCardInfo("Noble Hierarch", 151, Rarity.RARE, mage.cards.n.NobleHierarch.class));
        cards.add(new SetCardInfo("Novijen Sages", 53, Rarity.UNCOMMON, mage.cards.n.NovijenSages.class));
        cards.add(new SetCardInfo("Oblivion Ring", 29, Rarity.UNCOMMON, mage.cards.o.OblivionRing.class));
        cards.add(new SetCardInfo("Orzhov Basilica", 246, Rarity.UNCOMMON, mage.cards.o.OrzhovBasilica.class));
        cards.add(new SetCardInfo("Otherworldly Journey", 30, Rarity.COMMON, mage.cards.o.OtherworldlyJourney.class));
        cards.add(new SetCardInfo("Overwhelm", 152, Rarity.UNCOMMON, mage.cards.o.Overwhelm.class));
        cards.add(new SetCardInfo("Overwhelming Stampede", 153, Rarity.RARE, mage.cards.o.OverwhelmingStampede.class));
        cards.add(new SetCardInfo("Pelakka Wurm", 154, Rarity.UNCOMMON, mage.cards.p.PelakkaWurm.class));
        cards.add(new SetCardInfo("Pillory of the Sleepless", 183, Rarity.UNCOMMON, mage.cards.p.PilloryOfTheSleepless.class));
        cards.add(new SetCardInfo("Plagued Rusalka", 89, Rarity.COMMON, mage.cards.p.PlaguedRusalka.class));
        cards.add(new SetCardInfo("Plaxcaster Frogling", 184, Rarity.UNCOMMON, mage.cards.p.PlaxcasterFrogling.class));
        cards.add(new SetCardInfo("Plummet", 155, Rarity.COMMON, mage.cards.p.Plummet.class));
        cards.add(new SetCardInfo("Precursor Golem", 225, Rarity.RARE, mage.cards.p.PrecursorGolem.class));
        cards.add(new SetCardInfo("Primeval Titan", 156, Rarity.MYTHIC, mage.cards.p.PrimevalTitan.class));
        cards.add(new SetCardInfo("Profane Command", 90, Rarity.RARE, mage.cards.p.ProfaneCommand.class));
        cards.add(new SetCardInfo("Puppeteer Clique", 91, Rarity.RARE, mage.cards.p.PuppeteerClique.class));
        cards.add(new SetCardInfo("Qumulox", 54, Rarity.UNCOMMON, mage.cards.q.Qumulox.class));
        cards.add(new SetCardInfo("Raise the Alarm", 31, Rarity.COMMON, mage.cards.r.RaiseTheAlarm.class));
        cards.add(new SetCardInfo("Rakdos Carnarium", 247, Rarity.UNCOMMON, mage.cards.r.RakdosCarnarium.class));
        cards.add(new SetCardInfo("Rampant Growth", 157, Rarity.COMMON, mage.cards.r.RampantGrowth.class));
        cards.add(new SetCardInfo("Reassembling Skeleton", 92, Rarity.UNCOMMON, mage.cards.r.ReassemblingSkeleton.class));
        cards.add(new SetCardInfo("Remand", 55, Rarity.UNCOMMON, mage.cards.r.Remand.class));
        cards.add(new SetCardInfo("Repeal", 56, Rarity.COMMON, mage.cards.r.Repeal.class));
        cards.add(new SetCardInfo("Restless Apparition", 196, Rarity.UNCOMMON, mage.cards.r.RestlessApparition.class));
        cards.add(new SetCardInfo("Root-Kin Ally", 158, Rarity.UNCOMMON, mage.cards.r.RootKinAlly.class));
        cards.add(new SetCardInfo("Runed Servitor", 226, Rarity.COMMON, mage.cards.r.RunedServitor.class));
        cards.add(new SetCardInfo("Rusted Relic", 227, Rarity.COMMON, mage.cards.r.RustedRelic.class));
        cards.add(new SetCardInfo("Savage Twister", 185, Rarity.UNCOMMON, mage.cards.s.SavageTwister.class));
        cards.add(new SetCardInfo("Scatter the Seeds", 159, Rarity.COMMON, mage.cards.s.ScatterTheSeeds.class));
        cards.add(new SetCardInfo("Scavenger Drake", 93, Rarity.UNCOMMON, mage.cards.s.ScavengerDrake.class));
        cards.add(new SetCardInfo("Scion of the Wild", 160, Rarity.COMMON, mage.cards.s.ScionOfTheWild.class));
        cards.add(new SetCardInfo("Scute Mob", 161, Rarity.RARE, mage.cards.s.ScuteMob.class));
        cards.add(new SetCardInfo("Scuttling Death", 94, Rarity.COMMON, mage.cards.s.ScuttlingDeath.class));
        cards.add(new SetCardInfo("Selesnya Guildmage", 197, Rarity.UNCOMMON, mage.cards.s.SelesnyaGuildmage.class));
        cards.add(new SetCardInfo("Selesnya Sanctuary", 248, Rarity.UNCOMMON, mage.cards.s.SelesnyaSanctuary.class));
        cards.add(new SetCardInfo("Shadowmage Infiltrator", 186, Rarity.RARE, mage.cards.s.ShadowmageInfiltrator.class));
        cards.add(new SetCardInfo("Shrewd Hatchling", 198, Rarity.UNCOMMON, mage.cards.s.ShrewdHatchling.class));
        cards.add(new SetCardInfo("Shrivel", 95, Rarity.COMMON, mage.cards.s.Shrivel.class));
        cards.add(new SetCardInfo("Sickle Ripper", 96, Rarity.COMMON, mage.cards.s.SickleRipper.class));
        cards.add(new SetCardInfo("Sickleslicer", 228, Rarity.COMMON, mage.cards.s.Sickleslicer.class));
        cards.add(new SetCardInfo("Sigil Blessing", 187, Rarity.UNCOMMON, mage.cards.s.SigilBlessing.class));
        cards.add(new SetCardInfo("Sign in Blood", 97, Rarity.COMMON, mage.cards.s.SignInBlood.class));
        cards.add(new SetCardInfo("Simic Growth Chamber", 249, Rarity.UNCOMMON, mage.cards.s.SimicGrowthChamber.class));
        cards.add(new SetCardInfo("Simic Initiate", 162, Rarity.COMMON, mage.cards.s.SimicInitiate.class));
        cards.add(new SetCardInfo("Skarrgan Firebird", 123, Rarity.UNCOMMON, mage.cards.s.SkarrganFirebird.class));
        cards.add(new SetCardInfo("Skyhunter Skirmisher", 32, Rarity.COMMON, mage.cards.s.SkyhunterSkirmisher.class));
        cards.add(new SetCardInfo("Skyreach Manta", 229, Rarity.COMMON, mage.cards.s.SkyreachManta.class));
        cards.add(new SetCardInfo("Smash to Smithereens", 124, Rarity.COMMON, mage.cards.s.SmashToSmithereens.class));
        cards.add(new SetCardInfo("Smokebraider", 125, Rarity.COMMON, mage.cards.s.Smokebraider.class));
        cards.add(new SetCardInfo("Somber Hoverguard", 57, Rarity.COMMON, mage.cards.s.SomberHoverguard.class));
        cards.add(new SetCardInfo("Soulbright Flamekin", 126, Rarity.COMMON, mage.cards.s.SoulbrightFlamekin.class));
        cards.add(new SetCardInfo("Spectral Procession", 33, Rarity.UNCOMMON, mage.cards.s.SpectralProcession.class));
        cards.add(new SetCardInfo("Spellskite", 230, Rarity.RARE, mage.cards.s.Spellskite.class));
        cards.add(new SetCardInfo("Sphere of the Suns", 231, Rarity.COMMON, mage.cards.s.SphereOfTheSuns.class));
        cards.add(new SetCardInfo("Spikeshot Elder", 127, Rarity.RARE, mage.cards.s.SpikeshotElder.class));
        cards.add(new SetCardInfo("Spitebellows", 128, Rarity.UNCOMMON, mage.cards.s.Spitebellows.class));
        cards.add(new SetCardInfo("Splinter Twin", 129, Rarity.RARE, mage.cards.s.SplinterTwin.class));
        cards.add(new SetCardInfo("Spread the Sickness", 98, Rarity.UNCOMMON, mage.cards.s.SpreadTheSickness.class));
        cards.add(new SetCardInfo("Steady Progress", 58, Rarity.COMMON, mage.cards.s.SteadyProgress.class));
        cards.add(new SetCardInfo("Stoic Rebuttal", 59, Rarity.COMMON, mage.cards.s.StoicRebuttal.class));
        cards.add(new SetCardInfo("Stormblood Berserker", 130, Rarity.UNCOMMON, mage.cards.s.StormbloodBerserker.class));
        cards.add(new SetCardInfo("Sundering Vitae", 163, Rarity.COMMON, mage.cards.s.SunderingVitae.class));
        cards.add(new SetCardInfo("Sunforger", 232, Rarity.RARE, mage.cards.s.Sunforger.class));
        cards.add(new SetCardInfo("Sunlance", 34, Rarity.COMMON, mage.cards.s.Sunlance.class));
        cards.add(new SetCardInfo("Sunspear Shikari", 35, Rarity.COMMON, mage.cards.s.SunspearShikari.class));
        cards.add(new SetCardInfo("Surgical Extraction", 99, Rarity.RARE, mage.cards.s.SurgicalExtraction.class));
        cards.add(new SetCardInfo("Surrakar Spellblade", 60, Rarity.RARE, mage.cards.s.SurrakarSpellblade.class));
        cards.add(new SetCardInfo("Swans of Bryn Argoll", 199, Rarity.RARE, mage.cards.s.SwansOfBrynArgoll.class));
        cards.add(new SetCardInfo("Sylvan Bounty", 164, Rarity.COMMON, mage.cards.s.SylvanBounty.class));
        cards.add(new SetCardInfo("Taj-Nar Swordsmith", 36, Rarity.UNCOMMON, mage.cards.t.TajNarSwordsmith.class));
        cards.add(new SetCardInfo("Tarmogoyf", 165, Rarity.MYTHIC, mage.cards.t.Tarmogoyf.class));
        cards.add(new SetCardInfo("Telling Time", 61, Rarity.COMMON, mage.cards.t.TellingTime.class));
        cards.add(new SetCardInfo("Terashi's Grasp", 37, Rarity.COMMON, mage.cards.t.TerashisGrasp.class));
        cards.add(new SetCardInfo("Tezzeret's Gambit", 63, Rarity.UNCOMMON, mage.cards.t.TezzeretsGambit.class));
        cards.add(new SetCardInfo("Tezzeret the Seeker", 62, Rarity.MYTHIC, mage.cards.t.TezzeretTheSeeker.class));
        cards.add(new SetCardInfo("Thief of Hope", 100, Rarity.COMMON, mage.cards.t.ThiefOfHope.class));
        cards.add(new SetCardInfo("Thoughtcast", 64, Rarity.COMMON, mage.cards.t.Thoughtcast.class));
        cards.add(new SetCardInfo("Thrive", 166, Rarity.COMMON, mage.cards.t.Thrive.class));
        cards.add(new SetCardInfo("Thrummingbird", 65, Rarity.COMMON, mage.cards.t.Thrummingbird.class));
        cards.add(new SetCardInfo("Thunderblust", 131, Rarity.RARE, mage.cards.t.Thunderblust.class));
        cards.add(new SetCardInfo("Tribal Flames", 132, Rarity.COMMON, mage.cards.t.TribalFlames.class));
        cards.add(new SetCardInfo("Tukatongue Thallid", 167, Rarity.COMMON, mage.cards.t.TukatongueThallid.class));
        cards.add(new SetCardInfo("Tumble Magnet", 233, Rarity.UNCOMMON, mage.cards.t.TumbleMagnet.class));
        cards.add(new SetCardInfo("Ulamog's Crusher", 7, Rarity.COMMON, mage.cards.u.UlamogsCrusher.class));
        cards.add(new SetCardInfo("Ulamog, the Infinite Gyre", 6, Rarity.MYTHIC, mage.cards.u.UlamogTheInfiniteGyre.class));
        cards.add(new SetCardInfo("Vampire Lacerator", 101, Rarity.COMMON, mage.cards.v.VampireLacerator.class));
        cards.add(new SetCardInfo("Vampire Outcasts", 102, Rarity.UNCOMMON, mage.cards.v.VampireOutcasts.class));
        cards.add(new SetCardInfo("Vapor Snag", 66, Rarity.COMMON, mage.cards.v.VaporSnag.class));
        cards.add(new SetCardInfo("Vendilion Clique", 67, Rarity.MYTHIC, mage.cards.v.VendilionClique.class));
        cards.add(new SetCardInfo("Vengeful Rebirth", 188, Rarity.UNCOMMON, mage.cards.v.VengefulRebirth.class));
        cards.add(new SetCardInfo("Viashino Slaughtermaster", 133, Rarity.COMMON, mage.cards.v.ViashinoSlaughtermaster.class));
        cards.add(new SetCardInfo("Vigean Graftmage", 68, Rarity.COMMON, mage.cards.v.VigeanGraftmage.class));
        cards.add(new SetCardInfo("Vines of Vastwood", 168, Rarity.COMMON, mage.cards.v.VinesOfVastwood.class));
        cards.add(new SetCardInfo("Waking Nightmare", 103, Rarity.COMMON, mage.cards.w.WakingNightmare.class));
        cards.add(new SetCardInfo("Water Servant", 69, Rarity.UNCOMMON, mage.cards.w.WaterServant.class));
        cards.add(new SetCardInfo("Waxmane Baku", 38, Rarity.COMMON, mage.cards.w.WaxmaneBaku.class));
        cards.add(new SetCardInfo("Wayfarer's Bauble", 234, Rarity.COMMON, mage.cards.w.WayfarersBauble.class));
        cards.add(new SetCardInfo("Wildfire", 134, Rarity.RARE, mage.cards.w.Wildfire.class));
        cards.add(new SetCardInfo("Wilt-Leaf Liege", 200, Rarity.RARE, mage.cards.w.WiltLeafLiege.class));
        cards.add(new SetCardInfo("Wings of Velis Vel", 70, Rarity.COMMON, mage.cards.w.WingsOfVelisVel.class));
        cards.add(new SetCardInfo("Wolfbriar Elemental", 169, Rarity.RARE, mage.cards.w.WolfbriarElemental.class));
        cards.add(new SetCardInfo("Worldheart Phoenix", 135, Rarity.UNCOMMON, mage.cards.w.WorldheartPhoenix.class));
        cards.add(new SetCardInfo("Wrap in Flames", 136, Rarity.COMMON, mage.cards.w.WrapInFlames.class));
        cards.add(new SetCardInfo("Wrecking Ball", 189, Rarity.UNCOMMON, mage.cards.w.WreckingBall.class));
    }

    @Override
    public BoosterCollator createCollator() {
        return new ModernMasters2015Collator();
    }
}

// Booster collation info from https://www.lethe.xyz/mtg/collation/mm2.html
// Using USA collation for all rarities
// Foil rare sheets used for regular rares as regular rare sheet is not known
class ModernMasters2015Collator implements BoosterCollator {
    private final CardRun commonA = new CardRun(true, "109", "150", "205", "50", "159", "132", "34", "160", "57", "229", "125", "148", "202", "9", "150", "109", "50", "216", "116", "147", "65", "106", "87", "157", "205", "132", "100", "159", "65", "87", "106", "147", "202", "9", "148", "109", "229", "150", "216", "132", "160", "34", "100", "125", "50", "205", "159", "116", "57", "157", "229", "125", "100", "160", "9", "132", "147", "205", "159", "65", "109", "202", "57", "34", "148", "106", "50", "87", "150", "116", "216", "157", "50", "9", "147", "87", "205", "109", "148", "202", "106", "150", "57", "229", "157", "116", "34", "100", "159", "65", "125", "216", "160", "132", "87", "34", "157", "106", "229", "160", "9", "57", "116", "202", "65", "147", "100", "125", "148", "216");
    private final CardRun commonB = new CardRun(true, "94", "44", "8", "85", "66", "21", "73", "58", "13", "81", "39", "32", "72", "61", "27", "101", "42", "31", "95", "52", "24", "84", "56", "12", "103", "70", "38", "89", "64", "17", "76", "68", "37", "96", "59", "30", "83", "47", "35", "72", "39", "31", "95", "56", "8", "103", "68", "17", "76", "70", "13", "96", "42", "27", "85", "66", "37", "94", "59", "21", "89", "52", "30", "73", "61", "38", "84", "47", "12", "81", "44", "24", "83", "64", "35", "101", "58", "32", "83", "68", "30", "84", "61", "35", "85", "59", "21", "96", "70", "24", "81", "64", "38", "101", "39", "31", "94", "56", "32", "95", "47", "13", "103", "44", "8", "76", "58", "12", "89", "52", "17", "72", "66", "27", "73", "42", "37");
    // Note that the two copies of Smash to Smithereens (124) in the last row are close enough to appear in the same pack
    private final CardRun commonC = new CardRun(true, "164", "113", "140", "115", "146", "105", "167", "112", "142", "136", "162", "126", "155", "108", "166", "114", "144", "133", "163", "120", "168", "105", "167", "136", "146", "126", "140", "112", "163", "133", "168", "115", "144", "114", "166", "120", "162", "113", "164", "124", "142", "108", "155", "113", "146", "133", "166", "108", "155", "126", "142", "105", "164", "124", "167", "136", "162", "114", "140", "120", "163", "112", "168", "115", "144", "114", "140", "126", "164", "124", "167", "115", "144", "108", "162", "120", "155", "133", "168", "113", "142", "105", "166", "136", "146", "112", "163", "115", "166", "105", "142", "120", "163", "136", "168", "112", "146", "126", "140", "113", "164", "133", "155", "108", "144", "114", "167", "124", "162", "124");
    private final CardRun commonD = new CardRun(true, "227", "218", "241", "215", "80", "231", "214", "201", "117", "224", "218", "203", "238", "228", "7", "226", "227", "234", "97", "217", "224", "241", "234", "226", "203", "97", "228", "238", "215", "7", "201", "80", "227", "217", "231", "117", "214", "218", "241", "231", "228", "214", "97", "234", "117", "215", "217", "201", "238", "224", "7", "227", "203", "226", "80", "218", "228", "80", "224", "117", "214", "238", "203", "97", "218", "234", "231", "7", "215", "227", "201", "241", "226", "217", "80", "228", "215", "241", "218", "234", "227", "203", "7", "201", "238", "226", "231", "217", "117", "97", "214", "224", "201", "7", "217", "228", "226", "241", "234", "80", "203", "117", "224", "215", "231", "238", "214", "97");
    private final CardRun uncommonA = new CardRun(true, "18", "74", "63", "123", "244", "158", "77", "22", "135", "54", "243", "78", "110", "152", "240", "45", "11", "92", "2", "239", "149", "69", "245", "93", "213", "36", "107", "145", "237", "55", "98", "15", "119", "143", "235", "130", "102", "53", "28", "137", "93", "245", "107", "15", "243", "92", "152", "239", "135", "53", "78", "18", "213", "137", "54", "74", "11", "2", "119", "145", "22", "235", "63", "123", "98", "244", "158", "45", "36", "237", "110", "102", "143", "240", "69", "28", "77", "130", "149", "55", "11", "92", "119", "36", "45", "244", "107", "18", "78", "54", "145", "240", "123", "74", "22", "158", "237", "63", "135", "93", "245", "15", "152", "2", "69", "235", "102", "137", "98", "53", "130", "213", "239", "149", "55", "28", "77", "143", "243", "110");
    private final CardRun uncommonB = new CardRun(true, "184", "154", "181", "122", "175", "248", "185", "211", "177", "40", "188", "212", "173", "206", "187", "33", "196", "79", "189", "249", "170", "207", "197", "233", "198", "141", "179", "128", "194", "51", "172", "246", "183", "222", "192", "208", "174", "29", "190", "247", "197", "212", "181", "33", "173", "208", "185", "248", "187", "141", "175", "233", "198", "122", "184", "211", "190", "79", "177", "154", "179", "246", "172", "29", "189", "207", "196", "40", "174", "249", "188", "128", "192", "206", "170", "51", "194", "247", "183", "222", "197", "247", "170", "212", "172", "154", "179", "122", "194", "207", "185", "211", "192", "51", "175", "248", "181", "33", "198", "206", "174", "222", "183", "249", "177", "141", "187", "208", "189", "128", "196", "79", "184", "246", "173", "40", "190", "233", "188", "29");
    private final CardRun rare = new CardRun(true, "220", "104", "186", "225", "86", "199", "14", "48", "210", "127", "180", "90", "25", "219", "139", "191", "49", "129", "242", "19", "178", "82", "41", "230", "138", "193", "1", "10", "209", "91", "176", "60", "134", "236", "99", "182", "26", "43", "232", "153", "200", "118", "161", "221", "169", "171", "131", "151", "204", "23", "195", "46", "88", "219", "139", "191", "48", "82", "225", "129", "186", "25", "60", "230", "90", "180", "127", "161", "242", "26", "193", "49", "99", "210", "104", "200", "151", "86", "204", "19", "171", "131", "1", "220", "43", "195", "23", "169", "232", "134", "182", "88", "46", "236", "10", "176", "138", "118", "221", "91", "199", "153", "14", "209", "41", "178");
    private final CardRun mythic = new CardRun(true, "156", "3", "71", "62", "111", "6", "16", "223", "165", "67", "4", "75", "20", "121", "5", "67", "71", "3", "16", "165", "4", "111", "75", "223", "5", "62", "156", "6", "20", "121", "67", "71", "5", "223", "111", "156", "4", "16", "62", "6", "165", "121", "3", "75", "20", "62", "5", "156", "223", "111", "20", "4", "71", "165", "3", "75", "16", "67", "6", "121", "20", "5", "71", "156", "3", "62", "121", "75", "6", "223", "16", "165", "4", "111", "67", "20", "5", "156", "71", "67", "3", "111", "223", "16", "4", "75", "165", "121", "6", "62", "71", "156", "4", "20", "67", "3", "121", "223", "5", "62", "75", "165", "6", "16", "111", "5", "71", "156", "20", "3", "121", "62", "6", "223", "165", "111", "75", "4", "67", "16");
    private final CardRun foilCommon = new CardRun(true, "140", "215", "30", "106", "103", "56", "226", "150", "136", "95", "234", "132", "61", "76", "228", "160", "214", "13", "113", "50", "155", "27", "241", "202", "12", "97", "42", "34", "124", "68", "84", "216", "167", "24", "94", "147", "112", "203", "59", "87", "37", "162", "117", "57", "83", "44", "133", "32", "66", "7", "164", "125", "73", "142", "227", "238", "229", "80", "108", "159", "8", "218", "89", "65", "21", "120", "101", "38", "144", "47", "81", "109", "35", "168", "224", "148", "96", "126", "70", "205", "114", "85", "39", "157", "231", "17", "217", "116", "58", "146", "31", "100", "201", "115", "163", "52", "105", "72", "166", "9", "64");

    private final BoosterStructure AABBBBCCDD = new BoosterStructure(
            commonA, commonA,
            commonB, commonB, commonB, commonB,
            commonC, commonC,
            commonD, commonD
    );
    private final BoosterStructure AABBBBCCCD = new BoosterStructure(
            commonA, commonA,
            commonB, commonB, commonB, commonB,
            commonC, commonC, commonC,
            commonD
    );
    private final BoosterStructure AAABBBCCDD = new BoosterStructure(
            commonA, commonA, commonA,
            commonB, commonB, commonB,
            commonC, commonC,
            commonD, commonD
    );
    private final BoosterStructure AAABBBBCCD = new BoosterStructure(
            commonA, commonA, commonA,
            commonB, commonB, commonB, commonB,
            commonC, commonC,
            commonD
    );
    private final BoosterStructure AAB = new BoosterStructure(uncommonA, uncommonA, uncommonB);
    private final BoosterStructure ABB = new BoosterStructure(uncommonA, uncommonB, uncommonB);
    private final BoosterStructure R1 = new BoosterStructure(rare);
    private final BoosterStructure M1 = new BoosterStructure(mythic);
    private final BoosterStructure FC = new BoosterStructure(foilCommon);
    private final BoosterStructure FUA = new BoosterStructure(uncommonA);
    private final BoosterStructure FUB = new BoosterStructure(uncommonB);

    // In order for equal numbers of each common to exist, the average booster must contain:
    // 2.18 A commons (220 / 101)
    // 3.86 B commons (390 / 101)
    // 2.18 C commons (220 / 101)
    // 1.78 D commons (180 / 101)
    private final RarityConfiguration commonRuns = new RarityConfiguration(
            AABBBBCCDD, AABBBBCCDD, AABBBBCCDD, AABBBBCCDD, AABBBBCCDD,
            AABBBBCCDD, AABBBBCCDD, AABBBBCCDD, AABBBBCCDD, AABBBBCCDD,
            AABBBBCCDD, AABBBBCCDD, AABBBBCCDD, AABBBBCCDD, AABBBBCCDD,
            AABBBBCCDD, AABBBBCCDD, AABBBBCCDD, AABBBBCCDD, AABBBBCCDD,
            AABBBBCCDD, AABBBBCCDD, AABBBBCCDD, AABBBBCCDD, AABBBBCCDD,
            AABBBBCCDD, AABBBBCCDD, AABBBBCCDD, AABBBBCCDD, AABBBBCCDD,
            AABBBBCCDD, AABBBBCCDD, AABBBBCCDD, AABBBBCCDD, AABBBBCCDD,
            AABBBBCCDD, AABBBBCCDD, AABBBBCCDD, AABBBBCCDD, AABBBBCCDD,
            AABBBBCCDD, AABBBBCCDD, AABBBBCCDD, AABBBBCCDD, AABBBBCCDD,
            AABBBBCCDD, AABBBBCCDD, AABBBBCCDD, AABBBBCCDD, AABBBBCCDD,
            AABBBBCCDD, AABBBBCCDD, AABBBBCCDD, AABBBBCCDD, AABBBBCCDD,
            AABBBBCCDD, AABBBBCCDD, AABBBBCCDD, AABBBBCCDD, AABBBBCCDD,
            AABBBBCCDD, AABBBBCCDD, AABBBBCCDD, AABBBBCCDD, AABBBBCCDD,

            AABBBBCCCD, AABBBBCCCD, AABBBBCCCD, AABBBBCCCD, AABBBBCCCD,
            AABBBBCCCD, AABBBBCCCD, AABBBBCCCD, AABBBBCCCD, AABBBBCCCD,
            AABBBBCCCD, AABBBBCCCD, AABBBBCCCD, AABBBBCCCD, AABBBBCCCD,
            AABBBBCCCD, AABBBBCCCD, AABBBBCCCD,

            AAABBBCCDD, AAABBBCCDD, AAABBBCCDD, AAABBBCCDD, AAABBBCCDD,
            AAABBBCCDD, AAABBBCCDD, AAABBBCCDD, AAABBBCCDD, AAABBBCCDD,
            AAABBBCCDD, AAABBBCCDD, AAABBBCCDD, AAABBBCCDD,

            AAABBBBCCD, AAABBBBCCD, AAABBBBCCD, AAABBBBCCD
    );
    private final RarityConfiguration uncommonRuns = new RarityConfiguration(AAB, ABB);
    private final RarityConfiguration rareRuns = new RarityConfiguration(R1, R1, R1, R1, R1, R1, R1, M1);
    private final RarityConfiguration foilRuns = new RarityConfiguration(
            FC, FC, FC, FC, FC, FC, FC, FC, FC, FC,
            FC, FC, FC, FC, FC, FC, FC, FC, FC, FC,
            FC, FC, FC, FC, FC, FC, FC, FC, FC, FC,
            FC, FC, FC, FC, FC, FC, FC, FC, FC, FC,
            FC, FC, FC, FC, FC, FC, FC, FC, FC, FC,
            FC, FC, FC, FC, FC, FC, FC, FC, FC, FC,
            FC, FC, FC, FC, FC, FC, FC, FC, FC, FC,
            FC, FC, FC, FC, FC, FC, FC, FC, FC, FC,
            FUA, FUA, FUA, FUA, FUA, FUA, FUA, FUA, FUA, FUA, FUA, FUA,
            FUB, FUB, FUB, FUB, FUB, FUB, FUB, FUB, FUB, FUB, FUB, FUB,
            R1, R1, R1, R1, R1, R1, R1, M1
    );

    @Override
    public List<String> makeBooster() {
        List<String> booster = new ArrayList<>();
        booster.addAll(commonRuns.getNext().makeRun());
        booster.addAll(uncommonRuns.getNext().makeRun());
        booster.addAll(rareRuns.getNext().makeRun());
        booster.addAll(foilRuns.getNext().makeRun());
        return booster;
    }
}
