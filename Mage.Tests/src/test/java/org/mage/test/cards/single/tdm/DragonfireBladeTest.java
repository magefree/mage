package org.mage.test.cards.single.tdm;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import static org.junit.jupiter.api.Assertions.*;

public class DragonfireBladeTest extends CardTestPlayerBase {

    private static final String blade = "Dragonfire Blade";
    private static final String equipText = "Equip {4}";
    public static final String ornithopter = "Ornithopter"; // colorless 0/1 creature
    private static final String turtle = "Aegis Turtle"; // U 0/5 creature
    private static final String leotau = "Grizzled Leotau"; // GW 1/5 creature
    private static final String mantis = "Mantis Rider"; // URW 3/3 creature
    private static final String glint = "Glint-Eye Nephilim"; // UBRG 2/2 creature

    @Test
    public void colorsTest() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, blade);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4 + 3 + 2 + 1);
        addCard(Zone.BATTLEFIELD, playerA, ornithopter);
        addCard(Zone.BATTLEFIELD, playerA, turtle);
        addCard(Zone.BATTLEFIELD, playerA, leotau);
        addCard(Zone.BATTLEFIELD, playerA, mantis);
        addCard(Zone.BATTLEFIELD, playerA, glint);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, equipText, ornithopter);
        waitStackResolved(1,PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, equipText, turtle);
        waitStackResolved(1,PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, equipText, leotau);
        waitStackResolved(1,PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, equipText, mantis);
        waitStackResolved(1,PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, equipText, glint);
        waitStackResolved(1,PhaseStep.PRECOMBAT_MAIN);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertAttachedTo(playerA, blade, glint, true);
        assertPowerToughness(playerA, glint, 4, 4);
    }
}