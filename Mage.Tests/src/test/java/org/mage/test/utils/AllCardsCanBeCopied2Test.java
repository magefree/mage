package org.mage.test.utils;

import mage.cards.repository.CardRepository;
import mage.cards.repository.CardScanner;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.GameStates;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.mage.test.serverside.base.CardTestPlayerBase;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Testing of that all the cards in CardRepository allow for reset.
 *
 * @author Suscr
 */
@RunWith(Parameterized.class)
public class AllCardsCanBeCopied2Test extends CardTestPlayerBase {

    private static Set<String> allCards;

    @Parameters(name = "{index}: resetTest({0})")
    public static Iterable<Object[]> data() {
        CardScanner.scan();
        allCards = CardRepository.instance.getNames();
        List<Object[]> names = allCards.stream().map(n -> new Object[]{n}).collect(Collectors.toList());
        return names;
    }

    @Parameter
    public String name;

    /**
     * Test reset when there is the 'name' card involved.
     */
    @Test
    public void test_AllCardsCanBeCopied() {
        GameStates states = currentGame.getGameStates();

        addCard(Zone.HAND, playerA, name);
        addCard(Zone.LIBRARY, playerA, name);

        setStopAt(1, PhaseStep.END_TURN);

        try {
            execute();
            currentGame.loadGameStates(states);
        } catch (StackOverflowError err) {
            System.err.println(name + " throws a StackOverflow on loadstate.");
            Assert.fail(name + " throws StackOverflow on loadstate");
        } catch (Exception err) {
            System.err.println(name + " throws an error " + err.getMessage() + " on loadstate.");
            Assert.fail(name + " throws an error " + err.getMessage() + " on loadstate");
        }

    }
}
