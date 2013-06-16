package org.mage.test.cards.cost.sacrifice;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author noxx
 */
public class MomentousFallTest extends CardTestPlayerBase {

    @Test
    public void testSacrificeCostAndLKI() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.HAND, playerA, "Momentous Fall");
        addCard(Zone.BATTLEFIELD, playerA, "Geralf's Messenger", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Glorious Anthem", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Momentous Fall");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Geralf's Messenger", 1);
        assertPowerToughness(playerA, "Geralf's Messenger", 5, 4); // +1/+1 counter + Anthem effect
        assertHandCount(playerA, 4); // +4 cards
        assertLife(playerA, 23); // +3 life
    }

    @Test
    public void testSacrificeCostForProGreen() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.HAND, playerA, "Momentous Fall");
        addCard(Zone.BATTLEFIELD, playerA, "Mirran Crusader");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Momentous Fall");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Mirran Crusader", 0);
        assertHandCount(playerA, 2); // +2 cards
        assertLife(playerA, 22); // +2 life
    }


}
