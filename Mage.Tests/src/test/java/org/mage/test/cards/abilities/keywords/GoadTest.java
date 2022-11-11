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
        playerA = createPlayer(game, "PlayerA");
        playerB = createPlayer(game, "PlayerB");
        playerC = createPlayer(game, "PlayerC");
        playerD = createPlayer(game, "PlayerD");
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

    /**
     * Checks whether the given attacker is NOT goaded by the provided player(s).
     *
     * @param attacker  the name of the attacker
     * @param players   the player(s) that the attacker is supposed to be goaded by.
     */
    private void assertNotGoaded(String attacker, TestPlayer... players) {
        Assert.assertTrue("At least one player should be provided", players.length > 0);
        Permanent permanent = getPermanent(attacker);
        Assert.assertNotEquals(
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

    /**
     * Checks whether the given attacker is goaded by the provided player(s).
     *
     * @param attacker  the name of the attacker
     * @param players   the player(s) that the attacker is supposed to be goaded by.
     */
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

        assertGoaded(lion, playerA, playerD);
        assertAttacking(lion, playerA, playerD);
    }

    @Test
    public void testRegularCombatRequirement() {
        addCard(Zone.BATTLEFIELD, playerA, "Berserkers of Blood Ridge");

        setStopAt(1, PhaseStep.DECLARE_BLOCKERS);
        execute();

        assertAttacking("Berserkers of Blood Ridge", playerB, playerC, playerD);
    }

    /**
     * Reported bug: https://github.com/magefree/mage/issues/9227
     * Geode Rager (and other goad all effects) goad creatures that enter the battlefield after the effect resolved.
     *
     * Ruling:
     *      Creatures that enter the battlefield or come under the target player’s control after Geode Rager’s ability has resolved won’t be goaded.
     *      (2020-09-25)
     */
    @Test
    public void goadAllCorrectAffect() {
        addCard(Zone.BATTLEFIELD, playerA, "Geode Rager");
        addCard(Zone.HAND, playerA, "Swamp");

        addCard(Zone.BATTLEFIELD, playerD, "Goblin Balloon Brigade");
        addCard(Zone.BATTLEFIELD, playerD, "Mountain");
        addCard(Zone.HAND, playerD, "Goblin Champion");

        setStrictChooseMode(true);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Swamp");
        addTarget(playerA, playerD); // Goad all of playerD's creatures

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerD, "Goblin Champion");
        addTarget(playerD, playerC); // Goblin Balloon Brigade attack player C
        // Should not have to specify a target for Goblin Champion since they aren't goaded

        setStopAt(2, PhaseStep.DECLARE_BLOCKERS);

        execute();

        assertGoaded("Goblin Balloon Brigade", playerA);
        assertNotGoaded("Goblin Champion", playerA);
    }

}
