package org.mage.test.cards.single.mat;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author jimga150
 */
public class DeificationTests extends CardTestPlayerBase {

    @Test
    public void testDamagePrevention() {

        addCard(Zone.HAND, playerA, "Deification");
        addCard(Zone.HAND, playerA, "Lightning Bolt", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Chandra, Dressed to Kill"); // 3 / Planeswalker type: Chandra
        addCard(Zone.BATTLEFIELD, playerA, "Tibalt, the Fiend-Blooded"); // 2 / Planeswalker type: Tibalt
        addCard(Zone.BATTLEFIELD, playerA, "Memnite", 1); // need a creature for Deification to work

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Deification", true);
        setChoice(playerA, "Chandra");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", true);
        addTarget(playerA, "Tibalt, the Fiend-Blooded");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", true);
        addTarget(playerA, "Chandra, Dressed to Kill");

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertGraveyardCount(playerA, "Tibalt, the Fiend-Blooded", 1);
        assertGraveyardCount(playerA, "Chandra, Dressed to Kill", 0);
        assertCounterCount(playerA, "Chandra, Dressed to Kill", CounterType.LOYALTY, 1);

    }

    @Test
    public void testDamagePreventionNoCreatures() {

        addCard(Zone.HAND, playerA, "Deification");
        addCard(Zone.HAND, playerA, "Lightning Bolt", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Chandra, Dressed to Kill"); // 3 / Planeswalker type: Chandra
        addCard(Zone.BATTLEFIELD, playerA, "Tibalt, the Fiend-Blooded"); // 2 / Planeswalker type: Tibalt

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Deification", true);
        setChoice(playerA, "Chandra");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", true);
        addTarget(playerA, "Tibalt, the Fiend-Blooded");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", true);
        addTarget(playerA, "Chandra, Dressed to Kill");

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertGraveyardCount(playerA, "Tibalt, the Fiend-Blooded", 1);
        assertGraveyardCount(playerA, "Chandra, Dressed to Kill", 1);

    }

    @Test
    public void testDamagePreventionNonLethal() {

        addCard(Zone.HAND, playerA, "Deification");
        addCard(Zone.HAND, playerA, "Lightning Bolt", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Chandra, Awakened Inferno"); // 6 / Planeswalker type: Chandra
        addCard(Zone.BATTLEFIELD, playerA, "Memnite", 1); // need a creature for Deification to work

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Deification", true);
        setChoice(playerA, "Chandra");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", true);
        addTarget(playerA, "Chandra, Awakened Inferno");

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertGraveyardCount(playerA, "Chandra, Awakened Inferno", 0);
        // not lethal damage, so should have taken all 3 damage
        assertCounterCount(playerA, "Chandra, Awakened Inferno", CounterType.LOYALTY, 3);

    }

}
