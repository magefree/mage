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
package org.mage.test.cards.copy;

import mage.abilities.keyword.FlyingAbility;
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
public class TheMimeoplasmTest extends CardTestPlayerBase {

    /**
     * I cast either Phyrexian Metamorph or Phantasmal Image and copied The
     * Mimeoplasm which was copying a Primeval Titan, but my clone became
     * another Mimeoplasm, so it exiled two creatures from graveyards and cloned
     * one of those instead.
     *
     * Copying cards that are copy of other cards copies the original, rather
     * than the copy.
     * To wit, when The Mimeoplasm is out (and lets say is a copy of Elvish
     * Mystic), and someone then plays Clone choosing to enter as the cloned
     * Elvish Mystic, they are incorrectly getting The Mimeoplasm instead.
     * 
     */
    @Test
    public void testCloneMimeoplasm() {
        // As The Mimeoplasm enters the battlefield, you may exile two creature cards from graveyards. 
        // If you do, it enters the battlefield as a copy of one of those cards with a number of additional +1/+1 counters on it equal to the power of the other card.        
        addCard(Zone.HAND, playerA, "The Mimeoplasm", 1); // {2}{G}{U}{B}
        
        addCard(Zone.HAND, playerA, "Clone", 1); // {3}{U}
        
        addCard(Zone.GRAVEYARD, playerB, "Silvercoat Lion", 1); // 2/2
        addCard(Zone.GRAVEYARD, playerB, "Aven Riftwatcher", 1); // 2/3
        
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 7);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);


        castSpell(1,PhaseStep.PRECOMBAT_MAIN, playerA, "The Mimeoplasm");
        setChoice(playerA, "Aven Riftwatcher");
        setChoice(playerA, "Silvercoat Lion");

        castSpell(1,PhaseStep.PRECOMBAT_MAIN, playerA, "Clone");
        setChoice(playerA, "Aven Riftwatcher");
        
        
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, "Clone", 0);

        assertLife(playerA, 24);
        assertLife(playerB, 20);
        
        assertPermanentCount(playerA, "Aven Riftwatcher", 2);
        assertPowerToughness(playerA, "Aven Riftwatcher", 4, 5);
        assertPowerToughness(playerA, "Aven Riftwatcher", 2, 3);

        assertGraveyardCount(playerB, 0);
        

    }
}