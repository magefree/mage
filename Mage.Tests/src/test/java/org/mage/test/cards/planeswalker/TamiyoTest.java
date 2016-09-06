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
package org.mage.test.cards.planeswalker;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class TamiyoTest extends CardTestPlayerBase {

    /*
     * Reported bug: I activated Tamiyo's +1 ability on a 5/5 Gideon and his 2/2 Knight Ally, but when they both attacked
     *  and dealt damage I only drew one card when I'm pretty sure I was supposed to draw for each of the two.
     */
    @Test
    public void testFieldResearcherFirstEffectOnGideon() {

        // Tamiyo, Field Researcher {1}{G}{W}{U} - 4 loyalty
        // +1: Choose up to two target creatures. Until your next turn,
        // whenever either of those creatures deals combat damage, you draw a card.
        addCard(Zone.BATTLEFIELD, playerA, "Tamiyo, Field Researcher", 1);

        /* Gideon, Ally of Zendikar {2}{W}{W} - 4 loyalty
         * +1: Until end of turn, Gideon, Ally of Zendikar becomes a 5/5 Human Soldier Ally creature with indestructible
         * that's still a planeswalker. Prevent all damage that would be dealt to him this turn.
         * 0: Put a 2/2 white Knight Ally creature token onto the battlefield.
        **/
        addCard(Zone.BATTLEFIELD, playerA, "Gideon, Ally of Zendikar", 1);

        // put 2/2 knight ally token on battlefield
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+0: Put a");

        // next, activate Gideon to make him a 5/5 human soldier ally creature
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "+1: Until end of turn");
        // finally, use Tamiyo +1 on both creatures
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "+1: Choose up to two");
        addTarget(playerA, "Knight Ally^Gideon, Ally of Zendikar"); // both token and Gideon as creature

        // attack with both unblocked
        attack(3, playerA, "Knight Ally");
        attack(3, playerA, "Gideon, Ally of Zendikar");

        setStopAt(3, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerB, 13); // 5 + 2 damage, 20 - 7 = 13
        assertPermanentCount(playerA, "Tamiyo, Field Researcher", 1);
        assertPermanentCount(playerA, "Gideon, Ally of Zendikar", 1);
        assertPermanentCount(playerA, "Knight Ally", 1);
        assertHandCount(playerA, 3); // two cards drawn from each creature dealing damage + 1 card drawn on turn
    }

    /*
     * Testing more basic scenario with Tamiyo, Field of Researcher +1 effect
     */
    @Test
    public void testFieldResearcherFirstEffectSimpleCreatureAttacks() {

        // Tamiyo, Field Researcher {1}{G}{W}{U} - 4 loyalty
        // +1: Choose up to two target creatures. Until your next turn,
        // whenever either of those creatures deals combat damage, you draw a card.
        addCard(Zone.HAND, playerA, "Tamiyo, Field Researcher", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Bronze Sable", 1); // 2/1

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tamiyo, Field Researcher");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+1: Choose up to two");
        addTarget(playerA, "Bronze Sable");

        attack(1, playerA, "Bronze Sable");

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerB, 18);
        assertHandCount(playerA, 1);
    }

    /*
     * Testing more basic scenario with Tamiyo, Field of Researcher +1 effect
     */
    @Test
    public void testFieldResearcherFirstEffectSimpleCreaturesAttacks() {

        // Tamiyo, Field Researcher {1}{G}{W}{U} - 4 loyalty
        // +1: Choose up to two target creatures. Until your next turn,
        // whenever either of those creatures deals combat damage, you draw a card.
        addCard(Zone.HAND, playerA, "Tamiyo, Field Researcher", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Bronze Sable", 1); // 2/1
        addCard(Zone.BATTLEFIELD, playerA, "Sylvan Advocate", 1); // 2/3

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tamiyo, Field Researcher");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+1: Choose up to two");
        addTarget(playerA, "Bronze Sable^Sylvan Advocate");

        attack(1, playerA, "Bronze Sable");
        attack(1, playerA, "Sylvan Advocate");

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerB, 16);
        assertHandCount(playerA, 2);
    }

    /*
     * Testing more basic scenarios with Tamiyo, Field of Researcher +1 effect
     */
    @Test
    public void testFieldResearcherFirstEffectAttackAndBlock() {

        // Tamiyo, Field Researcher {1}{G}{W}{U} - 4 loyalty
        // +1: Choose up to two target creatures. Until your next turn,
        // whenever either of those creatures deals combat damage, you draw a card.
        addCard(Zone.HAND, playerA, "Tamiyo, Field Researcher", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Sylvan Advocate", 1); // 2/3
        addCard(Zone.BATTLEFIELD, playerB, "Memnite", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tamiyo, Field Researcher");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+1: Choose up to two");
        addTarget(playerA, "Sylvan Advocate");

        attack(1, playerA, "Sylvan Advocate");
        attack(2, playerB, "Memnite");
        block(2, playerA, "Sylvan Advocate", "Memnite");

        setStopAt(2, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerB, 18);
        assertHandCount(playerA, 2); // Sylvan Advocate dealt combat damage twice
    }

    /*
     * Reported bug: Tamiyo's +1 ability remains on the creature for the entirety of the game.
     */
    @Test
    public void testFieldResearcherFirstEffectOnlyPersistsUntilYourNextTurn() {

        /*
        // Tamiyo, Field Researcher {1}{G}{W}{U} - 4 loyalty
            +1: Choose up to two target creatures. Until your next turn, whenever either of those creatures deals combat damage, you draw a card.
            −2: Tap up to two target nonland permanents. They don't untap during their controller's next untap step.
            −7: Draw three cards. You get an emblem with "You may cast nonland cards from your hand without paying their mana costs."
         */
        addCard(Zone.HAND, playerA, "Tamiyo, Field Researcher", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Sylvan Advocate", 1); // 2/3

        addCard(Zone.HAND, playerB, "Hero's Downfall", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Memnite", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tamiyo, Field Researcher");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+1: Choose up to two");
        addTarget(playerA, "Sylvan Advocate");

        attack(1, playerA, "Sylvan Advocate");

        attack(2, playerB, "Memnite");
        block(2, playerA, "Sylvan Advocate", "Memnite");

        castSpell(3, PhaseStep.UPKEEP, playerB, "Hero's Downfall");
        addTarget(playerB, "Tamiyo, Field Researcher");

        attack(3, playerA, "Sylvan Advocate"); // should not get extra card

        setStopAt(3, PhaseStep.END_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Tamiyo, Field Researcher", 1);
        assertGraveyardCount(playerB, "Hero's Downfall", 1);
        assertLife(playerB, 16);
        assertHandCount(playerA, 3); // 2 cards drawn from Advocate + 1 card during T3 draw step.
    }

    /*
     *  I activated his +1 ability once. then, the next turn, i activated it one more time, and then
     *  i get to draw 3 cards of three creatures. So i think the first activation wasn't away.
     */
    @Test
    public void testDrawEffectGetsRemoved() {

        /*
        // Tamiyo, Field Researcher {1}{G}{W}{U} - 4 loyalty
            +1: Choose up to two target creatures. Until your next turn, whenever either of those creatures deals combat damage, you draw a card.
            −2: Tap up to two target nonland permanents. They don't untap during their controller's next untap step.
            −7: Draw three cards. You get an emblem with "You may cast nonland cards from your hand without paying their mana costs."
         */
        addCard(Zone.HAND, playerA, "Tamiyo, Field Researcher", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Sylvan Advocate", 1); // 2/3
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1); // 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Pillarfield Ox", 1); // 2/4

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tamiyo, Field Researcher");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+1: Choose up to two", "Sylvan Advocate");

        attack(1, playerA, "Sylvan Advocate");

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "+1: Choose up to two", "Pillarfield Ox^Silvercoat Lion");

        attack(3, playerA, "Pillarfield Ox");
        attack(3, playerA, "Silvercoat Lion");

        setStopAt(3, PhaseStep.END_COMBAT);
        execute();

        assertPermanentCount(playerA, "Tamiyo, Field Researcher", 1);
        assertLife(playerB, 14);
        assertHandCount(playerA, 4); // 3 cards drawn from attackers + 1 card during T3 draw step.
    }

    @Test
    public void testFieldResearcherFirstAbilityTargetOpponentCreature() {
        /*
        // Tamiyo, Field Researcher {1}{G}{W}{U} - 4 loyalty
            +1: Choose up to two target creatures. Until your next turn, whenever either of those creatures deals combat damage, you draw a card.
            −2: Tap up to two target nonland permanents. They don't untap during their controller's next untap step.
            −7: Draw three cards. You get an emblem with "You may cast nonland cards from your hand without paying their mana costs."
         */
        addCard(Zone.HAND, playerA, "Tamiyo, Field Researcher", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        addCard(Zone.BATTLEFIELD, playerB, "Bronze Sable", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tamiyo, Field Researcher");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+1: Choose up to two");
        addTarget(playerA, "Bronze Sable");

        attack(2, playerB, "Bronze Sable");

        setStopAt(2, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerA, 18);
        assertHandCount(playerA, 1);
    }

    @Test
    public void testFieldResearcherFirstAbilityTargetOpponentCreatures() {
        /*
        // Tamiyo, Field Researcher {1}{G}{W}{U} - 4 loyalty
            +1: Choose up to two target creatures. Until your next turn, whenever either of those creatures deals combat damage, you draw a card.
            −2: Tap up to two target nonland permanents. They don't untap during their controller's next untap step.
            −7: Draw three cards. You get an emblem with "You may cast nonland cards from your hand without paying their mana costs."
         */
        addCard(Zone.HAND, playerA, "Tamiyo, Field Researcher", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        addCard(Zone.BATTLEFIELD, playerB, "Bronze Sable", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Memnite", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tamiyo, Field Researcher");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+1: Choose up to two");
        addTarget(playerA, "Bronze Sable^Memnite");

        attack(2, playerB, "Bronze Sable");
        attack(2, playerB, "Memnite");

        setStopAt(2, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerA, 17);
        assertHandCount(playerA, 2);
    }
}
