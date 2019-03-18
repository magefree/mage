
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author fireshoes
 */
public final class DuelDecksElspethVsKiora extends ExpansionSet {

    private static final DuelDecksElspethVsKiora instance = new DuelDecksElspethVsKiora();

    public static DuelDecksElspethVsKiora getInstance() {
        return instance;
    }

    private DuelDecksElspethVsKiora() {
        super("Duel Decks: Elspeth vs. Kiora", "DDO", ExpansionSet.buildDate(2015, 2, 27), SetType.SUPPLEMENTAL);
        this.blockName = "Duel Decks";
        this.hasBasicLands = true;

        cards.add(new SetCardInfo("Accumulated Knowledge", 35, Rarity.COMMON, mage.cards.a.AccumulatedKnowledge.class));
        cards.add(new SetCardInfo("Aetherize", 36, Rarity.UNCOMMON, mage.cards.a.Aetherize.class));
        cards.add(new SetCardInfo("Banisher Priest", 2, Rarity.UNCOMMON, mage.cards.b.BanisherPriest.class));
        cards.add(new SetCardInfo("Captain of the Watch", 3, Rarity.RARE, mage.cards.c.CaptainOfTheWatch.class));
        cards.add(new SetCardInfo("Celestial Flare", 4, Rarity.COMMON, mage.cards.c.CelestialFlare.class));
        cards.add(new SetCardInfo("Coiling Oracle", 51, Rarity.COMMON, mage.cards.c.CoilingOracle.class));
        cards.add(new SetCardInfo("Court Street Denizen", 5, Rarity.COMMON, mage.cards.c.CourtStreetDenizen.class));
        cards.add(new SetCardInfo("Dauntless Onslaught", 6, Rarity.UNCOMMON, mage.cards.d.DauntlessOnslaught.class));
        cards.add(new SetCardInfo("Decree of Justice", 7, Rarity.RARE, mage.cards.d.DecreeOfJustice.class));
        cards.add(new SetCardInfo("Dictate of Heliod", 8, Rarity.RARE, mage.cards.d.DictateOfHeliod.class));
        cards.add(new SetCardInfo("Elspeth, Sun's Champion", 1, Rarity.MYTHIC, mage.cards.e.ElspethSunsChampion.class));
        cards.add(new SetCardInfo("Evolving Wilds", 58, Rarity.COMMON, mage.cards.e.EvolvingWilds.class));
        cards.add(new SetCardInfo("Explore", 45, Rarity.COMMON, mage.cards.e.Explore.class));
        cards.add(new SetCardInfo("Explosive Vegetation", 46, Rarity.UNCOMMON, mage.cards.e.ExplosiveVegetation.class));
        cards.add(new SetCardInfo("Forest", 63, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 64, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 65, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gempalm Avenger", 9, Rarity.COMMON, mage.cards.g.GempalmAvenger.class));
        cards.add(new SetCardInfo("Grazing Gladehart", 47, Rarity.COMMON, mage.cards.g.GrazingGladehart.class));
        cards.add(new SetCardInfo("Gustcloak Harrier", 10, Rarity.COMMON, mage.cards.g.GustcloakHarrier.class));
        cards.add(new SetCardInfo("Gustcloak Savior", 11, Rarity.RARE, mage.cards.g.GustcloakSavior.class));
        cards.add(new SetCardInfo("Gustcloak Sentinel", 12, Rarity.UNCOMMON, mage.cards.g.GustcloakSentinel.class));
        cards.add(new SetCardInfo("Gustcloak Skirmisher", 13, Rarity.UNCOMMON, mage.cards.g.GustcloakSkirmisher.class));
        cards.add(new SetCardInfo("Icatian Javelineers", 14, Rarity.COMMON, mage.cards.i.IcatianJavelineers.class));
        cards.add(new SetCardInfo("Inkwell Leviathan", 37, Rarity.RARE, mage.cards.i.InkwellLeviathan.class));
        cards.add(new SetCardInfo("Island", 60, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 61, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 62, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kinsbaile Skirmisher", 15, Rarity.COMMON, mage.cards.k.KinsbaileSkirmisher.class));
        cards.add(new SetCardInfo("Kiora's Follower", 52, Rarity.UNCOMMON, mage.cards.k.KiorasFollower.class));
        cards.add(new SetCardInfo("Kiora, the Crashing Wave", 34, Rarity.MYTHIC, mage.cards.k.KioraTheCrashingWave.class));
        cards.add(new SetCardInfo("Kor Skyfisher", 16, Rarity.COMMON, mage.cards.k.KorSkyfisher.class));
        cards.add(new SetCardInfo("Lorescale Coatl", 53, Rarity.UNCOMMON, mage.cards.l.LorescaleCoatl.class));
        cards.add(new SetCardInfo("Loxodon Partisan", 17, Rarity.COMMON, mage.cards.l.LoxodonPartisan.class));
        cards.add(new SetCardInfo("Man-o'-War", 38, Rarity.COMMON, mage.cards.m.ManOWar.class));
        cards.add(new SetCardInfo("Mighty Leap", 18, Rarity.COMMON, mage.cards.m.MightyLeap.class));
        cards.add(new SetCardInfo("Mortal's Ardor", 19, Rarity.COMMON, mage.cards.m.MortalsArdor.class));
        cards.add(new SetCardInfo("Mother of Runes", 20, Rarity.UNCOMMON, mage.cards.m.MotherOfRunes.class));
        cards.add(new SetCardInfo("Nessian Asp", 48, Rarity.COMMON, mage.cards.n.NessianAsp.class));
        cards.add(new SetCardInfo("Netcaster Spider", 49, Rarity.COMMON, mage.cards.n.NetcasterSpider.class));
        cards.add(new SetCardInfo("Nimbus Swimmer", 54, Rarity.UNCOMMON, mage.cards.n.NimbusSwimmer.class));
        cards.add(new SetCardInfo("Noble Templar", 21, Rarity.COMMON, mage.cards.n.NobleTemplar.class));
        cards.add(new SetCardInfo("Omenspeaker", 39, Rarity.COMMON, mage.cards.o.Omenspeaker.class));
        cards.add(new SetCardInfo("Peel from Reality", 40, Rarity.COMMON, mage.cards.p.PeelFromReality.class));
        cards.add(new SetCardInfo("Plains", 30, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 31, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 32, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 33, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plasm Capture", 55, Rarity.RARE, mage.cards.p.PlasmCapture.class));
        cards.add(new SetCardInfo("Precinct Captain", 22, Rarity.RARE, mage.cards.p.PrecinctCaptain.class));
        cards.add(new SetCardInfo("Raise the Alarm", 23, Rarity.COMMON, mage.cards.r.RaiseTheAlarm.class));
        cards.add(new SetCardInfo("Scourge of Fleets", 41, Rarity.RARE, mage.cards.s.ScourgeOfFleets.class));
        cards.add(new SetCardInfo("Sealock Monster", 42, Rarity.UNCOMMON, mage.cards.s.SealockMonster.class));
        cards.add(new SetCardInfo("Secluded Steppe", 29, Rarity.COMMON, mage.cards.s.SecludedSteppe.class));
        cards.add(new SetCardInfo("Simic Sky Swallower", 56, Rarity.RARE, mage.cards.s.SimicSkySwallower.class));
        cards.add(new SetCardInfo("Soul Parry", 24, Rarity.COMMON, mage.cards.s.SoulParry.class));
        cards.add(new SetCardInfo("Standing Troops", 25, Rarity.COMMON, mage.cards.s.StandingTroops.class));
        cards.add(new SetCardInfo("Sunlance", 26, Rarity.COMMON, mage.cards.s.Sunlance.class));
        cards.add(new SetCardInfo("Surrakar Banisher", 43, Rarity.COMMON, mage.cards.s.SurrakarBanisher.class));
        cards.add(new SetCardInfo("Temple of the False God", 59, Rarity.UNCOMMON, mage.cards.t.TempleOfTheFalseGod.class));
        cards.add(new SetCardInfo("Time to Feed", 50, Rarity.COMMON, mage.cards.t.TimeToFeed.class));
        cards.add(new SetCardInfo("Urban Evolution", 57, Rarity.UNCOMMON, mage.cards.u.UrbanEvolution.class));
        cards.add(new SetCardInfo("Veteran Armorsmith", 27, Rarity.COMMON, mage.cards.v.VeteranArmorsmith.class));
        cards.add(new SetCardInfo("Veteran Swordsmith", 28, Rarity.COMMON, mage.cards.v.VeteranSwordsmith.class));
        cards.add(new SetCardInfo("Whelming Wave", 44, Rarity.RARE, mage.cards.w.WhelmingWave.class));
    }
}
