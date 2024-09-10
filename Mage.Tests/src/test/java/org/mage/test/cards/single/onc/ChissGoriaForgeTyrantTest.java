package org.mage.test.cards.single.onc;

import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import mage.constants.PhaseStep;
import mage.constants.Zone;

/**
 * @author Xanderhall
 */
public class ChissGoriaForgeTyrantTest extends CardTestPlayerBase {
    
    private static final String CHISS = "Chiss-Goria, Forge Tyrant";
    private static final String CHALICE = "Marble Chalice";
    private static final String COLOSSUS = "Blightsteel Colossus";
    private static final String GOBLIN = "Goblin Assailant";

    @Test
    public void testCastArtifact() {
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 15);
        addCard(Zone.BATTLEFIELD, playerA, CHALICE, 6);
        addCard(Zone.HAND, playerA, CHISS);
        addCard(Zone.LIBRARY, playerA, "Mountain", 4);
        addCard(Zone.LIBRARY, playerA, COLOSSUS, 1);
        skipInitShuffling();

        // Chiss-Goria should cost 3 mana, leaving 12 untapped
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, CHISS);

        attack(1, playerA, CHISS);
        // Chiss's on attack effect triggers, exiles the cards
        // Colossus should cost 6, tapping all mana
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, COLOSSUS, true);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertExileCount(playerA, 4);
        assertPermanentCount(playerA, COLOSSUS, 1);
        assertPermanentCount(playerA, CHISS, 1);
        assertTappedCount("Mountain", true, 9);
    }

    public void testCastCardGainingArtifact() {
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerA, CHALICE, 6);
        addCard(Zone.HAND, playerA, CHISS);
        addCard(Zone.HAND, playerA, "Encroaching Mycosynth", 1);
        addCard(Zone.LIBRARY, playerA, "Mountain", 4);
        addCard(Zone.LIBRARY, playerA, GOBLIN, 1);
        skipInitShuffling();

        // Chiss-Goria should cost 3 mana, leaving 1 untapped
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, CHISS);

        // Chiss's on attack effect triggers, exiles the cards, no artifacts.
        attack(1, playerA, CHISS);

        // Cast Encroaching Mycosynth, turning permanent spells into artifacts
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Encroaching Mycosynth");

        // Gobling Assailant should be castable for 1 red, leaving all lands tapped
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, GOBLIN, true);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertExileCount(playerA, 4);
        assertPermanentCount(playerA, GOBLIN, 1);
        assertPermanentCount(playerA, CHISS, 1);
        assertTappedCount("Mountain", true, 4);
        assertTappedCount("Island", true, 4);
    }

    public void testCastTwoArtifacts() {
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 7);
        addCard(Zone.BATTLEFIELD, playerA, CHALICE, 12);
        addCard(Zone.BATTLEFIELD, playerA, CHISS, 1);
        addCard(Zone.HAND, playerA, "Relentless Assault", 1);
        addCard(Zone.LIBRARY, playerA, "Mountain", 4);
        addCard(Zone.LIBRARY, playerA, COLOSSUS, 1);
        addCard(Zone.LIBRARY, playerA, "Mountain", 4);
        addCard(Zone.LIBRARY, playerA, COLOSSUS, 1);
        skipInitShuffling();

        // Chiss-Goria should cost 3 mana, leaving 4 untapped
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, CHISS);

        // Chiss's on attack effect triggers, exiles the cards
        attack(1, playerA, CHISS);

        // Add another combat phase
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Relentless Assault");

        // Chiss's effect triggers again
        attack(1, playerA, CHISS);

        // Should be able to cast both Colossuses
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, COLOSSUS, true);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, COLOSSUS, true);
        
        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertExileCount(playerA, 4);
        assertPermanentCount(playerA, COLOSSUS, 2);
        assertPermanentCount(playerA, CHISS, 1);
        assertTappedCount("Mountain", true, 7);
    }
}
