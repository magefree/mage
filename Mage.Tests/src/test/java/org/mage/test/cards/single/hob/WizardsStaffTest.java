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
    // When this creature dies, it deals damage equal to its power to each opponent.
    private static final String deathTrigger = "Heartfire Hero";
    private static final String sacOutlef = "Altar of Dementia";
    // Let the World Burn — Destroy all artifacts and creatures
    private static String kill_both = "What Must Be Done";



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

    @Test
    public void testDeathTrigger() {
        addCard(Zone.BATTLEFIELD, playerA, deathTrigger);
        addCard(Zone.BATTLEFIELD, playerA, staff);
        addCard(Zone.BATTLEFIELD, playerA, sacOutlef);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {3}", deathTrigger);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice a creature:", playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // 1 / 1 -> after equip 2 / 2 from trigger -> deal 2 damage twice on death
        assertLife(playerB, 16);
    }

    @Test
    public void testSimultateousDeathTrigger() {
        /*
        If a creature dying at the same time that another permanent you control leaves the battlefield causes a triggered ability of that permanent to trigger, that ability triggers an additional time.
        (2023-02-04)

        If a creature dying at the same time as Drivnod (including Drivnod itself dying) causes a triggered ability of a permanent you control to trigger, that ability triggers an additional time.
        (2023-02-04)

        Objects dying at the same time see each other and each others triggers (Oracle of Drivnod)
         */

        addCard(Zone.BATTLEFIELD, playerA, deathTrigger);
        addCard(Zone.BATTLEFIELD, playerA, staff);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 8);
        addCard(Zone.HAND, playerA, kill_both,1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {3}", deathTrigger);
        // 1 / 1 -> after equip 2 / 2
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, kill_both, true);
        // Let the World Burn — Destroy all artifacts and creatures
        setModeChoice(playerA, "1");
        // 2 / 2 -> two prowess triggers -> 4 / 4
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // 4 / 4 -> deal 4 damage twice on death
        assertLife(playerB, 12);
    }
}
