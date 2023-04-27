
package org.mage.test.cards.triggers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class ShuffleTriggeredTest extends CardTestPlayerBase {

    @Test
    public void testWidespreadPanicDoesTrigger() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        // Whenever a spell or ability causes its controller to shuffle their library, that player puts a card from their hand on top of their library.
        addCard(Zone.HAND, playerA, "Widespread Panic", 1); // Enchantment {2}{R}
        // Search your library for a basic land card and put that card onto the battlefield. Then shuffle your library.
        addCard(Zone.HAND, playerA, "Untamed Wilds"); // Sorcery - {2}{G}

        addCard(Zone.HAND, playerA, "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Widespread Panic", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Untamed Wilds");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Widespread Panic", 1);
        assertGraveyardCount(playerA, "Untamed Wilds", 1);

        assertHandCount(playerA, "Silvercoat Lion", 0); // Because Untamed Wilds does trigger Widespread Panic the card goes to library
    }

    /**
     * Wenn ich mit Knowledge Exploitation einen Gegner seine Bibliothek mischen
     * lasse, dann triggert Widespread Panic für ihn (sollte garnicht triggern).
     * Bei Bribery ist es genauso.
     * 
     * If I have an opponent shuffle their library using Knowledge Exploitation, Widespread Panic triggers for them (shoudn't trigger at all). Same thing with Bribery.
     */
    @Test
    public void testWidespreadPanicDoesNotTriggerIfOpponentShufflesPlayersLibrary() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 9);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        // Whenever a spell or ability causes its controller to shuffle their library, that player puts a card from their hand on top of their library.
        addCard(Zone.HAND, playerA, "Widespread Panic", 1); // Enchantment {2}{R}
        // Prowl {3}{U}
        // Search target opponent's library for an instant or sorcery card. You may cast that card without paying its mana cost. Then that player shuffles their library.
        addCard(Zone.HAND, playerA, "Knowledge Exploitation"); // Sorcery - {5}{U}{U}

        addCard(Zone.HAND, playerB, "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Widespread Panic", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Knowledge Exploitation", playerB);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Widespread Panic", 1);
        assertGraveyardCount(playerA, "Knowledge Exploitation", 1);

        assertHandCount(playerB, "Silvercoat Lion", 1); // Because Knowledge Exploitation does not trigger the card stays in hand
    }

}
