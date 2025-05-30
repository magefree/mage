package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/sis
 *
 * @author JayDi85
 */
public class ShadowsOfThePast extends ExpansionSet {

    private static final ShadowsOfThePast instance = new ShadowsOfThePast();

    public static ShadowsOfThePast getInstance() {
        return instance;
    }

    private ShadowsOfThePast() {
        super("Shadows of the Past", "SIS", ExpansionSet.buildDate(2023, 3, 21), SetType.MAGIC_ARENA);
        this.hasBoosters = false; // part of SIR - Shadows over Innistrad Remastered
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Angel of Flight Alabaster", 1, Rarity.RARE, mage.cards.a.AngelOfFlightAlabaster.class));
        cards.add(new SetCardInfo("Avacyn's Collar", 68, Rarity.UNCOMMON, mage.cards.a.AvacynsCollar.class));
        cards.add(new SetCardInfo("Avacyn's Pilgrim", 48, Rarity.COMMON, mage.cards.a.AvacynsPilgrim.class));
        cards.add(new SetCardInfo("Avacyn, Angel of Hope", 2, Rarity.MYTHIC, mage.cards.a.AvacynAngelOfHope.class));
        cards.add(new SetCardInfo("Balefire Dragon", 37, Rarity.MYTHIC, mage.cards.b.BalefireDragon.class));
        cards.add(new SetCardInfo("Barter in Blood", 25, Rarity.UNCOMMON, mage.cards.b.BarterInBlood.class));
        cards.add(new SetCardInfo("Battleground Geist", 13, Rarity.COMMON, mage.cards.b.BattlegroundGeist.class));
        cards.add(new SetCardInfo("Blazing Torch", 69, Rarity.COMMON, mage.cards.b.BlazingTorch.class));
        cards.add(new SetCardInfo("Bloodflow Connoisseur", 26, Rarity.COMMON, mage.cards.b.BloodflowConnoisseur.class));
        cards.add(new SetCardInfo("Bloodline Keeper", 27, Rarity.MYTHIC, mage.cards.b.BloodlineKeeper.class));
        cards.add(new SetCardInfo("Bonds of Faith", 3, Rarity.COMMON, mage.cards.b.BondsOfFaith.class));
        cards.add(new SetCardInfo("Brimstone Volley", 38, Rarity.UNCOMMON, mage.cards.b.BrimstoneVolley.class));
        cards.add(new SetCardInfo("Bump in the Night", 28, Rarity.COMMON, mage.cards.b.BumpInTheNight.class));
        cards.add(new SetCardInfo("Butcher's Cleaver", 70, Rarity.UNCOMMON, mage.cards.b.ButchersCleaver.class));
        cards.add(new SetCardInfo("Cackling Counterpart", 14, Rarity.RARE, mage.cards.c.CacklingCounterpart.class));
        cards.add(new SetCardInfo("Demonmail Hauberk", 71, Rarity.UNCOMMON, mage.cards.d.DemonmailHauberk.class));
        cards.add(new SetCardInfo("Devil's Play", 39, Rarity.RARE, mage.cards.d.DevilsPlay.class));
        cards.add(new SetCardInfo("Diregraf Captain", 59, Rarity.UNCOMMON, mage.cards.d.DiregrafCaptain.class));
        cards.add(new SetCardInfo("Divine Reckoning", 4, Rarity.RARE, mage.cards.d.DivineReckoning.class));
        cards.add(new SetCardInfo("Doomed Traveler", 5, Rarity.COMMON, mage.cards.d.DoomedTraveler.class));
        cards.add(new SetCardInfo("Drogskol Captain", 60, Rarity.UNCOMMON, mage.cards.d.DrogskolCaptain.class));
        cards.add(new SetCardInfo("Elder Cathar", 6, Rarity.COMMON, mage.cards.e.ElderCathar.class));
        cards.add(new SetCardInfo("Evolving Wilds", 75, Rarity.COMMON, mage.cards.e.EvolvingWilds.class));
        cards.add(new SetCardInfo("Faithless Looting", 40, Rarity.COMMON, mage.cards.f.FaithlessLooting.class));
        cards.add(new SetCardInfo("Falkenrath Aristocrat", 61, Rarity.MYTHIC, mage.cards.f.FalkenrathAristocrat.class));
        cards.add(new SetCardInfo("Falkenrath Noble", 29, Rarity.UNCOMMON, mage.cards.f.FalkenrathNoble.class));
        cards.add(new SetCardInfo("Feeling of Dread", 7, Rarity.COMMON, mage.cards.f.FeelingOfDread.class));
        cards.add(new SetCardInfo("Fiend Hunter", 8, Rarity.UNCOMMON, mage.cards.f.FiendHunter.class));
        cards.add(new SetCardInfo("Forbidden Alchemy", 15, Rarity.COMMON, mage.cards.f.ForbiddenAlchemy.class));
        cards.add(new SetCardInfo("Forge Devil", 41, Rarity.COMMON, mage.cards.f.ForgeDevil.class));
        cards.add(new SetCardInfo("Galvanic Juggernaut", 72, Rarity.UNCOMMON, mage.cards.g.GalvanicJuggernaut.class));
        cards.add(new SetCardInfo("Garruk Relentless", 49, Rarity.MYTHIC, mage.cards.g.GarrukRelentless.class));
        cards.add(new SetCardInfo("Garruk, the Veil-Cursed", 49, Rarity.MYTHIC, mage.cards.g.GarrukTheVeilCursed.class));
        cards.add(new SetCardInfo("Geist of Saint Traft", 62, Rarity.MYTHIC, mage.cards.g.GeistOfSaintTraft.class));
        cards.add(new SetCardInfo("Ghoulraiser", 30, Rarity.COMMON, mage.cards.g.Ghoulraiser.class));
        cards.add(new SetCardInfo("Gnaw to the Bone", 50, Rarity.COMMON, mage.cards.g.GnawToTheBone.class));
        cards.add(new SetCardInfo("Griselbrand", 31, Rarity.MYTHIC, mage.cards.g.Griselbrand.class));
        cards.add(new SetCardInfo("Gutter Grime", 51, Rarity.RARE, mage.cards.g.GutterGrime.class));
        cards.add(new SetCardInfo("Haunted Fengraf", 76, Rarity.COMMON, mage.cards.h.HauntedFengraf.class));
        cards.add(new SetCardInfo("Havengul Lich", 63, Rarity.MYTHIC, mage.cards.h.HavengulLich.class));
        cards.add(new SetCardInfo("Havengul Runebinder", 16, Rarity.RARE, mage.cards.h.HavengulRunebinder.class));
        cards.add(new SetCardInfo("Hollowhenge Scavenger", 52, Rarity.COMMON, mage.cards.h.HollowhengeScavenger.class));
        cards.add(new SetCardInfo("Howlpack Alpha", 53, Rarity.RARE, mage.cards.h.HowlpackAlpha.class));
        cards.add(new SetCardInfo("Huntmaster of the Fells", 64, Rarity.MYTHIC, mage.cards.h.HuntmasterOfTheFells.class));
        cards.add(new SetCardInfo("Immerwolf", 65, Rarity.UNCOMMON, mage.cards.i.Immerwolf.class));
        cards.add(new SetCardInfo("Increasing Ambition", 32, Rarity.RARE, mage.cards.i.IncreasingAmbition.class));
        cards.add(new SetCardInfo("Invisible Stalker", 17, Rarity.UNCOMMON, mage.cards.i.InvisibleStalker.class));
        cards.add(new SetCardInfo("Kruin Outlaw", 42, Rarity.RARE, mage.cards.k.KruinOutlaw.class));
        cards.add(new SetCardInfo("Lingering Souls", 9, Rarity.UNCOMMON, mage.cards.l.LingeringSouls.class));
        cards.add(new SetCardInfo("Lord of Lineage", 27, Rarity.MYTHIC, mage.cards.l.LordOfLineage.class));
        cards.add(new SetCardInfo("Mayor of Avabruck", 53, Rarity.RARE, mage.cards.m.MayorOfAvabruck.class));
        cards.add(new SetCardInfo("Mist Raven", 18, Rarity.COMMON, mage.cards.m.MistRaven.class));
        cards.add(new SetCardInfo("Moonmist", 54, Rarity.COMMON, mage.cards.m.Moonmist.class));
        cards.add(new SetCardInfo("Murder of Crows", 19, Rarity.UNCOMMON, mage.cards.m.MurderOfCrows.class));
        cards.add(new SetCardInfo("Mystic Retrieval", 20, Rarity.UNCOMMON, mage.cards.m.MysticRetrieval.class));
        cards.add(new SetCardInfo("Past in Flames", 43, Rarity.MYTHIC, mage.cards.p.PastInFlames.class));
        cards.add(new SetCardInfo("Rally the Peasants", 10, Rarity.UNCOMMON, mage.cards.r.RallyThePeasants.class));
        cards.add(new SetCardInfo("Ravager of the Fells", 64, Rarity.MYTHIC, mage.cards.r.RavagerOfTheFells.class));
        cards.add(new SetCardInfo("Requiem Angel", 11, Rarity.RARE, mage.cards.r.RequiemAngel.class));
        cards.add(new SetCardInfo("Seance", 12, Rarity.UNCOMMON, mage.cards.s.Seance.class));
        cards.add(new SetCardInfo("Selhoff Occultist", 21, Rarity.COMMON, mage.cards.s.SelhoffOccultist.class));
        cards.add(new SetCardInfo("Sever the Bloodline", 33, Rarity.UNCOMMON, mage.cards.s.SeverTheBloodline.class));
        cards.add(new SetCardInfo("Sigarda, Host of Herons", 66, Rarity.MYTHIC, mage.cards.s.SigardaHostOfHerons.class));
        cards.add(new SetCardInfo("Silent Departure", 22, Rarity.COMMON, mage.cards.s.SilentDeparture.class));
        cards.add(new SetCardInfo("Skirsdag Cultist", 44, Rarity.UNCOMMON, mage.cards.s.SkirsdagCultist.class));
        cards.add(new SetCardInfo("Skirsdag High Priest", 34, Rarity.RARE, mage.cards.s.SkirsdagHighPriest.class));
        cards.add(new SetCardInfo("Snapcaster Mage", 23, Rarity.MYTHIC, mage.cards.s.SnapcasterMage.class));
        cards.add(new SetCardInfo("Somberwald Sage", 55, Rarity.UNCOMMON, mage.cards.s.SomberwaldSage.class));
        cards.add(new SetCardInfo("Spider Spawning", 56, Rarity.UNCOMMON, mage.cards.s.SpiderSpawning.class));
        cards.add(new SetCardInfo("Stitcher's Apprentice", 24, Rarity.COMMON, mage.cards.s.StitchersApprentice.class));
        cards.add(new SetCardInfo("Stromkirk Captain", 67, Rarity.UNCOMMON, mage.cards.s.StromkirkCaptain.class));
        cards.add(new SetCardInfo("Terror of Kruin Pass", 42, Rarity.RARE, mage.cards.t.TerrorOfKruinPass.class));
        cards.add(new SetCardInfo("Tragic Slip", 35, Rarity.COMMON, mage.cards.t.TragicSlip.class));
        cards.add(new SetCardInfo("Traitorous Blood", 45, Rarity.COMMON, mage.cards.t.TraitorousBlood.class));
        cards.add(new SetCardInfo("Travel Preparations", 57, Rarity.COMMON, mage.cards.t.TravelPreparations.class));
        cards.add(new SetCardInfo("Traveler's Amulet", 73, Rarity.COMMON, mage.cards.t.TravelersAmulet.class));
        cards.add(new SetCardInfo("Unburial Rites", 36, Rarity.UNCOMMON, mage.cards.u.UnburialRites.class));
        cards.add(new SetCardInfo("Vampiric Fury", 46, Rarity.COMMON, mage.cards.v.VampiricFury.class));
        cards.add(new SetCardInfo("Vessel of Endless Rest", 74, Rarity.UNCOMMON, mage.cards.v.VesselOfEndlessRest.class));
        cards.add(new SetCardInfo("Young Wolf", 58, Rarity.COMMON, mage.cards.y.YoungWolf.class));
        cards.add(new SetCardInfo("Zealous Conscripts", 47, Rarity.RARE, mage.cards.z.ZealousConscripts.class));
    }
}