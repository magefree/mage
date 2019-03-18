
package org.mage.test.combat;

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
public class RemoveFromCombatTest extends CardTestPlayerBase {

    /**
     * In a test game against the AI, it attacked me with a Stomping Ground
     * animated by an Ambush Commander and boosted it with the Commander's
     * second ability. I killed the Commander. The now non-creature land
     * continued attacking and dealt 3 damage to me.
     */
    @Test
    public void testLeavesCombatIfNoLongerACreature() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.HAND, playerA, "Lightning Blast", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Elvish Mystic", 1);

        // Forests you control are 1/1 green Elf creatures that are still lands.
        // {1}{G},Sacrifice an Elf: Target creature gets +3/+3 until end of turn.
        addCard(Zone.BATTLEFIELD, playerB, "Ambush Commander", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Stomping Ground");

        attack(2, playerB, "Stomping Ground");
        activateAbility(2, PhaseStep.DECLARE_ATTACKERS, playerB, "{1}{G}", "Stomping Ground");
        setChoice(playerB, "Elvish Mystic");
        castSpell(2, PhaseStep.DECLARE_BLOCKERS, playerA, "Lightning Blast", "Ambush Commander");
        setStopAt(2, PhaseStep.COMBAT_DAMAGE);
        execute();

        assertGraveyardCount(playerB, "Elvish Mystic", 1);
        assertGraveyardCount(playerA, "Lightning Blast", 1);
        assertGraveyardCount(playerB, "Ambush Commander", 1);

        assertPowerToughness(playerB, "Stomping Ground", 0, 0);

        Permanent stompingGround = getPermanent("Stomping Ground", playerB);
        Assert.assertEquals("Stomping Ground has to be removed from combat", false, stompingGround.isAttacking());

        assertLife(playerA, 20);
        assertLife(playerB, 20);

    }

}
