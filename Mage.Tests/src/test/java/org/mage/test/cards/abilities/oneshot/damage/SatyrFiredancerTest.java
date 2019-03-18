
package org.mage.test.cards.abilities.oneshot.damage;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class SatyrFiredancerTest extends CardTestPlayerBase {

    @Test
    public void testDamageFromInstantToPlayer() {
        // Whenever an instant or sorcery spell you control deals damage to an opponent, Satyr Firedancer deals that much damage to target creature that player controls.        
        addCard(Zone.BATTLEFIELD, playerA, "Satyr Firedancer");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        addTarget(playerA, "Silvercoat Lion");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 17);
        
        assertGraveyardCount(playerA, "Lightning Bolt", 1);
        assertGraveyardCount(playerB, "Silvercoat Lion", 1);
    }

    @Test
    public void testDamageFromAttackWontTrigger() {
        // Whenever an instant or sorcery spell you control deals damage to an opponent, Satyr Firedancer deals that much damage to target creature that player controls.        
        addCard(Zone.BATTLEFIELD, playerA, "Satyr Firedancer");
        addCard(Zone.BATTLEFIELD, playerA, "Pillarfield Ox", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");

        attack(1, playerA, "Pillarfield Ox");        
        addTarget(playerA, "Silvercoat Lion");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 18);
        
        assertGraveyardCount(playerB, "Silvercoat Lion", 0);
        assertPermanentCount(playerB, "Silvercoat Lion", 1);
    }

    
    @Test
    public void testDamageFromOtherCreature() {
        // Whenever an instant or sorcery spell you control deals damage to an opponent, Satyr Firedancer deals that much damage to target creature that player controls.        
        addCard(Zone.BATTLEFIELD, playerA, "Satyr Firedancer");

        // {T}: Prodigal Pyromancer deals 1 damage to any target.
        addCard(Zone.BATTLEFIELD, playerA, "Prodigal Pyromancer", 1);

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: {source} deals", playerB);        
        addTarget(playerA, playerB);        
        
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 19);
        
    }
}
