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

package org.mage.test.cards.abilities.keywords;

import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class BestowTest extends CardTestPlayerBase {

    /**
     * Tests that if from bestow permanent targeted creature
     * gets protection from the color of the bestow permanent,
     * the bestow permanent becomes a creature on the battlefield.
     *
     */

    /* Silent Artisan
     * Creature - Giant 3/5
     *
     *
     * Hopeful Eidolon {W}
     * Enchantment Creature - Spirit   1/1
     * Bestow {3}{W} (If you cast this card for its bestow cost, it's an Aura spell with enchant creature.
     * It becomes a creature again if it's not attached to a creature.)
     * Lifelink (Damage dealt by this creature also causes you to gain that much life.)
     * Enchanted creature gets +1/+1 and has lifelink.
     *
     * Gods Willing {W}
     * Instant
     * Target creature you control gains protection from the color of your choice until end of turn.
     * Scry 1. (Look at the top card of your library. You may put that card on the bottom of your library.)
     *
     */

    @Test
    public void bestowEnchantmentToCreature() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Silent Artisan");
        addCard(Zone.HAND, playerA, "Hopeful Eidolon");
        addCard(Zone.HAND, playerA, "Gods Willing");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Hopeful Eidolon using bestow", "Silent Artisan");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Gods Willing", "Silent Artisan");
        setChoice(playerA, "White");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // because of protection the Hopeful Eidolon should be a creature on the battlefield
        assertPermanentCount(playerA, "Silent Artisan", 1);
        assertPowerToughness(playerA, "Silent Artisan", 3, 5);
        assertPermanentCount(playerA, "Hopeful Eidolon", 1);
        assertPowerToughness(playerA, "Hopeful Eidolon", 1, 1);
    }

    /**
     * Test that cast with bestow does not trigger evolve
     */
    @Test
    public void bestowEnchantmentDoesNotTriggerEvolve() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 6);
        // Creature - Giant 3/5
        addCard(Zone.BATTLEFIELD, playerA, "Silent Artisan");
        
        addCard(Zone.HAND, playerA, "Experiment One");
        // Enchanted creature gets +4/+2.
        addCard(Zone.HAND, playerA, "Boon Satyr");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Experiment One");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Boon Satyr using bestow", "Silent Artisan");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // because Boon Satyr is no creature on the battlefield, evolve may not trigger
        assertPermanentCount(playerA, "Boon Satyr", 1);
        Permanent boonSatyr = getPermanent("Boon Satyr", playerA);
        Assert.assertTrue("Boon Satyr may not be a creature",!boonSatyr.getCardType().contains(CardType.CREATURE));
        assertPermanentCount(playerA, "Silent Artisan", 1);
        assertPermanentCount(playerA, "Experiment One", 1);
        assertPowerToughness(playerA, "Experiment One", 1, 1);
        assertPowerToughness(playerA, "Silent Artisan", 7, 7);        
        
    }
    
    /**
     * Test that the bestow enchantment becomes a creature if the enchanted creature dies
     */
    @Test
    public void bestowEnchantmentBecomesCreature() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");        
        addCard(Zone.HAND, playerA, "Hopeful Eidolon");
        
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.HAND, playerB, "Lightning Bolt");
                
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Hopeful Eidolon using bestow", "Silvercoat Lion");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", "Silvercoat Lion");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // because Boon Satyr is no creature on the battlefield, evolve may not trigger
        assertLife(playerA, 20);
        assertLife(playerB, 20);
        
        assertPermanentCount(playerA, "Silvercoat Lion", 0);
        assertPermanentCount(playerA, "Hopeful Eidolon", 1);
        assertPowerToughness(playerA, "Hopeful Eidolon", 1, 1);

        Permanent hopefulEidolon = getPermanent("Hopeful Eidolon", playerA);
        Assert.assertTrue("Hopeful Eidolon has to be a creature but is not", hopefulEidolon.getCardType().contains(CardType.CREATURE));
        Assert.assertTrue("Hopeful Eidolon has to be an enchantment but is not", hopefulEidolon.getCardType().contains(CardType.ENCHANTMENT));


    }
    
    /**
     * Test that card cast with bestow will not be tapped, if creatures come into play tapped
     */
    @Test
    public void bestowEnchantmentWillNotBeTapped() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Silent Artisan");
        
        addCard(Zone.HAND, playerA, "Boon Satyr");
        
        // Enchantment {1}{W}
        // Creatures your opponents control enter the battlefield tapped.
        addCard(Zone.BATTLEFIELD, playerB, "Imposing Sovereign");
        
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Boon Satyr using bestow", "Silent Artisan");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // because Boon Satyr is no creature on the battlefield, evolve may not trigger
        assertPermanentCount(playerA, "Silent Artisan", 1);
        assertPowerToughness(playerA, "Silent Artisan", 7, 7);
        // because cast with bestow, Boon Satyr may not be tapped
        assertTapped("Boon Satyr", false);
        
    }  
    
    /**
     * If I have a bestowed creature on the battlefield and my opponent uses Far // Away casting 
     * both sides, will the creature that has bestow come in time for it to be sacrificed or does 
     * it fully resolve before the creature comes in?
     * 
     * Bestowed creature can be used to sacrifice a creature for the Away part.
     * http://www.mtgsalvation.com/forums/magic-fundamentals/magic-rulings/magic-rulings-archives/513828-bestow-far-away
     */    
    @Test
    @Ignore  // Handling of targets of Fused spells is not handled yet in TestPlayer class
    public void bestowWithFusedSpell() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        /**
         * Cyclops of One-Eyed Pass  {2}{R}{R}
         *  Creature - Cyclops
         *  5/2
         */
        addCard(Zone.BATTLEFIELD, playerA, "Cyclops of One-Eyed Pass");
        
        /**
         * Nyxborn Rollicker  {R}
         *  Enchantment Creature - Satyr
         *  1/1
         *  Bestow {1}{R} (If you cast this card for its bestow cost, it's an Aura spell with enchant creature. It becomes a creature again if it's not attached to a creature.)
         *  Enchanted creature gets +1/+1.
         */
        addCard(Zone.HAND, playerA, "Nyxborn Rollicker");

        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);

        /**
         * Far {1}{U}
         * Instant
         * Return target creature to its owner's hand.
         * Away {2}{B}
         * Instant
         * Target player sacrifices a creature.
         * Fuse (You may cast one or both halves of this card from your hand.)
         */        
        addCard(Zone.HAND, playerB, "Far // Away");
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Nyxborn Rollicker using bestow", "Cyclops of One-Eyed Pass");
        
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "fused Far // Away", "Cyclops of One-Eyed Pass^targetPlayer=PlayerA");
        playerA.addTarget("Nyxborn Rollicker");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, 0);
        assertHandCount(playerB, 0);
        
        assertGraveyardCount(playerB, "Far // Away", 1);
        
        assertPermanentCount(playerA, "Nyxborn Rollicker", 0);
        assertGraveyardCount(playerA, "Nyxborn Rollicker", 1);
        
    }

    /**
     * Test that CMC of a spell cast with bestowed is correct
     * Disdainful Stroke doesn't check converted mana cost correctly. Opponent was
     * able to use it to counter a Hypnotic Siren cast with Bestow.
     */
    @Test
    public void bestowCheckForCorrectCMC() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 7);
        // Enchantment Creature — Siren
        // 1/1
        // Bestow {5}{U}{U} (If you cast this card for its bestow cost, it's an Aura spell with enchant creature. It becomes a creature again if it's not attached to a creature.)
        // Flying
        // You control enchanted creature.
        // Enchanted creature gets +1/+1 and has flying.
        addCard(Zone.HAND, playerA, "Hypnotic Siren");
        // Instant {1}{U}
        // Counter target spell with converted mana cost 4 or greater.
        addCard(Zone.HAND, playerB, "Disdainful Stroke");
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Hypnotic Siren using bestow", "Silvercoat Lion");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Disdainful Stroke", "Hypnotic Siren");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        //
        assertHandCount(playerA, "Hypnotic Siren", 0);
        assertGraveyardCount(playerA, "Hypnotic Siren", 0);
        assertHandCount(playerB, "Disdainful Stroke", 1);
        assertPermanentCount(playerA, "Hypnotic Siren", 1);

        // because cast with bestow, Boon Satyr may not be tapped
        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertPowerToughness(playerA, "Silvercoat Lion", 3,3);

    }
    
  /**
     * 
     * 
     * 
     */
    @Test
    public void bestowMogissWarhound() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        // Enchantment Creature — Hound
        // 2/2
        // Bestow 2R (If you cast this card for its bestow cost, it's an Aura spell with enchant creature. It becomes a creature again if it's not attached to a creature.)
        // Mogis's Warhound attacks each turn if able.
        // Enchanted creature gets +2/+2 and attacks each turn if able.
        addCard(Zone.HAND, playerA, "Mogis's Warhound");
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");

        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 4);
        // Chandra's Outrage deals 4 damage to target creature and 2 damage to that creature's controller.
        addCard(Zone.HAND, playerB, "Chandra's Outrage");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Mogis's Warhound using bestow", "Silvercoat Lion");
        castSpell(1, PhaseStep.END_TURN, playerB, "Chandra's Outrage", "Silvercoat Lion");
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 18); // -2 from Chandra's Outrage
        assertLife(playerB, 18); // -2 from attack of Mogis's Warhound
        //
        assertHandCount(playerA, "Mogis's Warhound", 0);
        assertPermanentCount(playerA, "Mogis's Warhound", 1);
        assertGraveyardCount(playerA, "Silvercoat Lion", 1);

    }    
}
