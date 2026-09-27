package org.mage.test.cards.single.hob;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class WizardsStaffTest extends CardTestPlayerBase {

    private static final String staff = "Wizard's Staff";
    // 2/2 No Ability
    private static final String bear = "Grizzly Bears";
    // 1/3 Prowess Wizard
    private static final String prowess_wizard = "Sanguinary Mage";
    private static final String instant = "Obsessive Search";
    // Alliance — Whenever another creature you control enters, this creature gets +1/+1 until end of turn. 2/1
    private static final String otherTrigger = "Attended Socialite";


    @Test
    public void testNonWizard() {
        addCard(Zone.BATTLEFIELD, playerA, bear);
        addCard(Zone.BATTLEFIELD, playerA, staff);
        addCard(Zone.HAND, playerA, instant);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {3}", bear);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, instant, true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAttachedTo(playerA, staff, bear, true);
        // Two Prowess tiggers
        assertPowerToughness(playerA, bear, 2+2, 2+2);
    }

    @Test
    public void testWizard() {
        addCard(Zone.BATTLEFIELD, playerA, prowess_wizard);
        addCard(Zone.BATTLEFIELD, playerA, staff);
        addCard(Zone.HAND, playerA, instant,2);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);


        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, instant, true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        // Prowess trigger
        assertPowerToughness(playerA, prowess_wizard, 1+1, 3+1);


        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Equip Wizard {1}", prowess_wizard);
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, instant, true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAttachedTo(playerA, staff, prowess_wizard, true);
        // Four more Prowess tiggers (creature itself + staff + doubled)
        assertPowerToughness(playerA, prowess_wizard, 1+1+4, 3+1+4);
    }

    @Test
    public void testOtherTrigger() {
        addCard(Zone.BATTLEFIELD, playerA, otherTrigger);
        addCard(Zone.BATTLEFIELD, playerA, staff);
        addCard(Zone.HAND, playerA, bear, 2);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 7);



        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bear, true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        // Alliance Trigger
        assertPowerToughness(playerA, otherTrigger, 2+1, 1+1);


        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Equip {3}", otherTrigger);
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, bear, true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAttachedTo(playerA, staff, otherTrigger, true);
        // Two more Alliance triggers
        assertPowerToughness(playerA, otherTrigger, 2+1+2, 1+1+2);
    }

    @Test
    public void testTwoWizards() {
        addCard(Zone.BATTLEFIELD, playerA, prowess_wizard);
        addCard(Zone.BATTLEFIELD, playerA, prowess_wizard);
        addCard(Zone.BATTLEFIELD, playerA, staff);
        addCard(Zone.HAND, playerA, instant,1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip Wizard {1}", prowess_wizard);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, instant, true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAttachedTo(playerA, staff, prowess_wizard, true);
        // One Prowess trigger
        assertPowerToughness(playerA, prowess_wizard, 1+1, 3+1);
        // Four Prowess tiggers (creature itself + staff + doubled)
        assertPowerToughness(playerA, prowess_wizard, 1+4, 3+4);
    }
}
