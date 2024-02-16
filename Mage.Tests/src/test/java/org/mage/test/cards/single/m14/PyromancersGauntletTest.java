package org.mage.test.cards.single.m14;

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
public class PyromancersGauntletTest extends CardTestPlayerBase {

    @Test
    public void basicTest() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, "Lightning Bolt", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        // If a red instant or sorcery spell you control or a red planeswalker you control
        // would deal damage to a permanent or player, it deals that much damage plus 2 to that permanent or player instead.
        addCard(Zone.BATTLEFIELD, playerA, "Pyromancer's Gauntlet"); // Artifact {5}

        addCard(Zone.BATTLEFIELD, playerB, "Pillarfield Ox");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Pillarfield Ox");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 15); // Bolt 3 + 2

        assertGraveyardCount(playerA, "Lightning Bolt", 2);
        assertGraveyardCount(playerB, "Pillarfield Ox", 1);
    }

    @Test
    public void opponentsPyromancersGauntletAppliedToOwnPlaneswalkerTest() {
        setStrictChooseMode(true);

        // +1: Elementals you control get +2/+0 until end of turn.
        // −1: Add {R}{R}.
        // −2: Chandra, Novice Pyromancer deals 2 damage to any target.
        addCard(Zone.BATTLEFIELD, playerA, "Chandra, Novice Pyromancer"); // Planeswalker (5) {3}{R}

        // If a red instant or sorcery spell you control or a red planeswalker you control
        // would deal damage to a permanent or player, it deals that much damage plus 2 to that permanent or player instead.
        addCard(Zone.BATTLEFIELD, playerB, "Pyromancer's Gauntlet"); // Creature 2/4 {1}{R}{R}{R}
        addCard(Zone.BATTLEFIELD, playerB, "Chandra, Novice Pyromancer"); // Planeswalker (5) {3}{R}

        addCard(Zone.BATTLEFIELD, playerB, "Barbarian Horde"); // Creature 3/3 {3}{R}

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "-2:", playerB);

        attack(2, playerB, "Barbarian Horde");
        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "-2:", playerA);

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "-2:", "Barbarian Horde");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);

        execute();

        assertLife(playerA, 13); // Attack from Horde 3 + Dmage 2+2 from planeswalker
        assertLife(playerB, 18); // Damage from planeswalker 2

        assertPermanentCount(playerB, "Barbarian Horde", 1);

        assertCounterCount(playerA, "Chandra, Novice Pyromancer", CounterType.LOYALTY, 1);
        assertCounterCount(playerB, "Chandra, Novice Pyromancer", CounterType.LOYALTY, 3);
    }

    @Test
    public void with3PlayersTest() throws GameException {
        playerC = createPlayer(currentGame, "PlayerC");

        setStrictChooseMode(true);

        // +1: Elementals you control get +2/+0 until end of turn.
        // −1: Add {R}{R}.
        // −2: Chandra, Novice Pyromancer deals 2 damage to any target.
        addCard(Zone.BATTLEFIELD, playerA, "Chandra, Novice Pyromancer"); // Planeswalker (5) {3}{R}

        // If a red instant or sorcery spell you control or a red planeswalker you control
        // would deal damage to a permanent or player, it deals that much damage plus 2 to that permanent or player instead.
        addCard(Zone.BATTLEFIELD, playerC, "Pyromancer's Gauntlet"); // Creature 2/4 {1}{R}{R}{R}
        addCard(Zone.BATTLEFIELD, playerB, "Chandra, Novice Pyromancer"); // Planeswalker (5) {3}{R}

        addCard(Zone.BATTLEFIELD, playerB, "Barbarian Horde"); // Creature 3/3 {3}{R}

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "-2:", playerB);

        attack(3, playerB, "Barbarian Horde");
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerB, "-2:", playerA);

        activateAbility(4, PhaseStep.PRECOMBAT_MAIN, playerA, "-2:", "Barbarian Horde");

        setStopAt(4, PhaseStep.BEGIN_COMBAT);

        execute();

        assertLife(playerA, 15); // Attack from Horde 3 + Dmage 2 from planeswalker
        assertLife(playerB, 18); // Damage from planeswalker 2
        assertLife(playerC, 20);
        assertPermanentCount(playerB, "Barbarian Horde", 1);

        assertCounterCount(playerA, "Chandra, Novice Pyromancer", CounterType.LOYALTY, 1);
        assertCounterCount(playerB, "Chandra, Novice Pyromancer", CounterType.LOYALTY, 3);
    }
}
