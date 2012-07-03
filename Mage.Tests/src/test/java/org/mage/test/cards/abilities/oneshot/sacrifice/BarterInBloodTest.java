package org.mage.test.cards.abilities.oneshot.sacrifice;

import mage.Constants;
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
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Constants.Zone.HAND, playerA, "Barter in Blood");
        
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Memnite");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Royal Assassin");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Sengir Vampire");

        addCard(Constants.Zone.BATTLEFIELD, playerB, "Island");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Flowering Lumberknot");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Moorland Inquisitor");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Barter in Blood");

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, 5);
        assertPermanentCount(playerB, 1);
    }
}
