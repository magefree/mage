package org.mage.test.mulligan;

import mage.cards.Card;
import mage.cards.CardsImpl;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.mulligan.MulliganDefaultHandSorter;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author JayDi85
 */
public class MulliganCardSorterTest extends CardTestPlayerBase {

    private void assertHandSort(List<String> goodList) {
        MulliganDefaultHandSorter sorter = new MulliganDefaultHandSorter();

        // prepare good list
        List<Card> good = goodList
                .stream()
                .map(name -> currentGame.getCards()
                        .stream()
                        .filter(c -> c.getName().equals(name))
                        .findFirst()
                        .orElseThrow(() -> new IllegalStateException("Can't find testing card " + name))
                )
                .collect(Collectors.toList());

        // multiple tests with diff starting order
        IntStream.range(0, 5).forEach(x -> {
            List<Card> bad = new ArrayList<>(good);
            Collections.shuffle(bad);

            CardsImpl sorted = new CardsImpl(bad);
            sorted.sortCards(currentGame, sorter);
            List<Card> badSorted = new ArrayList<>(sorted.getCards(currentGame));
            assertList(good, badSorted);
        });
    }

    private void assertList(List<Card> need, List<Card> sorted) {
        String needStr = need.stream().map(Card::getName).collect(Collectors.joining("; "));
        String sortedStr = sorted.stream().map(Card::getName).collect(Collectors.joining("; "));
        Assert.assertEquals("bad sorting", needStr, sortedStr);
    }

    @Test
    public void test_HandSorting() {
        // init cards for sort testing
        // lands
        addCard(Zone.HAND, playerA, "Forest", 1);
        addCard(Zone.HAND, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerA, "Island", 1);
        // other
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1); // mana 1, instant
        addCard(Zone.HAND, playerA, "From Beyond", 1); // mana 4, enchantment
        addCard(Zone.HAND, playerA, "Samite Blessing", 1); // mana 1, enchantment
        addCard(Zone.HAND, playerA, "Druid's Call", 1); // mana 2, enchantment
        // creatures
        addCard(Zone.HAND, playerA, "Grizzly Bears", 1); // mana 2
        addCard(Zone.HAND, playerA, "Aspiring Champion", 1); // mana 4
        addCard(Zone.HAND, playerA, "Yellow Scarves Troops", 1); // mana 2
        addCard(Zone.HAND, playerA, "Marchesa's Infiltrator", 1); // mana 3

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // lands by name
        assertHandSort(Arrays.asList(
                "Forest",
                "Island",
                "Mountain"
        ));

        // creatures by mana
        assertHandSort(Arrays.asList(
                "Aspiring Champion", // 4
                "Marchesa's Infiltrator", // 3
                "Grizzly Bears", // 2
                "Yellow Scarves Troops" // 2
        ));

        // other by mana
        assertHandSort(Arrays.asList(
                "From Beyond", // 4
                "Druid's Call", // 2
                "Lightning Bolt", // 1
                "Samite Blessing" // 1
        ));

        // lands > others > creatures
        assertHandSort(Arrays.asList(
                "Forest", // land
                "Island", // land
                "Druid's Call", // other, 2
                "Lightning Bolt", // other, 1
                "Samite Blessing", // other, 1
                "Grizzly Bears", // creature, 2
                "Yellow Scarves Troops" // creature, 2
        ));

    }
}