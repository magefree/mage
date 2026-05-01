package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author muz
 */
public final class TheHobbit extends ExpansionSet {

    private static final TheHobbit instance = new TheHobbit();

    public static TheHobbit getInstance() {
        return instance;
    }

    private TheHobbit() {
        super("The Hobbit", "HOB", ExpansionSet.buildDate(2026, 8, 14), SetType.CUSTOM_SET);
        // super("The Hobbit", "HOB", ExpansionSet.buildDate(2026, 8, 14), SetType.EXPANSION); TODO: Once mtgjson/Scryfall have the set, change to EXPANSION
        this.blockName = "The Hobbit"; // for sorting in GUI
        this.hasBasicLands = false; // TODO: Enable once basic collector numbers are known

        // this.enablePlayBooster(305); TODO: Enable later

        cards.add(new SetCardInfo("An Unexpected Party", 29, Rarity.RARE, mage.cards.a.AnUnexpectedParty.class));
        cards.add(new SetCardInfo("My Precious", 176, Rarity.RARE, mage.cards.m.MyPrecious.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("My Precious", 235, Rarity.RARE, mage.cards.m.MyPrecious.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Smaug the Magnificent", 110, Rarity.MYTHIC, mage.cards.s.SmaugTheMagnificent.class));
        cards.add(new SetCardInfo("Tom, Bert, and William", 169, Rarity.RARE, mage.cards.t.TomBertAndWilliam.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tom, Bert, and William", 312, Rarity.RARE, mage.cards.t.TomBertAndWilliam.class, NON_FULL_USE_VARIOUS));
    }
}
