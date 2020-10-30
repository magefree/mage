package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/slu
 */
public class SecretLairUltimateEdition extends ExpansionSet {

    private static final SecretLairUltimateEdition instance = new SecretLairUltimateEdition();

    public static SecretLairUltimateEdition getInstance() {
        return instance;
    }

    private SecretLairUltimateEdition() {
        super("Secret Lair: Ultimate Edition", "SLU", ExpansionSet.buildDate(2020, 5, 29), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Arid Mesa", 4, Rarity.RARE, mage.cards.a.AridMesa.class));
        cards.add(new SetCardInfo("Marsh Flats", 1, Rarity.RARE, mage.cards.m.MarshFlats.class));
        cards.add(new SetCardInfo("Misty Rainforest", 5, Rarity.RARE, mage.cards.m.MistyRainforest.class));
        cards.add(new SetCardInfo("Scalding Tarn", 2, Rarity.RARE, mage.cards.s.ScaldingTarn.class));
        cards.add(new SetCardInfo("Verdant Catacombs", 3, Rarity.RARE, mage.cards.v.VerdantCatacombs.class));
     }
}
