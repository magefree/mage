package org.mage.test.cards.damage;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class ExcessDamageTest extends CardTestPlayerBase {

    private static final String spill = "Flame Spill";
    private static final String bear = "Grizzly Bears";
    private static final String jab = "Flame Jab";
    private static final String spirit = "Pestilent Spirit";
    private static final String myr = "Darksteel Myr";
    private static final String gideon = "Gideon Jura";
    private static final String leyline = "Leyline of Punishment";
    private static final String bolt = "Lightning Bolt";
    private static final String aegar = "Aegar, the Freezing Flame";

    @Test
    public void testExcessDamageRegular() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.BATTLEFIELD, playerA, bear);
        addCard(Zone.HAND, playerA, spill);

        setStrictChooseMode(true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, spill, bear);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, bear, 1);
        assertPermanentCount(playerA, bear, 0);
        assertLife(playerA, 20 - 2);
    }

    @Test
    public void testExcessDamageAlreadyDamaged() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.BATTLEFIELD, playerA, bear);
        addCard(Zone.HAND, playerA, spill);
        addCard(Zone.HAND, playerA, jab);

        setStrictChooseMode(true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, jab, bear);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, spill, bear);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, bear, 1);
        assertPermanentCount(playerA, bear, 0);
        assertLife(playerA, 20 - 3);
    }

    @Test
    public void testExcessDamageDeathtouch() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.BATTLEFIELD, playerA, bear);
        addCard(Zone.BATTLEFIELD, playerA, spirit);
        addCard(Zone.HAND, playerA, spill);

        setStrictChooseMode(true);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, spill, bear);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, bear, 1);
        assertPermanentCount(playerA, bear, 0);
        assertLife(playerA, 20 - 3);
    }

    @Test
    public void testExcessDamageIndestructible() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.BATTLEFIELD, playerA, myr);
        addCard(Zone.HAND, playerA, spill);

        setStrictChooseMode(true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, spill, myr);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, myr, 0);
        assertPermanentCount(playerA, myr, 1);
        assertLife(playerA, 20 - 3);
    }

    @Test
    public void testExcessDamagePlaneswalker() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.BATTLEFIELD, playerA, gideon);
        addCard(Zone.BATTLEFIELD, playerA, leyline);
        addCard(Zone.HAND, playerA, spill);
        addCard(Zone.HAND, playerA, bolt);

        setStrictChooseMode(true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bolt, gideon, true);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "0:");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, spill, gideon);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, gideon, 1);
        assertPermanentCount(playerA, gideon, 0);
        assertLife(playerA, 20 - 1);
    }

    @Test
    public void testAegarTheFreezingFlame() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, aegar);
        addCard(Zone.HAND, playerA, bolt);
        addCard(Zone.BATTLEFIELD, playerB, bear);

        setStrictChooseMode(true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bolt, bear);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, 1);
        assertGraveyardCount(playerA, bolt, 1);
        assertGraveyardCount(playerB, bear, 1);
    }
}
