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
public class IzzetStaticasterTest extends CardTestPlayerBase {

    /**
     * Izzet Staticaster killed 2 Birds of Paradise under my opponent control
     * but it didn't kill my own Birds of Paradise. I remember this worked good
     * on XMage but this time it just did not. Is it possibile because the 3
     * Birds come from different sets? (my opponent had those Ravnica, while
     * mine was from M12).
     */
    @Test
    public void testAllReceiveDamage() {
        // Flash (You may cast this spell any time you could cast an instant.)
        // Haste
        // {T}: Izzet Staticaster deals 1 damage to target creature and each other creature with the same name as that creature.
        addCard(Zone.BATTLEFIELD, playerA, "Izzet Staticaster");
        addCard(Zone.BATTLEFIELD, playerA, "Birds of Paradise", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Birds of Paradise", 2);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: {this} deals", "Birds of Paradise");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Birds of Paradise", 2);
        assertGraveyardCount(playerB, "Birds of Paradise", 2);

    }
}
