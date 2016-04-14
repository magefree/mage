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
package org.mage.test.cards.abilities.oneshot.damage;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class JayaBallardTaskMageTest extends CardTestPlayerBase {

    @Test
    public void testDamageNormal() {
        // {R}, {tap}, Discard a card: Destroy target blue permanent.
        // {1}{R}, {tap}, Discard a card: Jaya Ballard, Task Mage deals 3 damage to target creature or player. A creature dealt damage this way can't be regenerated this turn.
        // {5}{R}{R}, {tap}, Discard a card: Jaya Ballard deals 6 damage to each creature and each player.
        addCard(Zone.BATTLEFIELD, playerA, "Jaya Ballard, Task Mage");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 7);
        addCard(Zone.HAND, playerA, "Silvercoat Lion", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Pillarfield Ox");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{5}{R}{R}");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Jaya Ballard, Task Mage", 1);
        assertGraveyardCount(playerA, "Silvercoat Lion", 1);

        assertGraveyardCount(playerB, "Pillarfield Ox", 1);

        assertLife(playerA, 14);
        assertLife(playerB, 14);
    }

    @Test
    public void testDamageWithDeathPitsOfRath() {
        // {R}, {tap}, Discard a card: Destroy target blue permanent.
        // {1}{R}, {tap}, Discard a card: Jaya Ballard, Task Mage deals 3 damage to target creature or player. A creature dealt damage this way can't be regenerated this turn.
        // {5}{R}{R}, {tap}, Discard a card: Jaya Ballard deals 6 damage to each creature and each player.
        addCard(Zone.BATTLEFIELD, playerA, "Jaya Ballard, Task Mage");
        // Whenever a creature is dealt damage, destroy it. It can't be regenerated.
        addCard(Zone.BATTLEFIELD, playerA, "Death Pits of Rath");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 7);
        addCard(Zone.HAND, playerA, "Silvercoat Lion", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Pillarfield Ox");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{5}{R}{R}");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Jaya Ballard, Task Mage", 1);
        assertPermanentCount(playerA, "Death Pits of Rath", 1);
        assertGraveyardCount(playerA, "Silvercoat Lion", 1);

        assertGraveyardCount(playerB, "Pillarfield Ox", 1);

        assertLife(playerA, 14);
        assertLife(playerB, 14);
    }

    @Test
    public void testDamageWithRepercussion() {
        // {R}, {tap}, Discard a card: Destroy target blue permanent.
        // {1}{R}, {tap}, Discard a card: Jaya Ballard, Task Mage deals 3 damage to target creature or player. A creature dealt damage this way can't be regenerated this turn.
        // {5}{R}{R}, {tap}, Discard a card: Jaya Ballard deals 6 damage to each creature and each player.
        addCard(Zone.BATTLEFIELD, playerA, "Jaya Ballard, Task Mage");
        // Whenever a creature is dealt damage, Repercussion deals that much damage to that creature's controller.
        addCard(Zone.BATTLEFIELD, playerA, "Repercussion");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 7);
        addCard(Zone.HAND, playerA, "Silvercoat Lion", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Pillarfield Ox", 2);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{5}{R}{R}");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Jaya Ballard, Task Mage", 1);
        assertPermanentCount(playerA, "Repercussion", 1);
        assertGraveyardCount(playerA, "Silvercoat Lion", 1);

        assertGraveyardCount(playerB, "Pillarfield Ox", 2);

        assertLife(playerA, 8);
        assertLife(playerB, 2);
    }

}
