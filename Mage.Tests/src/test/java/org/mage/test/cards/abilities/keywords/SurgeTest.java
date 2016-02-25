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
public class SurgeTest extends CardTestPlayerBase {

    /**
     *
     */
    @Test
    public void testWithoutSurge() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        // Surge {3}{U}{U} (You may cast this spell for its surge cost if you or a teammate has cast another spell this turn)
        // Return all nonland permanents to their owners' hands. If Crush of Tentacles surge cost was paid, put an 8/8 blue Octopus creature token onto the battlefield.
        addCard(Zone.HAND, playerA, "Crush of Tentacles"); // {4}{U}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Crush of Tentacles");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Crush of Tentacles", 1);
        assertHandCount(playerA, "Silvercoat Lion", 1);
        assertHandCount(playerB, "Silvercoat Lion", 1);
        assertPermanentCount(playerA, 6);
    }

    @Test
    public void testWithSurge() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        // Surge {3}{U}{U} (You may cast this spell for its surge cost if you or a teammate has cast another spell this turn)
        // Return all nonland permanents to their owners' hands. If Crush of Tentacles surge cost was paid, put an 8/8 blue Octopus creature token onto the battlefield.
        addCard(Zone.HAND, playerA, "Crush of Tentacles"); // {4}{U}{U}
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");

        // Put a token onto the battlefield that's a copy of target creature you control.
        // Flashback {5}{U}{U}(You may cast this card from your graveyard for its flashback cost. Then exile it.)
        addCard(Zone.HAND, playerB, "Cackling Counterpart");
        addCard(Zone.BATTLEFIELD, playerB, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Cackling Counterpart", "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Crush of Tentacles with surge");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Lightning Bolt", 1);
        assertGraveyardCount(playerB, "Cackling Counterpart", 1);
        assertGraveyardCount(playerA, "Crush of Tentacles", 1);
        assertPermanentCount(playerA, "Octopus", 1);
        assertPermanentCount(playerB, "Silvercoat Lion", 0);
        assertHandCount(playerA, "Silvercoat Lion", 1);
        assertHandCount(playerB, "Silvercoat Lion", 1);
        assertPermanentCount(playerA, 7);

        assertLife(playerB, 17);
    }

    @Test
    public void testTyrantOfValakut() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);
        // Surge {3}{R}{R} (You may cast this spell for its surge cost if you or a teammate has cast another spell this turn)
        // Flying
        // When Tyrant of Valakut enters the battlefield, if its surge cost was paid, it deals 3 damage to target creature or player.
        addCard(Zone.HAND, playerA, "Tyrant of Valakut"); // {5}{R}{R}
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tyrant of Valakut");
        addTarget(playerA, playerB);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Lightning Bolt", 1);
        assertPermanentCount(playerA, "Tyrant of Valakut", 1);

        assertLife(playerB, 14);
    }

    @Test
    public void testContainmentMembrane() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        // Enchant creature
        // Enchanted creature doesn't untap during its controller's untap step.
        // Surge {U} (You may cast a spell for its surge cost if you or a teammate have cast another spell in the same turn.)
        addCard(Zone.HAND, playerA, "Containment Membrane"); // {2}{U}
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1, true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Containment Membrane", "Silvercoat Lion");

        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertLife(playerB, 17);
        assertGraveyardCount(playerA, "Lightning Bolt", 1);
        assertPermanentCount(playerA, "Containment Membrane", 1);

        assertTapped("Silvercoat Lion", true);

    }

}
