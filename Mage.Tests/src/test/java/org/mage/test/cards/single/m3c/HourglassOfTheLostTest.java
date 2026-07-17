package org.mage.test.cards.single.m3c;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class HourglassOfTheLostTest extends CardTestPlayerBase {

    @Test
    public void testReturnZero() {
        addCard(Zone.BATTLEFIELD, playerA, "Hourglass of the Lost");
        addCard(Zone.GRAVEYARD, playerA, "Mox Amber");
        addCard(Zone.GRAVEYARD, playerA, "Memnite");
        addCard(Zone.GRAVEYARD, playerA, "Sol Ring", 2);
        addCard(Zone.GRAVEYARD, playerA, "Serra Ascendant");
        addCard(Zone.GRAVEYARD, playerA, "Wastes");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}, Remove");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Mox Amber", 1);
        assertPermanentCount(playerA, "Memnite", 1);
        assertPermanentCount(playerA, "Sol Ring", 0);
        assertPermanentCount(playerA, "Serra Ascendant", 0);
        assertPermanentCount(playerA, "Wastes", 0);
        assertExileCount("Hourglass of the Lost", 1);
    }

    @Test
    public void testReturnOne() {
        addCard(Zone.BATTLEFIELD, playerA, "Hourglass of the Lost");
        addCard(Zone.GRAVEYARD, playerA, "Mox Amber");
        addCard(Zone.GRAVEYARD, playerA, "Memnite");
        addCard(Zone.GRAVEYARD, playerA, "Sol Ring", 2);
        addCard(Zone.GRAVEYARD, playerA, "Serra Ascendant");
        addCard(Zone.GRAVEYARD, playerA, "Wastes");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {W}");

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}, Remove");
        setChoice(playerA, "X=1");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Mox Amber", 0);
        assertPermanentCount(playerA, "Memnite", 0);
        assertPermanentCount(playerA, "Sol Ring", 2);
        assertPermanentCount(playerA, "Serra Ascendant", 1);
        assertPermanentCount(playerA, "Wastes", 0);
        assertExileCount("Hourglass of the Lost", 1);
    }

    @Test
    public void testReturnLesser() {
        addCard(Zone.BATTLEFIELD, playerA, "Hourglass of the Lost");
        addCard(Zone.GRAVEYARD, playerA, "Mox Amber");
        addCard(Zone.GRAVEYARD, playerA, "Memnite");
        addCard(Zone.GRAVEYARD, playerA, "Sol Ring", 2);
        addCard(Zone.GRAVEYARD, playerA, "Serra Ascendant");
        addCard(Zone.GRAVEYARD, playerA, "Wastes");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {W}");

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}, Remove");
        setChoice(playerA, "X=0");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Mox Amber", 1);
        assertPermanentCount(playerA, "Memnite", 1);
        assertPermanentCount(playerA, "Sol Ring", 0);
        assertPermanentCount(playerA, "Serra Ascendant", 0);
        assertPermanentCount(playerA, "Wastes", 0);
        assertExileCount("Hourglass of the Lost", 1);
    }

}
