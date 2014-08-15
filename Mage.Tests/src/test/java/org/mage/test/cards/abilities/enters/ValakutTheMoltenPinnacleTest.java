/*
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

package org.mage.test.cards.abilities.enters;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class ValakutTheMoltenPinnacleTest extends CardTestPlayerBase {

    /**
     * Valakut, the Molten Pinnacle
     * Land
     * Valakut, the Molten Pinnacle enters the battlefield tapped.
     * Whenever a Mountain enters the battlefield under your control, if you control at least five other Mountains, you may have Valakut, the Molten Pinnacle deal 3 damage to target creature or player.
     *  {T}: Add {R} to your mana pool.
     */

    @Test
    public void onlyFourMountainsNoDamage() {

        addCard(Zone.BATTLEFIELD, playerA, "Valakut, the Molten Pinnacle");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);

        playLand(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Mountain");
        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Valakut, the Molten Pinnacle", 1);
        assertPermanentCount(playerA, "Mountain", 5);

        assertLife(playerA, 20);
        assertLife(playerB, 20);
    }

    @Test
    public void fiveMountainsDamageToPlayerB() {

        addCard(Zone.BATTLEFIELD, playerA, "Valakut, the Molten Pinnacle");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);

        playLand(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Mountain"); // 3 damage because already 5 Mountains on battlefield

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Valakut, the Molten Pinnacle", 1);
        assertPermanentCount(playerA, "Mountain", 6);

        assertLife(playerA, 20);
        assertLife(playerB, 17);


    }

        // Scapeshift {2}{G}{G}
        // Sorcery
        // Sacrifice any number of lands. Search your library for that many land cards, put them onto the battlefield tapped, then shuffle your library.

    @Test
    public void sixEnterWithScapeshiftDamageToPlayerB() {

        addCard(Zone.LIBRARY, playerA, "Mountain", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Valakut, the Molten Pinnacle");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 6);
        addCard(Zone.HAND, playerA, "Scapeshift");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Scapeshift");
        addTarget(playerA, "Forest^Forest^Forest^Forest^Forest^Forest");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Valakut, the Molten Pinnacle", 1);
        assertGraveyardCount(playerA, "Forest", 6);
        assertPermanentCount(playerA, "Mountain", 6);

        assertLife(playerA, 20);
        assertLife(playerB, 2); // 6 * 3 damage = 18


    }

    @Test
    public void sixAndValakutEnterWithScapeshiftDamageToPlayerB() {

        addCard(Zone.LIBRARY, playerA, "Mountain", 6);
        addCard(Zone.LIBRARY, playerA, "Valakut, the Molten Pinnacle");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 7);
        addCard(Zone.HAND, playerA, "Scapeshift");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Scapeshift");
        addTarget(playerA, "Forest^Forest^Forest^Forest^Forest^Forest^Forest");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Valakut, the Molten Pinnacle", 1);
        assertGraveyardCount(playerA, "Forest", 7);
        assertPermanentCount(playerA, "Mountain", 6);

        assertLife(playerA, 20);
        assertLife(playerB, 2); // 6 * 3 damage = 18

    }

    // using some shock lands instead of only mountains
    @Test
    public void sixWithShocklandsAndValakutEnterWithScapeshiftDamageToPlayerB() {

        addCard(Zone.LIBRARY, playerA, "Mountain", 3);
        addCard(Zone.LIBRARY, playerA, "Stomping Ground", 3);
        addCard(Zone.LIBRARY, playerA, "Valakut, the Molten Pinnacle");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 7);
        addCard(Zone.HAND, playerA, "Scapeshift");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Scapeshift");
        addTarget(playerA, "Forest^Forest^Forest^Forest^Forest^Forest^Forest");
        setChoice(playerA, "No"); // Stomping Ground can be tapped
        setChoice(playerA, "No"); // Stomping Ground can be tapped
        setChoice(playerA, "No"); // Stomping Ground can be tapped
        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Valakut, the Molten Pinnacle", 1);
        assertGraveyardCount(playerA, "Forest", 7);
        assertPermanentCount(playerA, "Mountain", 3);
        assertPermanentCount(playerA, "Stomping Ground", 3);

        assertLife(playerA, 20);
        assertLife(playerB, 2); // 6 * 3 damage = 18

    }
}
