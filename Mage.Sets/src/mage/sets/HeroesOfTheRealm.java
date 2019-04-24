package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class HeroesOfTheRealm extends ExpansionSet {

    private static final HeroesOfTheRealm instance = new HeroesOfTheRealm();

    public static HeroesOfTheRealm getInstance() {
        return instance;
    }

    private HeroesOfTheRealm() {
        super("Heroes of the Realm", "HTR", ExpansionSet.buildDate(2017, 9, 20), SetType.JOKESET);
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Chandra, Gremlin Wrangler", 1, Rarity.MYTHIC, mage.cards.c.ChandraGremlinWrangler.class));
    }
}
