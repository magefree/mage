package org.mage.test.cards.single.mat;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author jimga150, Susucr
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

    @Test
    public void testDamagePrevention_NonCombat() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, "Deification");
        addCard(Zone.HAND, playerB, "Burn Down the House");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Chandra, Dressed to Kill"); // 3 / Planeswalker type: Chandra
        addCard(Zone.BATTLEFIELD, playerA, "Tibalt, the Fiend-Blooded"); // 2 / Planeswalker type: Tibalt
        addCard(Zone.BATTLEFIELD, playerA, "Memnite", 1); // need a creature for Deification to work

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Deification", true);
        setChoice(playerA, "Chandra");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Burn Down the House");
        setModeChoice(playerB, "1"); // {this} deals 5 damage to each creature and each planeswalker

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Memnite", 1);
        assertGraveyardCount(playerA, "Tibalt, the Fiend-Blooded", 1);
        assertGraveyardCount(playerA, "Chandra, Dressed to Kill", 0);
        assertCounterCount(playerA, "Chandra, Dressed to Kill", CounterType.LOYALTY, 1);
    }

    @Test
    public void testDamagePrevention_Combat() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, "Deification");
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerB, "Craw Wurm");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Chandra, Dressed to Kill"); // 3 / Planeswalker type: Chandra
        addCard(Zone.BATTLEFIELD, playerA, "Tibalt, the Fiend-Blooded"); // 2 / Planeswalker type: Tibalt
        addCard(Zone.BATTLEFIELD, playerA, "Memnite", 1); // need a creature for Deification to work

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Deification", true);
        setChoice(playerA, "Chandra");

        attack(2, playerB, "Craw Wurm", "Chandra, Dressed to Kill");
        attack(2, playerB, "Grizzly Bears", "Tibalt, the Fiend-Blooded");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Tibalt, the Fiend-Blooded", 1);
        assertGraveyardCount(playerA, "Chandra, Dressed to Kill", 0);
        assertCounterCount(playerA, "Chandra, Dressed to Kill", CounterType.LOYALTY, 1);
    }
}
