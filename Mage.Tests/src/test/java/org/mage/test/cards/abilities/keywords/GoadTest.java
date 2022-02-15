package org.mage.test.cards.abilities.keywords;

import mage.constants.MultiplayerAttackOption;
import mage.constants.PhaseStep;
import mage.constants.RangeOfInfluence;
import mage.constants.Zone;
import mage.game.FreeForAll;
import mage.game.Game;
import mage.game.GameException;
import mage.game.mulligan.MulliganType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestMultiPlayerBase;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author LevelX2
 */
public class GoadTest extends CardTestMultiPlayerBase {

    private static final String marisi = "Marisi, Breaker of the Coil";
    private static final String griffin = "Abbey Griffin";
    private static final String ray = "Ray of Command";
    private static final String homunculus = "Jeering Homunculus";
    private static final String lion = "Silvercoat Lion";
    private static final String archon = "Blazing Archon";

    @Override
    protected Game createNewGameAndPlayers() throws GameException, FileNotFoundException {
        Game game = new FreeForAll(MultiplayerAttackOption.MULTIPLE, RangeOfInfluence.ALL, MulliganType.GAME_DEFAULT.getMulligan(0), 40);
        // Player order: A -> D -> C -> B
        playerA = createPlayer(game, playerA, "PlayerA");
        playerB = createPlayer(game, playerB, "PlayerB");
        playerC = createPlayer(game, playerC, "PlayerC");
        playerD = createPlayer(game, playerD, "PlayerD");
        return game;
    }

    private void assertAttacking(String attacker, TestPlayer... players) {
        Assert.assertTrue("At least one player should be provided", players.length > 0);
        Permanent permanent = getPermanent(attacker);
        Assert.assertTrue("Creature should be tapped", permanent.isTapped());
        Assert.assertTrue("Creature should be attacking", permanent.isAttacking());
        UUID defenderId = currentGame.getCombat().getDefenderId(permanent.getId());
        Assert.assertTrue(
                "Creature should be attacking one the following players: "
                        + Arrays
                        .stream(players)
                        .map(Player::getName)
                        .reduce((a, b) -> a + ", " + b)
                        .orElse(""),
                Arrays.stream(players)
                        .map(TestPlayer::getId)
                        .anyMatch(defenderId::equals)
        );
    }

    private void assertGoaded(String attacker, TestPlayer... players) {
        Assert.assertTrue("At least one player should be provided", players.length > 0);
        Permanent permanent = getPermanent(attacker);
        Assert.assertEquals(
                "Creature should be goaded by "
                        + Arrays
                        .stream(players)
                        .map(Player::getName)
                        .reduce((a, b) -> a + ", " + b).orElse(""),
                permanent.getGoadingPlayers(),
                Arrays.stream(players)
                        .map(TestPlayer::getId)
                        .collect(Collectors.toSet())
        );
    }

    @Test
    public void testCantAttackGoadingPlayer() {
        addCard(Zone.HAND, playerA, homunculus);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerD, lion);

        addTarget(playerA, lion);
        setChoice(playerA, "Yes");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, homunculus);

        setStopAt(2, PhaseStep.DECLARE_BLOCKERS);
        execute();
        assertAllCommandsUsed();

        assertGoaded(lion, playerA);
        assertAttacking(lion, playerB, playerC);
    }

    @Test
    public void testCanOnlyAttackOnePlayer() {
        addCard(Zone.HAND, playerA, homunculus);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerD, lion);
        addCard(Zone.BATTLEFIELD, playerB, archon);

        addTarget(playerA, lion);
        setChoice(playerA, "Yes");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, homunculus);

        setStopAt(2, PhaseStep.DECLARE_BLOCKERS);
        execute();
        assertAllCommandsUsed();

        assertGoaded(lion, playerA);
        assertAttacking(lion, playerC);
    }

    @Test
    public void testMustAttackGoader() {
        addCard(Zone.HAND, playerA, homunculus);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerD, lion);
        addCard(Zone.BATTLEFIELD, playerB, archon);
        addCard(Zone.BATTLEFIELD, playerC, archon);

        addTarget(playerA, lion);
        setChoice(playerA, "Yes");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, homunculus);

        setStopAt(2, PhaseStep.DECLARE_BLOCKERS);
        execute();
        assertAllCommandsUsed();

        assertGoaded(lion, playerA);
        assertAttacking(lion, playerA);
    }

    @Test
    public void testMultipleGoad() {
        addCard(Zone.HAND, playerA, homunculus);
        addCard(Zone.HAND, playerD, homunculus);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerD, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerC, lion);

        addTarget(playerA, lion);
        setChoice(playerA, "Yes");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, homunculus);

        addTarget(playerD, lion);
        setChoice(playerD, "Yes");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerD, homunculus);

        setStopAt(3, PhaseStep.DECLARE_BLOCKERS);
        execute();
        assertAllCommandsUsed();

        assertGoaded(lion, playerA, playerD);
        assertAttacking(lion, playerB);
    }

    @Test
    public void testMultipleGoadRestriction() {
        addCard(Zone.HAND, playerA, homunculus);
        addCard(Zone.HAND, playerD, homunculus);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerD, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerB, archon);
        addCard(Zone.BATTLEFIELD, playerC, lion);

        addTarget(playerA, lion);
        setChoice(playerA, "Yes");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, homunculus);

        addTarget(playerD, lion);
        setChoice(playerD, "Yes");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerD, homunculus);

        setStopAt(3, PhaseStep.DECLARE_BLOCKERS);
        execute();
        assertAllCommandsUsed();

        assertGoaded(lion, playerA, playerD);
        assertAttacking(lion, playerA, playerD);
    }

    @Test
    public void testRegularCombatRequirement() {
        addCard(Zone.BATTLEFIELD, playerA, "Berserkers of Blood Ridge");

        setStopAt(1, PhaseStep.DECLARE_BLOCKERS);
        execute();
        assertAllCommandsUsed();

        assertAttacking("Berserkers of Blood Ridge", playerB, playerC, playerD);
    }
}
