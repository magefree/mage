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

import mage.abilities.keyword.HasteAbility;
import mage.constants.CardType;
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
public class FelhideSpiritbinderTest extends CardTestPlayerBase {

    /**
     * http://www.slightlymagic.net/forum/viewtopic.php?f=70&t=17295&p=181417#p181440
     * Felhide Spiritbinder does not seem to be giving haste or the enchantment
     * subtype to tokens it creates..
     *
     */
    @Test
    public void testTokenCopy() {
        // Inspired - Whenever Felhide Spiritbinder becomes untapped, you may pay {1}{R}.
        // If you do, put a token onto the battlefield that's a copy of another target creature
        // except it's an enchantment in addition to its other types. It gains haste. Exile it at the beginning of the next end step.
        addCard(Zone.BATTLEFIELD, playerB, "Felhide Spiritbinder", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 2);

        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);

        attack(2, playerB, "Felhide Spiritbinder");

        setStopAt(4, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerB, "Silvercoat Lion", 1);
        Permanent lion = getPermanent("Silvercoat Lion", playerB);
        assertAbility(playerB, "Silvercoat Lion", HasteAbility.getInstance(), true);
        Assert.assertEquals("token has to have card type enchantment", true, lion.getCardType().contains(CardType.ENCHANTMENT));

        assertLife(playerA, 17);
        assertLife(playerB, 20);

    }

    @Test
    public void testTokenCopyExiled() {
        // Inspired - Whenever Felhide Spiritbinder becomes untapped, you may pay {1}{R}.
        // If you do, put a token onto the battlefield that's a copy of another target creature
        // except it's an enchantment in addition to its other types. It gains haste. Exile it at the beginning of the next end step.
        addCard(Zone.BATTLEFIELD, playerB, "Felhide Spiritbinder", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 2);

        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);

        attack(2, playerB, "Felhide Spiritbinder");

        setStopAt(5, PhaseStep.UPKEEP);
        execute();

        assertPermanentCount(playerB, "Silvercoat Lion", 0);

        assertLife(playerA, 17);
        assertLife(playerB, 20);

    }

    @Test
    public void testCopyATokenCreature() {

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.HAND, playerA, "Call of the Herd", 1);

        // Inspired - Whenever Felhide Spiritbinder becomes untapped, you may pay {1}{R}.
        // If you do, put a token onto the battlefield that's a copy of another target creature
        // except it's an enchantment in addition to its other types. It gains haste. Exile it at the beginning of the next end step.
        addCard(Zone.BATTLEFIELD, playerB, "Felhide Spiritbinder", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Call of the Herd");

        attack(2, playerB, "Felhide Spiritbinder");

        setStopAt(4, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Elephant", 1);
        assertPermanentCount(playerB, "Elephant", 1);
        assertAbility(playerB, "Elephant", HasteAbility.getInstance(), true);

        Permanent copiedTokenElephant = getPermanent("Elephant", playerB);
        Assert.assertEquals("Elephant has Enchantment card type", true, copiedTokenElephant.getCardType().contains(CardType.ENCHANTMENT));

        assertLife(playerA, 17);
        assertLife(playerB, 20);

    }
}
