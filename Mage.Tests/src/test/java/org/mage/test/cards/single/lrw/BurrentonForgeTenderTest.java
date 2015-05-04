/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package org.mage.test.cards.single.lrw;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author Kranken, LevelX2
 */

public class BurrentonForgeTenderTest extends CardTestPlayerBase {

    @Test
    public void testPreventDamageFromStack() {
        // Sacrifice Burrenton Forge-Tender: Prevent all damage a red source of your choice would deal this turn.
        addCard(Zone.BATTLEFIELD, playerA, "Burrenton Forge-Tender");
        addCard(Zone.BATTLEFIELD, playerA, "Soldier of the Pantheon");

        addCard(Zone.HAND, playerB, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", "Soldier of the Pantheon");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice {this}: Prevent all damage a red source of your choice would deal this turn.", NO_TARGET, "Cast Lightning Bolt");
        playerA.addChoice("Lightning Bolt");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertGraveyardCount(playerB, "Lightning Bolt", 1);

        assertGraveyardCount(playerA, "Burrenton Forge-Tender", 1);
        assertPermanentCount(playerA, "Soldier of the Pantheon", 1);
    }

    @Test
    public void testPreventDamageFromFlametongueKavu() {
        addCard(Zone.BATTLEFIELD, playerA, "Burrenton Forge-Tender");
        addCard(Zone.BATTLEFIELD, playerA, "Soldier of the Pantheon");

        // When Flametongue Kavu enters the battlefield, it deals 4 damage to target creature.
        addCard(Zone.HAND, playerB, "Flametongue Kavu");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 4);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Flametongue Kavu");
        addTarget(playerB, "Soldier of the Pantheon");
        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice {this}: Prevent all damage a red source of your choice would deal this turn.", 
                NO_TARGET, "When {this} enters the battlefield, {source} deals 4 damage to target creature.");
        playerA.addChoice("Flametongue Kavu");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerB, "Flametongue Kavu", 1);
        
        assertGraveyardCount(playerA, "Burrenton Forge-Tender", 1);
        assertPermanentCount(playerA, "Soldier of the Pantheon", 1);
    }

    @Test
    public void testPreventDamageFromFlametongueKavuNotAfterCloudshift() {
        addCard(Zone.BATTLEFIELD, playerA, "Burrenton Forge-Tender");
        addCard(Zone.BATTLEFIELD, playerA, "Soldier of the Pantheon");

        // When Flametongue Kavu enters the battlefield, it deals 4 damage to target creature.
        addCard(Zone.HAND, playerB, "Flametongue Kavu");
        // Exile target creature you control, then return that card to the battlefield under your control.
        addCard(Zone.HAND, playerB, "Cloudshift");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 5);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Flametongue Kavu");
        addTarget(playerB, "Soldier of the Pantheon");
        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice {this}: Prevent all damage a red source of your choice would deal this turn.", 
                NO_TARGET, "When {this} enters the battlefield, {source} deals 4 damage to target creature.");
        playerA.addChoice("Flametongue Kavu");
        
        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerB, "Cloudshift", "Flametongue Kavu");
        addTarget(playerB, "Soldier of the Pantheon"); // now the damage may not be prevented because it's a new object
        
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, "Flametongue Kavu", 1);
        assertGraveyardCount(playerB, "Cloudshift", 1);
        
        assertGraveyardCount(playerA, "Burrenton Forge-Tender", 1);
        assertGraveyardCount(playerA, "Soldier of the Pantheon", 1);
    }    
    
    
    @Test
    public void testPreventDamageFromToken() {
        addCard(Zone.BATTLEFIELD, playerA, "Burrenton Forge-Tender");
        addCard(Zone.BATTLEFIELD, playerA, "Soldier of the Pantheon");
        // Sacrifice Mogg Fanatic: Mogg Fanatic deals 1 damage to target creature or player.
        addCard(Zone.BATTLEFIELD, playerA, "Mogg Fanatic");

        // Kicker {5} (You may pay an additional as you cast this spell.)
        // Put a token onto the battlefield that's a copy of target creature. If Rite of Replication was kicked, put five of those tokens onto the battlefield instead.
        addCard(Zone.HAND, playerB, "Rite of Replication");
        addCard(Zone.BATTLEFIELD, playerB, "Island", 4);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Rite of Replication", "Mogg Fanatic");
        setChoice(playerB, "No"); // no kicker
        
        activateAbility(2, PhaseStep.BEGIN_COMBAT, playerA, "Sacrifice {this}: {source} deals 1 damage to target creature or player.",playerB);

        activateAbility(2, PhaseStep.END_COMBAT, playerA, "Sacrifice {this}: Prevent all damage a red source of your choice would deal this turn.");
        playerA.addChoice("Mogg Fanatic");

        activateAbility(2, PhaseStep.POSTCOMBAT_MAIN, playerB, "Sacrifice {this}: {source} deals 1 damage to target creature or player.","Soldier of the Pantheon");
        
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 19);
        
        assertPermanentCount(playerB, "Mogg Fanatic", 0);
        assertGraveyardCount(playerA, "Mogg Fanatic", 1);
        assertGraveyardCount(playerB, "Rite of Replication", 1);
        
        assertGraveyardCount(playerA, "Burrenton Forge-Tender", 1);
        assertPermanentCount(playerA, "Soldier of the Pantheon", 1);
    }    
    

}