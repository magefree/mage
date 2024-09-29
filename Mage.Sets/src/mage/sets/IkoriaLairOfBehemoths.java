package mage.sets;

import mage.cards.ExpansionSet;
import mage.cards.repository.CardInfo;
import mage.constants.Rarity;
import mage.constants.SetType;
import mage.collation.BoosterCollator;
import mage.collation.BoosterStructure;
import mage.collation.CardRun;
import mage.collation.RarityConfiguration;

import java.util.Arrays;
import java.util.List;

/**
 * @author TheElk801
 */
public final class IkoriaLairOfBehemoths extends ExpansionSet {

    public static final List<String> mutateNames = Arrays.asList(
            "Archipelagore",
            "Auspicious Starrix",
            "Boneyard Lurker",
            "Brokkos, Apex of Forever",
            "Cavern Whisperer",
            "Chittering Harvester",
            "Cloudpiercer",
            "Cubwarden",
            "Dirge Bat",
            "Dreamtail Heron",
            "Everquill Phoenix",
            "Gemrazer",
            "Glowstone Recluse",
            "Huntmaster Liger",
            "Illuna, Apex of Wishes",
            "Insatiable Hemophage",
            "Lore Drakkis",
            "Majestic Auricorn",
            "Migratory Greathorn",
            "Necropanther",
            "Nethroi, Apex of Death",
            "Parcelbeast",
            "Porcuparrot",
            "Pouncing Shoreshark",
            "Regal Leosaur",
            "Sea-Dasher Octopus",
            "Snapdax, Apex of the Hunt",
            "Trumpeting Gnarr",
            "Vadrok, Apex of Thunder",
            "Vulpikeet"
    );

    private static final IkoriaLairOfBehemoths instance = new IkoriaLairOfBehemoths();

    public static IkoriaLairOfBehemoths getInstance() {
        return instance;
    }

    private IkoriaLairOfBehemoths() {
        super("Ikoria: Lair of Behemoths", "IKO", ExpansionSet.buildDate(2020, 4, 24), SetType.EXPANSION);
        this.blockName = "Ikoria: Lair of Behemoths";
        this.hasBoosters = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        this.maxCardNumberInBooster = 274;

        // About half of boosters will have a gainland rather than a basic
        // Source: https://twitter.com/GavinVerhey/status/1248731315412717568
        this.ratioBoosterSpecialLand = 2;
        this.ratioBoosterSpecialLandNumerator = 1;

        cards.add(new SetCardInfo("Adaptive Shimmerer", 1, Rarity.COMMON, mage.cards.a.AdaptiveShimmerer.class));
        cards.add(new SetCardInfo("Adventurous Impulse", 142, Rarity.COMMON, mage.cards.a.AdventurousImpulse.class));
        cards.add(new SetCardInfo("Aegis Turtle", 39, Rarity.COMMON, mage.cards.a.AegisTurtle.class));
        cards.add(new SetCardInfo("Alert Heedbonder", 218, Rarity.UNCOMMON, mage.cards.a.AlertHeedbonder.class));
        cards.add(new SetCardInfo("Almighty Brushwagg", 143, Rarity.COMMON, mage.cards.a.AlmightyBrushwagg.class));
        cards.add(new SetCardInfo("Anticipate", 40, Rarity.COMMON, mage.cards.a.Anticipate.class));
        cards.add(new SetCardInfo("Archipelagore", 283, Rarity.UNCOMMON, mage.cards.a.Archipelagore.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Archipelagore", 41, Rarity.UNCOMMON, mage.cards.a.Archipelagore.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Auspicious Starrix", 144, Rarity.UNCOMMON, mage.cards.a.AuspiciousStarrix.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Auspicious Starrix", 294, Rarity.UNCOMMON, mage.cards.a.AuspiciousStarrix.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Avian Oddity", 42, Rarity.UNCOMMON, mage.cards.a.AvianOddity.class));
        cards.add(new SetCardInfo("Back for More", 177, Rarity.UNCOMMON, mage.cards.b.BackForMore.class));
        cards.add(new SetCardInfo("Barrier Breach", 145, Rarity.UNCOMMON, mage.cards.b.BarrierBreach.class));
        cards.add(new SetCardInfo("Bastion of Remembrance", 73, Rarity.UNCOMMON, mage.cards.b.BastionOfRemembrance.class));
        cards.add(new SetCardInfo("Blade Banish", 4, Rarity.COMMON, mage.cards.b.BladeBanish.class));
        cards.add(new SetCardInfo("Blazing Volley", 107, Rarity.COMMON, mage.cards.b.BlazingVolley.class));
        cards.add(new SetCardInfo("Blisterspit Gremlin", 108, Rarity.COMMON, mage.cards.b.BlisterspitGremlin.class));
        cards.add(new SetCardInfo("Blitz Leech", 74, Rarity.COMMON, mage.cards.b.BlitzLeech.class));
        cards.add(new SetCardInfo("Blitz of the Thunder-Raptor", 109, Rarity.UNCOMMON, mage.cards.b.BlitzOfTheThunderRaptor.class));
        cards.add(new SetCardInfo("Blood Curdle", 75, Rarity.COMMON, mage.cards.b.BloodCurdle.class));
        cards.add(new SetCardInfo("Bloodfell Caves", 243, Rarity.COMMON, mage.cards.b.BloodfellCaves.class));
        cards.add(new SetCardInfo("Blossoming Sands", 244, Rarity.COMMON, mage.cards.b.BlossomingSands.class));
        cards.add(new SetCardInfo("Bonders' Enclave", 245, Rarity.RARE, mage.cards.b.BondersEnclave.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bonders' Enclave", 363, Rarity.RARE, mage.cards.b.BondersEnclave.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Boneyard Lurker", 178, Rarity.UNCOMMON, mage.cards.b.BoneyardLurker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Boneyard Lurker", 298, Rarity.UNCOMMON, mage.cards.b.BoneyardLurker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Boon of the Wish-Giver", 43, Rarity.UNCOMMON, mage.cards.b.BoonOfTheWishGiver.class));
        cards.add(new SetCardInfo("Boot Nipper", 76, Rarity.COMMON, mage.cards.b.BootNipper.class));
        cards.add(new SetCardInfo("Bristling Boar", 146, Rarity.COMMON, mage.cards.b.BristlingBoar.class));
        cards.add(new SetCardInfo("Bushmeat Poacher", 77, Rarity.COMMON, mage.cards.b.BushmeatPoacher.class));
        cards.add(new SetCardInfo("Call of the Death-Dweller", 78, Rarity.UNCOMMON, mage.cards.c.CallOfTheDeathDweller.class));
        cards.add(new SetCardInfo("Capture Sphere", 44, Rarity.COMMON, mage.cards.c.CaptureSphere.class));
        cards.add(new SetCardInfo("Cathartic Reunion", 110, Rarity.COMMON, mage.cards.c.CatharticReunion.class));
        cards.add(new SetCardInfo("Cavern Whisperer", 287, Rarity.COMMON, mage.cards.c.CavernWhisperer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cavern Whisperer", 79, Rarity.COMMON, mage.cards.c.CavernWhisperer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Channeled Force", 180, Rarity.UNCOMMON, mage.cards.c.ChanneledForce.class));
        cards.add(new SetCardInfo("Charge of the Forever-Beast", 147, Rarity.UNCOMMON, mage.cards.c.ChargeOfTheForeverBeast.class));
        cards.add(new SetCardInfo("Checkpoint Officer", 5, Rarity.COMMON, mage.cards.c.CheckpointOfficer.class));
        cards.add(new SetCardInfo("Chevill, Bane of Monsters", 181, Rarity.MYTHIC, mage.cards.c.ChevillBaneOfMonsters.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Chevill, Bane of Monsters", 330, Rarity.MYTHIC, mage.cards.c.ChevillBaneOfMonsters.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Chittering Harvester", 288, Rarity.UNCOMMON, mage.cards.c.ChitteringHarvester.class));
        cards.add(new SetCardInfo("Chittering Harvester", 80, Rarity.UNCOMMON, mage.cards.c.ChitteringHarvester.class));
        cards.add(new SetCardInfo("Clash of Titans", 111, Rarity.UNCOMMON, mage.cards.c.ClashOfTitans.class));
        cards.add(new SetCardInfo("Cloudpiercer", 112, Rarity.COMMON, mage.cards.c.Cloudpiercer.class));
        cards.add(new SetCardInfo("Cloudpiercer", 291, Rarity.COMMON, mage.cards.c.Cloudpiercer.class));
        cards.add(new SetCardInfo("Colossification", 148, Rarity.RARE, mage.cards.c.Colossification.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Colossification", 327, Rarity.RARE, mage.cards.c.Colossification.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Colossification", 364, Rarity.RARE, mage.cards.c.Colossification.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Convolute", 45, Rarity.COMMON, mage.cards.c.Convolute.class));
        cards.add(new SetCardInfo("Coordinated Charge", 6, Rarity.COMMON, mage.cards.c.CoordinatedCharge.class));
        cards.add(new SetCardInfo("Corpse Churn", 81, Rarity.COMMON, mage.cards.c.CorpseChurn.class));
        cards.add(new SetCardInfo("Crystacean", 46, Rarity.COMMON, mage.cards.c.Crystacean.class));
        cards.add(new SetCardInfo("Crystalline Giant", 234, Rarity.RARE, mage.cards.c.CrystallineGiant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Crystalline Giant", 361, Rarity.RARE, mage.cards.c.CrystallineGiant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Crystalline Giant", 387, Rarity.RARE, mage.cards.c.CrystallineGiant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cubwarden", 279, Rarity.RARE, mage.cards.c.Cubwarden.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cubwarden", 7, Rarity.RARE, mage.cards.c.Cubwarden.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cunning Nightbonder", 219, Rarity.UNCOMMON, mage.cards.c.CunningNightbonder.class));
        cards.add(new SetCardInfo("Dark Bargain", 82, Rarity.COMMON, mage.cards.d.DarkBargain.class));
        cards.add(new SetCardInfo("Daysquad Marshal", 8, Rarity.COMMON, mage.cards.d.DaysquadMarshal.class));
        cards.add(new SetCardInfo("Dead Weight", 83, Rarity.COMMON, mage.cards.d.DeadWeight.class));
        cards.add(new SetCardInfo("Death's Oasis", 182, Rarity.RARE, mage.cards.d.DeathsOasis.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Death's Oasis", 331, Rarity.RARE, mage.cards.d.DeathsOasis.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dire Tactics", 183, Rarity.UNCOMMON, mage.cards.d.DireTactics.class));
        cards.add(new SetCardInfo("Dirge Bat", 289, Rarity.RARE, mage.cards.d.DirgeBat.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dirge Bat", 386, Rarity.RARE, mage.cards.d.DirgeBat.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dirge Bat", 84, Rarity.RARE, mage.cards.d.DirgeBat.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dismal Backwater", 246, Rarity.COMMON, mage.cards.d.DismalBackwater.class));
        cards.add(new SetCardInfo("Divine Arrow", 9, Rarity.COMMON, mage.cards.d.DivineArrow.class));
        cards.add(new SetCardInfo("Drannith Healer", 10, Rarity.COMMON, mage.cards.d.DrannithHealer.class));
        cards.add(new SetCardInfo("Drannith Magistrate", 11, Rarity.RARE, mage.cards.d.DrannithMagistrate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Drannith Magistrate", 314, Rarity.RARE, mage.cards.d.DrannithMagistrate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Drannith Stinger", 113, Rarity.COMMON, mage.cards.d.DrannithStinger.class));
        cards.add(new SetCardInfo("Dreamtail Heron", 284, Rarity.COMMON, mage.cards.d.DreamtailHeron.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dreamtail Heron", 47, Rarity.COMMON, mage.cards.d.DreamtailHeron.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Durable Coilbug", 85, Rarity.COMMON, mage.cards.d.DurableCoilbug.class));
        cards.add(new SetCardInfo("Duskfang Mentor", 86, Rarity.UNCOMMON, mage.cards.d.DuskfangMentor.class));
        cards.add(new SetCardInfo("Easy Prey", 87, Rarity.UNCOMMON, mage.cards.e.EasyPrey.class));
        cards.add(new SetCardInfo("Eerie Ultimatum", 184, Rarity.RARE, mage.cards.e.EerieUltimatum.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Eerie Ultimatum", 332, Rarity.RARE, mage.cards.e.EerieUltimatum.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Emergent Ultimatum", 185, Rarity.RARE, mage.cards.e.EmergentUltimatum.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Emergent Ultimatum", 333, Rarity.RARE, mage.cards.e.EmergentUltimatum.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Escape Protocol", 48, Rarity.UNCOMMON, mage.cards.e.EscapeProtocol.class));
        cards.add(new SetCardInfo("Essence Scatter", 49, Rarity.COMMON, mage.cards.e.EssenceScatter.class));
        cards.add(new SetCardInfo("Essence Symbiote", 149, Rarity.COMMON, mage.cards.e.EssenceSymbiote.class));
        cards.add(new SetCardInfo("Everquill Phoenix", 114, Rarity.RARE, mage.cards.e.EverquillPhoenix.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Everquill Phoenix", 292, Rarity.RARE, mage.cards.e.EverquillPhoenix.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Everquill Phoenix", 374, Rarity.RARE, mage.cards.e.EverquillPhoenix.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Evolving Wilds", 247, Rarity.COMMON, mage.cards.e.EvolvingWilds.class));
        cards.add(new SetCardInfo("Excavation Mole", 150, Rarity.COMMON, mage.cards.e.ExcavationMole.class));
        cards.add(new SetCardInfo("Extinction Event", 321, Rarity.RARE, mage.cards.e.ExtinctionEvent.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Extinction Event", 88, Rarity.RARE, mage.cards.e.ExtinctionEvent.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Exuberant Wolfbear", 151, Rarity.UNCOMMON, mage.cards.e.ExuberantWolfbear.class));
        cards.add(new SetCardInfo("Facet Reader", 50, Rarity.COMMON, mage.cards.f.FacetReader.class));
        cards.add(new SetCardInfo("Farfinder", 2, Rarity.COMMON, mage.cards.f.Farfinder.class));
        cards.add(new SetCardInfo("Ferocious Tigorilla", 115, Rarity.COMMON, mage.cards.f.FerociousTigorilla.class));
        cards.add(new SetCardInfo("Fertilid", 152, Rarity.COMMON, mage.cards.f.Fertilid.class));
        cards.add(new SetCardInfo("Fiend Artisan", 220, Rarity.MYTHIC, mage.cards.f.FiendArtisan.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fiend Artisan", 350, Rarity.MYTHIC, mage.cards.f.FiendArtisan.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fight as One", 12, Rarity.UNCOMMON, mage.cards.f.FightAsOne.class));
        cards.add(new SetCardInfo("Fire Prophecy", 116, Rarity.COMMON, mage.cards.f.FireProphecy.class));
        cards.add(new SetCardInfo("Flame Spill", 117, Rarity.UNCOMMON, mage.cards.f.FlameSpill.class));
        cards.add(new SetCardInfo("Flourishing Fox", 13, Rarity.UNCOMMON, mage.cards.f.FlourishingFox.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Flourishing Fox", 365, Rarity.UNCOMMON, mage.cards.f.FlourishingFox.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Flycatcher Giraffid", 153, Rarity.COMMON, mage.cards.f.FlycatcherGiraffid.class));
        cards.add(new SetCardInfo("Footfall Crater", 118, Rarity.UNCOMMON, mage.cards.f.FootfallCrater.class));
        cards.add(new SetCardInfo("Forbidden Friendship", 119, Rarity.COMMON, mage.cards.f.ForbiddenFriendship.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forbidden Friendship", 367, Rarity.COMMON, mage.cards.f.ForbiddenFriendship.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 272, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 273, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 274, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Frenzied Raptor", 120, Rarity.COMMON, mage.cards.f.FrenziedRaptor.class));
        cards.add(new SetCardInfo("Frillscare Mentor", 121, Rarity.UNCOMMON, mage.cards.f.FrillscareMentor.class));
        cards.add(new SetCardInfo("Frondland Felidar", 186, Rarity.RARE, mage.cards.f.FrondlandFelidar.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Frondland Felidar", 334, Rarity.RARE, mage.cards.f.FrondlandFelidar.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Frost Lynx", 51, Rarity.COMMON, mage.cards.f.FrostLynx.class));
        cards.add(new SetCardInfo("Frostveil Ambush", 52, Rarity.COMMON, mage.cards.f.FrostveilAmbush.class));
        cards.add(new SetCardInfo("Fully Grown", 154, Rarity.COMMON, mage.cards.f.FullyGrown.class));
        cards.add(new SetCardInfo("Garrison Cat", 14, Rarity.COMMON, mage.cards.g.GarrisonCat.class));
        cards.add(new SetCardInfo("Gemrazer", 155, Rarity.RARE, mage.cards.g.Gemrazer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gemrazer", 295, Rarity.RARE, mage.cards.g.Gemrazer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gemrazer", 376, Rarity.RARE, mage.cards.g.Gemrazer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("General Kudro of Drannith", 187, Rarity.MYTHIC, mage.cards.g.GeneralKudroOfDrannith.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("General Kudro of Drannith", 335, Rarity.MYTHIC, mage.cards.g.GeneralKudroOfDrannith.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("General's Enforcer", 188, Rarity.UNCOMMON, mage.cards.g.GeneralsEnforcer.class));
        cards.add(new SetCardInfo("Genesis Ultimatum", 189, Rarity.RARE, mage.cards.g.GenesisUltimatum.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Genesis Ultimatum", 336, Rarity.RARE, mage.cards.g.GenesisUltimatum.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Glimmerbell", 53, Rarity.COMMON, mage.cards.g.Glimmerbell.class));
        cards.add(new SetCardInfo("Gloom Pangolin", 89, Rarity.COMMON, mage.cards.g.GloomPangolin.class));
        cards.add(new SetCardInfo("Glowstone Recluse", 156, Rarity.UNCOMMON, mage.cards.g.GlowstoneRecluse.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Glowstone Recluse", 296, Rarity.UNCOMMON, mage.cards.g.GlowstoneRecluse.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Go for Blood", 122, Rarity.COMMON, mage.cards.g.GoForBlood.class));
        cards.add(new SetCardInfo("Greater Sandwurm", 157, Rarity.COMMON, mage.cards.g.GreaterSandwurm.class));
        cards.add(new SetCardInfo("Grimdancer", 90, Rarity.UNCOMMON, mage.cards.g.Grimdancer.class));
        cards.add(new SetCardInfo("Gust of Wind", 54, Rarity.COMMON, mage.cards.g.GustOfWind.class));
        cards.add(new SetCardInfo("Gyruda, Doom of Depths", 221, Rarity.RARE, mage.cards.g.GyrudaDoomOfDepths.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gyruda, Doom of Depths", 351, Rarity.RARE, mage.cards.g.GyrudaDoomOfDepths.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gyruda, Doom of Depths", 384, Rarity.RARE, mage.cards.g.GyrudaDoomOfDepths.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hampering Snare", 55, Rarity.COMMON, mage.cards.h.HamperingSnare.class));
        cards.add(new SetCardInfo("Heartless Act", 366, Rarity.UNCOMMON, mage.cards.h.HeartlessAct.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Heartless Act", 91, Rarity.UNCOMMON, mage.cards.h.HeartlessAct.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Heightened Reflexes", 123, Rarity.COMMON, mage.cards.h.HeightenedReflexes.class));
        cards.add(new SetCardInfo("Helica Glider", 15, Rarity.COMMON, mage.cards.h.HelicaGlider.class));
        cards.add(new SetCardInfo("Honey Mammoth", 158, Rarity.COMMON, mage.cards.h.HoneyMammoth.class));
        cards.add(new SetCardInfo("Hornbash Mentor", 159, Rarity.UNCOMMON, mage.cards.h.HornbashMentor.class));
        cards.add(new SetCardInfo("Humble Naturalist", 160, Rarity.COMMON, mage.cards.h.HumbleNaturalist.class));
        cards.add(new SetCardInfo("Hunted Nightmare", 322, Rarity.RARE, mage.cards.h.HuntedNightmare.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hunted Nightmare", 92, Rarity.RARE, mage.cards.h.HuntedNightmare.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Huntmaster Liger", 16, Rarity.UNCOMMON, mage.cards.h.HuntmasterLiger.class));
        cards.add(new SetCardInfo("Huntmaster Liger", 280, Rarity.UNCOMMON, mage.cards.h.HuntmasterLiger.class));
        cards.add(new SetCardInfo("Huntmaster Liger", 370, Rarity.UNCOMMON, mage.cards.h.HuntmasterLiger.class));
        cards.add(new SetCardInfo("Illuna, Apex of Wishes", 190, Rarity.MYTHIC, mage.cards.i.IllunaApexOfWishes.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Illuna, Apex of Wishes", 300, Rarity.MYTHIC, mage.cards.i.IllunaApexOfWishes.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Illuna, Apex of Wishes", 379, Rarity.MYTHIC, mage.cards.i.IllunaApexOfWishes.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Imposing Vantasaur", 17, Rarity.COMMON, mage.cards.i.ImposingVantasaur.class));
        cards.add(new SetCardInfo("Indatha Crystal", 235, Rarity.UNCOMMON, mage.cards.i.IndathaCrystal.class));
        cards.add(new SetCardInfo("Indatha Triome", 248, Rarity.RARE, mage.cards.i.IndathaTriome.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Indatha Triome", 309, Rarity.RARE, mage.cards.i.IndathaTriome.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Insatiable Hemophage", 290, Rarity.UNCOMMON, mage.cards.i.InsatiableHemophage.class));
        cards.add(new SetCardInfo("Insatiable Hemophage", 93, Rarity.UNCOMMON, mage.cards.i.InsatiableHemophage.class));
        cards.add(new SetCardInfo("Inspired Ultimatum", 191, Rarity.RARE, mage.cards.i.InspiredUltimatum.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Inspired Ultimatum", 337, Rarity.RARE, mage.cards.i.InspiredUltimatum.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 263, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 264, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 265, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ivy Elemental", 161, Rarity.UNCOMMON, mage.cards.i.IvyElemental.class));
        cards.add(new SetCardInfo("Jegantha, the Wellspring", 222, Rarity.RARE, mage.cards.j.JeganthaTheWellspring.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jegantha, the Wellspring", 352, Rarity.RARE, mage.cards.j.JeganthaTheWellspring.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jubilant Skybonder", 223, Rarity.UNCOMMON, mage.cards.j.JubilantSkybonder.class));
        cards.add(new SetCardInfo("Jungle Hollow", 249, Rarity.COMMON, mage.cards.j.JungleHollow.class));
        cards.add(new SetCardInfo("Kaheera, the Orphanguard", 224, Rarity.RARE, mage.cards.k.KaheeraTheOrphanguard.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kaheera, the Orphanguard", 353, Rarity.RARE, mage.cards.k.KaheeraTheOrphanguard.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Keensight Mentor", 18, Rarity.UNCOMMON, mage.cards.k.KeensightMentor.class));
        cards.add(new SetCardInfo("Keep Safe", 56, Rarity.COMMON, mage.cards.k.KeepSafe.class));
        cards.add(new SetCardInfo("Keruga, the Macrosage", 225, Rarity.RARE, mage.cards.k.KerugaTheMacrosage.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Keruga, the Macrosage", 354, Rarity.RARE, mage.cards.k.KerugaTheMacrosage.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ketria Crystal", 236, Rarity.UNCOMMON, mage.cards.k.KetriaCrystal.class));
        cards.add(new SetCardInfo("Ketria Triome", 250, Rarity.RARE, mage.cards.k.KetriaTriome.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ketria Triome", 310, Rarity.RARE, mage.cards.k.KetriaTriome.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kinnan, Bonder Prodigy", 192, Rarity.MYTHIC, mage.cards.k.KinnanBonderProdigy.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kinnan, Bonder Prodigy", 338, Rarity.MYTHIC, mage.cards.k.KinnanBonderProdigy.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kogla, the Titan Ape", 162, Rarity.RARE, mage.cards.k.KoglaTheTitanApe.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kogla, the Titan Ape", 328, Rarity.RARE, mage.cards.k.KoglaTheTitanApe.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Labyrinth Raptor", 193, Rarity.RARE, mage.cards.l.LabyrinthRaptor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Labyrinth Raptor", 339, Rarity.RARE, mage.cards.l.LabyrinthRaptor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lava Serpent", 124, Rarity.COMMON, mage.cards.l.LavaSerpent.class));
        cards.add(new SetCardInfo("Lavabrink Venturer", 19, Rarity.RARE, mage.cards.l.LavabrinkVenturer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lavabrink Venturer", 315, Rarity.RARE, mage.cards.l.LavabrinkVenturer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lead the Stampede", 163, Rarity.UNCOMMON, mage.cards.l.LeadTheStampede.class));
        cards.add(new SetCardInfo("Light of Hope", 20, Rarity.COMMON, mage.cards.l.LightOfHope.class));
        cards.add(new SetCardInfo("Lore Drakkis", 194, Rarity.UNCOMMON, mage.cards.l.LoreDrakkis.class));
        cards.add(new SetCardInfo("Lore Drakkis", 301, Rarity.UNCOMMON, mage.cards.l.LoreDrakkis.class));
        cards.add(new SetCardInfo("Lukka, Coppercoat Outcast", 125, Rarity.MYTHIC, mage.cards.l.LukkaCoppercoatOutcast.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lukka, Coppercoat Outcast", 276, Rarity.MYTHIC, mage.cards.l.LukkaCoppercoatOutcast.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Luminous Broodmoth", 21, Rarity.MYTHIC, mage.cards.l.LuminousBroodmoth.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Luminous Broodmoth", 316, Rarity.MYTHIC, mage.cards.l.LuminousBroodmoth.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Luminous Broodmoth", 371, Rarity.MYTHIC, mage.cards.l.LuminousBroodmoth.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lurking Deadeye", 94, Rarity.COMMON, mage.cards.l.LurkingDeadeye.class));
        cards.add(new SetCardInfo("Lurrus of the Dream-Den", 226, Rarity.RARE, mage.cards.l.LurrusOfTheDreamDen.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lurrus of the Dream-Den", 355, Rarity.RARE, mage.cards.l.LurrusOfTheDreamDen.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lutri, the Spellchaser", 227, Rarity.RARE, mage.cards.l.LutriTheSpellchaser.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lutri, the Spellchaser", 356, Rarity.RARE, mage.cards.l.LutriTheSpellchaser.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Majestic Auricorn", 22, Rarity.UNCOMMON, mage.cards.m.MajesticAuricorn.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Majestic Auricorn", 281, Rarity.UNCOMMON, mage.cards.m.MajesticAuricorn.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Maned Serval", 23, Rarity.COMMON, mage.cards.m.ManedServal.class));
        cards.add(new SetCardInfo("Memory Leak", 95, Rarity.COMMON, mage.cards.m.MemoryLeak.class));
        cards.add(new SetCardInfo("Migration Path", 164, Rarity.UNCOMMON, mage.cards.m.MigrationPath.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Migration Path", 368, Rarity.UNCOMMON, mage.cards.m.MigrationPath.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Migratory Greathorn", 165, Rarity.COMMON, mage.cards.m.MigratoryGreathorn.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Migratory Greathorn", 297, Rarity.COMMON, mage.cards.m.MigratoryGreathorn.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Momentum Rumbler", 126, Rarity.UNCOMMON, mage.cards.m.MomentumRumbler.class));
        cards.add(new SetCardInfo("Monstrous Step", 166, Rarity.UNCOMMON, mage.cards.m.MonstrousStep.class));
        cards.add(new SetCardInfo("Mosscoat Goriak", 167, Rarity.COMMON, mage.cards.m.MosscoatGoriak.class));
        cards.add(new SetCardInfo("Mountain", 269, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 270, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 271, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mutual Destruction", 96, Rarity.COMMON, mage.cards.m.MutualDestruction.class));
        cards.add(new SetCardInfo("Mysterious Egg", 3, Rarity.COMMON, mage.cards.m.MysteriousEgg.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mysterious Egg", 385, Rarity.COMMON, mage.cards.m.MysteriousEgg.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mystic Subdual", 57, Rarity.UNCOMMON, mage.cards.m.MysticSubdual.class));
        cards.add(new SetCardInfo("Mythos of Brokkos", 168, Rarity.RARE, mage.cards.m.MythosOfBrokkos.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mythos of Brokkos", 329, Rarity.RARE, mage.cards.m.MythosOfBrokkos.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mythos of Illuna", 318, Rarity.RARE, mage.cards.m.MythosOfIlluna.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mythos of Illuna", 58, Rarity.RARE, mage.cards.m.MythosOfIlluna.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mythos of Nethroi", 323, Rarity.RARE, mage.cards.m.MythosOfNethroi.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mythos of Nethroi", 97, Rarity.RARE, mage.cards.m.MythosOfNethroi.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mythos of Snapdax", 24, Rarity.RARE, mage.cards.m.MythosOfSnapdax.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mythos of Snapdax", 317, Rarity.RARE, mage.cards.m.MythosOfSnapdax.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mythos of Vadrok", 127, Rarity.RARE, mage.cards.m.MythosOfVadrok.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mythos of Vadrok", 324, Rarity.RARE, mage.cards.m.MythosOfVadrok.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Narset of the Ancient Way", 195, Rarity.MYTHIC, mage.cards.n.NarsetOfTheAncientWay.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Narset of the Ancient Way", 278, Rarity.MYTHIC, mage.cards.n.NarsetOfTheAncientWay.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Necropanther", 196, Rarity.UNCOMMON, mage.cards.n.Necropanther.class));
        cards.add(new SetCardInfo("Necropanther", 302, Rarity.UNCOMMON, mage.cards.n.Necropanther.class));
        cards.add(new SetCardInfo("Nethroi, Apex of Death", 197, Rarity.MYTHIC, mage.cards.n.NethroiApexOfDeath.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nethroi, Apex of Death", 303, Rarity.MYTHIC, mage.cards.n.NethroiApexOfDeath.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nethroi, Apex of Death", 380, Rarity.MYTHIC, mage.cards.n.NethroiApexOfDeath.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Neutralize", 59, Rarity.UNCOMMON, mage.cards.n.Neutralize.class));
        cards.add(new SetCardInfo("Nightsquad Commando", 98, Rarity.COMMON, mage.cards.n.NightsquadCommando.class));
        cards.add(new SetCardInfo("Obosh, the Preypiercer", 228, Rarity.RARE, mage.cards.o.OboshThePreypiercer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Obosh, the Preypiercer", 357, Rarity.RARE, mage.cards.o.OboshThePreypiercer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Of One Mind", 60, Rarity.COMMON, mage.cards.o.OfOneMind.class));
        cards.add(new SetCardInfo("Offspring's Revenge", 198, Rarity.RARE, mage.cards.o.OffspringsRevenge.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Offspring's Revenge", 340, Rarity.RARE, mage.cards.o.OffspringsRevenge.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ominous Seas", 61, Rarity.UNCOMMON, mage.cards.o.OminousSeas.class));
        cards.add(new SetCardInfo("Pacifism", 25, Rarity.COMMON, mage.cards.p.Pacifism.class));
        cards.add(new SetCardInfo("Parcelbeast", 199, Rarity.UNCOMMON, mage.cards.p.Parcelbeast.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Parcelbeast", 304, Rarity.UNCOMMON, mage.cards.p.Parcelbeast.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Patagia Tiger", 26, Rarity.COMMON, mage.cards.p.PatagiaTiger.class));
        cards.add(new SetCardInfo("Perimeter Sergeant", 27, Rarity.COMMON, mage.cards.p.PerimeterSergeant.class));
        cards.add(new SetCardInfo("Phase Dolphin", 62, Rarity.COMMON, mage.cards.p.PhaseDolphin.class));
        cards.add(new SetCardInfo("Plains", 260, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 261, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 262, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plummet", 169, Rarity.COMMON, mage.cards.p.Plummet.class));
        cards.add(new SetCardInfo("Pollywog Symbiote", 372, Rarity.UNCOMMON, mage.cards.p.PollywogSymbiote.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Pollywog Symbiote", 63, Rarity.UNCOMMON, mage.cards.p.PollywogSymbiote.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Porcuparrot", 128, Rarity.UNCOMMON, mage.cards.p.Porcuparrot.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Porcuparrot", 293, Rarity.UNCOMMON, mage.cards.p.Porcuparrot.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Pouncing Shoreshark", 285, Rarity.UNCOMMON, mage.cards.p.PouncingShoreshark.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Pouncing Shoreshark", 64, Rarity.UNCOMMON, mage.cards.p.PouncingShoreshark.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Prickly Marmoset", 129, Rarity.COMMON, mage.cards.p.PricklyMarmoset.class));
        cards.add(new SetCardInfo("Primal Empathy", 200, Rarity.UNCOMMON, mage.cards.p.PrimalEmpathy.class));
        cards.add(new SetCardInfo("Proud Wildbonder", 229, Rarity.UNCOMMON, mage.cards.p.ProudWildbonder.class));
        cards.add(new SetCardInfo("Pyroceratops", 130, Rarity.COMMON, mage.cards.p.Pyroceratops.class));
        cards.add(new SetCardInfo("Quartzwood Crasher", 201, Rarity.RARE, mage.cards.q.QuartzwoodCrasher.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Quartzwood Crasher", 341, Rarity.RARE, mage.cards.q.QuartzwoodCrasher.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Raking Claws", 131, Rarity.COMMON, mage.cards.r.RakingClaws.class));
        cards.add(new SetCardInfo("Ram Through", 170, Rarity.COMMON, mage.cards.r.RamThrough.class));
        cards.add(new SetCardInfo("Raugrin Crystal", 238, Rarity.UNCOMMON, mage.cards.r.RaugrinCrystal.class));
        cards.add(new SetCardInfo("Raugrin Triome", 251, Rarity.RARE, mage.cards.r.RaugrinTriome.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Raugrin Triome", 311, Rarity.RARE, mage.cards.r.RaugrinTriome.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Reconnaissance Mission", 65, Rarity.UNCOMMON, mage.cards.r.ReconnaissanceMission.class));
        cards.add(new SetCardInfo("Regal Leosaur", 202, Rarity.UNCOMMON, mage.cards.r.RegalLeosaur.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Regal Leosaur", 305, Rarity.UNCOMMON, mage.cards.r.RegalLeosaur.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Reptilian Reflection", 132, Rarity.UNCOMMON, mage.cards.r.ReptilianReflection.class));
        cards.add(new SetCardInfo("Rielle, the Everwise", 203, Rarity.MYTHIC, mage.cards.r.RielleTheEverwise.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rielle, the Everwise", 342, Rarity.MYTHIC, mage.cards.r.RielleTheEverwise.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rooting Moloch", 133, Rarity.UNCOMMON, mage.cards.r.RootingMoloch.class));
        cards.add(new SetCardInfo("Rugged Highlands", 252, Rarity.COMMON, mage.cards.r.RuggedHighlands.class));
        cards.add(new SetCardInfo("Ruinous Ultimatum", 204, Rarity.RARE, mage.cards.r.RuinousUltimatum.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ruinous Ultimatum", 343, Rarity.RARE, mage.cards.r.RuinousUltimatum.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rumbling Rockslide", 134, Rarity.COMMON, mage.cards.r.RumblingRockslide.class));
        cards.add(new SetCardInfo("Sanctuary Lockdown", 28, Rarity.UNCOMMON, mage.cards.s.SanctuaryLockdown.class));
        cards.add(new SetCardInfo("Sanctuary Smasher", 135, Rarity.UNCOMMON, mage.cards.s.SanctuarySmasher.class));
        cards.add(new SetCardInfo("Savai Crystal", 239, Rarity.UNCOMMON, mage.cards.s.SavaiCrystal.class));
        cards.add(new SetCardInfo("Savai Sabertooth", 29, Rarity.COMMON, mage.cards.s.SavaiSabertooth.class));
        cards.add(new SetCardInfo("Savai Thundermane", 205, Rarity.UNCOMMON, mage.cards.s.SavaiThundermane.class));
        cards.add(new SetCardInfo("Savai Triome", 253, Rarity.RARE, mage.cards.s.SavaiTriome.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Savai Triome", 312, Rarity.RARE, mage.cards.s.SavaiTriome.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Scoured Barrens", 254, Rarity.COMMON, mage.cards.s.ScouredBarrens.class));
        cards.add(new SetCardInfo("Sea-Dasher Octopus", 286, Rarity.RARE, mage.cards.s.SeaDasherOctopus.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sea-Dasher Octopus", 66, Rarity.RARE, mage.cards.s.SeaDasherOctopus.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Serrated Scorpion", 99, Rarity.COMMON, mage.cards.s.SerratedScorpion.class));
        cards.add(new SetCardInfo("Shark Typhoon", 319, Rarity.RARE, mage.cards.s.SharkTyphoon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shark Typhoon", 67, Rarity.RARE, mage.cards.s.SharkTyphoon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shredded Sails", 136, Rarity.COMMON, mage.cards.s.ShreddedSails.class));
        cards.add(new SetCardInfo("Skull Prophet", 206, Rarity.UNCOMMON, mage.cards.s.SkullProphet.class));
        cards.add(new SetCardInfo("Skycat Sovereign", 207, Rarity.RARE, mage.cards.s.SkycatSovereign.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Skycat Sovereign", 344, Rarity.RARE, mage.cards.s.SkycatSovereign.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sleeper Dart", 240, Rarity.COMMON, mage.cards.s.SleeperDart.class));
        cards.add(new SetCardInfo("Slitherwisp", 208, Rarity.RARE, mage.cards.s.Slitherwisp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Slitherwisp", 345, Rarity.RARE, mage.cards.s.Slitherwisp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Snapdax, Apex of the Hunt", 209, Rarity.MYTHIC, mage.cards.s.SnapdaxApexOfTheHunt.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Snapdax, Apex of the Hunt", 306, Rarity.MYTHIC, mage.cards.s.SnapdaxApexOfTheHunt.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Snapdax, Apex of the Hunt", 381, Rarity.MYTHIC, mage.cards.s.SnapdaxApexOfTheHunt.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Snare Tactician", 30, Rarity.COMMON, mage.cards.s.SnareTactician.class));
        cards.add(new SetCardInfo("Solid Footing", 31, Rarity.COMMON, mage.cards.s.SolidFooting.class));
        cards.add(new SetCardInfo("Song of Creation", 210, Rarity.RARE, mage.cards.s.SongOfCreation.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Song of Creation", 346, Rarity.RARE, mage.cards.s.SongOfCreation.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sonorous Howlbonder", 230, Rarity.UNCOMMON, mage.cards.s.SonorousHowlbonder.class));
        cards.add(new SetCardInfo("Spelleater Wolverine", 137, Rarity.COMMON, mage.cards.s.SpelleaterWolverine.class));
        cards.add(new SetCardInfo("Splendor Mare", 32, Rarity.UNCOMMON, mage.cards.s.SplendorMare.class));
        cards.add(new SetCardInfo("Spontaneous Flight", 33, Rarity.COMMON, mage.cards.s.SpontaneousFlight.class));
        cards.add(new SetCardInfo("Springjaw Trap", 241, Rarity.COMMON, mage.cards.s.SpringjawTrap.class));
        cards.add(new SetCardInfo("Sprite Dragon", 211, Rarity.UNCOMMON, mage.cards.s.SpriteDragon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sprite Dragon", 369, Rarity.UNCOMMON, mage.cards.s.SpriteDragon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sprite Dragon", 382, Rarity.UNCOMMON, mage.cards.s.SpriteDragon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Startling Development", 68, Rarity.COMMON, mage.cards.s.StartlingDevelopment.class));
        cards.add(new SetCardInfo("Stormwild Capridor", 34, Rarity.UNCOMMON, mage.cards.s.StormwildCapridor.class));
        cards.add(new SetCardInfo("Sudden Spinnerets", 171, Rarity.COMMON, mage.cards.s.SuddenSpinnerets.class));
        cards.add(new SetCardInfo("Suffocating Fumes", 100, Rarity.COMMON, mage.cards.s.SuffocatingFumes.class));
        cards.add(new SetCardInfo("Survivors' Bond", 172, Rarity.COMMON, mage.cards.s.SurvivorsBond.class));
        cards.add(new SetCardInfo("Swallow Whole", 35, Rarity.UNCOMMON, mage.cards.s.SwallowWhole.class));
        cards.add(new SetCardInfo("Swamp", 266, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 267, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 268, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swiftwater Cliffs", 255, Rarity.COMMON, mage.cards.s.SwiftwaterCliffs.class));
        cards.add(new SetCardInfo("Tentative Connection", 138, Rarity.COMMON, mage.cards.t.TentativeConnection.class));
        cards.add(new SetCardInfo("The Ozolith", 237, Rarity.RARE, mage.cards.t.TheOzolith.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Ozolith", 362, Rarity.RARE, mage.cards.t.TheOzolith.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thieving Otter", 69, Rarity.COMMON, mage.cards.t.ThievingOtter.class));
        cards.add(new SetCardInfo("Thornwood Falls", 256, Rarity.COMMON, mage.cards.t.ThornwoodFalls.class));
        cards.add(new SetCardInfo("Thwart the Enemy", 173, Rarity.COMMON, mage.cards.t.ThwartTheEnemy.class));
        cards.add(new SetCardInfo("Titanoth Rex", 174, Rarity.UNCOMMON, mage.cards.t.TitanothRex.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Titanoth Rex", 377, Rarity.UNCOMMON, mage.cards.t.TitanothRex.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Titans' Nest", 212, Rarity.RARE, mage.cards.t.TitansNest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Titans' Nest", 347, Rarity.RARE, mage.cards.t.TitansNest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tranquil Cove", 257, Rarity.COMMON, mage.cards.t.TranquilCove.class));
        cards.add(new SetCardInfo("Trumpeting Gnarr", 213, Rarity.UNCOMMON, mage.cards.t.TrumpetingGnarr.class));
        cards.add(new SetCardInfo("Trumpeting Gnarr", 307, Rarity.UNCOMMON, mage.cards.t.TrumpetingGnarr.class));
        cards.add(new SetCardInfo("Umori, the Collector", 231, Rarity.RARE, mage.cards.u.UmoriTheCollector.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Umori, the Collector", 358, Rarity.RARE, mage.cards.u.UmoriTheCollector.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Unbreakable Bond", 101, Rarity.UNCOMMON, mage.cards.u.UnbreakableBond.class));
        cards.add(new SetCardInfo("Unexpected Fangs", 102, Rarity.COMMON, mage.cards.u.UnexpectedFangs.class));
        cards.add(new SetCardInfo("Unlikely Aid", 103, Rarity.COMMON, mage.cards.u.UnlikelyAid.class));
        cards.add(new SetCardInfo("Unpredictable Cyclone", 139, Rarity.RARE, mage.cards.u.UnpredictableCyclone.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Unpredictable Cyclone", 325, Rarity.RARE, mage.cards.u.UnpredictableCyclone.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vadrok, Apex of Thunder", 214, Rarity.MYTHIC, mage.cards.v.VadrokApexOfThunder.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vadrok, Apex of Thunder", 308, Rarity.MYTHIC, mage.cards.v.VadrokApexOfThunder.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vadrok, Apex of Thunder", 383, Rarity.MYTHIC, mage.cards.v.VadrokApexOfThunder.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Valiant Rescuer", 36, Rarity.UNCOMMON, mage.cards.v.ValiantRescuer.class));
        cards.add(new SetCardInfo("Vivien, Monsters' Advocate", 175, Rarity.MYTHIC, mage.cards.v.VivienMonstersAdvocate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vivien, Monsters' Advocate", 277, Rarity.MYTHIC, mage.cards.v.VivienMonstersAdvocate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Void Beckoner", 104, Rarity.UNCOMMON, mage.cards.v.VoidBeckoner.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Void Beckoner", 373, Rarity.UNCOMMON, mage.cards.v.VoidBeckoner.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Voracious Greatshark", 320, Rarity.RARE, mage.cards.v.VoraciousGreatshark.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Voracious Greatshark", 70, Rarity.RARE, mage.cards.v.VoraciousGreatshark.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vulpikeet", 282, Rarity.COMMON, mage.cards.v.Vulpikeet.class));
        cards.add(new SetCardInfo("Vulpikeet", 37, Rarity.COMMON, mage.cards.v.Vulpikeet.class));
        cards.add(new SetCardInfo("Weaponize the Monsters", 140, Rarity.UNCOMMON, mage.cards.w.WeaponizeTheMonsters.class));
        cards.add(new SetCardInfo("Whirlwind of Thought", 215, Rarity.RARE, mage.cards.w.WhirlwindOfThought.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Whirlwind of Thought", 348, Rarity.RARE, mage.cards.w.WhirlwindOfThought.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Whisper Squad", 105, Rarity.COMMON, mage.cards.w.WhisperSquad.class));
        cards.add(new SetCardInfo("Will of the All-Hunter", 38, Rarity.UNCOMMON, mage.cards.w.WillOfTheAllHunter.class));
        cards.add(new SetCardInfo("Wilt", 176, Rarity.COMMON, mage.cards.w.Wilt.class));
        cards.add(new SetCardInfo("Wind-Scarred Crag", 258, Rarity.COMMON, mage.cards.w.WindScarredCrag.class));
        cards.add(new SetCardInfo("Wingfold Pteron", 71, Rarity.COMMON, mage.cards.w.WingfoldPteron.class));
        cards.add(new SetCardInfo("Wingspan Mentor", 72, Rarity.UNCOMMON, mage.cards.w.WingspanMentor.class));
        cards.add(new SetCardInfo("Winota, Joiner of Forces", 216, Rarity.MYTHIC, mage.cards.w.WinotaJoinerOfForces.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Winota, Joiner of Forces", 349, Rarity.MYTHIC, mage.cards.w.WinotaJoinerOfForces.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Yidaro, Wandering Monster", 141, Rarity.RARE, mage.cards.y.YidaroWanderingMonster.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Yidaro, Wandering Monster", 326, Rarity.RARE, mage.cards.y.YidaroWanderingMonster.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Yidaro, Wandering Monster", 375, Rarity.RARE, mage.cards.y.YidaroWanderingMonster.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Yorion, Sky Nomad", 232, Rarity.RARE, mage.cards.y.YorionSkyNomad.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Yorion, Sky Nomad", 359, Rarity.RARE, mage.cards.y.YorionSkyNomad.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Zagoth Crystal", 242, Rarity.UNCOMMON, mage.cards.z.ZagothCrystal.class));
        cards.add(new SetCardInfo("Zagoth Mamba", 106, Rarity.UNCOMMON, mage.cards.z.ZagothMamba.class));
        cards.add(new SetCardInfo("Zagoth Triome", 259, Rarity.RARE, mage.cards.z.ZagothTriome.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Zagoth Triome", 313, Rarity.RARE, mage.cards.z.ZagothTriome.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Zenith Flare", 217, Rarity.UNCOMMON, mage.cards.z.ZenithFlare.class));
        cards.add(new SetCardInfo("Zilortha, Strength Incarnate", 275, Rarity.MYTHIC, mage.cards.z.ZilorthaStrengthIncarnate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Zirda, the Dawnwaker", 233, Rarity.RARE, mage.cards.z.ZirdaTheDawnwaker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Zirda, the Dawnwaker", 360, Rarity.RARE, mage.cards.z.ZirdaTheDawnwaker.class, NON_FULL_USE_VARIOUS));

        cards.removeIf(setCardInfo -> mutateNames.contains(setCardInfo.getName())); // remove when mutate is implemented
    }

    @Override
    protected List<CardInfo> findSpecialCardsByRarity(Rarity rarity) {
        List<CardInfo> cardInfos = super.findSpecialCardsByRarity(rarity);
        if (rarity == Rarity.LAND) {
            // Evolving Wilds is a normal common
            cardInfos.removeIf(cardInfo -> "Evolving Wilds".equals(cardInfo.getName()));
        }
        return cardInfos;
    }

   @Override
   public BoosterCollator createCollator() {
       return new IkoriaLairOfBehemothsCollator();
   }
}

// Booster collation info from https://www.lethe.xyz/mtg/collation/iko.html
// Using US collation for commons and uncommons
class IkoriaLairOfBehemothsCollator implements BoosterCollator {
    private final CardRun commonA = new CardRun(true, "69", "137", "14", "62", "110", "9", "53", "130", "23", "54", "129", "15", "44", "124", "4", "56", "119", "30", "60", "113", "17", "68", "120", "5", "69", "122", "33", "71", "138", "31", "52", "137", "15", "51", "115", "27", "62", "130", "4", "44", "113", "30", "60", "124", "23", "53", "110", "14", "56", "122", "9", "71", "120", "17", "54", "115", "5", "52", "129", "33", "68", "119", "31", "51", "138", "27");
    private final CardRun commonB = new CardRun(true, "152", "99", "170", "74", "176", "77", "142", "105", "171", "96", "154", "103", "146", "83", "143", "85", "158", "287", "160", "77", "176", "94", "142", "82", "170", "99", "172", "74", "152", "105", "171", "83", "143", "79", "160", "96", "154", "85", "146", "103", "176", "94", "158", "99", "152", "74", "142", "82", "172", "77", "170", "105", "154", "83", "171", "79", "143", "96", "160", "82", "172", "103", "146", "94", "158", "85");
    private final CardRun commonC1 = new CardRun(true, "134", "45", "3", "81", "153", "25", "108", "49", "98", "169", "10", "131", "55", "75", "173", "26", "123", "240", "45", "102", "149", "131", "247", "95", "49", "157", "25", "108", "50", "98", "153", "20", "134", "39", "3", "81", "173", "26", "136", "55", "247", "75", "169", "10", "123", "39", "102", "149", "20", "136", "50", "240", "95", "157", "241");
    private final CardRun commonC2 = new CardRun(true, "150", "76", "40", "116", "1", "37", "165", "89", "284", "107", "8", "46", "150", "100", "2", "112", "6", "297", "89", "47", "1", "37", "167", "76", "40", "107", "8", "2", "6", "150", "100", "46", "116", "29", "40", "112", "282", "167", "76", "1", "46", "116", "29", "165", "89", "107", "29", "8", "167", "100", "47", "6", "291", "2", "241");
    private final CardRun uncommonA = new CardRun(true, "104", "57", "18", "87", "72", "206", "16", "126", "147", "13", "42", "78", "213", "164", "38", "285", "200", "35", "133", "159", "218", "12", "111", "217", "174", "22", "59", "211", "28", "140", "166", "202", "65", "132", "229", "151", "18", "72", "307", "16", "147", "36", "230", "57", "118", "206", "164", "12", "64", "223", "104", "126", "166", "200", "42", "13", "202", "174", "132", "59", "230", "38", "111", "159", "229", "65", "133", "211", "151", "281", "57", "217", "35", "140", "147", "28", "64", "280", "223", "164", "36", "72", "206", "18", "118", "166", "305", "42", "126", "200", "159", "13", "59", "213", "12", "133", "174", "211", "65", "132", "217", "35", "87", "111", "230", "22", "78", "140", "229", "104", "38", "218", "151", "87", "28", "118", "218", "78", "36", "223");
    private final CardRun uncommonB = new CardRun(true, "73", "238", "194", "91", "121", "48", "80", "177", "63", "235", "117", "219", "145", "106", "205", "109", "236", "43", "32", "93", "34", "298", "161", "61", "101", "90", "188", "239", "135", "199", "163", "183", "283", "235", "180", "293", "156", "48", "106", "144", "288", "242", "177", "121", "86", "302", "91", "63", "238", "73", "205", "161", "34", "90", "194", "236", "101", "304", "43", "145", "109", "32", "93", "239", "117", "219", "61", "135", "178", "106", "163", "188", "128", "156", "86", "183", "41", "235", "73", "91", "301", "238", "63", "177", "80", "145", "294", "196", "242", "43", "199", "121", "48", "180", "32", "161", "236", "117", "219", "163", "109", "205", "101", "290", "188", "90", "239", "61", "144", "180", "242", "41", "178", "135", "34", "196", "128", "296", "86", "183");
    // aleternative for Japanese Collation for commons and uncommons
    // private final CardRun commonA = new CardRun(true, "131", "152", "247", "160", "154", "136", "172", "103", "170", "143", "105", "167", "33", "158", "102", "153", "3", "142", "241", "171", "173", "138", "146", "100", "173", "136", "142", "241", "157", "102", "146", "3", "171", "172", "105", "160", "131", "153", "33", "152", "176", "137", "167", "100", "158", "247", "170", "154", "103", "143", "138", "153", "241", "157", "146", "131", "176", "103", "142", "160", "137", "172", "3", "171", "105", "152", "136", "167", "247", "173", "102", "170", "154", "100", "158", "33", "143", "137", "173", "33", "146", "138", "143", "105", "160", "247", "154", "102", "157", "152", "103", "176", "170", "136", "171", "3", "158", "138", "167", "153", "131", "172", "241", "142", "137", "176", "100", "157");
    // private final CardRun commonB = new CardRun(true, "53", "17", "108", "60", "10", "39", "29", "51", "1", "31", "50", "69", "20", "240", "52", "5", "71", "89", "4", "60", "44", "6", "108", "62", "15", "46", "23", "68", "10", "55", "9", "45", "17", "56", "107", "30", "40", "53", "14", "169", "46", "27", "44", "4", "39", "31", "46", "10", "53", "6", "240", "69", "23", "55", "29", "71", "108", "15", "50", "52", "27", "89", "62", "5", "56", "169", "17", "45", "54", "14", "107", "40", "9", "60", "1", "30", "68", "51", "20", "1", "71", "4", "54", "6", "56", "20", "62", "169", "27", "45", "14", "44", "5", "51", "68", "15", "52", "107", "9", "54", "40", "31", "89", "69", "30", "55", "23", "50", "240", "29", "39");
    // private final CardRun commonC = new CardRun(true, "129", "74", "165", "110", "99", "116", "284", "94", "113", "75", "124", "96", "149", "119", "85", "26", "115", "76", "83", "134", "130", "77", "8", "120", "95", "150", "123", "98", "25", "291", "82", "76", "124", "2", "74", "116", "95", "165", "129", "85", "47", "120", "79", "26", "130", "99", "49", "115", "81", "25", "122", "96", "150", "110", "79", "8", "112", "81", "149", "123", "75", "77", "113", "282", "94", "134", "83", "8", "130", "98", "116", "74", "47", "123", "95", "149", "110", "76", "82", "115", "26", "99", "122", "150", "287", "134", "77", "49", "124", "85", "2", "119", "75", "112", "83", "25", "113", "96", "297", "119", "94", "37", "129", "81", "49", "120", "98", "37", "122", "82", "2");
    // private final CardRun uncommonA = new CardRun(true, "174", "42", "126", "72", "104", "242", "38", "61", "36", "151", "239", "140", "163", "63", "161", "28", "159", "86", "35", "133", "34", "132", "59", "18", "164", "43", "236", "12", "135", "147", "57", "101", "48", "87", "145", "73", "72", "166", "235", "13", "140", "65", "106", "126", "38", "239", "174", "78", "238", "104", "163", "42", "28", "63", "101", "242", "35", "61", "36", "151", "48", "147", "133", "161", "18", "159", "86", "43", "34", "164", "236", "132", "59", "87", "38", "135", "242", "12", "145", "57", "73", "72", "140", "42", "13", "166", "239", "35", "235", "65", "126", "163", "106", "18", "59", "238", "174", "78", "63", "104", "161", "86", "28", "48", "101", "61", "132", "151", "36", "147", "135", "164", "133", "34", "159", "236", "12", "43", "166", "72", "174", "65", "38", "87", "242", "73", "145", "106", "57", "126", "42", "140", "235", "13", "239", "104", "35", "63", "86", "161", "59", "163", "18", "78", "147", "238", "28", "151", "132", "159", "135", "61", "101", "48", "36", "43", "164", "12", "235", "133", "236", "166", "34", "73", "65", "145", "78", "42", "174", "72", "87", "126", "38", "242", "106", "36", "151", "57", "140", "239", "13", "161", "104", "61", "35", "86", "132", "63", "163", "59", "18", "238", "164", "34", "147", "28", "236", "101", "135", "159", "48", "133", "43", "12", "145", "73", "166", "65", "235", "106", "57", "13", "87", "238", "78");
    // private final CardRun uncommonB = new CardRun(true, "294", "223", "121", "180", "177", "118", "194", "283", "117", "211", "196", "217", "111", "93", "22", "298", "229", "80", "219", "200", "32", "296", "230", "90", "202", "205", "206", "218", "91", "188", "280", "199", "213", "128", "64", "109", "223", "118", "183", "80", "217", "144", "200", "117", "196", "121", "90", "281", "177", "180", "41", "301", "111", "211", "202", "229", "188", "64", "205", "290", "178", "206", "156", "304", "16", "219", "32", "230", "293", "213", "91", "223", "109", "218", "121", "211", "144", "302", "111", "180", "117", "41", "118", "288", "183", "217", "194", "177", "229", "200", "305", "90", "22", "91", "230", "32", "218", "199", "285", "206", "16", "188", "128", "93", "205", "307", "178", "156", "183", "109", "219");
    private final CardRun rare = new CardRun(false, "245", "245", "179", "181", "148", "148", "234", "234", "7", "7", "182", "182", "84", "84", "11", "11", "184", "184", "185", "185", "114", "114", "88", "88", "220", "186", "186", "155", "155", "187", "189", "189", "221", "221", "92", "92", "190", "248", "248", "191", "191", "222", "222", "224", "224", "225", "225", "250", "250", "192", "162", "162", "193", "193", "19", "19", "125", "21", "226", "226", "227", "227", "168", "168", "58", "58", "97", "97", "24", "24", "127", "127", "195", "197", "228", "228", "198", "198", "201", "201", "251", "251", "203", "204", "204", "253", "253", "66", "66", "67", "67", "207", "207", "208", "208", "209", "210", "210", "237", "237", "212", "212", "231", "231", "139", "139", "214", "175", "70", "70", "215", "215", "216", "141", "141", "232", "232", "259", "259", "275y", "233", "233");
    // uses 2:3 for individual basic:nonbasic
    private final CardRun land = new CardRun(false, "243", "243", "243", "244", "244", "244", "246", "246", "246", "249", "249", "249", "252", "252", "252", "254", "254", "254", "255", "255", "255", "256", "256", "256", "257", "257", "257", "258", "258", "258", "260", "260", "261", "261", "262", "262", "263", "263", "264", "264", "265", "265", "266", "266", "267", "267", "268", "268", "269", "269", "270", "270", "271", "271", "272", "272", "273", "273", "274", "274");

    private final BoosterStructure AABB111111 = new BoosterStructure(
            commonA, commonA,
            commonB, commonB,
            commonC1, commonC1, commonC1, commonC1, commonC1, commonC1
    );
    private final BoosterStructure AAABB11111 = new BoosterStructure(
            commonA, commonA, commonA,
            commonB, commonB,
            commonC1, commonC1, commonC1, commonC1, commonC1
    );
    private final BoosterStructure AAAABB2222 = new BoosterStructure(
            commonA, commonA, commonA, commonA,
            commonB, commonB,
            commonC2, commonC2, commonC2, commonC2
    );
    private final BoosterStructure AAAABBB222 = new BoosterStructure(
            commonA, commonA, commonA, commonA,
            commonB, commonB, commonB,
            commonC2, commonC2, commonC2
    );
    private final BoosterStructure AAAABBBB22 = new BoosterStructure(
            commonA, commonA, commonA, commonA,
            commonB, commonB, commonB, commonB,
            commonC2, commonC2
    );
    // aleternative for Japanese Collation for commons
    // private final BoosterStructure AABBBBCCCC = new BoosterStructure(
            // commonA, commonA,
            // commonB, commonB, commonB, commonB,
            // commonC, commonC, commonC, commonC
    // );
    // private final BoosterStructure AAABBBCCCC = new BoosterStructure(
            // commonA, commonA, commonA,
            // commonB, commonB, commonB,
            // commonC, commonC, commonC, commonC
    // );
    // private final BoosterStructure AAABBBBCCC = new BoosterStructure(
            // commonA, commonA, commonA,
            // commonB, commonB, commonB, commonB,
            // commonC, commonC, commonC
    // );
    private final BoosterStructure AAB = new BoosterStructure(uncommonA, uncommonA, uncommonB);
    private final BoosterStructure ABB = new BoosterStructure(uncommonA, uncommonB, uncommonB);
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
            AABBBBCCCC, AABBBBCCCC, AABBBBCCCC, AABBBBCCCC, AABBBBCCCC,
            AABBBBCCCC, AABBBBCCCC, AABBBBCCCC, AABBBBCCCC, AABBBBCCCC,
            AABBBBCCCC, AABBBBCCCC, AABBBBCCCC, AABBBBCCCC, AABBBBCCCC,
            AABBBBCCCC, AABBBBCCCC, AABBBBCCCC, AABBBBCCCC, AABBBBCCCC,
            AABBBBCCCC, AABBBBCCCC, AABBBBCCCC, AABBBBCCCC, AABBBBCCCC,
            AABBBBCCCC, AABBBBCCCC, AABBBBCCCC, AABBBBCCCC, AABBBBCCCC,
            AABBBBCCCC, AABBBBCCCC, AABBBBCCCC,
            AAABBBCCCC, AAABBBCCCC, AAABBBCCCC, AAABBBCCCC, AAABBBCCCC,
            AAABBBCCCC, AAABBBCCCC, AAABBBCCCC, AAABBBCCCC, AAABBBCCCC,
            AAABBBCCCC, AAABBBCCCC, AAABBBCCCC, AAABBBCCCC, AAABBBCCCC,
            AAABBBCCCC, AAABBBCCCC, AAABBBCCCC, AAABBBCCCC, AAABBBCCCC,
            AAABBBCCCC, AAABBBCCCC, AAABBBCCCC, AAABBBCCCC, AAABBBCCCC,
            AAABBBCCCC, AAABBBCCCC, AAABBBCCCC, AAABBBCCCC, AAABBBCCCC,
            AAABBBCCCC, AAABBBCCCC, AAABBBCCCC, AAABBBCCCC, 
            AAABBBBCCC, AAABBBBCCC, AAABBBBCCC, AAABBBBCCC, AAABBBBCCC,
            AAABBBBCCC, AAABBBBCCC, AAABBBBCCC, AAABBBBCCC, AAABBBBCCC,
            AAABBBBCCC, AAABBBBCCC, AAABBBBCCC, AAABBBBCCC, AAABBBBCCC,
            AAABBBBCCC, AAABBBBCCC, AAABBBBCCC, AAABBBBCCC, AAABBBBCCC,
            AAABBBBCCC, AAABBBBCCC, AAABBBBCCC, AAABBBBCCC, AAABBBBCCC,
            AAABBBBCCC, AAABBBBCCC, AAABBBBCCC, AAABBBBCCC, AAABBBBCCC,
            AAABBBBCCC, AAABBBBCCC, AAABBBBCCC, AAABBBBCCC
    );
    private final RarityConfiguration uncommonRuns = new RarityConfiguration(AAB, ABB);
    // aleternative for Japanese Collation for commons and uncommons
    // In order for equal numbers of each common to exist, the average booster must contain:
    // 2.67 A commons (270 / 101)
    // 3.66 B commons (370 / 101)
    // 3.66 C commons (370 / 101)
    // These numbers are the same for all sets with 101 commons in 27:37:37 A/B/C print runs
    // and with 10 common slots per booster
    // private final RarityConfiguration commonRuns = new RarityConfiguration(
            // AABB111111, AABB111111, AABB111111, AABB111111, AABB111111,
            // AAABB11111, AAABB11111, AAABB11111, AAABB11111, AAABB11111, AAABB11111,

            // AAAABB2222, AAAABB2222, AAAABB2222, AAAABB2222, AAAABB2222,
            // AAAABB2222, AAAABB2222, AAAABB2222, AAAABBB222,
            // AAAABBB222, AAAABBBB22
    // );
    // private final RarityConfiguration uncommonRuns = new RarityConfiguration(
            // AAB, AAB, AAB, AAB, AAB, AAB, AAB, AAB, AAB, AAB,
            // AAB, AAB, AAB, AAB, AAB, AAB, AAB, AAB, AAB, AAB,
            // AAB, AAB, AAB, AAB, AAB, AAB, AAB, AAB, AAB, AAB,
            // AAB, AAB, AAB, AAB, AAB, AAB, AAB, AAB, AAB, AAB,
            // AAB, AAB, AAB, AAB, AAB, AAB, AAB, AAB, AAB,
            // ABB, ABB, ABB, ABB, ABB, ABB, ABB, ABB, ABB, ABB,
            // ABB, ABB, ABB, ABB, ABB, ABB, ABB, ABB, ABB, ABB,
            // ABB, ABB, ABB, ABB, ABB, ABB, ABB, ABB, ABB, ABB, ABB
    // );
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