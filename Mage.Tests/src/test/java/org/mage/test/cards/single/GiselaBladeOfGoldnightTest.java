package org.mage.test.cards.single;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Gisela, Blade of Goldnight:
 *   If a source would deal damage to an opponent or a permanent an opponent controls, that source deals double that damage to that player or permanent instead.
 *   If a source would deal damage to you or a permanent you control, prevent half that damage, rounded up.
 *
 * @author noxx
 */
public class GiselaBladeOfGoldnightTest extends CardTestPlayerBase {

    @Test
    public void testCard() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Gisela, Blade of Goldnight");
        addCard(Zone.BATTLEFIELD, playerA, "Devout Chaplain");
        addCard(Zone.BATTLEFIELD, playerA, "Corpse Traders");
        addCard(Zone.HAND, playerA, "Lightning Bolt", 4);
        addCard(Zone.HAND, playerA, "Shock");

        addCard(Zone.BATTLEFIELD, playerB, "Air Elemental", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Elite Vanguard", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shock", "Air Elemental");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Devout Chaplain");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Llanowar Elves");

        attack(2, playerB, "Elite Vanguard");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        // 1 from Lightning Bolt + 1 from Elite Vanguard
        assertLife(playerA, 18);
        assertLife(playerB, 14);

        assertPermanentCount(playerA, "Devout Chaplain", 1);
        assertPermanentCount(playerA, "Llanowar Elves", 0);

        assertPermanentCount(playerB, "Air Elemental", 0);
    }

}
