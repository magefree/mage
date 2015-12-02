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
package org.mage.test.cards.replacement.entersBattlefield;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class KjeldoranOutpostTest extends CardTestPlayerBase {

    @Test
    public void testNoPlainsAvailable() {
        // If Kjeldoran Outpost would enter the battlefield, sacrifice a Plains instead. If you do, put Kjeldoran Outpost onto the battlefield. If you don't, put it into its owner's graveyard.
        // {T}: Add {W} to your mana pool.
        // {1}{W}, {tap}: Put a 1/1 white Soldier creature token onto the battlefield.
        addCard(Zone.HAND, playerA, "Kjeldoran Outpost");

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Kjeldoran Outpost");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Kjeldoran Outpost", 0);
        assertGraveyardCount(playerA, "Kjeldoran Outpost", 1);
    }

    @Test
    public void testPlainsAvailable() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        // If Kjeldoran Outpost would enter the battlefield, sacrifice a Plains instead. If you do, put Kjeldoran Outpost onto the battlefield. If you don't, put it into its owner's graveyard.
        // {T}: Add {W} to your mana pool.
        // {1}{W}, {tap}: Put a 1/1 white Soldier creature token onto the battlefield.
        addCard(Zone.HAND, playerA, "Kjeldoran Outpost");

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Kjeldoran Outpost");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Kjeldoran Outpost", 1);
        assertGraveyardCount(playerA, "Kjeldoran Outpost", 0);
        assertGraveyardCount(playerA, "Plains", 1);
    }

    @Test
    public void testOnlySnowcoveredPlainsAvailable() {
        addCard(Zone.BATTLEFIELD, playerA, "Snow-Covered Plains");
        // If Kjeldoran Outpost would enter the battlefield, sacrifice a Plains instead. If you do, put Kjeldoran Outpost onto the battlefield. If you don't, put it into its owner's graveyard.
        // {T}: Add {W} to your mana pool.
        // {1}{W}, {tap}: Put a 1/1 white Soldier creature token onto the battlefield.
        addCard(Zone.HAND, playerA, "Kjeldoran Outpost");

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Kjeldoran Outpost");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Kjeldoran Outpost", 1);
        assertGraveyardCount(playerA, "Kjeldoran Outpost", 0);
        assertGraveyardCount(playerA, "Snow-Covered Plains", 1);
    }

}
