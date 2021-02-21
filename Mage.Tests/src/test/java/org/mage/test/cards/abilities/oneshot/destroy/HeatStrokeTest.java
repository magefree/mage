package org.mage.test.cards.abilities.oneshot.destroy;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class HeatStrokeTest extends CardTestPlayerBase {

    @Test
    public void testWithValidTarget() {
        addCard(Zone.BATTLEFIELD, playerA, "Heat Stroke");

        addCard(Zone.BATTLEFIELD, playerA, "Pillarfield Ox");
        addCard(Zone.BATTLEFIELD, playerB, "Pillarfield Ox");

        attack(2, playerB, "Pillarfield Ox");
        block(2, playerA, "Pillarfield Ox", "Pillarfield Ox");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Pillarfield Ox", 0);
        assertPermanentCount(playerB, "Pillarfield Ox", 0);

        assertGraveyardCount(playerA, "Pillarfield Ox", 1);
        assertGraveyardCount(playerB, "Pillarfield Ox", 1);

    }

}
