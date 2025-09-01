package org.mage.test.cards.single.one;

import mage.cards.Card;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommanderDuelBase;

import java.util.List;

import static org.junit.Assert.assertTrue;


public class EncroachingMycosynthTest extends CardTestCommanderDuelBase {

    /*
    Encroaching Mycosynth
    {3}{U}
    Artifact

    Nonland permanents you control are artifacts in addition to their other types.
    The same is true for permanent spells you control and nonland permanent cards you own that aren’t on the battlefield.
     */
    private static final String encroachingMycosynth = "Encroaching Mycosynth";
    private static final String balduvianBears = "Balduvian Bears";
    @Test
    public void testEncroachingMycosynth() {
        setStrictChooseMode(true);

        addCard(Zone.GRAVEYARD, playerA, balduvianBears);
        addCard(Zone.HAND, playerA, balduvianBears, 2);
        addCard(Zone.BATTLEFIELD, playerA, balduvianBears);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, encroachingMycosynth);
        addCard(Zone.EXILED, playerA, balduvianBears);
        addCard(Zone.LIBRARY, playerA, balduvianBears);
        addCard(Zone.COMMAND, playerA, balduvianBears);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, balduvianBears);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertType(balduvianBears, CardType.ARTIFACT, true);
        List<Card> cards = getHandCards(playerA);
        cards.addAll(getLibraryCards(playerA));
        cards.addAll(getCommandCards(playerA));
        cards.addAll(getExiledCards(playerA));
        cards.addAll(getLibraryCards(playerA));
        for (Card card : cards) {
            if (!card.isLand(currentGame)) {
                assertTrue(card.getCardType(currentGame).contains(CardType.ARTIFACT));
            }
        }
    }

}
