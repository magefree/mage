package org.mage.test.cards.continuous;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class BecomesColorEffectTest extends CardTestPlayerBase {

    /*
    Ancient Kavu
    {3}{R}
    Creature — Kavu
    {2}: This creature becomes colorless until end of turn.
    Those with the ability to change their nature survived Phyrexia’s biological attacks. Everything else died.
    3/3
     */
    String kavu = "Ancient Kavu";
    /*
    Alchor's Tomb
    {4}
    Artifact
    {2}, {T}: Target permanent you control becomes the color of your choice. (This effect lasts indefinitely.)
     */
    String alchorsTomb = "Alchor's Tomb";
    /*

     */

    @Test
    public void testBecomesColorSource() {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, kavu);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        checkColor("Ancient Kavu is red", 1, PhaseStep.PRECOMBAT_MAIN, playerA, kavu, "R", true);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}: {this}");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkColor("Ancient Kavu is colorless", 1, PhaseStep.PRECOMBAT_MAIN, playerA, kavu, "C", true);
        checkColor("Ancient Kavu is red again", 2, PhaseStep.PRECOMBAT_MAIN, playerA, kavu, "R", true);

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();
    }

    @Test
    public void testBecomesColorTarget() {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, kavu);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, alchorsTomb);

        checkColor("Ancient Kavu is red", 1, PhaseStep.PRECOMBAT_MAIN, playerA, kavu, "R", true);
        // make Ancient Kavu green
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}, {T}: Target permanent", kavu);
        setChoice(playerA, "Green");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkColor("Ancient Kavu is green", 1, PhaseStep.PRECOMBAT_MAIN, playerA, kavu, "G", true);
        checkColor("Ancient Kavu is still green the following turn", 2, PhaseStep.PRECOMBAT_MAIN, playerA, kavu, "G", true);
        // activate Ancient Kavu's ability to override green color until end of turn
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}: {this}");
        checkColor("Ancient Kavu is colorless", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, kavu, "C", true);
        // next turn it should be green again
        checkColor("Ancient Kavu is green again", 4, PhaseStep.PRECOMBAT_MAIN, playerA, kavu, "G", true);

        setStopAt(4, PhaseStep.BEGIN_COMBAT);
        execute();
    }
}
