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
package org.mage.test.cards.enchantments;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class OathOfLiegesTest extends CardTestPlayerBase {

    @Test
    public void testSearchLandOwner() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        // At the beginning of each player's upkeep, that player chooses target player who controls more lands than he or she does and is his or her opponent.
        // The first player may search his or her library for a basic land card, put that card onto the battlefield, then shuffle his or her library.
        addCard(Zone.HAND, playerA, "Oath of Lieges", 1); // {1}{W}
        addCard(Zone.LIBRARY, playerA, "Plains", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Plains", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Oath of Lieges");
        addTarget(playerA, playerB);
        addTarget(playerA, "Plains");

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Oath of Lieges", 1);
        assertPermanentCount(playerA, "Plains", 3);

    }

    @Test
    public void testSearchLandOpponent() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        // At the beginning of each player's upkeep, that player chooses target player who controls more lands than he or she does and is his or her opponent.
        // The first player may search his or her library for a basic land card, put that card onto the battlefield, then shuffle his or her library.
        addCard(Zone.HAND, playerA, "Oath of Lieges", 1); // {1}{W}

        addCard(Zone.BATTLEFIELD, playerB, "Plains", 1);
        addCard(Zone.LIBRARY, playerB, "Plains", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Oath of Lieges");
        addTarget(playerB, playerA);
        addTarget(playerB, "Plains");

        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Oath of Lieges", 1);
        assertPermanentCount(playerB, "Plains", 2);
    }

    @Test
    public void testSearchLandOwnerCopy() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        // At the beginning of each player's upkeep, that player chooses target player who controls more lands than he or she does and is his or her opponent.
        // The first player may search his or her library for a basic land card, put that card onto the battlefield, then shuffle his or her library.
        addCard(Zone.HAND, playerA, "Oath of Lieges", 1); // {1}{W}
        addCard(Zone.LIBRARY, playerA, "Plains", 3);
        addCard(Zone.HAND, playerA, "Plains", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Island", 3);
        addCard(Zone.HAND, playerB, "Copy Enchantment", 1); // {2}{U}
        addCard(Zone.LIBRARY, playerB, "Plains", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Oath of Lieges");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Copy Enchantment");
        setChoice(playerB, "Oath of Lieges");

        // turn 3
        addTarget(playerA, playerB);
        addTarget(playerA, "Plains"); // 3rd land
        addTarget(playerA, "Plains"); // second trigger will fail because target player has no longer more lands than controller

        playLand(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Plains"); // 4th land

        // turn 4
        addTarget(playerB, playerA);
        addTarget(playerB, "Plains");
        addTarget(playerB, "Plains"); // second trigger will fail because target player has no longer more lands than controller

        setStopAt(4, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerB, "Oath of Lieges", 1);
        assertPermanentCount(playerA, "Oath of Lieges", 1);

        assertPermanentCount(playerB, "Plains", 1);
        assertPermanentCount(playerA, "Plains", 4);

    }
}
