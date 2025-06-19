package org.mage.test.cards.single.drk;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class DarkSphereTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.d.DarkSphere Dark Sphere} {0}
     * Artifact
     * {T}, Sacrifice this artifact: The next time a source of your choice would deal damage to you this turn, prevent half that damage, rounded down.
     */
    private static final String sphere = "Dark Sphere";

    @Test
    public void test_DamageOnCreature_NoPrevent() {
        addCard(Zone.BATTLEFIELD, playerA, sphere, 1);
        addCard(Zone.BATTLEFIELD, playerB, "Goblin Piker", 1); // 2/1
        addCard(Zone.BATTLEFIELD, playerA, "Caelorna, Coral Tyrant"); // 0/8

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}, Sacrifice");
        setChoice(playerA, "Goblin Piker"); // source to prevent from

        attack(2, playerB, "Goblin Piker", playerA);
        block(2, playerA, "Caelorna, Coral Tyrant", "Goblin Piker");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertDamageReceived(playerA, "Caelorna, Coral Tyrant", 2); // no prevent
    }

    @Test
    public void test_DamageOnYou_Prevent() {
        addCard(Zone.BATTLEFIELD, playerA, sphere, 1);
        addCard(Zone.BATTLEFIELD, playerB, "Craw Wurm", 1); // 6/4

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}, Sacrifice");
        setChoice(playerA, "Craw Wurm"); // source to prevent from

        attack(2, playerB, "Craw Wurm", playerA);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20 - 3);
    }

    @Test
    public void test_DoubleStrike_Prevent_ThenConsumedAndNoPrevent() {
        addCard(Zone.BATTLEFIELD, playerA, sphere, 1);
        addCard(Zone.BATTLEFIELD, playerB, "Blade Historian", 1); // 2/3 "Attacking creatures you control have double strike."
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}, Sacrifice");
        setChoice(playerA, "Blade Historian"); // source to prevent from

        attack(2, playerB, "Blade Historian", playerA);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20 - 3);
    }
}
