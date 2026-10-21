package org.mage.test.cards.cost.additional;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author DarioBarrabinoDev
 */
public class OrCostManaReductionTest extends CardTestPlayerBase {

    @Test
    public void test_LightningAxe_PayManaOption_IsReduced() {
        // Instant and sorcery spells you cast cost {X} less to cast, where X is Vadrik's power.
        addCard(Zone.BATTLEFIELD, playerA, "Vadrik, Astral Archmage"); // 1/2
        //
        // As an additional cost to cast this spell, discard a card or pay {5}.
        // Lightning Axe deals 5 damage to target creature.
        addCard(Zone.HAND, playerA, "Lightning Axe"); // {R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5); // {R} + {5} - {1}
        addCard(Zone.BATTLEFIELD, playerB, "Hill Giant");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Axe", "Hill Giant");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Hill Giant", 1);
        assertTappedCount("Mountain", true, 5);
    }

    @Test
    public void test_LouisoixsSacrifice_PayManaOption_IsReduced() {
        // Instant and sorcery spells you cast cost {X} less to cast, where X is Vadrik's power.
        addCard(Zone.BATTLEFIELD, playerA, "Vadrik, Astral Archmage"); // 1/2
        //
        // As an additional cost to cast this spell, sacrifice a legendary creature or pay {2}.
        // Counter target activated ability, triggered ability, or noncreature spell.
        addCard(Zone.HAND, playerA, "Louisoix's Sacrifice"); // {U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2); // {U} + {2} - {1}
        addCard(Zone.HAND, playerB, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", playerA);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Louisoix's Sacrifice", "Lightning Bolt", "Lightning Bolt");
        setChoice(playerA, false); // pay {2} instead of sacrificing Vadrik

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertGraveyardCount(playerB, "Lightning Bolt", 1);
        assertPermanentCount(playerA, "Vadrik, Astral Archmage", 1);
    }
}
