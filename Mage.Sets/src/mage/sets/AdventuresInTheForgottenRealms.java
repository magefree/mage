package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class AdventuresInTheForgottenRealms extends ExpansionSet {

    private static final AdventuresInTheForgottenRealms instance = new AdventuresInTheForgottenRealms();

    public static AdventuresInTheForgottenRealms getInstance() {
        return instance;
    }

    private AdventuresInTheForgottenRealms() {
        super("Adventures in the Forgotten Realms", "AFR", ExpansionSet.buildDate(2021, 7, 23), SetType.EXPANSION);
        this.blockName = "Adventures in the Forgotten Realms";
        this.hasBoosters = true;
        this.hasBasicLands = true;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.maxCardNumberInBooster = 275;

        cards.add(new SetCardInfo("+2 Mace", 1, Rarity.COMMON, mage.cards.p.Plus2Mace.class));
        cards.add(new SetCardInfo("Adult Gold Dragon", 216, Rarity.RARE, mage.cards.a.AdultGoldDragon.class));
        cards.add(new SetCardInfo("Air-Cult Elemental", 45, Rarity.COMMON, mage.cards.a.AirCultElemental.class));
        cards.add(new SetCardInfo("Bag of Holding", 240, Rarity.UNCOMMON, mage.cards.b.BagOfHolding.class));
        cards.add(new SetCardInfo("Baleful Beholder", 89, Rarity.COMMON, mage.cards.b.BalefulBeholder.class));
        cards.add(new SetCardInfo("Bar the Gate", 47, Rarity.COMMON, mage.cards.b.BarTheGate.class));
        cards.add(new SetCardInfo("Battle Cry Goblin", 132, Rarity.UNCOMMON, mage.cards.b.BattleCryGoblin.class));
        cards.add(new SetCardInfo("Black Dragon", 90, Rarity.UNCOMMON, mage.cards.b.BlackDragon.class));
        cards.add(new SetCardInfo("Bruenor Battlehammer", 219, Rarity.UNCOMMON, mage.cards.b.BruenorBattlehammer.class));
        cards.add(new SetCardInfo("Bulette", 173, Rarity.COMMON, mage.cards.b.Bulette.class));
        cards.add(new SetCardInfo("Bull's Strength", 174, Rarity.COMMON, mage.cards.b.BullsStrength.class));
        cards.add(new SetCardInfo("Cave of the Frost Dragon", 253, Rarity.RARE, mage.cards.c.CaveOfTheFrostDragon.class));
        cards.add(new SetCardInfo("Celestial Unicorn", 5, Rarity.COMMON, mage.cards.c.CelestialUnicorn.class));
        cards.add(new SetCardInfo("Charmed Sleep", 50, Rarity.COMMON, mage.cards.c.CharmedSleep.class));
        cards.add(new SetCardInfo("Choose Your Weapon", 175, Rarity.UNCOMMON, mage.cards.c.ChooseYourWeapon.class));
        cards.add(new SetCardInfo("Circle of Dreams Druid", 176, Rarity.RARE, mage.cards.c.CircleOfDreamsDruid.class));
        cards.add(new SetCardInfo("Clattering Skeletons", 93, Rarity.COMMON, mage.cards.c.ClatteringSkeletons.class));
        cards.add(new SetCardInfo("Clever Conjurer", 51, Rarity.COMMON, mage.cards.c.CleverConjurer.class));
        cards.add(new SetCardInfo("Cloister Gargoyle", 7, Rarity.UNCOMMON, mage.cards.c.CloisterGargoyle.class));
        cards.add(new SetCardInfo("Contact Other Plane", 52, Rarity.COMMON, mage.cards.c.ContactOtherPlane.class));
        cards.add(new SetCardInfo("Dawnbringer Cleric", 9, Rarity.COMMON, mage.cards.d.DawnbringerCleric.class));
        cards.add(new SetCardInfo("Delver's Torch", 10, Rarity.COMMON, mage.cards.d.DelversTorch.class));
        cards.add(new SetCardInfo("Den of the Bugbear", 254, Rarity.RARE, mage.cards.d.DenOfTheBugbear.class));
        cards.add(new SetCardInfo("Devoted Paladin", 11, Rarity.COMMON, mage.cards.d.DevotedPaladin.class));
        cards.add(new SetCardInfo("Dire Wolf Prowler", 179, Rarity.COMMON, mage.cards.d.DireWolfProwler.class));
        cards.add(new SetCardInfo("Displacer Beast", 54, Rarity.UNCOMMON, mage.cards.d.DisplacerBeast.class));
        cards.add(new SetCardInfo("Djinni Windseeker", 55, Rarity.COMMON, mage.cards.d.DjinniWindseeker.class));
        cards.add(new SetCardInfo("Dragon Turtle", 56, Rarity.RARE, mage.cards.d.DragonTurtle.class));
        cards.add(new SetCardInfo("Dragon's Disciple", 13, Rarity.UNCOMMON, mage.cards.d.DragonsDisciple.class));
        cards.add(new SetCardInfo("Drizzt Do'Urden", 220, Rarity.RARE, mage.cards.d.DrizztDoUrden.class));
        cards.add(new SetCardInfo("Dueling Rapier", 140, Rarity.COMMON, mage.cards.d.DuelingRapier.class));
        cards.add(new SetCardInfo("Dungeon Crawler", 99, Rarity.UNCOMMON, mage.cards.d.DungeonCrawler.class));
        cards.add(new SetCardInfo("Dungeon Descent", 255, Rarity.RARE, mage.cards.d.DungeonDescent.class));
        cards.add(new SetCardInfo("Dungeon Map", 242, Rarity.UNCOMMON, mage.cards.d.DungeonMap.class));
        cards.add(new SetCardInfo("Dwarfhold Champion", 14, Rarity.COMMON, mage.cards.d.DwarfholdChampion.class));
        cards.add(new SetCardInfo("Ebondeath, Dracolich", 100, Rarity.MYTHIC, mage.cards.e.EbondeathDracolich.class));
        cards.add(new SetCardInfo("Eccentric Apprentice", 57, Rarity.UNCOMMON, mage.cards.e.EccentricApprentice.class));
        cards.add(new SetCardInfo("Ellywick Tumblestrum", 181, Rarity.MYTHIC, mage.cards.e.EllywickTumblestrum.class));
        cards.add(new SetCardInfo("Elturgard Ranger", 182, Rarity.COMMON, mage.cards.e.ElturgardRanger.class));
        cards.add(new SetCardInfo("Evolving Wilds", 256, Rarity.COMMON, mage.cards.e.EvolvingWilds.class));
        cards.add(new SetCardInfo("Fates' Reversal", 102, Rarity.COMMON, mage.cards.f.FatesReversal.class));
        cards.add(new SetCardInfo("Feign Death", 103, Rarity.COMMON, mage.cards.f.FeignDeath.class));
        cards.add(new SetCardInfo("Fifty Feet of Rope", 244, Rarity.UNCOMMON, mage.cards.f.FiftyFeetOfRope.class));
        cards.add(new SetCardInfo("Find the Path", 183, Rarity.COMMON, mage.cards.f.FindThePath.class));
        cards.add(new SetCardInfo("Flumph", 15, Rarity.RARE, mage.cards.f.Flumph.class));
        cards.add(new SetCardInfo("Fly", 59, Rarity.UNCOMMON, mage.cards.f.Fly.class));
        cards.add(new SetCardInfo("Forest", 278, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Froghemoth", 184, Rarity.RARE, mage.cards.f.Froghemoth.class));
        cards.add(new SetCardInfo("Gloom Stalker", 16, Rarity.COMMON, mage.cards.g.GloomStalker.class));
        cards.add(new SetCardInfo("Gnoll Hunter", 185, Rarity.COMMON, mage.cards.g.GnollHunter.class));
        cards.add(new SetCardInfo("Goblin Javelineer", 144, Rarity.COMMON, mage.cards.g.GoblinJavelineer.class));
        cards.add(new SetCardInfo("Goblin Morningstar", 145, Rarity.UNCOMMON, mage.cards.g.GoblinMorningstar.class));
        cards.add(new SetCardInfo("Grazilaxx, Illithid Scholar", 60, Rarity.RARE, mage.cards.g.GrazilaxxIllithidScholar.class));
        cards.add(new SetCardInfo("Green Dragon", 186, Rarity.UNCOMMON, mage.cards.g.GreenDragon.class));
        cards.add(new SetCardInfo("Gretchen Titchwillow", 223, Rarity.UNCOMMON, mage.cards.g.GretchenTitchwillow.class));
        cards.add(new SetCardInfo("Grim Wanderer", 107, Rarity.UNCOMMON, mage.cards.g.GrimWanderer.class));
        cards.add(new SetCardInfo("Guardian of Faith", 18, Rarity.RARE, mage.cards.g.GuardianOfFaith.class));
        cards.add(new SetCardInfo("Guild Thief", 61, Rarity.UNCOMMON, mage.cards.g.GuildThief.class));
        cards.add(new SetCardInfo("Half-Elf Monk", 19, Rarity.COMMON, mage.cards.h.HalfElfMonk.class));
        cards.add(new SetCardInfo("Hama Pashar, Ruin Seeker", 224, Rarity.UNCOMMON, mage.cards.h.HamaPasharRuinSeeker.class));
        cards.add(new SetCardInfo("Hill Giant Herdgorger", 187, Rarity.COMMON, mage.cards.h.HillGiantHerdgorger.class));
        cards.add(new SetCardInfo("Hive of the Eye Tyrant", 258, Rarity.RARE, mage.cards.h.HiveOfTheEyeTyrant.class));
        cards.add(new SetCardInfo("Hoarding Ogre", 146, Rarity.COMMON, mage.cards.h.HoardingOgre.class));
        cards.add(new SetCardInfo("Hobgoblin Captain", 148, Rarity.COMMON, mage.cards.h.HobgoblinCaptain.class));
        cards.add(new SetCardInfo("Hulking Bugbear", 149, Rarity.UNCOMMON, mage.cards.h.HulkingBugbear.class));
        cards.add(new SetCardInfo("Hunter's Mark", 188, Rarity.UNCOMMON, mage.cards.h.HuntersMark.class));
        cards.add(new SetCardInfo("Icingdeath, Frost Tyrant", 20, Rarity.MYTHIC, mage.cards.i.IcingdeathFrostTyrant.class));
        cards.add(new SetCardInfo("Improvised Weaponry", 150, Rarity.COMMON, mage.cards.i.ImprovisedWeaponry.class));
        cards.add(new SetCardInfo("Inspiring Bard", 189, Rarity.COMMON, mage.cards.i.InspiringBard.class));
        cards.add(new SetCardInfo("Intrepid Outlander", 191, Rarity.UNCOMMON, mage.cards.i.IntrepidOutlander.class));
        cards.add(new SetCardInfo("Island", 266, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lightfoot Rogue", 111, Rarity.UNCOMMON, mage.cards.l.LightfootRogue.class));
        cards.add(new SetCardInfo("Lolth, Spider Queen", 112, Rarity.MYTHIC, mage.cards.l.LolthSpiderQueen.class));
        cards.add(new SetCardInfo("Manticore", 113, Rarity.COMMON, mage.cards.m.Manticore.class));
        cards.add(new SetCardInfo("Meteor Swarm", 155, Rarity.RARE, mage.cards.m.MeteorSwarm.class));
        cards.add(new SetCardInfo("Mimic", 249, Rarity.COMMON, mage.cards.m.Mimic.class));
        cards.add(new SetCardInfo("Minion of the Mighty", 156, Rarity.RARE, mage.cards.m.MinionOfTheMighty.class));
        cards.add(new SetCardInfo("Moon-Blessed Cleric", 26, Rarity.UNCOMMON, mage.cards.m.MoonBlessedCleric.class));
        cards.add(new SetCardInfo("Mordenkainen's Polymorph", 65, Rarity.COMMON, mage.cards.m.MordenkainensPolymorph.class));
        cards.add(new SetCardInfo("Mountain", 274, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nadaar, Selfless Paladin", 27, Rarity.RARE, mage.cards.n.NadaarSelflessPaladin.class));
        cards.add(new SetCardInfo("Plains", 262, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Planar Ally", 31, Rarity.COMMON, mage.cards.p.PlanarAlly.class));
        cards.add(new SetCardInfo("Plundering Barbarian", 158, Rarity.COMMON, mage.cards.p.PlunderingBarbarian.class));
        cards.add(new SetCardInfo("Portable Hole", 33, Rarity.UNCOMMON, mage.cards.p.PortableHole.class));
        cards.add(new SetCardInfo("Power Word Kill", 114, Rarity.UNCOMMON, mage.cards.p.PowerWordKill.class));
        cards.add(new SetCardInfo("Prosperous Innkeeper", 200, Rarity.UNCOMMON, mage.cards.p.ProsperousInnkeeper.class));
        cards.add(new SetCardInfo("Purple Worm", 201, Rarity.UNCOMMON, mage.cards.p.PurpleWorm.class));
        cards.add(new SetCardInfo("Ranger's Hawk", 37, Rarity.COMMON, mage.cards.r.RangersHawk.class));
        cards.add(new SetCardInfo("Red Dragon", 160, Rarity.UNCOMMON, mage.cards.r.RedDragon.class));
        cards.add(new SetCardInfo("Rimeshield Frost Giant", 69, Rarity.COMMON, mage.cards.r.RimeshieldFrostGiant.class));
        cards.add(new SetCardInfo("Secret Door", 71, Rarity.COMMON, mage.cards.s.SecretDoor.class));
        cards.add(new SetCardInfo("Shambling Ghast", 119, Rarity.COMMON, mage.cards.s.ShamblingGhast.class));
        cards.add(new SetCardInfo("Shocking Grasp", 72, Rarity.COMMON, mage.cards.s.ShockingGrasp.class));
        cards.add(new SetCardInfo("Shortcut Seeker", 73, Rarity.COMMON, mage.cards.s.ShortcutSeeker.class));
        cards.add(new SetCardInfo("Swamp", 270, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swarming Goblins", 162, Rarity.COMMON, mage.cards.s.SwarmingGoblins.class));
        cards.add(new SetCardInfo("Targ Nar, Demon-Fang Gnoll", 234, Rarity.UNCOMMON, mage.cards.t.TargNarDemonFangGnoll.class));
        cards.add(new SetCardInfo("Tasha's Hideous Laughter", 78, Rarity.RARE, mage.cards.t.TashasHideousLaughter.class));
        cards.add(new SetCardInfo("The Deck of Many Things", 241, Rarity.MYTHIC, mage.cards.t.TheDeckOfManyThings.class));
        cards.add(new SetCardInfo("Tiamat", 235, Rarity.MYTHIC, mage.cards.t.Tiamat.class));
        cards.add(new SetCardInfo("Treasure Chest", 252, Rarity.RARE, mage.cards.t.TreasureChest.class));
        cards.add(new SetCardInfo("Trelasarra Moon Dancer", 236, Rarity.UNCOMMON, mage.cards.t.TrelasarraMoonDancer.class));
        cards.add(new SetCardInfo("Varis, Silverymoon Ranger", 209, Rarity.RARE, mage.cards.v.VarisSilverymoonRanger.class));
        cards.add(new SetCardInfo("Veteran Dungeoneer", 40, Rarity.COMMON, mage.cards.v.VeteranDungeoneer.class));
        cards.add(new SetCardInfo("Vorpal Sword", 124, Rarity.RARE, mage.cards.v.VorpalSword.class));
        cards.add(new SetCardInfo("Werewolf Pack Leader", 211, Rarity.RARE, mage.cards.w.WerewolfPackLeader.class));
        cards.add(new SetCardInfo("You Come to a River", 83, Rarity.COMMON, mage.cards.y.YouComeToARiver.class));
        cards.add(new SetCardInfo("You Come to the Gnoll Camp", 168, Rarity.COMMON, mage.cards.y.YouComeToTheGnollCamp.class));
        cards.add(new SetCardInfo("You Find a Cursed Idol", 213, Rarity.COMMON, mage.cards.y.YouFindACursedIdol.class));
        cards.add(new SetCardInfo("You Find the Villains' Lair", 84, Rarity.COMMON, mage.cards.y.YouFindTheVillainsLair.class));
        cards.add(new SetCardInfo("You Hear Something on Watch", 42, Rarity.COMMON, mage.cards.y.YouHearSomethingOnWatch.class));
        cards.add(new SetCardInfo("You See a Guard Approach", 85, Rarity.COMMON, mage.cards.y.YouSeeAGuardApproach.class));
        cards.add(new SetCardInfo("You See a Pair of Goblins", 170, Rarity.UNCOMMON, mage.cards.y.YouSeeAPairOfGoblins.class));
        cards.add(new SetCardInfo("You're Ambushed on the Road", 43, Rarity.COMMON, mage.cards.y.YoureAmbushedOnTheRoad.class));
        cards.add(new SetCardInfo("Yuan-Ti Fang-Blade", 128, Rarity.COMMON, mage.cards.y.YuanTiFangBlade.class));
        cards.add(new SetCardInfo("Zalto, Fire Giant Duke", 171, Rarity.RARE, mage.cards.z.ZaltoFireGiantDuke.class));
        cards.add(new SetCardInfo("Zombie Ogre", 129, Rarity.COMMON, mage.cards.z.ZombieOgre.class));
    }
}

