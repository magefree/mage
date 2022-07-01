package org.mage.test.cards.single.afr;

import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestMultiPlayerBase;

/**
 * {@link VrondissRageOfAncientsTest Vrondriss, Rage of Ancients}
 * Enrage — Whenever Vrondiss, Rage of Ancients is dealt damage,
 *          you may create a 5/4 red and green Dragon Spirit creature token with
 *          “When this creature deals damage, sacrifice it.”
 * Whenever you roll one or more dice, you may have Vrondiss deal 1 damage to itself.
 */
public class VrondissRageOfAncientsTest extends CardTestMultiPlayerBase {

    /**
     * Reported bug: https://github.com/magefree/mage/issues/8189
     *
     * Chaos Dragon attacking would cause Vrondiss to trigger off of every player's roll, not just Vrondiss' controller.
     *
     * {@link mage.cards.c.ChaosDragon Chaos Dragon}
     * At the beginning of combat on your turn, each player rolls a d20.
     * If one or more opponents had the highest result,
     * Chaos Dragon can’t attack those players or planeswalkers they control this combat.
     */
    @Test
    public void testChaosDragonInteraction() {
        addCard(Zone.BATTLEFIELD, playerA, "Vrondiss, Rage of Ancients");
        addCard(Zone.BATTLEFIELD, playerA, "Chaos Dragon");

        setStrictChooseMode(true);

        attack(1, playerA, "Chaos Dragon", playerB);
        setDieRollResult(playerA, 10);
        setDieRollResult(playerB, 11);
        // setDieRollResult(playerC, 12);  NOTE: Range of influence of 1, so playerC does not have to roll
        setDieRollResult(playerD, 13);

        // Set choice for Vrondiss triggered ability
        setChoice(playerA, "No");
        // Should only be one trigger since it should only trigger off of playerA's roll

        execute();
        assertAllCommandsUsed();
    }
}
