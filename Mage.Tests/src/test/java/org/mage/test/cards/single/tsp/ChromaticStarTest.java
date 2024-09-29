package org.mage.test.cards.single.tsp;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class ChromaticStarTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.c.ChromaticStar Chromatic Star} {1}
     * Artifact
     * {1}, {T}, Sacrifice Chromatic Star: Add one mana of any color.
     * When Chromatic Star is put into a graveyard from the battlefield, draw a card.
     */
    private static final String star = "Chromatic Star";

    @Test
    public void test_Star_UseManaInPool() {
        setStrictChooseMode(true);
        disableManaAutoPayment(playerA);

        addCard(Zone.BATTLEFIELD, playerA, star);
        addCard(Zone.BATTLEFIELD, playerA, "Plains");

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {W}");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}, {T}");
        setChoice(playerA, "White"); // Use the White mana in pool
        setChoice(playerA, "Red"); // choice for produced mana

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertTappedCount("Plains", true, 1);
        assertGraveyardCount(playerA, 1);
        assertHandCount(playerA, 1);
    }

    @Ignore // not sure if that's possible to chain mana activation in tests?
    @Test
    public void test_Star_ChainMana() {
        setStrictChooseMode(true);
        disableManaAutoPayment(playerA);

        addCard(Zone.BATTLEFIELD, playerA, star);
        addCard(Zone.BATTLEFIELD, playerA, "Plains");

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}, {T}");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {W}");
        setChoice(playerA, "White"); // Use the White mana in pool
        setChoice(playerA, "Red"); // choice for produced mana

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertTappedCount("Plains", true, 1);
        assertGraveyardCount(playerA, 1);
        assertHandCount(playerA, 1);
    }

    @Test
    public void test_Star_ChainMana_Auto() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, star);
        addCard(Zone.BATTLEFIELD, playerA, "Plains");

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}, {T}");
        setChoice(playerA, "Red"); // choice for produced mana

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertTappedCount("Plains", true, 1);
        assertGraveyardCount(playerA, 1);
        assertHandCount(playerA, 1);
    }
}
