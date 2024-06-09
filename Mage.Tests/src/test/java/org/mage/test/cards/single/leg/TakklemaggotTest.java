package org.mage.test.cards.single.leg;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class TakklemaggotTest extends CardTestPlayerBase {

    private static final String takklemaggot = "Takklemaggot"; // 2BB Aura
    /* Enchant creature
     At the beginning of the upkeep of enchanted creature’s controller, put a -0/-1 counter on that creature.
     When enchanted creature dies, that creature’s controller chooses a creature that Takklemaggot could enchant.
     If the player does, return Takklemaggot to the battlefield under your control attached to that creature.
     If they don’t, return Takklemaggot to the battlefield under your control as a non-Aura enchantment.
     It loses “enchant creature” and gains “At the beginning of that player’s upkeep, Takklemaggot deals 1 damage to that player.”
     */

    @Test
    public void testTakklemaggot() {
        addCard(Zone.HAND, playerA, takklemaggot, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.BATTLEFIELD, playerB, "Harvest Hand", 1); // 2/2 returns as equipment "Scrounged Scythe"
        addCard(Zone.BATTLEFIELD, playerB, "White Knight", 1); // 2/2 protection from black
        addCard(Zone.BATTLEFIELD, playerB, "Mist Leopard", 1); // 3/2 shroud

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, takklemaggot, "Harvest Hand");

        checkPT("t1", 1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Harvest Hand", 2,2);
        checkPT("t2", 2, PhaseStep.PRECOMBAT_MAIN, playerB, "Harvest Hand", 2, 1);
        checkPT("t3", 3, PhaseStep.PRECOMBAT_MAIN, playerB, "Harvest Hand", 2, 1);
        setChoice(playerB, "Mist Leopard");
        checkPermanentCount("equipment", 4, PhaseStep.POSTCOMBAT_MAIN, playerB, "Scrounged Scythe", 1);
        checkPermanentCount("takklemaggot", 4, PhaseStep.POSTCOMBAT_MAIN, playerA, takklemaggot, 1);
        checkPT("t5", 5, PhaseStep.PRECOMBAT_MAIN, playerB, "Mist Leopard", 3, 2);
        checkPT("t6", 6, PhaseStep.PRECOMBAT_MAIN, playerB, "Mist Leopard", 3, 1);
        checkPT("t7", 7, PhaseStep.PRECOMBAT_MAIN, playerB, "Mist Leopard", 3, 1);
        checkGraveyardCount("leopard", 8, PhaseStep.POSTCOMBAT_MAIN, playerB, "Mist Leopard", 1);
        checkLife("t9", 9, PhaseStep.PRECOMBAT_MAIN, playerB, 20);
        checkLife("t10", 10, PhaseStep.PRECOMBAT_MAIN, playerB, 19);
        checkLife("t11", 11, PhaseStep.PRECOMBAT_MAIN, playerB, 19);

        setStopAt(12, PhaseStep.POSTCOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerB, 18);
    }
}
