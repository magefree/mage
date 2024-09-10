package org.mage.test.cards.single.slx;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Before;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TadeasJuniperAscendantTest extends CardTestPlayerBase {

    @Before
    public void setUp() {
        addCard(Zone.BATTLEFIELD, playerA, "Tadeas, Juniper Ascendant"); // 1/3
        addCard(Zone.BATTLEFIELD, playerA, "Sweet-Gum Recluse");         // 0/3 //Reach
        addCard(Zone.BATTLEFIELD, playerA, "Canopy Spider");             // 1/3 //Reach
        addCard(Zone.BATTLEFIELD, playerA, "Brood Weaver");              // 2/4 //Reach
        addCard(Zone.BATTLEFIELD, playerA, "Acolyte of Xathrid");        // 0/5

        addCard(Zone.BATTLEFIELD, playerB, "Phyrexian Walker");          // 0/3
        addCard(Zone.BATTLEFIELD, playerB, "Dryad Arbor");               // 1/1
        addCard(Zone.BATTLEFIELD, playerB, "Runeclaw Bear");             // 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Southern Elephant");         // 3/4
    }

    private Permanent getBlocker(String blocker, mage.game.Game game) {
        return game.getBattlefield().getAllActivePermanents()
                .stream()
                .filter(p -> p.getName().equals(blocker))
                .findFirst()
                .get();
    }

    @Test
    public void testAttackerLessThanTadeasAttackBlockerPowerEqualAttacker() {
        attack(1, playerA, "Canopy Spider");
        runCode("check blocking", 1, PhaseStep.DECLARE_BLOCKERS, playerB, (info, player, game) -> {
            Permanent equalPowerBlocker = getBlocker("Dryad Arbor", game);
            assertTrue(game.getCombat().getGroups().get(0).canBlock(equalPowerBlocker, game),
                    "equalPowerBlocker should be able to block");
        });

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void testAttackerLessThanTadeasAttackBlockerPowerMoreThanAttacker() {
        attack(1, playerA, "Sweet-Gum Recluse");
        runCode("check blocking", 1, PhaseStep.DECLARE_BLOCKERS, playerB, (info, player, game) -> {
            Permanent morePowerBlocker = getBlocker("Southern Elephant", game);
            assertFalse(game.getCombat().getGroups().get(0).canBlock(morePowerBlocker, game),
                    "morePowerBlocker should not be able to block");
        });

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void testAttackerLessThanTadeasAttackWithoutReachBlockerPowerMoreThanAttacker() {
        attack(1, playerA, "Canopy Spider");
        attack(1, playerA, "Acolyte of Xathrid");
        runCode("check blocking", 1, PhaseStep.DECLARE_BLOCKERS, playerB, (info, player, game) -> {
            Permanent morePowerBlocker = getBlocker("Runeclaw Bear", game);
            assertTrue(game.getCombat().getGroups().get(1).canBlock(morePowerBlocker, game),
                    "morePowerBlocker should be able to block");
        });

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void testAttackerEqualTadeasAttackBlockerPowerLessThanAttacker() {
        attack(1, playerA, "Canopy Spider");
        runCode("check blocking", 1, PhaseStep.DECLARE_BLOCKERS, playerB, (info, player, game) -> {
            Permanent lessPowerBlocker = getBlocker("Phyrexian Walker", game);
            assertTrue(game.getCombat().getGroups().get(0).canBlock(lessPowerBlocker, game),
                    "lessPowerBlocker should be able to block");
        });

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void testAttackerEqualTadeasAttackBlockerPowerEqualAttacker() {
        attack(1, playerA, "Canopy Spider");
        runCode("check blocking", 1, PhaseStep.DECLARE_BLOCKERS, playerB, (info, player, game) -> {
            Permanent equalPowerBlocker = getBlocker("Dryad Arbor", game);
            assertTrue(game.getCombat().getGroups().get(0).canBlock(equalPowerBlocker, game),
                    "equalPowerBlocker should be able to block");
        });

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void testAttackerEqualTadeasAttackBlockerPowerMoreThanAttacker() {
        attack(1, playerA, "Canopy Spider");
        runCode("check blocking", 1, PhaseStep.DECLARE_BLOCKERS, playerB, (info, player, game) -> {
            Permanent morePowerBlocker = getBlocker("Southern Elephant", game);
            assertFalse(game.getCombat().getGroups().get(0).canBlock(morePowerBlocker, game),
                    "morePowerBlocker should not be able to block");
        });

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

        @Test
        public void testAttackerMoreThanTadeasAttackBlockerPowerLessThanAttacker() {
            attack(1, playerA, "Brood Weaver");
            runCode("check blocking", 1, PhaseStep.DECLARE_BLOCKERS, playerB, (info, player, game) -> {
                Permanent lessPowerBlocker = getBlocker("Dryad Arbor", game);
                assertTrue(game.getCombat().getGroups().get(0).canBlock(lessPowerBlocker, game),
                        "lessPowerBlocker should not be able to block");
            });

            setStrictChooseMode(true);
            setStopAt(1, PhaseStep.END_TURN);
            execute();
        }

        @Test
        public void testAttackerMoreThanTadeasAttackBlockerPowerEqualAttacker() {
            attack(1, playerA, "Brood Weaver");
            runCode("check blocking", 1, PhaseStep.DECLARE_BLOCKERS, playerB, (info, player, game) -> {
                Permanent equalPowerBlocker = getBlocker("Runeclaw Bear", game);
                assertTrue(game.getCombat().getGroups().get(0).canBlock(equalPowerBlocker, game),
                        "equalPowerBlocker should be able to block");
            });

            setStrictChooseMode(true);
            setStopAt(1, PhaseStep.END_TURN);
            execute();
        }

        @Test
        public void testAttackerMoreThanTadeasAttackBlockerPowerMoreThanAttacker() {
            attack(1, playerA, "Brood Weaver");
            runCode("check blocking", 1, PhaseStep.DECLARE_BLOCKERS, playerB, (info, player, game) -> {
                Permanent morePowerBlocker = getBlocker("Southern Elephant", game);
                assertFalse(game.getCombat().getGroups().get(0).canBlock(morePowerBlocker, game),
                        "morePowerBlocker should not be able to block");
            });

            setStrictChooseMode(true);
            setStopAt(1, PhaseStep.END_TURN);
            execute();
        }
}
