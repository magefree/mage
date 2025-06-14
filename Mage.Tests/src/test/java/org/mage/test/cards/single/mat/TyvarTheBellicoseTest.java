package org.mage.test.cards.single.mat;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author jimga150
 */
public class TyvarTheBellicoseTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.t.TyvarTheBellicose}
     * Tyvar the Bellicose
     * {2}{B}{G}
     * Legendary Creature — Elf Warrior
     * Whenever one or more Elves you control attack, they gain deathtouch until end of turn.
     * Each creature you control has “Whenever a mana ability of this creature resolves, put a number of +1/+1
     * counters on it equal to the amount of mana this creature produced. This ability triggers only once each turn.”
     * 5/4
     */
    private static final String tyvar = "Tyvar the Bellicose";

    /**
     * {@link mage.cards.e.EnduringVitality}
     * <p>
     * Creatures you control have “{T}: Add one mana of any color.”
     * 3/3
     */
    private static final String enduringvitality = "Enduring Vitality";

    /**
     * {@link mage.cards.l.LlanowarElves}
     * <p>
     * {T}: Add {G}.
     * 1/1
     */
    private static final String llanowarelves = "Llanowar Elves";

    /**
     * {@link mage.cards.a.AkkiDrillmaster}
     * <p>
     * {T}: Target creature gains haste until end of turn.
     * 2/2
     */
    private static final String akkidrillmaster = "Akki Drillmaster";

    private static final String memnite = "Memnite"; // vanilla 1/1

    @Test
    public void testPrintedAbility() {

        addCard(Zone.BATTLEFIELD, playerA, llanowarelves);
        addCard(Zone.BATTLEFIELD, playerA, tyvar);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, llanowarelves, 2, 2);
    }

    @Test
    public void testNonManaAbility() {

        addCard(Zone.BATTLEFIELD, playerA, akkidrillmaster);
        addCard(Zone.BATTLEFIELD, playerA, tyvar);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}");
        addTarget(playerA, tyvar);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, akkidrillmaster, 2, 2);
    }

    @Test
    public void testGainedAbility() {

        addCard(Zone.BATTLEFIELD, playerA, enduringvitality);
        addCard(Zone.BATTLEFIELD, playerA, memnite);
        addCard(Zone.BATTLEFIELD, playerA, tyvar);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}");
        setChoice(playerA, "Green");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}");
        setChoice(playerA, "Green");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}");
        setChoice(playerA, "Green");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, enduringvitality, 4, 4);
        assertPowerToughness(playerA, memnite, 2, 2);
        assertPowerToughness(playerA, tyvar, 6, 5);
    }
}
