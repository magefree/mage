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
public final class AdventuresInTheForgottenRealms extends ExpansionSet {

    private static final AdventuresInTheForgottenRealms instance = new AdventuresInTheForgottenRealms();

    public static AdventuresInTheForgottenRealms getInstance() {
        return instance;
    }

    private AdventuresInTheForgottenRealms() {
        super("Adventures in the Forgotten Realms", "AFR", ExpansionSet.buildDate(2021, 7, 23), SetType.EXPANSION, new AdventuresInTheForgottenRealmsCollator());
        this.blockName = "Adventures in the Forgotten Realms";
        this.hasBoosters = true;
        this.hasBasicLands = true;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.maxCardNumberInBooster = 275;

        cards.add(new SetCardInfo("+2 Mace", 1, Rarity.COMMON, mage.cards.p.Plus2Mace.class));
        cards.add(new SetCardInfo("Aberrant Mind Sorcerer", 44, Rarity.UNCOMMON, mage.cards.a.AberrantMindSorcerer.class));
        cards.add(new SetCardInfo("Acererak the Archlich", 372, Rarity.MYTHIC, mage.cards.a.AcererakTheArchlich.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Acererak the Archlich", 87, Rarity.MYTHIC, mage.cards.a.AcererakTheArchlich.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Adult Gold Dragon", 216, Rarity.RARE, mage.cards.a.AdultGoldDragon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Adult Gold Dragon", 297, Rarity.RARE, mage.cards.a.AdultGoldDragon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Air-Cult Elemental", 45, Rarity.COMMON, mage.cards.a.AirCultElemental.class));
        cards.add(new SetCardInfo("Arborea Pegasus", 2, Rarity.COMMON, mage.cards.a.ArboreaPegasus.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Arborea Pegasus", 299, Rarity.COMMON, mage.cards.a.ArboreaPegasus.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Arcane Investigator", 46, Rarity.COMMON, mage.cards.a.ArcaneInvestigator.class));
        cards.add(new SetCardInfo("Armory Veteran", 130, Rarity.COMMON, mage.cards.a.ArmoryVeteran.class));
        cards.add(new SetCardInfo("Asmodeus the Archfiend", 373, Rarity.RARE, mage.cards.a.AsmodeusTheArchfiend.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Asmodeus the Archfiend", 88, Rarity.RARE, mage.cards.a.AsmodeusTheArchfiend.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bag of Holding", 240, Rarity.UNCOMMON, mage.cards.b.BagOfHolding.class));
        cards.add(new SetCardInfo("Baleful Beholder", 311, Rarity.COMMON, mage.cards.b.BalefulBeholder.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Baleful Beholder", 89, Rarity.COMMON, mage.cards.b.BalefulBeholder.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bar the Gate", 47, Rarity.COMMON, mage.cards.b.BarTheGate.class));
        cards.add(new SetCardInfo("Barbarian Class", 131, Rarity.UNCOMMON, mage.cards.b.BarbarianClass.class));
        cards.add(new SetCardInfo("Bard Class", 217, Rarity.RARE, mage.cards.b.BardClass.class));
        cards.add(new SetCardInfo("Barrowin of Clan Undurr", 218, Rarity.UNCOMMON, mage.cards.b.BarrowinOfClanUndurr.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Barrowin of Clan Undurr", 336, Rarity.UNCOMMON, mage.cards.b.BarrowinOfClanUndurr.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Battle Cry Goblin", 132, Rarity.UNCOMMON, mage.cards.b.BattleCryGoblin.class));
        cards.add(new SetCardInfo("Black Dragon", 291, Rarity.UNCOMMON, mage.cards.b.BlackDragon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Black Dragon", 90, Rarity.UNCOMMON, mage.cards.b.BlackDragon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Blink Dog", 3, Rarity.UNCOMMON, mage.cards.b.BlinkDog.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Blink Dog", 300, Rarity.UNCOMMON, mage.cards.b.BlinkDog.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Blue Dragon", 289, Rarity.UNCOMMON, mage.cards.b.BlueDragon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Blue Dragon", 49, Rarity.UNCOMMON, mage.cards.b.BlueDragon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Boots of Speed", 133, Rarity.COMMON, mage.cards.b.BootsOfSpeed.class));
        cards.add(new SetCardInfo("Brazen Dwarf", 134, Rarity.COMMON, mage.cards.b.BrazenDwarf.class));
        cards.add(new SetCardInfo("Bruenor Battlehammer", 219, Rarity.UNCOMMON, mage.cards.b.BruenorBattlehammer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bruenor Battlehammer", 337, Rarity.UNCOMMON, mage.cards.b.BruenorBattlehammer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bulette", 173, Rarity.COMMON, mage.cards.b.Bulette.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bulette", 324, Rarity.COMMON, mage.cards.b.Bulette.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bull's Strength", 174, Rarity.COMMON, mage.cards.b.BullsStrength.class));
        cards.add(new SetCardInfo("Burning Hands", 135, Rarity.UNCOMMON, mage.cards.b.BurningHands.class));
        cards.add(new SetCardInfo("Cave of the Frost Dragon", 253, Rarity.RARE, mage.cards.c.CaveOfTheFrostDragon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cave of the Frost Dragon", 350, Rarity.RARE, mage.cards.c.CaveOfTheFrostDragon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Celestial Unicorn", 301, Rarity.COMMON, mage.cards.c.CelestialUnicorn.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Celestial Unicorn", 5, Rarity.COMMON, mage.cards.c.CelestialUnicorn.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Chaos Channeler", 136, Rarity.UNCOMMON, mage.cards.c.ChaosChanneler.class));
        cards.add(new SetCardInfo("Charmed Sleep", 50, Rarity.COMMON, mage.cards.c.CharmedSleep.class));
        cards.add(new SetCardInfo("Check for Traps", 92, Rarity.UNCOMMON, mage.cards.c.CheckForTraps.class));
        cards.add(new SetCardInfo("Choose Your Weapon", 175, Rarity.UNCOMMON, mage.cards.c.ChooseYourWeapon.class));
        cards.add(new SetCardInfo("Circle of Dreams Druid", 176, Rarity.RARE, mage.cards.c.CircleOfDreamsDruid.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Circle of Dreams Druid", 383, Rarity.RARE, mage.cards.c.CircleOfDreamsDruid.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Circle of the Moon Druid", 177, Rarity.COMMON, mage.cards.c.CircleOfTheMoonDruid.class));
        cards.add(new SetCardInfo("Clattering Skeletons", 312, Rarity.COMMON, mage.cards.c.ClatteringSkeletons.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Clattering Skeletons", 93, Rarity.COMMON, mage.cards.c.ClatteringSkeletons.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cleric Class", 6, Rarity.UNCOMMON, mage.cards.c.ClericClass.class));
        cards.add(new SetCardInfo("Clever Conjurer", 51, Rarity.COMMON, mage.cards.c.CleverConjurer.class));
        cards.add(new SetCardInfo("Cloister Gargoyle", 302, Rarity.UNCOMMON, mage.cards.c.CloisterGargoyle.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cloister Gargoyle", 7, Rarity.UNCOMMON, mage.cards.c.CloisterGargoyle.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Compelled Duel", 178, Rarity.COMMON, mage.cards.c.CompelledDuel.class));
        cards.add(new SetCardInfo("Contact Other Plane", 52, Rarity.COMMON, mage.cards.c.ContactOtherPlane.class));
        cards.add(new SetCardInfo("Critical Hit", 137, Rarity.UNCOMMON, mage.cards.c.CriticalHit.class));
        cards.add(new SetCardInfo("Dancing Sword", 360, Rarity.RARE, mage.cards.d.DancingSword.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dancing Sword", 8, Rarity.RARE, mage.cards.d.DancingSword.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dawnbringer Cleric", 9, Rarity.COMMON, mage.cards.d.DawnbringerCleric.class));
        cards.add(new SetCardInfo("Deadly Dispute", 94, Rarity.COMMON, mage.cards.d.DeadlyDispute.class));
        cards.add(new SetCardInfo("Death-Priest of Myrkul", 95, Rarity.UNCOMMON, mage.cards.d.DeathPriestOfMyrkul.class));
        cards.add(new SetCardInfo("Delina, Wild Mage", 138, Rarity.RARE, mage.cards.d.DelinaWildMage.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Delina, Wild Mage", 317, Rarity.RARE, mage.cards.d.DelinaWildMage.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Delver's Torch", 10, Rarity.COMMON, mage.cards.d.DelversTorch.class));
        cards.add(new SetCardInfo("Demilich", 366, Rarity.MYTHIC, mage.cards.d.Demilich.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Demilich", 53, Rarity.MYTHIC, mage.cards.d.Demilich.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Demogorgon's Clutches", 96, Rarity.UNCOMMON, mage.cards.d.DemogorgonsClutches.class));
        cards.add(new SetCardInfo("Den of the Bugbear", 254, Rarity.RARE, mage.cards.d.DenOfTheBugbear.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Den of the Bugbear", 351, Rarity.RARE, mage.cards.d.DenOfTheBugbear.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Devoted Paladin", 11, Rarity.COMMON, mage.cards.d.DevotedPaladin.class));
        cards.add(new SetCardInfo("Devour Intellect", 97, Rarity.COMMON, mage.cards.d.DevourIntellect.class));
        cards.add(new SetCardInfo("Dire Wolf Prowler", 179, Rarity.COMMON, mage.cards.d.DireWolfProwler.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dire Wolf Prowler", 325, Rarity.COMMON, mage.cards.d.DireWolfProwler.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Displacer Beast", 305, Rarity.UNCOMMON, mage.cards.d.DisplacerBeast.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Displacer Beast", 54, Rarity.UNCOMMON, mage.cards.d.DisplacerBeast.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Divine Smite", 12, Rarity.UNCOMMON, mage.cards.d.DivineSmite.class));
        cards.add(new SetCardInfo("Djinni Windseer", 306, Rarity.COMMON, mage.cards.d.DjinniWindseer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Djinni Windseer", 55, Rarity.COMMON, mage.cards.d.DjinniWindseer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dragon Turtle", 307, Rarity.RARE, mage.cards.d.DragonTurtle.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dragon Turtle", 56, Rarity.RARE, mage.cards.d.DragonTurtle.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dragon's Disciple", 13, Rarity.UNCOMMON, mage.cards.d.DragonsDisciple.class));
        cards.add(new SetCardInfo("Dragon's Fire", 139, Rarity.COMMON, mage.cards.d.DragonsFire.class));
        cards.add(new SetCardInfo("Drider", 98, Rarity.UNCOMMON, mage.cards.d.Drider.class));
        cards.add(new SetCardInfo("Drizzt Do'Urden", 220, Rarity.RARE, mage.cards.d.DrizztDoUrden.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Drizzt Do'Urden", 338, Rarity.RARE, mage.cards.d.DrizztDoUrden.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Druid Class", 180, Rarity.UNCOMMON, mage.cards.d.DruidClass.class));
        cards.add(new SetCardInfo("Dueling Rapier", 140, Rarity.COMMON, mage.cards.d.DuelingRapier.class));
        cards.add(new SetCardInfo("Dungeon Crawler", 99, Rarity.UNCOMMON, mage.cards.d.DungeonCrawler.class));
        cards.add(new SetCardInfo("Dungeon Descent", 255, Rarity.RARE, mage.cards.d.DungeonDescent.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dungeon Descent", 352, Rarity.RARE, mage.cards.d.DungeonDescent.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dungeon Map", 242, Rarity.UNCOMMON, mage.cards.d.DungeonMap.class));
        cards.add(new SetCardInfo("Dwarfhold Champion", 14, Rarity.COMMON, mage.cards.d.DwarfholdChampion.class));
        cards.add(new SetCardInfo("Earth-Cult Elemental", 141, Rarity.COMMON, mage.cards.e.EarthCultElemental.class));
        cards.add(new SetCardInfo("Ebondeath, Dracolich", 100, Rarity.MYTHIC, mage.cards.e.EbondeathDracolich.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ebondeath, Dracolich", 292, Rarity.MYTHIC, mage.cards.e.EbondeathDracolich.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Eccentric Apprentice", 57, Rarity.UNCOMMON, mage.cards.e.EccentricApprentice.class));
        cards.add(new SetCardInfo("Ellywick Tumblestrum", 181, Rarity.MYTHIC, mage.cards.e.EllywickTumblestrum.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ellywick Tumblestrum", 286, Rarity.MYTHIC, mage.cards.e.EllywickTumblestrum.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Elturgard Ranger", 182, Rarity.COMMON, mage.cards.e.ElturgardRanger.class));
        cards.add(new SetCardInfo("Evolving Wilds", 256, Rarity.COMMON, mage.cards.e.EvolvingWilds.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Evolving Wilds", 353, Rarity.COMMON, mage.cards.e.EvolvingWilds.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Eye of Vecna", 243, Rarity.RARE, mage.cards.e.EyeOfVecna.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Eye of Vecna", 393, Rarity.RARE, mage.cards.e.EyeOfVecna.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Eyes of the Beholder", 101, Rarity.COMMON, mage.cards.e.EyesOfTheBeholder.class));
        cards.add(new SetCardInfo("Farideh's Fireball", 142, Rarity.COMMON, mage.cards.f.FaridehsFireball.class));
        cards.add(new SetCardInfo("Farideh, Devil's Chosen", 221, Rarity.UNCOMMON, mage.cards.f.FaridehDevilsChosen.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Farideh, Devil's Chosen", 339, Rarity.UNCOMMON, mage.cards.f.FaridehDevilsChosen.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fates' Reversal", 102, Rarity.COMMON, mage.cards.f.FatesReversal.class));
        cards.add(new SetCardInfo("Feign Death", 103, Rarity.COMMON, mage.cards.f.FeignDeath.class));
        cards.add(new SetCardInfo("Feywild Trickster", 58, Rarity.UNCOMMON, mage.cards.f.FeywildTrickster.class));
        cards.add(new SetCardInfo("Fifty Feet of Rope", 244, Rarity.UNCOMMON, mage.cards.f.FiftyFeetOfRope.class));
        cards.add(new SetCardInfo("Fighter Class", 222, Rarity.RARE, mage.cards.f.FighterClass.class));
        cards.add(new SetCardInfo("Find the Path", 183, Rarity.COMMON, mage.cards.f.FindThePath.class));
        cards.add(new SetCardInfo("Flameskull", 143, Rarity.MYTHIC, mage.cards.f.Flameskull.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Flameskull", 378, Rarity.MYTHIC, mage.cards.f.Flameskull.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Flumph", 15, Rarity.RARE, mage.cards.f.Flumph.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Flumph", 361, Rarity.RARE, mage.cards.f.Flumph.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fly", 59, Rarity.UNCOMMON, mage.cards.f.Fly.class));
        cards.add(new SetCardInfo("Forest", 278, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 279, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 280, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 281, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forsworn Paladin", 104, Rarity.RARE, mage.cards.f.ForswornPaladin.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forsworn Paladin", 375, Rarity.RARE, mage.cards.f.ForswornPaladin.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Froghemoth", 184, Rarity.RARE, mage.cards.f.Froghemoth.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Froghemoth", 384, Rarity.RARE, mage.cards.f.Froghemoth.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gelatinous Cube", 105, Rarity.RARE, mage.cards.g.GelatinousCube.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gelatinous Cube", 313, Rarity.RARE, mage.cards.g.GelatinousCube.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gloom Stalker", 16, Rarity.COMMON, mage.cards.g.GloomStalker.class));
        cards.add(new SetCardInfo("Gnoll Hunter", 185, Rarity.COMMON, mage.cards.g.GnollHunter.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gnoll Hunter", 326, Rarity.COMMON, mage.cards.g.GnollHunter.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Goblin Javelineer", 144, Rarity.COMMON, mage.cards.g.GoblinJavelineer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Goblin Javelineer", 318, Rarity.COMMON, mage.cards.g.GoblinJavelineer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Goblin Morningstar", 145, Rarity.UNCOMMON, mage.cards.g.GoblinMorningstar.class));
        cards.add(new SetCardInfo("Grand Master of Flowers", 17, Rarity.MYTHIC, mage.cards.g.GrandMasterOfFlowers.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Grand Master of Flowers", 282, Rarity.MYTHIC, mage.cards.g.GrandMasterOfFlowers.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Grazilaxx, Illithid Scholar", 367, Rarity.RARE, mage.cards.g.GrazilaxxIllithidScholar.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Grazilaxx, Illithid Scholar", 60, Rarity.RARE, mage.cards.g.GrazilaxxIllithidScholar.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Greataxe", 245, Rarity.COMMON, mage.cards.g.Greataxe.class));
        cards.add(new SetCardInfo("Green Dragon", 186, Rarity.UNCOMMON, mage.cards.g.GreenDragon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Green Dragon", 295, Rarity.UNCOMMON, mage.cards.g.GreenDragon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gretchen Titchwillow", 223, Rarity.UNCOMMON, mage.cards.g.GretchenTitchwillow.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gretchen Titchwillow", 340, Rarity.UNCOMMON, mage.cards.g.GretchenTitchwillow.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Grim Bounty", 106, Rarity.COMMON, mage.cards.g.GrimBounty.class));
        cards.add(new SetCardInfo("Grim Wanderer", 107, Rarity.UNCOMMON, mage.cards.g.GrimWanderer.class));
        cards.add(new SetCardInfo("Guardian of Faith", 18, Rarity.RARE, mage.cards.g.GuardianOfFaith.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Guardian of Faith", 362, Rarity.RARE, mage.cards.g.GuardianOfFaith.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Guild Thief", 61, Rarity.UNCOMMON, mage.cards.g.GuildThief.class));
        cards.add(new SetCardInfo("Half-Elf Monk", 19, Rarity.COMMON, mage.cards.h.HalfElfMonk.class));
        cards.add(new SetCardInfo("Hall of Storm Giants", 257, Rarity.RARE, mage.cards.h.HallOfStormGiants.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hall of Storm Giants", 354, Rarity.RARE, mage.cards.h.HallOfStormGiants.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hama Pashar, Ruin Seeker", 224, Rarity.UNCOMMON, mage.cards.h.HamaPasharRuinSeeker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hama Pashar, Ruin Seeker", 341, Rarity.UNCOMMON, mage.cards.h.HamaPasharRuinSeeker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hand of Vecna", 246, Rarity.RARE, mage.cards.h.HandOfVecna.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hand of Vecna", 394, Rarity.RARE, mage.cards.h.HandOfVecna.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Herald of Hadar", 108, Rarity.COMMON, mage.cards.h.HeraldOfHadar.class));
        cards.add(new SetCardInfo("Hill Giant Herdgorger", 187, Rarity.COMMON, mage.cards.h.HillGiantHerdgorger.class));
        cards.add(new SetCardInfo("Hired Hexblade", 109, Rarity.COMMON, mage.cards.h.HiredHexblade.class));
        cards.add(new SetCardInfo("Hive of the Eye Tyrant", 258, Rarity.RARE, mage.cards.h.HiveOfTheEyeTyrant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hive of the Eye Tyrant", 355, Rarity.RARE, mage.cards.h.HiveOfTheEyeTyrant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hoard Robber", 110, Rarity.COMMON, mage.cards.h.HoardRobber.class));
        cards.add(new SetCardInfo("Hoarding Ogre", 146, Rarity.COMMON, mage.cards.h.HoardingOgre.class));
        cards.add(new SetCardInfo("Hobgoblin Bandit Lord", 147, Rarity.RARE, mage.cards.h.HobgoblinBanditLord.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hobgoblin Bandit Lord", 379, Rarity.RARE, mage.cards.h.HobgoblinBanditLord.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hobgoblin Captain", 148, Rarity.COMMON, mage.cards.h.HobgoblinCaptain.class));
        cards.add(new SetCardInfo("Hulking Bugbear", 149, Rarity.UNCOMMON, mage.cards.h.HulkingBugbear.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hulking Bugbear", 319, Rarity.UNCOMMON, mage.cards.h.HulkingBugbear.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hunter's Mark", 188, Rarity.UNCOMMON, mage.cards.h.HuntersMark.class));
        cards.add(new SetCardInfo("Icingdeath, Frost Tyrant", 20, Rarity.MYTHIC, mage.cards.i.IcingdeathFrostTyrant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Icingdeath, Frost Tyrant", 287, Rarity.MYTHIC, mage.cards.i.IcingdeathFrostTyrant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Improvised Weaponry", 150, Rarity.COMMON, mage.cards.i.ImprovisedWeaponry.class));
        cards.add(new SetCardInfo("Inferno of the Star Mounts", 151, Rarity.MYTHIC, mage.cards.i.InfernoOfTheStarMounts.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Inferno of the Star Mounts", 293, Rarity.MYTHIC, mage.cards.i.InfernoOfTheStarMounts.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ingenious Smith", 21, Rarity.UNCOMMON, mage.cards.i.IngeniousSmith.class));
        cards.add(new SetCardInfo("Inspiring Bard", 189, Rarity.COMMON, mage.cards.i.InspiringBard.class));
        cards.add(new SetCardInfo("Instrument of the Bards", 190, Rarity.RARE, mage.cards.i.InstrumentOfTheBards.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Instrument of the Bards", 385, Rarity.RARE, mage.cards.i.InstrumentOfTheBards.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Intrepid Outlander", 191, Rarity.UNCOMMON, mage.cards.i.IntrepidOutlander.class));
        cards.add(new SetCardInfo("Iron Golem", 247, Rarity.UNCOMMON, mage.cards.i.IronGolem.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Iron Golem", 348, Rarity.UNCOMMON, mage.cards.i.IronGolem.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 266, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 267, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 268, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 269, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Iymrith, Desert Doom", 290, Rarity.MYTHIC, mage.cards.i.IymrithDesertDoom.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Iymrith, Desert Doom", 62, Rarity.MYTHIC, mage.cards.i.IymrithDesertDoom.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jaded Sell-Sword", 152, Rarity.COMMON, mage.cards.j.JadedSellSword.class));
        cards.add(new SetCardInfo("Kalain, Reclusive Painter", 225, Rarity.UNCOMMON, mage.cards.k.KalainReclusivePainter.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kalain, Reclusive Painter", 342, Rarity.UNCOMMON, mage.cards.k.KalainReclusivePainter.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Keen-Eared Sentry", 22, Rarity.UNCOMMON, mage.cards.k.KeenEaredSentry.class));
        cards.add(new SetCardInfo("Kick in the Door", 153, Rarity.COMMON, mage.cards.k.KickInTheDoor.class));
        cards.add(new SetCardInfo("Krydle of Baldur's Gate", 226, Rarity.UNCOMMON, mage.cards.k.KrydleOfBaldursGate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Krydle of Baldur's Gate", 343, Rarity.UNCOMMON, mage.cards.k.KrydleOfBaldursGate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lair of the Hydra", 259, Rarity.RARE, mage.cards.l.LairOfTheHydra.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lair of the Hydra", 356, Rarity.RARE, mage.cards.l.LairOfTheHydra.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Leather Armor", 248, Rarity.COMMON, mage.cards.l.LeatherArmor.class));
        cards.add(new SetCardInfo("Lightfoot Rogue", 111, Rarity.UNCOMMON, mage.cards.l.LightfootRogue.class));
        cards.add(new SetCardInfo("Loathsome Troll", 192, Rarity.UNCOMMON, mage.cards.l.LoathsomeTroll.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Loathsome Troll", 327, Rarity.UNCOMMON, mage.cards.l.LoathsomeTroll.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lolth, Spider Queen", 112, Rarity.MYTHIC, mage.cards.l.LolthSpiderQueen.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lolth, Spider Queen", 284, Rarity.MYTHIC, mage.cards.l.LolthSpiderQueen.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Long Rest", 193, Rarity.RARE, mage.cards.l.LongRest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Long Rest", 386, Rarity.RARE, mage.cards.l.LongRest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Loyal Warhound", 23, Rarity.RARE, mage.cards.l.LoyalWarhound.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Loyal Warhound", 363, Rarity.RARE, mage.cards.l.LoyalWarhound.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lurking Roper", 194, Rarity.UNCOMMON, mage.cards.l.LurkingRoper.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lurking Roper", 328, Rarity.UNCOMMON, mage.cards.l.LurkingRoper.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Magic Missile", 154, Rarity.UNCOMMON, mage.cards.m.MagicMissile.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Magic Missile", 401, Rarity.UNCOMMON, mage.cards.m.MagicMissile.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Manticore", 113, Rarity.COMMON, mage.cards.m.Manticore.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Manticore", 314, Rarity.COMMON, mage.cards.m.Manticore.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Meteor Swarm", 155, Rarity.RARE, mage.cards.m.MeteorSwarm.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Meteor Swarm", 380, Rarity.RARE, mage.cards.m.MeteorSwarm.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mimic", 249, Rarity.COMMON, mage.cards.m.Mimic.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mimic", 349, Rarity.COMMON, mage.cards.m.Mimic.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mind Flayer", 308, Rarity.RARE, mage.cards.m.MindFlayer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mind Flayer", 63, Rarity.RARE, mage.cards.m.MindFlayer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Minimus Containment", 24, Rarity.COMMON, mage.cards.m.MinimusContainment.class));
        cards.add(new SetCardInfo("Minion of the Mighty", 156, Rarity.RARE, mage.cards.m.MinionOfTheMighty.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Minion of the Mighty", 320, Rarity.RARE, mage.cards.m.MinionOfTheMighty.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Minsc, Beloved Ranger", 227, Rarity.MYTHIC, mage.cards.m.MinscBelovedRanger.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Minsc, Beloved Ranger", 344, Rarity.MYTHIC, mage.cards.m.MinscBelovedRanger.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Monk Class", 228, Rarity.RARE, mage.cards.m.MonkClass.class));
        cards.add(new SetCardInfo("Monk of the Open Hand", 25, Rarity.UNCOMMON, mage.cards.m.MonkOfTheOpenHand.class));
        cards.add(new SetCardInfo("Moon-Blessed Cleric", 26, Rarity.UNCOMMON, mage.cards.m.MoonBlessedCleric.class));
        cards.add(new SetCardInfo("Mordenkainen", 283, Rarity.MYTHIC, mage.cards.m.Mordenkainen.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mordenkainen", 64, Rarity.MYTHIC, mage.cards.m.Mordenkainen.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mordenkainen's Polymorph", 65, Rarity.COMMON, mage.cards.m.MordenkainensPolymorph.class));
        cards.add(new SetCardInfo("Mountain", 274, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 275, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 276, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 277, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nadaar, Selfless Paladin", 27, Rarity.RARE, mage.cards.n.NadaarSelflessPaladin.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nadaar, Selfless Paladin", 303, Rarity.RARE, mage.cards.n.NadaarSelflessPaladin.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Neverwinter Dryad", 195, Rarity.COMMON, mage.cards.n.NeverwinterDryad.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Neverwinter Dryad", 329, Rarity.COMMON, mage.cards.n.NeverwinterDryad.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ochre Jelly", 196, Rarity.RARE, mage.cards.o.OchreJelly.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ochre Jelly", 330, Rarity.RARE, mage.cards.o.OchreJelly.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Old Gnawbone", 197, Rarity.MYTHIC, mage.cards.o.OldGnawbone.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Old Gnawbone", 296, Rarity.MYTHIC, mage.cards.o.OldGnawbone.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Orb of Dragonkind", 157, Rarity.RARE, mage.cards.o.OrbOfDragonkind.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Orb of Dragonkind", 381, Rarity.RARE, mage.cards.o.OrbOfDragonkind.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Orcus, Prince of Undeath", 229, Rarity.RARE, mage.cards.o.OrcusPrinceOfUndeath.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Orcus, Prince of Undeath", 388, Rarity.RARE, mage.cards.o.OrcusPrinceOfUndeath.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Oswald Fiddlebender", 28, Rarity.RARE, mage.cards.o.OswaldFiddlebender.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Oswald Fiddlebender", 304, Rarity.RARE, mage.cards.o.OswaldFiddlebender.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Owlbear", 198, Rarity.COMMON, mage.cards.o.Owlbear.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Owlbear", 331, Rarity.COMMON, mage.cards.o.Owlbear.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Paladin Class", 29, Rarity.RARE, mage.cards.p.PaladinClass.class));
        cards.add(new SetCardInfo("Paladin's Shield", 30, Rarity.COMMON, mage.cards.p.PaladinsShield.class));
        cards.add(new SetCardInfo("Pixie Guide", 309, Rarity.COMMON, mage.cards.p.PixieGuide.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Pixie Guide", 66, Rarity.COMMON, mage.cards.p.PixieGuide.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 262, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 263, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 264, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 265, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Planar Ally", 31, Rarity.COMMON, mage.cards.p.PlanarAlly.class));
        cards.add(new SetCardInfo("Plate Armor", 32, Rarity.UNCOMMON, mage.cards.p.PlateArmor.class));
        cards.add(new SetCardInfo("Plummet", 199, Rarity.COMMON, mage.cards.p.Plummet.class));
        cards.add(new SetCardInfo("Plundering Barbarian", 158, Rarity.COMMON, mage.cards.p.PlunderingBarbarian.class));
        cards.add(new SetCardInfo("Portable Hole", 33, Rarity.UNCOMMON, mage.cards.p.PortableHole.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Portable Hole", 398, Rarity.UNCOMMON, mage.cards.p.PortableHole.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Potion of Healing", 34, Rarity.COMMON, mage.cards.p.PotionOfHealing.class));
        cards.add(new SetCardInfo("Power Word Kill", 114, Rarity.UNCOMMON, mage.cards.p.PowerWordKill.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Power Word Kill", 400, Rarity.UNCOMMON, mage.cards.p.PowerWordKill.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Power of Persuasion", 67, Rarity.UNCOMMON, mage.cards.p.PowerOfPersuasion.class));
        cards.add(new SetCardInfo("Precipitous Drop", 115, Rarity.COMMON, mage.cards.p.PrecipitousDrop.class));
        cards.add(new SetCardInfo("Price of Loyalty", 159, Rarity.COMMON, mage.cards.p.PriceOfLoyalty.class));
        cards.add(new SetCardInfo("Priest of Ancient Lore", 35, Rarity.COMMON, mage.cards.p.PriestOfAncientLore.class));
        cards.add(new SetCardInfo("Prosperous Innkeeper", 200, Rarity.UNCOMMON, mage.cards.p.ProsperousInnkeeper.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Prosperous Innkeeper", 402, Rarity.UNCOMMON, mage.cards.p.ProsperousInnkeeper.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Purple Worm", 201, Rarity.UNCOMMON, mage.cards.p.PurpleWorm.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Purple Worm", 332, Rarity.UNCOMMON, mage.cards.p.PurpleWorm.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rally Maneuver", 36, Rarity.UNCOMMON, mage.cards.r.RallyManeuver.class));
        cards.add(new SetCardInfo("Ranger Class", 202, Rarity.RARE, mage.cards.r.RangerClass.class));
        cards.add(new SetCardInfo("Ranger's Hawk", 37, Rarity.COMMON, mage.cards.r.RangersHawk.class));
        cards.add(new SetCardInfo("Ranger's Longbow", 203, Rarity.COMMON, mage.cards.r.RangersLongbow.class));
        cards.add(new SetCardInfo("Ray of Enfeeblement", 116, Rarity.UNCOMMON, mage.cards.r.RayOfEnfeeblement.class));
        cards.add(new SetCardInfo("Ray of Frost", 68, Rarity.UNCOMMON, mage.cards.r.RayOfFrost.class));
        cards.add(new SetCardInfo("Reaper's Talisman", 117, Rarity.UNCOMMON, mage.cards.r.ReapersTalisman.class));
        cards.add(new SetCardInfo("Red Dragon", 160, Rarity.UNCOMMON, mage.cards.r.RedDragon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Red Dragon", 294, Rarity.UNCOMMON, mage.cards.r.RedDragon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rimeshield Frost Giant", 310, Rarity.COMMON, mage.cards.r.RimeshieldFrostGiant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rimeshield Frost Giant", 69, Rarity.COMMON, mage.cards.r.RimeshieldFrostGiant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rogue Class", 230, Rarity.RARE, mage.cards.r.RogueClass.class));
        cards.add(new SetCardInfo("Rust Monster", 161, Rarity.UNCOMMON, mage.cards.r.RustMonster.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rust Monster", 321, Rarity.UNCOMMON, mage.cards.r.RustMonster.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Scaled Herbalist", 204, Rarity.COMMON, mage.cards.s.ScaledHerbalist.class));
        cards.add(new SetCardInfo("Scion of Stygia", 70, Rarity.COMMON, mage.cards.s.ScionOfStygia.class));
        cards.add(new SetCardInfo("Secret Door", 71, Rarity.COMMON, mage.cards.s.SecretDoor.class));
        cards.add(new SetCardInfo("Sepulcher Ghoul", 118, Rarity.COMMON, mage.cards.s.SepulcherGhoul.class));
        cards.add(new SetCardInfo("Shambling Ghast", 119, Rarity.COMMON, mage.cards.s.ShamblingGhast.class));
        cards.add(new SetCardInfo("Shessra, Death's Whisper", 231, Rarity.UNCOMMON, mage.cards.s.ShessraDeathsWhisper.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shessra, Death's Whisper", 345, Rarity.UNCOMMON, mage.cards.s.ShessraDeathsWhisper.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shocking Grasp", 72, Rarity.COMMON, mage.cards.s.ShockingGrasp.class));
        cards.add(new SetCardInfo("Shortcut Seeker", 73, Rarity.COMMON, mage.cards.s.ShortcutSeeker.class));
        cards.add(new SetCardInfo("Silver Raven", 74, Rarity.COMMON, mage.cards.s.SilverRaven.class));
        cards.add(new SetCardInfo("Skeletal Swarming", 232, Rarity.RARE, mage.cards.s.SkeletalSwarming.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Skeletal Swarming", 389, Rarity.RARE, mage.cards.s.SkeletalSwarming.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Skullport Merchant", 120, Rarity.UNCOMMON, mage.cards.s.SkullportMerchant.class));
        cards.add(new SetCardInfo("Sorcerer Class", 233, Rarity.RARE, mage.cards.s.SorcererClass.class));
        cards.add(new SetCardInfo("Soulknife Spy", 75, Rarity.COMMON, mage.cards.s.SoulknifeSpy.class));
        cards.add(new SetCardInfo("Spare Dagger", 250, Rarity.COMMON, mage.cards.s.SpareDagger.class));
        cards.add(new SetCardInfo("Sphere of Annihilation", 121, Rarity.RARE, mage.cards.s.SphereOfAnnihilation.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sphere of Annihilation", 376, Rarity.RARE, mage.cards.s.SphereOfAnnihilation.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Spiked Pit Trap", 251, Rarity.COMMON, mage.cards.s.SpikedPitTrap.class));
        cards.add(new SetCardInfo("Split the Party", 76, Rarity.UNCOMMON, mage.cards.s.SplitTheParty.class));
        cards.add(new SetCardInfo("Spoils of the Hunt", 205, Rarity.COMMON, mage.cards.s.SpoilsOfTheHunt.class));
        cards.add(new SetCardInfo("Steadfast Paladin", 38, Rarity.COMMON, mage.cards.s.SteadfastPaladin.class));
        cards.add(new SetCardInfo("Sudden Insight", 77, Rarity.UNCOMMON, mage.cards.s.SuddenInsight.class));
        cards.add(new SetCardInfo("Swamp", 270, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 271, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 272, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 273, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swarming Goblins", 162, Rarity.COMMON, mage.cards.s.SwarmingGoblins.class));
        cards.add(new SetCardInfo("Sylvan Shepherd", 206, Rarity.COMMON, mage.cards.s.SylvanShepherd.class));
        cards.add(new SetCardInfo("Targ Nar, Demon-Fang Gnoll", 234, Rarity.UNCOMMON, mage.cards.t.TargNarDemonFangGnoll.class));
        cards.add(new SetCardInfo("Tasha's Hideous Laughter", 368, Rarity.RARE, mage.cards.t.TashasHideousLaughter.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tasha's Hideous Laughter", 78, Rarity.RARE, mage.cards.t.TashasHideousLaughter.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Teleportation Circle", 364, Rarity.RARE, mage.cards.t.TeleportationCircle.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Teleportation Circle", 39, Rarity.RARE, mage.cards.t.TeleportationCircle.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Temple of the Dragon Queen", 260, Rarity.UNCOMMON, mage.cards.t.TempleOfTheDragonQueen.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Temple of the Dragon Queen", 357, Rarity.UNCOMMON, mage.cards.t.TempleOfTheDragonQueen.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Blackstaff of Waterdeep", 365, Rarity.RARE, mage.cards.t.TheBlackstaffOfWaterdeep.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Blackstaff of Waterdeep", 48, Rarity.RARE, mage.cards.t.TheBlackstaffOfWaterdeep.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Book of Exalted Deeds", 359, Rarity.MYTHIC, mage.cards.t.TheBookOfExaltedDeeds.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Book of Exalted Deeds", 4, Rarity.MYTHIC, mage.cards.t.TheBookOfExaltedDeeds.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Book of Vile Darkness", 374, Rarity.MYTHIC, mage.cards.t.TheBookOfVileDarkness.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Book of Vile Darkness", 91, Rarity.MYTHIC, mage.cards.t.TheBookOfVileDarkness.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Deck of Many Things", 241, Rarity.MYTHIC, mage.cards.t.TheDeckOfManyThings.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Deck of Many Things", 392, Rarity.MYTHIC, mage.cards.t.TheDeckOfManyThings.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Tarrasque", 207, Rarity.MYTHIC, mage.cards.t.TheTarrasque.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Tarrasque", 333, Rarity.MYTHIC, mage.cards.t.TheTarrasque.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thieves' Tools", 122, Rarity.COMMON, mage.cards.t.ThievesTools.class));
        cards.add(new SetCardInfo("Tiamat", 235, Rarity.MYTHIC, mage.cards.t.Tiamat.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tiamat", 298, Rarity.MYTHIC, mage.cards.t.Tiamat.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tiger-Tribe Hunter", 163, Rarity.UNCOMMON, mage.cards.t.TigerTribeHunter.class));
        cards.add(new SetCardInfo("Treasure Chest", 252, Rarity.RARE, mage.cards.t.TreasureChest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Treasure Chest", 395, Rarity.RARE, mage.cards.t.TreasureChest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Treasure Chest", 397, Rarity.RARE, mage.cards.t.TreasureChest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Treasure Vault", 261, Rarity.RARE, mage.cards.t.TreasureVault.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Treasure Vault", 358, Rarity.RARE, mage.cards.t.TreasureVault.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Trelasarra, Moon Dancer", 236, Rarity.UNCOMMON, mage.cards.t.TrelasarraMoonDancer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Trelasarra, Moon Dancer", 346, Rarity.UNCOMMON, mage.cards.t.TrelasarraMoonDancer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Trickster's Talisman", 79, Rarity.UNCOMMON, mage.cards.t.TrickstersTalisman.class));
        cards.add(new SetCardInfo("Triumphant Adventurer", 237, Rarity.RARE, mage.cards.t.TriumphantAdventurer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Triumphant Adventurer", 390, Rarity.RARE, mage.cards.t.TriumphantAdventurer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("True Polymorph", 369, Rarity.RARE, mage.cards.t.TruePolymorph.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("True Polymorph", 80, Rarity.RARE, mage.cards.t.TruePolymorph.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Underdark Basilisk", 208, Rarity.COMMON, mage.cards.u.UnderdarkBasilisk.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Underdark Basilisk", 334, Rarity.COMMON, mage.cards.u.UnderdarkBasilisk.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Unexpected Windfall", 164, Rarity.COMMON, mage.cards.u.UnexpectedWindfall.class));
        cards.add(new SetCardInfo("Valor Singer", 165, Rarity.COMMON, mage.cards.v.ValorSinger.class));
        cards.add(new SetCardInfo("Vampire Spawn", 123, Rarity.COMMON, mage.cards.v.VampireSpawn.class));
        cards.add(new SetCardInfo("Varis, Silverymoon Ranger", 209, Rarity.RARE, mage.cards.v.VarisSilverymoonRanger.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Varis, Silverymoon Ranger", 335, Rarity.RARE, mage.cards.v.VarisSilverymoonRanger.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Veteran Dungeoneer", 40, Rarity.COMMON, mage.cards.v.VeteranDungeoneer.class));
        cards.add(new SetCardInfo("Volo, Guide to Monsters", 238, Rarity.RARE, mage.cards.v.VoloGuideToMonsters.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Volo, Guide to Monsters", 347, Rarity.RARE, mage.cards.v.VoloGuideToMonsters.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vorpal Sword", 124, Rarity.RARE, mage.cards.v.VorpalSword.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vorpal Sword", 377, Rarity.RARE, mage.cards.v.VorpalSword.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vorpal Sword", 396, Rarity.RARE, mage.cards.v.VorpalSword.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wandering Troubadour", 210, Rarity.UNCOMMON, mage.cards.w.WanderingTroubadour.class));
        cards.add(new SetCardInfo("Warlock Class", 125, Rarity.UNCOMMON, mage.cards.w.WarlockClass.class));
        cards.add(new SetCardInfo("Werewolf Pack Leader", 211, Rarity.RARE, mage.cards.w.WerewolfPackLeader.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Werewolf Pack Leader", 387, Rarity.RARE, mage.cards.w.WerewolfPackLeader.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Westgate Regent", 126, Rarity.RARE, mage.cards.w.WestgateRegent.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Westgate Regent", 315, Rarity.RARE, mage.cards.w.WestgateRegent.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("White Dragon", 288, Rarity.UNCOMMON, mage.cards.w.WhiteDragon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("White Dragon", 41, Rarity.UNCOMMON, mage.cards.w.WhiteDragon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wight", 127, Rarity.RARE, mage.cards.w.Wight.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wight", 316, Rarity.RARE, mage.cards.w.Wight.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wild Shape", 212, Rarity.UNCOMMON, mage.cards.w.WildShape.class));
        cards.add(new SetCardInfo("Wish", 166, Rarity.RARE, mage.cards.w.Wish.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wish", 382, Rarity.RARE, mage.cards.w.Wish.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wizard Class", 81, Rarity.UNCOMMON, mage.cards.w.WizardClass.class));
        cards.add(new SetCardInfo("Wizard's Spellbook", 370, Rarity.RARE, mage.cards.w.WizardsSpellbook.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wizard's Spellbook", 82, Rarity.RARE, mage.cards.w.WizardsSpellbook.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Xanathar, Guild Kingpin", 239, Rarity.MYTHIC, mage.cards.x.XanatharGuildKingpin.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Xanathar, Guild Kingpin", 391, Rarity.MYTHIC, mage.cards.x.XanatharGuildKingpin.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Xorn", 167, Rarity.RARE, mage.cards.x.Xorn.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Xorn", 322, Rarity.RARE, mage.cards.x.Xorn.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("You Come to a River", 83, Rarity.COMMON, mage.cards.y.YouComeToARiver.class));
        cards.add(new SetCardInfo("You Come to the Gnoll Camp", 168, Rarity.COMMON, mage.cards.y.YouComeToTheGnollCamp.class));
        cards.add(new SetCardInfo("You Find Some Prisoners", 169, Rarity.UNCOMMON, mage.cards.y.YouFindSomePrisoners.class));
        cards.add(new SetCardInfo("You Find a Cursed Idol", 213, Rarity.COMMON, mage.cards.y.YouFindACursedIdol.class));
        cards.add(new SetCardInfo("You Find the Villains' Lair", 399, Rarity.COMMON, mage.cards.y.YouFindTheVillainsLair.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("You Find the Villains' Lair", 84, Rarity.COMMON, mage.cards.y.YouFindTheVillainsLair.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("You Happen On a Glade", 214, Rarity.UNCOMMON, mage.cards.y.YouHappenOnAGlade.class));
        cards.add(new SetCardInfo("You Hear Something on Watch", 42, Rarity.COMMON, mage.cards.y.YouHearSomethingOnWatch.class));
        cards.add(new SetCardInfo("You Meet in a Tavern", 215, Rarity.UNCOMMON, mage.cards.y.YouMeetInATavern.class));
        cards.add(new SetCardInfo("You See a Guard Approach", 85, Rarity.COMMON, mage.cards.y.YouSeeAGuardApproach.class));
        cards.add(new SetCardInfo("You See a Pair of Goblins", 170, Rarity.UNCOMMON, mage.cards.y.YouSeeAPairOfGoblins.class));
        cards.add(new SetCardInfo("You're Ambushed on the Road", 43, Rarity.COMMON, mage.cards.y.YoureAmbushedOnTheRoad.class));
        cards.add(new SetCardInfo("Yuan-Ti Fang-Blade", 128, Rarity.COMMON, mage.cards.y.YuanTiFangBlade.class));
        cards.add(new SetCardInfo("Yuan-Ti Malison", 371, Rarity.RARE, mage.cards.y.YuanTiMalison.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Yuan-Ti Malison", 86, Rarity.RARE, mage.cards.y.YuanTiMalison.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Zalto, Fire Giant Duke", 171, Rarity.RARE, mage.cards.z.ZaltoFireGiantDuke.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Zalto, Fire Giant Duke", 323, Rarity.RARE, mage.cards.z.ZaltoFireGiantDuke.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Zariel, Archduke of Avernus", 172, Rarity.MYTHIC, mage.cards.z.ZarielArchdukeOfAvernus.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Zariel, Archduke of Avernus", 285, Rarity.MYTHIC, mage.cards.z.ZarielArchdukeOfAvernus.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Zombie Ogre", 129, Rarity.COMMON, mage.cards.z.ZombieOgre.class));
    }
}

// Booster collation info from https://www.lethe.xyz/mtg/collation/afr.html
// Using USA collation for common/uncommon, rare collation inferred from other information
class AdventuresInTheForgottenRealmsCollator implements BoosterCollator {

    private static class AdventuresInTheForgottenRealmsRun extends CardRun {
        private static final AdventuresInTheForgottenRealmsRun commonA = new AdventuresInTheForgottenRealmsRun(true, "144", "30", "122", "85", "153", "250", "164", "199", "133", "38", "141", "251", "101", "74", "245", "153", "122", "85", "30", "318", "250", "133", "199", "38", "153", "245", "74", "101", "251", "141", "30", "85", "164", "122", "144", "101", "251", "133", "199", "164", "250", "85", "141", "38", "74", "144", "30", "245", "122", "164", "38", "133", "250", "153", "248", "144", "101", "245", "74", "199", "141", "251", "30", "250", "122", "153", "85", "38", "164", "199", "133", "245", "101", "251", "141", "74", "153", "85", "133", "250", "318", "122", "164", "30", "199", "101", "251", "38", "85", "122", "245", "164", "74", "30", "141", "199", "101", "153", "38", "144", "250", "133", "245", "74", "164", "85", "122", "251", "141", "250", "144", "30", "153", "38", "133", "199", "74", "251", "141", "245", "101");
        private static final AdventuresInTheForgottenRealmsRun commonB = new AdventuresInTheForgottenRealmsRun(true, "115", "182", "37", "47", "134", "119", "204", "16", "70", "146", "103", "189", "31", "51", "139", "102", "177", "14", "72", "158", "128", "205", "10", "46", "168", "108", "203", "43", "75", "150", "110", "178", "19", "52", "142", "123", "174", "40", "83", "140", "94", "213", "34", "73", "130", "109", "206", "9", "71", "162", "97", "183", "1", "65", "148", "118", "187", "11", "84", "159", "115", "204", "37", "51", "146", "119", "182", "31", "47", "134", "102", "177", "16", "70", "139", "103", "189", "14", "46", "168", "108", "205", "10", "72", "158", "128", "178", "19", "75", "150", "123", "203", "43", "52", "140", "110", "174", "40", "73", "142", "97", "206", "9", "83", "130", "118", "183", "34", "71", "162", "94", "213", "1", "65", "159", "109", "187", "11", "84", "148", "248");
        private static final AdventuresInTheForgottenRealmsRun commonC = new AdventuresInTheForgottenRealmsRun(true, "55", "311", "24", "208", "106", "69", "165", "179", "129", "249", "50", "42", "198", "353", "35", "113", "185", "2", "309", "195", "312", "5", "45", "334", "152", "89", "24", "173", "306", "106", "179", "165", "69", "129", "198", "256", "50", "249", "35", "113", "326", "42", "45", "195", "93", "299", "66", "208", "24", "89", "152", "324", "55", "5", "165", "69", "106", "179", "50", "314", "185", "2", "129", "349", "198", "42", "66", "256", "35", "329", "45", "5", "152", "89", "55", "208", "93", "310", "165", "173", "24", "249", "331", "42", "50", "106", "256", "35", "325", "129", "2", "185", "113", "66", "195", "93", "45", "301", "152", "173");
        private static final AdventuresInTheForgottenRealmsRun uncommonA = new AdventuresInTheForgottenRealmsRun(true, "76", "234", "92", "67", "175", "132", "96", "79", "154", "98", "188", "21", "125", "215", "61", "13", "120", "44", "136", "99", "244", "214", "135", "6", "81", "114", "170", "59", "116", "200", "68", "22", "240", "111", "58", "26", "210", "145", "25", "77", "242", "131", "33", "180", "163", "12", "117", "57", "32", "137", "212", "107", "36", "169", "191", "234", "76", "92", "132", "67", "96", "175", "79", "98", "21", "188", "125", "154", "44", "13", "120", "215", "61", "244", "136", "214", "99", "81", "135", "6", "59", "114", "170", "200", "22", "68", "116", "240", "26", "58", "111", "25", "131", "210", "33", "145", "77", "12", "163", "242", "180", "137", "107", "57", "32", "212", "117", "169", "191", "36");
        private static final AdventuresInTheForgottenRealmsRun uncommonB = new AdventuresInTheForgottenRealmsRun(true, "247", "332", "49", "224", "300", "41", "219", "357", "160", "223", "225", "54", "90", "7", "346", "218", "95", "231", "186", "149", "327", "221", "161", "194", "348", "49", "224", "201", "343", "41", "219", "260", "305", "223", "160", "3", "342", "95", "218", "7", "236", "291", "247", "149", "186", "345", "192", "339", "161", "194", "288", "219", "201", "289", "224", "226", "160", "340", "95", "3", "336", "54", "260", "225", "231", "7", "90", "149", "236", "295", "321", "192", "49", "341", "247", "194", "221", "226", "260", "54", "223", "41", "201", "337", "218", "302", "3", "95", "294", "225", "192", "236", "90", "319", "231", "186", "226", "328", "161", "221");
        private static final AdventuresInTheForgottenRealmsRun rareA = new AdventuresInTheForgottenRealmsRun(false, "87", "53", "100", "181", "143", "17", "20", "151", "62", "112", "227", "64", "197", "4", "91", "241", "207", "235", "239", "172", "216", "88", "217", "253", "176", "8", "138", "254", "56", "220", "255", "243", "222", "15", "104", "184", "105", "60", "18", "257", "246", "258", "147", "190", "259", "193", "23", "155", "63", "156", "228", "27", "196", "157", "229", "28", "29", "202", "230", "232", "233", "121", "78", "39", "48", "252", "261", "237", "80", "209", "238", "124", "211", "126", "127", "166", "82", "167", "86", "171", "216", "88", "217", "253", "176", "8", "138", "254", "56", "220", "255", "243", "222", "15", "104", "184", "105", "60", "18", "257", "246", "258", "147", "190", "259", "193", "23", "155", "63", "156", "228", "27", "196", "157", "229", "28", "29", "202", "230", "232", "233", "121", "78", "39", "48", "252", "261", "237", "80", "209", "238", "124", "211", "126", "127", "166", "82", "167", "86", "171");
        private static final AdventuresInTheForgottenRealmsRun rareB = new AdventuresInTheForgottenRealmsRun(false, "87", "53", "292", "286", "143", "282", "287", "293", "290", "284", "344", "283", "296", "4", "91", "241", "333", "298", "239", "285", "297", "88", "217", "350", "176", "8", "317", "351", "307", "338", "352", "243", "222", "15", "104", "184", "313", "60", "18", "354", "246", "355", "147", "190", "356", "193", "23", "155", "308", "320", "228", "303", "330", "157", "229", "304", "29", "202", "230", "232", "233", "121", "78", "39", "48", "397", "358", "237", "80", "335", "347", "396", "211", "315", "316", "166", "82", "322", "86", "323", "297", "88", "217", "350", "176", "8", "317", "351", "307", "338", "352", "243", "222", "15", "104", "184", "313", "60", "18", "354", "246", "355", "147", "190", "356", "193", "23", "155", "308", "320", "228", "303", "330", "157", "229", "304", "29", "202", "230", "232", "233", "121", "78", "39", "48", "397", "358", "237", "80", "335", "347", "396", "211", "315", "316", "166", "82", "322", "86", "323");
        private static final AdventuresInTheForgottenRealmsRun land = new AdventuresInTheForgottenRealmsRun(false, "262", "263", "264", "265", "266", "267", "268", "269", "270", "271", "272", "273", "274", "275", "276", "277", "278", "279", "280", "281");

        private AdventuresInTheForgottenRealmsRun(boolean keepOrder, String... numbers) {
            super(keepOrder, numbers);
        }
    }

    private static class AdventuresInTheForgottenRealmsStructure extends BoosterStructure {
        private static final AdventuresInTheForgottenRealmsStructure C1 = new AdventuresInTheForgottenRealmsStructure(
                AdventuresInTheForgottenRealmsRun.commonA,
                AdventuresInTheForgottenRealmsRun.commonB,
                AdventuresInTheForgottenRealmsRun.commonB,
                AdventuresInTheForgottenRealmsRun.commonB,
                AdventuresInTheForgottenRealmsRun.commonB,
                AdventuresInTheForgottenRealmsRun.commonB,
                AdventuresInTheForgottenRealmsRun.commonB,
                AdventuresInTheForgottenRealmsRun.commonC,
                AdventuresInTheForgottenRealmsRun.commonC,
                AdventuresInTheForgottenRealmsRun.commonC
        );
        private static final AdventuresInTheForgottenRealmsStructure C2 = new AdventuresInTheForgottenRealmsStructure(
                AdventuresInTheForgottenRealmsRun.commonA,
                AdventuresInTheForgottenRealmsRun.commonA,
                AdventuresInTheForgottenRealmsRun.commonB,
                AdventuresInTheForgottenRealmsRun.commonB,
                AdventuresInTheForgottenRealmsRun.commonB,
                AdventuresInTheForgottenRealmsRun.commonB,
                AdventuresInTheForgottenRealmsRun.commonB,
                AdventuresInTheForgottenRealmsRun.commonC,
                AdventuresInTheForgottenRealmsRun.commonC,
                AdventuresInTheForgottenRealmsRun.commonC
        );
        private static final AdventuresInTheForgottenRealmsStructure C3 = new AdventuresInTheForgottenRealmsStructure(
                AdventuresInTheForgottenRealmsRun.commonA,
                AdventuresInTheForgottenRealmsRun.commonA,
                AdventuresInTheForgottenRealmsRun.commonB,
                AdventuresInTheForgottenRealmsRun.commonB,
                AdventuresInTheForgottenRealmsRun.commonB,
                AdventuresInTheForgottenRealmsRun.commonB,
                AdventuresInTheForgottenRealmsRun.commonB,
                AdventuresInTheForgottenRealmsRun.commonB,
                AdventuresInTheForgottenRealmsRun.commonC,
                AdventuresInTheForgottenRealmsRun.commonC
        );
        private static final AdventuresInTheForgottenRealmsStructure U1 = new AdventuresInTheForgottenRealmsStructure(
                AdventuresInTheForgottenRealmsRun.uncommonA,
                AdventuresInTheForgottenRealmsRun.uncommonA,
                AdventuresInTheForgottenRealmsRun.uncommonA
        );
        private static final AdventuresInTheForgottenRealmsStructure U2 = new AdventuresInTheForgottenRealmsStructure(
                AdventuresInTheForgottenRealmsRun.uncommonB,
                AdventuresInTheForgottenRealmsRun.uncommonB,
                AdventuresInTheForgottenRealmsRun.uncommonB
        );
        private static final AdventuresInTheForgottenRealmsStructure R1 = new AdventuresInTheForgottenRealmsStructure(
                AdventuresInTheForgottenRealmsRun.rareA
        );
        private static final AdventuresInTheForgottenRealmsStructure R2 = new AdventuresInTheForgottenRealmsStructure(
                AdventuresInTheForgottenRealmsRun.rareB
        );
        private static final AdventuresInTheForgottenRealmsStructure L1 = new AdventuresInTheForgottenRealmsStructure(
                AdventuresInTheForgottenRealmsRun.land
        );

        private AdventuresInTheForgottenRealmsStructure(CardRun... runs) {
            super(runs);
        }
    }

    private final RarityConfiguration commonRuns = new RarityConfiguration(
            false,
            AdventuresInTheForgottenRealmsStructure.C1,
            AdventuresInTheForgottenRealmsStructure.C2,
            AdventuresInTheForgottenRealmsStructure.C3
    );
    private final RarityConfiguration uncommonRuns = new RarityConfiguration(
            false,
            AdventuresInTheForgottenRealmsStructure.U1, AdventuresInTheForgottenRealmsStructure.U1,
            AdventuresInTheForgottenRealmsStructure.U1, AdventuresInTheForgottenRealmsStructure.U1,
            AdventuresInTheForgottenRealmsStructure.U1, AdventuresInTheForgottenRealmsStructure.U1,
            AdventuresInTheForgottenRealmsStructure.U1, AdventuresInTheForgottenRealmsStructure.U1,
            AdventuresInTheForgottenRealmsStructure.U1, AdventuresInTheForgottenRealmsStructure.U1,
            AdventuresInTheForgottenRealmsStructure.U1,
            AdventuresInTheForgottenRealmsStructure.U2, AdventuresInTheForgottenRealmsStructure.U2,
            AdventuresInTheForgottenRealmsStructure.U2, AdventuresInTheForgottenRealmsStructure.U2,
            AdventuresInTheForgottenRealmsStructure.U2
    );
    private final RarityConfiguration rareRuns = new RarityConfiguration(
            false,
            AdventuresInTheForgottenRealmsStructure.R1,
            AdventuresInTheForgottenRealmsStructure.R1,
            AdventuresInTheForgottenRealmsStructure.R1,
            AdventuresInTheForgottenRealmsStructure.R2
    );
    private final RarityConfiguration landRuns = new RarityConfiguration(
            AdventuresInTheForgottenRealmsStructure.L1
    );


    @Override
    public void shuffle() {
        commonRuns.shuffle();
        uncommonRuns.shuffle();
        rareRuns.shuffle();
        landRuns.shuffle();
    }

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
