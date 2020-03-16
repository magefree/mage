package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/und
 */
public class Unsanctioned extends ExpansionSet {

    private static final Unsanctioned instance = new Unsanctioned();

    public static Unsanctioned getInstance() {
        return instance;
    }

    private Unsanctioned() {
        super("Unsanctioned", "UND", ExpansionSet.buildDate(2020, 2, 29), SetType.JOKESET);
        this.hasBoosters = false;
        this.hasBasicLands = true;

        //cards.add(new SetCardInfo("AWOL", 2, Rarity.COMMON, mage.cards.a.AWOL.class));
        //cards.add(new SetCardInfo("Abstract Iguanart", 47, Rarity.UNCOMMON, mage.cards.a.AbstractIguanart.class));
        //cards.add(new SetCardInfo("Acornelia, Fashionable Filcher", 31, Rarity.RARE, mage.cards.a.AcorneliaFashionableFilcher.class));
        //cards.add(new SetCardInfo("Adorable Kitten", 1, Rarity.COMMON, mage.cards.a.AdorableKitten.class));
        //cards.add(new SetCardInfo("Alexander Clamilton", 16, Rarity.RARE, mage.cards.a.AlexanderClamilton.class));
        //cards.add(new SetCardInfo("Avatar of Me", 17, Rarity.RARE, mage.cards.a.AvatarOfMe.class));
        cards.add(new SetCardInfo("B-I-N-G-O", 61, Rarity.RARE, mage.cards.b.BINGO.class));
        //cards.add(new SetCardInfo("B.O.B. (Bevy of Beebles)", 18, Rarity.MYTHIC, mage.cards.b.BOBBevyOfBeebles.class));
        //cards.add(new SetCardInfo("Bat-", 32, Rarity.UNCOMMON, mage.cards.b.Bat.class));
        cards.add(new SetCardInfo("Blast from the Past", 48, Rarity.RARE, mage.cards.b.BlastFromThePast.class));
        //cards.add(new SetCardInfo("Boomstacker", 49, Rarity.RARE, mage.cards.b.Boomstacker.class));
        cards.add(new SetCardInfo("Booster Tutor", 33, Rarity.UNCOMMON, mage.cards.b.BoosterTutor.class));
        //cards.add(new SetCardInfo("Bronze Calendar", 76, Rarity.UNCOMMON, mage.cards.b.BronzeCalendar.class));
        //cards.add(new SetCardInfo("Carnivorous Death-Parrot", 19, Rarity.COMMON, mage.cards.c.CarnivorousDeathParrot.class));
        //cards.add(new SetCardInfo("Cheatyface", 20, Rarity.UNCOMMON, mage.cards.c.Cheatyface.class));
        //cards.add(new SetCardInfo("Chicken à la King", 21, Rarity.RARE, mage.cards.c.ChickenLaKing.class));
        //cards.add(new SetCardInfo("Common Courtesy", 22, Rarity.UNCOMMON, mage.cards.c.CommonCourtesy.class));
        //cards.add(new SetCardInfo("Common Iguana", 50, Rarity.COMMON, mage.cards.c.CommonIguana.class));
        //cards.add(new SetCardInfo("Dirty Rat", 34, Rarity.COMMON, mage.cards.d.DirtyRat.class));
        //cards.add(new SetCardInfo("Duh", 35, Rarity.COMMON, mage.cards.d.Duh.class));
        cards.add(new SetCardInfo("Elvish Impersonators", 62, Rarity.COMMON, mage.cards.e.ElvishImpersonators.class));
        //cards.add(new SetCardInfo("Emcee", 3, Rarity.UNCOMMON, mage.cards.e.Emcee.class));
        //cards.add(new SetCardInfo("Enter the Dungeon", 36, Rarity.RARE, mage.cards.e.EnterTheDungeon.class));
        //cards.add(new SetCardInfo("Entirely Normal Armchair", 77, Rarity.UNCOMMON, mage.cards.e.EntirelyNormalArmchair.class));
        //cards.add(new SetCardInfo("Flavor Judge", 4, Rarity.RARE, mage.cards.f.FlavorJudge.class));
        cards.add(new SetCardInfo("Forest", 95, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 96, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        //cards.add(new SetCardInfo("Frankie Peanuts", 5, Rarity.RARE, mage.cards.f.FrankiePeanuts.class));
        cards.add(new SetCardInfo("Free-Range Chicken", 63, Rarity.COMMON, mage.cards.f.FreeRangeChicken.class));
        //cards.add(new SetCardInfo("GO TO JAIL", 6, Rarity.COMMON, mage.cards.g.GoToJail.class));
        //cards.add(new SetCardInfo("Goblin Haberdasher", 51, Rarity.UNCOMMON, mage.cards.g.GoblinHaberdasher.class));
        //cards.add(new SetCardInfo("Goblin S.W.A.T. Team", 52, Rarity.COMMON, mage.cards.g.GoblinSWATTeam.class));
        cards.add(new SetCardInfo("Goblin Tutor", 53, Rarity.UNCOMMON, mage.cards.g.GoblinTutor.class));
        cards.add(new SetCardInfo("Growth Spurt", 64, Rarity.COMMON, mage.cards.g.GrowthSpurt.class));
        //cards.add(new SetCardInfo("Half-Squirrel, Half-", 65, Rarity.UNCOMMON, mage.cards.h.HalfSquirrelHalf.class));
        //cards.add(new SetCardInfo("Hoisted Hireling", 37, Rarity.COMMON, mage.cards.h.HoistedHireling.class));
        //cards.add(new SetCardInfo("Humming-", 7, Rarity.COMMON, mage.cards.h.Humming.class));
        cards.add(new SetCardInfo("Infernal Spawn of Evil", 38, Rarity.RARE, mage.cards.i.InfernalSpawnOfEvil.class));
        //cards.add(new SetCardInfo("Infernal Spawn of Infernal Spawn of Evil", 39, Rarity.RARE, mage.cards.i.InfernalSpawnOfInfernalSpawnOfEvil.class));
        //cards.add(new SetCardInfo("Infernius Spawnington III, Esq.", 40, Rarity.RARE, mage.cards.i.InferniusSpawningtonIIIEsq.class));
        //cards.add(new SetCardInfo("Infinity Elemental", 54, Rarity.MYTHIC, mage.cards.i.InfinityElemental.class));
        cards.add(new SetCardInfo("Inhumaniac", 41, Rarity.UNCOMMON, mage.cards.i.Inhumaniac.class));
        cards.add(new SetCardInfo("Island", 89, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 90, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jack-in-the-Mox", 78, Rarity.RARE, mage.cards.j.JackInTheMox.class));
        cards.add(new SetCardInfo("Johnny, Combo Player", 23, Rarity.RARE, mage.cards.j.JohnnyComboPlayer.class));
        cards.add(new SetCardInfo("Jumbo Imp", 42, Rarity.UNCOMMON, mage.cards.j.JumboImp.class));
        //cards.add(new SetCardInfo("Knight of the Hokey Pokey", 8, Rarity.COMMON, mage.cards.k.KnightOfTheHokeyPokey.class));
        cards.add(new SetCardInfo("Krark's Other Thumb", 79, Rarity.UNCOMMON, mage.cards.k.KrarksOtherThumb.class));
        //cards.add(new SetCardInfo("Look at Me, I'm R&D", 9, Rarity.RARE, mage.cards.l.LookAtMeImRD.class));
        //cards.add(new SetCardInfo("Look at Me, I'm the DCI", 10, Rarity.RARE, mage.cards.l.LookAtMeImTheDci.class));
        //cards.add(new SetCardInfo("Magic Word", 24, Rarity.COMMON, mage.cards.m.MagicWord.class));
        //cards.add(new SetCardInfo("Mer Man", 25, Rarity.COMMON, mage.cards.m.MerMan.class));
        //cards.add(new SetCardInfo("Mother Kangaroo", 66, Rarity.COMMON, mage.cards.m.MotherKangaroo.class));
        cards.add(new SetCardInfo("Mountain", 93, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 94, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Old Fogey", 67, Rarity.RARE, mage.cards.o.OldFogey.class));
        //cards.add(new SetCardInfo("Old Guard", 11, Rarity.COMMON, mage.cards.o.OldGuard.class));
        //cards.add(new SetCardInfo("Ordinary Pony", 12, Rarity.COMMON, mage.cards.o.OrdinaryPony.class));
        cards.add(new SetCardInfo("Painiac", 55, Rarity.COMMON, mage.cards.p.Painiac.class));
        cards.add(new SetCardInfo("Paper Tiger", 80, Rarity.COMMON, mage.cards.p.PaperTiger.class));
        //cards.add(new SetCardInfo("Pippa, Duchess of Dice", 68, Rarity.RARE, mage.cards.p.PippaDuchessOfDice.class));
        cards.add(new SetCardInfo("Plains", 87, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 88, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        //cards.add(new SetCardInfo("Pointy Finger of Doom", 81, Rarity.RARE, mage.cards.p.PointyFingerOfDoom.class));
        cards.add(new SetCardInfo("Poultrygeist", 43, Rarity.COMMON, mage.cards.p.Poultrygeist.class));
        //cards.add(new SetCardInfo("Richard Garfield, Ph.D.", 26, Rarity.RARE, mage.cards.r.RichardGarfieldPhD.class));
        //cards.add(new SetCardInfo("Rings a Bell", 27, Rarity.UNCOMMON, mage.cards.r.RingsaBell.class));
        cards.add(new SetCardInfo("Rock Lobster", 82, Rarity.COMMON, mage.cards.r.RockLobster.class));
        cards.add(new SetCardInfo("Scissors Lizard", 83, Rarity.COMMON, mage.cards.s.ScissorsLizard.class));
        cards.add(new SetCardInfo("Six-y Beast", 56, Rarity.UNCOMMON, mage.cards.s.SixyBeast.class));
        //cards.add(new SetCardInfo("Skull Saucer", 44, Rarity.UNCOMMON, mage.cards.s.SkullSaucer.class));
        //cards.add(new SetCardInfo("Slaying Mantis", 69, Rarity.UNCOMMON, mage.cards.s.SlayingMantis.class));
        cards.add(new SetCardInfo("Snickering Squirrel", 45, Rarity.COMMON, mage.cards.s.SnickeringSquirrel.class));
        //cards.add(new SetCardInfo("Spirit of the Season", 70, Rarity.UNCOMMON, mage.cards.s.SpiritOfTheSeason.class));
        //cards.add(new SetCardInfo("Squirrel Farm", 71, Rarity.RARE, mage.cards.s.SquirrelFarm.class));
        //cards.add(new SetCardInfo("Staying Power", 13, Rarity.RARE, mage.cards.s.StayingPower.class));
        //cards.add(new SetCardInfo("Stet, Draconic Proofreader", 57, Rarity.RARE, mage.cards.s.StetDraconicProofreader.class));
        //cards.add(new SetCardInfo("Stinging Scorpion", 46, Rarity.COMMON, mage.cards.s.StingingScorpion.class));
        cards.add(new SetCardInfo("Strategy, Schmategy", 58, Rarity.RARE, mage.cards.s.StrategySchmategy.class));
        //cards.add(new SetCardInfo("Strutting Turkey", 14, Rarity.UNCOMMON, mage.cards.s.StruttingTurkey.class));
        //cards.add(new SetCardInfo("Super-Duper Death Ray", 59, Rarity.UNCOMMON, mage.cards.s.SuperDuperDeathRay.class));
        //cards.add(new SetCardInfo("Surgeon General Commander", 72, Rarity.MYTHIC, mage.cards.s.SurgeonGeneralCommander.class));
        cards.add(new SetCardInfo("Swamp", 91, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 92, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        //cards.add(new SetCardInfo("Sword of Dungeons & Dragons", 84, Rarity.MYTHIC, mage.cards.s.SwordOfDungeonsDragons.class));
        //cards.add(new SetCardInfo("Syr Cadian, Knight Owl", 15, Rarity.RARE, mage.cards.s.SyrCadianKnightOwl.class));
        cards.add(new SetCardInfo("Time Out", 28, Rarity.COMMON, mage.cards.t.TimeOut.class));
        cards.add(new SetCardInfo("Timmy, Power Gamer", 73, Rarity.RARE, mage.cards.t.TimmyPowerGamer.class));
        //cards.add(new SetCardInfo("Topsy Turvy", 29, Rarity.UNCOMMON, mage.cards.t.TopsyTurvy.class));
        //cards.add(new SetCardInfo("Underdome", 86, Rarity.COMMON, mage.cards.u.Underdome.class));
        //cards.add(new SetCardInfo("Wall of Fortune", 30, Rarity.COMMON, mage.cards.w.WallOfFortune.class));
        //cards.add(new SetCardInfo("Water Gun Balloon Game", 85, Rarity.RARE, mage.cards.w.WaterGunBalloonGame.class));
        //cards.add(new SetCardInfo("Who // What // When // Where // Why", 75, Rarity.RARE, mage.cards.w.WhoWhatWhenWhereWhy.class));
        //cards.add(new SetCardInfo("Wild Crocodile", 74, Rarity.COMMON, mage.cards.w.WildCrocodile.class));
        //cards.add(new SetCardInfo("Yet Another Aether Vortex", 60, Rarity.RARE, mage.cards.y.YetAnotherAetherVortex.class));
     }
}
