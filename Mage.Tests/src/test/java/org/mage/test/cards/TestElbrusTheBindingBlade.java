package org.mage.test.cards;

import mage.Constants;
import mage.counters.CounterType;
import mage.players.Player;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class TestElbrusTheBindingBlade extends CardTestPlayerBase {

    @Test
    public void testCard() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Air Elemental");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Elbrus, the Binding Blade");
        
        activateAbility(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {1}", "Air Elemental");
        attack(1, playerA, "Air Elemental");


        setStopAt(1, Constants.PhaseStep.END_COMBAT);
        execute();
        
        assertLife(playerA, 20);
        assertLife(playerB, 15);
        assertPermanentCount(playerA, "Air Elemental", 1);
        assertPermanentCount(playerA, "Elbrus, the Binding Blade", 0);
        assertPermanentCount(playerA, "Withengar Unbound", 1);
    }

}
