package org.mage.test.cards.abilities.enters;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class NayaSoulbeastTest extends CardTestPlayerBase {
    
    /*
    Reported bug: I am finding an issue with Naya Soulbeast, it enters the battlefield and the effect that makes the players reveal 
    the top of their library work, my issue is that it doesn't gain the +1/+1 counters even if there are non-land cards revealed, 
    and since it is a 0/0 it is destroyed as it enters battlefield unless you have a permanent grants creatures you control +1/+1 
    and even in that situation, it doesn't gain the +1/+1 counters.
    */
    @Test
    public void testNayaEntersWithTwoCounters() {
        
        // Naya Soulbeast - {6}{G}{G}
        // Creature Beast - 0/0 - Trample
        // When you cast Naya Soulbeast, each player reveals the top card of their library.
        // Naya Soulbeast enters the battlefield with X +1/+1 counters on it, where X is the total converted mana cost of all cards revealed this way.
        addCard(Zone.HAND, playerA, "Naya Soulbeast", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 8);
        
        removeAllCardsFromLibrary(playerB);
        addCard(Zone.LIBRARY, playerB, "Bronze Sable", 1); // {2} 2/1
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Naya Soulbeast");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertPermanentCount(playerA, "Naya Soulbeast", 1);
        assertCounterCount("Naya Soulbeast", CounterType.P1P1, 2);
        assertPowerToughness(playerA, "Naya Soulbeast", 2, 2);
    }
}
