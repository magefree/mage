package org.mage.test.cards.single.akh;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

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
        execute();

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
