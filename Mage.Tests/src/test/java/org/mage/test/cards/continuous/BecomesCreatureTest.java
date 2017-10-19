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

import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class BecomesCreatureTest extends CardTestPlayerBase {

    @Test
    public void testChimericMass() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);

        // Chimeric Mass enters the battlefield with X charge counters on it.
        // {1}: Until end of turn, Chimeric Mass becomes a Construct artifact creature with "This creature's power and toughness are each equal to the number of charge counters on it."
        addCard(Zone.HAND, playerA, "Chimeric Mass", 1); // Artifiact - {X}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Chimeric Mass");
        setChoice(playerA, "X=3");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}:");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Chimeric Mass", 1);
        assertPowerToughness(playerA, "Chimeric Mass", 3, 3);
        assertType("Chimeric Mass", CardType.CREATURE, SubType.CONSTRUCT);

    }

    @Test
    public void testChimericMassAbilityRemoved() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);

        // Chimeric Mass enters the battlefield with X charge counters on it.
        // {1}: Until end of turn, Chimeric Mass becomes a Construct artifact creature with "This creature's power and toughness are each equal to the number of charge counters on it."
        addCard(Zone.HAND, playerA, "Chimeric Mass", 1); // Artifiact - {X}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Chimeric Mass");
        setChoice(playerA, "X=3");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}:");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Chimeric Mass", 1);
        assertPowerToughness(playerA, "Chimeric Mass", 0, 0);
        assertType("Chimeric Mass", CardType.CREATURE, false);

        Assert.assertTrue("All layered effect have to be removed", currentGame.getContinuousEffects().getLayeredEffects(currentGame).isEmpty());

    }
}
