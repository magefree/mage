package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/phtr
 * @author TheElk801
 */
public final class HeroesOfTheRealm2016 extends ExpansionSet {

    private static final HeroesOfTheRealm2016 instance = new HeroesOfTheRealm2016();

    public static HeroesOfTheRealm2016 getInstance() {
        return instance;
    }

    private HeroesOfTheRealm2016() {
        super("Heroes of the Realm 2016", "PHTR", ExpansionSet.buildDate(2017, 9, 20), SetType.JOKESET);
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Chandra, Gremlin Wrangler", 1, Rarity.MYTHIC, mage.cards.c.ChandraGremlinWrangler.class));
        // Cards not implemented
        //cards.add(new SetCardInfo("Dungeon Master", 2, Rarity.MYTHIC, mage.cards.d.DungeonMaster.class));
        //cards.add(new SetCardInfo("Nira, Hellkite Duelist", 3, Rarity.MYTHIC, mage.cards.n.NiraHellkiteDuelist.class));
    }
}
