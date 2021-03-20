package org.mage.test.cards.triggers.state;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class SynodCenturionTest extends CardTestPlayerBase {


    /**
     * Check that Synod Centurion gets sacrificed if no other artifacts are on the battlefield
     * 
     */
    @Test
    public void testAlone() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);
        // Whenever a player casts a black spell, you may gain 1 life.
        addCard(Zone.BATTLEFIELD, playerA, "Demon's Horn");
        // Destroy target artifact.
        addCard(Zone.HAND, playerA, "Shatter");
        // When you control no other artifacts, sacrifice Synod Centurion.
        addCard(Zone.HAND, playerA, "Synod Centurion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Synod Centurion");
        
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Shatter", "Demon's Horn");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Demon's Horn", 1);
        assertGraveyardCount(playerA, "Shatter", 1);
        assertGraveyardCount(playerA, "Synod Centurion", 1);
    }

    /**
     * Check that Synod Centurion gets sacrificed if the only other
     * artifact left the battlefiled for a short time
     * 
     */
    @Test
    public void testWithFlicker() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Bottle Gnomes");
        addCard(Zone.HAND, playerA, "Cloudshift");
        // When you control no other artifacts, sacrifice Synod Centurion.
        addCard(Zone.HAND, playerA, "Synod Centurion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Synod Centurion");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cloudshift", "Bottle Gnomes");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Bottle Gnomes", 1);
        assertGraveyardCount(playerA, "Cloudshift", 1);
        assertGraveyardCount(playerA, "Synod Centurion", 1);
    }    
}