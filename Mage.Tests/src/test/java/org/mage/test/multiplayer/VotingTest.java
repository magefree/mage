package org.mage.test.multiplayer;

import mage.constants.MultiplayerAttackOption;
import mage.constants.PhaseStep;
import mage.constants.RangeOfInfluence;
import mage.constants.Zone;
import mage.game.FreeForAll;
import mage.game.Game;
import mage.game.GameException;
import mage.game.mulligan.MulliganType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestMultiPlayerBase;

import java.io.FileNotFoundException;

/**
 * @author TheElk801
 */
public class VotingTest extends CardTestMultiPlayerBase {

    private static final String lieutenant = "Lieutenants of the Guard";
    private static final String rep = "Brago's Representative";
    private static final String broker = "Ballot Broker";
    private static final String illusion = "Illusion of Choice";
    private static final String keeper = "Grudge Keeper";

    @Override
    protected Game createNewGameAndPlayers() throws GameException, FileNotFoundException {
        Game game = new FreeForAll(MultiplayerAttackOption.MULTIPLE, RangeOfInfluence.ALL, MulliganType.GAME_DEFAULT.getMulligan(0), 20);
        // Player order: A -> D -> C -> B
        playerA = createPlayer(game, playerA, "PlayerA");
        playerB = createPlayer(game, playerB, "PlayerB");
        playerC = createPlayer(game, playerC, "PlayerC");
        playerD = createPlayer(game, playerD, "PlayerD");
        return game;
    }

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
    public void testLieutenant1() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.HAND, playerA, lieutenant);

        setStrictChooseMode(true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, lieutenant);
        setChoices("Yes");

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPowerToughness(playerA, lieutenant, 6, 6);
        assertPermanentCount(playerA, "Soldier", 0);
    }

    @Test
    public void testLieutenant2() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.HAND, playerA, lieutenant);

        setStrictChooseMode(true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, lieutenant);
        setChoices("No");

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPowerToughness(playerA, lieutenant, 2, 2);
        assertPermanentCount(playerA, "Soldier", 4);
    }

    @Test
    public void testLieutenant3() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.HAND, playerA, lieutenant);

        setStrictChooseMode(true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, lieutenant);
        setChoices("Yes", "Yes", "No", "No");

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPowerToughness(playerA, lieutenant, 4, 4);
        assertPermanentCount(playerA, "Soldier", 2);
    }

    @Test
    public void testBragosRepresentative() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerA, rep);
        addCard(Zone.HAND, playerA, lieutenant);

        setStrictChooseMode(true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, lieutenant);
        setChoice(playerA, "Yes");
        setChoices("Yes", "Yes", "No", "No");

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPowerToughness(playerA, lieutenant, 5, 5);
        assertPermanentCount(playerA, "Soldier", 2);
    }

    @Test
    public void testBallotBroker() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerA, broker);
        addCard(Zone.HAND, playerA, lieutenant);

        setStrictChooseMode(true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, lieutenant);
        setChoice(playerA, "Yes"); // to have an additional vote
        setChoice(playerA, "No"); // the additional vote
        setChoices("Yes", "Yes", "No", "No");

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPowerToughness(playerA, lieutenant, 4, 4);
        assertPermanentCount(playerA, "Soldier", 3);
    }

    @Test
    public void testIllusionOfChoice() {
        addCard(Zone.BATTLEFIELD, playerA, "Tundra", 6);
        addCard(Zone.HAND, playerA, illusion);
        addCard(Zone.HAND, playerA, lieutenant);

        setStrictChooseMode(true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, illusion);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, lieutenant);
        setChoice(playerA, "Yes", 4);

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPowerToughness(playerA, lieutenant, 6, 6);
        assertPermanentCount(playerA, "Soldier", 0);
    }

    @Test
    public void testIllusionOfChoiceWithBragosRepresentative() {
        addCard(Zone.BATTLEFIELD, playerA, "Tundra", 6);
        addCard(Zone.BATTLEFIELD, playerB, rep);
        addCard(Zone.HAND, playerA, illusion);
        addCard(Zone.HAND, playerA, lieutenant);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, illusion);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, lieutenant);
        setChoice(playerA, "Yes", 5);

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPowerToughness(playerA, lieutenant, 7, 7);
        assertPermanentCount(playerA, "Soldier", 0);
    }

    @Test
    public void testIllusionOfChoiceTwice() {
        addCard(Zone.BATTLEFIELD, playerA, "Tundra", 6);
        addCard(Zone.BATTLEFIELD, playerB, "Island");
        addCard(Zone.HAND, playerA, illusion);
        addCard(Zone.HAND, playerB, illusion);
        addCard(Zone.HAND, playerA, lieutenant);

        setStrictChooseMode(true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, illusion);
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerB, illusion);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, lieutenant);
        setChoice(playerB, "Yes", 4);

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPowerToughness(playerA, lieutenant, 6, 6);
        assertPermanentCount(playerA, "Soldier", 0);
    }

    @Test
    public void testGrudgeKeeper() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerA, keeper);
        addCard(Zone.BATTLEFIELD, playerB, rep);
        addCard(Zone.HAND, playerA, lieutenant);

        setStrictChooseMode(true);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, lieutenant);
        setChoice(playerB, "Yes");
        setChoices("Yes", "No", "No", "No");

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPowerToughness(playerA, lieutenant, 4, 4);
        assertPermanentCount(playerA, "Soldier", 3);
        assertLife(playerA, 20);
        assertLife(playerB, 20 - 2);
        assertLife(playerC, 20 - 2);
        assertLife(playerD, 20 - 2);
    }

    @Test
    public void testGrudgeKeeper2() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerA, keeper);
        addCard(Zone.BATTLEFIELD, playerA, rep);
        addCard(Zone.BATTLEFIELD, playerB, rep);
        addCard(Zone.HAND, playerA, lieutenant);

        setStrictChooseMode(true);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, lieutenant);
        setChoice(playerA, "No");
        setChoice(playerB, "No");
        setChoices("Yes");

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPowerToughness(playerA, lieutenant, 6, 6);
        assertPermanentCount(playerA, "Soldier", 2);
        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertLife(playerC, 20);
        assertLife(playerD, 20);
    }
}
