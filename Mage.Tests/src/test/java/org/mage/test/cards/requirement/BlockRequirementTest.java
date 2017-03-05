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
package org.mage.test.cards.requirement;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2, icetc
 */
public class BlockRequirementTest extends CardTestPlayerBase {

    @Test
    public void testPrizedUnicorn() {
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion"); // 2/2

        // All creatures able to block Prized Unicorn do so.
        addCard(Zone.BATTLEFIELD, playerB, "Prized Unicorn"); // 2/2

        // Silvercoat Lion should be forced to block
        attack(2, playerB, "Prized Unicorn");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Silvercoat Lion", 1);
        assertGraveyardCount(playerB, "Prized Unicorn", 1);
    }

    @Test
    public void testPrizedUnicornAndOppressiveRays() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.HAND, playerA, "Oppressive Rays");
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion"); // 2/2

        // All creatures able to block Prized Unicorn do so.
        addCard(Zone.BATTLEFIELD, playerB, "Prized Unicorn"); // 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Oppressive Rays", "Silvercoat Lion");

        // Silvercoat Lion cannot block because it has to pay {3} to block
        attack(2, playerB, "Prized Unicorn");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Oppressive Rays", 1);
        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertPermanentCount(playerB, "Prized Unicorn", 1);
    }

    /**
     * Joraga Invocation is bugged big time. He cast it with 2 creatures out. I
     * only had one untapped creature. Blocked one of his, hit Done, error
     * message popped up saying the other one needed to be blocked in an
     * infinite loop. Had to shut down the program via Task Manager.
     */
    @Test
    public void testJoragaInvocationTest() {
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 6);
        // Each creature you control gets +3/+3 until end of turn and must be blocked this turn if able.
        addCard(Zone.HAND, playerB, "Joraga Invocation");
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion"); // 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Pillarfield Ox"); // 2/4

        // Swampwalk
        addCard(Zone.BATTLEFIELD, playerA, "Bog Wraith"); // 3/3

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Joraga Invocation");

        // Silvercoat Lion has not to block because it has to pay {3} to block
        attack(2, playerB, "Silvercoat Lion");
        attack(2, playerB, "Pillarfield Ox");
        block(2, playerA, "Bog Wraith", "Pillarfield Ox");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 15);

        assertGraveyardCount(playerB, "Joraga Invocation", 1);
        assertPowerToughness(playerB, "Silvercoat Lion", 5, 5);
        assertPowerToughness(playerB, "Pillarfield Ox", 5, 7);
        assertGraveyardCount(playerA, "Bog Wraith", 1);
    }

    /**
     * Elemental Uprising - "it must be blocked this turn if able", not working
     *
     * The bug just happened for me today as well - the problem is "must be
     * blocked" is not being enforced correctly. During opponent's main phase he
     * casted Elemental Uprising targeting an untapped land. He attacked with
     * two creatures, I had one creature to block with, and did not block the
     * land-creature targeted by Elemental Uprising. Instead I blocked a 2/2 of
     * his with my 2/3. I should have been forced to block the land targeted by
     * Elemental Uprising.
     */
    @Test
    public void testElementalUprising() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion"); // 2/2
        // Target land you control becomes a 4/4 Elemental creature with haste until end of turn. It's still a land. It must be blocked this turn if able.
        addCard(Zone.HAND, playerA, "Elemental Uprising");

        addCard(Zone.BATTLEFIELD, playerB, "Pillarfield Ox"); // 2/4

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Elemental Uprising", "Mountain");

        // Silvercoat Lion has not to block because it has to pay {3} to block
        attack(1, playerA, "Mountain");
        attack(1, playerA, "Silvercoat Lion");
        block(1, playerB, "Pillarfield Ox", "Silvercoat Lion"); // Not allowed, the Mountain has to be blocked

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Elemental Uprising", 1);
        assertPowerToughness(playerA, "Mountain", 4, 4);

        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertGraveyardCount(playerB, "Pillarfield Ox", 1);

        assertLife(playerB, 18);
    }

    /**
     * Okk is red creature that can't block unless a creature with greater power
     * also blocks.
     */
    @Test
    public void testOkkBlocking() {

        // 3/3 Vanilla creature
        addCard(Zone.BATTLEFIELD, playerA, "Hill Giant", 1);

        // 4/4 Goblin:
        // Okk can't attack unless a creature with greater power also attacks.
        // Okk can't block unless a creature with greater power also blocks.
        addCard(Zone.BATTLEFIELD, playerB, "Okk", 1); //

        attack(1, playerA, "Hill Giant");

        // Not allowed because of Okk's blocking restrictions
        block(1, playerB, "Okk", "Hill Giant");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        // Hill giant is still alive and Played B loses 3 lives
        assertPermanentCount(playerA, "Hill Giant", 1);
        assertLife(playerB, 17);
    }

    /**
     * Reported bug: When Breaker of Armies is granted Menace and there is only
     * 1 valid blocker, the game enters a state that cannot be continued. He
     * must be blocked by all creatures that are able, however, with menace the
     * only valid blocks would be by more than one creature, so the expected
     * behavior is no blocks can be made.
     */
    @Test
    public void testBreakerOfArmiesWithMenace() {

        // {8}
        // All creatures able to block Breaker of Armies do so.
        addCard(Zone.BATTLEFIELD, playerA, "Breaker of Armies", 1); // 10/8

        // 3/3 Vanilla creature
        addCard(Zone.BATTLEFIELD, playerB, "Hill Giant", 1);

        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 8);
        // {2}{B} Enchanted creature gets +2/+1 and has menace.
        addCard(Zone.HAND, playerA, "Untamed Hunger", 1);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Untamed Hunger", "Breaker of Armies");

        attack(1, playerA, "Breaker of Armies");

        // not allowed due to Breaker of Armies having menace
        block(1, playerB, "Hill Giant", "Breaker of Armies");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        // Hill giant is still alive
        assertPermanentCount(playerB, "Hill Giant", 1);
        // Player B was unable to block, so goes down to 10 life
        assertLife(playerB, 8);
    }
    
    /*
    Reported bug: Slayer's Cleaver did not force Wretched Gryff (an eldrazi) to block 
    */
    @Test
    public void testSlayersCleaver() {
        // Equipped creature gets +3/+1 and must be blocked by an Eldrazi if able.
        // Equip {4}
        addCard(Zone.BATTLEFIELD, playerA, "Slayer's Cleaver");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Memnite"); // {1} 1/1
        
        addCard(Zone.BATTLEFIELD, playerB, "Dimensional Infiltrator"); // 2/1 - Eldrazi
        addCard(Zone.BATTLEFIELD, playerB, "Llanowar Elves"); // 1/1
        
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", "Memnite"); // pumps to 4/2
        attack(1, playerA, "Memnite"); // must be blocked by Dimensional Infiltrator
        block(1, playerB, "Llanowar Elves", "Memnite"); // should not be allowed as only blocker
        
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();
                
        assertPermanentCount(playerA, "Slayer's Cleaver", 1);
        assertLife(playerB, 20);
        assertGraveyardCount(playerA, "Memnite", 1);
        assertGraveyardCount(playerB, "Dimensional Infiltrator", 1);
        assertGraveyardCount(playerB, "Llanowar Elves", 1);
    }
    
    /*
    Reported bug: Nacatl War-pride unable to be blocked ?
    Basic case: have it be blocked by one creature.
    */
    @Test
    public void testNacatlWarPrideBlockOneCreature() {
        /*  
        Nacatl War-Pride {3}{G}{G}{G}
        Creature - Cat Warrior 3/3        
        Nacatl War-Pride must be blocked by exactly one creature if able.        
        Whenever Nacatl War-Pride attacks, create X tokens that are copies of Nacatl War-Pride and that are tapped and attacking, 
        where X is the number of creatures defending player controls. Exile the tokens at the beginning of the next end step.
        */
        addCard(Zone.BATTLEFIELD, playerA, "Nacatl War-Pride");
        
        /*
        Primeval Titan {4}{G}{G}
        Creature - Giant 6/6
        Trample. Whenever Primeval Titan enters the battlefield or attacks, you may search your library for up to two land cards, 
        put them onto the battlefield tapped, then shuffle your library.
        */
        addCard(Zone.BATTLEFIELD, playerB, "Primeval Titan");
        
        attack(1, playerA, "Nacatl War-Pride");
        
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();
        
        assertLife(playerB, 17); // one 3/3 tokens attacking got through still, Nacatl forced to be blocked by Primeval
        assertGraveyardCount(playerA, "Nacatl War-Pride", 1);

        Permanent primetime = getPermanent("Primeval Titan", playerB);
        Assert.assertEquals("Damage to Primeval should be 3 not 0", 3, primetime.getDamage());
    }
    
    /*
    Reported bug: Nacatl War-pride unable to be blocked ?
    Give Nacatl unblockable and attempt to block it. Mix the two effects "cannot be blocked" and "must be blocked".
    */
    @Test
    public void testNacatlWarPrideCannotBeBlocked() {
        
        String nWarPride = "Nacatl War-Pride";
        String pTitan = "Primeval Titan";
        String aForm = "Aqueous Form";
        
        /*  
        Nacatl War-Pride {3}{G}{G}{G}
        Creature - Cat Warrior 3/3        
        Nacatl War-Pride must be blocked by exactly one creature if able.        
        Whenever Nacatl War-Pride attacks, create X tokens that are copies of Nacatl War-Pride and that are tapped and attacking, 
        where X is the number of creatures defending player controls. Exile the tokens at the beginning of the next end step.
        */
        addCard(Zone.BATTLEFIELD, playerA, nWarPride);
        
        /*        
        Enchantment - Aura {1}{U}
        Enchanted creature can't be blocked.
        Whenever enchanted creature attacks, scry 1. (Look at the top card of your library. You may put that card on the bottom of your library.)
        */
        addCard(Zone.HAND, playerA, aForm);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        
        addCard(Zone.BATTLEFIELD, playerB, pTitan);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, aForm, nWarPride);
                
        attack(1, playerA, nWarPride);
        block(1, playerB, pTitan, nWarPride); // blocking a token with the same name
        
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        
        assertLife(playerB, 17); // Nacatl dealt damage unblocked

        assertPermanentCount(playerA, nWarPride, 1);
        assertGraveyardCount(playerA, 0);
        Permanent primetime = getPermanent(pTitan, playerB);
        Assert.assertEquals("Damage to Primeval should be 3 not 0", 3, primetime.getDamage()); // blocked a token
    }
}
