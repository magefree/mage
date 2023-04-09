package org.mage.test.cards.battle;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class BattleTest extends CardTestPlayerBase {

    private static final String belenon = "Invasion of Belenon";
    private static final String bear = "Grizzly Bears";
    private static final String confiscate = "Confiscate";

    private void assertBattle(Player controller, Player protector, String name) {
        assertPermanentCount(controller, name, 1);
        Permanent permanent = getPermanent(name);
        Assertions.assertTrue(
                permanent.isProtectedBy(protector.getId()),
                "Battle " + name + " should be protected by " + protector.getName()
        );
    }

    @Test
    public void testRegularCastAndTrigger() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.HAND, playerA, belenon);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, belenon);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertBattle(playerA, playerB, belenon);
        assertPermanentCount(playerA, "Knight Token", 1);
    }

    @Test
    public void testAttackBattle() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, bear);
        addCard(Zone.HAND, playerA, belenon);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, belenon);

        attack(1, playerA, bear, belenon);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertBattle(playerA, playerB, belenon);
        assertPermanentCount(playerA, "Knight Token", 1);
        assertTapped(bear, true);
        assertLife(playerB, 20);
        assertCounterCount(belenon, CounterType.DEFENSE, 5 - 2);
    }

    @Test
    public void testCantAttackBattleYouProtect() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerB, bear);
        addCard(Zone.HAND, playerA, belenon);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, belenon);

        attack(2, playerB, bear, belenon);

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertBattle(playerA, playerB, belenon);
        assertPermanentCount(playerA, "Knight Token", 1);
        assertTapped(bear, false);
        assertLife(playerB, 20);
        assertCounterCount(belenon, CounterType.DEFENSE, 5);
    }

    @Test
    public void testChangeControl() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 6);
        addCard(Zone.HAND, playerB, confiscate);
        addCard(Zone.HAND, playerA, belenon);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, belenon);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, confiscate, belenon);

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertBattle(playerB, playerA, belenon);
        assertPermanentCount(playerA, "Knight Token", 1);
    }
}
