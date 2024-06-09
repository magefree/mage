package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/ph19
 * @author PurpleCrowbar
 */
public class HeroesOfTheRealm2019 extends ExpansionSet {

    private static final HeroesOfTheRealm2019 instance = new HeroesOfTheRealm2019();

    public static HeroesOfTheRealm2019 getInstance() {
        return instance;
    }

    private HeroesOfTheRealm2019() {
        super("Heroes of the Realm 2019", "PH19", ExpansionSet.buildDate(2020, 8, 1), SetType.JOKE_SET);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("The Cinematic Phoenix", 6, Rarity.MYTHIC, mage.cards.t.TheCinematicPhoenix.class));
    }
}
