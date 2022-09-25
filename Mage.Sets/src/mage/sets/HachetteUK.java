package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/phuk
 */
public class HachetteUK extends ExpansionSet {

    private static final HachetteUK instance = new HachetteUK();

    public static HachetteUK getInstance() {
        return instance;
    }

    private HachetteUK() {
        super("Hachette UK", "PHUK", ExpansionSet.buildDate(2006, 1, 1), SetType.SUPPLEMENTAL);
        this.hasBoosters = false;
        this.hasBasicLands = true;

        cards.add(new SetCardInfo("Betrayal of Flesh", 52, Rarity.UNCOMMON, mage.cards.b.BetrayalOfFlesh.class));
        cards.add(new SetCardInfo("Carrion Rats", 40, Rarity.COMMON, mage.cards.c.CarrionRats.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Carrion Rats", 41, Rarity.COMMON, mage.cards.c.CarrionRats.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Carrion Rats", 53, Rarity.COMMON, mage.cards.c.CarrionRats.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Chittering Rats", 5, Rarity.COMMON, mage.cards.c.ChitteringRats.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Chittering Rats", 19, Rarity.COMMON, mage.cards.c.ChitteringRats.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Chittering Rats", 54, Rarity.COMMON, mage.cards.c.ChitteringRats.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Crippling Fatigue", 6, Rarity.COMMON, mage.cards.c.CripplingFatigue.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Crippling Fatigue", 30, Rarity.COMMON, mage.cards.c.CripplingFatigue.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Crypt Rats", 28, Rarity.UNCOMMON, mage.cards.c.CryptRats.class));
        cards.add(new SetCardInfo("Diabolic Tutor", 51, Rarity.UNCOMMON, mage.cards.d.DiabolicTutor.class));
        cards.add(new SetCardInfo("Dirge of Dread", 18, Rarity.COMMON, mage.cards.d.DirgeOfDread.class));
        cards.add(new SetCardInfo("Dirty Wererat", 7, Rarity.COMMON, mage.cards.d.DirtyWererat.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dirty Wererat", 31, Rarity.COMMON, mage.cards.d.DirtyWererat.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gravestorm", 37, Rarity.RARE, mage.cards.g.Gravestorm.class));
        cards.add(new SetCardInfo("Infernal Contract", 13, Rarity.RARE, mage.cards.i.InfernalContract.class));
        cards.add(new SetCardInfo("Larceny", 49, Rarity.RARE, mage.cards.l.Larceny.class));
        cards.add(new SetCardInfo("Marrow-Gnawer", 1, Rarity.RARE, mage.cards.m.MarrowGnawer.class));
        cards.add(new SetCardInfo("Nezumi Bone-Reader", 50, Rarity.UNCOMMON, mage.cards.n.NezumiBoneReader.class));
        cards.add(new SetCardInfo("Nezumi Cutthroat", 17, Rarity.COMMON, mage.cards.n.NezumiCutthroat.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nezumi Cutthroat", 29, Rarity.COMMON, mage.cards.n.NezumiCutthroat.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nezumi Graverobber", 38, Rarity.UNCOMMON, mage.cards.n.NezumiGraverobber.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nezumi Graverobber", 39, Rarity.UNCOMMON, mage.cards.n.NezumiGraverobber.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nocturnal Raid", 3, Rarity.UNCOMMON, mage.cards.n.NocturnalRaid.class));
        cards.add(new SetCardInfo("Patron of the Nezumi", 25, Rarity.RARE, mage.cards.p.PatronOfTheNezumi.class));
        cards.add(new SetCardInfo("Rats' Feast", 44, Rarity.COMMON, mage.cards.r.RatsFeast.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rats' Feast", 55, Rarity.COMMON, mage.cards.r.RatsFeast.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sever Soul", 15, Rarity.UNCOMMON, mage.cards.s.SeverSoul.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sever Soul", 16, Rarity.UNCOMMON, mage.cards.s.SeverSoul.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Skullsnatcher", 4, Rarity.COMMON, mage.cards.s.Skullsnatcher.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Skullsnatcher", 42, Rarity.COMMON, mage.cards.s.Skullsnatcher.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Skullsnatcher", 43, Rarity.COMMON, mage.cards.s.Skullsnatcher.class, NON_FULL_USE_VARIOUS));
        // Card not implemented
        // cards.add(new SetCardInfo("Suppress", 26, Rarity.UNCOMMON, mage.cards.s.Suppress.class));
        cards.add(new SetCardInfo("Swamp", 8, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 9, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 10, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 11, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 12, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 20, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 21, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 22, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 23, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 24, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 32, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 33, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 34, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 35, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 36, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 45, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 46, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 47, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 48, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 56, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 57, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 58, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 59, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 60, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swarm of Rats", 2, Rarity.UNCOMMON, mage.cards.s.SwarmOfRats.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swarm of Rats", 14, Rarity.UNCOMMON, mage.cards.s.SwarmOfRats.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swarm of Rats", 27, Rarity.UNCOMMON, mage.cards.s.SwarmOfRats.class, NON_FULL_USE_VARIOUS));
     }
}
