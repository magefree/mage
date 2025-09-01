
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author Susucr
 */
public final class SpecialGuests extends ExpansionSet {

    private static final SpecialGuests instance = new SpecialGuests();

    public static SpecialGuests getInstance() {
        return instance;
    }

    private SpecialGuests() {
        super("Special Guests", "SPG", ExpansionSet.buildDate(2023, 11, 17), SetType.SUPPLEMENTAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Akroma's Memorial", 81, Rarity.MYTHIC, mage.cards.a.AkromasMemorial.class));
        cards.add(new SetCardInfo("Arid Mesa", 109, Rarity.MYTHIC, mage.cards.a.AridMesa.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Arid Mesa", 114, Rarity.MYTHIC, mage.cards.a.AridMesa.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bloom Tender", 79, Rarity.MYTHIC, mage.cards.b.BloomTender.class));
        cards.add(new SetCardInfo("Bone Miser", 87, Rarity.MYTHIC, mage.cards.b.BoneMiser.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bone Miser", 97, Rarity.MYTHIC, mage.cards.b.BoneMiser.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Brazen Borrower", 30, Rarity.MYTHIC, mage.cards.b.BrazenBorrower.class));
        cards.add(new SetCardInfo("Breeches, Brazen Plunderer", 6, Rarity.UNCOMMON, mage.cards.b.BreechesBrazenPlunderer.class));
        cards.add(new SetCardInfo("Bridge from Below", 3, Rarity.RARE, mage.cards.b.BridgeFromBelow.class));
        cards.add(new SetCardInfo("Burgeoning", 126, Rarity.MYTHIC, mage.cards.b.Burgeoning.class, FULL_ART));
        cards.add(new SetCardInfo("Carnage Tyrant", 10, Rarity.MYTHIC, mage.cards.c.CarnageTyrant.class));
        cards.add(new SetCardInfo("Cavalier of Dawn", 84, Rarity.MYTHIC, mage.cards.c.CavalierOfDawn.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cavalier of Dawn", 94, Rarity.MYTHIC, mage.cards.c.CavalierOfDawn.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Chandra's Ignition", 89, Rarity.MYTHIC, mage.cards.c.ChandrasIgnition.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Chandra's Ignition", 99, Rarity.MYTHIC, mage.cards.c.ChandrasIgnition.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Chrome Mox", 102, Rarity.MYTHIC, mage.cards.c.ChromeMox.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Chrome Mox", 92, Rarity.MYTHIC, mage.cards.c.ChromeMox.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Collected Company", 72, Rarity.MYTHIC, mage.cards.c.CollectedCompany.class));
        cards.add(new SetCardInfo("Condemn", 74, Rarity.MYTHIC, mage.cards.c.Condemn.class));
        cards.add(new SetCardInfo("Crashing Footfalls", 25, Rarity.MYTHIC, mage.cards.c.CrashingFootfalls.class));
        cards.add(new SetCardInfo("Damnation", 68, Rarity.MYTHIC, mage.cards.d.Damnation.class));
        cards.add(new SetCardInfo("Dargo, the Shipwrecker", 7, Rarity.UNCOMMON, mage.cards.d.DargoTheShipwrecker.class));
        cards.add(new SetCardInfo("Darkness", 124, Rarity.MYTHIC, mage.cards.d.Darkness.class, FULL_ART));
        cards.add(new SetCardInfo("Deafening Silence", 120, Rarity.MYTHIC, mage.cards.d.DeafeningSilence.class, FULL_ART));
        cards.add(new SetCardInfo("Desert", 37, Rarity.MYTHIC, mage.cards.d.Desert.class));
        cards.add(new SetCardInfo("Desertion", 31, Rarity.MYTHIC, mage.cards.d.Desertion.class));
        cards.add(new SetCardInfo("Dismember", 41, Rarity.MYTHIC, mage.cards.d.Dismember.class));
        cards.add(new SetCardInfo("Drown in the Loch", 27, Rarity.MYTHIC, mage.cards.d.DrownInTheLoch.class));
        cards.add(new SetCardInfo("Eerie Ultimatum", 104, Rarity.MYTHIC, mage.cards.e.EerieUltimatum.class));
        cards.add(new SetCardInfo("Embercleave", 77, Rarity.MYTHIC, mage.cards.e.Embercleave.class));
        cards.add(new SetCardInfo("Emergent Ultimatum", 105, Rarity.MYTHIC, mage.cards.e.EmergentUltimatum.class));
        cards.add(new SetCardInfo("Endurance", 48, Rarity.MYTHIC, mage.cards.e.Endurance.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Endurance", 53, Rarity.MYTHIC, mage.cards.e.Endurance.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Expressive Iteration", 43, Rarity.MYTHIC, mage.cards.e.ExpressiveIteration.class));
        cards.add(new SetCardInfo("Expropriate", 66, Rarity.MYTHIC, mage.cards.e.Expropriate.class));
        cards.add(new SetCardInfo("Fabricate", 20, Rarity.MYTHIC, mage.cards.f.Fabricate.class));
        cards.add(new SetCardInfo("Field of the Dead", 28, Rarity.MYTHIC, mage.cards.f.FieldOfTheDead.class));
        cards.add(new SetCardInfo("Fiend Artisan", 83, Rarity.MYTHIC, mage.cards.f.FiendArtisan.class));
        cards.add(new SetCardInfo("Frogmite", 61, Rarity.MYTHIC, mage.cards.f.Frogmite.class));
        cards.add(new SetCardInfo("Fury", 47, Rarity.MYTHIC, mage.cards.f.Fury.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fury", 52, Rarity.MYTHIC, mage.cards.f.Fury.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Galvanic Blast", 100, Rarity.MYTHIC, mage.cards.g.GalvanicBlast.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Galvanic Blast", 90, Rarity.MYTHIC, mage.cards.g.GalvanicBlast.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gamble", 24, Rarity.MYTHIC, mage.cards.g.Gamble.class));
        cards.add(new SetCardInfo("Genesis Ultimatum", 106, Rarity.MYTHIC, mage.cards.g.GenesisUltimatum.class));
        cards.add(new SetCardInfo("Ghalta, Primal Hunger", 11, Rarity.RARE, mage.cards.g.GhaltaPrimalHunger.class));
        cards.add(new SetCardInfo("Ghostly Prison", 19, Rarity.MYTHIC, mage.cards.g.GhostlyPrison.class));
        cards.add(new SetCardInfo("Goblin Bushwhacker", 78, Rarity.MYTHIC, mage.cards.g.GoblinBushwhacker.class));
        cards.add(new SetCardInfo("Green Sun's Zenith", 127, Rarity.MYTHIC, mage.cards.g.GreenSunsZenith.class, FULL_ART));
        cards.add(new SetCardInfo("Grief", 46, Rarity.MYTHIC, mage.cards.g.Grief.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Grief", 51, Rarity.MYTHIC, mage.cards.g.Grief.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Grim Tutor", 76, Rarity.MYTHIC, mage.cards.g.GrimTutor.class));
        cards.add(new SetCardInfo("Hallowed Haunting", 64, Rarity.MYTHIC, mage.cards.h.HallowedHaunting.class));
        cards.add(new SetCardInfo("Inspired Ultimatum", 107, Rarity.MYTHIC, mage.cards.i.InspiredUltimatum.class));
        cards.add(new SetCardInfo("Kalamax, the Stormsire", 13, Rarity.MYTHIC, mage.cards.k.KalamaxTheStormsire.class));
        cards.add(new SetCardInfo("Kindred Charge", 58, Rarity.MYTHIC, mage.cards.k.KindredCharge.class));
        cards.add(new SetCardInfo("Ledger Shredder", 55, Rarity.MYTHIC, mage.cards.l.LedgerShredder.class));
        cards.add(new SetCardInfo("Lord Windgrace", 14, Rarity.MYTHIC, mage.cards.l.LordWindgrace.class));
        cards.add(new SetCardInfo("Lord of Atlantis", 1, Rarity.RARE, mage.cards.l.LordOfAtlantis.class));
        cards.add(new SetCardInfo("Lord of the Undead", 88, Rarity.MYTHIC, mage.cards.l.LordOfTheUndead.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lord of the Undead", 98, Rarity.MYTHIC, mage.cards.l.LordOfTheUndead.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Maddening Hex", 70, Rarity.MYTHIC, mage.cards.m.MaddeningHex.class));
        cards.add(new SetCardInfo("Magus of the Moon", 125, Rarity.MYTHIC, mage.cards.m.MagusOfTheMoon.class, FULL_ART));
        cards.add(new SetCardInfo("Malcolm, Keen-Eyed Navigator", 2, Rarity.UNCOMMON, mage.cards.m.MalcolmKeenEyedNavigator.class));
        cards.add(new SetCardInfo("Mana Crypt", "17a", Rarity.MYTHIC, mage.cards.m.ManaCrypt.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mana Crypt", "17b", Rarity.MYTHIC, mage.cards.m.ManaCrypt.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mana Crypt", "17c", Rarity.MYTHIC, mage.cards.m.ManaCrypt.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mana Crypt", "17d", Rarity.MYTHIC, mage.cards.m.ManaCrypt.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mana Crypt", "17e", Rarity.MYTHIC, mage.cards.m.ManaCrypt.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mana Crypt", "17f", Rarity.MYTHIC, mage.cards.m.ManaCrypt.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mana Crypt", 17, Rarity.MYTHIC, mage.cards.m.ManaCrypt.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Marsh Flats", 110, Rarity.MYTHIC, mage.cards.m.MarshFlats.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Marsh Flats", 115, Rarity.MYTHIC, mage.cards.m.MarshFlats.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mephidross Vampire", 4, Rarity.RARE, mage.cards.m.MephidrossVampire.class));
        cards.add(new SetCardInfo("Mirri, Weatherlight Duelist", 15, Rarity.MYTHIC, mage.cards.m.MirriWeatherlightDuelist.class));
        cards.add(new SetCardInfo("Misty Rainforest", 111, Rarity.MYTHIC, mage.cards.m.MistyRainforest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Misty Rainforest", 116, Rarity.MYTHIC, mage.cards.m.MistyRainforest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Morbid Opportunist", 32, Rarity.MYTHIC, mage.cards.m.MorbidOpportunist.class));
        cards.add(new SetCardInfo("Mystic Snake", 35, Rarity.MYTHIC, mage.cards.m.MysticSnake.class));
        cards.add(new SetCardInfo("Nexus of Fate", 122, Rarity.MYTHIC, mage.cards.n.NexusOfFate.class, FULL_ART));
        cards.add(new SetCardInfo("Notion Thief", 36, Rarity.MYTHIC, mage.cards.n.NotionThief.class));
        cards.add(new SetCardInfo("Noxious Revival", 73, Rarity.MYTHIC, mage.cards.n.NoxiousRevival.class));
        cards.add(new SetCardInfo("Paradise Druid", 80, Rarity.MYTHIC, mage.cards.p.ParadiseDruid.class));
        cards.add(new SetCardInfo("Paradox Haze", 123, Rarity.MYTHIC, mage.cards.p.ParadoxHaze.class, FULL_ART));
        cards.add(new SetCardInfo("Pathbreaker Ibex", 101, Rarity.MYTHIC, mage.cards.p.PathbreakerIbex.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Pathbreaker Ibex", 91, Rarity.MYTHIC, mage.cards.p.PathbreakerIbex.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Persist", 42, Rarity.MYTHIC, mage.cards.p.Persist.class));
        cards.add(new SetCardInfo("Phantasmal Image", 67, Rarity.MYTHIC, mage.cards.p.PhantasmalImage.class));
        cards.add(new SetCardInfo("Pitiless Plunderer", 5, Rarity.UNCOMMON, mage.cards.p.PitilessPlunderer.class));
        cards.add(new SetCardInfo("Polyraptor", 12, Rarity.MYTHIC, mage.cards.p.Polyraptor.class));
        cards.add(new SetCardInfo("Port Razer", 33, Rarity.MYTHIC, mage.cards.p.PortRazer.class));
        cards.add(new SetCardInfo("Prismatic Ending", 40, Rarity.MYTHIC, mage.cards.p.PrismaticEnding.class));
        cards.add(new SetCardInfo("Prismatic Vista", 38, Rarity.MYTHIC, mage.cards.p.PrismaticVista.class));
        cards.add(new SetCardInfo("Rampaging Ferocidon", 8, Rarity.RARE, mage.cards.r.RampagingFerocidon.class));
        cards.add(new SetCardInfo("Rat Colony", 56, Rarity.MYTHIC, mage.cards.r.RatColony.class));
        cards.add(new SetCardInfo("Relentless Rats", 57, Rarity.MYTHIC, mage.cards.r.RelentlessRats.class));
        cards.add(new SetCardInfo("Robe of Stars", 121, Rarity.MYTHIC, mage.cards.r.RobeOfStars.class, FULL_ART));
        cards.add(new SetCardInfo("Ruinous Ultimatum", 108, Rarity.MYTHIC, mage.cards.r.RuinousUltimatum.class));
        cards.add(new SetCardInfo("Sacrifice", 69, Rarity.MYTHIC, mage.cards.s.Sacrifice.class));
        cards.add(new SetCardInfo("Scalding Tarn", 112, Rarity.MYTHIC, mage.cards.s.ScaldingTarn.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Scalding Tarn", 117, Rarity.MYTHIC, mage.cards.s.ScaldingTarn.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Scapeshift", 34, Rarity.MYTHIC, mage.cards.s.Scapeshift.class));
        cards.add(new SetCardInfo("Secluded Courtyard", 63, Rarity.MYTHIC, mage.cards.s.SecludedCourtyard.class));
        cards.add(new SetCardInfo("Show and Tell", 21, Rarity.MYTHIC, mage.cards.s.ShowAndTell.class));
        cards.add(new SetCardInfo("Skysovereign, Consul Flagship", 103, Rarity.MYTHIC, mage.cards.s.SkysovereignConsulFlagship.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Skysovereign, Consul Flagship", 93, Rarity.MYTHIC, mage.cards.s.SkysovereignConsulFlagship.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sliver Overlord", 128, Rarity.MYTHIC, mage.cards.s.SliverOverlord.class, FULL_ART));
        cards.add(new SetCardInfo("Solitude", 44, Rarity.MYTHIC, mage.cards.s.Solitude.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Solitude", 49, Rarity.MYTHIC, mage.cards.s.Solitude.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Soul Warden", 65, Rarity.MYTHIC, mage.cards.s.SoulWarden.class));
        cards.add(new SetCardInfo("Sphinx's Tutelage", 75, Rarity.MYTHIC, mage.cards.s.SphinxsTutelage.class));
        cards.add(new SetCardInfo("Star Compass", 18, Rarity.UNCOMMON, mage.cards.s.StarCompass.class));
        cards.add(new SetCardInfo("Stoneforge Mystic", 29, Rarity.MYTHIC, mage.cards.s.StoneforgeMystic.class));
        cards.add(new SetCardInfo("Subtlety", 45, Rarity.MYTHIC, mage.cards.s.Subtlety.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Subtlety", 50, Rarity.MYTHIC, mage.cards.s.Subtlety.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sword of Fire and Ice", 62, Rarity.MYTHIC, mage.cards.s.SwordOfFireAndIce.class));
        cards.add(new SetCardInfo("Swords to Plowshares", 54, Rarity.MYTHIC, mage.cards.s.SwordsToPlowshares.class));
        cards.add(new SetCardInfo("Sylvan Tutor", 59, Rarity.MYTHIC, mage.cards.s.SylvanTutor.class));
        cards.add(new SetCardInfo("Temporal Manipulation", 82, Rarity.MYTHIC, mage.cards.t.TemporalManipulation.class));
        cards.add(new SetCardInfo("Thought-Knot Seer", 39, Rarity.MYTHIC, mage.cards.t.ThoughtKnotSeer.class));
        cards.add(new SetCardInfo("Thoughtcast", 85, Rarity.MYTHIC, mage.cards.t.Thoughtcast.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thoughtcast", 95, Rarity.MYTHIC, mage.cards.t.Thoughtcast.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thrasios, Triton Hero", 16, Rarity.RARE, mage.cards.t.ThrasiosTritonHero.class));
        cards.add(new SetCardInfo("Tireless Tracker", 26, Rarity.MYTHIC, mage.cards.t.TirelessTracker.class));
        cards.add(new SetCardInfo("Toski, Bearer of Secrets", 60, Rarity.MYTHIC, mage.cards.t.ToskiBearerOfSecrets.class));
        cards.add(new SetCardInfo("Tragic Slip", 22, Rarity.MYTHIC, mage.cards.t.TragicSlip.class));
        cards.add(new SetCardInfo("Underworld Breach", 9, Rarity.RARE, mage.cards.u.UnderworldBreach.class));
        cards.add(new SetCardInfo("Unholy Heat", 71, Rarity.MYTHIC, mage.cards.u.UnholyHeat.class));
        cards.add(new SetCardInfo("Verdant Catacombs", 113, Rarity.MYTHIC, mage.cards.v.VerdantCatacombs.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Verdant Catacombs", 118, Rarity.MYTHIC, mage.cards.v.VerdantCatacombs.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Victimize", 23, Rarity.MYTHIC, mage.cards.v.Victimize.class));
        cards.add(new SetCardInfo("Warping Wail", 119, Rarity.MYTHIC, mage.cards.w.WarpingWail.class, FULL_ART));
        cards.add(new SetCardInfo("Whir of Invention", 86, Rarity.MYTHIC, mage.cards.w.WhirOfInvention.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Whir of Invention", 96, Rarity.MYTHIC, mage.cards.w.WhirOfInvention.class, NON_FULL_USE_VARIOUS));
    }
}
