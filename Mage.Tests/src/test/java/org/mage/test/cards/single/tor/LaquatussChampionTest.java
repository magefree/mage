package org.mage.test.cards.single.tor;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author Quercitron
 */
public class LaquatussChampionTest extends CardTestPlayerBase {

    @Test
    public void testEntersBattlefieldTriggeredAbility() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 6);
        addCard(Zone.HAND, playerA, "Laquatus's Champion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Laquatus's Champion");
        addTarget(playerA, playerB);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 14);
    }

    @Test
    public void testLeavesBattlefieldTriggeredAbility() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 7);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 7);
        addCard(Zone.HAND, playerA, "Laquatus's Champion");
        addCard(Zone.HAND, playerA, "Terminate");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Laquatus's Champion");
        addTarget(playerA, playerB);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Terminate", "Laquatus's Champion");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20);
    }

    @Test
    public void testDoubleZoneChange() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.HAND, playerA, "Laquatus's Champion");
        addCard(Zone.HAND, playerA, "Cloudshift");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Laquatus's Champion");
        addTarget(playerA, playerB);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cloudshift", "Laquatus's Champion");
        addTarget(playerA, playerB);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 14);
    }

    @Test
    public void testLeavesBattlefieldWhenFirstAbilityInStack() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 7);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 7);
        addCard(Zone.HAND, playerA, "Laquatus's Champion");
        // Destroy target creature. It can't be regenerated.
        addCard(Zone.HAND, playerA, "Terminate");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Laquatus's Champion");
        addTarget(playerA, playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Terminate", "Laquatus's Champion", "enters the battlefield", StackClause.WHILE_ON_STACK);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20);
    }

}
