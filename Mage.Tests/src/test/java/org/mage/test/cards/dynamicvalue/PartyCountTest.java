package org.mage.test.cards.dynamicvalue;

import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class PartyCountTest extends CardTestPlayerBase {

    private static final String shpd = "Shepherd of Heroes";
    private static final String skrmshr = "Aven Skirmisher";
    private static final String dncstr = "Dromoka Dunecaster";
    private static final String ddgr = "Goldmeadow Dodger";

    @Test
    public void testSingleMember() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.HAND, playerA, shpd);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, shpd);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();
        assertAllCommandsUsed();

        assertLife(playerA, 22);
    }

    @Test
    public void testTwoMembers() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 6);
        addCard(Zone.HAND, playerA, skrmshr);
        addCard(Zone.HAND, playerA, shpd);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, skrmshr);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, shpd);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();
        assertAllCommandsUsed();

        assertLife(playerA, 24);
    }

    @Test
    public void testTwoMembers2() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 7);
        addCard(Zone.HAND, playerA, skrmshr, 2);
        addCard(Zone.HAND, playerA, shpd);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, skrmshr);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, skrmshr);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, shpd);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();
        assertAllCommandsUsed();

        assertLife(playerA, 24);
    }

    @Test
    public void testThreeMembers() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 7);
        addCard(Zone.HAND, playerA, skrmshr);
        addCard(Zone.HAND, playerA, dncstr);
        addCard(Zone.HAND, playerA, shpd);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, skrmshr);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, dncstr);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, shpd);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();
        assertAllCommandsUsed();

        assertLife(playerA, 26);
    }

    private void makeCreature(String name, SubType... subTypes) {
        addCustomCardWithAbility("dude", playerA, null, null, CardType.CREATURE, "{1}", Zone.BATTLEFIELD, subTypes);
    }

    @Test
    public void testOddCombos() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.HAND, playerA, shpd);
        makeCreature("crtA", SubType.ROGUE, SubType.WIZARD, SubType.WARRIOR);
        makeCreature("crtB", SubType.ROGUE, SubType.CLERIC);
        makeCreature("crtC", SubType.CLERIC, SubType.WIZARD);
        makeCreature("crtD", SubType.WARRIOR, SubType.WIZARD);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, shpd);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();
        assertAllCommandsUsed();

        assertLife(playerA, 28);
    }
}
