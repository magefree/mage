package org.mage.test.cards.single.eld;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.GameException;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class TorbranThaneOfRedFellTest extends CardTestPlayerBase {

    @Test
    public void basicTest() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, "Lightning Bolt", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        // If a red source you control would deal damage to an opponent or a permanent an opponent controls,
        // it deals that much damage plus 2 instead.
        addCard(Zone.BATTLEFIELD, playerA, "Torbran, Thane of Red Fell"); // Creature 2/4 {1}{R}{R}{R}

        addCard(Zone.BATTLEFIELD, playerB, "Pillarfield Ox");

        attack(1, playerA, "Torbran, Thane of Red Fell");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Pillarfield Ox");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 11); // damage from: attack 2 + 2 Bolt 3 + 2

        assertGraveyardCount(playerA, "Lightning Bolt", 2);
        assertGraveyardCount(playerB, "Pillarfield Ox", 1);
    }

    @Test
    public void opponentsTornbanAppliedToOwnPlaneswalkerTest() {
        setStrictChooseMode(true);

        // +1: Elementals you control get +2/+0 until end of turn.
        // −1: Add {R}{R}.
        // −2: Chandra, Novice Pyromancer deals 2 damage to any target.
        addCard(Zone.BATTLEFIELD, playerA, "Chandra, Novice Pyromancer"); // Planeswalker (5) {3}{R}

        // If a red source you control would deal damage to an opponent or a permanent an opponent controls,
        // it deals that much damage plus 2 instead.
        addCard(Zone.BATTLEFIELD, playerB, "Torbran, Thane of Red Fell"); // Creature 2/4 {1}{R}{R}{R}

        addCard(Zone.BATTLEFIELD, playerB, "Barbarian Horde"); // Creature 3/3 {3}{R}

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "-2:", playerB);

        attack(2, playerB, "Barbarian Horde");

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "-2:", "Barbarian Horde");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);

        execute();

        assertLife(playerA, 15); // Attack from Horde 3+2
        assertLife(playerB, 18); // Damage from planeswalker 2

        assertPermanentCount(playerB, "Barbarian Horde", 1);

        assertCounterCount("Chandra, Novice Pyromancer", CounterType.LOYALTY, 1);
    }

    @Test
    public void with3PlayersTest() throws GameException {
        playerC = createPlayer(currentGame, "PlayerC");
        setStrictChooseMode(true);

        // +1: Elementals you control get +2/+0 until end of turn.
        // −1: Add {R}{R}.
        // −2: Chandra, Novice Pyromancer deals 2 damage to any target.
        addCard(Zone.BATTLEFIELD, playerA, "Chandra, Novice Pyromancer"); // Planeswalker (5) {3}{R}

        // If a red source you control would deal damage to an opponent or a permanent an opponent controls,
        // it deals that much damage plus 2 instead.
        addCard(Zone.BATTLEFIELD, playerC, "Torbran, Thane of Red Fell"); // Creature 2/4 {1}{R}{R}{R}

        addCard(Zone.BATTLEFIELD, playerB, "Barbarian Horde"); // Creature 3/3 {3}{R}
        addCard(Zone.BATTLEFIELD, playerB, "Chandra, Novice Pyromancer"); // Planeswalker (5) {3}{R}

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "-2:", playerB);

        attack(3, playerB, "Barbarian Horde", playerA);
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerB, "-2:", playerA);

        activateAbility(4, PhaseStep.PRECOMBAT_MAIN, playerA, "-2:", "Barbarian Horde");

        setStopAt(4, PhaseStep.BEGIN_COMBAT);

        execute();

        assertLife(playerA, 15); // Attack from Horde 3+2
        assertLife(playerB, 18); // Damage from planeswalker 2

        assertPermanentCount(playerB, "Barbarian Horde", 1);

        assertCounterCount(playerA, "Chandra, Novice Pyromancer", CounterType.LOYALTY, 1);
        assertCounterCount(playerB, "Chandra, Novice Pyromancer", CounterType.LOYALTY, 3);
    }
}
