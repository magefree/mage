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

    /**
     * A token copy's control ends the moment the token leaves, not once the stack is empty.
     */
    @Test
    public void testMasterThief_TokenCopyLeaves() {
        addCard(Zone.BATTLEFIELD, playerA, "Volcanic Island", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Master Thief");
        addCard(Zone.BATTLEFIELD, playerB, "Accorder's Shield");
        addCard(Zone.HAND, playerA, "Cackling Counterpart"); // {1}{U}{U} instant: token copy of target creature you control
        addCard(Zone.HAND, playerA, "Shock");
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cackling Counterpart", "Master Thief");
        addTarget(playerA, "Accorder's Shield"); // the token's enters trigger
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        checkPermanentCount("the token takes it", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Accorder's Shield", 1);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shock", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Master Thief[only copy]", "Shock");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA, true);
        checkPermanentCount("token gone, Shock still on the stack", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Accorder's Shield", 0);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerB, "Accorder's Shield", 1);
        assertLife(playerB, 20 - 2);
    }
}
