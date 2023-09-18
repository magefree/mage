package mage.sets;

import mage.cards.ExpansionSet;
import mage.cards.repository.CardInfo;
import mage.constants.Rarity;
import mage.constants.SetType;

import java.util.Arrays;
import java.util.List;

/**
 * @author TheElk801
 */
public final class IkoriaLairOfBehemoths extends ExpansionSet {

    private static final List<String> mutateNames = Arrays.asList(
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
}
