package org.mage.test.cards.single.stx;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author htrajan, JayDi85
 */
public class BalefulMasteryTest extends CardTestPlayerBase {

    @Test
    public void test_BalefulMastery_NormalCost() {
        // You may pay {1}{B} rather than pay this spell's mana cost.
        // If the {1}{B} cost was paid, an opponent draws a card.
        // Exile target creature or planeswalker.
        addCard(Zone.HAND, playerA, "Baleful Mastery"); // {3}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Goblin Piker");
        addCard(Zone.BATTLEFIELD, playerB, "Witchbane Orb");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Baleful Mastery", "Goblin Piker");
        setChoice(playerA, false); // use normal cost

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, 0);
        assertHandCount(playerB, 0);
        assertExileCount(playerB, "Goblin Piker", 1);
    }

    @Test
    public void test_BalefulMastery_AlternativeCost() {
        // You may pay {1}{B} rather than pay this spell's mana cost.
        // If the {1}{B} cost was paid, an opponent draws a card.
        // Exile target creature or planeswalker.
        addCard(Zone.HAND, playerA, "Baleful Mastery"); // {3}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Goblin Piker");
        addCard(Zone.BATTLEFIELD, playerB, "Witchbane Orb");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Baleful Mastery", "Goblin Piker");
        setChoice(playerA, true); // use alternative cost
        addTarget(playerA, playerB); // select opponent

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, 0);
        assertHandCount(playerB, 1); // +1 from cost's draw
        assertExileCount(playerB, "Goblin Piker", 1);
    }

    @Test
    public void test_BalefulMastery_DoubleCast() {
        // You may pay {1}{B} rather than pay this spell's mana cost.
        // If the {1}{B} cost was paid, an opponent draws a card.
        // Exile target creature or planeswalker.
        addCard(Zone.HAND, playerA, "Baleful Mastery", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2 + 4); // 1x normal, 1x alternative
        //
        addCard(Zone.BATTLEFIELD, playerB, "Goblin Piker");
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears");

        // cast 1 - alternative
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Baleful Mastery", "Goblin Piker");
        setChoice(playerA, true); // use alternative cost
        addTarget(playerA, playerB); // select opponent

        // cast 2 - normal
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Baleful Mastery", "Grizzly Bears");
        setChoice(playerA, false); // normal cast

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, 0);
        assertHandCount(playerB, 1);
        assertExileCount(playerB, "Goblin Piker", 1);
        assertExileCount(playerB, "Grizzly Bears", 1);
    }

    @Test
    public void test_BalefulMastery_BlinkMustResetAlternativeCost() {
        addCustomEffect_ReturnFromAnyToHand(playerA);

        // You may pay {1}{B} rather than pay this spell's mana cost.
        // If the {1}{B} cost was paid, an opponent draws a card.
        // Exile target creature or planeswalker.
        addCard(Zone.HAND, playerA, "Baleful Mastery"); // {3}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2 + 4); // 1x normal, 1x alternative
        //
        addCard(Zone.BATTLEFIELD, playerB, "Goblin Piker");
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears");

        // cast 1 - with alternative
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Baleful Mastery", "Goblin Piker");
        setChoice(playerA, true); // use alternative cost
        addTarget(playerA, playerB); // select opponent
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkGraveyardCount("after cast 1", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Baleful Mastery", 1);
        checkHandCount("after cast 1", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 0);
        checkHandCount("after cast 1", 1, PhaseStep.PRECOMBAT_MAIN, playerB, 1); // +1 from cost's draw
        checkExileCount("after cast 1", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Goblin Piker", 1);
        checkExileCount("after cast 1", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", 0);

        // return to hand
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "return from graveyard");
        addTarget(playerA, "Baleful Mastery");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkHandCardCount("after return", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Baleful Mastery", 1);

        // cast 2 - without alternative
        // possible bug: cost status can be found from previous object (e.g. it ask about opponent select, but must not)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Baleful Mastery", "Grizzly Bears");
        setChoice(playerA, false); // do not use alternative cost
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkGraveyardCount("after cast 2", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Baleful Mastery", 1);
        checkHandCount("after cast 2", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 0);
        checkHandCount("after cast 2", 1, PhaseStep.PRECOMBAT_MAIN, playerB, 1); // no draws on cast 2
        checkExileCount("after cast 2", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Goblin Piker", 1);
        checkExileCount("after cast 2", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", 1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_BalefulMastery_CopyMustKeepAlternativeCost() {
        // You may pay {1}{B} rather than pay this spell's mana cost.
        // If the {1}{B} cost was paid, an opponent draws a card.
        // Exile target creature or planeswalker.
        addCard(Zone.HAND, playerA, "Baleful Mastery");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        //
        // Copy target instant or sorcery spell. You may choose new targets for the copy.
        addCard(Zone.HAND, playerA, "Twincast"); // {U}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Goblin Piker");
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears");

        // cast with alternative
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {B}", 2);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Baleful Mastery", "Goblin Piker");
        setChoice(playerA, true); // use alternative cost
        // copy spell
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Twincast", "Cast Baleful Mastery", "Cast Baleful Mastery");
        setChoice(playerA, true); // change target
        addTarget(playerA, "Grizzly Bears"); // new target
        checkStackSize("before copy", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 2);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA, true);
        checkStackSize("after copy", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 2);
        //
        // resolve copied spell
        // possible bug: alternative cost will be lost for copied spell, so no opponent selections
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA, true);
        addTarget(playerA, playerB); // select opponent
        checkStackSize("after copy resolve", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 1);
        // resolve original spell
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA, true);
        addTarget(playerA, playerB); // select opponent
        checkStackSize("after original resolve", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 0);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }
}
