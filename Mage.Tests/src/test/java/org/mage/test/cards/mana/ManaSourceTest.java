package org.mage.test.cards.mana;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class ManaSourceTest extends CardTestPlayerBase {

    /**
     * I can use Simian Spirit Guide's mana to cast Myr Superion, but it is a
     * creature card and not a creature when it is in hand, so it's wrong.
     */
    @Test
    public void testCantCastWithCreatureCard() {
        // Exile Simian Spirit Guide from your hand: Add {R}.
        addCard(Zone.HAND, playerB, "Simian Spirit Guide", 1);
        // Spend only mana produced by creatures to cast Myr Superion.
        addCard(Zone.HAND, playerB, "Myr Superion", 1); // {2}

        addCard(Zone.BATTLEFIELD, playerB, "Manakin", 1);

        activateManaAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Exile");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Myr Superion");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertExileCount("Simian Spirit Guide", 1);

        assertPermanentCount(playerB, "Myr Superion", 0);
        assertHandCount(playerB, "Myr Superion", 1);
    }

}
