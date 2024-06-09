package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/ph17
 * @author PurpleCrowbar
 */
public class HeroesOfTheRealm2017 extends ExpansionSet {

    private static final HeroesOfTheRealm2017 instance = new HeroesOfTheRealm2017();

    public static HeroesOfTheRealm2017 getInstance() {
        return instance;
    }

    private HeroesOfTheRealm2017() {
        super("Heroes of the Realm 2017", "PH17", ExpansionSet.buildDate(2018, 8, 1), SetType.JOKE_SET);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Inzerva, Master of Insights", 2, Rarity.MYTHIC, mage.cards.i.InzervaMasterOfInsights.class));
    }
}
