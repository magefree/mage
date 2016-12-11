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

import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class AnimateArtifactTest extends CardTestPlayerBase {

    @Test
    public void testAnimateArtifact() {

        addCard(Zone.BATTLEFIELD, playerA, "Crucible of Worlds");

        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);

        // Enchant artifact
        // As long as enchanted artifact isn't a creature, it's an artifact creature with power and toughness each equal to its converted mana cost.
        addCard(Zone.HAND, playerA, "Animate Artifact", 1); // Enchantment {3}{U}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Animate Artifact", "Crucible of Worlds");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Crucible of Worlds", 1);
        assertPermanentCount(playerA, "Animate Artifact", 1);
        assertType("Crucible of Worlds", CardType.CREATURE, null);
        assertPowerToughness(playerA, "Crucible of Worlds", 3, 3);
    }

    @Test
    public void testAnimateArtifactCreature() {

        addCard(Zone.BATTLEFIELD, playerA, "Juggernaut");// Artifact Creature - Juggernaut {4}

        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);

        // Enchant artifact
        // As long as enchanted artifact isn't a creature, it's an artifact creature with power and toughness each equal to its converted mana cost.
        addCard(Zone.HAND, playerA, "Animate Artifact", 1); // Enchantment {3}{U}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Animate Artifact", "Juggernaut");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Juggernaut", 1);
        assertPermanentCount(playerA, "Animate Artifact", 1);
        assertType("Juggernaut", CardType.CREATURE, "Juggernaut");
        assertPowerToughness(playerA, "Juggernaut", 5, 3);
    }

}
