
package org.mage.test.cards.abilities.enters;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class SepulchralPrimordialTest extends CardTestPlayerBase {

    @Test
    public void testETB2Effect() {
        // When Sepulchral Primordial enters the battlefield, for each opponent, you may put up to one
        // target creature card from that player's graveyard onto the battlefield under your control.
        addCard(Zone.HAND, playerA, "Sepulchral Primordial", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 7);

        addCard(Zone.GRAVEYARD, playerB, "Silvercoat Lion", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sepulchral Primordial");
        addTarget(playerA, "Silvercoat Lion"); // target for ETB Sepulchral Primordial
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Sepulchral Primordial", 1);
        assertPermanentCount(playerA, "Silvercoat Lion", 1);

        assertLife(playerA, 20);
        assertLife(playerB, 20);

    }

    /**
     * Sepulchral Primordial's "enter the battlefield" effect works correctly on
     * cast, but does not trigger if he is returned to the battlefield by other
     * means (e.g. summoned from the graveyard). I've encountered this in
     * 4-player commander games with other humans.
     */
    @Test
    public void testETBFromGraveyardEffect() {
        // Return target creature card from your graveyard to the battlefield. Put a +1/+1 counter on it.
        addCard(Zone.HAND, playerA, "Miraculous Recovery", 1); // Instant {4}{W}
        // When Sepulchral Primordial enters the battlefield, for each opponent, you may put up to one
        // target creature card from that player's graveyard onto the battlefield under your control.
        addCard(Zone.GRAVEYARD, playerA, "Sepulchral Primordial", 1); // Creature 5/4
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);

        addCard(Zone.GRAVEYARD, playerB, "Silvercoat Lion", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Miraculous Recovery", "Sepulchral Primordial");
        addTarget(playerA, "Silvercoat Lion"); // target for ETB Sepulchral Primordial
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Miraculous Recovery", 1);
        assertPowerToughness(playerA, "Sepulchral Primordial", 6, 5);
        assertPermanentCount(playerA, "Silvercoat Lion", 1);

        assertLife(playerA, 20);
        assertLife(playerB, 20);

    }
}
