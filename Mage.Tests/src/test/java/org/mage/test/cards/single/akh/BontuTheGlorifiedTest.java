package org.mage.test.cards.single.akh;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link BontuTheGlorifiedTest Bontu the Glorified}
 * Menace, Indestructible
 * Bontu the Glorified can’t attack or block unless a creature died under your control this turn.
 * {1}{B}, Sacrifice another creature: Scry 1. Each opponent loses 1 life and you gain 1 life.
 */
public class BontuTheGlorifiedTest extends CardTestPlayerBase {
    String bontu = "Bontu the Glorified";
    String swamp = "Swamp";
    String grizzly = "Grizzly Bears";

    @Test
    public void testBontuNotAttack() {
        addCard(Zone.BATTLEFIELD, playerA, bontu);
        addCard(Zone.BATTLEFIELD, playerA, swamp, 10);
        addCard(Zone.BATTLEFIELD, playerA, grizzly);

        attack(1, playerA, bontu);

        setStopAt(4, PhaseStep.POSTCOMBAT_MAIN);

        try {
            execute();

            Assert.fail("must throw exception on execute");
        } catch (Throwable e) {
            if (!e.getMessage().contains("Player PlayerA must have 0 actions but found 1")) {
                Assert.fail("must throw error about having 0 actions, but got:\n" + e.getMessage());
            }
        }

        assertLife(playerB, 20);

    }

    @Test
    public void testBontuAttack() {
        addCard(Zone.BATTLEFIELD, playerA, bontu);
        addCard(Zone.BATTLEFIELD, playerA, swamp, 10);
        addCard(Zone.BATTLEFIELD, playerA, grizzly);
        addCard(Zone.HAND, playerA, "Terror");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Terror", grizzly);

        attack(1, playerA, bontu);

        setStopAt(4, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 16);

    }

    @Test
    public void testBontuSacAbility() {

        addCard(Zone.BATTLEFIELD, playerA, bontu);
        addCard(Zone.BATTLEFIELD, playerA, swamp, 10);
        addCard(Zone.BATTLEFIELD, playerA, grizzly);
        addCard(Zone.HAND, playerA, "Terror");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{B}");

        setStopAt(4, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 19);
        assertPermanentCount(playerA, grizzly, 0);

    }

    @Test
    public void testBontuSacAbilityTriggersAttack() {
        addCard(Zone.BATTLEFIELD, playerA, bontu);
        addCard(Zone.BATTLEFIELD, playerA, swamp, 10);
        addCard(Zone.BATTLEFIELD, playerA, grizzly);
        addCard(Zone.HAND, playerA, "Terror");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{B}");
        attack(1, playerA, bontu);
        setStopAt(4, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 15);
        assertPermanentCount(playerA, grizzly, 0);

    }
}
