package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/ph17
 * @author PurpleCrowbar
 */
public class The2017HeroesOfTheRealm extends ExpansionSet {

    private static final The2017HeroesOfTheRealm instance = new The2017HeroesOfTheRealm();

    public static The2017HeroesOfTheRealm getInstance() {
        return instance;
    }

    private The2017HeroesOfTheRealm() {
        super("2017 Heroes of the Realm", "PH17", ExpansionSet.buildDate(2018, 8, 1), SetType.JOKE_SET);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Inzerva, Master of Insights", 2, Rarity.MYTHIC, mage.cards.i.InzervaMasterOfInsights.class));
    }
}
