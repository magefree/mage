package org.mage.test.cards.single.neo;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author xenohedron
 */
public class HistoriansWisdomTest extends CardTestPlayerBase {

    /** 2G Aura
     Enchant artifact or creature
     When Historian’s Wisdom enters the battlefield, if enchanted permanent is a creature
            with the greatest power among creatures on the battlefield, draw a card.
     As long as enchanted permanent is a creature, it gets +2/+1.
     */

    private static final String hw = "Historian's Wisdom";
    private static final String bear = "Runeclaw Bear"; // 2/2
    private static final String chimera = "Horizon Chimera"; // 3/2 Whenever you draw a card, you gain 1 life.

    @Test
    @Ignore // apply effects in condition is not an appropriate solution
    public void testTrigger() {
        addCard(Zone.BATTLEFIELD, playerA, bear);
        addCard(Zone.BATTLEFIELD, playerA, chimera);
        addCard(Zone.HAND, playerA, hw);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, hw, bear);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, bear, 4, 3);
        assertPowerToughness(playerA, chimera, 3, 2);
        assertPermanentCount(playerA, hw, 1);
        assertAttachedTo(playerA, hw, bear, true);
        assertHandCount(playerA, 1);
        assertLife(playerA, 21);
        assertLife(playerB, 20);
    }

}
