
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pspl
 * @author ReSech
 */
public final class SpotlightSeries extends ExpansionSet {

    private static final SpotlightSeries instance = new SpotlightSeries();

    public static SpotlightSeries getInstance() {
        return instance;
    }

    private SpotlightSeries() {
        super("Spotlight Series", "PSPL", ExpansionSet.buildDate(2025, 1, 3), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Cloud, Midgar Mercenary", 5, Rarity.MYTHIC, mage.cards.c.CloudMidgarMercenary.class));
        cards.add(new SetCardInfo("Get Lost", 6, Rarity.RARE, mage.cards.g.GetLost.class));
        cards.add(new SetCardInfo("Kaldra Compleat", 2, Rarity.MYTHIC, mage.cards.k.KaldraCompleat.class));
        cards.add(new SetCardInfo("Sword of Forge and Frontier", 3, Rarity.MYTHIC, mage.cards.s.SwordOfForgeAndFrontier.class));
        cards.add(new SetCardInfo("Terror of the Peaks", 1, Rarity.RARE, mage.cards.t.TerrorOfThePeaks.class));
    }

}
