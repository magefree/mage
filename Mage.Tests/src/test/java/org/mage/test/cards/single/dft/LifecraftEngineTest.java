package org.mage.test.cards.single.dft;

import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class LifecraftEngineTest extends CardTestPlayerBase {

    /*
    Lifecraft Engine {3}
    Artifact — Vehicle
    As this Vehicle enters, choose a creature type.
    Vehicle creatures you control are the chosen creature type in addition to their other types.
    Each creature you control of the chosen type other than this Vehicle gets +1/+1.
    Crew 3
    4/4
     */
    private static final String engine = "Lifecraft Engine";

    // Engine resolves before the aura, so its layer-4 effect has the earlier timestamp.
    @Test
    public void testChosenTypeAfterVehicleBecomesCreature() {
        addCard(Zone.HAND, playerA, engine);
        addCard(Zone.HAND, playerA, "Aerial Modification");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 8);
        addCard(Zone.BATTLEFIELD, playerA, "Shorikai, Genesis Engine");

        setStrictChooseMode(true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, engine, true);
        setChoice(playerA, "Dinosaur");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Aerial Modification", "Shorikai, Genesis Engine");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertType("Shorikai, Genesis Engine", CardType.CREATURE, true);
        assertSubtype("Shorikai, Genesis Engine", SubType.VEHICLE);
        assertSubtype("Shorikai, Genesis Engine", SubType.DINOSAUR);
        // Printed 8/8, aura +2/+2, Engine +1/+1.
        assertPowerToughness(playerA, "Shorikai, Genesis Engine", 11, 11);
    }
}
