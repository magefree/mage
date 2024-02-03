package org.mage.test.cards.single.mkm;

import mage.abilities.keyword.FlyingAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.k.KayaSpiritsJustice}
 * @author DominionSpy
 */
public class KayaSpiritsJusticeTest extends CardTestPlayerBase {

    // Test first ability of Kaya
    @Test
    public void test_TriggeredAbility() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1 + 2 + 6);
        addCard(Zone.BATTLEFIELD, playerA, "Kaya, Spirits' Justice");
        addCard(Zone.BATTLEFIELD, playerA, "Llanowar Elves");
        addCard(Zone.GRAVEYARD, playerA, "Fyndhorn Elves");
        addCard(Zone.HAND, playerA, "Thraben Inspector");
        addCard(Zone.HAND, playerA, "Astrid Peth");
        addCard(Zone.HAND, playerA, "Farewell");

        // Creates a Clue token
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Thraben Inspector");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        // Creates a Food token
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Astrid Peth");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        // Exile all creatures and graveyards
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Farewell");
        setModeChoice(playerA, "2");
        setModeChoice(playerA, "4");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, 1);

        // Kaya's first ability triggers twice, so choose which is put on the stack:
        // Whenever one or more creatures you control and/or creature cards in your graveyard are put into exile,
        // you may choose a creature card from among them. Until end of turn, target token you control becomes a copy of it,
        // except it has flying.
        setChoice(playerA, "Whenever", 1);
        // Trigger targets
        addTarget(playerA, "Clue Token");
        addTarget(playerA, "Food Token");
        // Copy choices
        addTarget(playerA, "Fyndhorn Elves");
        addTarget(playerA, "Llanowar Elves");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Clue Token", 0);
        assertPermanentCount(playerA, "Fyndhorn Elves", 1);
        assertAbility(playerA, "Fyndhorn Elves", FlyingAbility.getInstance(), true);
        assertPermanentCount(playerA, "Food Token", 0);
        assertPermanentCount(playerA, "Llanowar Elves", 1);
        assertAbility(playerA, "Llanowar Elves", FlyingAbility.getInstance(), true);
    }
}
