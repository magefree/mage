package org.mage.test.cards.single.tsp;

import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Created by Alexsandr0x.
 */


public class HivestoneTest extends CardTestPlayerBase {

    /**
     * If a creature is already a Sliver, Hivestone has no effect on it.
     */
    @Test
    public void abilityCheckTest() {
        // Coloca criatura qualquer em campo, uma no player A e outra no player B, checa se
        // receberam o subtipo
    }

    /**
     * Turns only your creatures on the battlefield, not in other zones, into Slivers. It won’t allow you to have
     * Root Sliver on the battlefield and make your Grizzly Bears uncounterable, for example.
     */
    @Test
    public void rootSliverTest() {
        // com o root sliver e a hivestone em campo, tenta counterar uma carta que não é sliver vinda
        // da mão (tem que funcionar, ja que a carta não é sliver na stack)
    }
}
