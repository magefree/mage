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

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class FlashbackTest extends CardTestPlayerBase {

    /**
     * Fracturing Gust is bugged. In a match against Affinity, it worked
     * properly when cast from hand. When I cast it from graveyard c/o
     * Snapcaster Mage flashback, it destroyed my opponent's Darksteel Citadels,
     * which it did not do when cast from my hand.
     */
    @Test
    public void testSnapcasterMageWithFracturingGust() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        addCard(Zone.HAND, playerA, "Snapcaster Mage", 1);

        // Destroy all artifacts and enchantments. You gain 2 life for each permanent destroyed this way.
        addCard(Zone.GRAVEYARD, playerA, "Fracturing Gust");

        addCard(Zone.BATTLEFIELD, playerA, "Berserkers' Onslaught", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Darksteel Citadel", 1);

        // When Snapcaster Mage enters the battlefield, target instant or sorcery card in your graveyard gains flashback until end of turn. The flashback cost is equal to its mana cost.
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Snapcaster Mage");
        setChoice(playerA, "Fracturing Gust");

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Flashback {2}{G/W}{G/W}{G/W}"); // now snapcaster mage is died so -13/-13

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Snapcaster Mage", 1);
        assertGraveyardCount(playerA, "Berserkers' Onslaught", 1);

        assertPermanentCount(playerB, "Darksteel Citadel", 1);

        assertExileCount("Fracturing Gust", 1);
    }

    /**
     * My opponent put Iona on the battlefield using Unburial Rites, but my game
     * log didn't show me the color he has chosen.
     *
     */
    @Test
    public void testUnburialRites() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 8);
        // Return target creature card from your graveyard to the battlefield.
        // Flashback {3}{W}
        addCard(Zone.HAND, playerA, "Unburial Rites", 1); // Sorcery - {4}{B}

        // Flying
        // As Iona, Shield of Emeria enters the battlefield, choose a color.
        // Your opponents can't cast spells of the chosen color.
        addCard(Zone.GRAVEYARD, playerA, "Iona, Shield of Emeria");

        // As Lurebound Scarecrow enters the battlefield, choose a color.
        // When you control no permanents of the chosen color, sacrifice Lurebound Scarecrow.
        addCard(Zone.GRAVEYARD, playerA, "Lurebound Scarecrow"); // Enchantment - {2}{U}

        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.HAND, playerB, "Lightning Bolt", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Unburial Rites", "Iona, Shield of Emeria");
        setChoice(playerA, "Red");

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Flashback {3}{W}");
        addTarget(playerA, "Lurebound Scarecrow");
        setChoice(playerA, "White");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", playerA);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Iona, Shield of Emeria", 1);
        assertPermanentCount(playerA, "Lurebound Scarecrow", 1);

        assertHandCount(playerB, "Lightning Bolt", 1);

        assertExileCount("Unburial Rites", 1);
    }

    /**
     *
     */
    @Test
    public void testFlashbackWithConverge() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.HAND, playerA, "Snapcaster Mage", 1);

        // Converge - Put a 1/1 white Kor Ally creature token onto the battlefield for each color of mana spent to cast Unified Front.
        addCard(Zone.GRAVEYARD, playerA, "Unified Front"); // {3}{W}

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {W}");
        // When Snapcaster Mage enters the battlefield, target instant or sorcery card in your graveyard gains flashback until end of turn. The flashback cost is equal to its mana cost.
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Snapcaster Mage");
        setChoice(playerA, "Unified Front");

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Flashback {3}{W}");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Snapcaster Mage", 1);
        assertPermanentCount(playerA, "Kor Ally", 4);
        assertExileCount("Unified Front", 1);

    }

    /**
     * Conflagrate flashback no longer works. Requires mana payment but never
     * allows target selection before resolving.
     */
    @Test
    public void testConflagrate() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 7);

        // Conflagrate deals X damage divided as you choose among any number of target creatures and/or players.
        // Flashback-{R}{R}, Discard X cards.
        addCard(Zone.HAND, playerA, "Conflagrate", 1);

        addCard(Zone.HAND, playerA, "Forest", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Conflagrate");
        setChoice(playerA, "X=2");

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Flashback");
        setChoice(playerA, "X=4");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 14);

        assertExileCount("Conflagrate", 1);

    }

    /**
     * Ancestral Vision has no casting cost (this is different to a casting cost
     * of {0}). Snapcaster Mage, for example, is able to give it flashback
     * whilst it is in the graveyard.
     *
     * However the controller should not be able to cast Ancestral Visions from
     * the graveyard for {0} mana.
     */
    @Test
    public void testFlashbackAncestralVision() {
        // Suspend 4-{U}
        // Target player draws three cards.
        addCard(Zone.GRAVEYARD, playerA, "Ancestral Vision", 1);

        // Flash
        // When Snapcaster Mage enters the battlefield, target instant or sorcery card in your graveyard gains flashback until end of turn. The flashback cost is equal to its mana cost.
        addCard(Zone.HAND, playerA, "Snapcaster Mage", 1);

        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Snapcaster Mage");
        addTarget(playerA, "Ancestral Vision");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Flashback");
        addTarget(playerA, playerA);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, "Snapcaster Mage", 0);
        assertPermanentCount(playerA, "Snapcaster Mage", 1);
        assertGraveyardCount(playerA, "Ancestral Vision", 1);
        assertHandCount(playerA, 0);

    }

    /**
     * I cast Runic Repetition targeting a Silent Departure in exile, and
     * afterwards I cast the Silent Departure from my hand. When it resolves, it
     * goes back to exile instead of ending up in my graveyard. Looks like a
     * problem with Runic Repetition?
     */
    @Test
    public void testFlashbackReturnToHandAndCastAgain() {
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 2);

        addCard(Zone.BATTLEFIELD, playerA, "Island", 9);
        // Return target creature to its owner's hand.
        // Flashback {4}{U}
        addCard(Zone.GRAVEYARD, playerA, "Silent Departure", 1); // {U}
        addCard(Zone.HAND, playerA, "Runic Repetition", 1);// {2}{U}

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Flashback");
        addTarget(playerA, "Silvercoat Lion");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Runic Repetition");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Silent Departure", "Silvercoat Lion");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, "Silvercoat Lion", 2);
        assertExileCount("Silent Departure", 0);
        assertGraveyardCount(playerA, "Silent Departure", 1);
        assertGraveyardCount(playerA, "Runic Repetition", 1);

    }
}
