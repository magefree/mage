package org.mage.test.cards.single.scg;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Alex-Vasile
 *
 * Decree of Pain
 * {6}{B}{B}
 * Sorcery
 * Destroy all creatures. They can’t be regenerated. Draw a card for each creature destroyed this way.
 * Cycling {3}{B}{B} ({3}{B}{B}, Discard this card: Draw a card.)
 * When you cycle Decree of Pain, all creatures get -2/-2 until end of turn.
 */
public class DecreeOfPainTest  extends CardTestPlayerBase {

    /**
     * Reported bug: https://github.com/magefree/mage/issues/9273
     * You cast Decree of pain while an opponent has Xyris, the Writhing Storm and 9 other creatures on the battlefield.
     * All 10 creatures die, you draw 10 cards, but Xyris triggers and creates 10 snakes.
     * This is wrong since the draws occur after the creatures have been destroyed.
     */
    @Test
    @Ignore
    public void testDrawHappensAfterDestruction(){
        // Destroy all creatures. They can’t be regenerated. Draw a card for each creature destroyed this way.
        addCard(Zone.HAND, playerA, "Decree of Pain");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 8);

        // Whenever an opponent draws a card except the first one they draw in each of their draw steps, create a 1/1 green Snake creature token.
        addCard(Zone.BATTLEFIELD, playerB, "Xyris, the Writhing Storm");
        addCard(Zone.BATTLEFIELD, playerB, "Aven Envoy", 9);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Decree of Pain");

        execute();

        assertGraveyardCount(playerB, "Xyris, the Writhing Storm", 1);
        assertGraveyardCount(playerB, "Aven Envoy", 9);
        assertPermanentCount(playerB, 0);
        assertHandCount(playerB, 0);
    }
}
