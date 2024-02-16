
package org.mage.test.cards.triggers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LeveX2
 */
public class JourneyToNowhereTest extends CardTestPlayerBase {

    /*
     Journey to Nowhere   Enchantment {1}{W}
     When Journey to Nowhere enters the battlefield, exile target creature.
     When Journey to Nowhere leaves the battlefield, return the exiled card to the battlefield under its owner's control.

     10/1/2009: If Journey to Nowhere leaves the battlefield before its first ability has resolved, its second ability will
     trigger and do nothing. Then its first ability will resolve and exile the targeted creature forever.
     */
    @Test
    public void testTargetGetsExiled() {
        addCard(Zone.HAND, playerA, "Journey to Nowhere");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Journey to Nowhere");
        // Silvercoat Lion is auto-chosen since only target
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Journey to Nowhere", 1);
        assertExileCount("Silvercoat Lion", 1);
    }

    @Test
    public void testTargetGetsExiledAndReturns() {
        addCard(Zone.HAND, playerA, "Journey to Nowhere");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        addCard(Zone.HAND, playerB, "Disenchant", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Journey to Nowhere");
        // Silvercoat Lion is auto-chosen since it's the only possible target

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Disenchant", "Journey to Nowhere");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Journey to Nowhere", 1);
        assertGraveyardCount(playerB, "Disenchant", 1);
        assertPermanentCount(playerB, "Silvercoat Lion", 1);
    }

    /**
     10/1/2009: If Journey to Nowhere leaves the battlefield before its first ability has resolved, its second ability will
     trigger and do nothing. Then its first ability will resolve and exile the targeted creature forever.
     */
    @Test
    public void testTargetGetsExiledAndDoesNeverReturn() {
        addCard(Zone.HAND, playerA, "Journey to Nowhere");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        addCard(Zone.HAND, playerB, "Disenchant", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Journey to Nowhere");
        // Silvercoat Lion" is auto-chosen since only target
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Disenchant", "Journey to Nowhere", "When {this} enters");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Journey to Nowhere", 1);
        assertGraveyardCount(playerB, "Disenchant", 1);
        assertExileCount("Silvercoat Lion", 1);
    }

    /*
     Journey is played and targets the creature as it enters the battlefield.
     The Journey will be returned to hand before the ability resolves.
     The Journey will be played again targeting another creature.
     The Journey will be disenchanted later, so only the second creature has to return to battlefield.

     */
    @Test
    public void testTargetGetsExiledAndDoesNeverReturnAndJourneyPlayedAgain() {
        addCard(Zone.HAND, playerA, "Journey to Nowhere");
        addCard(Zone.HAND, playerA, "Boomerang");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);

        addCard(Zone.HAND, playerB, "Disenchant", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Pillarfield Ox", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Journey to Nowhere");
        addTarget(playerA, "Silvercoat Lion");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Boomerang", "Journey to Nowhere", "Journey to Nowhere", StackClause.WHILE_NOT_ON_STACK);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Journey to Nowhere");
        // Pillarfield Ox is auto-chosen since only possible target

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Disenchant", "Journey to Nowhere");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Boomerang", 1);
        assertGraveyardCount(playerA, "Journey to Nowhere", 1);
        assertGraveyardCount(playerB, "Disenchant", 1);
        assertPermanentCount(playerB, "Pillarfield Ox", 1);

        assertPermanentCount(playerB, "Silvercoat Lion", 0);
        assertExileCount("Silvercoat Lion", 1);

    }
}
