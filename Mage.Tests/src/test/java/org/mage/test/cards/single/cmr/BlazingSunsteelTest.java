package org.mage.test.cards.single.cmr;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class BlazingSunsteelTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.b.BlazingSunsteel Blazing Sunsteel} {1}{R}
     * Artifact — Equipment
     * Equipped creature gets +1/+0 for each opponent you have.
     * Whenever equipped creature is dealt damage, it deals that much damage to any target.
     * Equip {4}
     */
    private static final String sunsteel = "Blazing Sunsteel";

    @Test
    public void test_Trigger() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, sunsteel);
        addCard(Zone.BATTLEFIELD, playerA, "Memnite");
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", "Memnite");
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, "Lightning Bolt", "Memnite");
        addTarget(playerA, playerB); // Sunsteel trigger

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Memnite", 1);
        assertGraveyardCount(playerA, "Lightning Bolt", 1);
        assertLife(playerB, 20 - 3);
    }
}
