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
package org.mage.test.cards.rules;

import mage.abilities.keyword.IndestructibleAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class CantBeEnchantedTest extends CardTestPlayerBase {

    @Test
    public void testConsecrateLand() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains");

        // Enchant land
        // Enchanted land is indestructible and can't be enchanted by other Auras.
        addCard(Zone.HAND, playerA, "Consecrate Land", 1); // Enchantment {W}

        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        // Enchant land
        // Whenever enchanted land becomes tapped, Psychic Venom deals 2 damage to that land's controller.
        addCard(Zone.HAND, playerB, "Psychic Venom", 1); // Enchantment {1}{U}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Consecrate Land", "Plains");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Psychic Venom", "Plains");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Consecrate Land", 0);
        assertPermanentCount(playerA, "Consecrate Land", 1);
        assertAbility(playerA, "Plains", IndestructibleAbility.getInstance(), true);
        assertPermanentCount(playerB, "Psychic Venom", 0);
        assertGraveyardCount(playerB, "Psychic Venom", 1);

    }

    @Test
    public void testConsecrateLandEnchantedBefore() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains");

        // Enchant land
        // Enchanted land is indestructible and can't be enchanted by other Auras.
        addCard(Zone.HAND, playerA, "Consecrate Land", 1); // Enchantment {W}

        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        // Enchant land
        // Whenever enchanted land becomes tapped, Psychic Venom deals 2 damage to that land's controller.
        addCard(Zone.HAND, playerB, "Psychic Venom", 1); // Enchantment {1}{U}

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Psychic Venom", "Plains");
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Consecrate Land", "Plains");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Consecrate Land", 0);
        assertPermanentCount(playerA, "Consecrate Land", 1);
        assertAbility(playerA, "Plains", IndestructibleAbility.getInstance(), true);

        assertLife(playerA, 18);
        assertPermanentCount(playerB, "Psychic Venom", 0);
        assertGraveyardCount(playerB, "Psychic Venom", 1);
        assertHandCount(playerB, "Psychic Venom", 0);

    }
}
