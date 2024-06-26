package org.mage.test.cards.single.war;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author xenohedron
 */
public class ObNixilisTheHateTwistedTest extends CardTestPlayerBase {
    
    /* Whenever an opponent draws a card, Ob Nixilis, the Hate-Twisted deals 1 damage to that player.
    −2: Destroy target creature. Its controller draws two cards.
    */
    private static final String nixilis = "Ob Nixilis, the Hate-Twisted";
    private static final String ghoul = "Warpath Ghoul";
    
    @Test
    public void testNixilis() {
        addCard(Zone.BATTLEFIELD, playerA, nixilis);
        addCard(Zone.BATTLEFIELD, playerB, ghoul);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "-2: Destroy", ghoul);
        setChoice(playerA, "Whenever"); // order triggers

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        
        assertGraveyardCount(playerB, ghoul, 1);
        assertHandCount(playerA, 0);
        assertHandCount(playerB, 2);
        assertCounterCount(playerA, nixilis, CounterType.LOYALTY, 3);
        assertLife(playerA, 20);
        assertLife(playerB, 18);
    }

    @Test
    public void testNixilisControlChanged() {
        String threaten = "Act of Treason";

        addCard(Zone.BATTLEFIELD, playerA, nixilis);
        addCard(Zone.BATTLEFIELD, playerB, ghoul);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.HAND, playerA, threaten);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, threaten, ghoul);

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "-2: Destroy", ghoul);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, ghoul, 1);
        assertHandCount(playerA, 2);
        assertHandCount(playerB, 0);
        assertCounterCount(playerA, nixilis, CounterType.LOYALTY, 3);
        assertLife(playerA, 20);
        assertLife(playerB, 20);
    }

}
