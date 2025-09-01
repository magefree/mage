package org.mage.test.cards.single.sir;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class BrainInAJarTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.b.BrainInAJar Brain in a Jar} {2}
     * Artifact
     * {1}, {T}: Put a charge counter on this artifact, then you may cast an instant or sorcery spell with mana value equal to the number of charge counters on this artifact from your hand without paying its mana cost.
     * {3}, {T}, Remove X charge counters from this artifact: Scry X.
     */
    private static final String brain = "Brain in a Jar";

    @Test
    public void test_scry2() {
        skipInitShuffling();
        addCard(Zone.LIBRARY, playerA, "Ancient Amphitheater");
        addCard(Zone.LIBRARY, playerA, "Baleful Strix");

        addCard(Zone.BATTLEFIELD, playerA, brain, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Dragon Appeasement"); // enchantement for skip draw
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}, {T}");

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}, {T}");

        activateAbility(5, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}, {T}");

        activateAbility(7, PhaseStep.PRECOMBAT_MAIN, playerA, "{3}, {T}");
        setChoice(playerA, "X=2"); // how many counters to remove
        addTarget(playerA, "Baleful Strix^Ancient Amphitheater"); // put on bottom with Scry 2
        setChoice(playerA, "Ancient Amphitheater"); // order for bottom

        setStrictChooseMode(true);
        setStopAt(7, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertCounterCount(playerA, brain, CounterType.CHARGE, 1);
    }
}
