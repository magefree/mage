package org.mage.test.cards.single.fdn;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class RiteOfPassageTest extends CardTestPlayerBase {


    @Test
    public void addCounterNonLethal(){
        // Watchwolf 3/3
        addCard(Zone.BATTLEFIELD, playerA, "Watchwolf", 1);
        // Whenever a creature you control is dealt damage, put a +1/+1 counter on it.
        addCard(Zone.BATTLEFIELD, playerA, "Rite of Passage");
        // Shock - deal 2 damage to any target
        addCard(Zone.HAND, playerA, "Shock");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA,"Shock","Watchwolf");
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertCounterCount("Watchwolf", CounterType.P1P1, 1);
    }

    @Test
    public void addCounterNonLethalOppControlsVorinclex(){
        // Watchwolf 3/3
        addCard(Zone.BATTLEFIELD, playerA, "Watchwolf", 1);
        // Whenever a creature you control is dealt damage, put a +1/+1 counter on it.
        addCard(Zone.BATTLEFIELD, playerA, "Rite of Passage");
        // Shock - deal 2 damage to any target
        addCard(Zone.HAND, playerA, "Shock");
        // Vorinclex
        //If an opponent would put one or more counters on a permanent or player,
        // they put half that many of each of those kinds of counters on that permanent or player instead, rounded down.
        addCard(Zone.BATTLEFIELD, playerB, "Vorinclex, Monstrous Raider");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA,"Shock","Watchwolf");
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertCounterCount("Watchwolf", CounterType.P1P1, 0);
    }
}
