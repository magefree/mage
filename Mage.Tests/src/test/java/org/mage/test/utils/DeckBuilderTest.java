package org.mage.test.utils;

import mage.MageInt;
import mage.cards.CardSetInfo;
import mage.cards.basiclands.Island;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Rarity;
import mage.interfaces.rate.RateCallback;
import mage.utils.DeckBuilder;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


/**
 * @author dustinconrad
 */
public class DeckBuilderTest {

    @Test
    public void testAllArtifacts() {
        final List<Card> spellCardPool = new ArrayList<>();
        final UUID owner = UUID.randomUUID();
        final List<ColoredManaSymbol> allowedColors = new ArrayList<>(Arrays.asList(ColoredManaSymbol.U));
        final List<String> setsToUse = new ArrayList<>();
        final List<Card> landCardPool = null;
        final RateCallback rateCallback = new RateCallback() {
            @Override
            public int rateCard(Card card) {
                return 6;
            }

            @Override
            public Card getBestBasicLand(ColoredManaSymbol color, List<String> setsToUse) {
                Assert.assertNotNull(color);
                return new Island(owner, new CardSetInfo("Island", "MRD", "999", Rarity.LAND));
            }
        };

        for(int i = 0; i < 24; i++) {
            Card c = new RandomArtifactCreature(owner, i, "Random Artifact " + i);
            spellCardPool.add(c);
        }

        DeckBuilder.buildDeck(spellCardPool, allowedColors, setsToUse, landCardPool, 40, rateCallback);
    }

    private static class RandomArtifactCreature extends CardImpl {

        public RandomArtifactCreature(UUID ownerId, int cardNumber, String name) {
            super(ownerId, new CardSetInfo(name, "MRD", String.valueOf(cardNumber), Rarity.COMMON), new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}");
            this.power = new MageInt(1);
            this.toughness = new MageInt(1);
        }

        public RandomArtifactCreature(final RandomArtifactCreature card) {
            super(card);
        }

        @Override
        public RandomArtifactCreature copy() {
            return new RandomArtifactCreature(this);
        }
    }

}
