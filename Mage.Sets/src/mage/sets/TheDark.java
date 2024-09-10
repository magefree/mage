package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author North
 */
public final class TheDark extends ExpansionSet {

    private static final TheDark instance = new TheDark();

    public static TheDark getInstance() {
        return instance;
    }

    private TheDark() {
        super("The Dark", "DRK", ExpansionSet.buildDate(1994, 7, 1), SetType.EXPANSION);
        this.hasBasicLands = false;
        this.hasBoosters = true; // note: paper boosters had only 8 cards
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 0;

        cards.add(new SetCardInfo("Amnesia", 20, Rarity.UNCOMMON, mage.cards.a.Amnesia.class));
        cards.add(new SetCardInfo("Angry Mob", 1, Rarity.UNCOMMON, mage.cards.a.AngryMob.class));
        cards.add(new SetCardInfo("Apprentice Wizard", 21, Rarity.RARE, mage.cards.a.ApprenticeWizard.class));
        cards.add(new SetCardInfo("Ashes to Ashes", 39, Rarity.COMMON, mage.cards.a.AshesToAshes.class));
        cards.add(new SetCardInfo("Ball Lightning", 57, Rarity.RARE, mage.cards.b.BallLightning.class));
        cards.add(new SetCardInfo("Banshee", 40, Rarity.UNCOMMON, mage.cards.b.Banshee.class));
        cards.add(new SetCardInfo("Barl's Cage", 96, Rarity.RARE, mage.cards.b.BarlsCage.class));
        cards.add(new SetCardInfo("Blood Moon", 58, Rarity.RARE, mage.cards.b.BloodMoon.class));
        cards.add(new SetCardInfo("Blood of the Martyr", 2, Rarity.UNCOMMON, mage.cards.b.BloodOfTheMartyr.class));
        cards.add(new SetCardInfo("Bog Imp", 41, Rarity.COMMON, mage.cards.b.BogImp.class));
        cards.add(new SetCardInfo("Bog Rats", 42, Rarity.COMMON, mage.cards.b.BogRats.class));
        cards.add(new SetCardInfo("Bone Flute", 97, Rarity.UNCOMMON, mage.cards.b.BoneFlute.class));
        cards.add(new SetCardInfo("Book of Rass", 98, Rarity.UNCOMMON, mage.cards.b.BookOfRass.class));
        cards.add(new SetCardInfo("Brainwash", 3, Rarity.COMMON, mage.cards.b.Brainwash.class));
        cards.add(new SetCardInfo("Brothers of Fire", 59, Rarity.UNCOMMON, mage.cards.b.BrothersOfFire.class));
        cards.add(new SetCardInfo("Carnivorous Plant", 75, Rarity.COMMON, mage.cards.c.CarnivorousPlant.class));
        cards.add(new SetCardInfo("Cave People", 60, Rarity.UNCOMMON, mage.cards.c.CavePeople.class));
        cards.add(new SetCardInfo("City of Shadows", 116, Rarity.RARE, mage.cards.c.CityOfShadows.class));
        cards.add(new SetCardInfo("Cleansing", 4, Rarity.RARE, mage.cards.c.Cleansing.class));
        cards.add(new SetCardInfo("Coal Golem", 99, Rarity.UNCOMMON, mage.cards.c.CoalGolem.class));
        cards.add(new SetCardInfo("Curse Artifact", 43, Rarity.UNCOMMON, mage.cards.c.CurseArtifact.class));
        cards.add(new SetCardInfo("Dance of Many", 22, Rarity.RARE, mage.cards.d.DanceOfMany.class));
        cards.add(new SetCardInfo("Dark Heart of the Wood", 95, Rarity.COMMON, mage.cards.d.DarkHeartOfTheWood.class));
        cards.add(new SetCardInfo("Dark Sphere", 100, Rarity.UNCOMMON, mage.cards.d.DarkSphere.class));
        cards.add(new SetCardInfo("Deep Water", 23, Rarity.COMMON, mage.cards.d.DeepWater.class));
        cards.add(new SetCardInfo("Diabolic Machine", 101, Rarity.UNCOMMON, mage.cards.d.DiabolicMachine.class));
        cards.add(new SetCardInfo("Drowned", 24, Rarity.COMMON, mage.cards.d.Drowned.class));
        cards.add(new SetCardInfo("Dust to Dust", 5, Rarity.COMMON, mage.cards.d.DustToDust.class));
        cards.add(new SetCardInfo("Eater of the Dead", 44, Rarity.UNCOMMON, mage.cards.e.EaterOfTheDead.class));
        cards.add(new SetCardInfo("Electric Eel", 25, Rarity.UNCOMMON, mage.cards.e.ElectricEel.class));
        cards.add(new SetCardInfo("Elves of Deep Shadow", 76, Rarity.UNCOMMON, mage.cards.e.ElvesOfDeepShadow.class));
        cards.add(new SetCardInfo("Erosion", 26, Rarity.COMMON, mage.cards.e.Erosion.class));
        cards.add(new SetCardInfo("Eternal Flame", 61, Rarity.RARE, mage.cards.e.EternalFlame.class));
        cards.add(new SetCardInfo("Exorcist", 6, Rarity.RARE, mage.cards.e.Exorcist.class));
        cards.add(new SetCardInfo("Fasting", 7, Rarity.UNCOMMON, mage.cards.f.Fasting.class));
        cards.add(new SetCardInfo("Fellwar Stone", 102, Rarity.UNCOMMON, mage.cards.f.FellwarStone.class));
        cards.add(new SetCardInfo("Festival", 8, Rarity.COMMON, mage.cards.f.Festival.class));
        cards.add(new SetCardInfo("Fire and Brimstone", 9, Rarity.UNCOMMON, mage.cards.f.FireAndBrimstone.class));
        cards.add(new SetCardInfo("Fire Drake", 62, Rarity.UNCOMMON, mage.cards.f.FireDrake.class));
        cards.add(new SetCardInfo("Fissure", 63, Rarity.COMMON, mage.cards.f.Fissure.class));
        cards.add(new SetCardInfo("Flood", 27, Rarity.UNCOMMON, mage.cards.f.Flood.class));
        cards.add(new SetCardInfo("Fountain of Youth", "103+", Rarity.UNCOMMON, mage.cards.f.FountainOfYouth.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fountain of Youth", 103, Rarity.UNCOMMON, mage.cards.f.FountainOfYouth.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gaea's Touch", "77+", Rarity.COMMON, mage.cards.g.GaeasTouch.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gaea's Touch", 77, Rarity.COMMON, mage.cards.g.GaeasTouch.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ghost Ship", 28, Rarity.COMMON, mage.cards.g.GhostShip.class));
        cards.add(new SetCardInfo("Giant Shark", 29, Rarity.COMMON, mage.cards.g.GiantShark.class));
        cards.add(new SetCardInfo("Goblin Caves", 64, Rarity.COMMON, mage.cards.g.GoblinCaves.class));
        cards.add(new SetCardInfo("Goblin Digging Team", 65, Rarity.COMMON, mage.cards.g.GoblinDiggingTeam.class));
        cards.add(new SetCardInfo("Goblin Hero", 66, Rarity.COMMON, mage.cards.g.GoblinHero.class));
        cards.add(new SetCardInfo("Goblin Rock Sled", 67, Rarity.COMMON, mage.cards.g.GoblinRockSled.class));
        cards.add(new SetCardInfo("Goblin Shrine", 68, Rarity.COMMON, mage.cards.g.GoblinShrine.class));
        cards.add(new SetCardInfo("Goblin Wizard", 69, Rarity.RARE, mage.cards.g.GoblinWizard.class));
        cards.add(new SetCardInfo("Goblins of the Flarg", 70, Rarity.COMMON, mage.cards.g.GoblinsOfTheFlarg.class));
        cards.add(new SetCardInfo("Grave Robbers", 46, Rarity.RARE, mage.cards.g.GraveRobbers.class));
        cards.add(new SetCardInfo("Hidden Path", 78, Rarity.RARE, mage.cards.h.HiddenPath.class));
        cards.add(new SetCardInfo("Holy Light", 10, Rarity.COMMON, mage.cards.h.HolyLight.class));
        cards.add(new SetCardInfo("Inferno", 71, Rarity.RARE, mage.cards.i.Inferno.class));
        cards.add(new SetCardInfo("Inquisition", 47, Rarity.COMMON, mage.cards.i.Inquisition.class));
        cards.add(new SetCardInfo("Knights of Thorn", 11, Rarity.RARE, mage.cards.k.KnightsOfThorn.class));
        cards.add(new SetCardInfo("Land Leeches", 79, Rarity.COMMON, mage.cards.l.LandLeeches.class));
        cards.add(new SetCardInfo("Leviathan", 30, Rarity.RARE, mage.cards.l.Leviathan.class));
        cards.add(new SetCardInfo("Living Armor", 104, Rarity.UNCOMMON, mage.cards.l.LivingArmor.class));
        cards.add(new SetCardInfo("Lurker", 80, Rarity.RARE, mage.cards.l.Lurker.class));
        cards.add(new SetCardInfo("Mana Clash", 72, Rarity.RARE, mage.cards.m.ManaClash.class));
        cards.add(new SetCardInfo("Mana Vortex", 31, Rarity.RARE, mage.cards.m.ManaVortex.class));
        cards.add(new SetCardInfo("Marsh Gas", 48, Rarity.COMMON, mage.cards.m.MarshGas.class));
        cards.add(new SetCardInfo("Marsh Goblins", 93, Rarity.COMMON, mage.cards.m.MarshGoblins.class));
        cards.add(new SetCardInfo("Marsh Viper", 81, Rarity.COMMON, mage.cards.m.MarshViper.class));
        cards.add(new SetCardInfo("Martyr's Cry", 12, Rarity.RARE, mage.cards.m.MartyrsCry.class));
        cards.add(new SetCardInfo("Maze of Ith", 117, Rarity.UNCOMMON, mage.cards.m.MazeOfIth.class));
        cards.add(new SetCardInfo("Merfolk Assassin", 32, Rarity.UNCOMMON, mage.cards.m.MerfolkAssassin.class));
        cards.add(new SetCardInfo("Mind Bomb", 33, Rarity.RARE, mage.cards.m.MindBomb.class));
        cards.add(new SetCardInfo("Miracle Worker", 13, Rarity.COMMON, mage.cards.m.MiracleWorker.class));
        cards.add(new SetCardInfo("Morale", 14, Rarity.COMMON, mage.cards.m.Morale.class));
        cards.add(new SetCardInfo("Murk Dwellers", 49, Rarity.COMMON, mage.cards.m.MurkDwellers.class));
        cards.add(new SetCardInfo("Nameless Race", 50, Rarity.RARE, mage.cards.n.NamelessRace.class));
        cards.add(new SetCardInfo("Necropolis", 105, Rarity.UNCOMMON, mage.cards.n.Necropolis.class));
        cards.add(new SetCardInfo("Niall Silvain", 82, Rarity.RARE, mage.cards.n.NiallSilvain.class));
        cards.add(new SetCardInfo("Orc General", 73, Rarity.UNCOMMON, mage.cards.o.OrcGeneral.class));
        cards.add(new SetCardInfo("People of the Woods", 83, Rarity.UNCOMMON, mage.cards.p.PeopleOfTheWoods.class));
        cards.add(new SetCardInfo("Pikemen", 15, Rarity.COMMON, mage.cards.p.Pikemen.class));
        cards.add(new SetCardInfo("Preacher", 16, Rarity.RARE, mage.cards.p.Preacher.class));
        cards.add(new SetCardInfo("Psychic Allergy", 34, Rarity.RARE, mage.cards.p.PsychicAllergy.class));
        cards.add(new SetCardInfo("Rag Man", 51, Rarity.RARE, mage.cards.r.RagMan.class));
        cards.add(new SetCardInfo("Riptide", 35, Rarity.COMMON, mage.cards.r.Riptide.class));
        cards.add(new SetCardInfo("Runesword", "107+", Rarity.UNCOMMON, mage.cards.r.Runesword.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Runesword", 107, Rarity.UNCOMMON, mage.cards.r.Runesword.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Safe Haven", 118, Rarity.RARE, mage.cards.s.SafeHaven.class));
        cards.add(new SetCardInfo("Savaen Elves", 84, Rarity.COMMON, mage.cards.s.SavaenElves.class));
        cards.add(new SetCardInfo("Scarecrow", 108, Rarity.UNCOMMON, mage.cards.s.Scarecrow.class));
        cards.add(new SetCardInfo("Scarwood Bandits", 85, Rarity.RARE, mage.cards.s.ScarwoodBandits.class));
        cards.add(new SetCardInfo("Scarwood Goblins", 94, Rarity.COMMON, mage.cards.s.ScarwoodGoblins.class));
        cards.add(new SetCardInfo("Scarwood Hag", 86, Rarity.UNCOMMON, mage.cards.s.ScarwoodHag.class));
        cards.add(new SetCardInfo("Scavenger Folk", 87, Rarity.COMMON, mage.cards.s.ScavengerFolk.class));
        cards.add(new SetCardInfo("Season of the Witch", 52, Rarity.RARE, mage.cards.s.SeasonOfTheWitch.class));
        cards.add(new SetCardInfo("Sisters of the Flame", 74, Rarity.UNCOMMON, mage.cards.s.SistersOfTheFlame.class));
        cards.add(new SetCardInfo("Skull of Orm", 109, Rarity.UNCOMMON, mage.cards.s.SkullOfOrm.class));
        cards.add(new SetCardInfo("Sorrow's Path", 119, Rarity.RARE, mage.cards.s.SorrowsPath.class));
        cards.add(new SetCardInfo("Spitting Slug", 88, Rarity.UNCOMMON, mage.cards.s.SpittingSlug.class));
        cards.add(new SetCardInfo("Squire", 17, Rarity.COMMON, mage.cards.s.Squire.class));
        cards.add(new SetCardInfo("Standing Stones", 110, Rarity.UNCOMMON, mage.cards.s.StandingStones.class));
        cards.add(new SetCardInfo("Stone Calendar", 111, Rarity.RARE, mage.cards.s.StoneCalendar.class));
        cards.add(new SetCardInfo("Sunken City", 36, Rarity.COMMON, mage.cards.s.SunkenCity.class));
        cards.add(new SetCardInfo("Tangle Kelp", 37, Rarity.UNCOMMON, mage.cards.t.TangleKelp.class));
        cards.add(new SetCardInfo("The Fallen", 53, Rarity.UNCOMMON, mage.cards.t.TheFallen.class));
        cards.add(new SetCardInfo("Tivadar's Crusade", 18, Rarity.UNCOMMON, mage.cards.t.TivadarsCrusade.class));
        cards.add(new SetCardInfo("Tormod's Crypt", 112, Rarity.UNCOMMON, mage.cards.t.TormodsCrypt.class));
        cards.add(new SetCardInfo("Tower of Coireall", 113, Rarity.UNCOMMON, mage.cards.t.TowerOfCoireall.class));
        cards.add(new SetCardInfo("Tracker", 89, Rarity.RARE, mage.cards.t.Tracker.class));
        cards.add(new SetCardInfo("Uncle Istvan", 54, Rarity.UNCOMMON, mage.cards.u.UncleIstvan.class));
        cards.add(new SetCardInfo("Venom", 90, Rarity.COMMON, mage.cards.v.Venom.class));
        cards.add(new SetCardInfo("Wand of Ith", 114, Rarity.UNCOMMON, mage.cards.w.WandOfIth.class));
        cards.add(new SetCardInfo("War Barge", 115, Rarity.UNCOMMON, mage.cards.w.WarBarge.class));
        cards.add(new SetCardInfo("Water Wurm", 38, Rarity.COMMON, mage.cards.w.WaterWurm.class));
        cards.add(new SetCardInfo("Witch Hunter", 19, Rarity.RARE, mage.cards.w.WitchHunter.class));
        cards.add(new SetCardInfo("Word of Binding", 55, Rarity.COMMON, mage.cards.w.WordOfBinding.class));
        cards.add(new SetCardInfo("Worms of the Earth", 56, Rarity.RARE, mage.cards.w.WormsOfTheEarth.class));
        cards.add(new SetCardInfo("Wormwood Treefolk", 92, Rarity.RARE, mage.cards.w.WormwoodTreefolk.class));
    }
}
