package mage.sets;

import mage.ObjectColor;
import mage.cards.CardGraphicInfo;
import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;
import mage.collation.BoosterCollator;
import mage.collation.BoosterStructure;
import mage.collation.CardRun;
import mage.collation.RarityConfiguration;

import java.util.ArrayList;
import java.util.List;

public final class Onslaught extends ExpansionSet {

    private static final Onslaught instance = new Onslaught();

    public static Onslaught getInstance() {
        return instance;
    }

    private Onslaught() {
        super("Onslaught", "ONS", ExpansionSet.buildDate(2002, 10, 7), SetType.EXPANSION);
        this.blockName = "Onslaught";
        this.hasBoosters = true;
        this.rotationSet = true;
        this.numBoosterLands = 0;
        this.numBoosterCommon = 11;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 0;

        cards.add(new SetCardInfo("Accursed Centaur", 123, Rarity.COMMON, mage.cards.a.AccursedCentaur.class, RETRO_ART));
        cards.add(new SetCardInfo("Aether Charge", 184, Rarity.UNCOMMON, mage.cards.a.AetherCharge.class, RETRO_ART));
        cards.add(new SetCardInfo("Aggravated Assault", 185, Rarity.RARE, mage.cards.a.AggravatedAssault.class, RETRO_ART));
        cards.add(new SetCardInfo("Airborne Aid", 62, Rarity.COMMON, mage.cards.a.AirborneAid.class, RETRO_ART));
        cards.add(new SetCardInfo("Airdrop Condor", 186, Rarity.UNCOMMON, mage.cards.a.AirdropCondor.class, RETRO_ART));
        cards.add(new SetCardInfo("Akroma's Blessing", 1, Rarity.UNCOMMON, mage.cards.a.AkromasBlessing.class, RETRO_ART));
        cards.add(new SetCardInfo("Akroma's Vengeance", 2, Rarity.RARE, mage.cards.a.AkromasVengeance.class, RETRO_ART));
        cards.add(new SetCardInfo("Ancestor's Prophet", 3, Rarity.RARE, mage.cards.a.AncestorsProphet.class, RETRO_ART));
        cards.add(new SetCardInfo("Animal Magnetism", 245, Rarity.RARE, mage.cards.a.AnimalMagnetism.class, RETRO_ART));
        cards.add(new SetCardInfo("Annex", 63, Rarity.UNCOMMON, mage.cards.a.Annex.class, RETRO_ART));
        cards.add(new SetCardInfo("Anurid Murkdiver", 124, Rarity.COMMON, mage.cards.a.AnuridMurkdiver.class, RETRO_ART));
        cards.add(new SetCardInfo("Aphetto Alchemist", 64, Rarity.UNCOMMON, mage.cards.a.AphettoAlchemist.class, RETRO_ART));
        cards.add(new SetCardInfo("Aphetto Dredging", 125, Rarity.COMMON, mage.cards.a.AphettoDredging.class, RETRO_ART));
        cards.add(new SetCardInfo("Aphetto Grifter", 65, Rarity.UNCOMMON, mage.cards.a.AphettoGrifter.class, RETRO_ART));
        cards.add(new SetCardInfo("Aphetto Vulture", 126, Rarity.UNCOMMON, mage.cards.a.AphettoVulture.class, RETRO_ART));
        cards.add(new SetCardInfo("Arcanis the Omnipotent", 66, Rarity.RARE, mage.cards.a.ArcanisTheOmnipotent.class, RETRO_ART));
//        cards.add(new SetCardInfo("Artificial Evolution", 67, Rarity.RARE, mage.cards.a.ArtificialEvolution.class, RETRO_ART));
        cards.add(new SetCardInfo("Ascending Aven", 68, Rarity.COMMON, mage.cards.a.AscendingAven.class, RETRO_ART));
        cards.add(new SetCardInfo("Astral Slide", 4, Rarity.UNCOMMON, mage.cards.a.AstralSlide.class, RETRO_ART));
        cards.add(new SetCardInfo("Aura Extraction", 5, Rarity.UNCOMMON, mage.cards.a.AuraExtraction.class, RETRO_ART));
        cards.add(new SetCardInfo("Aurification", 6, Rarity.RARE, mage.cards.a.Aurification.class, RETRO_ART));
        cards.add(new SetCardInfo("Avarax", 187, Rarity.UNCOMMON, mage.cards.a.Avarax.class, RETRO_ART));
        cards.add(new SetCardInfo("Aven Brigadier", 7, Rarity.RARE, mage.cards.a.AvenBrigadier.class, RETRO_ART));
        cards.add(new SetCardInfo("Aven Fateshaper", 69, Rarity.UNCOMMON, mage.cards.a.AvenFateshaper.class, RETRO_ART));
        cards.add(new SetCardInfo("Aven Soulgazer", 8, Rarity.UNCOMMON, mage.cards.a.AvenSoulgazer.class, RETRO_ART));
        cards.add(new SetCardInfo("Backslide", 70, Rarity.COMMON, mage.cards.b.Backslide.class, RETRO_ART));
        cards.add(new SetCardInfo("Barkhide Mauler", 246, Rarity.COMMON, mage.cards.b.BarkhideMauler.class, RETRO_ART));
        cards.add(new SetCardInfo("Barren Moor", 312, Rarity.COMMON, mage.cards.b.BarrenMoor.class, RETRO_ART));
        cards.add(new SetCardInfo("Battering Craghorn", 188, Rarity.COMMON, mage.cards.b.BatteringCraghorn.class, RETRO_ART));
        cards.add(new SetCardInfo("Battlefield Medic", 9, Rarity.COMMON, mage.cards.b.BattlefieldMedic.class, RETRO_ART));
        cards.add(new SetCardInfo("Biorhythm", 247, Rarity.RARE, mage.cards.b.Biorhythm.class, RETRO_ART));
        cards.add(new SetCardInfo("Birchlore Rangers", 248, Rarity.COMMON, mage.cards.b.BirchloreRangers.class, RETRO_ART));
        cards.add(new SetCardInfo("Blackmail", 127, Rarity.UNCOMMON, mage.cards.b.Blackmail.class, RETRO_ART));
        cards.add(new SetCardInfo("Blatant Thievery", 71, Rarity.RARE, mage.cards.b.BlatantThievery.class, RETRO_ART));
        cards.add(new SetCardInfo("Blistering Firecat", 189, Rarity.RARE, mage.cards.b.BlisteringFirecat.class, RETRO_ART));
        cards.add(new SetCardInfo("Bloodline Shaman", 249, Rarity.UNCOMMON, mage.cards.b.BloodlineShaman.class, RETRO_ART));
        cards.add(new SetCardInfo("Bloodstained Mire", 313, Rarity.RARE, mage.cards.b.BloodstainedMire.class, RETRO_ART));
        cards.add(new SetCardInfo("Boneknitter", 128, Rarity.UNCOMMON, mage.cards.b.Boneknitter.class, RETRO_ART));
        cards.add(new SetCardInfo("Break Open", 190, Rarity.COMMON, mage.cards.b.BreakOpen.class, RETRO_ART));
        cards.add(new SetCardInfo("Brightstone Ritual", 191, Rarity.COMMON, mage.cards.b.BrightstoneRitual.class, RETRO_ART));
        cards.add(new SetCardInfo("Broodhatch Nantuko", 250, Rarity.UNCOMMON, mage.cards.b.BroodhatchNantuko.class, RETRO_ART));
        cards.add(new SetCardInfo("Butcher Orgg", 192, Rarity.RARE, mage.cards.b.ButcherOrgg.class, RETRO_ART));
        cards.add(new SetCardInfo("Cabal Archon", 129, Rarity.UNCOMMON, mage.cards.c.CabalArchon.class, RETRO_ART));
        cards.add(new SetCardInfo("Cabal Executioner", 130, Rarity.UNCOMMON, mage.cards.c.CabalExecutioner.class, RETRO_ART));
        cards.add(new SetCardInfo("Cabal Slaver", 131, Rarity.UNCOMMON, mage.cards.c.CabalSlaver.class, RETRO_ART));
        cards.add(new SetCardInfo("Callous Oppressor", 72, Rarity.RARE, mage.cards.c.CallousOppressor.class, RETRO_ART));
        cards.add(new SetCardInfo("Catapult Master", 10, Rarity.RARE, mage.cards.c.CatapultMaster.class, RETRO_ART));
        cards.add(new SetCardInfo("Catapult Squad", 11, Rarity.UNCOMMON, mage.cards.c.CatapultSquad.class, RETRO_ART));
        cards.add(new SetCardInfo("Centaur Glade", 251, Rarity.UNCOMMON, mage.cards.c.CentaurGlade.class, RETRO_ART));
        cards.add(new SetCardInfo("Chain of Acid", 252, Rarity.UNCOMMON, mage.cards.c.ChainOfAcid.class, RETRO_ART));
        cards.add(new SetCardInfo("Chain of Plasma", 193, Rarity.UNCOMMON, mage.cards.c.ChainOfPlasma.class, RETRO_ART));
        cards.add(new SetCardInfo("Chain of Silence", 12, Rarity.UNCOMMON, mage.cards.c.ChainOfSilence.class, RETRO_ART));
        cards.add(new SetCardInfo("Chain of Smog", 132, Rarity.UNCOMMON, mage.cards.c.ChainOfSmog.class, RETRO_ART));
        cards.add(new SetCardInfo("Chain of Vapor", 73, Rarity.UNCOMMON, mage.cards.c.ChainOfVapor.class, RETRO_ART));
        cards.add(new SetCardInfo("Charging Slateback", 194, Rarity.COMMON, mage.cards.c.ChargingSlateback.class, RETRO_ART));
        cards.add(new SetCardInfo("Choking Tethers", 74, Rarity.COMMON, mage.cards.c.ChokingTethers.class, RETRO_ART));
        cards.add(new SetCardInfo("Circle of Solace", 13, Rarity.RARE, mage.cards.c.CircleOfSolace.class, RETRO_ART));
        cards.add(new SetCardInfo("Clone", 75, Rarity.RARE, mage.cards.c.Clone.class, RETRO_ART));
        cards.add(new SetCardInfo("Commando Raid", 195, Rarity.UNCOMMON, mage.cards.c.CommandoRaid.class, RETRO_ART));
        cards.add(new SetCardInfo("Complicate", 76, Rarity.UNCOMMON, mage.cards.c.Complicate.class, RETRO_ART));
        cards.add(new SetCardInfo("Contested Cliffs", 314, Rarity.RARE, mage.cards.c.ContestedCliffs.class, RETRO_ART));
        cards.add(new SetCardInfo("Convalescent Care", 14, Rarity.RARE, mage.cards.c.ConvalescentCare.class, RETRO_ART));
        cards.add(new SetCardInfo("Cover of Darkness", 133, Rarity.RARE, mage.cards.c.CoverOfDarkness.class, RETRO_ART));
        cards.add(new SetCardInfo("Crafty Pathmage", 77, Rarity.COMMON, mage.cards.c.CraftyPathmage.class, RETRO_ART));
        cards.add(new SetCardInfo("Crowd Favorites", 15, Rarity.UNCOMMON, mage.cards.c.CrowdFavorites.class, RETRO_ART));
        cards.add(new SetCardInfo("Crown of Ascension", 78, Rarity.COMMON, mage.cards.c.CrownOfAscension.class, RETRO_ART));
        cards.add(new SetCardInfo("Crown of Awe", 16, Rarity.COMMON, mage.cards.c.CrownOfAwe.class, RETRO_ART));
        cards.add(new SetCardInfo("Crown of Fury", 196, Rarity.COMMON, mage.cards.c.CrownOfFury.class, RETRO_ART));
        cards.add(new SetCardInfo("Crown of Suspicion", 134, Rarity.COMMON, mage.cards.c.CrownOfSuspicion.class, RETRO_ART));
        cards.add(new SetCardInfo("Crown of Vigor", 253, Rarity.COMMON, mage.cards.c.CrownOfVigor.class, RETRO_ART));
        cards.add(new SetCardInfo("Crude Rampart", 17, Rarity.UNCOMMON, mage.cards.c.CrudeRampart.class, RETRO_ART));
        cards.add(new SetCardInfo("Cruel Revival", 135, Rarity.COMMON, mage.cards.c.CruelRevival.class, RETRO_ART));
        cards.add(new SetCardInfo("Cryptic Gateway", 306, Rarity.RARE, mage.cards.c.CrypticGateway.class, RETRO_ART));
        cards.add(new SetCardInfo("Custody Battle", 197, Rarity.UNCOMMON, mage.cards.c.CustodyBattle.class, RETRO_ART));
        cards.add(new SetCardInfo("Daru Cavalier", 18, Rarity.COMMON, mage.cards.d.DaruCavalier.class, RETRO_ART));
        cards.add(new SetCardInfo("Daru Encampment", 315, Rarity.UNCOMMON, mage.cards.d.DaruEncampment.class, RETRO_ART));
        cards.add(new SetCardInfo("Daru Healer", 19, Rarity.COMMON, mage.cards.d.DaruHealer.class, RETRO_ART));
        cards.add(new SetCardInfo("Daru Lancer", 20, Rarity.COMMON, mage.cards.d.DaruLancer.class, RETRO_ART));
        cards.add(new SetCardInfo("Daunting Defender", 21, Rarity.COMMON, mage.cards.d.DauntingDefender.class, RETRO_ART));
        cards.add(new SetCardInfo("Dawning Purist", 22, Rarity.UNCOMMON, mage.cards.d.DawningPurist.class, RETRO_ART));
        cards.add(new SetCardInfo("Death Match", 136, Rarity.RARE, mage.cards.d.DeathMatch.class, RETRO_ART));
        cards.add(new SetCardInfo("Death Pulse", 137, Rarity.UNCOMMON, mage.cards.d.DeathPulse.class, RETRO_ART));
        cards.add(new SetCardInfo("Defensive Maneuvers", 23, Rarity.COMMON, mage.cards.d.DefensiveManeuvers.class, RETRO_ART));
        cards.add(new SetCardInfo("Demystify", 24, Rarity.COMMON, mage.cards.d.Demystify.class, RETRO_ART));
        cards.add(new SetCardInfo("Dirge of Dread", 138, Rarity.COMMON, mage.cards.d.DirgeOfDread.class, RETRO_ART));
        cards.add(new SetCardInfo("Disciple of Grace", 25, Rarity.COMMON, mage.cards.d.DiscipleOfGrace.class, RETRO_ART));
        cards.add(new SetCardInfo("Disciple of Malice", 139, Rarity.COMMON, mage.cards.d.DiscipleOfMalice.class, RETRO_ART));
        cards.add(new SetCardInfo("Discombobulate", 79, Rarity.UNCOMMON, mage.cards.d.Discombobulate.class, RETRO_ART));
        cards.add(new SetCardInfo("Dispersing Orb", 80, Rarity.UNCOMMON, mage.cards.d.DispersingOrb.class, RETRO_ART));
        cards.add(new SetCardInfo("Disruptive Pitmage", 81, Rarity.COMMON, mage.cards.d.DisruptivePitmage.class, RETRO_ART));
        cards.add(new SetCardInfo("Dive Bomber", 26, Rarity.COMMON, mage.cards.d.DiveBomber.class, RETRO_ART));
        cards.add(new SetCardInfo("Doom Cannon", 307, Rarity.RARE, mage.cards.d.DoomCannon.class, RETRO_ART));
        cards.add(new SetCardInfo("Doomed Necromancer", 140, Rarity.RARE, mage.cards.d.DoomedNecromancer.class, RETRO_ART));
        cards.add(new SetCardInfo("Doubtless One", 27, Rarity.UNCOMMON, mage.cards.d.DoubtlessOne.class, RETRO_ART));
        cards.add(new SetCardInfo("Dragon Roost", 198, Rarity.RARE, mage.cards.d.DragonRoost.class, RETRO_ART));
        cards.add(new SetCardInfo("Dream Chisel", 308, Rarity.RARE, mage.cards.d.DreamChisel.class, RETRO_ART));
        cards.add(new SetCardInfo("Dwarven Blastminer", 199, Rarity.UNCOMMON, mage.cards.d.DwarvenBlastminer.class, RETRO_ART));
        cards.add(new SetCardInfo("Ebonblade Reaper", 141, Rarity.RARE, mage.cards.e.EbonbladeReaper.class, RETRO_ART));
        cards.add(new SetCardInfo("Elven Riders", 254, Rarity.UNCOMMON, mage.cards.e.ElvenRiders.class, RETRO_ART));
        cards.add(new SetCardInfo("Elvish Guidance", 255, Rarity.COMMON, mage.cards.e.ElvishGuidance.class, RETRO_ART));
        cards.add(new SetCardInfo("Elvish Pathcutter", 256, Rarity.COMMON, mage.cards.e.ElvishPathcutter.class, RETRO_ART));
        cards.add(new SetCardInfo("Elvish Pioneer", 257, Rarity.COMMON, mage.cards.e.ElvishPioneer.class, RETRO_ART));
        cards.add(new SetCardInfo("Elvish Scrapper", 258, Rarity.UNCOMMON, mage.cards.e.ElvishScrapper.class, RETRO_ART));
        cards.add(new SetCardInfo("Elvish Vanguard", 259, Rarity.RARE, mage.cards.e.ElvishVanguard.class, RETRO_ART));
        cards.add(new SetCardInfo("Elvish Warrior", 260, Rarity.COMMON, mage.cards.e.ElvishWarrior.class, RETRO_ART));
        cards.add(new SetCardInfo("Embermage Goblin", 200, Rarity.UNCOMMON, mage.cards.e.EmbermageGoblin.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Embermage Goblin", "200*", Rarity.UNCOMMON, mage.cards.e.EmbermageGoblin.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Enchantress's Presence", 261, Rarity.RARE, mage.cards.e.EnchantresssPresence.class, RETRO_ART));
        cards.add(new SetCardInfo("Endemic Plague", 142, Rarity.RARE, mage.cards.e.EndemicPlague.class, RETRO_ART));
        cards.add(new SetCardInfo("Entrails Feaster", 143, Rarity.RARE, mage.cards.e.EntrailsFeaster.class, RETRO_ART));
        cards.add(new SetCardInfo("Erratic Explosion", 201, Rarity.COMMON, mage.cards.e.ErraticExplosion.class, RETRO_ART));
        cards.add(new SetCardInfo("Essence Fracture", 82, Rarity.UNCOMMON, mage.cards.e.EssenceFracture.class, RETRO_ART));
        cards.add(new SetCardInfo("Everglove Courier", 262, Rarity.UNCOMMON, mage.cards.e.EvergloveCourier.class, RETRO_ART));
        cards.add(new SetCardInfo("Exalted Angel", 28, Rarity.RARE, mage.cards.e.ExaltedAngel.class, RETRO_ART));
        cards.add(new SetCardInfo("Explosive Vegetation", 263, Rarity.UNCOMMON, mage.cards.e.ExplosiveVegetation.class, RETRO_ART));
        cards.add(new SetCardInfo("Fade from Memory", 144, Rarity.UNCOMMON, mage.cards.f.FadeFromMemory.class, RETRO_ART));
        cards.add(new SetCardInfo("Fallen Cleric", 145, Rarity.COMMON, mage.cards.f.FallenCleric.class, RETRO_ART));
        cards.add(new SetCardInfo("False Cure", 146, Rarity.RARE, mage.cards.f.FalseCure.class, RETRO_ART));
        cards.add(new SetCardInfo("Feeding Frenzy", 147, Rarity.UNCOMMON, mage.cards.f.FeedingFrenzy.class, RETRO_ART));
        cards.add(new SetCardInfo("Festering Goblin", 148, Rarity.COMMON, mage.cards.f.FesteringGoblin.class, RETRO_ART));
        cards.add(new SetCardInfo("Fever Charm", 202, Rarity.COMMON, mage.cards.f.FeverCharm.class, RETRO_ART));
        cards.add(new SetCardInfo("Flamestick Courier", 203, Rarity.UNCOMMON, mage.cards.f.FlamestickCourier.class, RETRO_ART));
        cards.add(new SetCardInfo("Fleeting Aven", 83, Rarity.UNCOMMON, mage.cards.f.FleetingAven.class, RETRO_ART));
        cards.add(new SetCardInfo("Flooded Strand", 316, Rarity.RARE, mage.cards.f.FloodedStrand.class, RETRO_ART));
        cards.add(new SetCardInfo("Foothill Guide", 29, Rarity.COMMON, mage.cards.f.FoothillGuide.class, RETRO_ART));
        cards.add(new SetCardInfo("Forest", 347, Rarity.LAND, mage.cards.basiclands.Forest.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 348, Rarity.LAND, mage.cards.basiclands.Forest.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 349, Rarity.LAND, mage.cards.basiclands.Forest.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 350, Rarity.LAND, mage.cards.basiclands.Forest.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Forgotten Cave", 317, Rarity.COMMON, mage.cards.f.ForgottenCave.class, RETRO_ART));
        cards.add(new SetCardInfo("Frightshroud Courier", 149, Rarity.UNCOMMON, mage.cards.f.FrightshroudCourier.class, RETRO_ART));
        cards.add(new SetCardInfo("Future Sight", 84, Rarity.RARE, mage.cards.f.FutureSight.class, RETRO_ART));
        cards.add(new SetCardInfo("Gangrenous Goliath", 150, Rarity.RARE, mage.cards.g.GangrenousGoliath.class, RETRO_ART));
        cards.add(new SetCardInfo("Ghosthelm Courier", 85, Rarity.UNCOMMON, mage.cards.g.GhosthelmCourier.class, RETRO_ART));
        cards.add(new SetCardInfo("Gigapede", 264, Rarity.RARE, mage.cards.g.Gigapede.class, RETRO_ART));
        cards.add(new SetCardInfo("Glarecaster", 30, Rarity.RARE, mage.cards.g.Glarecaster.class, RETRO_ART));
        cards.add(new SetCardInfo("Glory Seeker", 31, Rarity.COMMON, mage.cards.g.GlorySeeker.class, RETRO_ART));
        cards.add(new SetCardInfo("Gluttonous Zombie", 151, Rarity.UNCOMMON, mage.cards.g.GluttonousZombie.class, RETRO_ART));
        cards.add(new SetCardInfo("Goblin Burrows", 318, Rarity.UNCOMMON, mage.cards.g.GoblinBurrows.class, RETRO_ART));
        cards.add(new SetCardInfo("Goblin Machinist", 204, Rarity.UNCOMMON, mage.cards.g.GoblinMachinist.class, RETRO_ART));
        cards.add(new SetCardInfo("Goblin Piledriver", 205, Rarity.RARE, mage.cards.g.GoblinPiledriver.class, RETRO_ART));
        cards.add(new SetCardInfo("Goblin Pyromancer", 206, Rarity.RARE, mage.cards.g.GoblinPyromancer.class, RETRO_ART));
        cards.add(new SetCardInfo("Goblin Sharpshooter", 207, Rarity.RARE, mage.cards.g.GoblinSharpshooter.class, RETRO_ART));
        cards.add(new SetCardInfo("Goblin Sky Raider", 208, Rarity.COMMON, mage.cards.g.GoblinSkyRaider.class, RETRO_ART));
        cards.add(new SetCardInfo("Goblin Sledder", 209, Rarity.COMMON, mage.cards.g.GoblinSledder.class, RETRO_ART));
        cards.add(new SetCardInfo("Goblin Taskmaster", 210, Rarity.COMMON, mage.cards.g.GoblinTaskmaster.class, RETRO_ART));
        cards.add(new SetCardInfo("Grand Coliseum", 319, Rarity.RARE, mage.cards.g.GrandColiseum.class, RETRO_ART));
        cards.add(new SetCardInfo("Grand Melee", 211, Rarity.RARE, mage.cards.g.GrandMelee.class, RETRO_ART));
        cards.add(new SetCardInfo("Grassland Crusader", 32, Rarity.COMMON, mage.cards.g.GrasslandCrusader.class, RETRO_ART));
        cards.add(new SetCardInfo("Gratuitous Violence", 212, Rarity.RARE, mage.cards.g.GratuitousViolence.class, RETRO_ART));
        cards.add(new SetCardInfo("Gravel Slinger", 33, Rarity.COMMON, mage.cards.g.GravelSlinger.class, RETRO_ART));
        cards.add(new SetCardInfo("Gravespawn Sovereign", 152, Rarity.RARE, mage.cards.g.GravespawnSovereign.class, RETRO_ART));
        cards.add(new SetCardInfo("Graxiplon", 86, Rarity.UNCOMMON, mage.cards.g.Graxiplon.class, RETRO_ART));
        cards.add(new SetCardInfo("Grinning Demon", 153, Rarity.RARE, mage.cards.g.GrinningDemon.class, RETRO_ART));
        cards.add(new SetCardInfo("Gustcloak Harrier", 34, Rarity.COMMON, mage.cards.g.GustcloakHarrier.class, RETRO_ART));
        cards.add(new SetCardInfo("Gustcloak Runner", 35, Rarity.COMMON, mage.cards.g.GustcloakRunner.class, RETRO_ART));
        cards.add(new SetCardInfo("Gustcloak Savior", 36, Rarity.RARE, mage.cards.g.GustcloakSavior.class, RETRO_ART));
        cards.add(new SetCardInfo("Gustcloak Sentinel", 37, Rarity.UNCOMMON, mage.cards.g.GustcloakSentinel.class, RETRO_ART));
        cards.add(new SetCardInfo("Gustcloak Skirmisher", 38, Rarity.UNCOMMON, mage.cards.g.GustcloakSkirmisher.class, RETRO_ART));
        cards.add(new SetCardInfo("Harsh Mercy", 39, Rarity.RARE, mage.cards.h.HarshMercy.class, RETRO_ART));
        cards.add(new SetCardInfo("Haunted Cadaver", 154, Rarity.COMMON, mage.cards.h.HauntedCadaver.class, RETRO_ART));
        cards.add(new SetCardInfo("Head Games", 155, Rarity.RARE, mage.cards.h.HeadGames.class, RETRO_ART));
        cards.add(new SetCardInfo("Headhunter", 156, Rarity.UNCOMMON, mage.cards.h.Headhunter.class, RETRO_ART));
        cards.add(new SetCardInfo("Heedless One", 265, Rarity.UNCOMMON, mage.cards.h.HeedlessOne.class, RETRO_ART));
        cards.add(new SetCardInfo("Hystrodon", 266, Rarity.RARE, mage.cards.h.Hystrodon.class, RETRO_ART));
        cards.add(new SetCardInfo("Imagecrafter", 87, Rarity.COMMON, mage.cards.i.Imagecrafter.class, RETRO_ART));
        cards.add(new SetCardInfo("Improvised Armor", 40, Rarity.UNCOMMON, mage.cards.i.ImprovisedArmor.class, RETRO_ART));
        cards.add(new SetCardInfo("Infest", 157, Rarity.UNCOMMON, mage.cards.i.Infest.class, RETRO_ART));
        cards.add(new SetCardInfo("Information Dealer", 88, Rarity.COMMON, mage.cards.i.InformationDealer.class, RETRO_ART));
        cards.add(new SetCardInfo("Inspirit", 41, Rarity.UNCOMMON, mage.cards.i.Inspirit.class, RETRO_ART));
        cards.add(new SetCardInfo("Insurrection", 213, Rarity.RARE, mage.cards.i.Insurrection.class, RETRO_ART));
        cards.add(new SetCardInfo("Invigorating Boon", 267, Rarity.UNCOMMON, mage.cards.i.InvigoratingBoon.class, RETRO_ART));
        cards.add(new SetCardInfo("Ironfist Crusher", 42, Rarity.UNCOMMON, mage.cards.i.IronfistCrusher.class, RETRO_ART));
        cards.add(new SetCardInfo("Island", 335, Rarity.LAND, mage.cards.basiclands.Island.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 336, Rarity.LAND, mage.cards.basiclands.Island.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 337, Rarity.LAND, mage.cards.basiclands.Island.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 338, Rarity.LAND, mage.cards.basiclands.Island.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Ixidor's Will", 90, Rarity.COMMON, mage.cards.i.IxidorsWill.class, RETRO_ART));
        cards.add(new SetCardInfo("Ixidor, Reality Sculptor", 89, Rarity.RARE, mage.cards.i.IxidorRealitySculptor.class, RETRO_ART));
        cards.add(new SetCardInfo("Jareth, Leonine Titan", 43, Rarity.RARE, mage.cards.j.JarethLeonineTitan.class, RETRO_ART));
        cards.add(new SetCardInfo("Kaboom!", 214, Rarity.RARE, mage.cards.k.Kaboom.class, RETRO_ART));
        cards.add(new SetCardInfo("Kamahl's Summons", 269, Rarity.UNCOMMON, mage.cards.k.KamahlsSummons.class, RETRO_ART));
        cards.add(new SetCardInfo("Kamahl, Fist of Krosa", 268, Rarity.RARE, mage.cards.k.KamahlFistOfKrosa.class, RETRO_ART));
        cards.add(new SetCardInfo("Krosan Colossus", 270, Rarity.RARE, mage.cards.k.KrosanColossus.class, RETRO_ART));
        cards.add(new SetCardInfo("Krosan Groundshaker", 271, Rarity.UNCOMMON, mage.cards.k.KrosanGroundshaker.class, RETRO_ART));
        cards.add(new SetCardInfo("Krosan Tusker", 272, Rarity.COMMON, mage.cards.k.KrosanTusker.class, RETRO_ART));
        cards.add(new SetCardInfo("Lavamancer's Skill", 215, Rarity.COMMON, mage.cards.l.LavamancersSkill.class, RETRO_ART));
        cards.add(new SetCardInfo("Lay Waste", 216, Rarity.COMMON, mage.cards.l.LayWaste.class, RETRO_ART));
        cards.add(new SetCardInfo("Leery Fogbeast", 273, Rarity.COMMON, mage.cards.l.LeeryFogbeast.class, RETRO_ART));
        cards.add(new SetCardInfo("Lightning Rift", 217, Rarity.UNCOMMON, mage.cards.l.LightningRift.class, RETRO_ART));
        cards.add(new SetCardInfo("Lonely Sandbar", 320, Rarity.COMMON, mage.cards.l.LonelySandbar.class, RETRO_ART));
        cards.add(new SetCardInfo("Mage's Guile", 91, Rarity.COMMON, mage.cards.m.MagesGuile.class, RETRO_ART));
        cards.add(new SetCardInfo("Mana Echoes", 218, Rarity.RARE, mage.cards.m.ManaEchoes.class, RETRO_ART));
        cards.add(new SetCardInfo("Meddle", 92, Rarity.UNCOMMON, mage.cards.m.Meddle.class, RETRO_ART));
        cards.add(new SetCardInfo("Menacing Ogre", 219, Rarity.RARE, mage.cards.m.MenacingOgre.class, RETRO_ART));
        cards.add(new SetCardInfo("Misery Charm", 158, Rarity.COMMON, mage.cards.m.MiseryCharm.class, RETRO_ART));
        cards.add(new SetCardInfo("Mistform Dreamer", 93, Rarity.COMMON, mage.cards.m.MistformDreamer.class, RETRO_ART));
        cards.add(new SetCardInfo("Mistform Mask", 94, Rarity.COMMON, mage.cards.m.MistformMask.class, RETRO_ART));
        cards.add(new SetCardInfo("Mistform Mutant", 95, Rarity.UNCOMMON, mage.cards.m.MistformMutant.class, RETRO_ART));
        cards.add(new SetCardInfo("Mistform Shrieker", 96, Rarity.UNCOMMON, mage.cards.m.MistformShrieker.class, RETRO_ART));
        cards.add(new SetCardInfo("Mistform Skyreaver", 97, Rarity.RARE, mage.cards.m.MistformSkyreaver.class, RETRO_ART));
        cards.add(new SetCardInfo("Mistform Stalker", 98, Rarity.UNCOMMON, mage.cards.m.MistformStalker.class, RETRO_ART));
        cards.add(new SetCardInfo("Mistform Wall", 99, Rarity.COMMON, mage.cards.m.MistformWall.class, RETRO_ART));
        cards.add(new SetCardInfo("Mobilization", 44, Rarity.RARE, mage.cards.m.Mobilization.class, RETRO_ART));
        cards.add(new SetCardInfo("Mountain", 343, Rarity.LAND, mage.cards.basiclands.Mountain.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 344, Rarity.LAND, mage.cards.basiclands.Mountain.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 345, Rarity.LAND, mage.cards.basiclands.Mountain.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 346, Rarity.LAND, mage.cards.basiclands.Mountain.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Mythic Proportions", 274, Rarity.RARE, mage.cards.m.MythicProportions.class, RETRO_ART));
        cards.add(new SetCardInfo("Nameless One", 100, Rarity.UNCOMMON, mage.cards.n.NamelessOne.class, RETRO_ART));
        cards.add(new SetCardInfo("Nantuko Husk", 159, Rarity.COMMON, mage.cards.n.NantukoHusk.class, RETRO_ART));
        cards.add(new SetCardInfo("Naturalize", 275, Rarity.COMMON, mage.cards.n.Naturalize.class, RETRO_ART));
        cards.add(new SetCardInfo("Nosy Goblin", 220, Rarity.COMMON, mage.cards.n.NosyGoblin.class, RETRO_ART));
        cards.add(new SetCardInfo("Nova Cleric", 45, Rarity.UNCOMMON, mage.cards.n.NovaCleric.class, RETRO_ART));
        cards.add(new SetCardInfo("Oblation", 46, Rarity.RARE, mage.cards.o.Oblation.class, RETRO_ART));
        cards.add(new SetCardInfo("Oversold Cemetery", 160, Rarity.RARE, mage.cards.o.OversoldCemetery.class, RETRO_ART));
        cards.add(new SetCardInfo("Overwhelming Instinct", 276, Rarity.UNCOMMON, mage.cards.o.OverwhelmingInstinct.class, RETRO_ART));
        cards.add(new SetCardInfo("Pacifism", 47, Rarity.COMMON, mage.cards.p.Pacifism.class, RETRO_ART));
        cards.add(new SetCardInfo("Patriarch's Bidding", 161, Rarity.RARE, mage.cards.p.PatriarchsBidding.class, RETRO_ART));
        cards.add(new SetCardInfo("Pearlspear Courier", 48, Rarity.UNCOMMON, mage.cards.p.PearlspearCourier.class, RETRO_ART));
        cards.add(new SetCardInfo("Peer Pressure", 101, Rarity.RARE, mage.cards.p.PeerPressure.class, RETRO_ART));
        cards.add(new SetCardInfo("Piety Charm", 49, Rarity.COMMON, mage.cards.p.PietyCharm.class, RETRO_ART));
        cards.add(new SetCardInfo("Pinpoint Avalanche", 221, Rarity.COMMON, mage.cards.p.PinpointAvalanche.class, RETRO_ART));
        cards.add(new SetCardInfo("Plains", 331, Rarity.LAND, mage.cards.basiclands.Plains.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 332, Rarity.LAND, mage.cards.basiclands.Plains.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 333, Rarity.LAND, mage.cards.basiclands.Plains.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 334, Rarity.LAND, mage.cards.basiclands.Plains.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Polluted Delta", 321, Rarity.RARE, mage.cards.p.PollutedDelta.class, RETRO_ART));
        cards.add(new SetCardInfo("Primal Boost", 277, Rarity.UNCOMMON, mage.cards.p.PrimalBoost.class, RETRO_ART));
        cards.add(new SetCardInfo("Profane Prayers", 162, Rarity.COMMON, mage.cards.p.ProfanePrayers.class, RETRO_ART));
        cards.add(new SetCardInfo("Prowling Pangolin", 163, Rarity.UNCOMMON, mage.cards.p.ProwlingPangolin.class, RETRO_ART));
        cards.add(new SetCardInfo("Psychic Trance", 102, Rarity.RARE, mage.cards.p.PsychicTrance.class, RETRO_ART));
        cards.add(new SetCardInfo("Quicksilver Dragon", 103, Rarity.RARE, mage.cards.q.QuicksilverDragon.class, RETRO_ART));
        cards.add(new SetCardInfo("Ravenous Baloth", 278, Rarity.RARE, mage.cards.r.RavenousBaloth.class, RETRO_ART));
        cards.add(new SetCardInfo("Read the Runes", 104, Rarity.RARE, mage.cards.r.ReadTheRunes.class, RETRO_ART));
        cards.add(new SetCardInfo("Reckless One", 222, Rarity.UNCOMMON, mage.cards.r.RecklessOne.class, RETRO_ART));
        cards.add(new SetCardInfo("Reminisce", 105, Rarity.UNCOMMON, mage.cards.r.Reminisce.class, RETRO_ART));
        cards.add(new SetCardInfo("Renewed Faith", 50, Rarity.COMMON, mage.cards.r.RenewedFaith.class, RETRO_ART));
        cards.add(new SetCardInfo("Righteous Cause", 51, Rarity.UNCOMMON, mage.cards.r.RighteousCause.class, RETRO_ART));
        cards.add(new SetCardInfo("Riptide Biologist", 106, Rarity.COMMON, mage.cards.r.RiptideBiologist.class, RETRO_ART));
        cards.add(new SetCardInfo("Riptide Chronologist", 107, Rarity.UNCOMMON, mage.cards.r.RiptideChronologist.class, RETRO_ART));
        cards.add(new SetCardInfo("Riptide Entrancer", 108, Rarity.RARE, mage.cards.r.RiptideEntrancer.class, RETRO_ART));
        cards.add(new SetCardInfo("Riptide Laboratory", 322, Rarity.RARE, mage.cards.r.RiptideLaboratory.class, RETRO_ART));
        cards.add(new SetCardInfo("Riptide Replicator", 309, Rarity.RARE, mage.cards.r.RiptideReplicator.class, RETRO_ART));
        cards.add(new SetCardInfo("Riptide Shapeshifter", 109, Rarity.UNCOMMON, mage.cards.r.RiptideShapeshifter.class, RETRO_ART));
        cards.add(new SetCardInfo("Risky Move", 223, Rarity.RARE, mage.cards.r.RiskyMove.class, RETRO_ART));
        cards.add(new SetCardInfo("Rorix Bladewing", 224, Rarity.RARE, mage.cards.r.RorixBladewing.class, RETRO_ART));
        cards.add(new SetCardInfo("Rotlung Reanimator", 164, Rarity.RARE, mage.cards.r.RotlungReanimator.class, RETRO_ART));
        cards.add(new SetCardInfo("Rummaging Wizard", 110, Rarity.UNCOMMON, mage.cards.r.RummagingWizard.class, RETRO_ART));
        cards.add(new SetCardInfo("Run Wild", 279, Rarity.UNCOMMON, mage.cards.r.RunWild.class, RETRO_ART));
        cards.add(new SetCardInfo("Sage Aven", 111, Rarity.COMMON, mage.cards.s.SageAven.class, RETRO_ART));
        cards.add(new SetCardInfo("Sandskin", 52, Rarity.COMMON, mage.cards.s.Sandskin.class, RETRO_ART));
        cards.add(new SetCardInfo("Screaming Seahawk", 112, Rarity.COMMON, mage.cards.s.ScreamingSeahawk.class, RETRO_ART));
        cards.add(new SetCardInfo("Screeching Buzzard", 165, Rarity.COMMON, mage.cards.s.ScreechingBuzzard.class, RETRO_ART));
        cards.add(new SetCardInfo("Sea's Claim", 113, Rarity.COMMON, mage.cards.s.SeasClaim.class, RETRO_ART));
        cards.add(new SetCardInfo("Searing Flesh", 225, Rarity.UNCOMMON, mage.cards.s.SearingFlesh.class, RETRO_ART));
        cards.add(new SetCardInfo("Seaside Haven", 323, Rarity.UNCOMMON, mage.cards.s.SeasideHaven.class, RETRO_ART));
        cards.add(new SetCardInfo("Secluded Steppe", 324, Rarity.COMMON, mage.cards.s.SecludedSteppe.class, RETRO_ART));
        cards.add(new SetCardInfo("Serpentine Basilisk", 280, Rarity.UNCOMMON, mage.cards.s.SerpentineBasilisk.class, RETRO_ART));
        cards.add(new SetCardInfo("Severed Legion", 166, Rarity.COMMON, mage.cards.s.SeveredLegion.class, RETRO_ART));
        cards.add(new SetCardInfo("Shade's Breath", 167, Rarity.UNCOMMON, mage.cards.s.ShadesBreath.class, RETRO_ART));
        cards.add(new SetCardInfo("Shaleskin Bruiser", 226, Rarity.UNCOMMON, mage.cards.s.ShaleskinBruiser.class, RETRO_ART));
        cards.add(new SetCardInfo("Shared Triumph", 53, Rarity.RARE, mage.cards.s.SharedTriumph.class, RETRO_ART));
        cards.add(new SetCardInfo("Shepherd of Rot", 168, Rarity.COMMON, mage.cards.s.ShepherdOfRot.class, RETRO_ART));
        cards.add(new SetCardInfo("Shieldmage Elder", 54, Rarity.UNCOMMON, mage.cards.s.ShieldmageElder.class, RETRO_ART));
        cards.add(new SetCardInfo("Shock", 227, Rarity.COMMON, mage.cards.s.Shock.class, RETRO_ART));
        cards.add(new SetCardInfo("Sigil of the New Dawn", 55, Rarity.RARE, mage.cards.s.SigilOfTheNewDawn.class, RETRO_ART));
        cards.add(new SetCardInfo("Silent Specter", 169, Rarity.RARE, mage.cards.s.SilentSpecter.class, RETRO_ART));
        cards.add(new SetCardInfo("Silklash Spider", 281, Rarity.RARE, mage.cards.s.SilklashSpider.class, RETRO_ART));
        cards.add(new SetCardInfo("Silvos, Rogue Elemental", 282, Rarity.RARE, mage.cards.s.SilvosRogueElemental.class, RETRO_ART));
        cards.add(new SetCardInfo("Skirk Commando", 228, Rarity.COMMON, mage.cards.s.SkirkCommando.class, RETRO_ART));
        cards.add(new SetCardInfo("Skirk Fire Marshal", 229, Rarity.RARE, mage.cards.s.SkirkFireMarshal.class, RETRO_ART));
        cards.add(new SetCardInfo("Skirk Prospector", 230, Rarity.COMMON, mage.cards.s.SkirkProspector.class, RETRO_ART));
        cards.add(new SetCardInfo("Skittish Valesk", 231, Rarity.UNCOMMON, mage.cards.s.SkittishValesk.class, RETRO_ART));
        cards.add(new SetCardInfo("Slate of Ancestry", 310, Rarity.RARE, mage.cards.s.SlateOfAncestry.class, RETRO_ART));
        cards.add(new SetCardInfo("Slice and Dice", 232, Rarity.UNCOMMON, mage.cards.s.SliceAndDice.class, RETRO_ART));
        cards.add(new SetCardInfo("Slipstream Eel", 114, Rarity.COMMON, mage.cards.s.SlipstreamEel.class, RETRO_ART));
        cards.add(new SetCardInfo("Smother", 170, Rarity.UNCOMMON, mage.cards.s.Smother.class, RETRO_ART));
        cards.add(new SetCardInfo("Snapping Thragg", 233, Rarity.UNCOMMON, mage.cards.s.SnappingThragg.class, RETRO_ART));
        cards.add(new SetCardInfo("Snarling Undorak", 283, Rarity.COMMON, mage.cards.s.SnarlingUndorak.class, RETRO_ART));
        cards.add(new SetCardInfo("Solar Blast", 234, Rarity.COMMON, mage.cards.s.SolarBlast.class, RETRO_ART));
        cards.add(new SetCardInfo("Soulless One", 171, Rarity.UNCOMMON, mage.cards.s.SoullessOne.class, RETRO_ART));
        cards.add(new SetCardInfo("Sparksmith", 235, Rarity.COMMON, mage.cards.s.Sparksmith.class, RETRO_ART));
        cards.add(new SetCardInfo("Spined Basher", 172, Rarity.COMMON, mage.cards.s.SpinedBasher.class, RETRO_ART));
        cards.add(new SetCardInfo("Spitfire Handler", 236, Rarity.UNCOMMON, mage.cards.s.SpitfireHandler.class, RETRO_ART));
        cards.add(new SetCardInfo("Spitting Gourna", 284, Rarity.COMMON, mage.cards.s.SpittingGourna.class, RETRO_ART));
        cards.add(new SetCardInfo("Spurred Wolverine", 237, Rarity.COMMON, mage.cards.s.SpurredWolverine.class, RETRO_ART));
        cards.add(new SetCardInfo("Spy Network", 115, Rarity.COMMON, mage.cards.s.SpyNetwork.class, RETRO_ART));
        cards.add(new SetCardInfo("Stag Beetle", 285, Rarity.RARE, mage.cards.s.StagBeetle.class, RETRO_ART));
        cards.add(new SetCardInfo("Standardize", 116, Rarity.RARE, mage.cards.s.Standardize.class, RETRO_ART));
        cards.add(new SetCardInfo("Starlit Sanctum", 325, Rarity.UNCOMMON, mage.cards.s.StarlitSanctum.class, RETRO_ART));
        cards.add(new SetCardInfo("Starstorm", 238, Rarity.RARE, mage.cards.s.Starstorm.class, RETRO_ART));
        cards.add(new SetCardInfo("Steely Resolve", 286, Rarity.RARE, mage.cards.s.SteelyResolve.class, RETRO_ART));
        cards.add(new SetCardInfo("Strongarm Tactics", 173, Rarity.RARE, mage.cards.s.StrongarmTactics.class, RETRO_ART));
        cards.add(new SetCardInfo("Sunfire Balm", 56, Rarity.UNCOMMON, mage.cards.s.SunfireBalm.class, RETRO_ART));
        cards.add(new SetCardInfo("Supreme Inquisitor", 117, Rarity.RARE, mage.cards.s.SupremeInquisitor.class, RETRO_ART));
        cards.add(new SetCardInfo("Swamp", 339, Rarity.LAND, mage.cards.basiclands.Swamp.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 340, Rarity.LAND, mage.cards.basiclands.Swamp.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 341, Rarity.LAND, mage.cards.basiclands.Swamp.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 342, Rarity.LAND, mage.cards.basiclands.Swamp.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Swat", 174, Rarity.COMMON, mage.cards.s.Swat.class, RETRO_ART));
        cards.add(new SetCardInfo("Symbiotic Beast", 287, Rarity.UNCOMMON, mage.cards.s.SymbioticBeast.class, RETRO_ART));
        cards.add(new SetCardInfo("Symbiotic Elf", 288, Rarity.COMMON, mage.cards.s.SymbioticElf.class, RETRO_ART));
        cards.add(new SetCardInfo("Symbiotic Wurm", 289, Rarity.RARE, mage.cards.s.SymbioticWurm.class, RETRO_ART));
        cards.add(new SetCardInfo("Syphon Mind", 175, Rarity.COMMON, mage.cards.s.SyphonMind.class, RETRO_ART));
        cards.add(new SetCardInfo("Syphon Soul", 176, Rarity.COMMON, mage.cards.s.SyphonSoul.class, RETRO_ART));
        cards.add(new SetCardInfo("Taunting Elf", 290, Rarity.COMMON, mage.cards.t.TauntingElf.class, RETRO_ART));
        cards.add(new SetCardInfo("Tempting Wurm", 291, Rarity.RARE, mage.cards.t.TemptingWurm.class, RETRO_ART));
        cards.add(new SetCardInfo("Tephraderm", 239, Rarity.RARE, mage.cards.t.Tephraderm.class, RETRO_ART));
        cards.add(new SetCardInfo("Thoughtbound Primoc", 240, Rarity.UNCOMMON, mage.cards.t.ThoughtboundPrimoc.class, RETRO_ART));
        cards.add(new SetCardInfo("Thrashing Mudspawn", 177, Rarity.UNCOMMON, mage.cards.t.ThrashingMudspawn.class, RETRO_ART));
        cards.add(new SetCardInfo("Threaten", 241, Rarity.UNCOMMON, mage.cards.t.Threaten.class, RETRO_ART));
        cards.add(new SetCardInfo("Thunder of Hooves", 242, Rarity.UNCOMMON, mage.cards.t.ThunderOfHooves.class, RETRO_ART));
        cards.add(new SetCardInfo("Towering Baloth", 292, Rarity.UNCOMMON, mage.cards.t.ToweringBaloth.class, RETRO_ART));
        cards.add(new SetCardInfo("Trade Secrets", 118, Rarity.RARE, mage.cards.t.TradeSecrets.class, RETRO_ART));
        cards.add(new SetCardInfo("Tranquil Thicket", 326, Rarity.COMMON, mage.cards.t.TranquilThicket.class, RETRO_ART));
        cards.add(new SetCardInfo("Treespring Lorian", 293, Rarity.COMMON, mage.cards.t.TreespringLorian.class, RETRO_ART));
        cards.add(new SetCardInfo("Tribal Golem", 311, Rarity.RARE, mage.cards.t.TribalGolem.class, RETRO_ART));
        cards.add(new SetCardInfo("Tribal Unity", 294, Rarity.UNCOMMON, mage.cards.t.TribalUnity.class, RETRO_ART));
        cards.add(new SetCardInfo("Trickery Charm", 119, Rarity.COMMON, mage.cards.t.TrickeryCharm.class, RETRO_ART));
        cards.add(new SetCardInfo("True Believer", 57, Rarity.RARE, mage.cards.t.TrueBeliever.class, RETRO_ART));
        cards.add(new SetCardInfo("Undead Gladiator", 178, Rarity.RARE, mage.cards.u.UndeadGladiator.class, RETRO_ART));
        cards.add(new SetCardInfo("Unholy Grotto", 327, Rarity.RARE, mage.cards.u.UnholyGrotto.class, RETRO_ART));
        cards.add(new SetCardInfo("Unified Strike", 58, Rarity.COMMON, mage.cards.u.UnifiedStrike.class, RETRO_ART));
        cards.add(new SetCardInfo("Venomspout Brackus", 295, Rarity.UNCOMMON, mage.cards.v.VenomspoutBrackus.class, RETRO_ART));
        cards.add(new SetCardInfo("Visara the Dreadful", 179, Rarity.RARE, mage.cards.v.VisaraTheDreadful.class, RETRO_ART));
        cards.add(new SetCardInfo("Vitality Charm", 296, Rarity.COMMON, mage.cards.v.VitalityCharm.class, RETRO_ART));
        cards.add(new SetCardInfo("Voice of the Woods", 297, Rarity.RARE, mage.cards.v.VoiceOfTheWoods.class, RETRO_ART));
        cards.add(new SetCardInfo("Voidmage Prodigy", 120, Rarity.RARE, mage.cards.v.VoidmageProdigy.class, RETRO_ART));
        cards.add(new SetCardInfo("Walking Desecration", 180, Rarity.UNCOMMON, mage.cards.w.WalkingDesecration.class, RETRO_ART));
        cards.add(new SetCardInfo("Wall of Mulch", 298, Rarity.UNCOMMON, mage.cards.w.WallOfMulch.class, RETRO_ART));
        cards.add(new SetCardInfo("Wave of Indifference", 243, Rarity.COMMON, mage.cards.w.WaveOfIndifference.class, RETRO_ART));
        cards.add(new SetCardInfo("Weathered Wayfarer", 59, Rarity.RARE, mage.cards.w.WeatheredWayfarer.class, RETRO_ART));
        cards.add(new SetCardInfo("Weird Harvest", 299, Rarity.RARE, mage.cards.w.WeirdHarvest.class, RETRO_ART));
        cards.add(new SetCardInfo("Wellwisher", 300, Rarity.COMMON, mage.cards.w.Wellwisher.class, RETRO_ART));
        cards.add(new SetCardInfo("Wheel and Deal", 121, Rarity.RARE, mage.cards.w.WheelAndDeal.class, RETRO_ART));
        cards.add(new SetCardInfo("Whipcorder", 60, Rarity.UNCOMMON, mage.cards.w.Whipcorder.class, RETRO_ART));
        cards.add(new SetCardInfo("Windswept Heath", 328, Rarity.RARE, mage.cards.w.WindsweptHeath.class, RETRO_ART));
        cards.add(new SetCardInfo("Wirewood Elf", 301, Rarity.COMMON, mage.cards.w.WirewoodElf.class, RETRO_ART));
        cards.add(new SetCardInfo("Wirewood Herald", 302, Rarity.COMMON, mage.cards.w.WirewoodHerald.class, RETRO_ART));
        cards.add(new SetCardInfo("Wirewood Lodge", 329, Rarity.UNCOMMON, mage.cards.w.WirewoodLodge.class, RETRO_ART));
        cards.add(new SetCardInfo("Wirewood Pride", 303, Rarity.COMMON, mage.cards.w.WirewoodPride.class, RETRO_ART));
        cards.add(new SetCardInfo("Wirewood Savage", 304, Rarity.COMMON, mage.cards.w.WirewoodSavage.class, RETRO_ART));
        cards.add(new SetCardInfo("Withering Hex", 181, Rarity.UNCOMMON, mage.cards.w.WitheringHex.class, RETRO_ART));
        cards.add(new SetCardInfo("Wooded Foothills", 330, Rarity.RARE, mage.cards.w.WoodedFoothills.class, RETRO_ART));
        cards.add(new SetCardInfo("Words of War", 244, Rarity.RARE, mage.cards.w.WordsOfWar.class, RETRO_ART));
        cards.add(new SetCardInfo("Words of Waste", 182, Rarity.RARE, mage.cards.w.WordsOfWaste.class, RETRO_ART));
        cards.add(new SetCardInfo("Words of Wilding", 305, Rarity.RARE, mage.cards.w.WordsOfWilding.class, RETRO_ART));
        cards.add(new SetCardInfo("Words of Wind", 122, Rarity.RARE, mage.cards.w.WordsOfWind.class, RETRO_ART));
        cards.add(new SetCardInfo("Words of Worship", 61, Rarity.RARE, mage.cards.w.WordsOfWorship.class, RETRO_ART));
        cards.add(new SetCardInfo("Wretched Anurid", 183, Rarity.COMMON, mage.cards.w.WretchedAnurid.class, RETRO_ART));
    }

    @Override
    public BoosterCollator createCollator() {
        return new OnslaughtCollator();
    }
}

// Booster collation info from https://www.lethe.xyz/mtg/collation/ons.html
// Using US collation
class OnslaughtCollator implements BoosterCollator {
    private final CardRun commonAC = new CardRun(true, "154", "50", "243", "77", "275", "138", "33", "188", "111", "304", "168", "49", "243", "114", "296", "166", "21", "210", "111", "253", "168", "29", "201", "106", "296", "172", "21", "202", "112", "253", "138", "18", "188", "99", "275", "154", "29", "215", "106", "304", "125", "18", "201", "114", "293", "166", "50", "202", "99", "302", "172", "33", "215", "77", "293", "125", "49", "210", "112", "302", "145", "9", "221", "70", "248", "162", "32", "209", "62", "260", "148", "9", "216", "94", "248", "124", "52", "220", "91", "301", "148", "19", "221", "62", "288", "145", "52", "194", "91", "290", "134", "31", "220", "74", "260", "162", "19", "209", "94", "288", "124", "31", "216", "70", "301", "134", "32", "194", "74", "290");
    private final CardRun commonB = new CardRun(true, "183", "35", "191", "113", "257", "176", "58", "208", "78", "256", "175", "25", "190", "88", "303", "139", "24", "191", "78", "255", "183", "23", "237", "119", "257", "139", "16", "208", "113", "300", "176", "23", "230", "90", "273", "158", "35", "196", "119", "255", "123", "25", "237", "115", "303", "175", "24", "190", "90", "300", "123", "58", "230", "115", "256", "158", "16", "196", "88", "273");
    private final CardRun commonD = new CardRun(true, "165", "26", "228", "68", "326", "312", "20", "227", "81", "284", "135", "34", "317", "68", "272", "159", "324", "234", "93", "246", "174", "26", "235", "320", "283", "159", "47", "317", "87", "272", "135", "20", "235", "81", "326", "165", "324", "227", "87", "284", "312", "34", "228", "93", "283", "174", "47", "234", "320", "246");
    private final CardRun uncommonA = new CardRun(true, "130", "41", "186", "98", "280", "157", "37", "193", "96", "292", "329", "126", "1", "241", "83", "251", "127", "41", "200", "95", "292", "315", "137", "37", "195", "83", "277", "128", "11", "233", "79", "262", "325", "170", "22", "203", "92", "277", "163", "11", "241", "85", "254", "323", "130", "38", "193", "76", "280", "129", "40", "203", "69", "298", "318", "137", "48", "242", "85", "262", "157", "22", "232", "96", "294", "329", "149", "40", "233", "98", "295", "170", "17", "186", "76", "287", "315", "149", "48", "232", "79", "295", "163", "8", "195", "82", "287", "325", "128", "1", "187", "95", "298", "126", "17", "242", "69", "251", "323", "127", "8", "187", "92", "254", "129", "38", "200", "82", "294", "318");
    private final CardRun uncommonB = new CardRun(true, "132", "12", "204", "107", "250", "144", "56", "226", "109", "249", "167", "15", "184", "65", "269", "171", "4", "199", "80", "258", "156", "51", "222", "73", "276", "180", "27", "184", "109", "258", "171", "5", "197", "110", "271", "177", "12", "217", "107", "279", "156", "54", "225", "64", "276", "131", "51", "231", "110", "252", "147", "27", "236", "100", "267", "167", "42", "225", "86", "265", "144", "54", "204", "63", "252", "131", "4", "240", "64", "269", "177", "45", "236", "63", "263", "151", "5", "199", "100", "279", "147", "45", "231", "105", "250", "181", "60", "217", "65", "267", "151", "56", "240", "105", "271", "180", "15", "197", "80", "249", "132", "60", "222", "86", "263", "181", "42", "226", "73", "265");
    // Artificial Evolution (rare, #67) not implemented, so removed from the run
    private final CardRun rare = new CardRun(false, "75", "153", "213", "245", "13", "118", "140", "39", "198", "286", "322", "30", "291", "244", "161", "101", "36", "308", "274", "212", "160", "66", "316", "43", "247", "207", "306", "164", "102", "28", "270", "214", "178", "84", "313", "3", "285", "185", "97", "136", "229", "282", "310", "53", "71", "182", "319", "238", "261", "55", "281", "141", "108", "307", "57", "297", "205", "143", "104", "330", "179", "61", "211", "103", "305", "173", "10", "189", "120", "268", "155", "327", "7", "223", "116", "278", "309", "146", "2", "218", "72", "289", "152", "328", "44", "239", "122", "264", "133", "46", "219", "117", "311", "259", "150", "321", "59", "192", "121", "266", "142", "14", "224", "299", "169", "6", "314", "206", "89");

    private final BoosterStructure AAAAABBBDDD = new BoosterStructure(
            commonAC, commonAC, commonAC, commonAC, commonAC,
            commonB, commonB, commonB,
            commonD, commonD, commonD
    );
    private final BoosterStructure AAAAAABBBDD = new BoosterStructure(
            commonAC, commonAC, commonAC, commonAC, commonAC, commonAC,
            commonB, commonB, commonB,
            commonD, commonD
    );
    private final BoosterStructure ABB = new BoosterStructure(uncommonA, uncommonB, uncommonB);
    private final BoosterStructure AAB = new BoosterStructure(uncommonA, uncommonA, uncommonB);

    private final BoosterStructure R1 = new BoosterStructure(rare);

    private final RarityConfiguration commonRuns = new RarityConfiguration(
            AAAAABBBDDD, AAAAAABBBDD
    );

    private final RarityConfiguration uncommonRuns = new RarityConfiguration(
            AAB, ABB
    );

    private final RarityConfiguration rareRuns = new RarityConfiguration(R1);

    @Override
    public List<String> makeBooster() {
        List<String> booster = new ArrayList<>();
        booster.addAll(commonRuns.getNext().makeRun());
        booster.addAll(uncommonRuns.getNext().makeRun());
        booster.addAll(rareRuns.getNext().makeRun());
        return booster;
    }
}
