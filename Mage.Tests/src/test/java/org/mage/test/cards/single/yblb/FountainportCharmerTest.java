package org.mage.test.cards.single.yblb;

import mage.constants.PhaseStep;
import mage.constants.Zone;

import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class FountainportCharmerTest extends CardTestPlayerBase {

    @Test
    public void testDiscountPersistsAfterBounce() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.HAND, playerA, "Fountainport Charmer");
        addCard(Zone.HAND, playerA, "Grizzly Bears");
        addCard(Zone.HAND, playerA, "Unsummon");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fountainport Charmer");
        setChoice(playerA, false);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Unsummon", "Grizzly Bears");
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears");
        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertTappedCount("Forest", true, 1);
    }

    @Test
    public void testOffspringStacksDiscount() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 6);
        addCard(Zone.HAND, playerA, "Fountainport Charmer");
        addCard(Zone.HAND, playerA, "Centaur Courser");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fountainport Charmer");
        setChoice(playerA, true);
        setChoice(playerA, "When {this} enters, if its offspring cost was paid");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Centaur Courser");
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Fountainport Charmer", 2);
        assertPermanentCount(playerA, "Centaur Courser", 1);
        assertTappedCount("Forest", true, 5);
    }

}
