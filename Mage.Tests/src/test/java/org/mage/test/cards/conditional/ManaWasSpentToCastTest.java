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
package org.mage.test.cards.conditional;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class ManaWasSpentToCastTest extends CardTestPlayerBase {
    
    @Test
    public void testArtifactWillBeDestroyed() {
        // Tin Street Hooligan - Creature 2/1   {1}{R}
        // When Tin Street Hooligan enters the battlefield, if {G} was spent to cast Tin Street Hooligan, destroy target artifact.
        addCard(Zone.HAND, playerA, "Tin Street Hooligan");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        
        addCard(Zone.BATTLEFIELD, playerB, "Abzan Banner");
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tin Street Hooligan");
        addTarget(playerA, "Abzan Banner");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Tin Street Hooligan", 1);
        assertPermanentCount(playerB, "Abzan Banner", 0);
    }

    @Test
    public void testArtifactWontBeDestroyed() {
        // Tin Street Hooligan - Creature 2/1   {1}{R}
        // When Tin Street Hooligan enters the battlefield, if {G} was spent to cast Tin Street Hooligan, destroy target artifact.
        addCard(Zone.HAND, playerA, "Tin Street Hooligan");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        
        addCard(Zone.BATTLEFIELD, playerB, "Abzan Banner");
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tin Street Hooligan");
        addTarget(playerA, "Abzan Banner");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Tin Street Hooligan", 1);
        assertPermanentCount(playerB, "Abzan Banner", 1);
    }
    
}