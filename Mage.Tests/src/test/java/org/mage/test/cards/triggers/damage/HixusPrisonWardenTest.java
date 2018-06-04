
package org.mage.test.cards.triggers.damage;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class HixusPrisonWardenTest extends CardTestPlayerBase {

    @Test
    public void testCreatureIsExiled() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        // Flash
        // Whenever a creature deals combat damage to you, if Hixus, Prison Warden entered the battlefield this turn, exile that creature until Hixus leaves the battlefield.
        addCard(Zone.HAND, playerA, "Hixus, Prison Warden", 1); // 4/4

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);

        attack(2, playerB, "Silvercoat Lion");
        castSpell(2, PhaseStep.DECLARE_BLOCKERS, playerA, "Hixus, Prison Warden");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 18);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Hixus, Prison Warden", 1);
        assertExileCount("Silvercoat Lion", 1);
    }

    @Test
    public void testCreatureIsExiledAndDoesReturn() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        // Flash
        // Whenever a creature deals combat damage to you, if Hixus, Prison Warden entered the battlefield this turn, exile that creature until Hixus leaves the battlefield.
        addCard(Zone.HAND, playerA, "Hixus, Prison Warden", 1); // 4/4

        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.HAND, playerB, "Boomerang", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);

        attack(2, playerB, "Silvercoat Lion");
        castSpell(2, PhaseStep.DECLARE_BLOCKERS, playerA, "Hixus, Prison Warden");

        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerB, "Boomerang", "Hixus, Prison Warden");
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 18);
        assertLife(playerB, 20);

        assertGraveyardCount(playerB, "Boomerang", 1);
        assertHandCount(playerA, "Hixus, Prison Warden", 1);
        assertPermanentCount(playerB, "Silvercoat Lion", 1);

        Permanent lion = getPermanent("Silvercoat Lion", playerB);
        Assert.assertEquals("The lion did come into play this turn", true, lion.hasSummoningSickness());
    }

}
