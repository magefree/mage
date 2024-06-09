package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pl23
 */
public class YearOfTheRabbit2023 extends ExpansionSet {

    private static final YearOfTheRabbit2023 instance = new YearOfTheRabbit2023();

    public static YearOfTheRabbit2023 getInstance() {
        return instance;
    }

    private YearOfTheRabbit2023() {
        super("Year of the Rabbit 2023", "PL23", ExpansionSet.buildDate(2023, 2, 10), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Arcbound Ravager", 6, Rarity.RARE, mage.cards.a.ArcboundRavager.class));
        cards.add(new SetCardInfo("Ethereal Armor", 5, Rarity.RARE, mage.cards.e.EtherealArmor.class));
        cards.add(new SetCardInfo("Kwain, Itinerant Meddler", 3, Rarity.RARE, mage.cards.k.KwainItinerantMeddler.class));
        cards.add(new SetCardInfo("Rabbit Battery", 1, Rarity.RARE, mage.cards.r.RabbitBattery.class));
        cards.add(new SetCardInfo("Swiftfoot Boots", 4, Rarity.RARE, mage.cards.s.SwiftfootBoots.class));
    }
}
