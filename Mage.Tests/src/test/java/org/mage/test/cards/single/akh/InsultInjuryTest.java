package org.mage.test.cards.single.akh;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author stravant
 */
public class InsultInjuryTest extends CardTestPlayerBase {
    @Test
    public void testCastInsult() {
        //Cast dusk from hand
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 2);
        addCard(Zone.HAND, playerA, "Insult // Injury");

        // Insult, and then deal 5 damage to opponent, should bring them to 10 life
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Insult");
        attack(3, playerA, "Grizzly Bears");
        castSpell(3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", playerB);

        // Next turn, should only deal 3 damage with bolt
        castSpell(4, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", playerA);

        setStopAt(4, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 17);
        assertLife(playerB, 10);
    }

    @Test
    public void testCastInjury() {
        addCard(Zone.BATTLEFIELD, playerB, "Squire");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.GRAVEYARD, playerA, "Insult // Injury");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Injury", "Squire");
        addTarget(playerA, playerB);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerA, "Insult // Injury", 1);
        assertGraveyardCount(playerB, "Squire", 1);
        assertLife(playerB, 18);
    }
}
