package org.mage.test.cards.single.msh;

import mage.abilities.keyword.HexproofAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author nandmp
 */
public class KidLokiTest extends CardTestPlayerBase {

    @Test
    public void testCounterAddedBeforeKidLokiEntersStillGivesHexproof() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);

        addCard(Zone.HAND, playerA, "Battlegrowth", 2);
        addCard(Zone.HAND, playerA, "Kid Loki", 1);

        // Turn 1: put a +1/+1 counter on Lion.
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Battlegrowth", "Silvercoat Lion");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // Turn 3: put a +1/+1 counter on Bears first, then resolve Kid Loki in the same turn.
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Battlegrowth", "Grizzly Bears");
        waitStackResolved(3, PhaseStep.PRECOMBAT_MAIN);
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Kid Loki");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertCounterCount(playerA, "Grizzly Bears", CounterType.P1P1, 1);
        assertCounterCount(playerA, "Silvercoat Lion", CounterType.P1P1, 1);
        assertAbility(playerA, "Grizzly Bears", HexproofAbility.getInstance(), true);
        assertAbility(playerA, "Silvercoat Lion", HexproofAbility.getInstance(), false);
    }

    @Test
    public void testOpponentAddsCounterNoHexproof() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 1);

        addCard(Zone.HAND, playerA, "Kid Loki", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Forest", 1);
        addCard(Zone.HAND, playerB, "Battlegrowth", 1);

        // Opponent puts the +1/+1 counter on your creature
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Battlegrowth", "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // You cast Kid Loki 
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Kid Loki");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertCounterCount(playerA, "Grizzly Bears", CounterType.P1P1, 1);
        assertAbility(playerA, "Grizzly Bears", HexproofAbility.getInstance(), false);
    }

    @Test
    public void testAddCounterOnOpponentTurnKidLokiGivesHexproof() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Kid Loki", 1);
        addCard(Zone.HAND, playerA, "Ancestral Recall", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 1);
        addCard(Zone.HAND, playerB, "Bloodchief's Thirst", 1);

        // Opponent tries to destroy creature
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Bloodchief's Thirst", "Kid Loki");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Ancestral Recall");
        waitStackResolved(2, PhaseStep.PRECOMBAT_MAIN);

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertCounterCount(playerA, "Kid Loki", CounterType.P1P1, 1);
        assertAbility(playerA, "Kid Loki", HexproofAbility.getInstance(), true);
    }

    @Test
    public void testEntersWithP1P1CounterGainsHexproof() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.HAND, playerA, "Kid Loki", 1);
        addCard(Zone.HAND, playerA, "Stonecoil Serpent", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Stonecoil Serpent");
        setChoice(playerA, "X=1");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Kid Loki");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertCounterCount(playerA, "Stonecoil Serpent", CounterType.P1P1, 1);
        assertAbility(playerA, "Stonecoil Serpent", HexproofAbility.getInstance(), true);
    }

    @Test
    public void testEntersWithP1P1Counter2GainsHexproof() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Renata, Called to the Hunt", 1);
        addCard(Zone.HAND, playerA, "Kid Loki", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Kid Loki");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertCounterCount(playerA, "Kid Loki", CounterType.P1P1, 1);
        assertAbility(playerA, "Kid Loki", HexproofAbility.getInstance(), true);
    }

    @Test
    public void testGetsCounterFromTriggeredAbilityHexproof() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Good-Fortune Unicorn", 1);
        addCard(Zone.HAND, playerA, "Kid Loki", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Kid Loki");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertCounterCount(playerA, "Kid Loki", CounterType.P1P1, 1);
        assertAbility(playerA, "Kid Loki", HexproofAbility.getInstance(), true);
    }

}
