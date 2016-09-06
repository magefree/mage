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

import mage.abilities.keyword.IndestructibleAbility;
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
public class LignifyTest extends CardTestPlayerBase {

    /**
     * Lignify shouldn't make the creature it enchants a (whatever it
     * is)treefolk as it makes the creature just a treefolk i.e. a Sliver
     * Hivelord enchanted by lignify should be just a treefolk and not a sliver
     * treefolk
     */
    @Test
    public void LooseType() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        // Enchant creature
        // Enchanted creature is a Treefolk with base power and toughness 0/4 and loses all abilities.
        addCard(Zone.HAND, playerA, "Lignify", 1); // {1}{G}

        // Sliver creatures you control have indestructible.
        addCard(Zone.BATTLEFIELD, playerB, "Sliver Hivelord", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lignify", "Sliver Hivelord");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Lignify", 1);
        assertPermanentCount(playerB, "Sliver Hivelord", 1);

        assertAbility(playerB, "Sliver Hivelord", IndestructibleAbility.getInstance(), false);
        assertPowerToughness(playerB, "Sliver Hivelord", 0, 4);

        Permanent hivelord = getPermanent("Sliver Hivelord", playerB);

        Assert.assertFalse("Sliver Hivelord may not be of subtype Sliver", hivelord.getSubtype(currentGame).contains("Sliver"));

    }

}
