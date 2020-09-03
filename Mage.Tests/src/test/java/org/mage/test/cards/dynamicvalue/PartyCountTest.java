package org.mage.test.cards.dynamicvalue;

import mage.constants.PhaseStep;
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
    private static final String mstfrm = "Mistform Sliver";
    private static final String glrdr = "Galerider Sliver";
    private static final String mtllc = "Metallic Sliver";
    private static final String pltd = "Plated Sliver";
    private static final String cleric = "Cleric";
    private static final String rogue = "Rogue";
    private static final String warrior = "Warrior";
    private static final String wizard = "Wizard";

    private void setType(String creature, String type) {
        setChoice(playerA, type);
        activateAbility(2, PhaseStep.POSTCOMBAT_MAIN, playerA, creature);
        waitStackResolved(2, PhaseStep.POSTCOMBAT_MAIN);
    }

    @Test
    public void testSingleMember() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.HAND, playerA, shpd);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, shpd);

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 22);
    }

    @Test
    public void testTwoMembers() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 6);
        addCard(Zone.HAND, playerA, skrmshr);
        addCard(Zone.HAND, playerA, shpd);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, skrmshr);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, shpd);

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 24);
    }

    @Test
    public void testTwoMembers2() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 7);
        addCard(Zone.HAND, playerA, skrmshr, 2);
        addCard(Zone.HAND, playerA, shpd);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, skrmshr);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, skrmshr);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, shpd);

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 24);
    }

    @Test
    public void testThreeMembers() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 7);
        addCard(Zone.HAND, playerA, skrmshr);
        addCard(Zone.HAND, playerA, dncstr);
        addCard(Zone.HAND, playerA, shpd);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, skrmshr);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, dncstr);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, shpd);

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 26);
    }

    @Test
    public void testOddCombos() {
        addCard(Zone.BATTLEFIELD, playerA, "Tundra", 19);
        addCard(Zone.HAND, playerA, mstfrm);
        addCard(Zone.HAND, playerA, glrdr);
        addCard(Zone.HAND, playerA, mtllc);
        addCard(Zone.HAND, playerA, pltd);
        addCard(Zone.HAND, playerA, shpd);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, mstfrm);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, glrdr);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, mtllc);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, pltd);

        setType(mstfrm, rogue);
        setType(mstfrm, wizard);
        setType(mstfrm, warrior);

        setType(glrdr, rogue);
        setType(glrdr, cleric);

        setType(mtllc, cleric);
        setType(mtllc, wizard);

        setType(pltd, warrior);
        setType(pltd, wizard);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, shpd);

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 28);
    }
}
