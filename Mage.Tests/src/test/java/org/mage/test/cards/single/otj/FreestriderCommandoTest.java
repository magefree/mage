package org.mage.test.cards.single.otj;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class FreestriderCommandoTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.f.FreestriderCommando Freestrider Commando} {2}{G}
     * Creature — Centaur Mercenary
     * Freestrider Commando enters the battlefield with two +1/+1 counters on it if it wasn’t cast or no mana was spent to cast it.
     * Plot {3}{G} (You may pay {3}{G} and exile this card from your hand. Cast it as a sorcery on a later turn without paying its mana cost. Plot only as a sorcery.)
     * 3/3
     */
    private static final String commando = "Freestrider Commando";

    @Test
    public void test_RegularCast() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.HAND, playerA, commando);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, commando);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // No +1/+1 as cast with mana
        assertPowerToughness(playerA, commando, 3, 3);
    }

    @Test
    public void test_PlotCast() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.HAND, playerA, commando);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Plot");
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, commando + " using Plot");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        // 2 +1/+1 as cast free with plot
        assertPowerToughness(playerA, commando, 3 + 2, 3 + 2);
    }

    @Test
    public void test_OmniscienceCast() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Omniscience");
        addCard(Zone.HAND, playerA, commando);

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, commando);
        setChoice(playerA, true); // Omniscience asks for confirmation to cast to avoid missclick?

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        // 2 +1/+1 as cast free with Omniscience
        assertPowerToughness(playerA, commando, 3 + 2, 3 + 2);
    }

    @Test
    public void test_PlotCast_WithTax() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.HAND, playerA, commando);
        addCard(Zone.BATTLEFIELD, playerA, "Sphere of Resistance"); // Spells cost {1} more to cast

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Plot");
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, commando + " using Plot");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        // no +1/+1 as cast free with plot, but tax make mana being paid
        assertPowerToughness(playerA, commando, 3, 3);
    }

    @Test
    public void test_Blink() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Savannah", 4);
        addCard(Zone.HAND, playerA, commando);
        addCard(Zone.HAND, playerA, "Ephemerate");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, commando);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ephemerate", "Freestrider Commando");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // 2 +1/+1 as cast free with plot, as re-enter without being cast
        assertPowerToughness(playerA, commando, 3 + 2, 3 + 2);
    }

    @Test
    public void test_DoubleMajor() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Tropical Island", 6);
        addCard(Zone.HAND, playerA, commando);
        addCard(Zone.HAND, playerA, "Double Major");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, commando);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Double Major", "Freestrider Commando");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, 2); // resolve Double Major + the copy
        checkPT("double major copy enters as a 5/5", 1, PhaseStep.PRECOMBAT_MAIN, playerA, commando, 3 + 2, 3 + 2);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, commando, 2); // real + copy
    }
}
