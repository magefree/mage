package org.mage.test.cards.abilities.oneshot.sacrifice;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author noxx
 */
public class MorticianBeetleTest extends CardTestPlayerBase {

    /**
     * Checks that pro black can still be sacrificed
     */
    @Test
    public void testSacrifice() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        // Target opponent sacrifices a creature.
        addCard(Zone.HAND, playerA, "Cruel Edict");

        // Target player sacrifices a creature and loses 1 life.
        addCard(Zone.HAND, playerA, "Geth's Verdict");
        
        // Whenever a player sacrifices a creature, you may put a +1/+1 counter on Mortician Beetle.
        addCard(Zone.BATTLEFIELD, playerA, "Mortician Beetle");

        addCard(Zone.BATTLEFIELD, playerB, "Savannah Lions");
        
        // First strike
        // Exalted (Whenever a creature you control attacks alone, that creature gets +1/+1 until end of turn.)
        addCard(Zone.BATTLEFIELD, playerB, "Sigiled Paladin");


        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cruel Edict");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Geth's Verdict");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerB, 0);
        assertPowerToughness(playerA, "Mortician Beetle", 3, 3);
    }
}
