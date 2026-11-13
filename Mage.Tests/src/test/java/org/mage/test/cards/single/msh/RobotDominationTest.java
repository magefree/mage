package org.mage.test.cards.single.msh;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class RobotDominationTest extends CardTestPlayerBase {

    /**
     * Robot Domination {3}{B}
     * Enchantment — Plan
     * Whenever one or more creature cards are put into your graveyard from anywhere, you draw a card, lose 1 life, and
     * put a plan counter on this enchantment.
     * When the third plan counter is put on this enchantment, sacrifice it and create three 2/2 colorless Robot Villain
     * artifact creature tokens.
     */
    private static final String domination = "Robot Domination";

    @Test
    public void test_SeveralCreatureCardsAtOnce_OneTrigger() {
        addCard(Zone.BATTLEFIELD, playerA, domination);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");
        //
        // Destroy all creatures. They can't be regenerated.
        addCard(Zone.HAND, playerA, "Day of Judgment"); // {2}{W}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);

        // two creature cards in one event - one trigger (603.2c)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Day of Judgment");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Grizzly Bears", 2);
        assertGraveyardCount(playerB, "Silvercoat Lion", 1);
        assertHandCount(playerA, 1);
        assertLife(playerA, 20 - 1);
        assertCounterCount(playerA, domination, CounterType.PLAN, 1);
    }

    @Test
    public void test_NoncreatureOrOpponentCreatureCard_NoTrigger() {
        addCard(Zone.BATTLEFIELD, playerA, domination);
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears");
        //
        addCard(Zone.HAND, playerA, "Lightning Bolt"); // {R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        // a noncreature card in your graveyard and a creature card in the opponent's graveyard
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Lightning Bolt", 1);
        assertGraveyardCount(playerB, "Grizzly Bears", 1);
        assertHandCount(playerA, 0);
        assertLife(playerA, 20);
        assertCounterCount(playerA, domination, CounterType.PLAN, 0);
    }

    @Test
    public void test_ThirdCounter_SacrificeAndTokens() {
        addCard(Zone.BATTLEFIELD, playerA, domination);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 3);
        //
        // Sacrifice a creature: Goblin Bombardment deals 1 damage to any target.
        addCard(Zone.BATTLEFIELD, playerA, "Goblin Bombardment");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice a creature", playerB);
        setChoice(playerA, "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice a creature", playerB);
        setChoice(playerA, "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCounters("before third", 1, PhaseStep.PRECOMBAT_MAIN, playerA, domination, CounterType.PLAN, 2);
        checkPermanentCount("before third", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Robot Villain Token", 0);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice a creature", playerB);
        setChoice(playerA, "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, domination, 1);
        assertPermanentCount(playerA, "Robot Villain Token", 3);
        assertHandCount(playerA, 3);
        assertLife(playerA, 20 - 3);
        assertLife(playerB, 20 - 3);
    }

    @Test
    public void test_ThirdCounter_SourceRemovedInResponse_TokensAnyway() {
        addCard(Zone.BATTLEFIELD, playerA, domination);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 3);
        //
        // Sacrifice a creature: Goblin Bombardment deals 1 damage to any target.
        addCard(Zone.BATTLEFIELD, playerA, "Goblin Bombardment");
        //
        addCard(Zone.HAND, playerB, "Disenchant"); // {1}{W}, instant
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice a creature", playerB);
        setChoice(playerA, "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice a creature", playerB);
        setChoice(playerA, "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice a creature", playerB);
        setChoice(playerA, "Grizzly Bears");

        // not "When you do" - the sacrifice is impossible, but the tokens are created (101.3, 608.2c)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Disenchant", domination, "When the third plan counter");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerB, "Disenchant", 1);
        assertGraveyardCount(playerA, domination, 1);
        assertPermanentCount(playerA, "Robot Villain Token", 3);
    }
}
