package org.mage.test.cards.single.msc;

import mage.constants.ManaType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author DarioBarrabinoDev
 */
public class PhotonMightyMarvelTest extends CardTestPlayerBase {

    @Test
    public void test_AddsThatMuchManaOfChosenColor() {
        // Flying
        // Whenever Photon deals combat damage to a player, add that much mana of any one color.
        // Until end of turn, you don't lose this mana as steps and phases end.
        addCard(Zone.BATTLEFIELD, playerA, "Photon, Mighty Marvel"); // 2/4

        attack(1, playerA, "Photon, Mighty Marvel");
        setChoice(playerA, "Blue"); // color of the added mana

        checkManaPool("2 blue from 2 combat damage", 1, PhaseStep.END_COMBAT, playerA, "U", 2);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerB, 20 - 2);
    }

    @Test
    public void test_ManaCanBeSpentInPostcombatMain() {
        // Flying
        // Whenever Photon deals combat damage to a player, add that much mana of any one color.
        // Until end of turn, you don't lose this mana as steps and phases end.
        addCard(Zone.BATTLEFIELD, playerA, "Photon, Mighty Marvel"); // 2/4
        addCard(Zone.HAND, playerA, "Think Twice"); // {1}{U}, playerA controls no lands

        attack(1, playerA, "Photon, Mighty Marvel");
        setChoice(playerA, "Blue"); // color of the added mana

        checkManaPool("mana survived the combat phase", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "U", 2);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Think Twice");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Think Twice", 1);
        assertManaPool(playerA, ManaType.BLUE, 0); // both blue mana paid for the spell
    }

    @Test
    public void test_ManaLostAtEndOfTurn() {
        // Flying
        // Whenever Photon deals combat damage to a player, add that much mana of any one color.
        // Until end of turn, you don't lose this mana as steps and phases end.
        addCard(Zone.BATTLEFIELD, playerA, "Photon, Mighty Marvel"); // 2/4

        attack(1, playerA, "Photon, Mighty Marvel");
        setChoice(playerA, "Blue"); // color of the added mana

        checkManaPool("still there at end of turn", 1, PhaseStep.END_TURN, playerA, "U", 2);
        checkManaPool("gone on the next turn", 2, PhaseStep.UPKEEP, playerA, "U", 0);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertManaPool(playerA, ManaType.BLUE, 0);
    }

    @Test
    public void test_ManaFollowsBoostedPower() {
        // Flying
        // Whenever Photon deals combat damage to a player, add that much mana of any one color.
        // Until end of turn, you don't lose this mana as steps and phases end.
        addCard(Zone.BATTLEFIELD, playerA, "Photon, Mighty Marvel"); // 2/4

        addCounters(1, PhaseStep.UPKEEP, playerA, "Photon, Mighty Marvel", CounterType.P1P1, 2); // 4/6
        attack(1, playerA, "Photon, Mighty Marvel");
        setChoice(playerA, "Red"); // color of the added mana

        checkManaPool("4 red from 4 combat damage", 1, PhaseStep.END_COMBAT, playerA, "R", 4);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerB, 20 - 4);
    }
}
