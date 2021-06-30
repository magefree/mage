package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author JayDi85
 *
 * https://scryfall.com/sets/pala
 */
public final class ShardsOfAlaraPromos extends ExpansionSet {

    private static final ShardsOfAlaraPromos instance = new ShardsOfAlaraPromos();

    public static ShardsOfAlaraPromos getInstance() {
        return instance;
    }

    private ShardsOfAlaraPromos() {
        super("Shards of Alara Promos", "PALA", ExpansionSet.buildDate(2008, 10, 3), SetType.PROMOTIONAL);
        this.blockName = "Shards of Alara";
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Ajani Vengeant", "154*", Rarity.MYTHIC, mage.cards.a.AjaniVengeant.class));
    }
}
