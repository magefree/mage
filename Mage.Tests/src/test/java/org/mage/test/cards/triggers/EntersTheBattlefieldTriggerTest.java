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
package org.mage.test.cards.triggers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class EntersTheBattlefieldTriggerTest extends CardTestPlayerBase {

    @Test
    public void testDrawCardsAddedCounters() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);
        addCard(Zone.HAND, playerA, "Soul Warden");

        // You may have Clever Impersonator enter the battlefield as a copy of any nonland permanent on the battlefield.
        addCard(Zone.HAND, playerB, "Clever Impersonator", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Soul Warden");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Clever Impersonator");
        setChoice(playerB, "Silvercoat Lion");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Soul Warden", 1);
        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertPermanentCount(playerB, "Silvercoat Lion", 1);

        assertLife(playerA, 21);
        assertLife(playerB, 20);
    }

    /**
     * Diluvian Primordial is bugged and doesn't trigger upon entering the
     * battlefield
     */
    @Test
    public void testDiluvianPrimordial() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 7);
        // Flying
        // When Diluvian Primordial enters the battlefield, for each opponent, you may cast up to one target instant or sorcery card from that player's graveyard without paying its mana cost. If a card cast this way would be put into a graveyard this turn, exile it instead.
        addCard(Zone.HAND, playerA, "Diluvian Primordial", 1); // {5}{U}{U}

        addCard(Zone.GRAVEYARD, playerB, "Lightning Bolt");

        // You may have Clever Impersonator enter the battlefield as a copy of any nonland permanent on the battlefield.
        addCard(Zone.HAND, playerB, "Clever Impersonator", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Diluvian Primordial");
        addTarget(playerA, "Lightning Bolt");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Diluvian Primordial", 1);

        assertExileCount("Lightning Bolt", 1);

        assertLife(playerA, 20);
        assertLife(playerB, 17);
    }

    /**
     * Scion of Vitu-Ghazi if it is NOT cast from the hand, it will still allow
     * the Populate effect. It should only allow these when it is cast from
     * hand.
     *
     */
    @Test
    public void testScionOfVituGhaziConditionNotTrue() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        // When Scion of Vitu-Ghazi enters the battlefield, if you cast it from your hand, put a 1/1 white Bird creature token with flying onto the battlefield, then populate.
        addCard(Zone.HAND, playerA, "Scion of Vitu-Ghazi", 1); // 4/4 - {3}{W}{W}
        // Put target creature card from a graveyard onto the battlefield under your control. You lose life equal to its converted mana cost.
        addCard(Zone.HAND, playerA, "Reanimate", 1); // {B}

        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 2);
        // Destroy target nonartifact, nonblack creature. It can't be regenerated.
        addCard(Zone.HAND, playerB, "Terror", 1); // {1}{B}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Scion of Vitu-Ghazi");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Terror", "Scion of Vitu-Ghazi");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Reanimate", "Scion of Vitu-Ghazi");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Terror", 1);

        assertGraveyardCount(playerA, "Reanimate", 1);
        assertPermanentCount(playerA, "Scion of Vitu-Ghazi", 1);
        assertPermanentCount(playerA, "Bird", 2); // only 2 from cast from hand creation and populate. Populate may not trigger from reanimate

        assertLife(playerA, 15);
        assertLife(playerB, 20);
    }

    /**
     * Dread Cacodemon's abilities should only trigger when cast from hand.
     *
     * Testing when cast from hand abilities take effect. Cast from hand
     * destroys opponents creatures and taps all other creatures owner controls.
     */
    @Test
    public void testDreadCacodemonConditionTrue() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 10);

        // When Dread Cacodemon enters the battlefield, if you cast it from your hand, destroy all creatures your opponents control, then tap all other creatures you control.
        addCard(Zone.HAND, playerA, "Dread Cacodemon", 1); // 8/8 - {7}{B}{B}{B}

        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 2);

        // Protection from white, first strike
        addCard(Zone.BATTLEFIELD, playerA, "Black Knight", 2); // {B}{B}
        // Deathtouch
        addCard(Zone.BATTLEFIELD, playerB, "Typhoid Rats", 2); // {B}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dread Cacodemon");
        setStopAt(1, PhaseStep.END_TURN);

        execute();

        assertPermanentCount(playerB, "Typhoid Rats", 0);

        assertPermanentCount(playerA, "Dread Cacodemon", 1);
        assertPermanentCount(playerA, "Black Knight", 2);
        assertTappedCount("Black Knight", true, 2);
        assertTapped("Dread Cacodemon", false);
    }

    /**
     * Dread Cacodemon's abilities should only trigger when cast from hand.
     *
     * Testing when card is not cast from hand, abilities do not take effect.
     * All opponents creatures remain alive and owner's creatures are not
     * tapped.
     */
    @Test
    public void testDreadCacodemonConditionFalse() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 10);

        // When Dread Cacodemon enters the battlefield, if you cast it from your hand, destroy all creatures your opponents control, then tap all other creatures you control.
        addCard(Zone.GRAVEYARD, playerA, "Dread Cacodemon", 1); // 8/8 - {7}{B}{B}{B}
        // Put target creature card from a graveyard onto the battlefield under your control. You lose life equal to its converted mana cost.
        addCard(Zone.HAND, playerA, "Reanimate", 1); // {B}

        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 2);

        // Protection from white, first strike
        addCard(Zone.BATTLEFIELD, playerA, "Black Knight", 2); // {B}{B}
        // Deathtouch
        addCard(Zone.BATTLEFIELD, playerB, "Typhoid Rats", 2); // {B}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Reanimate", "Dread Cacodemon");
        setStopAt(1, PhaseStep.END_TURN);

        execute();

        assertPermanentCount(playerB, "Typhoid Rats", 2);

        assertGraveyardCount(playerA, "Reanimate", 1);
        assertPermanentCount(playerA, "Dread Cacodemon", 1);
        assertPermanentCount(playerA, "Black Knight", 2);
        assertTappedCount("Black Knight", false, 2);
        assertTapped("Dread Cacodemon", false);

        assertLife(playerA, 10); // loses 10 life from reanimating Dread Cacodemon at 10 CMC
        assertLife(playerB, 20);
    }

    /**
     * Test that the cast from hand condition works for target permanent
     *
     */
    @Test
    public void testWildPair() {

        // Whenever a creature enters the battlefield, if you cast it from your hand, you may search your library for a creature card with the same total power and toughness and put it onto the battlefield. If you do, shuffle your library.
        addCard(Zone.BATTLEFIELD, playerA, "Wild Pair");
        addCard(Zone.HAND, playerA, "Silvercoat Lion", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        setChoice(playerA, "Silvercoat Lion");
        addCard(Zone.LIBRARY, playerA, "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Silvercoat Lion");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);

        execute();

        assertPermanentCount(playerA, "Silvercoat Lion", 2);

    }

}
