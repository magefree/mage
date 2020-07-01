/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mage.test.cards.modal;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class OneOrMoreTest extends CardTestPlayerBase {

    /**
     * Sublime Epiphany can bounce and
     * copy the same creature. This is because legality of targets is checked
     * only as the spell begins to resolve, not in between modes, and because
     * the games can use last known info of the legal target.
     */
    @Test
    public void testSubtleStrikeFirstMode() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        // Choose one or more —
        // 1 • Counter target spell
        // 2 • Counter target activated or triggered ability.
        // 3 • Return target nonland permanent to its owner's hand.
        // 4 • Create a token that's a copy of target creature you control.
        // 5 • Target player draws a card.
        addCard(Zone.HAND, playerA, "Sublime Epiphany"); // Instant {4}{U}{U}

        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sublime Epiphany", "Silvercoat Lion");
        setModeChoice(playerA, "3");        
        setModeChoice(playerA, "4");
        addTarget(playerA, "Silvercoat Lion");
        setModeChoice(playerA, "5");
        addTarget(playerA, playerB);
        setModeChoice(playerA, null);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerB, 1);
        assertHandCount(playerA, "Silvercoat Lion", 1);
        
        assertPowerToughness(playerA, "Silvercoat Lion", 2, 2);
        
        Permanent perm = getPermanent("Silvercoat Lion");
        Assert.assertTrue("Silvercoat Lion has to be a Token", perm instanceof PermanentToken);
        
    }
}
