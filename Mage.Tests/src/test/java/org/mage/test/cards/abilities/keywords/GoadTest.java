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
import org.junit.Ignore;
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

    @Ignore // currently fails due to how test works
    @Test
    public void goadWithOwnedCreatureTest() {
        // Your opponents can't cast spells during combat.
        // Whenever a creature you control deals combat damage to a player, goad each creature that player controls
        // (Until your next turn, that creature attacks each combat if able and attacks a player other than you if able.)
        addCard(Zone.BATTLEFIELD, playerD, marisi, 1); // Creature 5/4

        addCard(Zone.BATTLEFIELD, playerC, griffin, 3); // Creature 2/2

        attack(2, playerD, marisi, playerC);

        setStopAt(3, PhaseStep.BEGIN_COMBAT);

        setStrictChooseMode(true);
        execute();
        assertAllCommandsUsed();

        Permanent griffinPermanent = getPermanent(griffin);

        Assert.assertFalse("Griffin can attack playerD but should not be able",
                griffinPermanent.canAttack(playerD.getId(), currentGame));
        Assert.assertTrue("Griffin can't attack playerA but should be able",
                griffinPermanent.canAttack(playerA.getId(), currentGame));
        Assert.assertTrue("Griffin can't attack playerB but should be able",
                griffinPermanent.canAttack(playerB.getId(), currentGame));

        assertLife(playerC, 35);
        assertLife(playerD, 40); // player D can not be attacked from C because the creatures are goaded
        assertLife(playerA, 40);
        assertLife(playerB, 40);

    }

    /**
     * In a game of commander, my opponent gained control of Marisi, Breaker of
     * Coils (until end of turn) and did combat damage to another player. This
     * caused the creatures damaged by Marisi's controller to be goaded.
     * However, when the goaded creatures went to attack, they could not attack
     * me but could attack the (former) controller of Marisi.
     */
    @Ignore // currently fails due to how test works
    @Test
    public void goadWithNotOwnedCreatureTest() {
        // Your opponents can't cast spells during combat.
        // Whenever a creature you control deals combat damage to a player, goad each creature that player controls
        // (Until your next turn, that creature attacks each combat if able and attacks a player other than you if able.)
        addCard(Zone.BATTLEFIELD, playerA, marisi, 1); // Creature 5/4

        // Untap target creature an opponent controls and gain control of it until end of turn.
        // That creature gains haste until end of turn.
        // When you lose control of the creature, tap it.
        addCard(Zone.HAND, playerD, ray); // Instant {3}{U}
        addCard(Zone.BATTLEFIELD, playerD, "Island", 4);

        addCard(Zone.BATTLEFIELD, playerC, lion, 1); // Creature 2/2

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerD, ray, marisi);

        attack(2, playerD, marisi, playerC);

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertAllCommandsUsed();

        assertGraveyardCount(playerD, ray, 1);
        assertPermanentCount(playerA, marisi, 1);

        Permanent permanent = getPermanent(lion, playerC);

        Assert.assertFalse("Silvercoat lion shouldn't be able to attack player D but can", permanent.canAttack(playerD.getId(), currentGame));
        Assert.assertTrue("Silvercoat lion should be able to attack player A but can't", permanent.canAttack(playerA.getId(), currentGame));
        Assert.assertTrue("Silvercoat lion should be able to attack player B but can't", permanent.canAttack(playerB.getId(), currentGame));

        assertLife(playerD, 40);
        assertLife(playerC, 35);
        assertLife(playerA, 40);
        assertLife(playerB, 40);

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
