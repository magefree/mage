package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author PurpleCrowbar
 */
public class The2021HeroesOfTheRealm extends ExpansionSet {

    private static final The2021HeroesOfTheRealm instance = new The2021HeroesOfTheRealm();

    public static The2021HeroesOfTheRealm getInstance() {
        return instance;
    }

    private The2021HeroesOfTheRealm() {
        super("2021 Heroes of the Realm", "PH21", ExpansionSet.buildDate(2022, 8, 1), SetType.JOKE_SET);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Arteeoh, Dread Scavenger", 2, Rarity.MYTHIC, mage.cards.a.ArteeohDreadScavenger.class));
    }
}
