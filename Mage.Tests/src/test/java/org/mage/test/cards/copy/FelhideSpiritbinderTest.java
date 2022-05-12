
package org.mage.test.cards.copy;

import mage.abilities.keyword.HasteAbility;
import mage.constants.CardType;
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
public class FelhideSpiritbinderTest extends CardTestPlayerBase {

    /**
     * http://www.slightlymagic.net/forum/viewtopic.php?f=70&t=17295&p=181417#p181440
     * Felhide Spiritbinder does not seem to be giving haste or the enchantment
     * subtype to tokens it creates..
     *
     */
    @Test
    public void testTokenCopy() {
        // Inspired - Whenever Felhide Spiritbinder becomes untapped, you may pay {1}{R}.
        // If you do, put a token onto the battlefield that's a copy of another target creature
        // except it's an enchantment in addition to its other types. It gains haste. Exile it at the beginning of the next end step.
        addCard(Zone.BATTLEFIELD, playerB, "Felhide Spiritbinder", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 2);

        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);

        attack(2, playerB, "Felhide Spiritbinder");

        setStopAt(4, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerB, "Silvercoat Lion", 1);
        Permanent lion = getPermanent("Silvercoat Lion", playerB);
        assertAbility(playerB, "Silvercoat Lion", HasteAbility.getInstance(), true);
        Assert.assertEquals("token has to have card type enchantment", true, lion.getCardType(currentGame).contains(CardType.ENCHANTMENT));

        assertLife(playerA, 17);
        assertLife(playerB, 20);

    }

    @Test
    public void testTokenCopyExiled() {
        // Inspired - Whenever Felhide Spiritbinder becomes untapped, you may pay {1}{R}.
        // If you do, put a token onto the battlefield that's a copy of another target creature
        // except it's an enchantment in addition to its other types. It gains haste. Exile it at the beginning of the next end step.
        addCard(Zone.BATTLEFIELD, playerB, "Felhide Spiritbinder", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 2);

        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);

        attack(2, playerB, "Felhide Spiritbinder");

        setStopAt(5, PhaseStep.UPKEEP);
        execute();

        assertPermanentCount(playerB, "Silvercoat Lion", 0);

        assertLife(playerA, 17);
        assertLife(playerB, 20);

    }

    @Test
    public void testCopyATokenCreature() {

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.HAND, playerA, "Call of the Herd", 1);

        // Inspired - Whenever Felhide Spiritbinder becomes untapped, you may pay {1}{R}.
        // If you do, put a token onto the battlefield that's a copy of another target creature
        // except it's an enchantment in addition to its other types. It gains haste. Exile it at the beginning of the next end step.
        addCard(Zone.BATTLEFIELD, playerB, "Felhide Spiritbinder", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Call of the Herd");

        attack(2, playerB, "Felhide Spiritbinder");

        setStopAt(4, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Elephant Token", 1);
        assertPermanentCount(playerB, "Elephant Token", 1);
        assertAbility(playerB, "Elephant Token", HasteAbility.getInstance(), true);

        Permanent copiedTokenElephant = getPermanent("Elephant Token", playerB);
        Assert.assertEquals("Elephant has Enchantment card type", true, copiedTokenElephant.getCardType(currentGame).contains(CardType.ENCHANTMENT));

        assertLife(playerA, 17);
        assertLife(playerB, 20);

    }
}
