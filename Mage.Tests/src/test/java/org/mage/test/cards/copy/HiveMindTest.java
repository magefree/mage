
package org.mage.test.cards.copy;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class HiveMindTest extends CardTestPlayerBase {

    /**
     * Check that opponent gets a copy of Lightning Bolt
     */
    @Test
    public void testTransform() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        // Whenever a player casts an instant or sorcery spell, each other player copies that spell.
        // Each of those players may choose new targets for their copy.
        addCard(Zone.BATTLEFIELD, playerA, "Hive Mind", 1);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);
        setChoice(playerB, true);
        addTarget(playerB, playerA);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, 17);
        assertLife(playerA, 17);
    }

    /**
     * The Amulet Bloom deck in Modern has a few wincons, one of them being that
     * the Bloom player resolves a Hive Mind, then casts a Pact. The Hive Mind
     * copies it so the opponent also casts a Pact. If the opposing player has a
     * Chalice set on ZERO, it will counter both copies which it should NOT DO
     * because Hive Mind puts a copy onto the stack, not 'cast'. So while the
     * Bloom player's copy is countered, the opponent will still cast and have
     * to pay during their upkeep.
     */
    @Test
    public void testChaliceOfTtheVoid() {
        // Whenever a player casts an instant or sorcery spell, each other player copies that spell.
        // Each of those players may choose new targets for their copy.
        addCard(Zone.BATTLEFIELD, playerA, "Hive Mind", 1);
        // Create a 4/4 red Giant creature token onto the battlefield.
        // At the beginning of your next upkeep, pay {4}{R}. If you don't, you lose the game.
        addCard(Zone.HAND, playerA, "Pact of the Titan", 1);
        // Chalice of the Void enters the battlefield with X charge counters on it.
        // Whenever a player casts a spell with converted mana cost equal to the number of charge counters on Chalice of the Void, counter that spell.
        addCard(Zone.BATTLEFIELD, playerB, "Chalice of the Void", 1);

        setChoice(playerB, true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pact of the Titan");

        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Pact of the Titan", 1);
        assertPermanentCount(playerA, "Giant Token", 0); // was countered by Chalice
        assertPermanentCount(playerB, "Giant Token", 1); // was not countered by Chalice because it was not cast
        assertWonTheGame(playerA);
        assertLostTheGame(playerB);
        assertLife(playerB, 20);
        assertLife(playerA, 20);
    }
}
