package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/plgm
 */
public class DCILegendMembership extends ExpansionSet {

    private static final DCILegendMembership instance = new DCILegendMembership();

    public static DCILegendMembership getInstance() {
        return instance;
    }

    private DCILegendMembership() {
        super("DCI Legend Membership", "PLGM", ExpansionSet.buildDate(1995, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Counterspell", 1, Rarity.RARE, mage.cards.c.Counterspell.class));
        cards.add(new SetCardInfo("Incinerate", 2, Rarity.RARE, mage.cards.i.Incinerate.class));
    }
}
