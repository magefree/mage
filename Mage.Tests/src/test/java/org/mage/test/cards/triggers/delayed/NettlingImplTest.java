package org.mage.test.cards.triggers.delayed;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class NettlingImplTest extends CardTestPlayerBase {

    @Test
    public void testForcedDidAttack() {
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);

        // {T}: Choose target non-Wall creature the active player has controlled continuously since the beginning of the turn. That creature attacks this turn if able. If it doesn't, destroy it at the beginning of the next end step. Activate this ability only during an opponent's turn, before attackers are declared.
        addCard(Zone.BATTLEFIELD, playerA, "Nettling Imp", 1);

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Choose", "Silvercoat Lion");

        setStopAt(5, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Nettling Imp", 1);
        assertPermanentCount(playerB, "Silvercoat Lion", 1);

        assertLife(playerA, 18);
        assertLife(playerB, 20);
    }

    @Test
    public void testForcedDidNotAttack() {
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        // {T}: Choose target non-Wall creature the active player has controlled continuously since the beginning of the turn. That creature attacks this turn if able. If it doesn't, destroy it at the beginning of the next end step. Activate this ability only during an opponent's turn, before attackers are declared.
        addCard(Zone.BATTLEFIELD, playerA, "Nettling Imp", 1);
        // Tap target creature.
        // Draw a card.
        addCard(Zone.HAND, playerA, "Pressure Point", 1); // Instant {1}{W}

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Choose", "Silvercoat Lion");
        castSpell(2, PhaseStep.BEGIN_COMBAT, playerA, "Pressure Point", "Silvercoat Lion");
        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Nettling Imp", 1);
        assertGraveyardCount(playerA, "Pressure Point", 1);
        assertPermanentCount(playerB, "Silvercoat Lion", 0);

        assertHandCount(playerA, 2);
        assertLife(playerA, 20);
        assertLife(playerB, 20);
    }

}
