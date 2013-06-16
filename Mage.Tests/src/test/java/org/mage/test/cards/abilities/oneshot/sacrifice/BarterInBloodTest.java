package org.mage.test.cards.abilities.oneshot.sacrifice;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author noxx
 */
public class BarterInBloodTest extends CardTestPlayerBase {

    /**
     * Checks that both creatures will be sacrificed
     */
    @Test
    public void testSacrifice() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.HAND, playerA, "Barter in Blood");
        
        addCard(Zone.BATTLEFIELD, playerA, "Memnite");
        addCard(Zone.BATTLEFIELD, playerA, "Royal Assassin");
        addCard(Zone.BATTLEFIELD, playerA, "Sengir Vampire");

        addCard(Zone.BATTLEFIELD, playerB, "Island");
        addCard(Zone.BATTLEFIELD, playerB, "Flowering Lumberknot");
        addCard(Zone.BATTLEFIELD, playerB, "Moorland Inquisitor");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Barter in Blood");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, 5);
        assertPermanentCount(playerB, 1);
    }
}
