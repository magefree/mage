package org.mage.test.cards.single.all;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class SoldeviSentryTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.s.SoldeviSentry} <br>
     * Soldevi Sentry {1} <br>
     * Artifact Creature — Soldier <br>
     * {1}: Choose target opponent. Regenerate Soldevi Sentry. When it regenerates this way, that player may draw a card. <br>
     * 1/1
     */
    private static final String sentry = "Soldevi Sentry";

    @Test
    public void test_ReflexiveOnRegenerate() {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, sentry);
        addCard(Zone.HAND, playerA, "Doom Blade");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);

        activateAbility(1, PhaseStep.UPKEEP, playerA, "{1}: Choose", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Doom Blade", sentry);
        setChoice(playerB, true); // yes to drawing a card in the reflexive trigger

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, sentry, 1);
        assertTapped(sentry, true);
        assertHandCount(playerB, 1);
    }
}
