package org.mage.test.cards.control;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Tests the effect:
 *   - Exile target creature you control, then return that card to the battlefield under your control
 *
 * This effect grants you permanent control over the returned creature.
 * So you mail steal opponent's creature with "Act of Treason" and then use this effect for permanent control effect.
 *
 * @author noxx
 */
public class ExileAndReturnUnderYourControl extends CardTestPlayerBase {

    @Test
    public void testPermanentControlEffect() {
        addCard(Zone.HAND, playerA, "Cloudshift");
        addCard(Zone.HAND, playerA, "Act of Treason");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        addCard(Zone.BATTLEFIELD, playerB, "Elite Vanguard");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Act of Treason", "Elite Vanguard");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cloudshift", "Elite Vanguard");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Elite Vanguard", 1);
    }
}
