package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

public final class DuelDecksElspethVsTezzeret extends ExpansionSet {

    private static final DuelDecksElspethVsTezzeret instance = new DuelDecksElspethVsTezzeret();

    public static DuelDecksElspethVsTezzeret getInstance() {
        return instance;
    }

    private DuelDecksElspethVsTezzeret() {
        super("Duel Decks: Elspeth vs. Tezzeret", "DDF", ExpansionSet.buildDate(2010, 8, 3), SetType.SUPPLEMENTAL);
        this.blockName = "Duel Decks";
        this.hasBasicLands = true;

        cards.add(new SetCardInfo("Abolish", 29, Rarity.UNCOMMON, mage.cards.a.Abolish.class));
        cards.add(new SetCardInfo("Aether Spellbomb", 61, Rarity.COMMON, mage.cards.a.AetherSpellbomb.class));
        cards.add(new SetCardInfo("Angel of Salvation", 20, Rarity.RARE, mage.cards.a.AngelOfSalvation.class));
        cards.add(new SetCardInfo("Arcbound Worker", 40, Rarity.COMMON, mage.cards.a.ArcboundWorker.class));
        cards.add(new SetCardInfo("Argivian Restoration", 69, Rarity.UNCOMMON, mage.cards.a.ArgivianRestoration.class));
        cards.add(new SetCardInfo("Assembly-Worker", 45, Rarity.UNCOMMON, mage.cards.a.AssemblyWorker.class));
        cards.add(new SetCardInfo("Blinding Beam", 28, Rarity.COMMON, mage.cards.b.BlindingBeam.class));
        cards.add(new SetCardInfo("Burrenton Bombardier", 11, Rarity.COMMON, mage.cards.b.BurrentonBombardier.class));
        cards.add(new SetCardInfo("Catapult Master", 18, Rarity.RARE, mage.cards.c.CatapultMaster.class));
        cards.add(new SetCardInfo("Celestial Crusader", 14, Rarity.UNCOMMON, mage.cards.c.CelestialCrusader.class));
        cards.add(new SetCardInfo("Clockwork Condor", 50, Rarity.COMMON, mage.cards.c.ClockworkCondor.class));
        cards.add(new SetCardInfo("Clockwork Hydra", 55, Rarity.UNCOMMON, mage.cards.c.ClockworkHydra.class));
        cards.add(new SetCardInfo("Conclave Equenaut", 19, Rarity.COMMON, mage.cards.c.ConclaveEquenaut.class));
        cards.add(new SetCardInfo("Conclave Phalanx", 16, Rarity.UNCOMMON, mage.cards.c.ConclavePhalanx.class));
        cards.add(new SetCardInfo("Contagion Clasp", 63, Rarity.UNCOMMON, mage.cards.c.ContagionClasp.class));
        cards.add(new SetCardInfo("Crusade", 27, Rarity.RARE, mage.cards.c.Crusade.class));
        cards.add(new SetCardInfo("Darksteel Citadel", 72, Rarity.COMMON, mage.cards.d.DarksteelCitadel.class));
        cards.add(new SetCardInfo("Daru Encampment", 32, Rarity.UNCOMMON, mage.cards.d.DaruEncampment.class));
        cards.add(new SetCardInfo("Echoing Truth", 66, Rarity.COMMON, mage.cards.e.EchoingTruth.class));
        cards.add(new SetCardInfo("Elite Vanguard", 2, Rarity.UNCOMMON, mage.cards.e.EliteVanguard.class));
        cards.add(new SetCardInfo("Elixir of Immortality", 62, Rarity.UNCOMMON, mage.cards.e.ElixirOfImmortality.class));
        cards.add(new SetCardInfo("Elspeth, Knight-Errant", 1, Rarity.MYTHIC, mage.cards.e.ElspethKnightErrant.class));
        cards.add(new SetCardInfo("Energy Chamber", 64, Rarity.UNCOMMON, mage.cards.e.EnergyChamber.class));
        cards.add(new SetCardInfo("Esperzoa", 47, Rarity.UNCOMMON, mage.cards.e.Esperzoa.class));
        cards.add(new SetCardInfo("Everflowing Chalice", 60, Rarity.UNCOMMON, mage.cards.e.EverflowingChalice.class));
        cards.add(new SetCardInfo("Faerie Mechanist", 54, Rarity.COMMON, mage.cards.f.FaerieMechanist.class));
        cards.add(new SetCardInfo("Foil", 70, Rarity.UNCOMMON, mage.cards.f.Foil.class));
        cards.add(new SetCardInfo("Frogmite", 51, Rarity.COMMON, mage.cards.f.Frogmite.class));
        cards.add(new SetCardInfo("Glory Seeker", 7, Rarity.COMMON, mage.cards.g.GlorySeeker.class));
        cards.add(new SetCardInfo("Goldmeadow Harrier", 3, Rarity.COMMON, mage.cards.g.GoldmeadowHarrier.class));
        cards.add(new SetCardInfo("Infantry Veteran", 4, Rarity.COMMON, mage.cards.i.InfantryVeteran.class));
        cards.add(new SetCardInfo("Island", 76, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 77, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 78, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 79, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Journey to Nowhere", 23, Rarity.COMMON, mage.cards.j.JourneyToNowhere.class));
        cards.add(new SetCardInfo("Juggernaut", 52, Rarity.UNCOMMON, mage.cards.j.Juggernaut.class));
        cards.add(new SetCardInfo("Kabira Crossroads", 33, Rarity.COMMON, mage.cards.k.KabiraCrossroads.class));
        cards.add(new SetCardInfo("Kemba's Skyguard", 13, Rarity.COMMON, mage.cards.k.KembasSkyguard.class));
        cards.add(new SetCardInfo("Kor Aeronaut", 10, Rarity.UNCOMMON, mage.cards.k.KorAeronaut.class));
        cards.add(new SetCardInfo("Kor Hookmaster", 12, Rarity.COMMON, mage.cards.k.KorHookmaster.class));
        cards.add(new SetCardInfo("Kor Skyfisher", 8, Rarity.COMMON, mage.cards.k.KorSkyfisher.class));
        cards.add(new SetCardInfo("Loyal Sentry", 5, Rarity.RARE, mage.cards.l.LoyalSentry.class));
        cards.add(new SetCardInfo("Master of Etherium", 48, Rarity.RARE, mage.cards.m.MasterOfEtherium.class));
        cards.add(new SetCardInfo("Mighty Leap", 24, Rarity.COMMON, mage.cards.m.MightyLeap.class));
        cards.add(new SetCardInfo("Mishra's Factory", 73, Rarity.UNCOMMON, mage.cards.m.MishrasFactory.class));
        cards.add(new SetCardInfo("Moonglove Extract", 67, Rarity.COMMON, mage.cards.m.MoongloveExtract.class));
        cards.add(new SetCardInfo("Mosquito Guard", 6, Rarity.COMMON, mage.cards.m.MosquitoGuard.class));
        cards.add(new SetCardInfo("Pentavus", 58, Rarity.RARE, mage.cards.p.Pentavus.class));
        cards.add(new SetCardInfo("Plains", 35, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 36, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 37, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 38, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Qumulox", 59, Rarity.UNCOMMON, mage.cards.q.Qumulox.class));
        cards.add(new SetCardInfo("Raise the Alarm", 25, Rarity.COMMON, mage.cards.r.RaiseTheAlarm.class));
        cards.add(new SetCardInfo("Razor Barrier", 26, Rarity.COMMON, mage.cards.r.RazorBarrier.class));
        cards.add(new SetCardInfo("Razormane Masticore", 56, Rarity.RARE, mage.cards.r.RazormaneMasticore.class));
        cards.add(new SetCardInfo("Runed Servitor", 42, Rarity.UNCOMMON, mage.cards.r.RunedServitor.class));
        cards.add(new SetCardInfo("Rustic Clachan", 34, Rarity.RARE, mage.cards.r.RusticClachan.class));
        cards.add(new SetCardInfo("Saltblast", 30, Rarity.UNCOMMON, mage.cards.s.Saltblast.class));
        cards.add(new SetCardInfo("Seasoned Marshal", 15, Rarity.UNCOMMON, mage.cards.s.SeasonedMarshal.class));
        cards.add(new SetCardInfo("Seat of the Synod", 74, Rarity.COMMON, mage.cards.s.SeatOfTheSynod.class));
        cards.add(new SetCardInfo("Serrated Biskelion", 46, Rarity.UNCOMMON, mage.cards.s.SerratedBiskelion.class));
        cards.add(new SetCardInfo("Silver Myr", 43, Rarity.COMMON, mage.cards.s.SilverMyr.class));
        cards.add(new SetCardInfo("Stalking Stones", 75, Rarity.UNCOMMON, mage.cards.s.StalkingStones.class));
        cards.add(new SetCardInfo("Steel Overseer", 44, Rarity.RARE, mage.cards.s.SteelOverseer.class));
        cards.add(new SetCardInfo("Steel Wall", 41, Rarity.COMMON, mage.cards.s.SteelWall.class));
        cards.add(new SetCardInfo("Stormfront Riders", 17, Rarity.UNCOMMON, mage.cards.s.StormfrontRiders.class));
        cards.add(new SetCardInfo("Sunlance", 21, Rarity.COMMON, mage.cards.s.Sunlance.class));
        cards.add(new SetCardInfo("Swell of Courage", 31, Rarity.UNCOMMON, mage.cards.s.SwellOfCourage.class));
        cards.add(new SetCardInfo("Swords to Plowshares", 22, Rarity.UNCOMMON, mage.cards.s.SwordsToPlowshares.class));
        cards.add(new SetCardInfo("Synod Centurion", 53, Rarity.UNCOMMON, mage.cards.s.SynodCenturion.class));
        cards.add(new SetCardInfo("Temple Acolyte", 9, Rarity.COMMON, mage.cards.t.TempleAcolyte.class));
        cards.add(new SetCardInfo("Tezzeret the Seeker", 39, Rarity.MYTHIC, mage.cards.t.TezzeretTheSeeker.class));
        cards.add(new SetCardInfo("Thirst for Knowledge", 68, Rarity.UNCOMMON, mage.cards.t.ThirstForKnowledge.class));
        cards.add(new SetCardInfo("Thoughtcast", 71, Rarity.COMMON, mage.cards.t.Thoughtcast.class));
        cards.add(new SetCardInfo("Trinket Mage", 49, Rarity.COMMON, mage.cards.t.TrinketMage.class));
        cards.add(new SetCardInfo("Trip Noose", 65, Rarity.UNCOMMON, mage.cards.t.TripNoose.class));
        cards.add(new SetCardInfo("Triskelion", 57, Rarity.RARE, mage.cards.t.Triskelion.class));
    }
}
