

package org.mage.test.cards.copy;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Test the funtions of Feldon of the Third Path - {1}{R}{R} Legendary Creature
 * - Human Artificer 2/3 {2}{R}, {T} : Create a tokenonto the battlefield that's
 * a copy of target creature card in your graveyard, except it's an artifact in
 * addition to its other types. It gains haste. Sacrifice it at the beginning of
 * the next end step.
 *
 *
 * @author LevelX2
 */
public class FeldonOfTheThirdPathTest extends CardTestPlayerBase {

    /**
     * Checking that enters the battlefield abilities of the copied creature
     * card works.
     *
     */
    @Test
    public void testETBEffect() {
        // When Highway Robber enters the battlefield, target opponent loses 2 life and you gain 2 life.
        addCard(Zone.GRAVEYARD, playerA, "Highway Robber", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Feldon of the Third Path", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA,
                "{2}{R}, {T}: Create a token that's a copy of target creature card in your graveyard, except it's an artifact in addition to its other types. It gains haste. Sacrifice it at the beginning of the next end step.",
                "Highway Robber");
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Highway Robber", 1);
        assertPermanentCount(playerA, "Feldon of the Third Path", 1);

        assertLife(playerA, 22); // +2 from Robber
        assertLife(playerB, 18); // -2 from Robber

    }

    @Test
    public void testETB2Effect() {
        // When Sepulchral Primordial enters the battlefield, for each opponent, you may put up to one
        // target creature card from that player's graveyard onto the battlefield under your control.
        addCard(Zone.GRAVEYARD, playerA, "Sepulchral Primordial", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Feldon of the Third Path", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        addCard(Zone.GRAVEYARD, playerB, "Silvercoat Lion", 1);

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA,
                "{2}{R}, {T}: Create a token that's a copy of target creature card in your graveyard, except it's an artifact in addition to its other types. It gains haste. Sacrifice it at the beginning of the next end step.",
                "Sepulchral Primordial");
        addTarget(playerA, "Silvercoat Lion"); // target for ETB Sepulchral Primordial
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Feldon of the Third Path", 1);
        assertPermanentCount(playerA, "Sepulchral Primordial", 1);
        assertPermanentCount(playerA, "Silvercoat Lion", 1);

        assertLife(playerA, 20);
        assertLife(playerB, 20);

    }
}
