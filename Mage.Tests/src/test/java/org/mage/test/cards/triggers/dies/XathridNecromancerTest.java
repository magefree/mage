
package org.mage.test.cards.triggers.dies;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class XathridNecromancerTest extends CardTestPlayerBase {

    /**
     * My opponent had 2 Human Tokens from Gather the Townsfolk and a Xathrid Necromancer.
     * ( Whenever Xathrid Necromancer or another Human creature you control dies, put a 2/2 black Zombie creature token onto the battlefield tapped. )
     * All 3 of them died in combat but they only got 1 Zombie token.
     * There was no first strike damage involved.
     * 
     */
    @Test
    public void testDiesTriggeredAbility() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        // Creature - Human Wizard
        // 2/2
        // Whenever Xathrid Necromancer or another Human creature you control dies, put a 2/2 black Zombie creature token onto the battlefield tapped.
        addCard(Zone.BATTLEFIELD, playerA, "Xathrid Necromancer", 1);
        // Put two 1/1 white Human creature tokens onto the battlefield.
        // Fateful hour - If you have 5 or less life, put five of those tokens onto the battlefield instead.
        addCard(Zone.HAND, playerA, "Gather the Townsfolk");

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Pillarfield Ox", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Siege Mastodon", 1);
        
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Gather the Townsfolk");

        attack(2, playerB, "Silvercoat Lion");
        attack(2, playerB, "Pillarfield Ox");
        attack(2, playerB, "Siege Mastodon");

        block(2, playerA, "Human Token:0", "Silvercoat Lion");
        block(2, playerA, "Human Token:1", "Pillarfield Ox");
        block(2, playerA, "Xathrid Necromancer", "Siege Mastodon");
        
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Xathrid Necromancer", 1);
        assertGraveyardCount(playerA, "Gather the Townsfolk", 1);

        assertPermanentCount(playerA, "Human Token", 0);
        assertPermanentCount(playerA, "Zombie Token", 3);

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        
    }

}