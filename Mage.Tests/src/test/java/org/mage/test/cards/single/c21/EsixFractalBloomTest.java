package org.mage.test.cards.single.c21;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.e.EsixFractalBloom Esix, Fractal Bloom}
 * {4}{G}{U}
 * Legendary Creature — Fractal
 * Flying
 *
 * The first time you would create one or more tokens during each of your turns,
 * you may instead choose a creature other than Esix, Fractal Bloom
 * and create that many tokens that are copies of that creature.
 *
 * @author Alex-Vasile
 */
public class EsixFractalBloomTest extends CardTestPlayerBase {

    private static final String esix = "Esix, Fractal Bloom";

    /**
     * Reported bug: https://github.com/magefree/mage/issues/8117
     *      Even if Esix's ability is used on the tokens created by Nactl War-Pride, those tokens should still be
     *      exiled at the beginning of the next end step.
     */
    @Test
    public void tokensKeepSacrificeEffect() {
        // Nacatl War-Pride must be blocked by exactly one creature if able.
        // Whenever Nacatl War-Pride attacks, create X tokens that are copies of Nacatl War-Pride and that are tapped and attacking,
        // where X is the number of creatures defending player controls.
        // Exile the tokens at the beginning of the next end step.
        String nacatl = "Nacatl War-Pride";
        addCard(Zone.BATTLEFIELD, playerA, esix);
        addCard(Zone.BATTLEFIELD, playerA, nacatl);
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 3);

        setStrictChooseMode(true);

        attack(1, playerA, nacatl);
        setChoice(playerA, "Silvercoat Lion");

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();
        assertPermanentCount(playerA, nacatl, 1);
        assertPermanentCount(playerA, "Silvercoat Lion", 3);

        setStopAt(2, PhaseStep.UPKEEP);
        execute();
        assertPermanentCount(playerA, nacatl, 1);
        assertPermanentCount(playerA, "Silvercoat Lion", 0);
        assertPermanentCount(playerA, 2); // Esix + Nactl
    }
}
