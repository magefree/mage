package org.mage.test.cards.single.scg;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author xenohedron
 */
public class LethalVaporsTest extends CardTestPlayerBase {

    // Lethal Vapors {2}{B}{B}
    // Enchantment
    // Whenever a creature enters, destroy it.
    // {0}: Destroy this enchantment. You skip your next turn. Any player may activate this ability.
    private static final String vapors = "Lethal Vapors";
    private static final String ability = "{0}: Destroy";

    // At the beginning of your upkeep, you may gain 1 life.
    private static final String mantra = "Ajani's Mantra";

    @Test
    public void testPlayerADestroys(){
        addCard(Zone.BATTLEFIELD, playerA, vapors);
        addCard(Zone.BATTLEFIELD, playerA, mantra);
        addCard(Zone.BATTLEFIELD, playerB, mantra);

        setChoice(playerA, true);
        setChoice(playerB, true);
        setChoice(playerA, true);
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, ability);
        setChoice(playerB, true);
        setChoice(playerB, true);

        setStrictChooseMode(true);
        setStopAt(5, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertLife(playerA, 22);
        assertLife(playerB, 23);
    }

    @Test
    public void testPlayerBDestroys(){
        addCard(Zone.BATTLEFIELD, playerA, vapors);
        addCard(Zone.BATTLEFIELD, playerA, mantra);
        addCard(Zone.BATTLEFIELD, playerB, mantra);

        setChoice(playerA, true);
        setChoice(playerB, true);
        setChoice(playerA, true);
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerB, ability);
        setChoice(playerA, true);

        setStrictChooseMode(true);
        setStopAt(4, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertLife(playerA, 23);
        assertLife(playerB, 21);
    }
}
