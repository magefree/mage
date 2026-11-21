package org.mage.test.cards.single.dmu;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.l.LlanowarGreenwidow Llanowar Greenwidow} {2}{G}, 4/3
 * Reach, trample
 * Domain -- {7}{G}: Return Llanowar Greenwidow from your graveyard to the battlefield tapped. It gains
 * "If this permanent would leave the battlefield, exile it instead of putting it anywhere else."
 * This ability costs {1} less to activate for each basic land type among lands you control.
 *
 * @author notgreat, Claude Opus 5.5
 */
public class LlanowarGreenwidowTest extends CardTestPlayerBase {

    private static final String greenwidow = "Llanowar Greenwidow";

    /**
     * The Greenwidow that comes back keeps the replacement it gained on the way, so when it dies it
     * is exiled.
     */
    @Test
    public void testReturnedGreenwidowIsExiledWhenItDies() {
        addCard(Zone.BATTLEFIELD, playerA, "Taiga", 7); // Mountain Forest: domain 2, so {5}{G}
        addCard(Zone.GRAVEYARD, playerA, greenwidow);
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "<i>Domain</i>");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", greenwidow);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, greenwidow, 0);
        assertGraveyardCount(playerA, greenwidow, 0);
        assertExileCount(playerA, greenwidow, 1);
    }
}
