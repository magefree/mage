package org.mage.test.cards.triggers.dies;

import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import mage.constants.PhaseStep;
import mage.constants.Zone;

/**
 *
 * @author alexander-novo
 *
 *  Make sure sacrificed creatures trigger their own death abilities
 */
public class SacrificeDeathTriggersTest extends CardTestPlayerBase {
    static final String kokusho = "Kokusho, the Evening Star";

    @Test
    public void testSacrificeTriggersDiesAbility1() {
        addCard(Zone.BATTLEFIELD, playerA, "Phyrexian Tower", 1); // {T}, Sacrifice a creature: Add {B}{B}.
        addCard(Zone.BATTLEFIELD, playerA, kokusho, 1); // When Kokusho, the Evening Star dies, each opponent loses 5 life. You gain life equal to the life lost this way.

        setStrictChooseMode(true);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}, Sacrifice a creature: Add {B}{B}");
        setChoice(playerA, kokusho);

        checkStackObject("trigger", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "When {this} dies,", 1);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, kokusho, 0);
        assertLife(playerA, 25);
        assertLife(playerB, 15);
    }

    @Test
    public void testSacrificeTriggersDiesAbility2() {
        addCard(Zone.BATTLEFIELD, playerA, "Vivien on the Hunt", 1); // +2: You may sacrifice a creature.
        addCard(Zone.BATTLEFIELD, playerA, kokusho, 1); // When Kokusho, the Evening Star dies, each opponent loses 5 life. You gain life equal to the life lost this way.

        setStrictChooseMode(true);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+2: You may sacrifice a creature.");
        // activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}, Sacrifice a creature: Add {B}{B}");
        setChoice(playerA, true);
        setChoice(playerA, kokusho);

        checkStackObject("trigger", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "When {this} dies,", 1);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, kokusho, 0);
        assertLife(playerA, 25);
        assertLife(playerB, 15);
    }
}
