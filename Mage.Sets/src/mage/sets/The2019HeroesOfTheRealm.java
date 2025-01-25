package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/ph19
 * @author PurpleCrowbar
 */
public class The2019HeroesOfTheRealm extends ExpansionSet {

    private static final The2019HeroesOfTheRealm instance = new The2019HeroesOfTheRealm();

    public static The2019HeroesOfTheRealm getInstance() {
        return instance;
    }

    private The2019HeroesOfTheRealm() {
        super("2019 Heroes of the Realm", "PH19", ExpansionSet.buildDate(2020, 8, 1), SetType.JOKE_SET);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("The Cinematic Phoenix", 6, Rarity.MYTHIC, mage.cards.t.TheCinematicPhoenix.class));
    }
}
