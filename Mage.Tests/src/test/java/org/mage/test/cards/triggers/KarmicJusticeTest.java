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
public class KarmicJusticeTest extends CardTestPlayerBase {

    /*
    Karmic Justice
    Whenever a spell or ability an opponent controls destroys a noncreature permanent you control,
    you may destroy target permanent that opponent controls.
     */
    /**
     * Karmic Justice should triggers for its own destroyment
     */
    @Test
    public void testFirstTriggeredAbility() {
        addCard(Zone.BATTLEFIELD, playerA, "Karmic Justice");

        addCard(Zone.HAND, playerB, "Naturalize");
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Naturalize", "Karmic Justice");
        setChoice(playerA, "Yes");
        addTarget(playerA, "Forest");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Naturalize", 1);
        assertGraveyardCount(playerA, "Karmic Justice", 1);
        assertPermanentCount(playerB, "Forest", 1);

    }

    /**
     * Karmic Justice should triggers for each destroyed permanent
     */
    @Test
    public void testMultiplePermanentsDestroyedTriggeredAbility() {
        // At the beginning of each upkeep, if you lost life last turn, create a 1/1 white Soldier creature token.
        addCard(Zone.BATTLEFIELD, playerA, "First Response", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Karmic Justice");

        // Planar Cleansing {3}{W}{W}{W}
        // Sorcery
        // Destroy all nonland permanents.
        addCard(Zone.HAND, playerB, "Planar Cleansing");
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 1);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Planar Cleansing");
        setChoice(playerA, "Yes");
        addTarget(playerA, "Plains");
        setChoice(playerA, "Yes");
        addTarget(playerA, "Swamp");
        setChoice(playerA, "Yes");
        addTarget(playerA, "Mountain");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Planar Cleansing", 1);
        assertGraveyardCount(playerA, "Karmic Justice", 1);
        assertGraveyardCount(playerA, "First Response", 2);
        assertGraveyardCount(playerB, "Mountain", 1);
        assertGraveyardCount(playerB, "Swamp", 1);
        assertGraveyardCount(playerB, "Plains", 1);

    }

}
