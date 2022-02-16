package org.mage.test.cards.single.afc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommander4Players;


public class ShareTheSpoilsTest extends CardTestCommander4Players {

    private static final String shareTheSpoils = "Share the Spoils";

    /**
     * When Share the Spoils enters the battlefield every player exiles one card.
     */
    @Test
    public void enterTheBattleField() {
        addCard(Zone.HAND, playerA, shareTheSpoils);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, shareTheSpoils);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertAllCommandsUsed();
        assertExileCount(playerA, 1);
        assertExileCount(playerB, 1);
        assertExileCount(playerC, 1);
        assertExileCount(playerD, 1);
    }

    /**
     * When an opponent loses, their exiled cards are removed from the game and all other players exile a new card.
     */
    @Test
    public void nonOwnerLoses() {
        setLife(playerD, 1);
        addCard(Zone.BATTLEFIELD, playerA, shareTheSpoils);
        addCard(Zone.BATTLEFIELD, playerA, "Banehound", 1);

        attack(1, playerA, "Banehound", playerD);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertAllCommandsUsed();
        assertExileCount(playerA, 1);
        assertExileCount(playerB, 1);
        assertExileCount(playerC, 1);
        assertExileCount(playerD, 0);
    }

    /**
     * When an opponent loses, their exiled cards are removed from the game and all other players exile a new card.
     */
    @Test
    public void nonOwnerConcedes() {
        addCard(Zone.BATTLEFIELD, playerA, shareTheSpoils);
        concede(1, PhaseStep.PRECOMBAT_MAIN, playerD);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertAllCommandsUsed();
        assertExileCount(playerA, 1);
        assertExileCount(playerB, 1);
        assertExileCount(playerC, 1);
        assertExileCount(playerD, 0);
    }

    /**
     * When owner loses, no new cards should be exiled and owner's exiled cards are removed from the game.
     */
    @Test
    public void ownerConcedes() {
        addCard(Zone.HAND, playerA, shareTheSpoils);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, shareTheSpoils);
        concede(1, PhaseStep.POSTCOMBAT_MAIN, playerA);

        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertAllCommandsUsed();
        assertExileCount(playerA, 0);
        assertExileCount(playerB, 1);
        assertExileCount(playerC, 1);
        assertExileCount(playerD, 1);
    }

    /**
     * When owner loses, no new cards should be exiled and owner's exiled cards are removed from the game.
     */
    @Test
    public void ownerLoses() {
        setLife(playerA, 1);
        addCard(Zone.HAND, playerA, shareTheSpoils);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerD, "Banehound", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, shareTheSpoils);
        attack(2, playerD, "Banehound", playerA);

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertAllCommandsUsed();
        assertExileCount(playerA, 0);
        assertExileCount(playerB, 1);
        assertExileCount(playerC, 1);
        assertExileCount(playerD, 1);
    }

    /**
     * During your turn, you may cast A card from the cards exiled with share the spoils.
     */
    @Test
    public void canCastOnOwnTurn() {
        addCard(Zone.HAND, playerA, shareTheSpoils);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 8);

        // 3rd from the top, exiled when Tana is cast with Share the Spoils
        addCard(Zone.LIBRARY, playerA, "Reliquary Tower");
        // 2nd from the top, exile when Share the Spoils is cast
        addCard(Zone.LIBRARY, playerA, "Tana, the Bloodsower", 1); // {2}{R}{G}
        // Topmost, draw at beginning of turn
        addCard(Zone.LIBRARY, playerA, "Exotic Orchard");

        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, shareTheSpoils);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tana, the Bloodsower");

        setStopAt(1, PhaseStep.END_TURN);

        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Tana, the Bloodsower", 1);

        assertExileCount(playerA, "Tana, the Bloodsower", 0);
        assertExileCount(playerA, "Reliquary Tower", 1);

        assertExileCount(playerA, 1);
        assertExileCount(playerB, 1);
        assertExileCount(playerC, 1);
        assertExileCount(playerD, 1);
    }

    /**
     * During your turn, you may play A land from the cards exiled with share the spoils.
     */
    @Test
    public void playLandOnOwnTurn() {
        addCard(Zone.HAND, playerA, shareTheSpoils);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 8);

        // 3rd from the top, exiled when Exotic Orchard is played with Share the Spoils
        addCard(Zone.LIBRARY, playerA, "Reliquary Tower");
        // 2nd from the top, exile when Share the Spoils is cast
        addCard(Zone.LIBRARY, playerA, "Exotic Orchard");
        // Topmost, draw at beginning of turn
        addCard(Zone.LIBRARY, playerA, "Tana, the Bloodsower", 1); // {2}{R}{G}

        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, shareTheSpoils);
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Exotic Orchard");

        setStopAt(1, PhaseStep.END_TURN);

        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Exotic Orchard", 1);

        assertExileCount(playerA, "Exotic Orchard", 0);
        assertExileCount(playerA, "Reliquary Tower", 1);

        assertExileCount(playerA, 1);
        assertExileCount(playerB, 1);
        assertExileCount(playerC, 1);
        assertExileCount(playerD, 1);
    }

    /**
     * You cannot play a card or cast a spell when it's not your turn.
     */
    @Test
    public void cannotCastWhenNotYourTurn() {
        addCard(Zone.HAND, playerA, shareTheSpoils);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 8);

        // 3rd from the top, exiled when card is played with Share the Spoils
        addCard(Zone.LIBRARY, playerA, "Reliquary Tower");
        // 2nd from the top, exile when Share the Spoils is cast
        addCard(Zone.LIBRARY, playerA, "Lightning Bolt", 1); // {R}
        // Topmost, draw at beginning of turn
        addCard(Zone.LIBRARY, playerA, "Exotic Orchard");

        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, shareTheSpoils);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt");
        addTarget(playerA, playerB);

        setStopAt(2, PhaseStep.END_TURN);

        execute();

        boolean threwError = false;
        try {
            assertAllCommandsUsed();
        } catch (AssertionError error) {
            // Cannot cast Lightning Bolt through Share the Spoils on another player's turn, even though it's an instant.
            assert error.getMessage().equals("Player PlayerA must have 0 actions but found 1");
            threwError = true;
        }

        assert threwError;

        assertLife(playerB, 20);

        assertExileCount(playerA, "Lightning Bolt", 1);
        assertExileCount(playerA, "Reliquary Tower", 0);

        assertExileCount(playerA, 1);
        assertExileCount(playerB, 1);
        assertExileCount(playerC, 1);
        assertExileCount(playerD, 1);

        assertGraveyardCount(playerA, 0);
    }

    /**
     * You may cast A spell or play A card, you cannot do both, or multiple of either.
     */
    @Test
    public void tryToCastOrPlayASecondCard() {
        addCard(Zone.HAND, playerA, shareTheSpoils);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 8);

        // 3rd from the top, exiled when card is played with Share the Spoils
        addCard(Zone.LIBRARY, playerA, "Exotic Orchard");
        // 2nd from the top, exile when Share the Spoils is cast
        addCard(Zone.LIBRARY, playerA, "Lightning Bolt", 1); // {R}
        // Topmost, draw at beginning of turn
        addCard(Zone.LIBRARY, playerA, "Reliquary Tower");

        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, shareTheSpoils);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt");
        addTarget(playerA, playerB);
        playLand(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Exotic Orchard");

        setStopAt(2, PhaseStep.END_TURN);

        execute();

        boolean threwError = false;

        try {
            assertAllCommandsUsed();
        } catch (AssertionError error) {
            // Cannot play Exotic Orchard since it would be the second card of the turn
            assert error.getMessage().equals("Player PlayerA must have 0 actions but found 1");
            threwError = true;
        }

        assert threwError;

        assertLife(playerB, 17);

        assertExileCount(playerA, "Lightning Bolt", 0);
        assertExileCount(playerA, "Exotic Orchard", 1);

        assertExileCount(playerA, 1);
        assertExileCount(playerB, 1);
        assertExileCount(playerC, 1);
        assertExileCount(playerD, 1);

        assertGraveyardCount(playerA, 1);
    }

    /**
     * Ensure that the spending mana as if it were any color only works for cards exiled with Share the Spoils.
     */
    @Test
    public void checkManaSpendingForOtherExileSource() {
        // TODO
    }

    /**
     * Make sure that the more difficult types cards work properly.
     */
    @Test
    public void checkDifficultCards() {
        // TODO
        // Adventure
        // Modal Dualfaced cards, check both sides
        // Split card
    }

    /**
     * When Share the Spoils leaves the battlefield, the exiled cards are no longer playable.
     */
    @Test
    public void ensureCardsNotPlayable() {
        addCard(Zone.HAND, playerA, shareTheSpoils);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 8);

        // 3rd from the top, exiled when card is played with Share the Spoils
        addCard(Zone.LIBRARY, playerA, "Exotic Orchard");
        // 2nd from the top, exile when Share the Spoils is cast
        addCard(Zone.LIBRARY, playerA, "Aether Helix", 1); // {3}{G}{U}
        // Topmost, draw at beginning of turn
        addCard(Zone.LIBRARY, playerA, "Reliquary Tower");

        // Target in graveyard for Aether Helix
        addCard(Zone.GRAVEYARD, playerA, "Aether Spellbomb");

        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, shareTheSpoils);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Aether Helix");
        addTarget(playerA, shareTheSpoils);
        addTarget(playerA, "Aether Spellbomb");

        playLand(5, PhaseStep.PRECOMBAT_MAIN, playerA, "Exotic Orchard");

        setStopAt(5, PhaseStep.END_TURN);

        execute();

        boolean threwError = false;

        try {
            assertAllCommandsUsed();
        } catch (AssertionError error) {
            // Cannot play Exotic Orchard since it would be the second card of the turn
            assert error.getMessage().equals("Player PlayerA must have 0 actions but found 1");
            threwError = true;
        }

        assert threwError;

        assertExileCount(playerA, "Aether Helix", 0);
        assertExileCount(playerA, "Exotic Orchard", 1);

        assertExileCount(playerA, 1);
        assertExileCount(playerB, 1);
        assertExileCount(playerC, 1);
        assertExileCount(playerD, 1);

        assertGraveyardCount(playerA, 1);
    }

    /**
     * When this share the spoils is destroyed and it is somehow recast, it will create a new pool of cards.
     * The previous cards are no longer playable.
     */
    @Test
    public void checkDifferentCardPools() {
        addCard(Zone.HAND, playerA, shareTheSpoils);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 9);

        // 3rd from the top, exiled when card is played with Share the Spoils
        addCard(Zone.LIBRARY, playerA, "Exotic Orchard");
        // 2nd from the top, exile when Share the Spoils is cast
        addCard(Zone.LIBRARY, playerA, "Aether Helix", 1); // {3}{G}{U}
        // Topmost, draw at beginning of turn
        addCard(Zone.LIBRARY, playerA, "Reliquary Tower");

        // Target in graveyard for Aether Helix
        addCard(Zone.GRAVEYARD, playerA, "Aether Spellbomb");

        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, shareTheSpoils);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Aether Helix");
        addTarget(playerA, shareTheSpoils);
        addTarget(playerA, "Aether Spellbomb");
        // Recast, exile a new set of cards
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, shareTheSpoils);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Exotic Orchard");

        setStopAt(1, PhaseStep.END_TURN);

        execute();

        boolean threwError = false;

        try {
            assertAllCommandsUsed();
        } catch (AssertionError error) {
            // Cannot play Exotic Orchard since it would be the second card of the turn
            assert error.getMessage().equals("Player PlayerA must have 0 actions but found 1");
            threwError = true;
        }

        assert threwError;

        assertExileCount(playerA, "Aether Helix", 0);
        assertExileCount(playerA, "Exotic Orchard", 1);

        assertExileCount(playerA, 2);
        assertExileCount(playerB, 2);
        assertExileCount(playerC, 2);
        assertExileCount(playerD, 2);

        assertGraveyardCount(playerA, 1);
    }
}
