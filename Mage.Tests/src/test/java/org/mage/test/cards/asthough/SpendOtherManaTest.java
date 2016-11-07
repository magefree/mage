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
package org.mage.test.cards.asthough;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class SpendOtherManaTest extends CardTestPlayerBase {

    /**
     * Mycosynth Lattice doesn't work for floating mana with activated abillites
     * I was trying to activate Sydri, Galvanic Genius with a floating {C}
     * targeting a mountain when I clicked on the <> icon it wouldn't spend the
     * mana.
     */
    @Test
    public void testColorlessCanBeUsed() {
        // All permanents are artifacts in addition to their other types.
        // All cards that aren't on the battlefield, spells, and permanents are colorless.
        // Players may spend mana as though it were mana of any color.
        addCard(Zone.BATTLEFIELD, playerA, "Mycosynth Lattice");

        // {U}: Target noncreature artifact becomes an artifact creature with power and toughness each equal to its converted mana cost until end of turn.
        // {W}{B}: Target artifact creature gains deathtouch and lifelink until end of turn.
        addCard(Zone.BATTLEFIELD, playerA, "Sydri, Galvanic Genius");
        //{T}: Add {C} to your mana pool. ( represents colorless mana.)
        // {1}, {T}: Add one mana of any color to your mana pool.
        addCard(Zone.BATTLEFIELD, playerA, "Unknown Shores");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain");

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {C} to your mana pool");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{U}: Target noncreature artifact becomes an artifact creature with power and toughness", "Mountain");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertTapped("Unknown Shores", true);

        assertPermanentCount(playerB, "Mountain", 0);
    }

    /**
     * Tron mana doesn't work with Oath of Nissa. (e.g. can't cast Chandra,
     * Flamecaller with Urza's Tower, Power Plant, and Mine.)
     *
     * AI don't get the Planeswalker as playable card (probably because of the
     * as thought effect)
     */
    @Test
    public void testOathOfNissa() {
        // When Oath of Nissa enters the battlefield, look at the top three cards of your library. You may reveal a creature, land, or planeswalker card from among them and put it into your hand. Put the rest on the bottom of your library in any order.
        // You may spend mana as though it were mana of any color to cast planeswalker spells.
        addCard(Zone.BATTLEFIELD, playerA, "Oath of Nissa");
        addCard(Zone.BATTLEFIELD, playerA, "Urza's Mine");
        addCard(Zone.BATTLEFIELD, playerA, "Urza's Tower");
        addCard(Zone.BATTLEFIELD, playerA, "Urza's Power Plant");
        // +1: Put two 3/1 red Elemental creature tokens with haste onto the battlefield. Exile them at the beginning of the next end step.
        // 0: Discard all the cards in your hand, then draw that many cards plus one.
        // -X: Chandra, Flamecaller deals X damage to each creature.
        addCard(Zone.HAND, playerA, "Chandra, Flamecaller"); // {4}{R}{R}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Chandra, Flamecaller");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Chandra, Flamecaller", 1);
    }

    /**
     * I was unable to cast Nissa, Voice of Zendikar using black mana with Oath
     * of Nissa in play. Pretty sure Oath is working usually, so here were the
     * conditions in my game:
     *
     * -Cast Dark Petition with spell mastery -Attempt to cast Nissa, Voice of
     * Zendikar using the triple black mana from Dark Petition
     */
    @Test
    public void testOathOfNissaWithDarkPetition() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        // When Oath of Nissa enters the battlefield, look at the top three cards of your library. You may reveal a creature, land, or planeswalker card from among them and put it into your hand. Put the rest on the bottom of your library in any order.
        // You may spend mana as though it were mana of any color to cast planeswalker spells.
        addCard(Zone.BATTLEFIELD, playerA, "Oath of Nissa");
        addCard(Zone.GRAVEYARD, playerA, "Lightning Bolt", 2);

        // Search your library for a card and put that card into your hand. Then shuffle your library.
        // <i>Spell mastery</i> - If there are two or more instant and/or sorcery cards in your graveyard, add {B}{B}{B} to your mana pool.
        addCard(Zone.HAND, playerA, "Dark Petition"); // {3}{B}{B}

        // +1: Create a 0/1 green Plant creature token onto the battlefield.
        // -2: Put a +1/+1 counter on each creature you control.
        // -7: You gain X life and draw X cards, where X is the number of lands you control.
        addCard(Zone.LIBRARY, playerA, "Nissa, Voice of Zendikar"); // {1}{G}{G}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dark Petition");
        setChoice(playerA, "Nissa, Voice of Zendikar");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Nissa, Voice of Zendikar");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Dark Petition", 1);
        assertHandCount(playerA, "Nissa, Voice of Zendikar", 0);
        assertPermanentCount(playerA, "Nissa, Voice of Zendikar", 1);
    }
}
