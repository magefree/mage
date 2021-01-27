
package org.mage.test.cards.abilities.keywords;

import mage.abilities.keyword.HasteAbility;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2
 */
// http://magic.wizards.com/en/articles/archive/feature/kaladesh-mechanics-2016-09-02
public class CrewTest extends CardTestPlayerBase {

    @Test
    public void crewBasicTest() {
        // {T}: Add one mana of any color.
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

    @Test
    public void testGiantOx() {
        addCard(Zone.BATTLEFIELD, playerA, "Giant Ox");
        addCard(Zone.BATTLEFIELD, playerA, "Colossal Plow");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Crew");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertTapped("Giant Ox", true);
        assertType("Colossal Plow", CardType.CREATURE, true);
    }
}
