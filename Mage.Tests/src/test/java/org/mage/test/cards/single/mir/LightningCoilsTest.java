package org.mage.test.cards.single.mir;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author escplan9 (Derek Monturo)
 */
public class LightningCoilsTest extends CardTestPlayerBase {

    @Test
    public void sacrificeSixCreaturesProducesSixElementals() {

        /*
        Lightning Coils {3}
        Artifact
        Whenever a nontoken creature you control dies, put a charge counter on Lightning Coils.
        At the beginning of your upkeep, if Lightning Coils has five or more charge counters on it, remove all of them from it 
        and create that many 3/1 red Elemental creature tokens with haste. Exile them at the beginning of the next end step.
        */
        String lCoils = "Lightning Coils";
        
        /*
        Bottle Gnomes {3}
        Artifact Creature — Gnome
        Sacrifice Bottle Gnomes: You gain 3 life.
        */
        String bGnomes = "Bottle Gnomes";
        int gnomeCount = 6;
        
        addCard(Zone.BATTLEFIELD, playerA, lCoils);
        addCard(Zone.BATTLEFIELD, playerA, bGnomes, gnomeCount);
        
        for (int i = 0; i < gnomeCount; i++) // sac Gnomes 6 times
            activateAbility(2, PhaseStep.POSTCOMBAT_MAIN, playerA, "Sacrifice");
        
        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();
        
        assertGraveyardCount(playerA, bGnomes, gnomeCount);
        assertPermanentCount(playerA, lCoils, 1);
        assertCounterCount(playerA, lCoils, CounterType.CHARGE, 0);
        assertPermanentCount(playerA, "Elemental", gnomeCount);
    }
}
