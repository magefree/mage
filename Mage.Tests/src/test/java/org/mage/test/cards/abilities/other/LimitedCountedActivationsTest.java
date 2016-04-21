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
package org.mage.test.cards.abilities.other;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class LimitedCountedActivationsTest extends CardTestPlayerBase {

    /**
     * Tests usage of ActivationInfo class
     */
    @Test
    public void testDragonWhelpActivatedThreeTimes() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        // Flying
        // {R}: Dragon Whelp gets +1/+0 until end of turn. If this ability has been activated four or more times this turn, sacrifice Dragon Whelp at the beginning of the next end step.
        addCard(Zone.BATTLEFIELD, playerA, "Dragon Whelp", 1); // 3/3

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{R}: ");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{R}: ");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{R}: ");

        attack(1, playerA, "Dragon Whelp");

        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertPermanentCount(playerA, "Dragon Whelp", 1);

        assertLife(playerA, 20);
        assertLife(playerB, 15);
    }

    @Test
    public void testDragonWhelpActivatedFourTimes() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        // Flying
        // {R}: Dragon Whelp gets +1/+0 until end of turn. If this ability has been activated four or more times this turn, sacrifice Dragon Whelp at the beginning of the next end step.
        addCard(Zone.BATTLEFIELD, playerA, "Dragon Whelp", 1); // 3/3

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{R}: ");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{R}: ");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{R}: ");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{R}: ");

        attack(1, playerA, "Dragon Whelp");

        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertPermanentCount(playerA, "Dragon Whelp", 0);
        assertGraveyardCount(playerA, "Dragon Whelp", 1);

        assertLife(playerA, 20);
        assertLife(playerB, 14);
    }

    @Test
    public void testDragonWhelpActivatedFiveTimes() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        // Flying
        // {R}: Dragon Whelp gets +1/+0 until end of turn. If this ability has been activated four or more times this turn, sacrifice Dragon Whelp at the beginning of the next end step.
        addCard(Zone.BATTLEFIELD, playerA, "Dragon Whelp", 1); // 3/3

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{R}: ");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{R}: ");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{R}: ");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{R}: ");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{R}: ");

        attack(1, playerA, "Dragon Whelp");

        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertPermanentCount(playerA, "Dragon Whelp", 0);
        assertGraveyardCount(playerA, "Dragon Whelp", 1);

        assertLife(playerA, 20);
        assertLife(playerB, 13);
    }

    @Test
    public void testDragonWhelpTwoObjects() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        // Flying
        // {R}: Dragon Whelp gets +1/+0 until end of turn. If this ability has been activated four or more times this turn, sacrifice Dragon Whelp at the beginning of the next end step.
        addCard(Zone.BATTLEFIELD, playerA, "Dragon Whelp", 1); // 3/3
        // Put target creature card from a graveyard onto the battlefield under your control. You lose life equal to its converted mana cost.
        addCard(Zone.HAND, playerA, "Reanimate", 1);
        // Target creature gains haste until end of turn.
        addCard(Zone.HAND, playerA, "Unnatural Speed", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 2);
        // Destroy target nonartifact, nonblack creature. It can't be regenerated.
        addCard(Zone.HAND, playerB, "Terror", 1); // {1}{B}

        activateAbility(1, PhaseStep.UPKEEP, playerA, "{R}: ");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Terror", "Dragon Whelp");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Reanimate", "Dragon Whelp");
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, "Unnatural Speed", "Dragon Whelp");

        activateAbility(1, PhaseStep.DECLARE_ATTACKERS, playerA, "{R}: ");
        activateAbility(1, PhaseStep.DECLARE_ATTACKERS, playerA, "{R}: ");
        activateAbility(1, PhaseStep.DECLARE_ATTACKERS, playerA, "{R}: ");

        attack(1, playerA, "Dragon Whelp");

        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertGraveyardCount(playerA, "Unnatural Speed", 1);
        assertGraveyardCount(playerA, "Reanimate", 1);

        assertGraveyardCount(playerB, "Terror", 1);

        assertPermanentCount(playerA, "Dragon Whelp", 1);
        assertPowerToughness(playerA, "Dragon Whelp", 2, 3);
        assertGraveyardCount(playerA, "Dragon Whelp", 0);

        assertLife(playerA, 16);
        assertLife(playerB, 15);
    }

    @Test
    public void testDragonWhelpDontSacrificeNewObject() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 8);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        // Flying
        // {R}: Dragon Whelp gets +1/+0 until end of turn. If this ability has been activated four or more times this turn, sacrifice Dragon Whelp at the beginning of the next end step.
        addCard(Zone.BATTLEFIELD, playerA, "Dragon Whelp", 1); // 3/3
        // Put target creature card from a graveyard onto the battlefield under your control. You lose life equal to its converted mana cost.
        addCard(Zone.HAND, playerA, "Reanimate", 1);
        // Target creature gains haste until end of turn.
        addCard(Zone.HAND, playerA, "Unnatural Speed", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 2);
        // Destroy target nonartifact, nonblack creature. It can't be regenerated.
        addCard(Zone.HAND, playerB, "Terror", 1); // {1}{B}

        activateAbility(1, PhaseStep.UPKEEP, playerA, "{R}: ");
        activateAbility(1, PhaseStep.UPKEEP, playerA, "{R}: ");
        activateAbility(1, PhaseStep.UPKEEP, playerA, "{R}: ");
        activateAbility(1, PhaseStep.UPKEEP, playerA, "{R}: ");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Terror", "Dragon Whelp");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Reanimate", "Dragon Whelp");
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, "Unnatural Speed", "Dragon Whelp");

        activateAbility(1, PhaseStep.DECLARE_ATTACKERS, playerA, "{R}: ");
        activateAbility(1, PhaseStep.DECLARE_ATTACKERS, playerA, "{R}: ");
        activateAbility(1, PhaseStep.DECLARE_ATTACKERS, playerA, "{R}: ");

        attack(1, playerA, "Dragon Whelp");

        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertGraveyardCount(playerA, "Unnatural Speed", 1);
        assertGraveyardCount(playerA, "Reanimate", 1);

        assertGraveyardCount(playerB, "Terror", 1);

        assertPermanentCount(playerA, "Dragon Whelp", 1);
        assertPowerToughness(playerA, "Dragon Whelp", 2, 3);
        assertGraveyardCount(playerA, "Dragon Whelp", 0);

        assertLife(playerA, 16);
        assertLife(playerB, 15);
    }
}
