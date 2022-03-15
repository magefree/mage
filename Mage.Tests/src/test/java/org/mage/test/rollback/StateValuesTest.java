package org.mage.test.rollback;

import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class StateValuesTest extends CardTestPlayerBase {

    @Test
    public void testDragonWhelpActivatedFourTimes() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        // Flying
        // {R}: Dragon Whelp gets +1/+0 until end of turn. If this ability has been activated four or more times this turn, sacrifice Dragon Whelp at the beginning of the next end step.
        addCard(Zone.BATTLEFIELD, playerA, "Dragon Whelp", 1); // 2/3

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{R}: ");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{R}: ");
        attack(1, playerA, "Dragon Whelp");

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{R}: ");
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{R}: ");

        attack(3, playerA, "Dragon Whelp");

        rollbackTurns(3, PhaseStep.BEGIN_COMBAT, playerA, 0);
        rollbackAfterActionsStart();

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{R}: ");
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{R}: ");

        attack(3, playerA, "Dragon Whelp");
        rollbackAfterActionsEnd();

        setStopAt(4, PhaseStep.UPKEEP);
        execute();

        assertPermanentCount(playerA, "Dragon Whelp", 1);
        assertGraveyardCount(playerA, "Dragon Whelp", 0);

        assertLife(playerA, 20);
        assertLife(playerB, 12);

    }

    @Test
    public void testBriarbridgePatrol() {
        setStrictChooseMode(true);

        // Whenever Briarbridge Patrol deals damage to one or more creatures, investigate (Create a colorless Clue artifact token onto the battlefield with "{2}, Sacrifice this artifact: Draw a card.").
        // At the beginning of each end step, if you sacrificed three or more Clues this turn, you may put a creature card from your hand onto the battlefield.
        addCard(Zone.BATTLEFIELD, playerA, "Briarbridge Patrol", 1); // 3/3

        addCard(Zone.BATTLEFIELD, playerB, "Pillarfield Ox", 1); // 2/4

        attack(1, playerA, "Briarbridge Patrol");
        block(1, playerB, "Pillarfield Ox", "Briarbridge Patrol");

        attack(3, playerA, "Briarbridge Patrol");
        block(3, playerB, "Pillarfield Ox", "Briarbridge Patrol");

        rollbackTurns(3, PhaseStep.POSTCOMBAT_MAIN, playerA, 0);

        rollbackAfterActionsStart();
        attack(3, playerA, "Briarbridge Patrol");
        block(3, playerB, "Pillarfield Ox", "Briarbridge Patrol");
        rollbackAfterActionsEnd();

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertAllCommandsUsed();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Clue Token", 2);

    }

    @Test
    public void rollbackTokenCreationTest() {
        // Create two 1/1 white Bird creature tokens with flying.
        // Flashback—Tap three untapped white creatures you control. (You may cast this card from your graveyard for its flashback cost. Then exile it.)
        addCard(Zone.LIBRARY, playerA, "Battle Screech", 1); // Sorcery {2}{W}{W}
        skipInitShuffling();
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Battle Screech");

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Flashback");
        setChoice(playerA, "Bird Token");
        setChoice(playerA, "Bird Token");
        setChoice(playerA, "Silvercoat Lion");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);

        execute();
        assertAllCommandsUsed();

        // Before rollback
        assertTappedCount("Plains", true, 4);
        assertTappedCount("Bird Token", true, 2);
        assertTappedCount("Silvercoat Lion", true, 1);
        assertPermanentCount(playerA, "Bird Token", 4);
        assertHandCount(playerA, 0);
        assertExileCount(playerA, "Battle Screech", 1);

        currentGame.rollbackTurns(2);

        // After rollback to turn 1
        assertTappedCount("Plains", true, 0);
        assertTappedCount("Silvercoat Lion", true, 0);
        assertPermanentCount(playerA, "Bird Token", 0);
        assertLibraryCount(playerA, "Battle Screech", 1);
        assertHandCount(playerA, 0);
    }

    @Test
    public void rollbackBuffUntilEndOfTurnTest() {
        // Target creature gets +1/+1 and gains flying and first strike until end of turn.
        addCard(Zone.LIBRARY, playerA, "Aerial Maneuver", 1); // Instant {1}{W}
        skipInitShuffling();
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Aerial Maneuver", "Silvercoat Lion");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.BEGIN_COMBAT);

        execute();
        assertAllCommandsUsed();

        // Before rollback
        assertTappedCount("Plains", true, 2);
        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertPowerToughness(playerA, "Silvercoat Lion", 3, 3);
        assertAbility(playerA, "Silvercoat Lion", FlyingAbility.getInstance(), true);
        assertAbility(playerA, "Silvercoat Lion", FirstStrikeAbility.getInstance(), true);
        assertHandCount(playerA, 0);
        assertGraveyardCount(playerA, "Aerial Maneuver", 1);

        currentGame.rollbackTurns(0);

        // After rollback to begin turn 3
        assertTappedCount("Plains", true, 0);
        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertPowerToughness(playerA, "Silvercoat Lion", 2, 2);
        assertAbility(playerA, "Silvercoat Lion", FlyingAbility.getInstance(), false);
        assertAbility(playerA, "Silvercoat Lion", FirstStrikeAbility.getInstance(), false);
        assertLibraryCount(playerA, "Aerial Maneuver", 1);
        assertHandCount(playerA, 0);
    }
}
