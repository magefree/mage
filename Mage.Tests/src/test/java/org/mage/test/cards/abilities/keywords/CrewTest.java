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

import mage.abilities.keyword.HasteAbility;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
// http://magic.wizards.com/en/articles/archive/feature/kaladesh-mechanics-2016-09-02
public class CrewTest extends CardTestPlayerBase {

    @Test
    public void crewBasicTest() {
        // {T}: Add one mana of any color to your mana pool.
        // Crew 3 (Tap any number of creatures you control with total power 3 or more: This Vehicle becomes an artifact creature until end of turn.)";
        addCard(Zone.BATTLEFIELD, playerA, "Cultivator's Caravan", 1);

        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 2);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Crew 3");
        setChoice(playerA, "Silvercoat Lion^Silvercoat Lion");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertTappedCount("Silvercoat Lion", true, 2);
        assertPowerToughness(playerA, "Cultivator's Caravan", 5, 5);
        assertType("Cultivator's Caravan", CardType.CREATURE, SubType.VEHICLE);
    }

    @Test
    public void crewTriggerPilotTest() {
        // Flying
        // Whenever Smuggler's Copter attacks or blocks, you may draw a card. If you do, discard a card.
        // Crew 1 (Tap any number of creatures you control with total power 3 or more: This Vehicle becomes an artifact creature until end of turn.)";
        addCard(Zone.BATTLEFIELD, playerA, "Smuggler's Copter", 1);

        addCard(Zone.BATTLEFIELD, playerA, "Speedway Fanatic", 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Crew 1");
        setChoice(playerA, "Speedway Fanatic");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertTappedCount("Speedway Fanatic", true, 1);
        assertPowerToughness(playerA, "Smuggler's Copter", 3, 3);
        assertAbility(playerA, "Smuggler's Copter", HasteAbility.getInstance(), true);
        assertType("Smuggler's Copter", CardType.CREATURE, SubType.VEHICLE);
    }

    @Test
    public void testThatBouncingACrewedVehicleWillUncrewIt() {
        addCard(Zone.BATTLEFIELD, playerA, "Smuggler's Copter", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Speedway Fanatic", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 7);
        addCard(Zone.HAND, playerA, "Evacuation", 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Crew 1");
        setChoice(playerA, "Speedway Fanatic");

        // Return all creatures to there owners hands
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, "Evacuation");

        // (Re)Cast Smugglers Copter
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Smuggler's Copter");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // Only crewed vehicles have card type creature
        assertNotType("Smuggler's Copter", CardType.CREATURE);
    }

}
