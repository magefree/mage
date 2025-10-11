package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/lmar
 */
public class MarvelLegendsSeriesInserts extends ExpansionSet {

    private static final MarvelLegendsSeriesInserts instance = new MarvelLegendsSeriesInserts();

    public static MarvelLegendsSeriesInserts getInstance() {
        return instance;
    }

    private MarvelLegendsSeriesInserts() {
        super("Marvel Legends Series Inserts", "LMAR", ExpansionSet.buildDate(2025, 9, 30), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Anti-Venom, Horrifying Healer", 1, Rarity.MYTHIC, mage.cards.a.AntiVenomHorrifyingHealer.class));
        cards.add(new SetCardInfo("Huntmaster of the Fells", 3, Rarity.RARE, mage.cards.h.HuntmasterOfTheFells.class));
        cards.add(new SetCardInfo("Iron Spider, Stark Upgrade", 4, Rarity.RARE, mage.cards.i.IronSpiderStarkUpgrade.class));
        cards.add(new SetCardInfo("Ravager of the Fells", 3, Rarity.RARE, mage.cards.r.RavagerOfTheFells.class));
        cards.add(new SetCardInfo("Spectacular Spider-Man", 2, Rarity.RARE, mage.cards.s.SpectacularSpiderMan.class));
    }
}
