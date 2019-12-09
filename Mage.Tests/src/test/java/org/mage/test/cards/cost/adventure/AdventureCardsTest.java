package org.mage.test.cards.cost.adventure;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class AdventureCardsTest extends CardTestPlayerBase {
    @Test
    public void testCastTreatsToShare() {
        /*
         * Curious Pair {1}{G}
         * Creature — Human Peasant
         * 1/3
         * ----
         * Treats to Share {G}
         * Sorcery — Adventure
         * Create a Food token.
         */
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.HAND, playerA, "Curious Pair");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Treats to Share");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertHandCount(playerA, 0);
        assertPermanentCount(playerA, "Food", 1);
        assertExileCount(playerA, "Curious Pair", 1);
        assertGraveyardCount(playerA,0);
        assertAllCommandsUsed();
    }

    @Test
    public void testCastCuriousPair() {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.HAND, playerA, "Curious Pair");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Curious Pair");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertHandCount(playerA, 0);
        assertPermanentCount(playerA, "Food", 0);
        assertPermanentCount(playerA, "Curious Pair", 1);
        assertExileCount(playerA, "Curious Pair", 0);
        assertGraveyardCount(playerA,0);
        assertAllCommandsUsed();
    }

    @Test
    public void testCastTreatsToShareAndCuriousPair() {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.HAND, playerA, "Curious Pair");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Treats to Share");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Curious Pair");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertHandCount(playerA, 0);
        assertPermanentCount(playerA, "Food", 1);
        assertPermanentCount(playerA, "Curious Pair", 1);
        assertExileCount(playerA, "Curious Pair", 0);
        assertGraveyardCount(playerA, 0);
        assertAllCommandsUsed();
    }

    @Test
    public void testCastTreatsToShareWithEdgewallInnkeeper() {
        /*
         * Edgewall Innkeeper {G}
         * Creature — Human Peasant
         * Whenever you cast a creature spell that has an Adventure, draw a card.
         * 1/1
         */
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Edgewall Innkeeper");
        addCard(Zone.HAND, playerA, "Curious Pair");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Treats to Share");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertHandCount(playerA, 0);
        assertPermanentCount(playerA, "Food", 1);
        assertPermanentCount(playerA, "Curious Pair", 0);
        assertExileCount(playerA, "Curious Pair", 1);
        assertGraveyardCount(playerA, 0);
        assertAllCommandsUsed();
    }

    @Test
    public void testCastCuriousPairWithEdgewallInnkeeper() {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Edgewall Innkeeper");
        addCard(Zone.HAND, playerA, "Curious Pair");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Curious Pair");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertHandCount(playerA, 1);
        assertPermanentCount(playerA, "Food", 0);
        assertPermanentCount(playerA, "Curious Pair", 1);
        assertExileCount(playerA, "Curious Pair", 0);
        assertGraveyardCount(playerA,0);
        assertAllCommandsUsed();
    }

    @Test
    public void testCastTreatsToShareAndCuriousPairWithEdgewallInnkeeper() {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Edgewall Innkeeper");
        addCard(Zone.HAND, playerA, "Curious Pair");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Treats to Share");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Curious Pair");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertHandCount(playerA, 1);
        assertPermanentCount(playerA, "Food", 1);
        assertPermanentCount(playerA, "Curious Pair", 1);
        assertExileCount(playerA, "Curious Pair", 0);
        assertGraveyardCount(playerA, 0);
        assertAllCommandsUsed();
    }

    @Test
    public void testCastMemoryTheft() {
        /*
         * Memory Theft {2}{B}
         * Sorcery
         * Target opponent reveals their hand. You choose a nonland card from it. That player discards that card.
         * You may put a card that has an Adventure that player owns from exile into that player's graveyard.
         */
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.HAND, playerA, "Curious Pair");
        addCard(Zone.HAND, playerA, "Opt");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Treats to Share");

        addCard(Zone.BATTLEFIELD, playerB, "Swamp");
        addCard(Zone.BATTLEFIELD, playerB, "Swamp");
        addCard(Zone.BATTLEFIELD, playerB, "Swamp");
        addCard(Zone.HAND, playerB, "Memory Theft");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Memory Theft", playerA);
        playerB.addChoice("Opt");
        playerB.addChoice("Curious Pair");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, 0);
        assertExileCount(playerA, "Curious Pair", 0);
        assertGraveyardCount(playerA, 2);
        assertAllCommandsUsed();
    }

    @Test
    public void testCastTreatsToShareWithLuckyClover() {
        /*
         * Lucky Clover {2}
         * Artifact
         * Whenever you cast an Adventure instant or sorcery spell, copy it. You may choose new targets for the copy.
         */
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Lucky Clover");
        addCard(Zone.HAND, playerA, "Curious Pair");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Treats to Share");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, 0);
        assertPermanentCount(playerA, "Food", 2);
        assertPermanentCount(playerA, "Curious Pair", 0);
        assertExileCount(playerA, "Curious Pair", 1);
        assertGraveyardCount(playerA, 0);
        assertAllCommandsUsed();
    }

    @Test
    public void testCastTreatsToShareAndCopy() {
        /*
         * Fork {R}{R}
         * Instant
         * Copy target instant or sorcery spell, except that the copy is red. You may choose new targets for the copy.
         */
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.HAND, playerA, "Curious Pair");
        addCard(Zone.HAND, playerA, "Fork");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Treats to Share");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fork", "Treats to Share");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, 0);
        assertPermanentCount(playerA, "Food", 2);
        assertPermanentCount(playerA, 5);
        assertExileCount(playerA, "Curious Pair", 1);
        assertExileCount(playerA, 1);
        assertGraveyardCount(playerA, "Fork", 1);
        assertGraveyardCount(playerA, 1);
        assertAllCommandsUsed();
    }
}
