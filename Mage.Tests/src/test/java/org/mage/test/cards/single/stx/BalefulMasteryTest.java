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
        setChoice(playerA, "No"); // use normal cost

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

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
        setChoice(playerA, "Yes"); // use alternative cost
        addTarget(playerA, playerB); // select opponent

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertHandCount(playerA, 0);
        assertHandCount(playerB, 1); // +1 from cost's draw
        assertExileCount(playerB, "Goblin Piker", 1);
    }

    @Test // TODO: must be fixed
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
        setChoice(playerA, "Yes"); // use alternative cost
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
        setChoice(playerA, "No"); // do not use alternative cost
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkGraveyardCount("after cast 2", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Baleful Mastery", 1);
        checkHandCount("after cast 2", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 0);
        checkHandCount("after cast 2", 1, PhaseStep.PRECOMBAT_MAIN, playerB, 1); // no draws on cast 2
        checkExileCount("after cast 2", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Goblin Piker", 1);
        checkExileCount("after cast 2", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", 1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();
    }
}
