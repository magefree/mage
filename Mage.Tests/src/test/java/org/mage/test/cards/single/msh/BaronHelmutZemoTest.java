package org.mage.test.cards.single.msh;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;

import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author JayDi85
 */
public class BaronHelmutZemoTest extends CardTestPlayerBase {

    @Test
    public void test_Connivies() {
        skipInitShuffling();

        // Whenever you cast a black spell from your hand, Baron Helmut Zemo connives.
        //
        // Boast — Exile any number of black cards from your graveyard with fifteen or more black mana symbols 
        // among their mana costs: Copy those exiled cards. You may cast up to three of the copies without paying 
        // their mana costs. (Activate only if this creature attacked this turn and only once each turn.)
        addCard(Zone.BATTLEFIELD, playerA, "Baron Helmut Zemo");
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears");
        //
        // Taste of Blood deals 1 damage to target player or planeswalker and you gain 1 life.
        addCard(Zone.HAND, playerA, "Taste of Blood"); // {B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Taste of Blood");
        addTarget(playerA, playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 21);
        assertLife(playerB, 19);
        assertCounterCount("Baron Helmut Zemo", CounterType.P1P1, 1);
    }

    @Test
    public void test_Boast() {
        skipInitShuffling();

        // Whenever you cast a black spell from your hand, Baron Helmut Zemo connives.
        //
        // Boast — Exile any number of black cards from your graveyard with fifteen or more black mana symbols 
        // among their mana costs: Copy those exiled cards. You may cast up to three of the copies without paying 
        // their mana costs. (Activate only if this creature attacked this turn and only once each turn.)
        addCard(Zone.BATTLEFIELD, playerA, "Baron Helmut Zemo");
        addCard(Zone.GRAVEYARD, playerA, "Abomination", 10); // {3}{B}{B}
        //
        // Taste of Blood deals 1 damage to target player or planeswalker and you gain 1 life.
        addCard(Zone.GRAVEYARD, playerA, "Taste of Blood", 3); // {B}

        checkPlayableAbility("before attack must not have boast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Boast", false);
        attack(1, playerA, "Baron Helmut Zemo");
        checkPlayableAbility("after attack must have boast", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Boast", true);

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Boast");
        setChoice(playerA, "Abomination", 10 - 2);
        setChoice(playerA, "Taste of Blood", 3);
        setChoice(playerA, TestPlayer.CHOICE_SKIP); // stop to cards choose
        // cast free 1
        setChoice(playerA, "Taste of Blood"); // cast free - choose card
        setChoice(playerA, true); // cast free - confirm
        addTarget(playerA, playerB); // cast free - target damage
        // cast free 2
        setChoice(playerA, "Taste of Blood"); // cast free - choose card
        setChoice(playerA, true); // cast free - confirm
        addTarget(playerA, playerB); // cast free - target damage
        // cast free 3
        setChoice(playerA, "Taste of Blood"); // cast free - choose card
        setChoice(playerA, true); // cast free - confirm
        addTarget(playerA, playerB); // cast free - target damage

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20 + 3);
        assertLife(playerB, 20 - 3 - 3); // 3 from attack, 3 from x3 free casts
    }
}
