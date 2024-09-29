package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pm12
 */
public class Magic2012Promos extends ExpansionSet {

    private static final Magic2012Promos instance = new Magic2012Promos();

    public static Magic2012Promos getInstance() {
        return instance;
    }

    private Magic2012Promos() {
        super("Magic 2012 Promos", "PM12", ExpansionSet.buildDate(2011, 7, 14), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Bloodlord of Vaasgoth", "82*", Rarity.MYTHIC, mage.cards.b.BloodlordOfVaasgoth.class));
        cards.add(new SetCardInfo("Chandra's Phoenix", "126*", Rarity.RARE, mage.cards.c.ChandrasPhoenix.class));
        cards.add(new SetCardInfo("Garruk's Horde", "176*", Rarity.RARE, mage.cards.g.GarruksHorde.class));

    }
}
