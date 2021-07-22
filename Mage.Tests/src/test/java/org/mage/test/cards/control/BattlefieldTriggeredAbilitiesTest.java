package org.mage.test.cards.control;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author noxx
 */
public class BattlefieldTriggeredAbilitiesTest extends CardTestPlayerBase {

    @Test
    public void testBeguilerofWillsAndPrimevalTitan() {
        
        // Whenever Primeval Titan enters the battlefield or attacks, you may search your library for up to two land cards, 
        // put them onto the battlefield tapped, then shuffle your library.
        addCard(Zone.BATTLEFIELD, playerA, "Primeval Titan");
        addCard(Zone.LIBRARY, playerA, "Mountain", 10);

        // {T}: Gain control of target creature with power less than or equal to the number of creatures you control.
        addCard(Zone.BATTLEFIELD, playerB, "Beguiler of Wills");
        
        // Whenever Arrogant Bloodlord blocks or becomes blocked by a creature with power 1 or less, destroy Arrogant Bloodlord at end of combat.
        addCard(Zone.BATTLEFIELD, playerB, "Arrogant Bloodlord", 5);
        addCard(Zone.LIBRARY, playerB, "Mountain", 10);

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "{T}: Gain control", "Primeval Titan");
        attack(4, playerB, "Primeval Titan");

        setStopAt(4, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 14);
        assertLife(playerB, 20);

        assertPermanentCount(playerB, "Beguiler of Wills", 1);
        assertPermanentCount(playerB, "Arrogant Bloodlord", 5);
        assertPermanentCount(playerB, "Primeval Titan", 1);
        
        // lands weren't added to playerA
        assertPermanentCount(playerA, "Mountain", 0);
        // but to playerB instead
        int playerACount = 0;
        int playerBCount = 0;
        for (Permanent p : currentGame.getBattlefield().getAllActivePermanents()) {
            if (p.isLand(currentGame)) {
                if (p.getControllerId().equals(playerB.getId())) {
                    playerBCount++;
                }
                if (p.getControllerId().equals(playerA.getId())) {
                    playerACount++;
                }
            }
        }

        Assert.assertEquals(0, playerACount);
        Assert.assertEquals(2, playerBCount);
    }

}
