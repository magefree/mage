
package org.mage.test.cards.enchantments;

import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.SubType;
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
        assertType("Juggernaut", CardType.CREATURE, SubType.JUGGERNAUT);
        assertPowerToughness(playerA, "Juggernaut", 5, 3);
    }

}
