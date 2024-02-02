package org.mage.test.cards.single.mkm;

import mage.abilities.keyword.FlyingAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class KayaSpiritsJusticeTest extends CardTestPlayerBase {

    @Test
    public void test_TriggeredAbility() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1 + 6);
        addCard(Zone.BATTLEFIELD, playerA, "Kaya, Spirits' Justice");
        addCard(Zone.GRAVEYARD, playerA, "Llanowar Elves");
        addCard(Zone.GRAVEYARD, playerA, "Fyndhorn Elves");
        addCard(Zone.HAND, playerA, "Thraben Inspector");
        addCard(Zone.HAND, playerA, "Farewell");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Thraben Inspector");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        // Exile all creatures and graveyards
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Farewell");
        setModeChoice(playerA, "2");
        setModeChoice(playerA, "4");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, 1);

        showExile("exile", 1, PhaseStep.PRECOMBAT_MAIN, playerA);

        addTarget(playerA, "Clue Token");
        addTarget(playerA, "Fyndhorn Elves");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Clue Token", 0);
        assertPermanentCount(playerA, "Fyndhorn Elves", 1);
        assertAbility(playerA, "Fyndhorn Elves", FlyingAbility.getInstance(), true);
    }

    @Test
    public void test_Laelia() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Laelia, the Blade Reforged");
        addCard(Zone.GRAVEYARD, playerA, "Llanowar Elves");
        addCard(Zone.LIBRARY, playerA, "Llanowar Elves");
        addCard(Zone.HAND, playerA, "Llanowar Elves");
        addCard(Zone.HAND, playerA, "Cranial Extraction");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cranial Extraction", playerA);
        setChoice(playerA, "Llanowar Elves");
        setChoice(playerA, "Llanowar Elves");
        setChoice(playerA, "Llanowar Elves");
        setChoice(playerA, "Llanowar Elves");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, 1);

        checkStackSize("only one trigger", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerA, "Laelia, the Blade Reforged", CounterType.P1P1, 1);
    }
}
