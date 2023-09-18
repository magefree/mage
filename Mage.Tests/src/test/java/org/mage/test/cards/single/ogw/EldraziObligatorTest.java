package org.mage.test.cards.single.ogw;

import mage.abilities.keyword.HasteAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class EldraziObligatorTest extends CardTestPlayerBase {
    
    /*
     *  Reported bug: Eldrazi Obligator is untapping targetted creature when cost is not paid
     */
    @Test
    public void targetCreatureDoNotPayAdditionalCost() {
                
        addCard(Zone.BATTLEFIELD, playerA, "Bronze Sable"); // 2/1
        
        //When you cast Eldrazi Obligator, you may pay {1}{C}. If you do, gain control of target creature until end of turn, 
        //untap that creature, and it gains haste until end of turn.
        addCard(Zone.HAND, playerB, "Eldrazi Obligator"); // 3/1 w/haste
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 3);
        
        attack(1, playerA, "Bronze Sable"); // tap dat sable
        
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Eldrazi Obligator");
        setChoice(playerB, false);
        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertPermanentCount(playerA, "Bronze Sable", 1);
        assertPermanentCount(playerB, "Eldrazi Obligator", 1);
        assertAbility(playerA, "Bronze Sable", HasteAbility.getInstance(), false); // should not gain haste or untap
        assertTapped("Bronze Sable", true);
    }
}
