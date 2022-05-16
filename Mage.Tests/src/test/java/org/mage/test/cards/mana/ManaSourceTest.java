package org.mage.test.cards.mana;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
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
        addCard(Zone.HAND, playerA, "Simian Spirit Guide", 1);
        // Spend only mana produced by creatures to cast Myr Superion.
        addCard(Zone.HAND, playerA, "Myr Superion", 1); // {2}

        addCard(Zone.BATTLEFIELD, playerA, "Manakin", 1);

        activateManaAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Exile");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Myr Superion");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);

        try {
            execute();
        } catch (Throwable e) {
            if (!e.getMessage().contains("Player PlayerA must have 0 actions but found 1")) {
                Assert.fail("must not have throw error about bad targets, but got:\n" + e.getMessage());
            }
        }

        assertExileCount("Simian Spirit Guide", 1);
        assertPermanentCount(playerA, "Myr Superion", 0);
        assertHandCount(playerA, "Myr Superion", 1);
    }
}
