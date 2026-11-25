package org.mage.test.cards.single.blb;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Persistent Marshstalker {1}{B}
 * Creature — Rat Berserker
 * Threshold — Whenever you attack with one or more Rats, if seven or more cards
 * are in your graveyard, you may pay {2}{B}. If you do, return Persistent
 * Marshstalker from your graveyard to the battlefield tapped and attacking.
 * <p>
 * The "if seven or more cards are in your graveyard" clause immediately follows
 * the trigger condition, so CR 603.4 (the intervening-"if" rule) checks it both
 * when the trigger event occurs and as the ability resolves. With fewer than
 * seven cards in the graveyard the ability must not be put on the stack at all.
 */
public class PersistentMarshstalkerTest extends CardTestPlayerBase {

    @Test
    public void testBelowThresholdDoesNotTrigger() {
        // Two cards in the graveyard: below the seven-card threshold.
        addCard(Zone.GRAVEYARD, playerA, "Persistent Marshstalker");
        addCard(Zone.GRAVEYARD, playerA, "Early Winter");
        // Thought Shucker is a Rat Rogue, so the trigger event happens.
        addCard(Zone.BATTLEFIELD, playerA, "Thought Shucker");

        attack(1, playerA, "Thought Shucker");
        checkStackSize("below threshold, no trigger", 1, PhaseStep.DECLARE_ATTACKERS, playerA, 0);

        setStopAt(1, PhaseStep.DECLARE_ATTACKERS);
        execute();

        assertGraveyardCount(playerA, "Persistent Marshstalker", 1);
    }
}
