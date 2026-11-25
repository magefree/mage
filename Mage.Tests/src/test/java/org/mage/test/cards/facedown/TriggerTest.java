
package org.mage.test.cards.facedown;

import mage.constants.EmptyNames;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import static org.junit.Assert.assertTrue;

/**
 *
 * @author LevelX2
 */

    

public class TriggerTest extends CardTestPlayerBase {

    /**
     * Midnight Reaper triggers when dies face down #7063
     * Ixidron has turned Midnight Reaper and Balduvian Bears face down:
     * 
     */
    
    // test that cards imprinted using Summoner's Egg are face down
    @Test
    public void testReaperDoesNotTriggerDiesTriggerFaceDown() {
        setStrictChooseMode(true);
        
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        // As Ixidron enters the battlefield, turn all other nontoken creatures face down.
        // Ixidron's power and toughness are each equal to the number of face-down creatures on the battlefield.        
        addCard(Zone.HAND, playerA, "Ixidron"); // Creature {3}{U}{U} (*/*)        
        // Whenever a nontoken creature you control dies, Midnight Reaper deals 1 damage to you and you draw a card.        
        addCard(Zone.BATTLEFIELD, playerA, "Midnight Reaper"); // Creature {2}{B}
        
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.HAND, playerB, "Lightning Bolt"); // Instant 3 damage

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ixidron");
        
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", EmptyNames.FACE_DOWN_CREATURE.getTestCommand());
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Lightning Bolt", 1);
        
        assertGraveyardCount(playerA, "Midnight Reaper", 1);
        assertGraveyardCount(playerA, "Ixidron", 1);
        
        assertHandCount(playerA, 0);
        assertLife(playerA, 20);

    }

    @Test
    public void testIxidronTurnsOffRoomAbility() {
        String room = "Glassworks // Shattered Yard";
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        // Leave enough white and blue even if earlier generic costs use Plains or Islands.
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 7);
        addCard(Zone.BATTLEFIELD, playerA, "Zur, Eternal Schemer");
        addCard(Zone.HAND, playerA, room);
        addCard(Zone.HAND, playerA, "Ixidron");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shattered Yard");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{W}: Target non-Aura enchantment", "Shattered Yard");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ixidron");

        runCode("verify the Room is face down", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, (info, player, game) ->
                assertTrue("Ixidron must turn the Room face down", game.getBattlefield().getAllPermanents().stream()
                        .anyMatch(permanent -> room.equals(permanent.getMainCard().getName()) && permanent.isFaceDown(game))));

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.UPKEEP);
        execute();
        assertLife(playerB, 20);
    }
}
