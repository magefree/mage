package org.mage.test.cards.single.mh3;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author notgreat
 */
public class FangsOfKaloniaTest extends CardTestPlayerBase {
    /**
     * {@link mage.cards.f.FangsOfKalonia Fangs of Kalonia} {1}{G}
     * Sorcery
     * Put a +1/+1 counter on target creature you control, then double the number of +1/+1 counters on each creature that had a +1/+1 counter put on it this way.
     * Overload {4}{G}{G}
     **/
    private static final String fangs = "Fangs of Kalonia";

    private static final String grizzly = "Grizzly Bears";
    private static final String arcbound = "Arcbound Lancer"; //0/0 with Modular 4
    @Test
    public void test_Basic() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, fangs, 2);
        addCard(Zone.BATTLEFIELD, playerA, grizzly);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, fangs, grizzly, true);
        checkPT("First Fangs", 1, PhaseStep.PRECOMBAT_MAIN, playerA, grizzly, 4, 4);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, fangs, grizzly);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, grizzly, 8, 8);
    }
    @Test
    public void test_Overload() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, fangs);
        addCard(Zone.BATTLEFIELD, playerA, grizzly);
        addCard(Zone.BATTLEFIELD, playerA, arcbound);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 6);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, fangs + " with overload");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, grizzly, 4, 4);
        assertPowerToughness(playerA, arcbound, 10, 10);
    }
    @Test
    public void test_Vorinclex() {
        addCard(Zone.HAND, playerA, fangs, 2);
        addCard(Zone.BATTLEFIELD, playerA, grizzly);
        addCard(Zone.HAND, playerA, arcbound);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 7+2+2);

        addCard(Zone.BATTLEFIELD, playerB, "Vorinclex, Monstrous Raider");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, arcbound, true);
        checkPT("Played arcbound", 1, PhaseStep.PRECOMBAT_MAIN, playerA, arcbound, 2, 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, fangs, grizzly, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, fangs, arcbound);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, grizzly, 2, 2);
        assertPowerToughness(playerA, arcbound, 2, 2);
    }
    @Test
    public void test_HardenedScales() {
        addCard(Zone.HAND, playerA, fangs, 2);
        addCard(Zone.BATTLEFIELD, playerA, grizzly);
        addCard(Zone.HAND, playerA, arcbound);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 7+2+2);

        addCard(Zone.BATTLEFIELD, playerA, "Hardened Scales");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, arcbound, true);
        checkPT("Played arcbound", 1, PhaseStep.PRECOMBAT_MAIN, playerA, arcbound, 5, 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, fangs, grizzly, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, fangs, arcbound);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, grizzly, 7, 7);
        assertPowerToughness(playerA, arcbound, 15, 15);
    }
    @Test
    public void test_DoubleReplacement1() {
        addCard(Zone.HAND, playerA, fangs, 2);
        addCard(Zone.HAND, playerA, arcbound);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 7+2);

        addCard(Zone.BATTLEFIELD, playerA, "Hardened Scales");
        addCard(Zone.BATTLEFIELD, playerB, "Vorinclex, Monstrous Raider");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, arcbound);
        setChoice(playerA, "Hardened Scales"); //(4+1)/2 = 2
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPT("Played arcbound", 1, PhaseStep.PRECOMBAT_MAIN, playerA, arcbound, 2, 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, fangs, arcbound);
        setChoice(playerA, "Hardened Scales"); //adds (1+1)/2 = 1, success. Now 3 counters, double adds (3+1)/2 = 2

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, arcbound, 5, 5);
    }
    @Test
    public void test_DoubleReplacement2() {
        addCard(Zone.HAND, playerA, fangs, 2);
        addCard(Zone.HAND, playerA, arcbound);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 7+2);

        addCard(Zone.BATTLEFIELD, playerA, "Hardened Scales");
        addCard(Zone.BATTLEFIELD, playerB, "Vorinclex, Monstrous Raider");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, arcbound);
        setChoice(playerA, "Vorinclex, Monstrous Raider"); //(4/2)+1 = 3
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPT("Played arcbound", 1, PhaseStep.PRECOMBAT_MAIN, playerA, arcbound, 3, 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, fangs, arcbound);
        setChoice(playerA, "Vorinclex, Monstrous Raider"); // adds (1/2), then no placement action to add to

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, arcbound, 3, 3);
    }

    @Test
    public void test_VorinclexOverload() {
        addCard(Zone.HAND, playerA, fangs);
        addCard(Zone.BATTLEFIELD, playerA, grizzly);
        addCard(Zone.HAND, playerA, arcbound);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 7+6);

        addCard(Zone.BATTLEFIELD, playerB, "Vorinclex, Monstrous Raider");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, arcbound, true);
        checkPT("Played arcbound", 1, PhaseStep.PRECOMBAT_MAIN, playerA, arcbound, 2, 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, fangs + " with overload");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, grizzly, 2, 2);
        assertPowerToughness(playerA, arcbound, 2, 2);
    }
    @Test
    public void test_HardenedScalesOverload() {
        addCard(Zone.HAND, playerA, fangs);
        addCard(Zone.BATTLEFIELD, playerA, grizzly);
        addCard(Zone.HAND, playerA, arcbound);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 7+6);

        addCard(Zone.BATTLEFIELD, playerA, "Hardened Scales");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, arcbound, true);
        checkPT("Played arcbound", 1, PhaseStep.PRECOMBAT_MAIN, playerA, arcbound, 5, 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, fangs + " with overload");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, grizzly, 7, 7);
        assertPowerToughness(playerA, arcbound, 15, 15);
    }
}
