package org.mage.test.multiplayer;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommander4PlayersWithAIHelps;

/**
 * @author TheElk801
 */
public class VotingTest extends CardTestCommander4PlayersWithAIHelps {

    // Player order: A -> D -> C -> B

    // Council’s dilemma — When Lieutenants of the Guard enters the battlefield, starting with you,
    // each player votes for strength or numbers. Put a +1/+1 counter on Lieutenants of the Guard
    // for each strength vote and create a 1/1 white Soldier creature token for each numbers vote.
    private static final String lieutenant = "Lieutenants of the Guard";

    // While voting, you get an additional vote. (The votes can be for different choices or for the same choice.)
    private static final String rep = "Brago's Representative";

    // While voting, you may vote an additional time. (The votes can be for different choices or for the same choice.)
    private static final String broker = "Ballot Broker";

    // You choose how each player votes this turn.
    private static final String illusion = "Illusion of Choice";

    // TODO: add test with broker
    // rulues:
    // The ability only affects spells and abilities that use the word “vote.” Other cards that involve choices,
    // such as Archangel of Strife, are unaffected.
    // (2016-08-23)

    // Whenever players finish voting, each opponent who voted for a choice you didn’t vote for loses 2 life.
    private static final String keeper = "Grudge Keeper";

    // Will of the council - Starting with you, each player votes for death or torture. If death gets more votes,
    // each opponent sacrifices a creature. If torture gets more votes or the vote is tied, each opponent loses 4 life.
    private static final String tyrant = "Tyrant's Choice";

    private void setChoices(String choice) {
        setChoices(choice, choice, choice, choice);
    }

    private void setChoices(String choiceA, String choiceB, String choiceC, String choiceD) {
        setChoice(playerA, choiceA);
        setChoice(playerB, choiceB);
        setChoice(playerC, choiceC);
        setChoice(playerD, choiceD);
    }

    @Test
    public void test_LieutenantsOfTheGuard_1() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.HAND, playerA, lieutenant);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, lieutenant);
        setChoices("Yes");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPowerToughness(playerA, lieutenant, 6, 6);
        assertPermanentCount(playerA, "Soldier Token", 0);
    }

    @Test
    public void test_LieutenantsOfTheGuard_2() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.HAND, playerA, lieutenant);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, lieutenant);
        setChoices("No");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPowerToughness(playerA, lieutenant, 2, 2);
        assertPermanentCount(playerA, "Soldier Token", 4);
    }

    @Test
    public void test_LieutenantsOfTheGuard_3() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.HAND, playerA, lieutenant);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, lieutenant);
        setChoices("Yes", "Yes", "No", "No");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPowerToughness(playerA, lieutenant, 4, 4);
        assertPermanentCount(playerA, "Soldier Token", 2);
    }

    @Test
    public void test_TyrantsChoice_AI_Normal() {
        addCard(Zone.HAND, playerA, tyrant); // {1}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);

        // ai play
        // opponents must have more votes so final result is sacrifice (best for opponents)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, tyrant);
        aiPlayPriority(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        aiPlayPriority(1, PhaseStep.PRECOMBAT_MAIN, playerB);
        aiPlayPriority(1, PhaseStep.PRECOMBAT_MAIN, playerC);
        aiPlayPriority(1, PhaseStep.PRECOMBAT_MAIN, playerD);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertGraveyardCount(playerA, tyrant, 1);
        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertLife(playerC, 20);
        assertLife(playerD, 20);
    }

    @Test
    public void test_TyrantsChoice_AI_UnderControl() {
        addCard(Zone.HAND, playerA, tyrant); // {1}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        //
        addCard(Zone.HAND, playerA, illusion, 1); // {U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);

        // prepare vote control
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, illusion);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkGraveyardCount("prepare", 1, PhaseStep.PRECOMBAT_MAIN, playerA, illusion, 1);

        // ai play
        // you control the opponents, so votes result must be lose life (best for controller)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, tyrant);
        checkStackSize("before resolve", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 1);
        aiPlayPriority(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        aiPlayPriority(1, PhaseStep.PRECOMBAT_MAIN, playerB);
        aiPlayPriority(1, PhaseStep.PRECOMBAT_MAIN, playerC);
        aiPlayPriority(1, PhaseStep.PRECOMBAT_MAIN, playerD);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertGraveyardCount(playerA, tyrant, 1);
        assertLife(playerA, 20);
        assertLife(playerB, 20 - 4);
        assertLife(playerC, 20 - 4);
        assertLife(playerD, 20 - 4);
    }

    @Test
    public void test_BragosRepresentative() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerA, rep);
        addCard(Zone.HAND, playerA, lieutenant);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, lieutenant);
        setChoice(playerA, true);
        setChoices("Yes", "Yes", "No", "No");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPowerToughness(playerA, lieutenant, 5, 5);
        assertPermanentCount(playerA, "Soldier Token", 2);
    }

    @Test
    public void test_BallotBroker() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerA, broker);
        addCard(Zone.HAND, playerA, lieutenant);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, lieutenant);
        setChoices("Yes", "Yes", "No", "No");
        setChoice(playerA, true); // to have an additional vote
        setChoice(playerA, false); // the additional vote

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPowerToughness(playerA, lieutenant, 4, 4);
        assertPermanentCount(playerA, "Soldier Token", 3);
    }

    @Test
    public void test_IllusionOfChoice_Single() {
        addCard(Zone.BATTLEFIELD, playerA, "Tundra", 6);
        addCard(Zone.HAND, playerA, illusion);
        addCard(Zone.HAND, playerA, lieutenant);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, illusion);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, lieutenant);
        setChoice(playerA, true, 4);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPowerToughness(playerA, lieutenant, 6, 6);
        assertPermanentCount(playerA, "Soldier Token", 0);
    }

    @Test
    public void test_IllusionOfChoice_WithBragosRepresentative() {
        addCard(Zone.BATTLEFIELD, playerA, "Tundra", 6);
        addCard(Zone.BATTLEFIELD, playerB, rep);
        addCard(Zone.HAND, playerA, illusion);
        addCard(Zone.HAND, playerA, lieutenant);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, illusion);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, lieutenant);
        setChoice(playerA, true, 5);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPowerToughness(playerA, lieutenant, 7, 7);
        assertPermanentCount(playerA, "Soldier Token", 0);
    }

    @Test
    public void test_IllusionOfChoice_Double() {
        addCard(Zone.BATTLEFIELD, playerA, "Tundra", 6);
        addCard(Zone.BATTLEFIELD, playerB, "Island");
        addCard(Zone.HAND, playerA, illusion);
        addCard(Zone.HAND, playerB, illusion);
        addCard(Zone.HAND, playerA, lieutenant);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, illusion);
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerB, illusion);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, lieutenant);
        setChoice(playerB, true, 4);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPowerToughness(playerA, lieutenant, 6, 6);
        assertPermanentCount(playerA, "Soldier Token", 0);
    }

    @Test
    public void test_GrudgeKeeper_1() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerA, keeper);
        addCard(Zone.BATTLEFIELD, playerB, rep);
        addCard(Zone.HAND, playerA, lieutenant);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, lieutenant);
        setChoice(playerB, true);
        setChoices("Yes", "No", "No", "No");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPowerToughness(playerA, lieutenant, 4, 4);
        assertPermanentCount(playerA, "Soldier Token", 3);
        assertLife(playerA, 20);
        assertLife(playerB, 20 - 2);
        assertLife(playerC, 20 - 2);
        assertLife(playerD, 20 - 2);
    }

    @Test
    public void test_GrudgeKeeper_2() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerA, keeper);
        addCard(Zone.BATTLEFIELD, playerA, rep);
        addCard(Zone.BATTLEFIELD, playerB, rep);
        addCard(Zone.HAND, playerA, lieutenant);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, lieutenant);
        setChoice(playerA, false);
        setChoice(playerB, false);
        setChoices("Yes");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPowerToughness(playerA, lieutenant, 6, 6);
        assertPermanentCount(playerA, "Soldier Token", 2);
        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertLife(playerC, 20);
        assertLife(playerD, 20);
    }
}
