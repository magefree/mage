package org.mage.test.cards.single.lci;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author xenohedron
 */
public class ShipwreckSentryTest extends CardTestPlayerBase {

    /**
     Shipwreck Sentry {1}{U}
     Creature — Human Pirate
     Defender
     As long as an artifact entered the battlefield under your control this turn, Shipwreck Sentry can attack as though it didn’t have defender.
     */
    private static final String sentry = "Shipwreck Sentry";

    // {0} Artifact
    private static final String relic = "Darksteel Relic";

    @Test
    public void testCantAttack() {
        addCard(Zone.BATTLEFIELD, playerA, sentry);
        addCard(Zone.HAND, playerA, relic);

        attack(1, playerA, sentry, playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        try {
            execute();
        } catch (AssertionError e) {
            Assert.assertTrue("No artifact entered",
                    e.getMessage().contains("Can't find available command - attack:Shipwreck Sentry$defendingPlayer=PlayerB"));
        }

    }

    @Test
    public void testCanAttack() {
        addCard(Zone.BATTLEFIELD, playerA, sentry);
        addCard(Zone.HAND, playerA, relic);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, relic);
        attack(1, playerA, sentry, playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, relic, 1);
        assertTappedCount(sentry, true, 1);
        assertLife(playerB, 17);

    }

}
