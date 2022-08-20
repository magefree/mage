package org.mage.test.cards.single.cmd;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class AcornCatapultTest extends CardTestPlayerBase {

    @Test
    public void testAcornCatapultCreature() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        // {1}, {T}: Acorn Catapult deals 1 damage to any target.
        // That permanent's controller or that player creates a 1/1 green Squirrel creature token.
        addCard(Zone.HAND, playerA, "Acorn Catapult"); //Artifact {4}

        // {1}{B}, {T}: Target player loses 1 life.
        addCard(Zone.BATTLEFIELD, playerB, "Acolyte of Xathrid"); // Creature 0/1

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Acorn Catapult");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}, {T}:", "Acolyte of Xathrid");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Acorn Catapult", 1);

        assertGraveyardCount(playerB, "Acolyte of Xathrid", 1);

        assertPermanentCount(playerB, "Squirrel Token", 1);
    }

    @Test
    public void testAcornCatapultPlayer() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        // {1}, {T}: Acorn Catapult deals 1 damage to any target.
        // That permanent's controller or that player creates a 1/1 green Squirrel creature token.
        addCard(Zone.HAND, playerA, "Acorn Catapult"); //Artifact {4}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Acorn Catapult");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}, {T}:", playerB);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Acorn Catapult", 1);

        assertLife(playerB, 19);

        assertPermanentCount(playerB, "Squirrel Token", 1);

    }
}
