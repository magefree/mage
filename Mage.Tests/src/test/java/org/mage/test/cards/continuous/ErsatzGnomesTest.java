/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package org.mage.test.cards.continuous;

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
public class ErsatzGnomesTest extends CardTestPlayerBase {

    /**
     * Ersatz Gnomes is incorrectly used I targeted a spell which is a permanent
     * to colorless. When it enters the field its suppose to be colorless not go
     * back to normal. It's colorless until it leaves the battlefield when you
     * make a permanent spell colorless when you cast it.
     */
    @Test
    public void testColorlessSpellCreatesColorlessPermanent() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.HAND, playerA, "Silvercoat Lion", 1);

        // {T}: Target spell becomes colorless.
        // {T}: Target permanent becomes colorless until end of turn.
        addCard(Zone.BATTLEFIELD, playerA, "Ersatz Gnomes");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Silvercoat Lion");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Target spell", "Silvercoat Lion");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertTapped("Ersatz Gnomes", true);
        Permanent lion = getPermanent("Silvercoat Lion", playerA);
        Assert.assertTrue("Silvercoat lion has to be colorless", lion.getColor(currentGame).isColorless());
    }

    @Test
    public void testColorlessSpellCreatesColorlessPermanentUntilItBattlefieldLeft() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.HAND, playerA, "Silvercoat Lion", 1);

        // {T}: Target spell becomes colorless.
        // {T}: Target permanent becomes colorless until end of turn.
        addCard(Zone.BATTLEFIELD, playerA, "Ersatz Gnomes");

        addCard(Zone.BATTLEFIELD, playerB, "Island", 1);
        addCard(Zone.HAND, playerB, "Unsummon");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Silvercoat Lion");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Target spell", "Silvercoat Lion");

        castSpell(1, PhaseStep.END_COMBAT, playerB, "Unsummon", "Silvercoat Lion");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Silvercoat Lion");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Unsummon", 1);
        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertTapped("Ersatz Gnomes", true);
        Permanent lion = getPermanent("Silvercoat Lion", playerA);
        Assert.assertTrue("Silvercoat lion has to be white", lion.getColor(currentGame).isWhite());
    }

}
