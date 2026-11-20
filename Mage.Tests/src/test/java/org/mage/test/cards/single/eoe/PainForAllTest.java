package org.mage.test.cards.single.eoe;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author adams-shaun
 */
public class PainForAllTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.p.PainForAll Pain for All} {2}{R}
     * Enchantment - Aura
     * Enchant creature you control
     * When this Aura enters, enchanted creature deals damage equal to its power to any other target.
     * Whenever enchanted creature is dealt damage, it deals that much damage to each opponent.
     */
    private static final String pain = "Pain for All";

    private void castPainThenShock(String creature) {
        addCard(Zone.BATTLEFIELD, playerA, creature);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.HAND, playerA, pain);
        addCard(Zone.HAND, playerA, "Shock");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, pain, creature);
        addTarget(playerA, playerA); // enters trigger
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shock", creature);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();
    }

    @Test
    public void test_CreatureSurvivesTheDamage() {
        castPainThenShock("Hill Giant");

        assertPermanentCount(playerA, "Hill Giant", 1);
        assertLife(playerB, 20 - 2);
    }

    @Test
    public void test_CreatureDiesFromTheDamage() {
        castPainThenShock("Grizzly Bears");

        assertGraveyardCount(playerA, "Grizzly Bears", 1);
        assertGraveyardCount(playerA, pain, 1);
        assertLife(playerB, 20 - 2);
    }
}
