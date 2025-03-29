package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author tiera3
 */
public final class UnknownEvent extends ExpansionSet {

    private static final UnknownEvent instance = new UnknownEvent();

    public static UnknownEvent getInstance() {
        return instance;
    }

    private UnknownEvent() {
        super("Unknown Event", "UNK", ExpansionSet.buildDate(2023, 2, 15), SetType.JOKE_SET);
        this.hasBasicLands = false;
        this.hasBoosters = false;

        cards.add(new SetCardInfo("More of That Strange Oil...", "CU13", Rarity.COMMON, mage.cards.m.MoreOfThatStrangeOil.class));
    }
}
