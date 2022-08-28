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
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);

        setStrictChooseMode(true);
        execute();

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

        setStrictChooseMode(true);
        execute();

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

        setStrictChooseMode(true);
        execute();

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
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        concede(1, PhaseStep.POSTCOMBAT_MAIN, playerA);

        setStopAt(1, PhaseStep.END_TURN);

        setStrictChooseMode(true);
        execute();

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
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        attack(2, playerD, "Banehound", playerA);

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);

        setStrictChooseMode(true);
        execute();

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
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tana, the Bloodsower");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        setStopAt(1, PhaseStep.END_TURN);

        setStrictChooseMode(true);
        execute();

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
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Exotic Orchard");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        setStopAt(1, PhaseStep.END_TURN);

        setStrictChooseMode(true);
        execute();

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

        // 2nd from the top, exile when Share the Spoils is cast
        addCard(Zone.LIBRARY, playerA, "Lightning Bolt", 1); // {R}
        // Topmost, draw at beginning of turn
        addCard(Zone.LIBRARY, playerA, "Exotic Orchard");

        addCard(Zone.LIBRARY, playerB, "Reliquary Tower");

        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, shareTheSpoils);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        checkPlayableAbility("normal cast", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Lightning Bolt", false);
        checkPlayableAbility("before play", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Play Reliquary Tower", false);

        setStopAt(2, PhaseStep.END_TURN);

        setStrictChooseMode(true);
        execute();

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
        addCard(Zone.LIBRARY, playerA, "Lightning Bolt", 1); // {R}
        // 2nd from the top, exile when Share the Spoils is cast
        addCard(Zone.LIBRARY, playerA, "Exotic Orchard");
        // Topmost, draw at beginning of turn
        addCard(Zone.LIBRARY, playerA, "Reliquary Tower");

        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, shareTheSpoils);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Exotic Orchard");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        // Abilities available after casting with Share the Spoils
        checkPlayableAbility("Available Abilities", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Lightning Bolt", false);

        setStopAt(1, PhaseStep.END_TURN);

        setStrictChooseMode(true);
        execute();

        assertExileCount(playerA, "Exotic Orchard", 0);
        assertExileCount(playerA, "Lightning Bolt", 1);

        assertExileCount(playerA, 1);
        assertExileCount(playerB, 1);
        assertExileCount(playerC, 1);
        assertExileCount(playerD, 1);
    }

    /**
     * Ensure that the spending mana as if it were any color only works for cards exiled with Share the Spoils.
     */
    @Test
    public void checkManaSpendingForOtherExileSource() {
        addCard(Zone.HAND, playerA, shareTheSpoils);
        addCard(Zone.HAND, playerA, "Augury Raven");
        addCard(Zone.BATTLEFIELD, playerA, "Prosper, Tome-Bound");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 8);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);

        // 3rd from the top, exile at end of turn with propser
        addCard(Zone.LIBRARY, playerA, "Tana, the Bloodsower", 1); // {2}{R}{G}
        // 2nd from the top, exile when Share the Spoils is cast
        addCard(Zone.LIBRARY, playerA, "Exotic Orchard");
        // Topmost, draw at beginning of turn
        addCard(Zone.LIBRARY, playerA, "Ardenvale Tactician"); // Adventure part is "Dizzying Swoop"

        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, shareTheSpoils);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        // Foretell Augury Raven
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Foretell");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // Try to cast Tana, you should not be able to since there isn't the {G} for it since she was exiled by Proser
        checkPlayableAbility("normal cast", 5, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Tana, the Bloodsower", false);

        // Try to activate the foretell on Augury Raven, but we can't since we don't have the {U} for it.
        checkPlayableAbility("foretell creature cast", 5, PhaseStep.PRECOMBAT_MAIN, playerA, "Foretell {1}{U}", false);

        // Cast an adventure card from hand
        castSpell(5, PhaseStep.PRECOMBAT_MAIN, playerA, "Dizzying Swoop");
        addTarget(playerA, "Prosper, Tome-Bound");
        waitStackResolved(5, PhaseStep.PRECOMBAT_MAIN);

        // Make sure the creature card can't be played from exile since there isn't the {W}{W} for it
        checkPlayableAbility("creature cast", 5, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Ardenvale Tactician", false);

        setStopAt(5, PhaseStep.POSTCOMBAT_MAIN);

        setStrictChooseMode(true);
        execute();

        // 1 exiled with Share the Spoils
        // 1 exiled Prosper (he only exiles one since we stop before the end step of playerA's second turn)
        // 1 for the foretold Augury Raven
        // 1 for the Dizzying Swoop Adventure
        assertExileCount(playerA, 4);
        assertExileCount(playerB, 1);
        assertExileCount(playerC, 1);
        assertExileCount(playerD, 1);
    }

    /**
     * Make sure that the more difficult types cards work properly.
     */
    @Test
    public void checkDifficultCards() {
        addCard(Zone.HAND, playerA, shareTheSpoils);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 8);

        // 3rd from the top, exile Lovestruck Beast is cast
        addCard(Zone.LIBRARY, playerA, "Ardenvale Tactician"); // Adventure, creature half {1}{W}{W}
        // 2nd from the top, exile when Share the Spoils is cast
        addCard(Zone.LIBRARY, playerA, "Lovestruck Beast"); // Adventure, adventure half "Heart's Desire" {G}
        // Topmost, draw at beginning of turn
        addCard(Zone.LIBRARY, playerA, "Mountain");

        // Modal dual face, cast front
        addCard(Zone.LIBRARY, playerB, "Alrund, God of the Cosmos"); // Backside is "Hakka, Whispering Raven"
        // Modal fual face, cast back
        addCard(Zone.LIBRARY, playerC, "Esika, God of the Tree"); // Backside is "The Prismatic Bridge"
        // Split card
        addCard(Zone.LIBRARY, playerD, "Fire // Ice");

        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, shareTheSpoils);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        // Cast the Adventure half of an Adventure card
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Heart's Desire");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        // Cast the Creature half of an Adventure card
        castSpell(5, PhaseStep.PRECOMBAT_MAIN, playerA, "Ardenvale Tactician");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        // Cast split card
        castSpell(9, PhaseStep.PRECOMBAT_MAIN, playerA, "Ice");
        addTarget(playerA, "Mountain");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        // Cast front side of Modal dual face card
        castSpell(13, PhaseStep.PRECOMBAT_MAIN, playerA, "Alrund, God of the Cosmos");
        setChoice(playerA, "Land");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        // Cast back side of Modal dual face card
        castSpell(17, PhaseStep.PRECOMBAT_MAIN, playerA, "The Prismatic Bridge");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        setStopAt(17, PhaseStep.POSTCOMBAT_MAIN);

        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, "Ardenvale Tactician", 1);

        assertExileCount(playerA, "Lovestruck Beast", 1);

        // 1 from Share the Spoils exiling a card for ETB
        // 1 from casting the Adventur half of a card (Heart's Desire) and leaving it in exile
        // 1 from casting "Ice"
        // 1 from casting "Alrund, God of the Cosmos"
        // 1 from casting "The Prismatic Bridge
        assertExileCount(playerA, 5);
        // 0 since playerA cast their card and replaced it with one of their own
        assertExileCount(playerB, 0);
        // 0 since playerA cast their card and replaced it with one of their own
        assertExileCount(playerC, 0);
        // 0 since playerA cast their card and replaced it with one of their own
        assertExileCount(playerD, 0);

        // Ice is the only card that went in here
        assertGraveyardCount(playerD, 1);
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
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Aether Helix");
        addTarget(playerA, shareTheSpoils);
        addTarget(playerA, "Aether Spellbomb");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        checkPlayableAbility("before play", 5, PhaseStep.PRECOMBAT_MAIN, playerA, "Play Exotic Orchard", false);

        setStopAt(5, PhaseStep.END_TURN);

        setStrictChooseMode(true);
        execute();

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
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        // Casting Aether Helix from exile with Share the spoils.
        // Doing so exiles Exotic Orchard.
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Aether Helix");
        addTarget(playerA, shareTheSpoils);
        addTarget(playerA, "Aether Spellbomb");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        // Recast, exile a new set of cards
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, shareTheSpoils);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        // Exotic Orchard was exile by the first Share the Spoils, so can't be cast again with the new one
        checkPlayableAbility("before play", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Play Exotic Orchard", false);

        setStopAt(1, PhaseStep.END_TURN);

        setStrictChooseMode(true);
        execute();

        assertExileCount(playerA, "Aether Helix", 0);
        assertExileCount(playerA, "Exotic Orchard", 1);

        assertExileCount(playerA, 2);
        assertExileCount(playerB, 2);
        assertExileCount(playerC, 2);
        assertExileCount(playerD, 2);

        assertGraveyardCount(playerA, 1);
    }

    /**
     * When a card exiled by Share the Spoils is played, another card is exiled.
     * Check that this newly exiled card is correctly taken from the deck of the player who played the card,
     * AND NOT from the controller of Share the Spoils.
     *
     * For https://github.com/magefree/mage/issues/9046
     */
    @Test
    public void checkExileFromCorrectDeck() {
        addCard(Zone.HAND, playerA, shareTheSpoils);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 8);

        // 3rd from the top, exiled when card is played with Share the Spoils
        addCard(Zone.LIBRARY, playerA, "Lightning Bolt", 1); // {R}
        // 2nd from the top, exile when Share the Spoils is cast
        addCard(Zone.LIBRARY, playerA, "Exotic Orchard");
        // Topmost, draw at beginning of turn
        addCard(Zone.LIBRARY, playerA, "Reliquary Tower");

        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, shareTheSpoils);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        playLand(2, PhaseStep.PRECOMBAT_MAIN, playerD, "Exotic Orchard");
        waitStackResolved(2, PhaseStep.PRECOMBAT_MAIN, playerD);

        setStopAt(2, PhaseStep.END_TURN);

        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerD, "Exotic Orchard",1);

        assertExileCount(playerA, 0); // playerA's Exotic Orchard was played by playerD
        assertExileCount(playerB, 1);
        assertExileCount(playerC, 1);
        assertExileCount(playerD, 2); // 2nd card exiled when they played the Exotic Orchard
    }
}
