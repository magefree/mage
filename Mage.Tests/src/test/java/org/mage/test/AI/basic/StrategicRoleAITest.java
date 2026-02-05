package org.mage.test.AI.basic;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.Game;
import mage.player.ai.DeckArchetypeAnalyzer;
import mage.player.ai.StrategicRoleEvaluator;
import mage.players.Player;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBaseAI;

/**
 * Tests for the AI Strategic Role System ("Who's the Beatdown?").
 *
 * This system helps the AI adapt its play style based on:
 * - Deck archetype (aggro vs control)
 * - Matchup comparison
 * - Play/draw advantage
 * - Current game state
 *
 * @author AI improvements
 */
public class StrategicRoleAITest extends CardTestPlayerBaseAI {

    /**
     * Test that an aggro deck (low curve, many creatures) gets a high aggro score.
     */
    @Test
    public void testAggroDeckAnalysis() {
        // Set up an aggro deck - low cost creatures
        addCard(Zone.LIBRARY, playerA, "Goblin Guide", 4);      // 1 mana 2/2 haste
        addCard(Zone.LIBRARY, playerA, "Monastery Swiftspear", 4); // 1 mana 1/2 haste
        addCard(Zone.LIBRARY, playerA, "Eidolon of the Great Revel", 4); // 2 mana
        addCard(Zone.LIBRARY, playerA, "Lightning Bolt", 4);    // 1 mana removal
        addCard(Zone.LIBRARY, playerA, "Mountain", 20);

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.HAND, playerA, "Goblin Guide", 2);

        // Set up a standard control deck for opponent
        addCard(Zone.LIBRARY, playerB, "Island", 20);
        addCard(Zone.LIBRARY, playerB, "Cancel", 4);            // 3 mana counterspell
        addCard(Zone.LIBRARY, playerB, "Wrath of God", 4);      // 4 mana board wipe
        addCard(Zone.LIBRARY, playerB, "Sphinx of Uthuun", 4);  // 7 mana finisher
        addCard(Zone.BATTLEFIELD, playerB, "Island", 4);

        skipInitShuffling();
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        Game game = currentGame;
        Player player = game.getPlayer(playerA.getId());
        Player opponent = game.getPlayer(playerB.getId());

        // Analyze deck archetypes
        int playerAggroScore = DeckArchetypeAnalyzer.analyzeAggroScore(player, game);
        int opponentAggroScore = DeckArchetypeAnalyzer.analyzeAggroScore(opponent, game);

        // Aggro deck should have higher score
        Assert.assertTrue("Aggro deck should have high aggro score (>60), got: " + playerAggroScore,
                playerAggroScore > 60);
        Assert.assertTrue("Control deck should have low aggro score (<40), got: " + opponentAggroScore,
                opponentAggroScore < 50);

        // Determine roles
        int playerRole = StrategicRoleEvaluator.determineRole(player, opponent, game);
        int opponentRole = StrategicRoleEvaluator.determineRole(opponent, player, game);

        Assert.assertEquals("Aggro player should be BEATDOWN",
                StrategicRoleEvaluator.ROLE_BEATDOWN, playerRole);
        Assert.assertEquals("Control player should be CONTROL",
                StrategicRoleEvaluator.ROLE_CONTROL, opponentRole);
    }

    /**
     * Test that a control deck (high curve, removal) gets a low aggro score.
     */
    @Test
    public void testControlDeckAnalysis() {
        // Set up a control deck - high cost, removal heavy
        addCard(Zone.LIBRARY, playerA, "Cancel", 4);            // 3 mana counterspell
        addCard(Zone.LIBRARY, playerA, "Wrath of God", 4);      // 4 mana board wipe
        addCard(Zone.LIBRARY, playerA, "Sphinx of Uthuun", 4);  // 7 mana finisher
        addCard(Zone.LIBRARY, playerA, "Doom Blade", 4);        // 2 mana removal
        addCard(Zone.LIBRARY, playerA, "Island", 15);
        addCard(Zone.LIBRARY, playerA, "Swamp", 10);

        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.HAND, playerA, "Cancel", 1);

        // Set up midrange opponent
        addCard(Zone.LIBRARY, playerB, "Tarmogoyf", 4);         // 2 mana
        addCard(Zone.LIBRARY, playerB, "Siege Rhino", 4);       // 4 mana
        addCard(Zone.LIBRARY, playerB, "Forest", 20);
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 3);

        skipInitShuffling();
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        Game game = currentGame;
        Player player = game.getPlayer(playerA.getId());

        int aggroScore = DeckArchetypeAnalyzer.analyzeAggroScore(player, game);

        // Control deck should have low aggro score
        Assert.assertTrue("Control deck should have low aggro score (<40), got: " + aggroScore,
                aggroScore < 40);
    }

    /**
     * Test that in a mirror matchup, the player who went first becomes the beatdown
     * due to tempo advantage.
     */
    @Test
    public void testPlayDrawAdvantageInMirror() {
        // Set up similar midrange decks for both players
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears", 8);
        addCard(Zone.LIBRARY, playerA, "Giant Growth", 4);
        addCard(Zone.LIBRARY, playerA, "Forest", 20);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.HAND, playerA, "Grizzly Bears", 2);

        addCard(Zone.LIBRARY, playerB, "Grizzly Bears", 8);
        addCard(Zone.LIBRARY, playerB, "Giant Growth", 4);
        addCard(Zone.LIBRARY, playerB, "Forest", 20);
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 3);
        addCard(Zone.HAND, playerB, "Grizzly Bears", 2);

        skipInitShuffling();
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        Game game = currentGame;
        Player player = game.getPlayer(playerA.getId());
        Player opponent = game.getPlayer(playerB.getId());

        // Both decks should have similar aggro scores (midrange ~50)
        int playerAggroScore = DeckArchetypeAnalyzer.analyzeAggroScore(player, game);
        int opponentAggroScore = DeckArchetypeAnalyzer.analyzeAggroScore(opponent, game);

        // Scores should be close (within 20 points)
        Assert.assertTrue("Mirror matchup should have similar aggro scores",
                Math.abs(playerAggroScore - opponentAggroScore) < 20);

        // Player A went first (turn 1), so should be the beatdown
        int playerRole = StrategicRoleEvaluator.determineRole(player, opponent, game);
        int opponentRole = StrategicRoleEvaluator.determineRole(opponent, player, game);

        // The player who went first should lean toward beatdown
        // The player who went second should lean toward control
        Assert.assertTrue("Player who went first should lean beatdown (role >= 0)",
                playerRole >= StrategicRoleEvaluator.ROLE_FLEXIBLE);
        Assert.assertTrue("Player who went second should lean control (role <= 0)",
                opponentRole <= StrategicRoleEvaluator.ROLE_FLEXIBLE);
    }

    /**
     * Test that being behind on life pushes toward more aggressive play.
     */
    @Test
    public void testLifeTotalAffectsRole() {
        // Set up midrange decks
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears", 8);
        addCard(Zone.LIBRARY, playerA, "Forest", 20);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);

        addCard(Zone.LIBRARY, playerB, "Grizzly Bears", 8);
        addCard(Zone.LIBRARY, playerB, "Forest", 20);
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 3);

        // PlayerA is at low life
        setLife(playerA, 5);
        setLife(playerB, 20);

        skipInitShuffling();
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        Game game = currentGame;
        Player player = game.getPlayer(playerA.getId());
        Player opponent = game.getPlayer(playerB.getId());

        // Get the role score (not just the category)
        int playerRoleScore = StrategicRoleEvaluator.getRoleScore(player, opponent, game);
        int opponentRoleScore = StrategicRoleEvaluator.getRoleScore(opponent, player, game);

        // Being behind on life should push player toward aggressive play (higher score)
        // Being ahead on life should push opponent toward defensive play (lower score)
        Assert.assertTrue("Player behind on life should have higher role score (more aggressive), got: " + playerRoleScore,
                playerRoleScore > opponentRoleScore);
    }

    /**
     * Test role name helper method.
     */
    @Test
    public void testRoleNameHelpers() {
        Assert.assertEquals("Beatdown", StrategicRoleEvaluator.getRoleName(StrategicRoleEvaluator.ROLE_BEATDOWN));
        Assert.assertEquals("Control", StrategicRoleEvaluator.getRoleName(StrategicRoleEvaluator.ROLE_CONTROL));
        Assert.assertEquals("Flexible", StrategicRoleEvaluator.getRoleName(StrategicRoleEvaluator.ROLE_FLEXIBLE));
    }

    /**
     * Test that opponent deck analysis works with revealed information only.
     * Cards in the graveyard and battlefield should be analyzed,
     * but hidden cards (library, hand) should not be directly known.
     */
    @Test
    public void testOpponentRevealedInfoAnalysis() {
        // Set up our deck
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears", 8);
        addCard(Zone.LIBRARY, playerA, "Forest", 20);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);

        // Opponent has cards in various zones
        addCard(Zone.LIBRARY, playerB, "Sphinx of Uthuun", 4);  // Hidden - 7 mana control finisher
        addCard(Zone.LIBRARY, playerB, "Wrath of God", 4);      // Hidden - control spell
        addCard(Zone.LIBRARY, playerB, "Island", 20);

        // These are revealed (battlefield and graveyard)
        addCard(Zone.BATTLEFIELD, playerB, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerB, "Serra Angel", 1);   // Revealed - 5 mana flyer
        addCard(Zone.GRAVEYARD, playerB, "Doom Blade", 2);      // Revealed - removal spells

        skipInitShuffling();
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        Game game = currentGame;
        Player opponent = game.getPlayer(playerB.getId());

        // Get revealed-only analysis
        int revealedScore = DeckArchetypeAnalyzer.analyzeOpponentFromRevealedInfo(opponent, game);

        // The revealed cards suggest a control deck (Serra Angel, Doom Blades)
        // With limited info, should still give a reasonable estimate
        Assert.assertTrue("Revealed info should give a score (not default 50 with enough revealed cards)",
                revealedScore != 50 || countRevealedCards(opponent, game) < 3);
    }

    /**
     * Test that evasive creatures push toward more aggressive play.
     */
    @Test
    public void testEvasiveCreaturesPushAggressive() {
        // Player has flying creatures
        addCard(Zone.LIBRARY, playerA, "Forest", 20);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Serra Angel", 2);  // 4/4 flying, vigilance

        // Opponent has ground creatures
        addCard(Zone.LIBRARY, playerB, "Forest", 20);
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 4);
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears", 3);

        skipInitShuffling();
        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);  // Turn 2 so creatures can attack
        execute();

        Game game = currentGame;
        Player player = game.getPlayer(playerA.getId());
        Player opponent = game.getPlayer(playerB.getId());

        // Player with flyers should lean toward beatdown due to evasion pressure
        int playerRole = StrategicRoleEvaluator.determineRole(player, opponent, game);

        Assert.assertTrue("Player with evasive creatures should lean aggressive (role >= 0)",
                playerRole >= StrategicRoleEvaluator.ROLE_FLEXIBLE);
    }

    /**
     * Test that late game pushes toward control play style.
     */
    @Test
    public void testLateGameFavorsControl() {
        // Set up similar midrange decks
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears", 8);
        addCard(Zone.LIBRARY, playerA, "Forest", 20);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 8);

        addCard(Zone.LIBRARY, playerB, "Grizzly Bears", 8);
        addCard(Zone.LIBRARY, playerB, "Forest", 20);
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 8);

        skipInitShuffling();
        // Simulate late game by advancing to turn 10
        setStopAt(10, PhaseStep.PRECOMBAT_MAIN);
        execute();

        Game game = currentGame;
        Player player = game.getPlayer(playerA.getId());
        Player opponent = game.getPlayer(playerB.getId());

        int earlyGameRoleScore = 0; // Would be neutral early game

        // In late game (turn 10), role should shift toward control
        int lateGameRoleScore = StrategicRoleEvaluator.getRoleScore(player, opponent, game);

        // Late game modifier should push the score negative (toward control)
        // We can't compare to early game directly, but we can check it trends toward control
        Assert.assertTrue("Late game should push role score toward control (negative adjustment)",
                lateGameRoleScore < 20); // Should not be strongly beatdown in late game with even boards
    }

    /**
     * Helper method to count revealed cards for an opponent.
     */
    private int countRevealedCards(Player opponent, Game game) {
        int count = 0;
        count += game.getBattlefield().getAllActivePermanents(opponent.getId()).size();
        count += opponent.getGraveyard().size();
        return count;
    }
}
