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

package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class DashTest extends CardTestPlayerBase {

    /**
     * 702.108. Dash
     * 702.108a Dash represents three abilities: two static abilities that function while the card with dash is
     * on the stack, one of which may create a delayed triggered ability, and a static ability that
     * functions while the object with dash is on the battlefield. “Dash [cost]” means “You may cast
     * this card by paying [cost] rather that its mana cost,” “If this spell’s dash cost was paid, return the
     * permanent this spell becomes to its owner’s hand at the beginning of the next end step,” and “As
     * long as this permanent’s dash cost was paid, it has haste.” Paying a card’s dash cost follows the
     * rules for paying alternative costs in rules 601.2b and 601.2e–g.
     * 
     */

    /** 
     * 	Screamreach Brawler
     * 	Creature — Orc Berserker 2/3, 2R (3)
     * 	Dash {1}{R} (You may cast this spell for its dash cost. If you do, it 
     *  gains haste, and it's returned from the battlefield to its owner's hand 
     *  at the beginning of the next end step.)
     *
     */

    @Test
    public void testDash() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.HAND, playerA, "Screamreach Brawler");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Screamreach Brawler");
        setChoice(playerA, "Yes");
        attack(1, playerA, "Screamreach Brawler");

        setStopAt(2, PhaseStep.UNTAP);
        execute();

        assertLife(playerB, 18);
        assertPermanentCount(playerA, "Screamreach Brawler", 0);
        assertHandCount(playerA, "Screamreach Brawler", 1);

    }

    @Test
    public void testNoDash() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.HAND, playerA, "Screamreach Brawler");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Screamreach Brawler");
        setChoice(playerA, "No");
        attack(1, playerA, "Screamreach Brawler");

        setStopAt(2, PhaseStep.UNTAP);
        execute();

        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Screamreach Brawler", 1);
        assertHandCount(playerA, "Screamreach Brawler", 0);

    }

}
