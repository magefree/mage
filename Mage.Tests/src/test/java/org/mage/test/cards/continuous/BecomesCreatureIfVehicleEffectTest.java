package org.mage.test.cards.continuous;

import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class BecomesCreatureIfVehicleEffectTest extends CardTestPlayerBase {

    /*
    Aerial Modification
    {4}{W}
    Enchantment — Aura
    Enchant creature or Vehicle
    As long as enchanted permanent is a Vehicle, it’s a creature in addition to its other types.
    Enchanted creature gets +2/+2 and has flying.
     */
    String aerialMod = "Aerial Modification";
    /*
    Goliath Truck
    {4}
    Artifact — Vehicle
    Stowage — Whenever this Vehicle attacks, put two +1/+1 counters on another target attacking creature.
    Crew 2 (Tap any number of creatures you control with total power 2 or more: This Vehicle becomes an artifact creature until end of turn.)
    4/4
     */
    String goliathTruck = "Goliath Truck";

    @Test
    public void testBecomesCreatureIfVehicleEffect() {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, goliathTruck);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.HAND, playerA, aerialMod);

        checkType("Goliath Truck is not a creature", 1, PhaseStep.PRECOMBAT_MAIN, playerA, goliathTruck, CardType.CREATURE, false);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, aerialMod, goliathTruck);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertType(goliathTruck, CardType.CREATURE, true);
    }
}
