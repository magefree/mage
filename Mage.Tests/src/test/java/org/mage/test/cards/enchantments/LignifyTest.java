
package org.mage.test.cards.enchantments;

import mage.abilities.keyword.IndestructibleAbility;
import mage.constants.PhaseStep;
import mage.constants.SubType;
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

        Assert.assertFalse("Sliver Hivelord may not be of subtype Sliver", hivelord.hasSubtype(SubType.SLIVER, currentGame));

    }

}
