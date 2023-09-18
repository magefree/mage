

package org.mage.test.cards.abilities.oneshot.counterspell;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Counterbalance
 * Enchantment, UU
 * Whenever an opponent casts a spell, you may reveal the top card of your library. If you do, counter that spell
 * if it has the same converted mana cost as the revealed card.
 *
 * @author LevelX2
 */
public class CounterbalanceTest extends CardTestPlayerBase {

    /**
     * Test that X mana costs from spells are taken into account to calculate the converted mana costs
     * of stack objects
     */
    @Test
    public void testCommand() {
        addCard(Zone.HAND, playerA, "Death Grasp");
        // Sorcery {X}{W}{B}
        // Death Grasp deals X damage to any target. You gain X life.
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);

        addCard(Zone.BATTLEFIELD, playerB, "Counterbalance");
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);

        addCard(Zone.LIBRARY, playerB, "Desert Twister"); // cmc = 6 ({G}{G}{4} because DeathGrasp = 2 + 4 (of X) = 6
        skipInitShuffling(); // so the set to top card stays at top

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Death Grasp", "targetPlayer=PlayerB");
        setChoice(playerA, "X=4");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertGraveyardCount(playerA, "Death Grasp", 1);
        assertGraveyardCount(playerA, 1);
        assertGraveyardCount(playerB, 0);

    }

    /**
     * Test that if the top card is a split card, the total of both halves of the split card
     * count to counter the spell. If one of the split cards halves has the equal casting
     * cost, the spell is not countered.
     *
     */

    @Test
    public void testSplitCard() {
        addCard(Zone.HAND, playerA, "Nessian Courser");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);

        addCard(Zone.BATTLEFIELD, playerB, "Counterbalance");
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);

        addCard(Zone.LIBRARY, playerB, "Wear // Tear"); // CMC 3
        skipInitShuffling(); // so the set to top card stays at top

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Nessian Courser");
        setChoice(playerB, true);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Nessian Courser", 0);
        assertGraveyardCount(playerA, "Nessian Courser", 1);
        assertGraveyardCount(playerA, 1);
        assertGraveyardCount(playerB, 0);

    }
}
