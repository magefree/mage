/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mage.test.cards.control;

import mage.abilities.keyword.HasteAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class GainControlTargetEffectTest extends CardTestPlayerBase {

    /**
     * Checks if control has changed and the controlled creature has Hase
     * 
     */
    @Test
    public void testPermanentControlEffect() {
        addCard(Zone.HAND, playerA, "Smelt-Ward Gatekeepers", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Boros Guildgate", 2);

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Smelt-Ward Gatekeepers");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // under opponent's control
        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertAbility(playerA, "Silvercoat Lion", HasteAbility.getInstance(), true);
    }
}
