package org.mage.test.cards.single.tmt;

import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class DonatelloMutantMechanicTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.d.DonatelloMutantMechanic}
     * <p>
     * {3}{U} Legendary Creature - Mutant Ninja Turtle 3/5<br>
     * {T}: Put three +1/+1 counters on target artifact you control. If it isn't a creature, it becomes a 0/0 Robot
     * creature in addition to its other types. Activate only as a sorcery.<br>
     * Whenever an artifact you control is put into a graveyard from the battlefield, if it had counters on it, put
     * those counters on up to one target artifact or creature you control.
     */
    private static final String DONATELLO = "Donatello, Mutant Mechanic";

    @Test
    public void test_ArtifactCreature_KeepsPowerToughness() {
        addCard(Zone.BATTLEFIELD, playerA, DONATELLO);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Ornithopter"); // artifact creature, Thopter 0/2, flying

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Put three", "Ornithopter");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        // keep
        assertType("Ornithopter", CardType.ARTIFACT, true);
        assertType("Ornithopter", CardType.CREATURE, true);
        assertSubtype("Ornithopter", SubType.THOPTER);
        assertNotSubtype("Ornithopter", SubType.ROBOT);
        // gain
        assertPowerToughness(playerA, "Ornithopter", 0 + 3, 2 + 3);
    }

    @Test
    public void test_NonCreatureArtifact_BecomesRobotCreature() {
        addCard(Zone.BATTLEFIELD, playerA, DONATELLO);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Sol Ring"); // artifact

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Put three", "Sol Ring");

        // no duration in the card text, so it stays a creature on the next turns
        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        // keep
        assertType("Sol Ring", CardType.ARTIFACT, true);
        // gain
        assertType("Sol Ring", CardType.CREATURE, true);
        assertSubtype("Sol Ring", SubType.ROBOT);
        assertPowerToughness(playerA, "Sol Ring", 0 + 3, 0 + 3);
    }
}
