/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mage.test.cards.abilities.enters;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/* Gather Specimens - Instant {3}{U}{U}{U}
 *
 * If a creature would enter the battlefield under an opponent's control this turn, it enters the battlefield under your control instead.
 *
 * @author jeffwadsworth
 */
public class GatherSpecimensTest extends CardTestPlayerBase {

    @Test
    public void testFromHandEffect() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        addCard(Zone.HAND, playerA, "Gather Specimens", 1);

        addCard(Zone.LIBRARY, playerB, "Mountain", 1);
        addCard(Zone.HAND, playerB, "Memnite", 1); // Artifact Creature (1/1) {0}

        castSpell(2, PhaseStep.UPKEEP, playerA, "Gather Specimens");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Memnite");

        setStopAt(2, PhaseStep.END_TURN);

        execute();

        assertAllCommandsUsed();
        
        assertGraveyardCount(playerA, "Gather Specimens", 1);
        
        assertPermanentCount(playerA, "Memnite", 1);
        assertPermanentCount(playerB, "Memnite", 0);
    }

   @Test
    public void testTokenCreatedFromSpellEffect() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        addCard(Zone.HAND, playerA, "Gather Specimens", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Plains", 3);
        addCard(Zone.HAND, playerB, "Spectral Procession", 1);

        castSpell(2, PhaseStep.UPKEEP, playerA, "Gather Specimens");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Spectral Procession");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);

        execute();

        assertPermanentCount(playerA, "Spirit Token", 3);
        assertPermanentCount(playerB, "Spirit Token", 0);
    }

    @Test
    public void testFromGraveyardEffect() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        addCard(Zone.HAND, playerA, "Gather Specimens", 1);
        addCard(Zone.LIBRARY, playerA, "Memnite", 10);

        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 5);
        addCard(Zone.HAND, playerB, "Dread Return", 1);
        addCard(Zone.GRAVEYARD, playerB, "Memnite", 1);
        addCard(Zone.LIBRARY, playerB, "Swamp", 10);

        castSpell(2, PhaseStep.UPKEEP, playerA, "Gather Specimens");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Dread Return", "Memnite");

        setStopAt(2, PhaseStep.END_TURN);

        execute();

        assertPermanentCount(playerA, "Memnite", 1);
        assertPermanentCount(playerB, "Memnite", 0);
    }

    @Test
    public void testFromExileEffect() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 10);
        addCard(Zone.HAND, playerA, "Disenchant", 1);
        addCard(Zone.HAND, playerA, "Oblivion Ring", 1);
        addCard(Zone.HAND, playerA, "Gather Specimens", 1);
        addCard(Zone.LIBRARY, playerA, "Plains", 10);

        addCard(Zone.BATTLEFIELD, playerB, "Memnite", 1);
        addCard(Zone.LIBRARY, playerB, "Plains", 10);

        setStrictChooseMode(true);

        // If a creature would enter the battlefield under an opponent's control this turn, it enters the battlefield under your control instead.
        castSpell(1, PhaseStep.UPKEEP, playerA, "Gather Specimens");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Oblivion Ring");
        addTarget(playerA, "Memnite");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Disenchant", "Oblivion Ring");

        setStopAt(1, PhaseStep.END_TURN);

        execute();

        assertPermanentCount(playerB, "Memnite", 0);
        assertPermanentCount(playerA, "Memnite", 1);
    }
}
