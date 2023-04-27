package org.mage.test.cards.continuous;

import mage.abilities.keyword.VigilanceAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.m.MasterThief Master Thief}
 * {2}{U}{U}
 * Creature — Human Rogue
 * When Master Thief enters the battlefield, gain control of target artifact for as long as you control Master Thief.
 *
 * @author JayDi85
 */
public class MasterThiefTest extends CardTestPlayerBase {

    @Test
    public void testMasterThief_GetControlOnEnterBattlefield() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.HAND, playerA, "Master Thief", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Accorder's Shield", 1);

        // Cast Master Theif, auto get control of the shield since it's the only artifact in play
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Master Thief");

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Island", 5);
        assertPermanentCount(playerA, "Master Thief", 1);
        assertPermanentCount(playerA, "Accorder's Shield", 1);

        assertPermanentCount(playerB, 0);
    }

    @Test
    public void testMasterThief_LostControlOnSacrifice() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 10);
        // When Master Thief enters the battlefield, gain control of target artifact for as long as you control Master Thief.
        addCard(Zone.HAND, playerA, "Master Thief", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Accorder's Shield", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Bearer of the Heavens", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Ashnod's Altar", 1);

        // cast and get control of shield
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Master Thief");
        addTarget(playerA, "Accorder's Shield");

        // sacrifice Master Thief -- must lose control
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice a creature");
        setChoice(playerA, "Master Thief");

        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Island", 10);
        assertPermanentCount(playerA, "Master Thief", 0);
        assertPermanentCount(playerA, "Accorder's Shield", 0);
        assertPermanentCount(playerA, "Ashnod's Altar", 1);
        assertPermanentCount(playerA, "Bearer of the Heavens", 1);
        assertPowerToughness(playerA, "Bearer of the Heavens", 10, 10);

        assertPermanentCount(playerB, "Accorder's Shield", 1);
        assertPermanentCount(playerB, 1);
    }

    @Test
    public void testMasterThief_LostControlOnSacrificeButArtifactAttached() {
        addCard(Zone.HAND, playerA, "Master Thief", 3);

        addCard(Zone.BATTLEFIELD, playerA, "Island", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Bearer of the Heavens", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Ashnod's Altar", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Accorder's Shield", 1);

        // cast and get control of shield
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Master Thief");
        addTarget(playerA, "Accorder's Shield");

        checkPermanentCount("must control shield", 1, PhaseStep.BEGIN_COMBAT, playerA, "Accorder's Shield", 1);

        // attach and boost
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Equip {3}");
        addTarget(playerA, "Bearer of the Heavens");

        checkAbility("bear must have boost", 1, PhaseStep.END_TURN, playerA, "Bearer of the Heavens", VigilanceAbility.class, true);

        // sacrifice Master Thief, lose control of the shield but it should still be attached to Bearer of the Heavens
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice a creature");
        setChoice(playerA, "Master Thief");

        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Island", 10);
        assertPermanentCount(playerA, "Master Thief", 0);
        assertPermanentCount(playerA, "Accorder's Shield", 0);
        assertPermanentCount(playerA, "Ashnod's Altar", 1);
        assertPermanentCount(playerA, "Bearer of the Heavens", 1);
        assertPowerToughness(playerA, "Bearer of the Heavens", 10, 10 + 3);

        assertPermanentCount(playerB, "Accorder's Shield", 1);
        assertPermanentCount(playerB, 1);
    }
}
