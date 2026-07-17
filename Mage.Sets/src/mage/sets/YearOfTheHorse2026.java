package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pl26
 */
public class YearOfTheHorse2026 extends ExpansionSet {

    private static final YearOfTheHorse2026 instance = new YearOfTheHorse2026();

    public static YearOfTheHorse2026 getInstance() {
        return instance;
    }

    private YearOfTheHorse2026() {
        super("Year of the Horse 2026", "PL26", ExpansionSet.buildDate(2026, 2, 17), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = true;

        cards.add(new SetCardInfo("Calamity, Galloping Inferno", 4, Rarity.RARE, mage.cards.c.CalamityGallopingInferno.class));
        cards.add(new SetCardInfo("Caustic Bronco", 1, Rarity.RARE, mage.cards.c.CausticBronco.class));
        cards.add(new SetCardInfo("Emiel the Blessed", 3, Rarity.MYTHIC, mage.cards.e.EmielTheBlessed.class));
        cards.add(new SetCardInfo("Plains", 5, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
    }
}
