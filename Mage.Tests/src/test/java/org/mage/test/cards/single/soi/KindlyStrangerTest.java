package org.mage.test.cards.single.soi;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class KindlyStrangerTest extends CardTestPlayerBase {
    
    /*
     * Reported bug: When Kindly Stranger transforms, does not allow destroying target creature
     */
    @Test
    public void transformDestroyCreature() {
        
        // Delirium {2}{B}: Transform Kindly Stranger. Activate this ability only if there are four or more card types among cards in your graveyard.
        // When this creature transforms into Demon-Possessed Witch, you may destroy target creature.
        addCard(Zone.BATTLEFIELD, playerA, "Kindly Stranger");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.GRAVEYARD, playerA, "Bronze Sable", 1);
        addCard(Zone.GRAVEYARD, playerA, "Bitterblossom", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Hill Giant", 1);
        
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "<i>Delirium");
        addTarget(playerA, "Hill Giant");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertPermanentCount(playerA, "Demon-Possessed Witch", 1);
        assertPermanentCount(playerA, "Kindly Stranger", 0);
        assertGraveyardCount(playerB, "Hill Giant", 1);
    }
}
