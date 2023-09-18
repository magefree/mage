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
 *
 * @author magenoxx_at_gmail.com
 */
public final class ReturnToRavnica extends ExpansionSet {

    private static final ReturnToRavnica instance = new ReturnToRavnica();

    public static ReturnToRavnica getInstance() {
        return instance;
    }

    private ReturnToRavnica() {
        super("Return to Ravnica", "RTR", ExpansionSet.buildDate(2012, 10, 5), SetType.EXPANSION);
        this.blockName = "Return to Ravnica";
        this.hasBoosters = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        cards.add(new SetCardInfo("Abrupt Decay", 141, Rarity.RARE, mage.cards.a.AbruptDecay.class));
        cards.add(new SetCardInfo("Aerial Predation", 113, Rarity.COMMON, mage.cards.a.AerialPredation.class));
        cards.add(new SetCardInfo("Angel of Serenity", 1, Rarity.MYTHIC, mage.cards.a.AngelOfSerenity.class));
        cards.add(new SetCardInfo("Annihilating Fire", 85, Rarity.COMMON, mage.cards.a.AnnihilatingFire.class));
        cards.add(new SetCardInfo("Aquus Steed", 29, Rarity.UNCOMMON, mage.cards.a.AquusSteed.class));
        cards.add(new SetCardInfo("Archon of the Triumvirate", 142, Rarity.RARE, mage.cards.a.ArchonOfTheTriumvirate.class));
        cards.add(new SetCardInfo("Archweaver", 114, Rarity.UNCOMMON, mage.cards.a.Archweaver.class));
        cards.add(new SetCardInfo("Armada Wurm", 143, Rarity.MYTHIC, mage.cards.a.ArmadaWurm.class));
        cards.add(new SetCardInfo("Armory Guard", 2, Rarity.COMMON, mage.cards.a.ArmoryGuard.class));
        cards.add(new SetCardInfo("Arrest", 3, Rarity.UNCOMMON, mage.cards.a.Arrest.class));
        cards.add(new SetCardInfo("Ash Zealot", 86, Rarity.RARE, mage.cards.a.AshZealot.class));
        cards.add(new SetCardInfo("Assassin's Strike", 57, Rarity.UNCOMMON, mage.cards.a.AssassinsStrike.class));
        cards.add(new SetCardInfo("Auger Spree", 144, Rarity.COMMON, mage.cards.a.AugerSpree.class));
        cards.add(new SetCardInfo("Avenging Arrow", 4, Rarity.COMMON, mage.cards.a.AvengingArrow.class));
        cards.add(new SetCardInfo("Axebane Guardian", 115, Rarity.COMMON, mage.cards.a.AxebaneGuardian.class));
        cards.add(new SetCardInfo("Axebane Stag", 116, Rarity.COMMON, mage.cards.a.AxebaneStag.class));
        cards.add(new SetCardInfo("Azorius Arrester", 5, Rarity.COMMON, mage.cards.a.AzoriusArrester.class));
        cards.add(new SetCardInfo("Azorius Charm", 145, Rarity.UNCOMMON, mage.cards.a.AzoriusCharm.class));
        cards.add(new SetCardInfo("Azorius Guildgate", 237, Rarity.COMMON, mage.cards.a.AzoriusGuildgate.class));
        cards.add(new SetCardInfo("Azorius Justiciar", 6, Rarity.UNCOMMON, mage.cards.a.AzoriusJusticiar.class));
        cards.add(new SetCardInfo("Azorius Keyrune", 225, Rarity.UNCOMMON, mage.cards.a.AzoriusKeyrune.class));
        cards.add(new SetCardInfo("Azor's Elocutors", 210, Rarity.RARE, mage.cards.a.AzorsElocutors.class));
        cards.add(new SetCardInfo("Batterhorn", 87, Rarity.COMMON, mage.cards.b.Batterhorn.class));
        cards.add(new SetCardInfo("Bazaar Krovod", 7, Rarity.UNCOMMON, mage.cards.b.BazaarKrovod.class));
        cards.add(new SetCardInfo("Bellows Lizard", 88, Rarity.COMMON, mage.cards.b.BellowsLizard.class));
        cards.add(new SetCardInfo("Blistercoil Weird", 211, Rarity.UNCOMMON, mage.cards.b.BlistercoilWeird.class));
        cards.add(new SetCardInfo("Blood Crypt", 238, Rarity.RARE, mage.cards.b.BloodCrypt.class));
        cards.add(new SetCardInfo("Bloodfray Giant", 89, Rarity.UNCOMMON, mage.cards.b.BloodfrayGiant.class));
        cards.add(new SetCardInfo("Blustersquall", 30, Rarity.UNCOMMON, mage.cards.b.Blustersquall.class));
        cards.add(new SetCardInfo("Brushstrider", 117, Rarity.UNCOMMON, mage.cards.b.Brushstrider.class));
        cards.add(new SetCardInfo("Call of the Conclave", 146, Rarity.UNCOMMON, mage.cards.c.CallOfTheConclave.class));
        cards.add(new SetCardInfo("Cancel", 31, Rarity.COMMON, mage.cards.c.Cancel.class));
        cards.add(new SetCardInfo("Carnival Hellsteed", 147, Rarity.RARE, mage.cards.c.CarnivalHellsteed.class));
        cards.add(new SetCardInfo("Catacomb Slug", 58, Rarity.COMMON, mage.cards.c.CatacombSlug.class));
        cards.add(new SetCardInfo("Centaur Healer", 148, Rarity.COMMON, mage.cards.c.CentaurHealer.class));
        cards.add(new SetCardInfo("Centaur's Herald", 118, Rarity.COMMON, mage.cards.c.CentaursHerald.class));
        cards.add(new SetCardInfo("Chaos Imps", 90, Rarity.RARE, mage.cards.c.ChaosImps.class));
        cards.add(new SetCardInfo("Chemister's Trick", 149, Rarity.COMMON, mage.cards.c.ChemistersTrick.class));
        cards.add(new SetCardInfo("Chorus of Might", 119, Rarity.COMMON, mage.cards.c.ChorusOfMight.class));
        cards.add(new SetCardInfo("Chromatic Lantern", 226, Rarity.RARE, mage.cards.c.ChromaticLantern.class));
        cards.add(new SetCardInfo("Chronic Flooding", 32, Rarity.COMMON, mage.cards.c.ChronicFlooding.class));
        cards.add(new SetCardInfo("Civic Saber", 227, Rarity.UNCOMMON, mage.cards.c.CivicSaber.class));
        cards.add(new SetCardInfo("Cobblebrute", 91, Rarity.COMMON, mage.cards.c.Cobblebrute.class));
        cards.add(new SetCardInfo("Codex Shredder", 228, Rarity.UNCOMMON, mage.cards.c.CodexShredder.class));
        cards.add(new SetCardInfo("Collective Blessing", 150, Rarity.RARE, mage.cards.c.CollectiveBlessing.class));
        cards.add(new SetCardInfo("Common Bond", 151, Rarity.COMMON, mage.cards.c.CommonBond.class));
        cards.add(new SetCardInfo("Concordia Pegasus", 8, Rarity.COMMON, mage.cards.c.ConcordiaPegasus.class));
        cards.add(new SetCardInfo("Conjured Currency", 33, Rarity.RARE, mage.cards.c.ConjuredCurrency.class));
        cards.add(new SetCardInfo("Corpsejack Menace", 152, Rarity.RARE, mage.cards.c.CorpsejackMenace.class));
        cards.add(new SetCardInfo("Counterflux", 153, Rarity.RARE, mage.cards.c.Counterflux.class));
        cards.add(new SetCardInfo("Coursers' Accord", 154, Rarity.COMMON, mage.cards.c.CoursersAccord.class));
        cards.add(new SetCardInfo("Cremate", 59, Rarity.COMMON, mage.cards.c.Cremate.class));
        cards.add(new SetCardInfo("Crosstown Courier", 34, Rarity.COMMON, mage.cards.c.CrosstownCourier.class));
        cards.add(new SetCardInfo("Cryptborn Horror", 212, Rarity.RARE, mage.cards.c.CryptbornHorror.class));
        cards.add(new SetCardInfo("Cyclonic Rift", 35, Rarity.RARE, mage.cards.c.CyclonicRift.class));
        cards.add(new SetCardInfo("Daggerdrome Imp", 60, Rarity.COMMON, mage.cards.d.DaggerdromeImp.class));
        cards.add(new SetCardInfo("Dark Revenant", 61, Rarity.UNCOMMON, mage.cards.d.DarkRevenant.class));
        cards.add(new SetCardInfo("Deadbridge Goliath", 120, Rarity.RARE, mage.cards.d.DeadbridgeGoliath.class));
        cards.add(new SetCardInfo("Dead Reveler", 62, Rarity.COMMON, mage.cards.d.DeadReveler.class));
        cards.add(new SetCardInfo("Deathrite Shaman", 213, Rarity.RARE, mage.cards.d.DeathriteShaman.class));
        cards.add(new SetCardInfo("Death's Presence", 121, Rarity.RARE, mage.cards.d.DeathsPresence.class));
        cards.add(new SetCardInfo("Desecration Demon", 63, Rarity.RARE, mage.cards.d.DesecrationDemon.class));
        cards.add(new SetCardInfo("Destroy the Evidence", 64, Rarity.COMMON, mage.cards.d.DestroyTheEvidence.class));
        cards.add(new SetCardInfo("Detention Sphere", 155, Rarity.RARE, mage.cards.d.DetentionSphere.class));
        cards.add(new SetCardInfo("Deviant Glee", 65, Rarity.COMMON, mage.cards.d.DeviantGlee.class));
        cards.add(new SetCardInfo("Dispel", 36, Rarity.COMMON, mage.cards.d.Dispel.class));
        cards.add(new SetCardInfo("Doorkeeper", 37, Rarity.COMMON, mage.cards.d.Doorkeeper.class));
        cards.add(new SetCardInfo("Downsize", 38, Rarity.COMMON, mage.cards.d.Downsize.class));
        cards.add(new SetCardInfo("Drainpipe Vermin", 66, Rarity.COMMON, mage.cards.d.DrainpipeVermin.class));
        cards.add(new SetCardInfo("Dramatic Rescue", 156, Rarity.COMMON, mage.cards.d.DramaticRescue.class));
        cards.add(new SetCardInfo("Dreadbore", 157, Rarity.RARE, mage.cards.d.Dreadbore.class));
        cards.add(new SetCardInfo("Dreg Mangler", 158, Rarity.UNCOMMON, mage.cards.d.DregMangler.class));
        cards.add(new SetCardInfo("Drudge Beetle", 122, Rarity.COMMON, mage.cards.d.DrudgeBeetle.class));
        cards.add(new SetCardInfo("Druid's Deliverance", 123, Rarity.COMMON, mage.cards.d.DruidsDeliverance.class));
        cards.add(new SetCardInfo("Dryad Militant", 214, Rarity.UNCOMMON, mage.cards.d.DryadMilitant.class));
        cards.add(new SetCardInfo("Dynacharge", 92, Rarity.COMMON, mage.cards.d.Dynacharge.class));
        cards.add(new SetCardInfo("Electrickery", 93, Rarity.COMMON, mage.cards.e.Electrickery.class));
        cards.add(new SetCardInfo("Epic Experiment", 159, Rarity.MYTHIC, mage.cards.e.EpicExperiment.class));
        cards.add(new SetCardInfo("Essence Backlash", 160, Rarity.COMMON, mage.cards.e.EssenceBacklash.class));
        cards.add(new SetCardInfo("Ethereal Armor", 9, Rarity.COMMON, mage.cards.e.EtherealArmor.class));
        cards.add(new SetCardInfo("Explosive Impact", 94, Rarity.COMMON, mage.cards.e.ExplosiveImpact.class));
        cards.add(new SetCardInfo("Eyes in the Skies", 10, Rarity.COMMON, mage.cards.e.EyesInTheSkies.class));
        cards.add(new SetCardInfo("Faerie Impostor", 39, Rarity.UNCOMMON, mage.cards.f.FaerieImpostor.class));
        cards.add(new SetCardInfo("Fall of the Gavel", 161, Rarity.UNCOMMON, mage.cards.f.FallOfTheGavel.class));
        cards.add(new SetCardInfo("Fencing Ace", 11, Rarity.UNCOMMON, mage.cards.f.FencingAce.class));
        cards.add(new SetCardInfo("Firemind's Foresight", 162, Rarity.RARE, mage.cards.f.FiremindsForesight.class));
        cards.add(new SetCardInfo("Forest", 270, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 271, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 272, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 273, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 274, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Frostburn Weird", 215, Rarity.COMMON, mage.cards.f.FrostburnWeird.class));
        cards.add(new SetCardInfo("Gatecreeper Vine", 124, Rarity.COMMON, mage.cards.g.GatecreeperVine.class));
        cards.add(new SetCardInfo("Giant Growth", 125, Rarity.COMMON, mage.cards.g.GiantGrowth.class));
        cards.add(new SetCardInfo("Gobbling Ooze", 126, Rarity.UNCOMMON, mage.cards.g.GobblingOoze.class));
        cards.add(new SetCardInfo("Goblin Electromancer", 163, Rarity.COMMON, mage.cards.g.GoblinElectromancer.class));
        cards.add(new SetCardInfo("Goblin Rally", 95, Rarity.UNCOMMON, mage.cards.g.GoblinRally.class));
        cards.add(new SetCardInfo("Golgari Charm", 164, Rarity.UNCOMMON, mage.cards.g.GolgariCharm.class));
        cards.add(new SetCardInfo("Golgari Decoy", 127, Rarity.UNCOMMON, mage.cards.g.GolgariDecoy.class));
        cards.add(new SetCardInfo("Golgari Guildgate", 239, Rarity.COMMON, mage.cards.g.GolgariGuildgate.class));
        cards.add(new SetCardInfo("Golgari Keyrune", 229, Rarity.UNCOMMON, mage.cards.g.GolgariKeyrune.class));
        cards.add(new SetCardInfo("Golgari Longlegs", 216, Rarity.COMMON, mage.cards.g.GolgariLonglegs.class));
        cards.add(new SetCardInfo("Gore-House Chainwalker", 96, Rarity.COMMON, mage.cards.g.GoreHouseChainwalker.class));
        cards.add(new SetCardInfo("Grave Betrayal", 67, Rarity.RARE, mage.cards.g.GraveBetrayal.class));
        cards.add(new SetCardInfo("Grim Roustabout", 68, Rarity.COMMON, mage.cards.g.GrimRoustabout.class));
        cards.add(new SetCardInfo("Grisly Salvage", 165, Rarity.COMMON, mage.cards.g.GrislySalvage.class));
        cards.add(new SetCardInfo("Grove of the Guardian", 240, Rarity.RARE, mage.cards.g.GroveOfTheGuardian.class));
        cards.add(new SetCardInfo("Growing Ranks", 217, Rarity.RARE, mage.cards.g.GrowingRanks.class));
        cards.add(new SetCardInfo("Guild Feud", 97, Rarity.RARE, mage.cards.g.GuildFeud.class));
        cards.add(new SetCardInfo("Guttersnipe", 98, Rarity.UNCOMMON, mage.cards.g.Guttersnipe.class));
        cards.add(new SetCardInfo("Hallowed Fountain", 241, Rarity.RARE, mage.cards.h.HallowedFountain.class));
        cards.add(new SetCardInfo("Havoc Festival", 166, Rarity.RARE, mage.cards.h.HavocFestival.class));
        cards.add(new SetCardInfo("Hellhole Flailer", 167, Rarity.UNCOMMON, mage.cards.h.HellholeFlailer.class));
        cards.add(new SetCardInfo("Heroes' Reunion", 168, Rarity.UNCOMMON, mage.cards.h.HeroesReunion.class));
        cards.add(new SetCardInfo("Horncaller's Chant", 128, Rarity.COMMON, mage.cards.h.HorncallersChant.class));
        cards.add(new SetCardInfo("Hover Barrier", 40, Rarity.UNCOMMON, mage.cards.h.HoverBarrier.class));
        cards.add(new SetCardInfo("Hussar Patrol", 169, Rarity.COMMON, mage.cards.h.HussarPatrol.class));
        cards.add(new SetCardInfo("Hypersonic Dragon", 170, Rarity.RARE, mage.cards.h.HypersonicDragon.class));
        cards.add(new SetCardInfo("Inaction Injunction", 41, Rarity.COMMON, mage.cards.i.InactionInjunction.class));
        cards.add(new SetCardInfo("Inspiration", 42, Rarity.COMMON, mage.cards.i.Inspiration.class));
        cards.add(new SetCardInfo("Island", 255, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 256, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 257, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 258, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 259, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Isperia's Skywatch", 43, Rarity.COMMON, mage.cards.i.IsperiasSkywatch.class));
        cards.add(new SetCardInfo("Isperia, Supreme Judge", 171, Rarity.MYTHIC, mage.cards.i.IsperiaSupremeJudge.class));
        cards.add(new SetCardInfo("Izzet Charm", 172, Rarity.UNCOMMON, mage.cards.i.IzzetCharm.class));
        cards.add(new SetCardInfo("Izzet Guildgate", 242, Rarity.COMMON, mage.cards.i.IzzetGuildgate.class));
        cards.add(new SetCardInfo("Izzet Keyrune", 230, Rarity.UNCOMMON, mage.cards.i.IzzetKeyrune.class));
        cards.add(new SetCardInfo("Izzet Staticaster", 173, Rarity.UNCOMMON, mage.cards.i.IzzetStaticaster.class));
        cards.add(new SetCardInfo("Jace, Architect of Thought", 44, Rarity.MYTHIC, mage.cards.j.JaceArchitectOfThought.class));
        cards.add(new SetCardInfo("Jarad, Golgari Lich Lord", 174, Rarity.MYTHIC, mage.cards.j.JaradGolgariLichLord.class));
        cards.add(new SetCardInfo("Jarad's Orders", 175, Rarity.RARE, mage.cards.j.JaradsOrders.class));
        cards.add(new SetCardInfo("Judge's Familiar", 218, Rarity.UNCOMMON, mage.cards.j.JudgesFamiliar.class));
        cards.add(new SetCardInfo("Keening Apparition", 12, Rarity.COMMON, mage.cards.k.KeeningApparition.class));
        cards.add(new SetCardInfo("Knightly Valor", 13, Rarity.COMMON, mage.cards.k.KnightlyValor.class));
        cards.add(new SetCardInfo("Korozda Guildmage", 176, Rarity.UNCOMMON, mage.cards.k.KorozdaGuildmage.class));
        cards.add(new SetCardInfo("Korozda Monitor", 129, Rarity.COMMON, mage.cards.k.KorozdaMonitor.class));
        cards.add(new SetCardInfo("Launch Party", 69, Rarity.COMMON, mage.cards.l.LaunchParty.class));
        cards.add(new SetCardInfo("Lobber Crew", 99, Rarity.COMMON, mage.cards.l.LobberCrew.class));
        cards.add(new SetCardInfo("Lotleth Troll", 177, Rarity.RARE, mage.cards.l.LotlethTroll.class));
        cards.add(new SetCardInfo("Loxodon Smiter", 178, Rarity.RARE, mage.cards.l.LoxodonSmiter.class));
        cards.add(new SetCardInfo("Lyev Skyknight", 179, Rarity.UNCOMMON, mage.cards.l.LyevSkyknight.class));
        cards.add(new SetCardInfo("Mana Bloom", 130, Rarity.RARE, mage.cards.m.ManaBloom.class));
        cards.add(new SetCardInfo("Martial Law", 14, Rarity.RARE, mage.cards.m.MartialLaw.class));
        cards.add(new SetCardInfo("Mercurial Chemister", 180, Rarity.RARE, mage.cards.m.MercurialChemister.class));
        cards.add(new SetCardInfo("Mind Rot", 70, Rarity.COMMON, mage.cards.m.MindRot.class));
        cards.add(new SetCardInfo("Minotaur Aggressor", 100, Rarity.UNCOMMON, mage.cards.m.MinotaurAggressor.class));
        cards.add(new SetCardInfo("Mizzium Mortars", 101, Rarity.RARE, mage.cards.m.MizziumMortars.class));
        cards.add(new SetCardInfo("Mizzium Skin", 45, Rarity.COMMON, mage.cards.m.MizziumSkin.class));
        cards.add(new SetCardInfo("Mountain", 265, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 266, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 267, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 268, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 269, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Necropolis Regent", 71, Rarity.MYTHIC, mage.cards.n.NecropolisRegent.class));
        cards.add(new SetCardInfo("New Prahv Guildmage", 181, Rarity.UNCOMMON, mage.cards.n.NewPrahvGuildmage.class));
        cards.add(new SetCardInfo("Nivix Guildmage", 182, Rarity.UNCOMMON, mage.cards.n.NivixGuildmage.class));
        cards.add(new SetCardInfo("Nivmagus Elemental", 219, Rarity.RARE, mage.cards.n.NivmagusElemental.class));
        cards.add(new SetCardInfo("Niv-Mizzet, Dracogenius", 183, Rarity.MYTHIC, mage.cards.n.NivMizzetDracogenius.class));
        cards.add(new SetCardInfo("Oak Street Innkeeper", 131, Rarity.UNCOMMON, mage.cards.o.OakStreetInnkeeper.class));
        cards.add(new SetCardInfo("Ogre Jailbreaker", 72, Rarity.COMMON, mage.cards.o.OgreJailbreaker.class));
        cards.add(new SetCardInfo("Overgrown Tomb", 243, Rarity.RARE, mage.cards.o.OvergrownTomb.class));
        cards.add(new SetCardInfo("Pack Rat", 73, Rarity.RARE, mage.cards.p.PackRat.class));
        cards.add(new SetCardInfo("Palisade Giant", 15, Rarity.RARE, mage.cards.p.PalisadeGiant.class));
        cards.add(new SetCardInfo("Paralyzing Grasp", 46, Rarity.COMMON, mage.cards.p.ParalyzingGrasp.class));
        cards.add(new SetCardInfo("Perilous Shadow", 74, Rarity.COMMON, mage.cards.p.PerilousShadow.class));
        cards.add(new SetCardInfo("Phantom General", 16, Rarity.UNCOMMON, mage.cards.p.PhantomGeneral.class));
        cards.add(new SetCardInfo("Pithing Needle", 231, Rarity.RARE, mage.cards.p.PithingNeedle.class));
        cards.add(new SetCardInfo("Plains", 250, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 251, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 252, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 253, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 254, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Precinct Captain", 17, Rarity.RARE, mage.cards.p.PrecinctCaptain.class));
        cards.add(new SetCardInfo("Psychic Spiral", 47, Rarity.UNCOMMON, mage.cards.p.PsychicSpiral.class));
        cards.add(new SetCardInfo("Pursuit of Flight", 102, Rarity.COMMON, mage.cards.p.PursuitOfFlight.class));
        cards.add(new SetCardInfo("Pyroconvergence", 103, Rarity.UNCOMMON, mage.cards.p.Pyroconvergence.class));
        cards.add(new SetCardInfo("Racecourse Fury", 104, Rarity.UNCOMMON, mage.cards.r.RacecourseFury.class));
        cards.add(new SetCardInfo("Rakdos Cackler", 220, Rarity.UNCOMMON, mage.cards.r.RakdosCackler.class));
        cards.add(new SetCardInfo("Rakdos Charm", 184, Rarity.UNCOMMON, mage.cards.r.RakdosCharm.class));
        cards.add(new SetCardInfo("Rakdos Guildgate", 244, Rarity.COMMON, mage.cards.r.RakdosGuildgate.class));
        cards.add(new SetCardInfo("Rakdos Keyrune", 232, Rarity.UNCOMMON, mage.cards.r.RakdosKeyrune.class));
        cards.add(new SetCardInfo("Rakdos, Lord of Riots", 187, Rarity.MYTHIC, mage.cards.r.RakdosLordOfRiots.class));
        cards.add(new SetCardInfo("Rakdos Ragemutt", 185, Rarity.UNCOMMON, mage.cards.r.RakdosRagemutt.class));
        cards.add(new SetCardInfo("Rakdos Ringleader", 186, Rarity.UNCOMMON, mage.cards.r.RakdosRingleader.class));
        cards.add(new SetCardInfo("Rakdos Shred-Freak", 221, Rarity.COMMON, mage.cards.r.RakdosShredFreak.class));
        cards.add(new SetCardInfo("Rakdos's Return", 188, Rarity.MYTHIC, mage.cards.r.RakdossReturn.class));
        cards.add(new SetCardInfo("Rest in Peace", 18, Rarity.RARE, mage.cards.r.RestInPeace.class));
        cards.add(new SetCardInfo("Righteous Authority", 189, Rarity.RARE, mage.cards.r.RighteousAuthority.class));
        cards.add(new SetCardInfo("Risen Sanctuary", 190, Rarity.UNCOMMON, mage.cards.r.RisenSanctuary.class));
        cards.add(new SetCardInfo("Rites of Reaping", 191, Rarity.UNCOMMON, mage.cards.r.RitesOfReaping.class));
        cards.add(new SetCardInfo("Rix Maadi Guildmage", 192, Rarity.UNCOMMON, mage.cards.r.RixMaadiGuildmage.class));
        cards.add(new SetCardInfo("Rogue's Passage", 245, Rarity.UNCOMMON, mage.cards.r.RoguesPassage.class));
        cards.add(new SetCardInfo("Rootborn Defenses", 19, Rarity.COMMON, mage.cards.r.RootbornDefenses.class));
        cards.add(new SetCardInfo("Rubbleback Rhino", 132, Rarity.COMMON, mage.cards.r.RubblebackRhino.class));
        cards.add(new SetCardInfo("Runewing", 48, Rarity.COMMON, mage.cards.r.Runewing.class));
        cards.add(new SetCardInfo("Savage Surge", 133, Rarity.UNCOMMON, mage.cards.s.SavageSurge.class));
        cards.add(new SetCardInfo("Search the City", 49, Rarity.RARE, mage.cards.s.SearchTheCity.class));
        cards.add(new SetCardInfo("Search Warrant", 193, Rarity.COMMON, mage.cards.s.SearchWarrant.class));
        cards.add(new SetCardInfo("Security Blockade", 20, Rarity.UNCOMMON, mage.cards.s.SecurityBlockade.class));
        cards.add(new SetCardInfo("Seek the Horizon", 134, Rarity.UNCOMMON, mage.cards.s.SeekTheHorizon.class));
        cards.add(new SetCardInfo("Selesnya Charm", 194, Rarity.UNCOMMON, mage.cards.s.SelesnyaCharm.class));
        cards.add(new SetCardInfo("Selesnya Guildgate", 246, Rarity.COMMON, mage.cards.s.SelesnyaGuildgate.class));
        cards.add(new SetCardInfo("Selesnya Keyrune", 233, Rarity.UNCOMMON, mage.cards.s.SelesnyaKeyrune.class));
        cards.add(new SetCardInfo("Selesnya Sentry", 21, Rarity.COMMON, mage.cards.s.SelesnyaSentry.class));
        cards.add(new SetCardInfo("Seller of Songbirds", 22, Rarity.COMMON, mage.cards.s.SellerOfSongbirds.class));
        cards.add(new SetCardInfo("Sewer Shambler", 75, Rarity.COMMON, mage.cards.s.SewerShambler.class));
        cards.add(new SetCardInfo("Shrieking Affliction", 76, Rarity.UNCOMMON, mage.cards.s.ShriekingAffliction.class));
        cards.add(new SetCardInfo("Skull Rend", 195, Rarity.COMMON, mage.cards.s.SkullRend.class));
        cards.add(new SetCardInfo("Skyline Predator", 50, Rarity.UNCOMMON, mage.cards.s.SkylinePredator.class));
        cards.add(new SetCardInfo("Skymark Roc", 196, Rarity.UNCOMMON, mage.cards.s.SkymarkRoc.class));
        cards.add(new SetCardInfo("Slaughter Games", 197, Rarity.RARE, mage.cards.s.SlaughterGames.class));
        cards.add(new SetCardInfo("Slime Molding", 135, Rarity.UNCOMMON, mage.cards.s.SlimeMolding.class));
        cards.add(new SetCardInfo("Slitherhead", 222, Rarity.UNCOMMON, mage.cards.s.Slitherhead.class));
        cards.add(new SetCardInfo("Sluiceway Scorpion", 198, Rarity.COMMON, mage.cards.s.SluicewayScorpion.class));
        cards.add(new SetCardInfo("Slum Reaper", 77, Rarity.UNCOMMON, mage.cards.s.SlumReaper.class));
        cards.add(new SetCardInfo("Soulsworn Spirit", 51, Rarity.UNCOMMON, mage.cards.s.SoulswornSpirit.class));
        cards.add(new SetCardInfo("Soul Tithe", 23, Rarity.UNCOMMON, mage.cards.s.SoulTithe.class));
        cards.add(new SetCardInfo("Spawn of Rix Maadi", 199, Rarity.COMMON, mage.cards.s.SpawnOfRixMaadi.class));
        cards.add(new SetCardInfo("Sphere of Safety", 24, Rarity.UNCOMMON, mage.cards.s.SphereOfSafety.class));
        cards.add(new SetCardInfo("Sphinx of the Chimes", 52, Rarity.RARE, mage.cards.s.SphinxOfTheChimes.class));
        cards.add(new SetCardInfo("Sphinx's Revelation", 200, Rarity.MYTHIC, mage.cards.s.SphinxsRevelation.class));
        cards.add(new SetCardInfo("Splatter Thug", 105, Rarity.COMMON, mage.cards.s.SplatterThug.class));
        cards.add(new SetCardInfo("Stab Wound", 78, Rarity.COMMON, mage.cards.s.StabWound.class));
        cards.add(new SetCardInfo("Stealer of Secrets", 53, Rarity.COMMON, mage.cards.s.StealerOfSecrets.class));
        cards.add(new SetCardInfo("Steam Vents", 247, Rarity.RARE, mage.cards.s.SteamVents.class));
        cards.add(new SetCardInfo("Stonefare Crocodile", 136, Rarity.COMMON, mage.cards.s.StonefareCrocodile.class));
        cards.add(new SetCardInfo("Street Spasm", 106, Rarity.UNCOMMON, mage.cards.s.StreetSpasm.class));
        cards.add(new SetCardInfo("Street Sweeper", 234, Rarity.UNCOMMON, mage.cards.s.StreetSweeper.class));
        cards.add(new SetCardInfo("Sundering Growth", 223, Rarity.COMMON, mage.cards.s.SunderingGrowth.class));
        cards.add(new SetCardInfo("Sunspire Griffin", 25, Rarity.COMMON, mage.cards.s.SunspireGriffin.class));
        cards.add(new SetCardInfo("Supreme Verdict", 201, Rarity.RARE, mage.cards.s.SupremeVerdict.class));
        cards.add(new SetCardInfo("Survey the Wreckage", 107, Rarity.COMMON, mage.cards.s.SurveyTheWreckage.class));
        cards.add(new SetCardInfo("Swamp", 260, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 261, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 262, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 263, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 264, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swift Justice", 26, Rarity.COMMON, mage.cards.s.SwiftJustice.class));
        cards.add(new SetCardInfo("Syncopate", 54, Rarity.UNCOMMON, mage.cards.s.Syncopate.class));
        cards.add(new SetCardInfo("Tablet of the Guilds", 235, Rarity.UNCOMMON, mage.cards.t.TabletOfTheGuilds.class));
        cards.add(new SetCardInfo("Tavern Swindler", 79, Rarity.UNCOMMON, mage.cards.t.TavernSwindler.class));
        cards.add(new SetCardInfo("Teleportal", 202, Rarity.UNCOMMON, mage.cards.t.Teleportal.class));
        cards.add(new SetCardInfo("Temple Garden", 248, Rarity.RARE, mage.cards.t.TempleGarden.class));
        cards.add(new SetCardInfo("Tenement Crasher", 108, Rarity.COMMON, mage.cards.t.TenementCrasher.class));
        cards.add(new SetCardInfo("Terrus Wurm", 80, Rarity.COMMON, mage.cards.t.TerrusWurm.class));
        cards.add(new SetCardInfo("Thoughtflare", 203, Rarity.UNCOMMON, mage.cards.t.Thoughtflare.class));
        cards.add(new SetCardInfo("Thrill-Kill Assassin", 81, Rarity.UNCOMMON, mage.cards.t.ThrillKillAssassin.class));
        cards.add(new SetCardInfo("Tower Drake", 55, Rarity.COMMON, mage.cards.t.TowerDrake.class));
        cards.add(new SetCardInfo("Towering Indrik", 137, Rarity.COMMON, mage.cards.t.ToweringIndrik.class));
        cards.add(new SetCardInfo("Trained Caracal", 27, Rarity.COMMON, mage.cards.t.TrainedCaracal.class));
        cards.add(new SetCardInfo("Traitorous Instinct", 109, Rarity.COMMON, mage.cards.t.TraitorousInstinct.class));
        cards.add(new SetCardInfo("Transguild Promenade", 249, Rarity.COMMON, mage.cards.t.TransguildPromenade.class));
        cards.add(new SetCardInfo("Treasured Find", 204, Rarity.UNCOMMON, mage.cards.t.TreasuredFind.class));
        cards.add(new SetCardInfo("Trestle Troll", 205, Rarity.COMMON, mage.cards.t.TrestleTroll.class));
        cards.add(new SetCardInfo("Trostani, Selesnya's Voice", 206, Rarity.MYTHIC, mage.cards.t.TrostaniSelesnyasVoice.class));
        cards.add(new SetCardInfo("Trostani's Judgment", 28, Rarity.COMMON, mage.cards.t.TrostanisJudgment.class));
        cards.add(new SetCardInfo("Ultimate Price", 82, Rarity.UNCOMMON, mage.cards.u.UltimatePrice.class));
        cards.add(new SetCardInfo("Underworld Connections", 83, Rarity.RARE, mage.cards.u.UnderworldConnections.class));
        cards.add(new SetCardInfo("Urban Burgeoning", 138, Rarity.COMMON, mage.cards.u.UrbanBurgeoning.class));
        cards.add(new SetCardInfo("Utvara Hellkite", 110, Rarity.MYTHIC, mage.cards.u.UtvaraHellkite.class));
        cards.add(new SetCardInfo("Vandalblast", 111, Rarity.UNCOMMON, mage.cards.v.Vandalblast.class));
        cards.add(new SetCardInfo("Vassal Soul", 224, Rarity.COMMON, mage.cards.v.VassalSoul.class));
        cards.add(new SetCardInfo("Viashino Racketeer", 112, Rarity.COMMON, mage.cards.v.ViashinoRacketeer.class));
        cards.add(new SetCardInfo("Vitu-Ghazi Guildmage", 207, Rarity.UNCOMMON, mage.cards.v.VituGhaziGuildmage.class));
        cards.add(new SetCardInfo("Voidwielder", 56, Rarity.COMMON, mage.cards.v.Voidwielder.class));
        cards.add(new SetCardInfo("Volatile Rig", 236, Rarity.RARE, mage.cards.v.VolatileRig.class));
        cards.add(new SetCardInfo("Vraska the Unseen", 208, Rarity.MYTHIC, mage.cards.v.VraskaTheUnseen.class));
        cards.add(new SetCardInfo("Wayfaring Temple", 209, Rarity.RARE, mage.cards.w.WayfaringTemple.class));
        cards.add(new SetCardInfo("Wild Beastmaster", 139, Rarity.RARE, mage.cards.w.WildBeastmaster.class));
        cards.add(new SetCardInfo("Worldspine Wurm", 140, Rarity.MYTHIC, mage.cards.w.WorldspineWurm.class));
        cards.add(new SetCardInfo("Zanikev Locust", 84, Rarity.UNCOMMON, mage.cards.z.ZanikevLocust.class));
    }

    @Override
    public BoosterCollator createCollator() {
        return new ReturnToRavnicaCollator();
    }
}

// Booster collation info from https://www.lethe.xyz/mtg/collation/rtr.html
// Using USA collation for all rarities
// Foil rare sheet used for regular rares as regular rare sheet is not known
class ReturnToRavnicaCollator implements BoosterCollator {
    private final CardRun commonA = new CardRun(true, "26", "123", "93", "34", "69", "4", "88", "36", "128", "102", "27", "32", "125", "109", "19", "36", "239", "65", "2", "138", "42", "27", "107", "113", "32", "66", "26", "128", "31", "88", "2", "113", "45", "58", "10", "107", "116", "65", "4", "80", "119", "93", "46", "116", "58", "69", "19", "138", "45", "92", "9", "46", "119", "10", "109", "42", "123", "9", "102", "66", "125", "34", "92", "80", "239", "31");
    private final CardRun commonB = new CardRun(true, "156", "216", "160", "148", "199", "224", "205", "163", "151", "221", "193", "198", "149", "154", "195", "169", "165", "215", "223", "144", "91", "169", "199", "154", "160", "198", "156", "221", "223", "163", "165", "249", "224", "195", "148", "149", "216", "193", "144", "151", "215", "205", "249", "91", "224", "223", "198", "199", "215", "193", "148", "165", "221", "160", "169", "151", "216", "195", "163", "91", "156", "154", "205", "144", "149", "249");
    private final CardRun commonC1 = new CardRun(true, "122", "37", "99", "70", "22", "56", "124", "112", "59", "21", "38", "94", "136", "60", "37", "244", "115", "38", "8", "59", "87", "41", "122", "244", "56", "21", "72", "87", "132", "22", "75", "96", "124", "5", "242", "60", "43", "99", "8", "64", "136", "96", "41", "242", "12", "72", "43", "132", "5", "94", "70", "115", "12", "112", "75");
    private final CardRun commonC2 = new CardRun(true, "48", "74", "28", "85", "118", "62", "53", "13", "105", "246", "68", "137", "48", "25", "74", "105", "118", "64", "55", "13", "85", "78", "137", "237", "55", "62", "28", "118", "74", "108", "25", "237", "129", "78", "105", "53", "246", "62", "85", "129", "28", "108", "68", "55", "25", "246", "129", "48", "78", "13", "137", "53", "68", "237", "108");
    private final CardRun uncommonA = new CardRun(true, "24", "228", "50", "133", "79", "16", "98", "29", "114", "100", "30", "23", "61", "227", "95", "133", "24", "104", "77", "39", "98", "134", "16", "61", "100", "228", "133", "20", "76", "104", "30", "131", "24", "79", "95", "77", "16", "39", "134", "76", "98", "50", "114", "23", "79", "29", "131", "227", "20", "39", "61", "134", "50", "95", "228", "23", "76", "30", "114", "104", "29", "20", "77", "131", "100", "227");
    private final CardRun uncommonB = new CardRun(true, "192", "233", "203", "222", "196", "232", "57", "3", "194", "229", "167", "211", "196", "233", "158", "184", "172", "225", "146", "222", "192", "230", "179", "3", "168", "191", "232", "203", "161", "57", "168", "192", "225", "158", "211", "194", "232", "179", "222", "172", "233", "167", "161", "191", "230", "146", "184", "196", "229", "203", "3", "146", "211", "191", "225", "167", "168", "172", "229", "179", "184", "57", "194", "230", "158", "161");
    private final CardRun uncommonC = new CardRun(true, "111", "81", "54", "117", "235", "103", "127", "7", "82", "234", "6", "47", "106", "84", "245", "126", "54", "11", "81", "40", "82", "127", "111", "7", "235", "47", "117", "6", "103", "40", "11", "82", "126", "84", "234", "106", "235", "127", "81", "103", "245", "54", "111", "11", "47", "84", "117", "234", "7", "126", "106", "40", "6", "245");
    private final CardRun uncommonD = new CardRun(true, "182", "164", "181", "185", "207", "202", "204", "181", "186", "190", "173", "164", "145", "220", "214", "135", "182", "176", "218", "185", "89", "214", "202", "164", "218", "186", "135", "89", "190", "182", "204", "145", "185", "135", "51", "207", "173", "176", "181", "220", "51", "89", "214", "173", "204", "218", "220", "190", "202", "51", "176", "145", "186", "207");
    private final CardRun rare = new CardRun(true, "101", "73", "143", "219", "86", "139", "187", "201", "18", "52", "141", "212", "97", "49", "150", "120", "159", "17", "210", "241", "180", "52", "213", "14", "200", "63", "209", "35", "162", "219", "71", "15", "178", "155", "67", "86", "177", "248", "217", "247", "197", "90", "180", "248", "166", "240", "147", "247", "236", "142", "243", "153", "18", "140", "212", "166", "33", "139", "209", "183", "238", "226", "189", "33", "177", "97", "152", "236", "188", "110", "178", "67", "150", "14", "210", "101", "206", "231", "142", "130", "147", "83", "49", "155", "121", "170", "17", "15", "152", "241", "175", "238", "197", "1", "174", "90", "63", "171", "201", "130", "231", "157", "170", "44", "240", "162", "120", "189", "83", "175", "226", "157", "217", "73", "243", "141", "153", "121", "35", "208", "213");
    private final CardRun land = new CardRun(false, "250", "251", "252", "253", "254", "255", "256", "257", "258", "259", "260", "261", "262", "263", "264", "265", "266", "267", "268", "269", "270", "271", "272", "273", "274");

    private final BoosterStructure AABBC1C1C1C1C1C1 = new BoosterStructure(
            commonA, commonA,
            commonB, commonB,
            commonC1, commonC1, commonC1, commonC1, commonC1, commonC1
    );
    private final BoosterStructure AAABBC1C1C1C1C1 = new BoosterStructure(
            commonA, commonA, commonA,
            commonB, commonB,
            commonC1, commonC1, commonC1, commonC1, commonC1
    );
    private final BoosterStructure AAAABBC2C2C2C2 = new BoosterStructure(
            commonA, commonA, commonA, commonA,
            commonB, commonB,
            commonC2, commonC2, commonC2, commonC2
    );
    private final BoosterStructure AAAABBBBC2C2 = new BoosterStructure(
            commonA, commonA, commonA, commonA,
            commonB, commonB, commonB, commonB,
            commonC2, commonC2
    );
    private final BoosterStructure ABC = new BoosterStructure(uncommonA, uncommonB, uncommonC);
    private final BoosterStructure ABD = new BoosterStructure(uncommonA, uncommonB, uncommonD);
    private final BoosterStructure ACD = new BoosterStructure(uncommonA, uncommonC, uncommonD);
    private final BoosterStructure BCD = new BoosterStructure(uncommonB, uncommonC, uncommonD);
    private final BoosterStructure R1 = new BoosterStructure(rare);
    private final BoosterStructure L1 = new BoosterStructure(land);

    // In order for equal numbers of each common to exist, the average booster must contain:
    // 3.27 A commons (36 / 11)
    // 2.18 B commons (24 / 11)
    // 2.73 C1 commons (30 / 11, or 60 / 11 in each C1 booster)
    // 1.82 C2 commons (20 / 11, or 40 / 11 in each C2 booster)
    // These numbers are the same for all sets with 101 commons in A/B/C1/C2 print runs
    // and with 10 common slots per booster
    private final RarityConfiguration commonRuns = new RarityConfiguration(
            AABBC1C1C1C1C1C1,
            AABBC1C1C1C1C1C1,
            AABBC1C1C1C1C1C1,
            AABBC1C1C1C1C1C1,
            AABBC1C1C1C1C1C1,
            AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1,

            AAAABBC2C2C2C2,
            AAAABBC2C2C2C2,
            AAAABBC2C2C2C2,
            AAAABBC2C2C2C2,
            AAAABBC2C2C2C2,
            AAAABBC2C2C2C2,
            AAAABBC2C2C2C2,
            AAAABBC2C2C2C2,
            AAAABBC2C2C2C2,
            AAAABBBBC2C2,
            AAAABBBBC2C2
    );
    // In order for equal numbers of each uncommon to exist, the average booster must contain:
    // 0.825 A uncommons (33 / 40)
    // 0.825 B uncommons (33 / 40)
    // 0.675 C uncommons (27 / 40)
    // 0.675 D uncommons (27 / 40)
    // These numbers are the same for all sets with 80 uncommons in asymmetrical A/B/C/D print runs
    private final RarityConfiguration uncommonRuns = new RarityConfiguration(
            ABC, ABD, ABC, ABD, ABC, ABD, ABC, ABD, ABC, ABD, ABC, ABD, ABC,
            ABD, ABC, ABD, ABC, ABD, ABC, ABD, ABC, ABD, ABC, ABD, ABC, ABD,
            ACD, BCD, ACD, BCD, ACD, BCD, ACD,
            BCD, ACD, BCD, ACD, BCD, ACD, BCD
    );
    private final RarityConfiguration rareRuns = new RarityConfiguration(R1);
    private final RarityConfiguration landRuns = new RarityConfiguration(L1);

    @Override
    public List<String> makeBooster() {
        List<String> booster = new ArrayList<>();
        booster.addAll(commonRuns.getNext().makeRun());
        booster.addAll(uncommonRuns.getNext().makeRun());
        booster.addAll(rareRuns.getNext().makeRun());
        booster.addAll(landRuns.getNext().makeRun());
        return booster;
    }
}
