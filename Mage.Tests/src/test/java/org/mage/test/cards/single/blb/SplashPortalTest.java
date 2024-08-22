package org.mage.test.cards.single.blb;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author jimga150
 */
public class SplashPortalTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.s.SplashPortal Splash Portal} {U}
     * Sorcery
     * Exile target creature you control, then return it to the battlefield under its owner's control.
     * If that creature is a Bird, Frog, Otter, or Rat, draw a card.
     */
    private static final String splashPortal = "Splash Portal";

    @Test
    public void test_ExileOtterNonOtter() {

        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Memnite");
        // When Alania's Pathmaker enters, exile the top card of your library. Until the end of your next turn, you may play that card.
        addCard(Zone.BATTLEFIELD, playerA, "Alania's Pathmaker");
        addCard(Zone.HAND, playerA, splashPortal, 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, splashPortal, true);
        addTarget(playerA, "Memnite");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, splashPortal, true);
        addTarget(playerA, "Alania's Pathmaker");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, 1);
    }
}
