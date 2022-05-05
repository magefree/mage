package org.mage.test.cards.single.dtk;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2
 */
public class LivingLoreTest extends CardTestPlayerBase {

    /**
     * That the +1/+1 counters are added to Living Lore before state based
     * actions take place
     */
    @Test
    public void testCountersAdded() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.HAND, playerA, "Living Lore"); //{3}{U}
        addCard(Zone.GRAVEYARD, playerA, "Natural Connection", 1); // {2}{G}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Living Lore");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Living Lore", 1);
        assertPowerToughness(playerA, "Living Lore", 3, 3);
    }

    @Test
    public void testCastSpell() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.HAND, playerA, "Living Lore"); //{3}{U}
        addCard(Zone.GRAVEYARD, playerA, "Lightning Bolt", 1); // {R}

        addTarget(playerA, "Lightning Bolt");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Living Lore");

        attack(3, playerA, "Living Lore", playerB);
        setChoice(playerA, true); // sacrifice
        setChoice(playerA, true); // cast spell
        addTarget(playerA, playerB);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_COMBAT);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Living Lore", 0);
        assertGraveyardCount(playerA, "Lightning Bolt", 1);
        assertLife(playerB, 20 - 1 - 3);
    }
}
