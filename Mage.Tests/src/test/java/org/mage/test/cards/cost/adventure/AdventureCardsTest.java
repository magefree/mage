package org.mage.test.cards.cost.adventure;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class AdventureCardsTest extends CardTestPlayerBase {
    @Test
    public void testCastTreatsToShare() {
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
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.HAND, playerA, "Curious Pair");
        addCard(Zone.HAND, playerA, "Opt");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Treats to Share");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);

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
}
