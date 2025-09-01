package org.mage.test.cards.single.tdm;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class NewWayForwardTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.n.NewWayForward New Way Forward} {2}{U}{R}{W}
     * Instant
     * The next time a source of your choice would deal damage to you this turn, prevent that damage. When damage is prevented this way, New Way Forward deals that much damage to that source’s controller and you draw that many cards.
     */
    private static final String way = "New Way Forward";

    @Test
    public void test_DamageOnCreature_NoPrevent() {
        addCard(Zone.HAND, playerA, way, 1);
        addCard(Zone.BATTLEFIELD, playerB, "Goblin Piker", 1); // 2/1
        addCard(Zone.BATTLEFIELD, playerA, "Caelorna, Coral Tyrant"); // 0/8
        addCard(Zone.BATTLEFIELD, playerA, "Plateau", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, way);
        setChoice(playerA, "Goblin Piker"); // source to prevent from

        attack(2, playerB, "Goblin Piker", playerA);
        block(2, playerA, "Caelorna, Coral Tyrant", "Goblin Piker");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertDamageReceived(playerA, "Caelorna, Coral Tyrant", 2); // no prevent
        assertLife(playerB, 20);
        assertHandCount(playerA, 0);
    }

    @Test
    public void test_DamageOnYou_Prevent() {
        addCard(Zone.HAND, playerA, way, 1);
        addCard(Zone.BATTLEFIELD, playerB, "Goblin Piker", 1); // 2/1
        addCard(Zone.BATTLEFIELD, playerA, "Plateau", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, way);
        setChoice(playerA, "Goblin Piker"); // source to prevent from

        attack(2, playerB, "Goblin Piker", playerA);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20 - 2);
        assertHandCount(playerA, 2);
    }

    @Test
    public void test_DoubleStrike_Prevent_ThenConsumedAndNoPrevent() {
        addCard(Zone.HAND, playerA, way, 1);
        addCard(Zone.BATTLEFIELD, playerB, "Blade Historian", 1); // 2/3 "Attacking creatures you control have double strike."
        addCard(Zone.BATTLEFIELD, playerA, "Plateau", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, way);
        setChoice(playerA, "Blade Historian"); // source to prevent from

        attack(2, playerB, "Blade Historian", playerA);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20 - 2);
        assertLife(playerB, 20 - 2);
        assertHandCount(playerA, 2);
    }
}
