package org.mage.test.cards.single.grn;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class ThousandYearStormTest extends CardTestPlayerBase {

    /*
    Thousand-Year Storm {4}{U}{R}
    Enchantment
    Whenever you cast an instant or sorcery spell, copy it for each other instant and sorcery spell you’ve cast before it this turn. You may choose new targets for the copies.
     */
    @Test
    public void test_CalcBeforeStorm() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 5);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerA, "Thousand-Year Storm");
        //
        addCard(Zone.BATTLEFIELD, playerB, "Augmenting Automaton");

        // bolt before
        castSpell(1, PhaseStep.UPKEEP, playerA, "Lightning Bolt", playerB);
        checkLife("before", 1, PhaseStep.PRECOMBAT_MAIN, playerB, 20 - 3);

        // storm
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Thousand-Year Storm");

        // bolt after (1 + 1x copy)
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, "Lightning Bolt", playerB);
        setChoice(playerA, false); // change target for copy 1
        checkLife("copy", 1, PhaseStep.END_COMBAT, playerB, 20 - 3 - 3 * 2);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_CalcStackBefore() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 5);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerA, "Thousand-Year Storm");
        //
        addCard(Zone.BATTLEFIELD, playerB, "Augmenting Automaton");

        // bolt stack before
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        // storm
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Thousand-Year Storm");

        // bolt after (1 + 1x copy)
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, "Lightning Bolt", playerB);
        setChoice(playerA, false); // change target for copy 1
        checkLife("copy", 1, PhaseStep.END_COMBAT, playerB, 20 - 3 - 3 * 2);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_CalcStackAfter() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 5);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerA, "Thousand-Year Storm");
        //
        addCard(Zone.BATTLEFIELD, playerB, "Augmenting Automaton");

        // storm
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Thousand-Year Storm");
        // bolt stack after
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);

        // bolt after (1 + 1x copy)
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, "Lightning Bolt", playerB);
        setChoice(playerA, false); // change target for copy 1
        checkLife("copy", 1, PhaseStep.END_COMBAT, playerB, 20 - 3 - 3 * 2);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_CalcAfterStormOnSameStep() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 5);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerA, "Thousand-Year Storm");
        //
        addCard(Zone.BATTLEFIELD, playerB, "Augmenting Automaton");

        // storm
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Thousand-Year Storm");

        // bolt after
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, "Lightning Bolt", playerB);
        // bolt after (1 + 1x copy)
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, "Lightning Bolt", playerB);
        setChoice(playerA, false); // change target for copy 1
        checkLife("copy", 1, PhaseStep.END_COMBAT, playerB, 20 - 3 - 3 * 2);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_CalcAfterStormOnDiffStep() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 5);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerA, "Thousand-Year Storm");
        //
        addCard(Zone.BATTLEFIELD, playerB, "Augmenting Automaton");

        // storm
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Thousand-Year Storm");

        // bolt after
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, "Lightning Bolt", playerB);
        // bolt after (1 + 1x copy)
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        setChoice(playerA, false); // change target for copy 1
        checkLife("copy", 1, PhaseStep.END_TURN, playerB, 20 - 3 - 3 * 2);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_OwnCounts() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 10);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerA, "Thousand-Year Storm");
        //
        addCard(Zone.BATTLEFIELD, playerB, "Augmenting Automaton");

        // turn 1
        int startLife = 100;
        setLife(playerA, 100);
        setLife(playerB, 100);

        // before storm (must counts too)
        castSpell(1, PhaseStep.UPKEEP, playerA, "Lightning Bolt", playerB);
        checkLife("0x copy", 1, PhaseStep.PRECOMBAT_MAIN, playerB, startLife - 3);

        // storm
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Thousand-Year Storm");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        // 1
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        checkLife("1x copy", 1, PhaseStep.BEGIN_COMBAT, playerB, startLife - 3 - 3 * 2);
        setChoice(playerA, false); // change target for copy 1
        // 2
        castSpell(1, PhaseStep.DECLARE_ATTACKERS, playerA, "Lightning Bolt", playerB);
        setChoice(playerA, false); // change target for copy 1
        setChoice(playerA, false); // change target for copy 2
        checkLife("2x copy", 1, PhaseStep.END_COMBAT, playerB, startLife - 3 - 3 * 2 - 3 * 3);
        // 3
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        setChoice(playerA, false); // change target for copy 1
        setChoice(playerA, false); // change target for copy 2
        setChoice(playerA, false); // change target for copy 3
        checkLife("3x copy", 1, PhaseStep.END_TURN, playerB, startLife - 3 - 3 * 2 - 3 * 3 - 3 * 4);

        // turn 3
        startLife = startLife - 3 - 3 * 2 - 3 * 3 - 3 * 4;
        logger.info("start life on turn 3: " + startLife);
        // after storm 0x
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        checkLife("3. no copy", 3, PhaseStep.BEGIN_COMBAT, playerB, startLife - 3);
        // after storm 1x
        castSpell(3, PhaseStep.DECLARE_ATTACKERS, playerA, "Lightning Bolt", playerB);
        setChoice(playerA, false); // change target for copy 1
        checkLife("3. 1x copy", 3, PhaseStep.END_COMBAT, playerB, startLife - 3 - 3 * 2);
        // after storm 2x
        castSpell(3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        setChoice(playerA, false); // change target for copy 1
        setChoice(playerA, false); // change target for copy 2
        checkLife("3. 2x copy", 3, PhaseStep.END_TURN, playerB, startLife - 3 - 3 * 2 - 3 * 3);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_OpponentDontCounts() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 10);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 5);
        addCard(Zone.HAND, playerB, "Lightning Bolt", 10);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Thousand-Year Storm");
        //
        addCard(Zone.BATTLEFIELD, playerB, "Augmenting Automaton");

        // turn 1
        // 1a
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        checkLife("0x copy", 1, PhaseStep.BEGIN_COMBAT, playerB, 20 - 3);
        // 1b
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", playerA);
        checkLife("0x copy", 1, PhaseStep.BEGIN_COMBAT, playerA, 20 - 3);

        // 2a
        castSpell(1, PhaseStep.DECLARE_ATTACKERS, playerA, "Lightning Bolt", playerB);
        setChoice(playerA, false); // change target for copy 1
        checkLife("1x copy", 1, PhaseStep.END_COMBAT, playerB, 20 - 3 - 3 * 2);
        // 2b
        castSpell(1, PhaseStep.DECLARE_ATTACKERS, playerB, "Lightning Bolt", playerA);
        checkLife("0x copy", 1, PhaseStep.END_COMBAT, playerA, 20 - 3 - 3);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_WaitStackResolvedWithBolts() {
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 5);
        addCard(Zone.HAND, playerB, "Lightning Bolt", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);

        castSpell(2, PhaseStep.END_TURN, playerB, "Lightning Bolt", playerA);
        castSpell(2, PhaseStep.END_TURN, playerB, "Lightning Bolt", playerA);
        castSpell(2, PhaseStep.END_TURN, playerA, "Lightning Bolt", playerB);
        waitStackResolved(2, PhaseStep.END_TURN);
        checkLife("A must get damage", 2, PhaseStep.END_TURN, playerA, 20 - 3 - 3);
        checkLife("B must get damage", 2, PhaseStep.END_TURN, playerB, 20 - 3);

        setStrictChooseMode(true);
        setStopAt(4, PhaseStep.END_TURN);
        execute();
    }

    /*
    Lay Claim {5}{U}{U}
    Enchant permanent
    You control enchanted permanent.
    Cycling {2} ({2}, Discard this card: Draw a card.)
     */
    @Test
    public void test_GetControlNotCounts() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 2);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 5);
        addCard(Zone.HAND, playerB, "Lightning Bolt", 3);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Thousand-Year Storm");
        //
        addCard(Zone.BATTLEFIELD, playerB, "Island", 7);
        addCard(Zone.HAND, playerB, "Lay Claim");

        // turn 2
        // pump card for A
        // 1
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        checkLife("a 0x copy", 2, PhaseStep.BEGIN_COMBAT, playerB, 20 - 3);
        // 2
        castSpell(2, PhaseStep.DECLARE_ATTACKERS, playerA, "Lightning Bolt", playerB);
        setChoice(playerA, false); // change target for copy 1
        checkLife("a 1x copy", 2, PhaseStep.END_COMBAT, playerB, 20 - 3 - 3 * 2);

        // change controller to B
        activateManaAbility(2, PhaseStep.POSTCOMBAT_MAIN, playerB, "{T}: Add {U}", 7);
        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lay Claim", "Thousand-Year Storm");
        // cast bolt without pump
        castSpell(2, PhaseStep.END_TURN, playerB, "Lightning Bolt", playerA);
        checkLife("b 0x copy after control", 3, PhaseStep.UPKEEP, playerA, 20 - 3);

        // turn 4
        // pump for B
        // 1
        castSpell(4, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", playerA);
        checkLife("b 0x copy", 4, PhaseStep.BEGIN_COMBAT, playerA, 20 - 3 - 3);
        // 2
        castSpell(4, PhaseStep.DECLARE_ATTACKERS, playerB, "Lightning Bolt", playerA);
        setChoice(playerB, false); // change target for copy 1
        checkLife("b 1x copy", 4, PhaseStep.END_COMBAT, playerA, 20 - 3 - 3 - 3 * 2);

        setStrictChooseMode(true);
        setStopAt(4, PhaseStep.END_TURN);
        execute();
    }
}
