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
public class UlamogTheInfiniteGyreTest extends CardTestPlayerBase {

    /**
     * Tests if Ulamog, the Infinite Gyre is countered its triggered ability
     * resolves anyway
     */
    @Test
    public void testDisabledEffectOnChangeZone() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 11);
        addCard(Zone.HAND, playerA, "Ulamog, the Infinite Gyre");

        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.HAND, playerB, "Counterspell", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ulamog, the Infinite Gyre");
        addTarget(playerA, "Island");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Counterspell", "Ulamog, the Infinite Gyre", "Ulamog, the Infinite Gyre");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, "Ulamog, the Infinite Gyre", 0);
        assertPermanentCount(playerA, "Ulamog, the Infinite Gyre", 0);
        assertGraveyardCount(playerB, "Counterspell", 1);
        assertPermanentCount(playerB, "Island", 1);
    }

    /**
     * If one of the big eldrazi is under the control of someone that is not its
     * owner when it goes to the graveyard, it's ability doesn't trigger
     * correctly.
     */
    @Test
    public void testControlledByOtherPlayer() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 15);
        // When you cast Kozilek, Butcher of Truth, draw four cards.
        // Annihilator 4 (Whenever this creature attacks, defending player sacrifices four permanents.)
        // When Kozilek is put into a graveyard from anywhere, its owner shuffles his or her graveyard into his or her library.
        addCard(Zone.HAND, playerA, "Kozilek, Butcher of Truth"); // {10}
        // Destroy target creature.
        // Spell mastery - If there are two or more instant and/or sorcery cards in your graveyard, you gain 2 life.
        addCard(Zone.HAND, playerA, "Unholy Hunger"); // {3}{B}{B}
        addCard(Zone.GRAVEYARD, playerA, "Silvercoat Lion");

        addCard(Zone.BATTLEFIELD, playerB, "Island", 4);
        addCard(Zone.HAND, playerB, "Control Magic", 1);
        addCard(Zone.GRAVEYARD, playerB, "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Kozilek, Butcher of Truth");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Control Magic", "Kozilek, Butcher of Truth");
        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerA, "Unholy Hunger", "Kozilek, Butcher of Truth");
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Kozilek, Butcher of Truth", 0);
        assertPermanentCount(playerB, "Kozilek, Butcher of Truth", 0);

        assertLife(playerA, 20);

        assertGraveyardCount(playerB, "Silvercoat Lion", 1);
        assertHandCount(playerB, "Control Magic", 0);
        assertGraveyardCount(playerB, "Control Magic", 1);

        assertGraveyardCount(playerA, "Kozilek, Butcher of Truth", 0);

        assertHandCount(playerA, "Kozilek, Butcher of Truth", 0);
        assertGraveyardCount(playerA, "Silvercoat Lion", 0);

        assertGraveyardCount(playerA, "Unholy Hunger", 0);

        assertHandCount(playerA, 4);

    }

    /**
     * Ixidron turned Ulamog, the Infinite Gyre to a 2/2 with no name and then
     * someone used an Wrath of the Gods effect. Ulamog, the Infinite Gyre
     * entered the graveyard without triggering his shuffle effect (just like
     * this case: http://www.mtgsalvation.com/forums/magi ... nd-kozilek).
     */
    @Test
    public void testFaceDownToGraveyard() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        // When you cast Kozilek, Butcher of Truth, draw four cards.
        // Annihilator 4 (Whenever this creature attacks, defending player sacrifices four permanents.)
        // When Kozilek is put into a graveyard from anywhere, its owner shuffles his or her graveyard into his or her library.
        addCard(Zone.BATTLEFIELD, playerA, "Kozilek, Butcher of Truth"); // {10}
        // As Ixidron enters the battlefield, turn all other nontoken creatures face down.
        // Ixidron's power and toughness are each equal to the number of face-down creatures on the battlefield.
        addCard(Zone.HAND, playerA, "Ixidron"); // {3}{U}{U}

        addCard(Zone.BATTLEFIELD, playerB, "Plains", 4);
        addCard(Zone.HAND, playerB, "Wrath of God", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ixidron");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Wrath of God");
        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Ixidron", 0);
        assertGraveyardCount(playerB, "Wrath of God", 1);
        assertGraveyardCount(playerA, 0);
    }
}
