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
package org.mage.test.cards.abilities.equipped;

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
public class EquipRestrictedTest extends CardTestPlayerBase {

    @Test
    public void testEquipLeoninScimitarToNonLegendary() {
        addCard(Zone.BATTLEFIELD, playerB, "Auriok Windwalker");
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerB, "Leonin Scimitar");

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "{T}: Attach target Equipment you control to target creature you control.", "Leonin Scimitar");
        addTarget(playerB, "Silvercout Lion");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        Permanent equipment = getPermanent("Leonin Scimitar", playerB);
        Assert.assertTrue("Leonin Scimitar has to be attached", equipment.getAttachedTo() != null);
    }

    /**
     * Konda's Banner, Gate Smasher, and O-Naginata can all be attached to
     * creatures that they should not be able to be attached to. The filter is
     * only applied to the EquipAbility and does not check other methods (such
     * as Auriok Windwaker)
     */
    @Test
    public void testEquipKondasBannerToNonLegendary() {
        addCard(Zone.BATTLEFIELD, playerB, "Auriok Windwalker");
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerB, "Konda's Banner");

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "{T}: Attach target Equipment you control to target creature you control.", "Konda's Banner");
        addTarget(playerB, "Silvercout Lion");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        Permanent equipment = getPermanent("Konda's Banner", playerB);
        Assert.assertTrue("Konda's Banner may not be attached", equipment.getAttachedTo() == null);
    }

    @Test
    public void testEquipGateSmasher() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        // {1}{W}: Kranioceros gets +0/+3 until end of turn.
        addCard(Zone.BATTLEFIELD, playerA, "Kranioceros");// 5/2
        // Gate Smasher can be attached only to a creature with toughness 4 or greater.
        // Equipped creature gets +3/+0 and has trample.
        // Equip {3}
        addCard(Zone.BATTLEFIELD, playerA, "Gate Smasher");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{W}:");
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Equip", "Kranioceros");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, "Kranioceros", 8, 5);
        Permanent equipment = getPermanent("Gate Smasher", playerA);
        Assert.assertTrue("Gate Smasher may no longer be attached", equipment.getAttachedTo() != null);
    }

    @Test
    public void testEquipGateSmasherAndUnattached() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        // {1}{W}: Kranioceros gets +0/+3 until end of turn.
        addCard(Zone.BATTLEFIELD, playerA, "Kranioceros");// 5/2
        // Gate Smasher can be attached only to a creature with toughness 4 or greater.
        // Equipped creature gets +3/+0 and has trample.
        // Equip {3}
        addCard(Zone.BATTLEFIELD, playerA, "Gate Smasher");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{W}:");
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Equip", "Kranioceros");

        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, "Kranioceros", 5, 2);
        Permanent equipment = getPermanent("Gate Smasher", playerA);
        Assert.assertTrue("Gate Smasher may no longer be attached", equipment.getAttachedTo() == null);
    }
}
