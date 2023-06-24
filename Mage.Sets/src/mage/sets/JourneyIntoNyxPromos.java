package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pjou
 */
public class JourneyIntoNyxPromos extends ExpansionSet {

    private static final JourneyIntoNyxPromos instance = new JourneyIntoNyxPromos();

    public static JourneyIntoNyxPromos getInstance() {
        return instance;
    }

    private JourneyIntoNyxPromos() {
        super("Journey into Nyx Promos", "PJOU", ExpansionSet.buildDate(2014, 4, 26), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Dawnbringer Charioteers", "6*", Rarity.RARE, mage.cards.d.DawnbringerCharioteers.class));
        cards.add(new SetCardInfo("Dictate of Kruphix", 37, Rarity.RARE, mage.cards.d.DictateOfKruphix.class));
        cards.add(new SetCardInfo("Dictate of the Twin Gods", "93*", Rarity.RARE, mage.cards.d.DictateOfTheTwinGods.class));
        cards.add(new SetCardInfo("Doomwake Giant", "66*", Rarity.RARE, mage.cards.d.DoomwakeGiant.class));
        cards.add(new SetCardInfo("Eidolon of Blossoms", "122*", Rarity.RARE, mage.cards.e.EidolonOfBlossoms.class));
        cards.add(new SetCardInfo("Heroes' Bane", "126*", Rarity.RARE, mage.cards.h.HeroesBane.class));
        cards.add(new SetCardInfo("Scourge of Fleets", "51*", Rarity.RARE, mage.cards.s.ScourgeOfFleets.class));
        cards.add(new SetCardInfo("Spawn of Thraxes", "112*", Rarity.RARE, mage.cards.s.SpawnOfThraxes.class));
        cards.add(new SetCardInfo("Squelching Leeches", 84, Rarity.UNCOMMON, mage.cards.s.SquelchingLeeches.class));
    }
}
