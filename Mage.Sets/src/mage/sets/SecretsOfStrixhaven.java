package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class SecretsOfStrixhaven extends ExpansionSet {

    private static final SecretsOfStrixhaven instance = new SecretsOfStrixhaven();

    public static SecretsOfStrixhaven getInstance() {
        return instance;
    }

    private SecretsOfStrixhaven() {
        super("Secrets of Strixhaven", "SOS", ExpansionSet.buildDate(2026, 4, 24), SetType.EXPANSION);
        this.blockName = "Secrets of Strixhaven"; // for sorting in GUI
        this.hasBasicLands = false; // temporary

        cards.add(new SetCardInfo("Lorehold, the Historian", 201, Rarity.MYTHIC, mage.cards.l.LoreholdTheHistorian.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lorehold, the Historian", 284, Rarity.MYTHIC, mage.cards.l.LoreholdTheHistorian.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mathemagics", 320, Rarity.MYTHIC, mage.cards.m.Mathemagics.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mathemagics", 58, Rarity.MYTHIC, mage.cards.m.Mathemagics.class, NON_FULL_USE_VARIOUS));
    }
}
