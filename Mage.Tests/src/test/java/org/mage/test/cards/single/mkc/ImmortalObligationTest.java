package org.mage.test.cards.single.mkc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author brahle
 */
public class ImmortalObligationTest extends CardTestPlayerBase {

    @Test
    public void testReanimateWithDutyCounter() {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.HAND, playerA, "Immortal Obligation");
        addCard(Zone.GRAVEYARD, playerB, "Centaur Courser");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Immortal Obligation", "Centaur Courser");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, "Centaur Courser", 1);
        assertCounterCount(playerB, "Centaur Courser", CounterType.DUTY, 1);
    }

    @Test
    public void testCannotAttackYouWhileDutyCounterOnIt() {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.HAND, playerA, "Immortal Obligation");
        addCard(Zone.GRAVEYARD, playerB, "Centaur Courser");

        // Player A reanimates Centaur Courser under Player B's control on Turn 1
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Immortal Obligation", "Centaur Courser");

        // Turn 2: Player B's turn. Centaur Courser is goaded but cannot attack Player A
        checkMayAttackDefender("Centaur Courser cannot attack Player A", 2, playerB, "Centaur Courser", playerA, false);

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertTapped("Centaur Courser", false);
    }

    @Test
    public void testCannotBlockCreaturesYouControlWhileDutyCounterOnIt() {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.HAND, playerA, "Immortal Obligation");
        addCard(Zone.GRAVEYARD, playerB, "Centaur Courser");

        // Turn 1: Reanimate Centaur Courser under Player B's control
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Immortal Obligation", "Centaur Courser");

        // Player A attacks with Grizzly Bears; Player B attempts to block with Centaur Courser
        attack(1, playerA, "Grizzly Bears");
        block(1, playerB, "Centaur Courser", "Grizzly Bears");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        // Centaur Courser cannot block Grizzly Bears, so Grizzly Bears survives and deals 2 damage to Player B
        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertLife(playerB, 18);
    }

    @Test
    public void testCanAttackAndBlockWhenDutyCounterRemoved() {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.HAND, playerA, "Immortal Obligation");
        addCard(Zone.GRAVEYARD, playerB, "Centaur Courser");
        addCard(Zone.BATTLEFIELD, playerB, "Vampire Hexmage");

        // Turn 1: Reanimate Centaur Courser under Player B's control
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Immortal Obligation", "Centaur Courser");

        // Turn 2: Remove duty counter with Vampire Hexmage
        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Sacrifice {this}: Remove all counters from target permanent.", "Centaur Courser");

        // Now Centaur Courser can attack Player A
        checkMayAttackDefender("Centaur Courser can attack Player A once duty counter is removed", 2, playerB, "Centaur Courser", playerA, true);
        attack(2, playerB, "Centaur Courser", playerA);

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertCounterCount(playerB, "Centaur Courser", CounterType.DUTY, 0);
        assertLife(playerA, 17); // Took 3 damage from Centaur Courser
    }
}
