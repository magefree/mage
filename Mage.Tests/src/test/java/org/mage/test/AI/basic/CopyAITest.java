package org.mage.test.AI.basic;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBaseWithAIHelps;

/**
 * @author JayDi85
 */
public class CopyAITest extends CardTestPlayerBaseWithAIHelps {

    // AI makes decisions by two different modes:
    // 1. Simulation: If it searching playable spells then it play it in FULL SIMULATION (abilities + all possible targets)
    // 2. Response: If it searching response on dialog then it use simple target search (without simulation)

    @Test
    public void test_CloneChoose_Manual() {
        // You may have Clone enter the battlefield as a copy of any creature on the battlefield.
        addCard(Zone.HAND, playerA, "Clone", 1); // {3}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Arbor Elf", 1); // 1/1
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears", 1); // 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Spectral Bears", 1); // 3/3

        // clone
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Clone");
        setChoice(playerA, true);
        setChoice(playerA, "Spectral Bears");

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, "Spectral Bears", 1);
        assertPermanentCount(playerB, "Spectral Bears", 1);
    }

    @Test
    public void test_CloneChoose_AI_Simulation_MostValueableFromOwn() {
        // You may have Clone enter the battlefield as a copy of any creature on the battlefield.
        addCard(Zone.HAND, playerA, "Clone", 1); // {3}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Arbor Elf", 1); // 1/1
        addCard(Zone.BATTLEFIELD, playerA, "Spectral Bears", 1); // 3/3
        //
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears", 1); // 2/2

        // clone (AI must choose most valueable permanent - own)
        aiPlayPriority(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, "Spectral Bears", 2);
        assertPermanentCount(playerB, "Spectral Bears", 0);
    }

    @Test
    public void test_CloneChoose_AI_Simulation_MostValueableFromOpponent() {
        // You may have Clone enter the battlefield as a copy of any creature on the battlefield.
        addCard(Zone.HAND, playerA, "Clone", 1); // {3}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Arbor Elf", 1); // 1/1
        //
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears", 1); // 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Spectral Bears", 1); // 3/3

        // clone (AI must choose most valueable permanent - opponent)
        aiPlayPriority(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, "Spectral Bears", 1);
        assertPermanentCount(playerB, "Spectral Bears", 1);
    }

    @Test
    public void test_CopyTarget_Manual() {
        // Exile target creature card from a graveyard. Dimir Doppelganger becomes a copy of that card, except it has this ability.
        addCard(Zone.BATTLEFIELD, playerA, "Dimir Doppelganger", 1); // {1}{U}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        //
        addCard(Zone.GRAVEYARD, playerA, "Arbor Elf", 1); // 1/1
        addCard(Zone.GRAVEYARD, playerB, "Balduvian Bears", 1); // 2/2
        addCard(Zone.GRAVEYARD, playerB, "Spectral Bears", 1); // 3/3

        // copy
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{U}{B}: Exile target");
        addTarget(playerA, "Spectral Bears");

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, "Spectral Bears", 1);
        assertExileCount("Spectral Bears", 1);
    }

    @Test
    public void test_CopyTarget_AI_Simulation_MostValueableFromOwn() {
        // Exile target creature card from a graveyard. Dimir Doppelganger becomes a copy of that card, except it has this ability.
        addCard(Zone.BATTLEFIELD, playerA, "Dimir Doppelganger", 1); // {1}{U}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        //
        addCard(Zone.GRAVEYARD, playerA, "Arbor Elf", 1); // 1/1
        addCard(Zone.GRAVEYARD, playerA, "Spectral Bears", 1); // 3/3
        //
        addCard(Zone.GRAVEYARD, playerB, "Balduvian Bears", 1); // 2/2

        // copy (AI must choose most valueable permanent - own)
        aiPlayPriority(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, "Spectral Bears", 1);
        assertExileCount("Spectral Bears", 1);
    }

    @Test
    public void test_CopyTarget_AI_Simulation_MostValueableFromOpponent() {
        // Exile target creature card from a graveyard. Dimir Doppelganger becomes a copy of that card, except it has this ability.
        addCard(Zone.BATTLEFIELD, playerA, "Dimir Doppelganger", 1); // {1}{U}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        //
        addCard(Zone.GRAVEYARD, playerA, "Arbor Elf", 1); // 1/1
        //
        addCard(Zone.GRAVEYARD, playerB, "Balduvian Bears", 1); // 2/2
        addCard(Zone.GRAVEYARD, playerB, "Spectral Bears", 1); // 3/3

        // copy (AI must choose most valueable permanent - opponent)
        aiPlayPriority(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, "Spectral Bears", 1);
        assertExileCount("Spectral Bears", 1);
    }

    @Test
    public void test_CopyTarget_AI_Response_MostValueableFromOwn() {
        // Exile target creature card from a graveyard. Dimir Doppelganger becomes a copy of that card, except it has this ability.
        addCard(Zone.BATTLEFIELD, playerA, "Dimir Doppelganger", 1); // {1}{U}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        //
        addCard(Zone.GRAVEYARD, playerA, "Arbor Elf", 1); // 1/1
        addCard(Zone.GRAVEYARD, playerA, "Spectral Bears", 1); // 3/3
        //
        addCard(Zone.GRAVEYARD, playerB, "Balduvian Bears", 1); // 2/2

        // copy (AI must choose most valueable permanent - own)
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{U}{B}: Exile target");
        //addTarget(playerA, "Spectral Bears"); // AI must choose

        setStopAt(1, PhaseStep.END_TURN);
        //setStrictChooseMode(true); // AI must choose
        execute();

        assertPermanentCount(playerA, "Spectral Bears", 1);
        assertExileCount("Spectral Bears", 1);
    }

    @Test
    public void test_CopyTarget_AI_Response_MostValueableFromOpponent() {
        // Exile target creature card from a graveyard. Dimir Doppelganger becomes a copy of that card, except it has this ability.
        addCard(Zone.BATTLEFIELD, playerA, "Dimir Doppelganger", 1); // {1}{U}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        //
        addCard(Zone.GRAVEYARD, playerA, "Arbor Elf", 1); // 1/1
        //
        addCard(Zone.GRAVEYARD, playerB, "Balduvian Bears", 1); // 2/2
        addCard(Zone.GRAVEYARD, playerB, "Spectral Bears", 1); // 3/3

        // copy (AI must choose most valueable permanent - opponent)
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{U}{B}: Exile target");
        //addTarget(playerA, "Spectral Bears"); // AI must choose

        setStopAt(1, PhaseStep.END_TURN);
        //setStrictChooseMode(true); // AI must choose
        execute();

        assertPermanentCount(playerA, "Spectral Bears", 1);
        assertExileCount("Spectral Bears", 1);
    }
}
