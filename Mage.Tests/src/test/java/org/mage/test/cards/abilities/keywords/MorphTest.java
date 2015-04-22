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

import mage.cards.Card;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.filter.Filter;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author levelX2
 */

public class MorphTest extends CardTestPlayerBase {

    /**
     * Tests if a creature with Morph is cast normal, it behaves as normal creature
     *
     */
    @Test
    public void testCastMoprhCreatureWithoutMorph() {
        /*    
        Pine Walker
        Creature - Elemental
        5/5
        Morph {4}{G} (You may cast this card face down as a 2/2 creature for . Turn it face up any time for its morph cost.)
        Whenever Pine Walker or another creature you control is turned face up, untap that creature.
        */
        addCard(Zone.HAND, playerA, "Pine Walker");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pine Walker");
        setChoice(playerA, "No"); // cast it normal as 5/5
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Pine Walker", 1);
        assertPowerToughness(playerA, "Pine Walker", 5, 5);

    }


    /**
     * Cast the creature face down as a 2/2
     */
    @Test
    public void testCastFaceDown() {
        addCard(Zone.HAND, playerA, "Pine Walker");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pine Walker");
        setChoice(playerA, "Yes"); // cast it face down as 2/2 creature
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "face down creature", 1);
        assertPowerToughness(playerA, "face down creature", 2, 2);

    }
    /**
     * Test triggered turn face up ability of Pine Walker
     */
    @Test
    public void testTurnFaceUpTrigger() {
        addCard(Zone.HAND, playerA, "Pine Walker");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pine Walker");
        setChoice(playerA, "Yes"); // cast it face down as 2/2 creature
        
        attack(3, playerA, "face down creature");
        
        activateAbility(3, PhaseStep.POSTCOMBAT_MAIN, playerA, "{4}{G}: Turn this face-down permanent face up.");
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 18);
        
        assertPermanentCount(playerA, "face down creature", 0);
        assertPermanentCount(playerA, "Pine Walker", 1);        
        assertPowerToughness(playerA, "Pine Walker", 5, 5);
        assertTapped("Pine Walker", false);

    }
    
    /**
     * Test that the triggered "turned face up" ability of Pine Walker does not trigger
     * as long as Pine Walker is not turned face up.
     * 
     */
    @Test
    public void testDoesNotTriggerFaceDown() {
        // Whenever Pine Walker or another creature you control is turned face up, untap that creature.
        addCard(Zone.HAND, playerA, "Pine Walker");
        // When Icefeather Aven is turned face up, you may return another target creature to its owner's hand.
        addCard(Zone.HAND, playerA, "Icefeather Aven");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pine Walker");
        setChoice(playerA, "Yes"); // cast it face down as 2/2 creature
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Icefeather Aven");
        setChoice(playerA, "Yes"); // cast it face down as 2/2 creature
        
        attack(3, playerA, "face down creature");
        attack(3, playerA, "face down creature");
        activateAbility(3, PhaseStep.DECLARE_BLOCKERS, playerA, "{1}{G}{U}: Turn this face-down permanent face up.");
        setChoice(playerA, "No"); // Don't use return permanent to hand effect
        
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 16);
        
        assertHandCount(playerA, "Pine Walker", 0);
        assertHandCount(playerA, "Icefeather Aven", 0);
        assertPermanentCount(playerA, "face down creature", 1);
        assertPermanentCount(playerA, "Icefeather Aven", 1);        
        assertTapped("Icefeather Aven", true);

    }

    /**
     * Test that Morph creature do not trigger abilities with their face up attributes
     * 
     */
    @Test
    public void testMorphedRemovesAttributesCreature() {
        // Ponyback Brigade {3}{R}{W}{B}
        // Creature - Goblin Warrior
        // 2/2
        // When Ponyback Brigade enters the battlefield or is turned face up, put three 1/1 red Goblin creature tokens onto the battlefield.
        // Morph {2}{R}{W}{B}(You may cast this card face down as a 2/2 creature for . Turn it face up any time for its morph cost.)        
        addCard(Zone.HAND, playerA, "Ponyback Brigade");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        
        addCard(Zone.BATTLEFIELD, playerB, "Soldier of the Pantheon", 1);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ponyback Brigade");
        setChoice(playerA, "Yes"); // cast it face down as 2/2 creature
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, 20); // and not 21 
        
        assertPermanentCount(playerA, "face down creature", 1);
        assertPermanentCount(playerB, "Soldier of the Pantheon", 1);

    }
    
   /**
     * Test to copy a morphed 2/2 creature
     * 
     */
    @Test
    public void testCopyAMorphedCreature() {
        addCard(Zone.HAND, playerA, "Pine Walker");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        
        // Clever Impersonator  {2}{U}{U}
        // Creature - Shapeshifter
        // 0/0
        // You may have Clever Impersonator enter the battlefield as a copy of any nonland permanent on the battlefield.
        addCard(Zone.HAND, playerB, "Clever Impersonator", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 4);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pine Walker");
        setChoice(playerA, "Yes"); // cast it face down as 2/2 creature
        
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Clever Impersonator");
        setChoice(playerB, "Yes"); // use to copy a nonland permanent
        addTarget(playerB, "face down creature"); // Morphed creature
                
        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, 20);
        
        assertPermanentCount(playerA, "face down creature", 1);
        assertPowerToughness(playerA, "face down creature", 2,2);
        assertPermanentCount(playerB, "a creature without name", 1);
        assertPowerToughness(playerB, "a creature without name", 2,2);

    }    
    
    /**
     * 
     * 
     */
    @Test
    public void testPineWalkerWithUnboostEffect() {
        addCard(Zone.HAND, playerA, "Pine Walker");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 8);
        
        // Doomwake Giant  {4}{B}
        // Creature - Giant
        // 4/6
        // Constellation - When Doomwake Giant or another enchantment enters the battlefield under your control, creatures your opponents control get -1/-1 until end of turn.
        addCard(Zone.HAND, playerB, "Doomwake Giant", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 5);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pine Walker");
        setChoice(playerA, "Yes"); // cast it face down as 2/2 creature
        
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Doomwake Giant");

        // activateAbility(2, PhaseStep.POSTCOMBAT_MAIN, playerA, "{2}{R}{W}{B}: Turn this face-down permanent face up.");
        activateAbility(2, PhaseStep.POSTCOMBAT_MAIN, playerA, "{4}{G}: Turn this face-down permanent face up.");
        
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20);
        
        assertHandCount(playerA, "Pine Walker", 0);        
        assertHandCount(playerB, "Doomwake Giant", 0);
        assertPermanentCount(playerA, "face down creature", 0);
        assertPermanentCount(playerB, "Doomwake Giant", 1);
        assertPermanentCount(playerA, "Pine Walker", 1);
        assertPowerToughness(playerA, "Pine Walker", 4,4);

    }    
    /**
     * If a morph is on the table and an enemy Doomwake Giant comes down, the morph goes 
     * down to 1/1 correctly. If you unmorph the 2/2 and is also a 2/2 after umorphing, 
     * the morph will be erroneously reduced to 0/0 and die.
     * 
     */
    @Test
    public void testDoomwakeGiantEffect() {
        addCard(Zone.HAND, playerA, "Ponyback Brigade");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);
        
        // Doomwake Giant  {4}{B}
        // Creature - Giant
        // 4/6
        // Constellation - When Doomwake Giant or another enchantment enters the battlefield under your control, creatures your opponents control get -1/-1 until end of turn.
        addCard(Zone.HAND, playerB, "Doomwake Giant", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 5);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ponyback Brigade");
        setChoice(playerA, "Yes"); // cast it face down as 2/2 creature
        
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Doomwake Giant");

        activateAbility(2, PhaseStep.POSTCOMBAT_MAIN, playerA, "{2}{R}{W}{B}: Turn this face-down permanent face up.");
        
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20);
        
        assertHandCount(playerA, "Ponyback Brigade", 0);        
        assertHandCount(playerB, "Doomwake Giant", 0);
        assertPermanentCount(playerA, "face down creature", 0);
        assertPermanentCount(playerA, "Goblin", 3);
        assertPowerToughness(playerA, "Goblin", 1,1,Filter.ComparisonScope.Any);
        assertPermanentCount(playerB, "Doomwake Giant", 1);
        assertPermanentCount(playerA, "Ponyback Brigade", 1);
        assertPowerToughness(playerA, "Ponyback Brigade", 1,1);

    }
    /**
     * Clone a Morph creature that was cast face down and meanwhile was turned face up
     *
     */
    @Test
    public void testCloneFaceUpMorphEffect() {
        // Sagu Mauler 6/6 - Creature - Beast
        // Trample, hexproof
        // Morph {3}{G}{B} (You may cast this card face down as a 2/2 creature for . Turn it face up any time for its morph cost.)
        addCard(Zone.HAND, playerA, "Sagu Mauler");
        addCard(Zone.HAND, playerA, "Clone");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sagu Mauler");
        setChoice(playerA, "Yes"); // cast it face down as 2/2 creature
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{3}{G}{U}: Turn this face-down permanent face up.");
        castSpell(5, PhaseStep.PRECOMBAT_MAIN, playerA, "Clone");
        setChoice(playerA, "Sagu Mauler");

        setStopAt(5, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerB, 20);

        assertHandCount(playerA, "Sagu Mauler", 0);
        assertHandCount(playerA, "Clone", 0);

        assertPermanentCount(playerA, "Sagu Mauler", 2);
        assertPowerToughness(playerA, "Sagu Mauler", 6,6,Filter.ComparisonScope.Any);

    }
    /**
     * Check that you can't counter a creature cast for it morph costs
     * with Disdainful Stroke if it's normal cmc > 3
     *
     */
    @Test
    public void testCounterCastWithMorphEffect() {
        // Sagu Mauler 6/6 - Creature - Beast
        // Trample, hexproof
        // Morph {3}{G}{B} (You may cast this card face down as a 2/2 creature for . Turn it face up any time for its morph cost.)
        addCard(Zone.HAND, playerA, "Sagu Mauler");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);

        // Disdainful Stroke {1}{U}
        // Instant
        // Counter target spell with converted mana cost 4 or greater.
        addCard(Zone.HAND, playerB, "Disdainful Stroke");
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sagu Mauler");
        setChoice(playerA, "Yes"); // cast it face down as 2/2 creature

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Disdainful Stroke", "Sagu Mauler");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, 20);

        assertHandCount(playerA, "Sagu Mauler", 0);
        assertHandCount(playerB, "Disdainful Stroke", 1); // can't be cast

        assertPermanentCount(playerA, "face down creature", 1);

    }

    /**
     * Check that an effect like "Target creature and all other creatures with the same name" does
     * only effect one face down creature, also if multiple on the battlefield. Because they have no
     * name, they don't have the same name.
     *
     */
    @Test
    public void testEchoingDecaySameNameEffect() {
        // Sagu Mauler 6/6 - Creature - Beast
        // Trample, hexproof
        // Morph {3}{G}{B} (You may cast this card face down as a 2/2 creature for . Turn it face up any time for its morph cost.)
        addCard(Zone.HAND, playerA, "Sagu Mauler", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 6);

        // Echoing Decay {1}{B}
        // Instant
        // Target creature and all other creatures with the same name as that creature get -2/-2 until end of turn.
        addCard(Zone.HAND, playerB, "Echoing Decay");
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sagu Mauler");
        setChoice(playerA, "Yes"); // cast it face down as 2/2 creature
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sagu Mauler");
        setChoice(playerA, "Yes"); // cast it face down as 2/2 creature

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Echoing Decay", "face down creature");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, 20);

        assertHandCount(playerA, "Sagu Mauler", 0);
        assertHandCount(playerB, "Echoing Decay", 0);

        assertPermanentCount(playerA, "face down creature", 1);

    }

    /**
     * I played a Akroma, Angel of Fury face down, and my opponent tried to counter it.
     * The counter failed and Akroma face successfully play face down, when it should have
     * been countered. (The card text on akroma should not prevent her from being countered).
     */

    @Test
    public void testRuleModifyingEffectsFromManifestedCardWontBeAppliedAbilities() {
        addCard(Zone.HAND, playerA, "Akroma, Angel of Fury", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        addCard(Zone.HAND, playerB, "Counterspell", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);


        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akroma, Angel of Fury");
        setChoice(playerA, "Yes"); // cast it face down as 2/2 creature
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Counterspell", "Akroma, Angel of Fury");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, 20);

        assertGraveyardCount(playerB, "Counterspell", 1);
        assertGraveyardCount(playerA, "Akroma, Angel of Fury", 1);

    }
    /**
     * Check if a face down Morph creature gets exiled, it will
     * be face up in exile zone.
     */

    @Test
    public void testExileFaceDownCreature() {
        addCard(Zone.HAND, playerA, "Birchlore Rangers", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);

        addCard(Zone.HAND, playerB, "Swords to Plowshares", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 1);


        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Birchlore Rangers");
        setChoice(playerA, "Yes"); // cast it face down as 2/2 creature

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Swords to Plowshares", "face down creature");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 22); // + 2 from Swords to Plowshares
        assertLife(playerB, 20);

        assertGraveyardCount(playerB, "Swords to Plowshares", 1);
        assertExileCount("Birchlore Rangers", 1);


        for (Card card: currentGame.getExile().getAllCards(currentGame)) {
            if (card.getName().equals("Birchlore Rangers")) {
                Assert.assertEquals("Birchlore Rangers has to be face up in exile", false, card.isFaceDown(currentGame));
                break;
            }
        }

    }
   /**
     * Check that a DiesTriggeredAbility of a creature does not trigger
     * if the creature dies face down
     */

    @Test
    public void testDiesTriggeredDoesNotTriggerIfFaceDown() {
        // Flying
        // When Ashcloud Phoenix dies, return it to the battlefield face down.
        // Morph (You may cast this card face down as a 2/2 creature for . Turn it face up any time for its morph cost.)
        // When Ashcloud Phoenix is turned face up, it deals 2 damage to each player.
        addCard(Zone.HAND, playerA, "Ashcloud Phoenix", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);

        addCard(Zone.HAND, playerB, "Lightning Bolt", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);


        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ashcloud Phoenix");
        setChoice(playerA, "Yes"); // cast it face down as 2/2 creature

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", "face down creature");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertGraveyardCount(playerB, "Lightning Bolt", 1);
        assertGraveyardCount(playerA, "Ashcloud Phoenix", 1);


        for (Card card: playerA.getGraveyard().getCards(currentGame)) {
            if (card.getName().equals("Ashcloud Phoenix")) {
                Assert.assertEquals("Ashcloud Phoenix has to be face up in graveyard", false, card.isFaceDown(currentGame));
                break;
            }
        }

    }    
   /**
     * Check that a DiesTriggeredAbility of a creature does not trigger
     * if the creature dies face down in combat
     */

    @Test
    public void testDiesTriggeredDoesNotTriggerInCombatIfFaceDown() {
        // Flying
        // When Ashcloud Phoenix dies, return it to the battlefield face down.
        // Morph (You may cast this card face down as a 2/2 creature for . Turn it face up any time for its morph cost.)
        // When Ashcloud Phoenix is turned face up, it deals 2 damage to each player.
        addCard(Zone.HAND, playerA, "Ashcloud Phoenix", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        // First strike, forestwalk, vigilance 
        // (This creature deals combat damage before creatures without first strike, it can't be blocked as long as defending player controls a Forest, and attacking doesn't cause this creature to tap.)
        addCard(Zone.BATTLEFIELD, playerB, "Mirri, Cat Warrior");


        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ashcloud Phoenix");
        setChoice(playerA, "Yes"); // cast it face down as 2/2 creature

        attack(2, playerB, "Mirri, Cat Warrior");
        block(2, playerA, "face down creature", "Mirri, Cat Warrior"); 

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Ashcloud Phoenix", 1);


        for (Card card: playerA.getGraveyard().getCards(currentGame)) {
            if (card.getName().equals("Ashcloud Phoenix")) {
                Assert.assertEquals("Ashcloud Phoenix has to be face up in graveyard", false, card.isFaceDown(currentGame));
                break;
            }
        }

        assertLife(playerA, 20);
        assertLife(playerB, 20);

    }    
}
