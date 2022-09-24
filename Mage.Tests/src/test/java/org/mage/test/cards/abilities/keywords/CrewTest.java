
package org.mage.test.cards.abilities.keywords;

import mage.abilities.keyword.HasteAbility;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2
 */
// http://magic.wizards.com/en/articles/archive/feature/kaladesh-mechanics-2016-09-02
public class CrewTest extends CardTestPlayerBase {

    private static final String lion = "Silvercoat Lion";
    private static final String caravan = "Cultivator's Caravan";
    private static final String fanatic = "Speedway Fanatic";
    private static final String copter = "Smuggler's Copter";
    private static final String evacuation = "Evacuation";
    private static final String ox = "Giant Ox";
    private static final String plow = "Colossal Plow";
    private static final String kotori = "Kotori, Pilot Prodigy";
    private static final String crusher = "Irontread Crusher";
    private static final String mechanic = "Hotshot Mechanic";
    private static final String express = "Aradara Express";
    private static final String heart = "Heart of Kiran";
    private static final String jace = "Jace Beleren";

    @Test
    public void crewBasicTest() {
        // {T}: Add one mana of any color.
        // Crew 3 (Tap any number of creatures you control with total power 3 or more: This Vehicle becomes an artifact creature until end of turn.)";
        addCard(Zone.BATTLEFIELD, playerA, caravan, 1);

        addCard(Zone.BATTLEFIELD, playerA, lion, 2);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Crew 3");
        setChoice(playerA, lion + "^" + lion);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertTappedCount(lion, true, 2);
        assertPowerToughness(playerA, caravan, 5, 5);
        assertType(caravan, CardType.CREATURE, SubType.VEHICLE);
    }

    @Test
    public void crewTriggerPilotTest() {
        // Flying
        // Whenever Smuggler's Copter attacks or blocks, you may draw a card. If you do, discard a card.
        // Crew 1 (Tap any number of creatures you control with total power 3 or more: This Vehicle becomes an artifact creature until end of turn.)";
        addCard(Zone.BATTLEFIELD, playerA, copter, 1);

        addCard(Zone.BATTLEFIELD, playerA, fanatic, 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Crew 1");
        setChoice(playerA, fanatic);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertTappedCount(fanatic, true, 1);
        assertPowerToughness(playerA, copter, 3, 3);
        assertAbility(playerA, copter, HasteAbility.getInstance(), true);
        assertType(copter, CardType.CREATURE, SubType.VEHICLE);
    }

    @Test
    public void testThatBouncingACrewedVehicleWillUncrewIt() {
        addCard(Zone.BATTLEFIELD, playerA, copter, 1);
        addCard(Zone.BATTLEFIELD, playerA, fanatic, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 7);
        addCard(Zone.HAND, playerA, evacuation, 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Crew 1");
        setChoice(playerA, fanatic);

        // Return all creatures to there owners hands
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, evacuation);

        // (Re)Cast Smugglers Copter
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, copter);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // Only crewed vehicles have card type creature
        assertNotType(copter, CardType.CREATURE);
    }

    @Test
    public void testGiantOx() {
        addCard(Zone.BATTLEFIELD, playerA, ox);
        addCard(Zone.BATTLEFIELD, playerA, plow);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Crew");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertTapped(ox, true);
        assertType(plow, CardType.CREATURE, true);
    }

    @Test
    public void testGrantedAbility() {
        addCard(Zone.BATTLEFIELD, playerA, kotori);
        addCard(Zone.BATTLEFIELD, playerA, crusher);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Crew 2");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertTapped(kotori, true);
        assertType(crusher, CardType.ARTIFACT, true);
        assertType(crusher, CardType.CREATURE, SubType.VEHICLE);
    }

    @Test
    public void testHotshotMechanic() {
        addCard(Zone.BATTLEFIELD, playerA, mechanic);
        addCard(Zone.BATTLEFIELD, playerA, express);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Crew 4");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertTapped(mechanic, true);
        assertType(express, CardType.ARTIFACT, true);
        assertType(express, CardType.CREATURE, SubType.VEHICLE);
    }

    @Test
    public void testHeartOfKiran() {
        addCard(Zone.BATTLEFIELD, playerA, jace);
        addCard(Zone.BATTLEFIELD, playerA, heart);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Crew 3");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerA, jace, CounterType.LOYALTY, 2);
        assertType(heart, CardType.ARTIFACT, true);
        assertType(heart, CardType.CREATURE, SubType.VEHICLE);
    }
}
