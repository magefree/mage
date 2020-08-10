package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/gn2
 * @author TheElk801
 */
public class GameNight2019 extends ExpansionSet {

    private static final GameNight2019 instance = new GameNight2019();

    public static GameNight2019 getInstance() {
        return instance;
    }

    private GameNight2019() {
        super("Game Night 2019", "GN2", ExpansionSet.buildDate(2019, 11, 15), SetType.SUPPLEMENTAL);
        this.hasBoosters = false;
        this.hasBasicLands = true;

        cards.add(new SetCardInfo("Accursed Horde", 26, Rarity.UNCOMMON, mage.cards.a.AccursedHorde.class));
        cards.add(new SetCardInfo("Akoum Hellkite", 36, Rarity.RARE, mage.cards.a.AkoumHellkite.class));
        cards.add(new SetCardInfo("Aven Wind Mage", 16, Rarity.COMMON, mage.cards.a.AvenWindMage.class));
        cards.add(new SetCardInfo("Brute Strength", 37, Rarity.COMMON, mage.cards.b.BruteStrength.class));
        cards.add(new SetCardInfo("Calculating Lich", 3, Rarity.MYTHIC, mage.cards.c.CalculatingLich.class));
        cards.add(new SetCardInfo("Carrion Screecher", 27, Rarity.COMMON, mage.cards.c.CarrionScreecher.class));
        cards.add(new SetCardInfo("Claustrophobia", 17, Rarity.COMMON, mage.cards.c.Claustrophobia.class));
        cards.add(new SetCardInfo("Consul's Lieutenant", 6, Rarity.UNCOMMON, mage.cards.c.ConsulsLieutenant.class));
        cards.add(new SetCardInfo("Crested Herdcaller", 46, Rarity.UNCOMMON, mage.cards.c.CrestedHerdcaller.class));
        cards.add(new SetCardInfo("Crow of Dark Tidings", 28, Rarity.COMMON, mage.cards.c.CrowOfDarkTidings.class));
        cards.add(new SetCardInfo("Cryptic Serpent", 18, Rarity.UNCOMMON, mage.cards.c.CrypticSerpent.class));
        cards.add(new SetCardInfo("Decision Paralysis", 19, Rarity.COMMON, mage.cards.d.DecisionParalysis.class));
        cards.add(new SetCardInfo("Destructive Tampering", 38, Rarity.COMMON, mage.cards.d.DestructiveTampering.class));
        cards.add(new SetCardInfo("Dragon Egg", 39, Rarity.UNCOMMON, mage.cards.d.DragonEgg.class));
        cards.add(new SetCardInfo("Dramatic Reversal", 20, Rarity.COMMON, mage.cards.d.DramaticReversal.class));
        cards.add(new SetCardInfo("Earthshaker Giant", 5, Rarity.MYTHIC, mage.cards.e.EarthshakerGiant.class));
        cards.add(new SetCardInfo("Engulf the Shore", 21, Rarity.RARE, mage.cards.e.EngulfTheShore.class));
        cards.add(new SetCardInfo("Fiend Binder", 7, Rarity.COMMON, mage.cards.f.FiendBinder.class));
        cards.add(new SetCardInfo("Fiendish Duo", 4, Rarity.MYTHIC, mage.cards.f.FiendishDuo.class));
        cards.add(new SetCardInfo("Forest", 63, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 64, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Galestrike", 22, Rarity.UNCOMMON, mage.cards.g.Galestrike.class));
        cards.add(new SetCardInfo("Gavony Unhallowed", 29, Rarity.COMMON, mage.cards.g.GavonyUnhallowed.class));
        cards.add(new SetCardInfo("Ghalta, Primal Hunger", 47, Rarity.RARE, mage.cards.g.GhaltaPrimalHunger.class));
        cards.add(new SetCardInfo("Grasp of Darkness", 30, Rarity.UNCOMMON, mage.cards.g.GraspOfDarkness.class));
        cards.add(new SetCardInfo("Grazing Whiptail", 48, Rarity.COMMON, mage.cards.g.GrazingWhiptail.class));
        cards.add(new SetCardInfo("Highcliff Felidar", 1, Rarity.MYTHIC, mage.cards.h.HighcliffFelidar.class));
        cards.add(new SetCardInfo("Howling Golem", 54, Rarity.UNCOMMON, mage.cards.h.HowlingGolem.class));
        cards.add(new SetCardInfo("Island", 57, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 58, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kargan Dragonrider", 40, Rarity.COMMON, mage.cards.k.KarganDragonrider.class));
        cards.add(new SetCardInfo("Kytheon's Irregulars", 8, Rarity.RARE, mage.cards.k.KytheonsIrregulars.class));
        cards.add(new SetCardInfo("Lathliss, Dragon Queen", 41, Rarity.RARE, mage.cards.l.LathlissDragonQueen.class));
        cards.add(new SetCardInfo("Lightning Strike", 42, Rarity.COMMON, mage.cards.l.LightningStrike.class));
        cards.add(new SetCardInfo("Liliana's Mastery", 31, Rarity.RARE, mage.cards.l.LilianasMastery.class));
        cards.add(new SetCardInfo("Lord of the Accursed", 32, Rarity.UNCOMMON, mage.cards.l.LordOfTheAccursed.class));
        cards.add(new SetCardInfo("Mighty Leap", 9, Rarity.COMMON, mage.cards.m.MightyLeap.class));
        cards.add(new SetCardInfo("Mountain", 61, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 62, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Patron of the Valiant", 10, Rarity.UNCOMMON, mage.cards.p.PatronOfTheValiant.class));
        cards.add(new SetCardInfo("Plains", 55, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 56, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ranging Raptors", 49, Rarity.UNCOMMON, mage.cards.r.RangingRaptors.class));
        cards.add(new SetCardInfo("Relief Captain", 11, Rarity.UNCOMMON, mage.cards.r.ReliefCaptain.class));
        cards.add(new SetCardInfo("Ripjaw Raptor", 50, Rarity.RARE, mage.cards.r.RipjawRaptor.class));
        cards.add(new SetCardInfo("Rise from the Grave", 33, Rarity.UNCOMMON, mage.cards.r.RiseFromTheGrave.class));
        cards.add(new SetCardInfo("Rise from the Tides", 23, Rarity.UNCOMMON, mage.cards.r.RiseFromTheTides.class));
        cards.add(new SetCardInfo("River's Rebuke", 24, Rarity.RARE, mage.cards.r.RiversRebuke.class));
        cards.add(new SetCardInfo("Salvager of Secrets", 25, Rarity.COMMON, mage.cards.s.SalvagerOfSecrets.class));
        cards.add(new SetCardInfo("Sparktongue Dragon", 43, Rarity.COMMON, mage.cards.s.SparktongueDragon.class));
        cards.add(new SetCardInfo("Sphinx of Enlightenment", 2, Rarity.MYTHIC, mage.cards.s.SphinxOfEnlightenment.class));
        cards.add(new SetCardInfo("Spidery Grasp", 51, Rarity.COMMON, mage.cards.s.SpideryGrasp.class));
        cards.add(new SetCardInfo("Steppe Glider", 12, Rarity.UNCOMMON, mage.cards.s.SteppeGlider.class));
        cards.add(new SetCardInfo("Swamp", 59, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 60, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Take Vengeance", 13, Rarity.COMMON, mage.cards.t.TakeVengeance.class));
        cards.add(new SetCardInfo("Tattered Mummy", 34, Rarity.COMMON, mage.cards.t.TatteredMummy.class));
        cards.add(new SetCardInfo("Thunderherd Migration", 52, Rarity.UNCOMMON, mage.cards.t.ThunderherdMigration.class));
        cards.add(new SetCardInfo("Thundering Spineback", 53, Rarity.UNCOMMON, mage.cards.t.ThunderingSpineback.class));
        cards.add(new SetCardInfo("Topan Freeblade", 14, Rarity.COMMON, mage.cards.t.TopanFreeblade.class));
        cards.add(new SetCardInfo("Torgaar, Famine Incarnate", 35, Rarity.RARE, mage.cards.t.TorgaarFamineIncarnate.class));
        cards.add(new SetCardInfo("Voldaren Duelist", 44, Rarity.COMMON, mage.cards.v.VoldarenDuelist.class));
        cards.add(new SetCardInfo("Zealot of the God-Pharaoh", 45, Rarity.COMMON, mage.cards.z.ZealotOfTheGodPharaoh.class));
        cards.add(new SetCardInfo("Zetalpa, Primal Dawn", 15, Rarity.RARE, mage.cards.z.ZetalpaPrimalDawn.class));
     }
}
