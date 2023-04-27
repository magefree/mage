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
 * @author BetaSteward_at_googlemail.com
 */
public final class Innistrad extends ExpansionSet {

    private static final Innistrad instance = new Innistrad();

    public static Innistrad getInstance() {
        return instance;
    }

    private Innistrad() {
        super("Innistrad", "ISD", ExpansionSet.buildDate(2011, 9, 30), SetType.EXPANSION);
        this.blockName = "Innistrad";
        this.hasBoosters = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 9;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        this.numBoosterDoubleFaced = 1;

        cards.add(new SetCardInfo("Abattoir Ghoul", 85, Rarity.UNCOMMON, mage.cards.a.AbattoirGhoul.class));
        cards.add(new SetCardInfo("Abbey Griffin", 1, Rarity.COMMON, mage.cards.a.AbbeyGriffin.class));
        cards.add(new SetCardInfo("Altar's Reap", 86, Rarity.COMMON, mage.cards.a.AltarsReap.class));
        cards.add(new SetCardInfo("Ambush Viper", 169, Rarity.COMMON, mage.cards.a.AmbushViper.class));
        cards.add(new SetCardInfo("Ancient Grudge", 127, Rarity.COMMON, mage.cards.a.AncientGrudge.class));
        cards.add(new SetCardInfo("Angel of Flight Alabaster", 2, Rarity.RARE, mage.cards.a.AngelOfFlightAlabaster.class));
        cards.add(new SetCardInfo("Angelic Overseer", 3, Rarity.MYTHIC, mage.cards.a.AngelicOverseer.class));
        cards.add(new SetCardInfo("Armored Skaab", 43, Rarity.COMMON, mage.cards.a.ArmoredSkaab.class));
        cards.add(new SetCardInfo("Army of the Damned", 87, Rarity.MYTHIC, mage.cards.a.ArmyOfTheDamned.class));
        cards.add(new SetCardInfo("Ashmouth Hound", 128, Rarity.COMMON, mage.cards.a.AshmouthHound.class));
        cards.add(new SetCardInfo("Avacyn's Pilgrim", 170, Rarity.COMMON, mage.cards.a.AvacynsPilgrim.class));
        cards.add(new SetCardInfo("Avacynian Priest", 4, Rarity.COMMON, mage.cards.a.AvacynianPriest.class));
        cards.add(new SetCardInfo("Back from the Brink", 44, Rarity.RARE, mage.cards.b.BackFromTheBrink.class));
        cards.add(new SetCardInfo("Balefire Dragon", 129, Rarity.MYTHIC, mage.cards.b.BalefireDragon.class));
        cards.add(new SetCardInfo("Bane of Hanweir", 145, Rarity.UNCOMMON, mage.cards.b.BaneOfHanweir.class));
        cards.add(new SetCardInfo("Battleground Geist", 45, Rarity.UNCOMMON, mage.cards.b.BattlegroundGeist.class));
        cards.add(new SetCardInfo("Bitterheart Witch", 88, Rarity.UNCOMMON, mage.cards.b.BitterheartWitch.class));
        cards.add(new SetCardInfo("Blasphemous Act", 130, Rarity.RARE, mage.cards.b.BlasphemousAct.class));
        cards.add(new SetCardInfo("Blazing Torch", 216, Rarity.COMMON, mage.cards.b.BlazingTorch.class));
        cards.add(new SetCardInfo("Bloodcrazed Neonate", 131, Rarity.COMMON, mage.cards.b.BloodcrazedNeonate.class));
        cards.add(new SetCardInfo("Bloodgift Demon", 89, Rarity.RARE, mage.cards.b.BloodgiftDemon.class));
        cards.add(new SetCardInfo("Bloodline Keeper", 90, Rarity.RARE, mage.cards.b.BloodlineKeeper.class));
        cards.add(new SetCardInfo("Bonds of Faith", 5, Rarity.COMMON, mage.cards.b.BondsOfFaith.class));
        cards.add(new SetCardInfo("Boneyard Wurm", 171, Rarity.UNCOMMON, mage.cards.b.BoneyardWurm.class));
        cards.add(new SetCardInfo("Brain Weevil", 91, Rarity.COMMON, mage.cards.b.BrainWeevil.class));
        cards.add(new SetCardInfo("Bramblecrush", 172, Rarity.UNCOMMON, mage.cards.b.Bramblecrush.class));
        cards.add(new SetCardInfo("Brimstone Volley", 132, Rarity.COMMON, mage.cards.b.BrimstoneVolley.class));
        cards.add(new SetCardInfo("Bump in the Night", 92, Rarity.COMMON, mage.cards.b.BumpInTheNight.class));
        cards.add(new SetCardInfo("Burning Vengeance", 133, Rarity.UNCOMMON, mage.cards.b.BurningVengeance.class));
        cards.add(new SetCardInfo("Butcher's Cleaver", 217, Rarity.UNCOMMON, mage.cards.b.ButchersCleaver.class));
        cards.add(new SetCardInfo("Cackling Counterpart", 46, Rarity.RARE, mage.cards.c.CacklingCounterpart.class));
        cards.add(new SetCardInfo("Caravan Vigil", 173, Rarity.COMMON, mage.cards.c.CaravanVigil.class));
        cards.add(new SetCardInfo("Cellar Door", 218, Rarity.UNCOMMON, mage.cards.c.CellarDoor.class));
        cards.add(new SetCardInfo("Champion of the Parish", 6, Rarity.RARE, mage.cards.c.ChampionOfTheParish.class));
        cards.add(new SetCardInfo("Chapel Geist", 7, Rarity.COMMON, mage.cards.c.ChapelGeist.class));
        cards.add(new SetCardInfo("Charmbreaker Devils", 134, Rarity.RARE, mage.cards.c.CharmbreakerDevils.class));
        cards.add(new SetCardInfo("Civilized Scholar", 47, Rarity.UNCOMMON, mage.cards.c.CivilizedScholar.class));
        cards.add(new SetCardInfo("Claustrophobia", 48, Rarity.COMMON, mage.cards.c.Claustrophobia.class));
        cards.add(new SetCardInfo("Clifftop Retreat", 238, Rarity.RARE, mage.cards.c.ClifftopRetreat.class));
        cards.add(new SetCardInfo("Cloistered Youth", 8, Rarity.UNCOMMON, mage.cards.c.CloisteredYouth.class));
        cards.add(new SetCardInfo("Cobbled Wings", 219, Rarity.COMMON, mage.cards.c.CobbledWings.class));
        cards.add(new SetCardInfo("Corpse Lunge", 93, Rarity.COMMON, mage.cards.c.CorpseLunge.class));
        cards.add(new SetCardInfo("Creeping Renaissance", 174, Rarity.RARE, mage.cards.c.CreepingRenaissance.class));
        cards.add(new SetCardInfo("Creepy Doll", 220, Rarity.RARE, mage.cards.c.CreepyDoll.class));
        cards.add(new SetCardInfo("Crossway Vampire", 135, Rarity.COMMON, mage.cards.c.CrosswayVampire.class));
        cards.add(new SetCardInfo("Curiosity", 49, Rarity.UNCOMMON, mage.cards.c.Curiosity.class));
        cards.add(new SetCardInfo("Curse of Death's Hold", 94, Rarity.RARE, mage.cards.c.CurseOfDeathsHold.class));
        cards.add(new SetCardInfo("Curse of Oblivion", 95, Rarity.COMMON, mage.cards.c.CurseOfOblivion.class));
        cards.add(new SetCardInfo("Curse of Stalked Prey", 136, Rarity.RARE, mage.cards.c.CurseOfStalkedPrey.class));
        cards.add(new SetCardInfo("Curse of the Bloody Tome", 50, Rarity.COMMON, mage.cards.c.CurseOfTheBloodyTome.class));
        cards.add(new SetCardInfo("Curse of the Nightly Hunt", 137, Rarity.UNCOMMON, mage.cards.c.CurseOfTheNightlyHunt.class));
        cards.add(new SetCardInfo("Curse of the Pierced Heart", 138, Rarity.COMMON, mage.cards.c.CurseOfThePiercedHeart.class));
        cards.add(new SetCardInfo("Darkthicket Wolf", 175, Rarity.COMMON, mage.cards.d.DarkthicketWolf.class));
        cards.add(new SetCardInfo("Daybreak Ranger", 176, Rarity.RARE, mage.cards.d.DaybreakRanger.class));
        cards.add(new SetCardInfo("Dead Weight", 96, Rarity.COMMON, mage.cards.d.DeadWeight.class));
        cards.add(new SetCardInfo("Dearly Departed", 9, Rarity.RARE, mage.cards.d.DearlyDeparted.class));
        cards.add(new SetCardInfo("Delver of Secrets", 51, Rarity.COMMON, mage.cards.d.DelverOfSecrets.class));
        cards.add(new SetCardInfo("Demonmail Hauberk", 221, Rarity.UNCOMMON, mage.cards.d.DemonmailHauberk.class));
        cards.add(new SetCardInfo("Deranged Assistant", 52, Rarity.COMMON, mage.cards.d.DerangedAssistant.class));
        cards.add(new SetCardInfo("Desperate Ravings", 139, Rarity.UNCOMMON, mage.cards.d.DesperateRavings.class));
        cards.add(new SetCardInfo("Devil's Play", 140, Rarity.RARE, mage.cards.d.DevilsPlay.class));
        cards.add(new SetCardInfo("Diregraf Ghoul", 97, Rarity.UNCOMMON, mage.cards.d.DiregrafGhoul.class));
        cards.add(new SetCardInfo("Disciple of Griselbrand", 98, Rarity.UNCOMMON, mage.cards.d.DiscipleOfGriselbrand.class));
        cards.add(new SetCardInfo("Dissipate", 53, Rarity.UNCOMMON, mage.cards.d.Dissipate.class));
        cards.add(new SetCardInfo("Divine Reckoning", 10, Rarity.RARE, mage.cards.d.DivineReckoning.class));
        cards.add(new SetCardInfo("Doomed Traveler", 11, Rarity.COMMON, mage.cards.d.DoomedTraveler.class));
        cards.add(new SetCardInfo("Dream Twist", 54, Rarity.COMMON, mage.cards.d.DreamTwist.class));
        cards.add(new SetCardInfo("Elder Cathar", 12, Rarity.COMMON, mage.cards.e.ElderCathar.class));
        cards.add(new SetCardInfo("Elder of Laurels", 177, Rarity.RARE, mage.cards.e.ElderOfLaurels.class));
        cards.add(new SetCardInfo("Elite Inquisitor", 13, Rarity.RARE, mage.cards.e.EliteInquisitor.class));
        cards.add(new SetCardInfo("Endless Ranks of the Dead", 99, Rarity.RARE, mage.cards.e.EndlessRanksOfTheDead.class));
        cards.add(new SetCardInfo("Essence of the Wild", 178, Rarity.MYTHIC, mage.cards.e.EssenceOfTheWild.class));
        cards.add(new SetCardInfo("Evil Twin", 212, Rarity.RARE, mage.cards.e.EvilTwin.class));
        cards.add(new SetCardInfo("Falkenrath Marauders", 141, Rarity.RARE, mage.cards.f.FalkenrathMarauders.class));
        cards.add(new SetCardInfo("Falkenrath Noble", 100, Rarity.UNCOMMON, mage.cards.f.FalkenrathNoble.class));
        cards.add(new SetCardInfo("Feeling of Dread", 14, Rarity.COMMON, mage.cards.f.FeelingOfDread.class));
        cards.add(new SetCardInfo("Feral Ridgewolf", 142, Rarity.COMMON, mage.cards.f.FeralRidgewolf.class));
        cards.add(new SetCardInfo("Festerhide Boar", 179, Rarity.COMMON, mage.cards.f.FesterhideBoar.class));
        cards.add(new SetCardInfo("Fiend Hunter", 15, Rarity.UNCOMMON, mage.cards.f.FiendHunter.class));
        cards.add(new SetCardInfo("Forbidden Alchemy", 55, Rarity.COMMON, mage.cards.f.ForbiddenAlchemy.class));
        cards.add(new SetCardInfo("Forest", 262, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 263, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 264, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fortress Crab", 56, Rarity.COMMON, mage.cards.f.FortressCrab.class));
        cards.add(new SetCardInfo("Frightful Delusion", 57, Rarity.COMMON, mage.cards.f.FrightfulDelusion.class));
        cards.add(new SetCardInfo("Full Moon's Rise", 180, Rarity.UNCOMMON, mage.cards.f.FullMoonsRise.class));
        cards.add(new SetCardInfo("Furor of the Bitten", 143, Rarity.COMMON, mage.cards.f.FurorOfTheBitten.class));
        cards.add(new SetCardInfo("Gallows Warden", 16, Rarity.UNCOMMON, mage.cards.g.GallowsWarden.class));
        cards.add(new SetCardInfo("Galvanic Juggernaut", 222, Rarity.UNCOMMON, mage.cards.g.GalvanicJuggernaut.class));
        cards.add(new SetCardInfo("Garruk Relentless", 181, Rarity.MYTHIC, mage.cards.g.GarrukRelentless.class));
        cards.add(new SetCardInfo("Garruk, the Veil-Cursed", 181, Rarity.MYTHIC, mage.cards.g.GarrukTheVeilCursed.class));
        cards.add(new SetCardInfo("Gatstaf Howler", 182, Rarity.UNCOMMON, mage.cards.g.GatstafHowler.class));
        cards.add(new SetCardInfo("Gatstaf Shepherd", 182, Rarity.UNCOMMON, mage.cards.g.GatstafShepherd.class));
        cards.add(new SetCardInfo("Gavony Township", 239, Rarity.RARE, mage.cards.g.GavonyTownship.class));
        cards.add(new SetCardInfo("Geist of Saint Traft", 213, Rarity.MYTHIC, mage.cards.g.GeistOfSaintTraft.class));
        cards.add(new SetCardInfo("Geist-Honored Monk", 17, Rarity.RARE, mage.cards.g.GeistHonoredMonk.class));
        cards.add(new SetCardInfo("Geistcatcher's Rig", 223, Rarity.UNCOMMON, mage.cards.g.GeistcatchersRig.class));
        cards.add(new SetCardInfo("Geistflame", 144, Rarity.COMMON, mage.cards.g.Geistflame.class));
        cards.add(new SetCardInfo("Ghost Quarter", 240, Rarity.UNCOMMON, mage.cards.g.GhostQuarter.class));
        cards.add(new SetCardInfo("Ghostly Possession", 18, Rarity.COMMON, mage.cards.g.GhostlyPossession.class));
        cards.add(new SetCardInfo("Ghoulcaller's Bell", 224, Rarity.COMMON, mage.cards.g.GhoulcallersBell.class));
        cards.add(new SetCardInfo("Ghoulcaller's Chant", 101, Rarity.COMMON, mage.cards.g.GhoulcallersChant.class));
        cards.add(new SetCardInfo("Ghoulraiser", 102, Rarity.COMMON, mage.cards.g.Ghoulraiser.class));
        cards.add(new SetCardInfo("Gnaw to the Bone", 183, Rarity.COMMON, mage.cards.g.GnawToTheBone.class));
        cards.add(new SetCardInfo("Grasp of Phantoms", 58, Rarity.UNCOMMON, mage.cards.g.GraspOfPhantoms.class));
        cards.add(new SetCardInfo("Grave Bramble", 184, Rarity.COMMON, mage.cards.g.GraveBramble.class));
        cards.add(new SetCardInfo("Graveyard Shovel", 225, Rarity.UNCOMMON, mage.cards.g.GraveyardShovel.class));
        cards.add(new SetCardInfo("Grimgrin, Corpse-Born", 214, Rarity.MYTHIC, mage.cards.g.GrimgrinCorpseBorn.class));
        cards.add(new SetCardInfo("Grimoire of the Dead", 226, Rarity.MYTHIC, mage.cards.g.GrimoireOfTheDead.class));
        cards.add(new SetCardInfo("Grizzled Outcasts", 185, Rarity.COMMON, mage.cards.g.GrizzledOutcasts.class));
        cards.add(new SetCardInfo("Gruesome Deformity", 103, Rarity.COMMON, mage.cards.g.GruesomeDeformity.class));
        cards.add(new SetCardInfo("Gutter Grime", 186, Rarity.RARE, mage.cards.g.GutterGrime.class));
        cards.add(new SetCardInfo("Hamlet Captain", 187, Rarity.UNCOMMON, mage.cards.h.HamletCaptain.class));
        cards.add(new SetCardInfo("Hanweir Watchkeep", 145, Rarity.UNCOMMON, mage.cards.h.HanweirWatchkeep.class));
        cards.add(new SetCardInfo("Harvest Pyre", 146, Rarity.COMMON, mage.cards.h.HarvestPyre.class));
        cards.add(new SetCardInfo("Heartless Summoning", 104, Rarity.RARE, mage.cards.h.HeartlessSummoning.class));
        cards.add(new SetCardInfo("Heretic's Punishment", 147, Rarity.RARE, mage.cards.h.HereticsPunishment.class));
        cards.add(new SetCardInfo("Hinterland Harbor", 241, Rarity.RARE, mage.cards.h.HinterlandHarbor.class));
        cards.add(new SetCardInfo("Hollowhenge Scavenger", 188, Rarity.UNCOMMON, mage.cards.h.HollowhengeScavenger.class));
        cards.add(new SetCardInfo("Homicidal Brute", 47, Rarity.UNCOMMON, mage.cards.h.HomicidalBrute.class));
        cards.add(new SetCardInfo("Howlpack Alpha", 193, Rarity.RARE, mage.cards.h.HowlpackAlpha.class));
        cards.add(new SetCardInfo("Howlpack of Estwald", 209, Rarity.COMMON, mage.cards.h.HowlpackOfEstwald.class));
        cards.add(new SetCardInfo("Hysterical Blindness", 59, Rarity.COMMON, mage.cards.h.HystericalBlindness.class));
        cards.add(new SetCardInfo("Infernal Plunge", 148, Rarity.COMMON, mage.cards.i.InfernalPlunge.class));
        cards.add(new SetCardInfo("Inquisitor's Flail", 227, Rarity.UNCOMMON, mage.cards.i.InquisitorsFlail.class));
        cards.add(new SetCardInfo("Insectile Aberration", 51, Rarity.COMMON, mage.cards.i.InsectileAberration.class));
        cards.add(new SetCardInfo("Instigator Gang", 149, Rarity.RARE, mage.cards.i.InstigatorGang.class));
        cards.add(new SetCardInfo("Intangible Virtue", 19, Rarity.UNCOMMON, mage.cards.i.IntangibleVirtue.class));
        cards.add(new SetCardInfo("Into the Maw of Hell", 150, Rarity.UNCOMMON, mage.cards.i.IntoTheMawOfHell.class));
        cards.add(new SetCardInfo("Invisible Stalker", 60, Rarity.UNCOMMON, mage.cards.i.InvisibleStalker.class));
        cards.add(new SetCardInfo("Ironfang", 168, Rarity.COMMON, mage.cards.i.Ironfang.class));
        cards.add(new SetCardInfo("Island", 253, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 254, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 255, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Isolated Chapel", 242, Rarity.RARE, mage.cards.i.IsolatedChapel.class));
        cards.add(new SetCardInfo("Kessig Cagebreakers", 189, Rarity.RARE, mage.cards.k.KessigCagebreakers.class));
        cards.add(new SetCardInfo("Kessig Wolf Run", 243, Rarity.RARE, mage.cards.k.KessigWolfRun.class));
        cards.add(new SetCardInfo("Kessig Wolf", 151, Rarity.COMMON, mage.cards.k.KessigWolf.class));
        cards.add(new SetCardInfo("Kindercatch", 190, Rarity.COMMON, mage.cards.k.Kindercatch.class));
        cards.add(new SetCardInfo("Krallenhorde Wantons", 185, Rarity.COMMON, mage.cards.k.KrallenhordeWantons.class));
        cards.add(new SetCardInfo("Kruin Outlaw", 152, Rarity.RARE, mage.cards.k.KruinOutlaw.class));
        cards.add(new SetCardInfo("Laboratory Maniac", 61, Rarity.RARE, mage.cards.l.LaboratoryManiac.class));
        cards.add(new SetCardInfo("Lantern Spirit", 62, Rarity.UNCOMMON, mage.cards.l.LanternSpirit.class));
        cards.add(new SetCardInfo("Liliana of the Veil", 105, Rarity.MYTHIC, mage.cards.l.LilianaOfTheVeil.class));
        cards.add(new SetCardInfo("Lord of Lineage", 90, Rarity.RARE, mage.cards.l.LordOfLineage.class));
        cards.add(new SetCardInfo("Lost in the Mist", 63, Rarity.COMMON, mage.cards.l.LostInTheMist.class));
        cards.add(new SetCardInfo("Ludevic's Abomination", 64, Rarity.RARE, mage.cards.l.LudevicsAbomination.class));
        cards.add(new SetCardInfo("Ludevic's Test Subject", 64, Rarity.RARE, mage.cards.l.LudevicsTestSubject.class));
        cards.add(new SetCardInfo("Lumberknot", 191, Rarity.UNCOMMON, mage.cards.l.Lumberknot.class));
        cards.add(new SetCardInfo("Make a Wish", 192, Rarity.UNCOMMON, mage.cards.m.MakeAWish.class));
        cards.add(new SetCardInfo("Makeshift Mauler", 65, Rarity.COMMON, mage.cards.m.MakeshiftMauler.class));
        cards.add(new SetCardInfo("Manor Gargoyle", 228, Rarity.RARE, mage.cards.m.ManorGargoyle.class));
        cards.add(new SetCardInfo("Manor Skeleton", 106, Rarity.COMMON, mage.cards.m.ManorSkeleton.class));
        cards.add(new SetCardInfo("Markov Patrician", 107, Rarity.COMMON, mage.cards.m.MarkovPatrician.class));
        cards.add(new SetCardInfo("Mask of Avacyn", 229, Rarity.UNCOMMON, mage.cards.m.MaskOfAvacyn.class));
        cards.add(new SetCardInfo("Mausoleum Guard", 20, Rarity.UNCOMMON, mage.cards.m.MausoleumGuard.class));
        cards.add(new SetCardInfo("Maw of the Mire", 108, Rarity.COMMON, mage.cards.m.MawOfTheMire.class));
        cards.add(new SetCardInfo("Mayor of Avabruck", 193, Rarity.RARE, mage.cards.m.MayorOfAvabruck.class));
        cards.add(new SetCardInfo("Memory's Journey", 66, Rarity.UNCOMMON, mage.cards.m.MemorysJourney.class));
        cards.add(new SetCardInfo("Mentor of the Meek", 21, Rarity.RARE, mage.cards.m.MentorOfTheMeek.class));
        cards.add(new SetCardInfo("Merciless Predator", 159, Rarity.UNCOMMON, mage.cards.m.MercilessPredator.class));
        cards.add(new SetCardInfo("Midnight Haunting", 22, Rarity.UNCOMMON, mage.cards.m.MidnightHaunting.class));
        cards.add(new SetCardInfo("Mikaeus, the Lunarch", 23, Rarity.MYTHIC, mage.cards.m.MikaeusTheLunarch.class));
        cards.add(new SetCardInfo("Mindshrieker", 67, Rarity.RARE, mage.cards.m.Mindshrieker.class));
        cards.add(new SetCardInfo("Mirror-Mad Phantasm", 68, Rarity.MYTHIC, mage.cards.m.MirrorMadPhantasm.class));
        cards.add(new SetCardInfo("Moan of the Unhallowed", 109, Rarity.UNCOMMON, mage.cards.m.MoanOfTheUnhallowed.class));
        cards.add(new SetCardInfo("Moldgraf Monstrosity", 194, Rarity.RARE, mage.cards.m.MoldgrafMonstrosity.class));
        cards.add(new SetCardInfo("Moment of Heroism", 24, Rarity.COMMON, mage.cards.m.MomentOfHeroism.class));
        cards.add(new SetCardInfo("Moon Heron", 69, Rarity.COMMON, mage.cards.m.MoonHeron.class));
        cards.add(new SetCardInfo("Moonmist", 195, Rarity.COMMON, mage.cards.m.Moonmist.class));
        cards.add(new SetCardInfo("Moorland Haunt", 244, Rarity.RARE, mage.cards.m.MoorlandHaunt.class));
        cards.add(new SetCardInfo("Morkrut Banshee", 110, Rarity.UNCOMMON, mage.cards.m.MorkrutBanshee.class));
        cards.add(new SetCardInfo("Mountain", 259, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 260, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 261, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mulch", 196, Rarity.COMMON, mage.cards.m.Mulch.class));
        cards.add(new SetCardInfo("Murder of Crows", 70, Rarity.UNCOMMON, mage.cards.m.MurderOfCrows.class));
        cards.add(new SetCardInfo("Naturalize", 197, Rarity.COMMON, mage.cards.n.Naturalize.class));
        cards.add(new SetCardInfo("Nephalia Drownyard", 245, Rarity.RARE, mage.cards.n.NephaliaDrownyard.class));
        cards.add(new SetCardInfo("Nevermore", 25, Rarity.RARE, mage.cards.n.Nevermore.class));
        cards.add(new SetCardInfo("Night Revelers", 153, Rarity.COMMON, mage.cards.n.NightRevelers.class));
        cards.add(new SetCardInfo("Night Terrors", 111, Rarity.COMMON, mage.cards.n.NightTerrors.class));
        cards.add(new SetCardInfo("Nightbird's Clutches", 154, Rarity.COMMON, mage.cards.n.NightbirdsClutches.class));
        cards.add(new SetCardInfo("Nightfall Predator", 176, Rarity.RARE, mage.cards.n.NightfallPredator.class));
        cards.add(new SetCardInfo("Olivia Voldaren", 215, Rarity.MYTHIC, mage.cards.o.OliviaVoldaren.class));
        cards.add(new SetCardInfo("One-Eyed Scarecrow", 230, Rarity.COMMON, mage.cards.o.OneEyedScarecrow.class));
        cards.add(new SetCardInfo("Orchard Spirit", 198, Rarity.COMMON, mage.cards.o.OrchardSpirit.class));
        cards.add(new SetCardInfo("Parallel Lives", 199, Rarity.RARE, mage.cards.p.ParallelLives.class));
        cards.add(new SetCardInfo("Paraselene", 26, Rarity.UNCOMMON, mage.cards.p.Paraselene.class));
        cards.add(new SetCardInfo("Past in Flames", 155, Rarity.MYTHIC, mage.cards.p.PastInFlames.class));
        cards.add(new SetCardInfo("Pitchburn Devils", 156, Rarity.COMMON, mage.cards.p.PitchburnDevils.class));
        cards.add(new SetCardInfo("Plains", 250, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 251, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 252, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Prey Upon", 200, Rarity.COMMON, mage.cards.p.PreyUpon.class));
        cards.add(new SetCardInfo("Purify the Grave", 27, Rarity.UNCOMMON, mage.cards.p.PurifyTheGrave.class));
        cards.add(new SetCardInfo("Rage Thrower", 157, Rarity.UNCOMMON, mage.cards.r.RageThrower.class));
        cards.add(new SetCardInfo("Rakish Heir", 158, Rarity.UNCOMMON, mage.cards.r.RakishHeir.class));
        cards.add(new SetCardInfo("Rally the Peasants", 28, Rarity.UNCOMMON, mage.cards.r.RallyThePeasants.class));
        cards.add(new SetCardInfo("Rampaging Werewolf", 165, Rarity.COMMON, mage.cards.r.RampagingWerewolf.class));
        cards.add(new SetCardInfo("Ranger's Guile", 201, Rarity.COMMON, mage.cards.r.RangersGuile.class));
        cards.add(new SetCardInfo("Reaper from the Abyss", 112, Rarity.MYTHIC, mage.cards.r.ReaperFromTheAbyss.class));
        cards.add(new SetCardInfo("Rebuke", 29, Rarity.COMMON, mage.cards.r.Rebuke.class));
        cards.add(new SetCardInfo("Reckless Waif", 159, Rarity.UNCOMMON, mage.cards.r.RecklessWaif.class));
        cards.add(new SetCardInfo("Riot Devils", 160, Rarity.COMMON, mage.cards.r.RiotDevils.class));
        cards.add(new SetCardInfo("Rolling Temblor", 161, Rarity.UNCOMMON, mage.cards.r.RollingTemblor.class));
        cards.add(new SetCardInfo("Rooftop Storm", 71, Rarity.RARE, mage.cards.r.RooftopStorm.class));
        cards.add(new SetCardInfo("Rotting Fensnake", 113, Rarity.COMMON, mage.cards.r.RottingFensnake.class));
        cards.add(new SetCardInfo("Runechanter's Pike", 231, Rarity.RARE, mage.cards.r.RunechantersPike.class));
        cards.add(new SetCardInfo("Runic Repetition", 72, Rarity.UNCOMMON, mage.cards.r.RunicRepetition.class));
        cards.add(new SetCardInfo("Scourge of Geier Reach", 162, Rarity.UNCOMMON, mage.cards.s.ScourgeOfGeierReach.class));
        cards.add(new SetCardInfo("Screeching Bat", 114, Rarity.UNCOMMON, mage.cards.s.ScreechingBat.class));
        cards.add(new SetCardInfo("Selfless Cathar", 30, Rarity.COMMON, mage.cards.s.SelflessCathar.class));
        cards.add(new SetCardInfo("Selhoff Occultist", 73, Rarity.COMMON, mage.cards.s.SelhoffOccultist.class));
        cards.add(new SetCardInfo("Sensory Deprivation", 74, Rarity.COMMON, mage.cards.s.SensoryDeprivation.class));
        cards.add(new SetCardInfo("Sever the Bloodline", 115, Rarity.RARE, mage.cards.s.SeverTheBloodline.class));
        cards.add(new SetCardInfo("Sharpened Pitchfork", 232, Rarity.UNCOMMON, mage.cards.s.SharpenedPitchfork.class));
        cards.add(new SetCardInfo("Shimmering Grotto", 246, Rarity.COMMON, mage.cards.s.ShimmeringGrotto.class));
        cards.add(new SetCardInfo("Silent Departure", 75, Rarity.COMMON, mage.cards.s.SilentDeparture.class));
        cards.add(new SetCardInfo("Silver-Inlaid Dagger", 233, Rarity.UNCOMMON, mage.cards.s.SilverInlaidDagger.class));
        cards.add(new SetCardInfo("Silverchase Fox", 31, Rarity.COMMON, mage.cards.s.SilverchaseFox.class));
        cards.add(new SetCardInfo("Skaab Goliath", 76, Rarity.UNCOMMON, mage.cards.s.SkaabGoliath.class));
        cards.add(new SetCardInfo("Skaab Ruinator", 77, Rarity.MYTHIC, mage.cards.s.SkaabRuinator.class));
        cards.add(new SetCardInfo("Skeletal Grimace", 116, Rarity.COMMON, mage.cards.s.SkeletalGrimace.class));
        cards.add(new SetCardInfo("Skirsdag Cultist", 163, Rarity.UNCOMMON, mage.cards.s.SkirsdagCultist.class));
        cards.add(new SetCardInfo("Skirsdag High Priest", 117, Rarity.RARE, mage.cards.s.SkirsdagHighPriest.class));
        cards.add(new SetCardInfo("Slayer of the Wicked", 32, Rarity.UNCOMMON, mage.cards.s.SlayerOfTheWicked.class));
        cards.add(new SetCardInfo("Smite the Monstrous", 33, Rarity.COMMON, mage.cards.s.SmiteTheMonstrous.class));
        cards.add(new SetCardInfo("Snapcaster Mage", 78, Rarity.RARE, mage.cards.s.SnapcasterMage.class));
        cards.add(new SetCardInfo("Somberwald Spider", 202, Rarity.COMMON, mage.cards.s.SomberwaldSpider.class));
        cards.add(new SetCardInfo("Spare from Evil", 34, Rarity.COMMON, mage.cards.s.SpareFromEvil.class));
        cards.add(new SetCardInfo("Spectral Flight", 79, Rarity.COMMON, mage.cards.s.SpectralFlight.class));
        cards.add(new SetCardInfo("Spectral Rider", 35, Rarity.UNCOMMON, mage.cards.s.SpectralRider.class));
        cards.add(new SetCardInfo("Spider Spawning", 203, Rarity.UNCOMMON, mage.cards.s.SpiderSpawning.class));
        cards.add(new SetCardInfo("Spidery Grasp", 204, Rarity.COMMON, mage.cards.s.SpideryGrasp.class));
        cards.add(new SetCardInfo("Splinterfright", 205, Rarity.RARE, mage.cards.s.Splinterfright.class));
        cards.add(new SetCardInfo("Stalking Vampire", 114, Rarity.UNCOMMON, mage.cards.s.StalkingVampire.class));
        cards.add(new SetCardInfo("Stensia Bloodhall", 247, Rarity.RARE, mage.cards.s.StensiaBloodhall.class));
        cards.add(new SetCardInfo("Stitched Drake", 80, Rarity.COMMON, mage.cards.s.StitchedDrake.class));
        cards.add(new SetCardInfo("Stitcher's Apprentice", 81, Rarity.COMMON, mage.cards.s.StitchersApprentice.class));
        cards.add(new SetCardInfo("Stony Silence", 36, Rarity.RARE, mage.cards.s.StonySilence.class));
        cards.add(new SetCardInfo("Stromkirk Noble", 164, Rarity.RARE, mage.cards.s.StromkirkNoble.class));
        cards.add(new SetCardInfo("Stromkirk Patrol", 118, Rarity.COMMON, mage.cards.s.StromkirkPatrol.class));
        cards.add(new SetCardInfo("Sturmgeist", 82, Rarity.RARE, mage.cards.s.Sturmgeist.class));
        cards.add(new SetCardInfo("Sulfur Falls", 248, Rarity.RARE, mage.cards.s.SulfurFalls.class));
        cards.add(new SetCardInfo("Swamp", 256, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 257, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 258, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Terror of Kruin Pass", 152, Rarity.RARE, mage.cards.t.TerrorOfKruinPass.class));
        cards.add(new SetCardInfo("Think Twice", 83, Rarity.COMMON, mage.cards.t.ThinkTwice.class));
        cards.add(new SetCardInfo("Thraben Militia", 38, Rarity.COMMON, mage.cards.t.ThrabenMilitia.class));
        cards.add(new SetCardInfo("Thraben Purebloods", 37, Rarity.COMMON, mage.cards.t.ThrabenPurebloods.class));
        cards.add(new SetCardInfo("Thraben Sentry", 38, Rarity.COMMON, mage.cards.t.ThrabenSentry.class));
        cards.add(new SetCardInfo("Tormented Pariah", 165, Rarity.COMMON, mage.cards.t.TormentedPariah.class));
        cards.add(new SetCardInfo("Traitorous Blood", 166, Rarity.COMMON, mage.cards.t.TraitorousBlood.class));
        cards.add(new SetCardInfo("Travel Preparations", 206, Rarity.COMMON, mage.cards.t.TravelPreparations.class));
        cards.add(new SetCardInfo("Traveler's Amulet", 234, Rarity.COMMON, mage.cards.t.TravelersAmulet.class));
        cards.add(new SetCardInfo("Tree of Redemption", 207, Rarity.MYTHIC, mage.cards.t.TreeOfRedemption.class));
        cards.add(new SetCardInfo("Trepanation Blade", 235, Rarity.UNCOMMON, mage.cards.t.TrepanationBlade.class));
        cards.add(new SetCardInfo("Tribute to Hunger", 119, Rarity.UNCOMMON, mage.cards.t.TributeToHunger.class));
        cards.add(new SetCardInfo("Typhoid Rats", 120, Rarity.COMMON, mage.cards.t.TyphoidRats.class));
        cards.add(new SetCardInfo("Ulvenwald Mystics", 208, Rarity.UNCOMMON, mage.cards.u.UlvenwaldMystics.class));
        cards.add(new SetCardInfo("Ulvenwald Primordials", 208, Rarity.UNCOMMON, mage.cards.u.UlvenwaldPrimordials.class));
        cards.add(new SetCardInfo("Unbreathing Horde", 121, Rarity.RARE, mage.cards.u.UnbreathingHorde.class));
        cards.add(new SetCardInfo("Unburial Rites", 122, Rarity.UNCOMMON, mage.cards.u.UnburialRites.class));
        cards.add(new SetCardInfo("Undead Alchemist", 84, Rarity.RARE, mage.cards.u.UndeadAlchemist.class));
        cards.add(new SetCardInfo("Unholy Fiend", 8, Rarity.UNCOMMON, mage.cards.u.UnholyFiend.class));
        cards.add(new SetCardInfo("Unruly Mob", 39, Rarity.COMMON, mage.cards.u.UnrulyMob.class));
        cards.add(new SetCardInfo("Urgent Exorcism", 40, Rarity.COMMON, mage.cards.u.UrgentExorcism.class));
        cards.add(new SetCardInfo("Vampire Interloper", 123, Rarity.COMMON, mage.cards.v.VampireInterloper.class));
        cards.add(new SetCardInfo("Vampiric Fury", 167, Rarity.COMMON, mage.cards.v.VampiricFury.class));
        cards.add(new SetCardInfo("Victim of Night", 124, Rarity.COMMON, mage.cards.v.VictimOfNight.class));
        cards.add(new SetCardInfo("Village Bell-Ringer", 41, Rarity.COMMON, mage.cards.v.VillageBellRinger.class));
        cards.add(new SetCardInfo("Village Cannibals", 125, Rarity.UNCOMMON, mage.cards.v.VillageCannibals.class));
        cards.add(new SetCardInfo("Village Ironsmith", 168, Rarity.COMMON, mage.cards.v.VillageIronsmith.class));
        cards.add(new SetCardInfo("Villagers of Estwald", 209, Rarity.COMMON, mage.cards.v.VillagersOfEstwald.class));
        cards.add(new SetCardInfo("Voiceless Spirit", 42, Rarity.COMMON, mage.cards.v.VoicelessSpirit.class));
        cards.add(new SetCardInfo("Walking Corpse", 126, Rarity.COMMON, mage.cards.w.WalkingCorpse.class));
        cards.add(new SetCardInfo("Wildblood Pack", 149, Rarity.RARE, mage.cards.w.WildbloodPack.class));
        cards.add(new SetCardInfo("Witchbane Orb", 236, Rarity.RARE, mage.cards.w.WitchbaneOrb.class));
        cards.add(new SetCardInfo("Wooden Stake", 237, Rarity.COMMON, mage.cards.w.WoodenStake.class));
        cards.add(new SetCardInfo("Woodland Cemetery", 249, Rarity.RARE, mage.cards.w.WoodlandCemetery.class));
        cards.add(new SetCardInfo("Woodland Sleuth", 210, Rarity.COMMON, mage.cards.w.WoodlandSleuth.class));
        cards.add(new SetCardInfo("Wreath of Geists", 211, Rarity.UNCOMMON, mage.cards.w.WreathOfGeists.class));
    }

    @Override
    public BoosterCollator createCollator() {
        return new InnistradCollator();
    }
}

// Booster collation info from https://www.lethe.xyz/mtg/collation/isd.html
// Using USA collation
class InnistradCollator implements BoosterCollator {
    private final CardRun commonA = new CardRun(true, "103", "48", "57", "128", "11", "30", "96", "202", "160", "86", "55", "12", "151", "237", "179", "59", "42", "101", "96", "39", "128", "210", "55", "142", "30", "95", "124", "202", "59", "4", "160", "50", "190", "142", "86", "103", "184", "4", "131", "31", "50", "219", "210", "95", "135", "237", "101", "11", "63", "12", "184", "131", "57", "219", "116", "42", "124", "48", "190", "135", "63", "151", "31", "39", "116", "179");
    private final CardRun commonB = new CardRun(true, "127", "37", "198", "143", "54", "69", "224", "108", "93", "34", "154", "169", "54", "144", "111", "24", "65", "92", "154", "234", "175", "169", "34", "69", "111", "143", "65", "14", "154", "175", "24", "108", "198", "224", "65", "34", "144", "69", "170", "92", "108", "37", "143", "14", "175", "56", "198", "127", "234", "93", "144", "37", "54", "170", "111", "56", "14", "93", "127", "169", "224", "92", "24", "56", "234", "170");
    private final CardRun commonC1 = new CardRun(true, "204", "73", "132", "106", "80", "146", "197", "29", "40", "123", "73", "166", "195", "230", "107", "18", "216", "52", "132", "183", "123", "138", "40", "106", "204", "230", "43", "5", "167", "197", "120", "146", "196", "33", "43", "216", "81", "126", "167", "52", "183", "18", "29", "120", "166", "107", "81", "196", "80", "138", "5", "246", "195", "126", "33");
    private final CardRun commonC2 = new CardRun(true, "41", "173", "74", "206", "102", "7", "75", "153", "79", "113", "173", "148", "201", "118", "75", "83", "156", "41", "200", "91", "113", "102", "79", "7", "156", "173", "1", "91", "74", "246", "118", "153", "7", "206", "83", "201", "1", "74", "148", "118", "156", "200", "113", "206", "41", "79", "91", "153", "201", "75", "102", "1", "200", "148", "83");
    private final CardRun uncommonA = new CardRun(true, "125", "76", "133", "20", "22", "221", "98", "171", "66", "139", "100", "49", "32", "235", "133", "191", "125", "58", "229", "35", "137", "72", "98", "203", "221", "20", "76", "217", "150", "122", "72", "187", "88", "15", "240", "229", "161", "53", "235", "97", "100", "15", "232", "171", "139", "53", "217", "97", "240", "35", "150", "191", "88", "58", "227", "22", "66", "187", "137", "122", "161", "32", "232", "227", "49", "203");
    private final CardRun uncommonB = new CardRun(true, "62", "223", "157", "85", "28", "218", "16", "192", "60", "225", "157", "180", "28", "45", "110", "222", "172", "163", "27", "60", "162", "188", "223", "110", "70", "19", "163", "218", "192", "85", "172", "109", "45", "19", "158", "225", "233", "119", "188", "26", "62", "27", "158", "222", "211", "119", "70", "233", "162", "26", "16", "109", "211", "180");
    private final CardRun rareA = new CardRun(true, "3", "68", "141", "105", "178", "236", "17", "112", "213", "71", "207", "141", "87", "236", "77", "17", "207", "155", "215", "71", "236", "112", "177", "23", "129", "77", "115", "215", "177", "141", "226", "87", "17", "129", "213", "115", "177", "68", "155", "236", "105", "71", "177", "214", "23", "141", "3", "115", "71", "178", "226", "17", "214", "115", "243");
    private final CardRun rareB = new CardRun(true, "84", "10", "174", "247", "25", "89", "121", "243", "10", "186", "61", "147", "84", "121", "130", "220", "174", "89", "147", "25", "61", "247", "186", "130", "89", "245", "25", "84", "186", "147", "121", "10", "61", "245", "243", "130", "174", "84", "247", "89", "220", "243", "186", "245", "130", "10", "220", "247", "147", "174", "61", "121", "25", "220", "245");
    private final CardRun rareC = new CardRun(true, "164", "238", "2", "228", "239", "21", "212", "248", "231", "242", "104", "13", "205", "2", "249", "241", "228", "21", "238", "46", "205", "239", "13", "249", "164", "104", "228", "238", "46", "82", "21", "241", "2", "248", "205", "164", "231", "238", "13", "249", "21", "248", "46", "82", "239", "228", "212", "242", "241", "104", "231", "46", "249", "205", "82", "164", "248", "242", "2", "104", "239", "231", "241", "13", "82", "242");
    private final CardRun rareD = new CardRun(true, "117", "199", "67", "36", "189", "94", "244", "6", "194", "117", "44", "140", "78", "199", "134", "99", "9", "94", "44", "194", "78", "212", "136", "189", "117", "67", "6", "199", "140", "9", "134", "78", "117", "244", "44", "199", "99", "136", "9", "67", "189", "6", "94", "36", "244", "140", "99", "67", "194", "189", "9", "36", "44", "134", "94", "136", "244", "194", "99", "78", "36", "134", "212", "140", "6", "136");
    private final CardRun doubleFacedA = new CardRun(true, "149", "168", "208", "185", "8", "38", "114", "168", "145", "185", "193", "38", "8", "168", "208", "185", "145", "38", "64", "168", "114", "185", "90", "38", "208", "168", "8", "185", "114", "38", "145", "168", "149", "185", "8", "38", "208", "168", "181", "185", "145", "38", "114", "168", "193", "185", "208", "38", "8", "168", "64", "185", "114", "38", "145", "168", "90", "185", "8", "38", "208", "168", "145", "185", "114", "38");
    private final CardRun doubleFacedB = new CardRun(true, "159", "165", "182", "209", "51", "47", "165", "176", "209", "51", "159", "165", "182", "209", "51", "47", "165", "159", "209", "51", "182", "165", "152", "209", "51", "47", "159", "165", "209", "51", "182", "165", "47", "209", "51", "176", "165", "159", "209", "51", "182", "165", "47", "209", "51", "159", "165", "182", "209", "51", "152", "165", "47", "209", "51");
    private final CardRun land = new CardRun(false, "250", "251", "252", "253", "254", "255", "256", "257", "258", "259", "260", "261", "262", "263", "264");

    private final BoosterStructure AABBC1C1C1C1C1 = new BoosterStructure(
        commonA, commonA,
        commonB, commonB,
        commonC1, commonC1, commonC1, commonC1, commonC1
    );
    private final BoosterStructure AAABC1C1C1C1C1 = new BoosterStructure(
        commonA, commonA, commonA,
        commonB,
        commonC1, commonC1, commonC1, commonC1, commonC1
    );
    private final BoosterStructure AAABBC2C2C2C2 = new BoosterStructure(
        commonA, commonA, commonA,
        commonB, commonB,
        commonC2, commonC2, commonC2, commonC2
    );
    private final BoosterStructure AAAABBBC2C2 = new BoosterStructure(
        commonA, commonA, commonA, commonA,
        commonB, commonB, commonB,
        commonC2, commonC2
    );
    private final BoosterStructure AAB = new BoosterStructure(uncommonA, uncommonA, uncommonB);
    private final BoosterStructure ABB = new BoosterStructure(uncommonA, uncommonB, uncommonB);
    private final BoosterStructure R1 = new BoosterStructure(rareA);
    private final BoosterStructure R2 = new BoosterStructure(rareB);
    private final BoosterStructure R3 = new BoosterStructure(rareC);
    private final BoosterStructure R4 = new BoosterStructure(rareD);
    private final BoosterStructure D1 = new BoosterStructure(doubleFacedA);
    private final BoosterStructure D2 = new BoosterStructure(doubleFacedB);
    private final BoosterStructure L1 = new BoosterStructure(land);

    // In order for equal numbers of each common to exist, the average booster must contain:
    // 2.95 A commons (162 / 55)
    // 1.96 B commons (108 / 55)
    // 2.46 C1 commons (135 / 55)
    // 1.64 C2 commons (90 / 55)
    private final RarityConfiguration commonRuns = new RarityConfiguration(
    AABBC1C1C1C1C1, AABBC1C1C1C1C1, AABBC1C1C1C1C1, AABBC1C1C1C1C1, AABBC1C1C1C1C1,
    AABBC1C1C1C1C1, AABBC1C1C1C1C1, AABBC1C1C1C1C1, AABBC1C1C1C1C1, AABBC1C1C1C1C1,
    AABBC1C1C1C1C1, AABBC1C1C1C1C1, AABBC1C1C1C1C1, AABBC1C1C1C1C1,
    AAABC1C1C1C1C1, AAABC1C1C1C1C1, AAABC1C1C1C1C1, AAABC1C1C1C1C1, AAABC1C1C1C1C1,
    AAABC1C1C1C1C1, AAABC1C1C1C1C1, AAABC1C1C1C1C1, AAABC1C1C1C1C1, AAABC1C1C1C1C1,
    AAABC1C1C1C1C1, AAABC1C1C1C1C1, AAABC1C1C1C1C1,
    AAABBC2C2C2C2, AAABBC2C2C2C2, AAABBC2C2C2C2, AAABBC2C2C2C2, AAABBC2C2C2C2,
    AAABBC2C2C2C2, AAABBC2C2C2C2, AAABBC2C2C2C2, AAABBC2C2C2C2, AAABBC2C2C2C2,
    AAABBC2C2C2C2, AAABBC2C2C2C2, AAABBC2C2C2C2, AAABBC2C2C2C2, AAABBC2C2C2C2,
    AAABBC2C2C2C2, AAABBC2C2C2C2,
    AAAABBBC2C2, AAAABBBC2C2, AAAABBBC2C2, AAAABBBC2C2, AAAABBBC2C2, AAAABBBC2C2,
    AAAABBBC2C2, AAAABBBC2C2, AAAABBBC2C2, AAAABBBC2C2, AAAABBBC2C2
    );
    // In order for equal numbers of each uncommon to exist, the average booster must contain:
    // 1.65 A uncommons (33 / 20)
    // 1.35 B uncommons (27 / 20)
    // These numbers are the same for all sets with 60 uncommons in asymmetrical A/B print runs
    private final RarityConfiguration uncommonRuns = new RarityConfiguration(
            AAB, AAB, AAB, AAB, AAB, AAB, AAB, AAB, AAB, AAB, AAB, AAB, AAB,
            ABB, ABB, ABB, ABB, ABB, ABB, ABB
    );
    private final RarityConfiguration rareRuns = new RarityConfiguration(
            R1, R1, R1, R1, R1,
            R2, R2, R2, R2, R2,
            R3, R3, R3, R3, R3, R3,
            R4, R4, R4, R4, R4, R4
    );
    private final RarityConfiguration dfcRuns = new RarityConfiguration(
            D1, D1, D1, D1, D1, D1,
            D2, D2, D2, D2, D2
    );
    private final RarityConfiguration landRuns = new RarityConfiguration(L1);

    @Override
    public List<String> makeBooster() {
        List<String> booster = new ArrayList<>();
        booster.addAll(commonRuns.getNext().makeRun());
        booster.addAll(uncommonRuns.getNext().makeRun());
        booster.addAll(rareRuns.getNext().makeRun());
        booster.addAll(dfcRuns.getNext().makeRun());
        booster.addAll(landRuns.getNext().makeRun());
        return booster;
    }
}
