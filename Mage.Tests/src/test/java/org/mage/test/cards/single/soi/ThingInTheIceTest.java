package org.mage.test.cards.single.soi;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Defender
 *
 * Thing in the Ice enters the battlefield with four ice counters on it.
 *
 * Whenever you cast an instant or sorcery spell, remove an ice counter from
 * Thing in the Ice. Then if it has no ice counters on it, transform it. When
 * this creature transforms into Awoken Horror, return all non-Horror creatures
 * to their owners' hands.
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class ThingInTheIceTest extends CardTestPlayerBase {

    /**
     * Reported bug: When Thing in the Ice transforms, it bounces Clue tokens.
     *
     */
    @Test
    public void testClueTokens() {
        // Whenever a land enters the battlefield under your control, investigate. <i>(Create a colorless Clue artifact token onto the battlefield with "{2}, Sacrifice this artifact: Draw a card.")</i>
        // Whenever you sacrifice a Clue, put a +1/+1 counter on Tireless Tracker.
        addCard(Zone.BATTLEFIELD, playerA, "Tireless Tracker", 1); // Human, Scout 3/2
        addCard(Zone.HAND, playerA, "Forest", 1);
        // Defender
        // Thing in the Ice enters the battlefield with four ice counters on it.
        // Whenever you cast an instant or sorcery spell, remove an ice counter from Thing in the Ice. Then if it has no ice counters on it, transform it.
        // When this creature transforms into Awoken Horrow, return all non-Horror creatures to their owners' hands.
        addCard(Zone.BATTLEFIELD, playerB, "Thing in the Ice", 1);
        // Target creature gains haste until end of turn.
        // Draw a card.
        addCard(Zone.HAND, playerB, "Expedite", 4);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 4);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Forest"); // creates a clue

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Expedite");
        addTarget(playerB, "Thing in the Ice");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Expedite");
        addTarget(playerB, "Thing in the Ice");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Expedite");
        addTarget(playerB, "Thing in the Ice");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Expedite");
        addTarget(playerB, "Thing in the Ice"); // remove all 4 ice counters to transform it

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Clue Token", 1);
        assertHandCount(playerA, "Tireless Tracker", 1); // returned to hand
        assertPermanentCount(playerA, "Tireless Tracker", 0);
        assertPermanentCount(playerB, "Awoken Horror", 1); // transformed
        assertGraveyardCount(playerB, "Expedite", 4);
    }
}
