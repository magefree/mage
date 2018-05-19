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
package org.mage.test.cards.continuous;

import mage.ObjectColor;
import mage.abilities.AbilitiesImpl;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author JayDi85
 */
public class BecomesCreatureAttachedTest extends CardTestPlayerBase {

    // Dryad Arbor -- green creature land

    @Test
    public void test_CreatureLandWithColor() {
        addCard(Zone.BATTLEFIELD, playerA, "Dryad Arbor", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Dryad Arbor", 1);
        assertPowerToughness(playerA, "Dryad Arbor", 1, 1);
        // land
        assertColor(playerA, "Forest", "WUBGR", false);
        // dryad
        assertColor(playerA, "Dryad Arbor", "G", true);
        assertColor(playerA, "Dryad Arbor", "WUBR", false);
    }

    @Test
    public void test_AttachToLandWithColorReplace() {
        // Enchanted land is a 2/2 blue Elemental creature with flying. It’s still a land.
        addCard(Zone.HAND, playerA, "Wind Zendikon", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);

        addCard(Zone.BATTLEFIELD, playerA, "Dryad Arbor", 1);

        // attach to forest and check color changing
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Wind Zendikon", "Dryad Arbor");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Dryad Arbor", 1);
        assertPowerToughness(playerA, "Dryad Arbor", 2, 2);
        assertType("Dryad Arbor", CardType.CREATURE, true);
        assertType("Dryad Arbor", CardType.LAND, true);
        assertAbilities(playerA, "Dryad Arbor", new AbilitiesImpl<>(FlyingAbility.getInstance()));
        assertColor(playerA, "Dryad Arbor", "U", true);
        assertColor(playerA, "Dryad Arbor", "WBGR", false);
    }

    @Test
    public void test_AttachToLandWithColorAdd() {
        // Enchanted land is a 2/2 blue Elemental creature with flying. It’s still a land.
        addCard(Zone.HAND, playerA, "Deep Freeze", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);

        addCard(Zone.BATTLEFIELD, playerA, "Dryad Arbor", 1);

        // attach to forest and check color changing
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Deep Freeze", "Dryad Arbor");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Dryad Arbor", 1);
        assertPowerToughness(playerA, "Dryad Arbor", 0, 4);
        assertType("Dryad Arbor", CardType.CREATURE, true);
        assertType("Dryad Arbor", CardType.LAND, true);
        assertType("Dryad Arbor", CardType.LAND, SubType.WALL);
        assertAbilities(playerA, "Dryad Arbor", new AbilitiesImpl<>(DefenderAbility.getInstance()));
        assertColor(playerA, "Dryad Arbor", "UG", true);
        assertColor(playerA, "Dryad Arbor", "WBR", false);
    }
}
