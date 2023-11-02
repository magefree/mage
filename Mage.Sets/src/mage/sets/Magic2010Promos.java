package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pm10
 */
public class Magic2010Promos extends ExpansionSet {

    private static final Magic2010Promos instance = new Magic2010Promos();

    public static Magic2010Promos getInstance() {
        return instance;
    }

    private Magic2010Promos() {
        super("Magic 2010 Promos", "PM10", ExpansionSet.buildDate(2009, 7, 16), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Ant Queen", "166*", Rarity.RARE, mage.cards.a.AntQueen.class));
        cards.add(new SetCardInfo("Honor of the Pure", "16*", Rarity.RARE, mage.cards.h.HonorOfThePure.class));
        cards.add(new SetCardInfo("Vampire Nocturnus", "118*", Rarity.MYTHIC, mage.cards.v.VampireNocturnus.class));
    }
}
