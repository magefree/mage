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
package org.mage.test.cards.abilities.activated;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class PutToGraveyardTest extends CardTestPlayerBase {

    /**
     * Void Attendant	The usable "Prozessor-Effect" doesnt put the card back
     * into the graveyard from the exile.
     */
    @Test
    public void testExileToGraveyard() {
        // Devoid
        // {1}{G}, Put a card an opponent owns from exile into that player's graveyard: Put a 1/1 colorless Eldrazi Scion creature token onto the battlefield. It has "Sacrifice this creature: Add {C} to your mana pool."
        addCard(Zone.BATTLEFIELD, playerA, "Void Attendant");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        // Flash (You may cast this spell any time you could cast an instant.)
        // When Stasis Snare enters the battlefield, exile target creature an opponent controls until Stasis Snare leaves the battlefield. (That creature returns under its owner's control.)
        addCard(Zone.HAND, playerA, "Stasis Snare", 1); // {1}{W}{W}

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Stasis Snare");
        addTarget(playerA, "Silvercoat Lion");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{G},", NO_TARGET, "");
        addTarget(playerA, "Silvercoat Lion");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Stasis Snare", 1);
        assertPermanentCount(playerB, "Silvercoat Lion", 0);
        assertExileCount(playerB, 0);
        assertGraveyardCount(playerB, "Silvercoat Lion", 1);
        assertPermanentCount(playerA, "Eldrazi Scion", 1);

    }

}
