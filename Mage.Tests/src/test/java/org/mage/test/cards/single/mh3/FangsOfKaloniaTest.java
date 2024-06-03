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

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, fangs, grizzly);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
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
    public void test_VorinclexOverload() {
        addCard(Zone.HAND, playerA, fangs);
        addCard(Zone.BATTLEFIELD, playerA, grizzly);
        addCard(Zone.HAND, playerA, arcbound);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 7+6);

        addCard(Zone.BATTLEFIELD, playerB, "Vorinclex, Monstrous Raider");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, arcbound);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPT("Played arcbound", 1, PhaseStep.PRECOMBAT_MAIN, playerA, arcbound, 2, 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, fangs + " with overload");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, grizzly, 2, 2);
        assertPowerToughness(playerA, arcbound, 2, 2);
    }
    @Test
    public void test_HardenedScales() {
        addCard(Zone.HAND, playerA, fangs);
        addCard(Zone.BATTLEFIELD, playerA, grizzly);
        addCard(Zone.HAND, playerA, arcbound);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 7+6);

        addCard(Zone.BATTLEFIELD, playerA, "Hardened Scales");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, arcbound);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPT("Played arcbound", 1, PhaseStep.PRECOMBAT_MAIN, playerA, arcbound, 5, 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, fangs + " with overload");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, grizzly, 7, 7);
        assertPowerToughness(playerA, arcbound, 15, 15);

    }
}
