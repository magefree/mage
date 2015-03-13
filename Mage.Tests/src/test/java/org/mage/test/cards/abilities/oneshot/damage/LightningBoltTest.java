package org.mage.test.cards.abilities.oneshot.damage;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author ayrat
 */
public class LightningBoltTest extends CardTestPlayerBase {

    @Test
    public void testDamageOpponent() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertLife(playerA, 20);
        assertLife(playerB, 17);
    }

    @Test
    public void testDamageSelf() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerA);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertLife(playerA, 17);
        assertLife(playerB, 20);
    }

    @Test
    public void testDamageSmallCreature() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerB, "Sejiri Merfolk");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Sejiri Merfolk");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertPermanentCount(playerB, "Sejiri Merfolk", 0);
    }

    @Test
    public void testDamageBigCreature() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerB, "Craw Wurm");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Craw Wurm");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertPermanentCount(playerB, "Craw Wurm", 1);
    }

    @Test
    public void testDamageBigCreatureTwice() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerB, "Craw Wurm");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Craw Wurm");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Craw Wurm");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertPermanentCount(playerB, "Craw Wurm", 0);
    }

    @Test
    public void testDamageTarmagoyf() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        
        addCard(Zone.GRAVEYARD, playerB, "Forest");
        // Destroy target creature with defender.
        addCard(Zone.GRAVEYARD, playerB, "Clear a Path");
        // Tarmogoyf's power is equal to the number of card types among cards in all graveyards and its toughness is equal to that number plus 1. 
        // (Artifact, creature, enchantment, instant, land, planeswalker, sorcery, and tribal are card types.)
        addCard(Zone.BATTLEFIELD, playerB, "Tarmogoyf");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Tarmogoyf");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        // Tarmogoyf is a 2/3 that then takes 3 damage, then becomes a 3/4 as the 
        // Lightning Bolt is put into the graveyard and then state base actions are checked and Tarmogoyf survives.
        // First, the Tarmogoyf will receive the damage, then Lightning Bolt goes to the graveyard. 
        // Then the active player would receive priority - which means, that right before that happens, state-based 
        // effects are checked: the game sees a 3/4 creature will 3 damage on it, so nothing happens. 
        // Then the active player receives priority.
        assertPermanentCount(playerB, "Tarmogoyf", 1);
    }

}
