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
 * @author TheElk801
 */
public final class CoreSet2020 extends ExpansionSet {

    private static final CoreSet2020 instance = new CoreSet2020();

    public static CoreSet2020 getInstance() {
        return instance;
    }

    private CoreSet2020() {
        super("Core Set 2020", "M20", ExpansionSet.buildDate(2019, 7, 12), SetType.CORE);
        this.hasBoosters = true;
        this.hasBasicLands = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        this.maxCardNumberInBooster = 280;

        // Core 2020 boosters have a 11/24 chance of basic land being replaced
        // with the common taplands, which DO NOT appear in the common slot.
        this.ratioBoosterSpecialLand = 24;
        this.ratioBoosterSpecialLandNumerator = 11;

        cards.add(new SetCardInfo("Act of Treason", 124, Rarity.COMMON, mage.cards.a.ActOfTreason.class));
        cards.add(new SetCardInfo("Aerial Assault", 1, Rarity.COMMON, mage.cards.a.AerialAssault.class));
        cards.add(new SetCardInfo("Aether Gust", 42, Rarity.UNCOMMON, mage.cards.a.AetherGust.class));
        cards.add(new SetCardInfo("Agent of Treachery", 43, Rarity.RARE, mage.cards.a.AgentOfTreachery.class));
        cards.add(new SetCardInfo("Aggressive Mammoth", 337, Rarity.RARE, mage.cards.a.AggressiveMammoth.class));
        cards.add(new SetCardInfo("Agonizing Syphon", 83, Rarity.COMMON, mage.cards.a.AgonizingSyphon.class));
        cards.add(new SetCardInfo("Air Elemental", 44, Rarity.UNCOMMON, mage.cards.a.AirElemental.class));
        cards.add(new SetCardInfo("Ajani, Inspiring Leader", 282, Rarity.MYTHIC, mage.cards.a.AjaniInspiringLeader.class));
        cards.add(new SetCardInfo("Ajani, Strength of the Pride", 2, Rarity.MYTHIC, mage.cards.a.AjaniStrengthOfThePride.class));
        cards.add(new SetCardInfo("Ancestral Blade", 3, Rarity.UNCOMMON, mage.cards.a.AncestralBlade.class));
        cards.add(new SetCardInfo("Angel of Vitality", 4, Rarity.UNCOMMON, mage.cards.a.AngelOfVitality.class));
        cards.add(new SetCardInfo("Angelic Gift", 5, Rarity.COMMON, mage.cards.a.AngelicGift.class));
        cards.add(new SetCardInfo("Angelic Guardian", 302, Rarity.RARE, mage.cards.a.AngelicGuardian.class));
        cards.add(new SetCardInfo("Anticipate", 45, Rarity.COMMON, mage.cards.a.Anticipate.class));
        cards.add(new SetCardInfo("Anvilwrought Raptor", 221, Rarity.COMMON, mage.cards.a.AnvilwroughtRaptor.class));
        cards.add(new SetCardInfo("Apostle of Purifying Light", 6, Rarity.UNCOMMON, mage.cards.a.ApostleOfPurifyingLight.class));
        cards.add(new SetCardInfo("Atemsis, All-Seeing", 46, Rarity.RARE, mage.cards.a.AtemsisAllSeeing.class));
        cards.add(new SetCardInfo("Audacious Thief", 84, Rarity.COMMON, mage.cards.a.AudaciousThief.class));
        cards.add(new SetCardInfo("Bag of Holding", 222, Rarity.RARE, mage.cards.b.BagOfHolding.class));
        cards.add(new SetCardInfo("Barkhide Troll", 165, Rarity.UNCOMMON, mage.cards.b.BarkhideTroll.class));
        cards.add(new SetCardInfo("Barony Vampire", 85, Rarity.COMMON, mage.cards.b.BaronyVampire.class));
        cards.add(new SetCardInfo("Bartizan Bats", 319, Rarity.COMMON, mage.cards.b.BartizanBats.class));
        cards.add(new SetCardInfo("Bastion Enforcer", 303, Rarity.COMMON, mage.cards.b.BastionEnforcer.class));
        cards.add(new SetCardInfo("Battalion Foot Soldier", 7, Rarity.COMMON, mage.cards.b.BattalionFootSoldier.class));
        cards.add(new SetCardInfo("Befuddle", 47, Rarity.COMMON, mage.cards.b.Befuddle.class));
        cards.add(new SetCardInfo("Bishop of Wings", 8, Rarity.RARE, mage.cards.b.BishopOfWings.class));
        cards.add(new SetCardInfo("Bladebrand", 86, Rarity.COMMON, mage.cards.b.Bladebrand.class));
        cards.add(new SetCardInfo("Blightbeetle", 87, Rarity.UNCOMMON, mage.cards.b.Blightbeetle.class));
        cards.add(new SetCardInfo("Blood Burglar", 88, Rarity.COMMON, mage.cards.b.BloodBurglar.class));
        cards.add(new SetCardInfo("Blood for Bones", 89, Rarity.UNCOMMON, mage.cards.b.BloodForBones.class));
        cards.add(new SetCardInfo("Bloodfell Caves", 242, Rarity.COMMON, mage.cards.b.BloodfellCaves.class));
        cards.add(new SetCardInfo("Bloodsoaked Altar", 90, Rarity.UNCOMMON, mage.cards.b.BloodsoakedAltar.class));
        cards.add(new SetCardInfo("Bloodthirsty Aerialist", 91, Rarity.UNCOMMON, mage.cards.b.BloodthirstyAerialist.class));
        cards.add(new SetCardInfo("Blossoming Sands", 243, Rarity.COMMON, mage.cards.b.BlossomingSands.class));
        cards.add(new SetCardInfo("Bogstomper", 320, Rarity.COMMON, mage.cards.b.Bogstomper.class));
        cards.add(new SetCardInfo("Bone Splinters", 92, Rarity.COMMON, mage.cards.b.BoneSplinters.class));
        cards.add(new SetCardInfo("Bone to Ash", 48, Rarity.COMMON, mage.cards.b.BoneToAsh.class));
        cards.add(new SetCardInfo("Boneclad Necromancer", 93, Rarity.COMMON, mage.cards.b.BonecladNecromancer.class));
        cards.add(new SetCardInfo("Boreal Elemental", 49, Rarity.COMMON, mage.cards.b.BorealElemental.class));
        cards.add(new SetCardInfo("Brightwood Tracker", 166, Rarity.COMMON, mage.cards.b.BrightwoodTracker.class));
        cards.add(new SetCardInfo("Brineborn Cutthroat", 50, Rarity.UNCOMMON, mage.cards.b.BrinebornCutthroat.class));
        cards.add(new SetCardInfo("Bristling Boar", 338, Rarity.COMMON, mage.cards.b.BristlingBoar.class));
        cards.add(new SetCardInfo("Brought Back", 9, Rarity.RARE, mage.cards.b.BroughtBack.class));
        cards.add(new SetCardInfo("Canopy Spider", 339, Rarity.COMMON, mage.cards.c.CanopySpider.class));
        cards.add(new SetCardInfo("Captivating Gyre", 51, Rarity.UNCOMMON, mage.cards.c.CaptivatingGyre.class));
        cards.add(new SetCardInfo("Cavalier of Dawn", 10, Rarity.MYTHIC, mage.cards.c.CavalierOfDawn.class));
        cards.add(new SetCardInfo("Cavalier of Flame", 125, Rarity.MYTHIC, mage.cards.c.CavalierOfFlame.class));
        cards.add(new SetCardInfo("Cavalier of Gales", 52, Rarity.MYTHIC, mage.cards.c.CavalierOfGales.class));
        cards.add(new SetCardInfo("Cavalier of Night", 94, Rarity.MYTHIC, mage.cards.c.CavalierOfNight.class));
        cards.add(new SetCardInfo("Cavalier of Thorns", 167, Rarity.MYTHIC, mage.cards.c.CavalierOfThorns.class));
        cards.add(new SetCardInfo("Celestial Messenger", 287, Rarity.COMMON, mage.cards.c.CelestialMessenger.class));
        cards.add(new SetCardInfo("Centaur Courser", 168, Rarity.COMMON, mage.cards.c.CentaurCourser.class));
        cards.add(new SetCardInfo("Cerulean Drake", 53, Rarity.UNCOMMON, mage.cards.c.CeruleanDrake.class));
        cards.add(new SetCardInfo("Chandra's Embercat", 129, Rarity.COMMON, mage.cards.c.ChandrasEmbercat.class));
        cards.add(new SetCardInfo("Chandra's Flame Wave", 295, Rarity.RARE, mage.cards.c.ChandrasFlameWave.class));
        cards.add(new SetCardInfo("Chandra's Outrage", 130, Rarity.COMMON, mage.cards.c.ChandrasOutrage.class));
        cards.add(new SetCardInfo("Chandra's Regulator", 131, Rarity.RARE, mage.cards.c.ChandrasRegulator.class));
        cards.add(new SetCardInfo("Chandra's Spitfire", 132, Rarity.UNCOMMON, mage.cards.c.ChandrasSpitfire.class));
        cards.add(new SetCardInfo("Chandra, Acolyte of Flame", 126, Rarity.RARE, mage.cards.c.ChandraAcolyteOfFlame.class));
        cards.add(new SetCardInfo("Chandra, Awakened Inferno", 127, Rarity.MYTHIC, mage.cards.c.ChandraAwakenedInferno.class));
        cards.add(new SetCardInfo("Chandra, Flame's Fury", 294, Rarity.MYTHIC, mage.cards.c.ChandraFlamesFury.class));
        cards.add(new SetCardInfo("Chandra, Novice Pyromancer", 128, Rarity.UNCOMMON, mage.cards.c.ChandraNovicePyromancer.class));
        cards.add(new SetCardInfo("Cloudkin Seer", 54, Rarity.COMMON, mage.cards.c.CloudkinSeer.class));
        cards.add(new SetCardInfo("Colossus Hammer", 223, Rarity.UNCOMMON, mage.cards.c.ColossusHammer.class));
        cards.add(new SetCardInfo("Concordia Pegasus", 304, Rarity.COMMON, mage.cards.c.ConcordiaPegasus.class));
        cards.add(new SetCardInfo("Convolute", 55, Rarity.COMMON, mage.cards.c.Convolute.class));
        cards.add(new SetCardInfo("Coral Merfolk", 315, Rarity.COMMON, mage.cards.c.CoralMerfolk.class));
        cards.add(new SetCardInfo("Corpse Knight", "206+", Rarity.UNCOMMON, mage.cards.c.CorpseKnight.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Corpse Knight", 206, Rarity.UNCOMMON, mage.cards.c.CorpseKnight.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Creeping Trailblazer", 207, Rarity.UNCOMMON, mage.cards.c.CreepingTrailblazer.class));
        cards.add(new SetCardInfo("Cryptic Caves", 244, Rarity.UNCOMMON, mage.cards.c.CrypticCaves.class));
        cards.add(new SetCardInfo("Daggersail Aeronaut", 133, Rarity.COMMON, mage.cards.d.DaggersailAeronaut.class));
        cards.add(new SetCardInfo("Dark Remedy", 321, Rarity.COMMON, mage.cards.d.DarkRemedy.class));
        cards.add(new SetCardInfo("Dawning Angel", 11, Rarity.COMMON, mage.cards.d.DawningAngel.class));
        cards.add(new SetCardInfo("Daybreak Chaplain", 12, Rarity.COMMON, mage.cards.d.DaybreakChaplain.class));
        cards.add(new SetCardInfo("Destructive Digger", 134, Rarity.COMMON, mage.cards.d.DestructiveDigger.class));
        cards.add(new SetCardInfo("Devout Decree", 13, Rarity.UNCOMMON, mage.cards.d.DevoutDecree.class));
        cards.add(new SetCardInfo("Diamond Knight", 224, Rarity.UNCOMMON, mage.cards.d.DiamondKnight.class));
        cards.add(new SetCardInfo("Disenchant", 14, Rarity.COMMON, mage.cards.d.Disenchant.class));
        cards.add(new SetCardInfo("Disentomb", 322, Rarity.COMMON, mage.cards.d.Disentomb.class));
        cards.add(new SetCardInfo("Disfigure", 95, Rarity.UNCOMMON, mage.cards.d.Disfigure.class));
        cards.add(new SetCardInfo("Dismal Backwater", 245, Rarity.COMMON, mage.cards.d.DismalBackwater.class));
        cards.add(new SetCardInfo("Diviner's Lockbox", 225, Rarity.UNCOMMON, mage.cards.d.DivinersLockbox.class));
        cards.add(new SetCardInfo("Dragon Mage", 135, Rarity.UNCOMMON, mage.cards.d.DragonMage.class));
        cards.add(new SetCardInfo("Drakuseth, Maw of Flames", 136, Rarity.RARE, mage.cards.d.DrakusethMawOfFlames.class));
        cards.add(new SetCardInfo("Drawn from Dreams", 56, Rarity.RARE, mage.cards.d.DrawnFromDreams.class));
        cards.add(new SetCardInfo("Dread Presence", 96, Rarity.RARE, mage.cards.d.DreadPresence.class));
        cards.add(new SetCardInfo("Dungeon Geists", 57, Rarity.RARE, mage.cards.d.DungeonGeists.class));
        cards.add(new SetCardInfo("Duress", 97, Rarity.COMMON, mage.cards.d.Duress.class));
        cards.add(new SetCardInfo("Elvish Reclaimer", 169, Rarity.RARE, mage.cards.e.ElvishReclaimer.class));
        cards.add(new SetCardInfo("Ember Hauler", 137, Rarity.UNCOMMON, mage.cards.e.EmberHauler.class));
        cards.add(new SetCardInfo("Embodiment of Agonies", 98, Rarity.RARE, mage.cards.e.EmbodimentOfAgonies.class));
        cards.add(new SetCardInfo("Empyrean Eagle", 208, Rarity.UNCOMMON, mage.cards.e.EmpyreanEagle.class));
        cards.add(new SetCardInfo("Engulfing Eruption", 328, Rarity.COMMON, mage.cards.e.EngulfingEruption.class));
        cards.add(new SetCardInfo("Epicure of Blood", 99, Rarity.COMMON, mage.cards.e.EpicureOfBlood.class));
        cards.add(new SetCardInfo("Eternal Isolation", 15, Rarity.UNCOMMON, mage.cards.e.EternalIsolation.class));
        cards.add(new SetCardInfo("Ethereal Elk", 299, Rarity.RARE, mage.cards.e.EtherealElk.class));
        cards.add(new SetCardInfo("Evolving Wilds", 246, Rarity.COMMON, mage.cards.e.EvolvingWilds.class));
        cards.add(new SetCardInfo("Faerie Miscreant", 58, Rarity.COMMON, mage.cards.f.FaerieMiscreant.class));
        cards.add(new SetCardInfo("Fathom Fleet Cutthroat", 100, Rarity.COMMON, mage.cards.f.FathomFleetCutthroat.class));
        cards.add(new SetCardInfo("Fearless Halberdier", 329, Rarity.COMMON, mage.cards.f.FearlessHalberdier.class));
        cards.add(new SetCardInfo("Fencing Ace", 16, Rarity.UNCOMMON, mage.cards.f.FencingAce.class));
        cards.add(new SetCardInfo("Feral Abomination", 101, Rarity.COMMON, mage.cards.f.FeralAbomination.class));
        cards.add(new SetCardInfo("Feral Invocation", 170, Rarity.COMMON, mage.cards.f.FeralInvocation.class));
        cards.add(new SetCardInfo("Ferocious Pup", 171, Rarity.COMMON, mage.cards.f.FerociousPup.class));
        cards.add(new SetCardInfo("Field of the Dead", 247, Rarity.RARE, mage.cards.f.FieldOfTheDead.class));
        cards.add(new SetCardInfo("Fire Elemental", 138, Rarity.COMMON, mage.cards.f.FireElemental.class));
        cards.add(new SetCardInfo("Flame Sweep", 139, Rarity.UNCOMMON, mage.cards.f.FlameSweep.class));
        cards.add(new SetCardInfo("Flood of Tears", 59, Rarity.RARE, mage.cards.f.FloodOfTears.class));
        cards.add(new SetCardInfo("Forest", 277, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 278, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 279, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 280, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fortress Crab", 60, Rarity.COMMON, mage.cards.f.FortressCrab.class));
        cards.add(new SetCardInfo("Frilled Sandwalla", 340, Rarity.COMMON, mage.cards.f.FrilledSandwalla.class));
        cards.add(new SetCardInfo("Frilled Sea Serpent", 61, Rarity.COMMON, mage.cards.f.FrilledSeaSerpent.class));
        cards.add(new SetCardInfo("Frost Lynx", 62, Rarity.COMMON, mage.cards.f.FrostLynx.class));
        cards.add(new SetCardInfo("Fry", 140, Rarity.UNCOMMON, mage.cards.f.Fry.class));
        cards.add(new SetCardInfo("Gargos, Vicious Watcher", 172, Rarity.RARE, mage.cards.g.GargosViciousWatcher.class));
        cards.add(new SetCardInfo("Gauntlets of Light", 17, Rarity.UNCOMMON, mage.cards.g.GauntletsOfLight.class));
        cards.add(new SetCardInfo("Gift of Paradise", 173, Rarity.COMMON, mage.cards.g.GiftOfParadise.class));
        cards.add(new SetCardInfo("Glaring Aegis", 18, Rarity.COMMON, mage.cards.g.GlaringAegis.class));
        cards.add(new SetCardInfo("Glint-Horn Buccaneer", 141, Rarity.RARE, mage.cards.g.GlintHornBuccaneer.class));
        cards.add(new SetCardInfo("Gnarlback Rhino", 300, Rarity.UNCOMMON, mage.cards.g.GnarlbackRhino.class));
        cards.add(new SetCardInfo("Goblin Assailant", 330, Rarity.COMMON, mage.cards.g.GoblinAssailant.class));
        cards.add(new SetCardInfo("Goblin Bird-Grabber", 142, Rarity.COMMON, mage.cards.g.GoblinBirdGrabber.class));
        cards.add(new SetCardInfo("Goblin Ringleader", 143, Rarity.UNCOMMON, mage.cards.g.GoblinRingleader.class));
        cards.add(new SetCardInfo("Goblin Smuggler", 144, Rarity.COMMON, mage.cards.g.GoblinSmuggler.class));
        cards.add(new SetCardInfo("Gods Willing", 19, Rarity.UNCOMMON, mage.cards.g.GodsWilling.class));
        cards.add(new SetCardInfo("Goldmane Griffin", 283, Rarity.RARE, mage.cards.g.GoldmaneGriffin.class));
        cards.add(new SetCardInfo("Golos, Tireless Pilgrim", 226, Rarity.RARE, mage.cards.g.GolosTirelessPilgrim.class));
        cards.add(new SetCardInfo("Gorging Vulture", 102, Rarity.COMMON, mage.cards.g.GorgingVulture.class));
        cards.add(new SetCardInfo("Grafdigger's Cage", 227, Rarity.RARE, mage.cards.g.GrafdiggersCage.class));
        cards.add(new SetCardInfo("Gravedigger", 103, Rarity.UNCOMMON, mage.cards.g.Gravedigger.class));
        cards.add(new SetCardInfo("Gravewaker", 323, Rarity.RARE, mage.cards.g.Gravewaker.class));
        cards.add(new SetCardInfo("Greenwood Sentinel", 174, Rarity.COMMON, mage.cards.g.GreenwoodSentinel.class));
        cards.add(new SetCardInfo("Griffin Protector", 20, Rarity.COMMON, mage.cards.g.GriffinProtector.class));
        cards.add(new SetCardInfo("Griffin Sentinel", 21, Rarity.COMMON, mage.cards.g.GriffinSentinel.class));
        cards.add(new SetCardInfo("Growth Cycle", 175, Rarity.COMMON, mage.cards.g.GrowthCycle.class));
        cards.add(new SetCardInfo("Gruesome Scourger", 104, Rarity.UNCOMMON, mage.cards.g.GruesomeScourger.class));
        cards.add(new SetCardInfo("Haazda Officer", 305, Rarity.COMMON, mage.cards.h.HaazdaOfficer.class));
        cards.add(new SetCardInfo("Hanged Executioner", 22, Rarity.RARE, mage.cards.h.HangedExecutioner.class));
        cards.add(new SetCardInfo("Hard Cover", 63, Rarity.UNCOMMON, mage.cards.h.HardCover.class));
        cards.add(new SetCardInfo("Healer of the Glade", 176, Rarity.COMMON, mage.cards.h.HealerOfTheGlade.class));
        cards.add(new SetCardInfo("Heart-Piercer Bow", 228, Rarity.COMMON, mage.cards.h.HeartPiercerBow.class));
        cards.add(new SetCardInfo("Herald of the Sun", 23, Rarity.UNCOMMON, mage.cards.h.HeraldOfTheSun.class));
        cards.add(new SetCardInfo("Hostile Minotaur", 331, Rarity.COMMON, mage.cards.h.HostileMinotaur.class));
        cards.add(new SetCardInfo("Howling Giant", 177, Rarity.UNCOMMON, mage.cards.h.HowlingGiant.class));
        cards.add(new SetCardInfo("Icon of Ancestry", 229, Rarity.RARE, mage.cards.i.IconOfAncestry.class));
        cards.add(new SetCardInfo("Immortal Phoenix", 332, Rarity.RARE, mage.cards.i.ImmortalPhoenix.class));
        cards.add(new SetCardInfo("Impassioned Orator", 306, Rarity.COMMON, mage.cards.i.ImpassionedOrator.class));
        cards.add(new SetCardInfo("Imperial Outrider", 307, Rarity.COMMON, mage.cards.i.ImperialOutrider.class));
        cards.add(new SetCardInfo("Infuriate", 145, Rarity.COMMON, mage.cards.i.Infuriate.class));
        cards.add(new SetCardInfo("Inspired Charge", 24, Rarity.COMMON, mage.cards.i.InspiredCharge.class));
        cards.add(new SetCardInfo("Inspiring Captain", 25, Rarity.COMMON, mage.cards.i.InspiringCaptain.class));
        cards.add(new SetCardInfo("Ironclad Krovod", 308, Rarity.COMMON, mage.cards.i.IroncladKrovod.class));
        cards.add(new SetCardInfo("Ironroot Warlord", 209, Rarity.UNCOMMON, mage.cards.i.IronrootWarlord.class));
        cards.add(new SetCardInfo("Island", 265, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 266, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 267, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 268, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jungle Hollow", 248, Rarity.COMMON, mage.cards.j.JungleHollow.class));
        cards.add(new SetCardInfo("Kaalia, Zenith Seeker", 210, Rarity.MYTHIC, mage.cards.k.KaaliaZenithSeeker.class));
        cards.add(new SetCardInfo("Keldon Raider", 146, Rarity.COMMON, mage.cards.k.KeldonRaider.class));
        cards.add(new SetCardInfo("Kethis, the Hidden Hand", 211, Rarity.MYTHIC, mage.cards.k.KethisTheHiddenHand.class));
        cards.add(new SetCardInfo("Knight of the Ebon Legion", 105, Rarity.RARE, mage.cards.k.KnightOfTheEbonLegion.class));
        cards.add(new SetCardInfo("Kykar, Wind's Fury", 212, Rarity.MYTHIC, mage.cards.k.KykarWindsFury.class));
        cards.add(new SetCardInfo("Lavakin Brawler", 147, Rarity.COMMON, mage.cards.l.LavakinBrawler.class));
        cards.add(new SetCardInfo("Leafkin Druid", 178, Rarity.COMMON, mage.cards.l.LeafkinDruid.class));
        cards.add(new SetCardInfo("Legion's End", 106, Rarity.RARE, mage.cards.l.LegionsEnd.class));
        cards.add(new SetCardInfo("Leyline of Abundance", 179, Rarity.RARE, mage.cards.l.LeylineOfAbundance.class));
        cards.add(new SetCardInfo("Leyline of Anticipation", 64, Rarity.RARE, mage.cards.l.LeylineOfAnticipation.class));
        cards.add(new SetCardInfo("Leyline of Combustion", 148, Rarity.RARE, mage.cards.l.LeylineOfCombustion.class));
        cards.add(new SetCardInfo("Leyline of Sanctity", 26, Rarity.RARE, mage.cards.l.LeylineOfSanctity.class));
        cards.add(new SetCardInfo("Leyline of the Void", 107, Rarity.RARE, mage.cards.l.LeylineOfTheVoid.class));
        cards.add(new SetCardInfo("Lightning Stormkin", 213, Rarity.UNCOMMON, mage.cards.l.LightningStormkin.class));
        cards.add(new SetCardInfo("Loaming Shaman", 180, Rarity.UNCOMMON, mage.cards.l.LoamingShaman.class));
        cards.add(new SetCardInfo("Lotus Field", 249, Rarity.RARE, mage.cards.l.LotusField.class));
        cards.add(new SetCardInfo("Loxodon Lifechanter", 27, Rarity.RARE, mage.cards.l.LoxodonLifechanter.class));
        cards.add(new SetCardInfo("Loyal Pegasus", 28, Rarity.UNCOMMON, mage.cards.l.LoyalPegasus.class));
        cards.add(new SetCardInfo("Mammoth Spider", 181, Rarity.COMMON, mage.cards.m.MammothSpider.class));
        cards.add(new SetCardInfo("Maniacal Rage", 149, Rarity.COMMON, mage.cards.m.ManiacalRage.class));
        cards.add(new SetCardInfo("Manifold Key", 230, Rarity.UNCOMMON, mage.cards.m.ManifoldKey.class));
        cards.add(new SetCardInfo("Marauder's Axe", 231, Rarity.COMMON, mage.cards.m.MaraudersAxe.class));
        cards.add(new SetCardInfo("Marauding Raptor", 150, Rarity.RARE, mage.cards.m.MaraudingRaptor.class));
        cards.add(new SetCardInfo("Mask of Immolation", 151, Rarity.UNCOMMON, mage.cards.m.MaskOfImmolation.class));
        cards.add(new SetCardInfo("Master Splicer", 29, Rarity.UNCOMMON, mage.cards.m.MasterSplicer.class));
        cards.add(new SetCardInfo("Masterful Replication", 65, Rarity.RARE, mage.cards.m.MasterfulReplication.class));
        cards.add(new SetCardInfo("Meteor Golem", 232, Rarity.UNCOMMON, mage.cards.m.MeteorGolem.class));
        cards.add(new SetCardInfo("Metropolis Sprite", 66, Rarity.COMMON, mage.cards.m.MetropolisSprite.class));
        cards.add(new SetCardInfo("Might of the Masses", 182, Rarity.UNCOMMON, mage.cards.m.MightOfTheMasses.class));
        cards.add(new SetCardInfo("Mind Rot", 108, Rarity.COMMON, mage.cards.m.MindRot.class));
        cards.add(new SetCardInfo("Moat Piranhas", 67, Rarity.COMMON, mage.cards.m.MoatPiranhas.class));
        cards.add(new SetCardInfo("Moldervine Reclamation", 214, Rarity.UNCOMMON, mage.cards.m.MoldervineReclamation.class));
        cards.add(new SetCardInfo("Moment of Heroism", 30, Rarity.COMMON, mage.cards.m.MomentOfHeroism.class));
        cards.add(new SetCardInfo("Moorland Inquisitor", 31, Rarity.COMMON, mage.cards.m.MoorlandInquisitor.class));
        cards.add(new SetCardInfo("Mountain", 273, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 274, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 275, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 276, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mu Yanling, Celestial Wind", 286, Rarity.MYTHIC, mage.cards.m.MuYanlingCelestialWind.class));
        cards.add(new SetCardInfo("Mu Yanling, Sky Dancer", 68, Rarity.MYTHIC, mage.cards.m.MuYanlingSkyDancer.class));
        cards.add(new SetCardInfo("Murder", 109, Rarity.COMMON, mage.cards.m.Murder.class));
        cards.add(new SetCardInfo("Mystic Forge", 233, Rarity.RARE, mage.cards.m.MysticForge.class));
        cards.add(new SetCardInfo("Natural End", 183, Rarity.COMMON, mage.cards.n.NaturalEnd.class));
        cards.add(new SetCardInfo("Negate", 69, Rarity.COMMON, mage.cards.n.Negate.class));
        cards.add(new SetCardInfo("Netcaster Spider", 184, Rarity.COMMON, mage.cards.n.NetcasterSpider.class));
        cards.add(new SetCardInfo("Nightpack Ambusher", 185, Rarity.RARE, mage.cards.n.NightpackAmbusher.class));
        cards.add(new SetCardInfo("Nimble Birdsticker", 333, Rarity.COMMON, mage.cards.n.NimbleBirdsticker.class));
        cards.add(new SetCardInfo("Noxious Grasp", 110, Rarity.UNCOMMON, mage.cards.n.NoxiousGrasp.class));
        cards.add(new SetCardInfo("Oakenform", 341, Rarity.COMMON, mage.cards.o.Oakenform.class));
        cards.add(new SetCardInfo("Octoprophet", 70, Rarity.COMMON, mage.cards.o.Octoprophet.class));
        cards.add(new SetCardInfo("Ogre Siegebreaker", 215, Rarity.UNCOMMON, mage.cards.o.OgreSiegebreaker.class));
        cards.add(new SetCardInfo("Omnath, Locus of the Roil", 216, Rarity.MYTHIC, mage.cards.o.OmnathLocusOfTheRoil.class));
        cards.add(new SetCardInfo("Overcome", 186, Rarity.UNCOMMON, mage.cards.o.Overcome.class));
        cards.add(new SetCardInfo("Overgrowth Elemental", 187, Rarity.UNCOMMON, mage.cards.o.OvergrowthElemental.class));
        cards.add(new SetCardInfo("Pacifism", 32, Rarity.COMMON, mage.cards.p.Pacifism.class));
        cards.add(new SetCardInfo("Pack Mastiff", 152, Rarity.COMMON, mage.cards.p.PackMastiff.class));
        cards.add(new SetCardInfo("Pattern Matcher", 234, Rarity.UNCOMMON, mage.cards.p.PatternMatcher.class));
        cards.add(new SetCardInfo("Phantom Warrior", 316, Rarity.COMMON, mage.cards.p.PhantomWarrior.class));
        cards.add(new SetCardInfo("Plains", 261, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 262, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 263, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 264, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Planar Cleansing", 33, Rarity.RARE, mage.cards.p.PlanarCleansing.class));
        cards.add(new SetCardInfo("Plummet", 188, Rarity.COMMON, mage.cards.p.Plummet.class));
        cards.add(new SetCardInfo("Portal of Sanctuary", 71, Rarity.UNCOMMON, mage.cards.p.PortalOfSanctuary.class));
        cards.add(new SetCardInfo("Prismite", 235, Rarity.COMMON, mage.cards.p.Prismite.class));
        cards.add(new SetCardInfo("Prized Unicorn", 342, Rarity.COMMON, mage.cards.p.PrizedUnicorn.class));
        cards.add(new SetCardInfo("Prowling Caracal", 309, Rarity.COMMON, mage.cards.p.ProwlingCaracal.class));
        cards.add(new SetCardInfo("Pulse of Murasa", 189, Rarity.UNCOMMON, mage.cards.p.PulseOfMurasa.class));
        cards.add(new SetCardInfo("Pyroclastic Elemental", 296, Rarity.UNCOMMON, mage.cards.p.PyroclasticElemental.class));
        cards.add(new SetCardInfo("Rabid Bite", 190, Rarity.COMMON, mage.cards.r.RabidBite.class));
        cards.add(new SetCardInfo("Raise the Alarm", 34, Rarity.COMMON, mage.cards.r.RaiseTheAlarm.class));
        cards.add(new SetCardInfo("Rapacious Dragon", 153, Rarity.UNCOMMON, mage.cards.r.RapaciousDragon.class));
        cards.add(new SetCardInfo("Reckless Air Strike", 154, Rarity.COMMON, mage.cards.r.RecklessAirStrike.class));
        cards.add(new SetCardInfo("Reduce to Ashes", 155, Rarity.COMMON, mage.cards.r.ReduceToAshes.class));
        cards.add(new SetCardInfo("Renowned Weaponsmith", 72, Rarity.UNCOMMON, mage.cards.r.RenownedWeaponsmith.class));
        cards.add(new SetCardInfo("Repeated Reverberation", 156, Rarity.RARE, mage.cards.r.RepeatedReverberation.class));
        cards.add(new SetCardInfo("Retributive Wand", 236, Rarity.UNCOMMON, mage.cards.r.RetributiveWand.class));
        cards.add(new SetCardInfo("Riddlemaster Sphinx", 317, Rarity.RARE, mage.cards.r.RiddlemasterSphinx.class));
        cards.add(new SetCardInfo("Rienne, Angel of Rebirth", 281, Rarity.MYTHIC, mage.cards.r.RienneAngelOfRebirth.class));
        cards.add(new SetCardInfo("Ripscale Predator", 157, Rarity.COMMON, mage.cards.r.RipscalePredator.class));
        cards.add(new SetCardInfo("Risen Reef", 217, Rarity.UNCOMMON, mage.cards.r.RisenReef.class));
        cards.add(new SetCardInfo("Rotting Regisaur", 111, Rarity.RARE, mage.cards.r.RottingRegisaur.class));
        cards.add(new SetCardInfo("Rubblebelt Recluse", 334, Rarity.COMMON, mage.cards.r.RubblebeltRecluse.class));
        cards.add(new SetCardInfo("Rugged Highlands", 250, Rarity.COMMON, mage.cards.r.RuggedHighlands.class));
        cards.add(new SetCardInfo("Rule of Law", 35, Rarity.UNCOMMON, mage.cards.r.RuleOfLaw.class));
        cards.add(new SetCardInfo("Sage's Row Denizen", 73, Rarity.COMMON, mage.cards.s.SagesRowDenizen.class));
        cards.add(new SetCardInfo("Salvager of Ruin", 237, Rarity.UNCOMMON, mage.cards.s.SalvagerOfRuin.class));
        cards.add(new SetCardInfo("Sanitarium Skeleton", 112, Rarity.COMMON, mage.cards.s.SanitariumSkeleton.class));
        cards.add(new SetCardInfo("Savage Gorger", 291, Rarity.COMMON, mage.cards.s.SavageGorger.class));
        cards.add(new SetCardInfo("Savannah Sage", 284, Rarity.COMMON, mage.cards.s.SavannahSage.class));
        cards.add(new SetCardInfo("Scampering Scorcher", 158, Rarity.UNCOMMON, mage.cards.s.ScamperingScorcher.class));
        cards.add(new SetCardInfo("Scheming Symmetry", 113, Rarity.RARE, mage.cards.s.SchemingSymmetry.class));
        cards.add(new SetCardInfo("Scholar of the Ages", 74, Rarity.UNCOMMON, mage.cards.s.ScholarOfTheAges.class));
        cards.add(new SetCardInfo("Scorch Spitter", 159, Rarity.COMMON, mage.cards.s.ScorchSpitter.class));
        cards.add(new SetCardInfo("Scoured Barrens", 251, Rarity.COMMON, mage.cards.s.ScouredBarrens.class));
        cards.add(new SetCardInfo("Scuttlemutt", 238, Rarity.UNCOMMON, mage.cards.s.Scuttlemutt.class));
        cards.add(new SetCardInfo("Season of Growth", 191, Rarity.UNCOMMON, mage.cards.s.SeasonOfGrowth.class));
        cards.add(new SetCardInfo("Sedge Scorpion", 192, Rarity.COMMON, mage.cards.s.SedgeScorpion.class));
        cards.add(new SetCardInfo("Sephara, Sky's Blade", 36, Rarity.RARE, mage.cards.s.SepharaSkysBlade.class));
        cards.add(new SetCardInfo("Serra's Guardian", 310, Rarity.RARE, mage.cards.s.SerrasGuardian.class));
        cards.add(new SetCardInfo("Shared Summons", 193, Rarity.RARE, mage.cards.s.SharedSummons.class));
        cards.add(new SetCardInfo("Shifting Ceratops", 194, Rarity.RARE, mage.cards.s.ShiftingCeratops.class));
        cards.add(new SetCardInfo("Shivan Dragon", 335, Rarity.RARE, mage.cards.s.ShivanDragon.class));
        cards.add(new SetCardInfo("Shock", 160, Rarity.COMMON, mage.cards.s.Shock.class));
        cards.add(new SetCardInfo("Show of Valor", 311, Rarity.COMMON, mage.cards.s.ShowOfValor.class));
        cards.add(new SetCardInfo("Siege Mastodon", 312, Rarity.COMMON, mage.cards.s.SiegeMastodon.class));
        cards.add(new SetCardInfo("Silverback Shaman", 195, Rarity.COMMON, mage.cards.s.SilverbackShaman.class));
        cards.add(new SetCardInfo("Skeleton Archer", 324, Rarity.COMMON, mage.cards.s.SkeletonArcher.class));
        cards.add(new SetCardInfo("Skyknight Vanguard", 218, Rarity.UNCOMMON, mage.cards.s.SkyknightVanguard.class));
        cards.add(new SetCardInfo("Sleep Paralysis", 75, Rarity.COMMON, mage.cards.s.SleepParalysis.class));
        cards.add(new SetCardInfo("Snapping Drake", 318, Rarity.COMMON, mage.cards.s.SnappingDrake.class));
        cards.add(new SetCardInfo("Sorcerer of the Fang", 114, Rarity.COMMON, mage.cards.s.SorcererOfTheFang.class));
        cards.add(new SetCardInfo("Sorin's Guide", 292, Rarity.RARE, mage.cards.s.SorinsGuide.class));
        cards.add(new SetCardInfo("Sorin's Thirst", 325, Rarity.COMMON, mage.cards.s.SorinsThirst.class));
        cards.add(new SetCardInfo("Sorin, Imperious Bloodlord", 115, Rarity.MYTHIC, mage.cards.s.SorinImperiousBloodlord.class));
        cards.add(new SetCardInfo("Sorin, Vampire Lord", 290, Rarity.MYTHIC, mage.cards.s.SorinVampireLord.class));
        cards.add(new SetCardInfo("Soul Salvage", 116, Rarity.COMMON, mage.cards.s.SoulSalvage.class));
        cards.add(new SetCardInfo("Soulmender", 37, Rarity.COMMON, mage.cards.s.Soulmender.class));
        cards.add(new SetCardInfo("Spectral Sailor", 76, Rarity.UNCOMMON, mage.cards.s.SpectralSailor.class));
        cards.add(new SetCardInfo("Squad Captain", 38, Rarity.COMMON, mage.cards.s.SquadCaptain.class));
        cards.add(new SetCardInfo("Starfield Mystic", 39, Rarity.RARE, mage.cards.s.StarfieldMystic.class));
        cards.add(new SetCardInfo("Steadfast Sentry", 40, Rarity.COMMON, mage.cards.s.SteadfastSentry.class));
        cards.add(new SetCardInfo("Steel Overseer", 239, Rarity.RARE, mage.cards.s.SteelOverseer.class));
        cards.add(new SetCardInfo("Stone Golem", 240, Rarity.COMMON, mage.cards.s.StoneGolem.class));
        cards.add(new SetCardInfo("Swamp", 269, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 270, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 271, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 272, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swiftwater Cliffs", 252, Rarity.COMMON, mage.cards.s.SwiftwaterCliffs.class));
        cards.add(new SetCardInfo("Take Vengeance", 313, Rarity.COMMON, mage.cards.t.TakeVengeance.class));
        cards.add(new SetCardInfo("Tale's End", 77, Rarity.RARE, mage.cards.t.TalesEnd.class));
        cards.add(new SetCardInfo("Tectonic Rift", 161, Rarity.COMMON, mage.cards.t.TectonicRift.class));
        cards.add(new SetCardInfo("Temple of Epiphany", 253, Rarity.RARE, mage.cards.t.TempleOfEpiphany.class));
        cards.add(new SetCardInfo("Temple of Malady", 254, Rarity.RARE, mage.cards.t.TempleOfMalady.class));
        cards.add(new SetCardInfo("Temple of Mystery", 255, Rarity.RARE, mage.cards.t.TempleOfMystery.class));
        cards.add(new SetCardInfo("Temple of Silence", 256, Rarity.RARE, mage.cards.t.TempleOfSilence.class));
        cards.add(new SetCardInfo("Temple of Triumph", 257, Rarity.RARE, mage.cards.t.TempleOfTriumph.class));
        cards.add(new SetCardInfo("Thicket Crasher", 196, Rarity.COMMON, mage.cards.t.ThicketCrasher.class));
        cards.add(new SetCardInfo("Thirsting Bloodlord", 293, Rarity.UNCOMMON, mage.cards.t.ThirstingBloodlord.class));
        cards.add(new SetCardInfo("Thornwood Falls", 258, Rarity.COMMON, mage.cards.t.ThornwoodFalls.class));
        cards.add(new SetCardInfo("Thought Distortion", 117, Rarity.UNCOMMON, mage.cards.t.ThoughtDistortion.class));
        cards.add(new SetCardInfo("Thrashing Brontodon", 197, Rarity.UNCOMMON, mage.cards.t.ThrashingBrontodon.class));
        cards.add(new SetCardInfo("Thunderkin Awakener", 162, Rarity.RARE, mage.cards.t.ThunderkinAwakener.class));
        cards.add(new SetCardInfo("Titanic Growth", 343, Rarity.COMMON, mage.cards.t.TitanicGrowth.class));
        cards.add(new SetCardInfo("Tomebound Lich", 219, Rarity.UNCOMMON, mage.cards.t.TomeboundLich.class));
        cards.add(new SetCardInfo("Tranquil Cove", 259, Rarity.COMMON, mage.cards.t.TranquilCove.class));
        cards.add(new SetCardInfo("Trusted Pegasus", 314, Rarity.COMMON, mage.cards.t.TrustedPegasus.class));
        cards.add(new SetCardInfo("Twinblade Paladin", 285, Rarity.UNCOMMON, mage.cards.t.TwinbladePaladin.class));
        cards.add(new SetCardInfo("Uncaged Fury", 163, Rarity.UNCOMMON, mage.cards.u.UncagedFury.class));
        cards.add(new SetCardInfo("Unchained Berserker", 164, Rarity.UNCOMMON, mage.cards.u.UnchainedBerserker.class));
        cards.add(new SetCardInfo("Undead Servant", 118, Rarity.COMMON, mage.cards.u.UndeadServant.class));
        cards.add(new SetCardInfo("Unholy Indenture", 119, Rarity.COMMON, mage.cards.u.UnholyIndenture.class));
        cards.add(new SetCardInfo("Unsummon", 78, Rarity.COMMON, mage.cards.u.Unsummon.class));
        cards.add(new SetCardInfo("Vampire of the Dire Moon", 120, Rarity.UNCOMMON, mage.cards.v.VampireOfTheDireMoon.class));
        cards.add(new SetCardInfo("Vampire Opportunist", 326, Rarity.COMMON, mage.cards.v.VampireOpportunist.class));
        cards.add(new SetCardInfo("Veil of Summer", 198, Rarity.UNCOMMON, mage.cards.v.VeilOfSummer.class));
        cards.add(new SetCardInfo("Vengeful Warchief", 121, Rarity.UNCOMMON, mage.cards.v.VengefulWarchief.class));
        cards.add(new SetCardInfo("Vial of Dragonfire", 241, Rarity.COMMON, mage.cards.v.VialOfDragonfire.class));
        cards.add(new SetCardInfo("Vilis, Broker of Blood", 122, Rarity.RARE, mage.cards.v.VilisBrokerOfBlood.class));
        cards.add(new SetCardInfo("Vivien's Crocodile", 301, Rarity.COMMON, mage.cards.v.ViviensCrocodile.class));
        cards.add(new SetCardInfo("Vivien, Arkbow Ranger", 199, Rarity.MYTHIC, mage.cards.v.VivienArkbowRanger.class));
        cards.add(new SetCardInfo("Vivien, Nature's Avenger", 298, Rarity.MYTHIC, mage.cards.v.VivienNaturesAvenger.class));
        cards.add(new SetCardInfo("Volcanic Dragon", 336, Rarity.UNCOMMON, mage.cards.v.VolcanicDragon.class));
        cards.add(new SetCardInfo("Voracious Hydra", 200, Rarity.RARE, mage.cards.v.VoraciousHydra.class));
        cards.add(new SetCardInfo("Vorstclaw", 201, Rarity.COMMON, mage.cards.v.Vorstclaw.class));
        cards.add(new SetCardInfo("Wakeroot Elemental", 202, Rarity.RARE, mage.cards.w.WakerootElemental.class));
        cards.add(new SetCardInfo("Walking Corpse", 327, Rarity.COMMON, mage.cards.w.WalkingCorpse.class));
        cards.add(new SetCardInfo("Warden of Evos Isle", 79, Rarity.UNCOMMON, mage.cards.w.WardenOfEvosIsle.class));
        cards.add(new SetCardInfo("Waterkin Shaman", 288, Rarity.UNCOMMON, mage.cards.w.WaterkinShaman.class));
        cards.add(new SetCardInfo("Wildfire Elemental", 297, Rarity.COMMON, mage.cards.w.WildfireElemental.class));
        cards.add(new SetCardInfo("Wind-Scarred Crag", 260, Rarity.COMMON, mage.cards.w.WindScarredCrag.class));
        cards.add(new SetCardInfo("Winged Words", 80, Rarity.COMMON, mage.cards.w.WingedWords.class));
        cards.add(new SetCardInfo("Wolfkin Bond", 203, Rarity.COMMON, mage.cards.w.WolfkinBond.class));
        cards.add(new SetCardInfo("Wolfrider's Saddle", 204, Rarity.UNCOMMON, mage.cards.w.WolfridersSaddle.class));
        cards.add(new SetCardInfo("Woodland Champion", 205, Rarity.UNCOMMON, mage.cards.w.WoodlandChampion.class));
        cards.add(new SetCardInfo("Woodland Mystic", 344, Rarity.COMMON, mage.cards.w.WoodlandMystic.class));
        cards.add(new SetCardInfo("Yanling's Harbinger", 289, Rarity.RARE, mage.cards.y.YanlingsHarbinger.class));
        cards.add(new SetCardInfo("Yarok's Fenlurker", 123, Rarity.UNCOMMON, mage.cards.y.YaroksFenlurker.class));
        cards.add(new SetCardInfo("Yarok's Wavecrasher", 81, Rarity.UNCOMMON, mage.cards.y.YaroksWavecrasher.class));
        cards.add(new SetCardInfo("Yarok, the Desecrated", 220, Rarity.MYTHIC, mage.cards.y.YarokTheDesecrated.class));
        cards.add(new SetCardInfo("Yoked Ox", 41, Rarity.COMMON, mage.cards.y.YokedOx.class));
        cards.add(new SetCardInfo("Zephyr Charge", 82, Rarity.COMMON, mage.cards.z.ZephyrCharge.class));
    }

    @Override
    public BoosterCollator createCollator() {
        return new CoreSet2020Collator();
    }
}

// Booster collation info from https://www.lethe.xyz/mtg/collation/m20.html
// Using USA collation for all rarities
// Foil rare sheet used for regular rares as regular rare sheet is not known
class CoreSet2020Collator implements BoosterCollator {
    private final CardRun commonA = new CardRun(true, "154", "34", "69", "155", "38", "61", "124", "25", "45", "145", "31", "58", "157", "14", "67", "138", "5", "66", "146", "1", "73", "155", "30", "69", "142", "12", "60", "159", "24", "70", "147", "31", "55", "144", "34", "48", "145", "40", "61", "154", "25", "67", "124", "38", "58", "146", "5", "60", "142", "1", "55", "144", "12", "66", "157", "14", "45", "138", "24", "70", "159", "40", "73", "147", "30", "48");
    private final CardRun commonB = new CardRun(true, "183", "85", "174", "88", "196", "101", "201", "99", "171", "112", "173", "100", "170", "97", "203", "92", "166", "84", "188", "101", "181", "116", "196", "88", "174", "86", "183", "85", "173", "112", "166", "99", "203", "100", "170", "84", "201", "101", "171", "85", "188", "92", "174", "97", "183", "86", "181", "116", "196", "88", "201", "99", "170", "100", "171", "84", "203", "112", "173", "97", "166", "92", "188", "116", "181", "86");
    private final CardRun commonC1 = new CardRun(true, "20", "108", "54", "161", "192", "241", "82", "178", "7", "160", "176", "231", "134", "75", "41", "133", "221", "195", "102", "161", "80", "108", "192", "82", "21", "175", "152", "37", "241", "83", "176", "54", "134", "20", "240", "178", "62", "133", "119", "231", "75", "41", "102", "175", "160", "240", "195", "7", "80", "119", "21", "83", "62", "152", "37");
    private final CardRun commonC2 = new CardRun(true, "184", "118", "129", "235", "32", "47", "168", "114", "93", "149", "49", "190", "228", "130", "18", "78", "114", "184", "47", "109", "11", "129", "168", "235", "32", "118", "49", "149", "93", "11", "190", "228", "130", "78", "235", "184", "47", "109", "221", "129", "18", "49", "149", "168", "93", "118", "32", "11", "130", "228", "190", "114", "78", "18", "109");
    private final CardRun uncommonA = new CardRun(true, "4", "198", "139", "19", "120", "215", "51", "232", "189", "128", "15", "89", "207", "234", "74", "197", "153", "16", "87", "209", "79", "177", "244", "198", "140", "28", "104", "213", "4", "44", "204", "164", "23", "95", "208", "50", "223", "180", "163", "29", "89", "215", "71", "103", "189", "128", "15", "120", "207", "197", "232", "74", "139", "19", "87", "209", "51", "23", "180", "153", "16", "104", "213", "79", "244", "177", "140", "28", "95", "208", "204", "234", "44", "164", "29", "103", "215", "50", "223", "189", "163", "19", "89", "207", "71", "232", "198", "139", "4", "120", "209", "51", "15", "197", "128", "16", "87", "153", "79", "234", "180", "140", "28", "103", "213", "50", "244", "177", "164", "23", "104", "208", "71", "223", "204", "163", "74", "95", "29", "44");
    private final CardRun uncommonB = new CardRun(true, "123", "205", "63", "214", "158", "76", "237", "110", "165", "17", "217", "151", "72", "236", "187", "117", "3", "206", "137", "53", "230", "90", "191", "6", "91", "143", "238", "81", "224", "110", "182", "35", "218", "132", "76", "225", "121", "186", "13", "219", "151", "42", "237", "123", "205", "3", "63", "158", "214", "236", "90", "165", "17", "217", "135", "81", "230", "6", "187", "117", "206", "143", "72", "238", "91", "191", "13", "218", "137", "53", "224", "121", "182", "35", "214", "132", "76", "225", "110", "186", "3", "205", "158", "42", "236", "123", "165", "6", "63", "135", "219", "237", "90", "187", "17", "217", "151", "81", "230", "182", "117", "137", "206", "143", "72", "238", "121", "191", "13", "218", "132", "53", "224", "91", "186", "35", "219", "135", "42", "225");
    private final CardRun rare = new CardRun(true, "8", "96", "126", "210", "169", "43", "233", "10", "148", "57", "247", "9", "98", "131", "2", "172", "46", "107", "200", "150", "59", "249", "22", "105", "136", "52", "229", "56", "111", "202", "156", "64", "253", "26", "106", "222", "211", "185", "8", "113", "68", "162", "65", "254", "27", "57", "200", "94", "247", "9", "122", "193", "239", "77", "255", "33", "59", "202", "125", "249", "22", "126", "194", "96", "222", "256", "36", "64", "107", "212", "253", "26", "131", "115", "98", "226", "257", "39", "65", "111", "167", "169", "27", "136", "179", "105", "148", "254", "43", "77", "113", "216", "172", "33", "141", "227", "106", "150", "255", "46", "141", "122", "127", "179", "36", "233", "199", "193", "156", "256", "56", "226", "239", "220", "185", "39", "229", "227", "194", "162", "257");
    private final CardRun landBasic = new CardRun(false, "261", "262", "263", "264", "265", "266", "267", "268", "269", "270", "271", "272", "273", "274", "275", "276", "277", "278", "279", "280");
    private final CardRun landNonBasic = new CardRun(false, "242", "243", "245", "246", "248", "250", "251", "252", "258", "259", "260");

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
    private final BoosterStructure AAAABBBC2C2C2 = new BoosterStructure(
            commonA, commonA, commonA, commonA,
            commonB, commonB, commonB,
            commonC2, commonC2, commonC2
    );
    private final BoosterStructure AAAABBBBC2C2 = new BoosterStructure(
            commonA, commonA, commonA, commonA,
            commonB, commonB, commonB, commonB,
            commonC2, commonC2
    );
    private final BoosterStructure AAB = new BoosterStructure(uncommonA, uncommonA, uncommonB);
    private final BoosterStructure ABB = new BoosterStructure(uncommonA, uncommonB, uncommonB);
    private final BoosterStructure R1 = new BoosterStructure(rare);
    private final BoosterStructure LB = new BoosterStructure(landBasic);
    private final BoosterStructure LN = new BoosterStructure(landNonBasic);

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
            AAAABBBC2C2C2,
            AAAABBBC2C2C2,
            AAAABBBBC2C2
    );
    private final RarityConfiguration uncommonRuns = new RarityConfiguration(AAB, ABB);
    private final RarityConfiguration rareRuns = new RarityConfiguration(R1);
    private final RarityConfiguration landRuns = new RarityConfiguration(
        LB, LB, LB, LB, LB, LB, LB, LB, LB, LB, LB, LB, LB,
        LN, LN, LN, LN, LN, LN, LN, LN, LN, LN, LN
    );

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
