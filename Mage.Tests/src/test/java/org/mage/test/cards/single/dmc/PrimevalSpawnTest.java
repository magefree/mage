package org.mage.test.cards.single.dmc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class PrimevalSpawnTest extends CardTestPlayerBase {

    private static final String spawn = "Primeval Spawn";
    private static final String helm = "Helm of Awakening";
    private static final String zombify = "Zombify";
    private static final String omniscience = "Omniscience";

    @Test
    public void testRegular() {
        addCard(Zone.HAND, playerA, spawn);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, spawn);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, spawn, 1);
        assertExileCount(playerA, spawn, 0);
    }

    @Test
    public void testRegularReduced() {
        addCard(Zone.HAND, playerA, spawn);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, helm, 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, spawn);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, spawn, 1);
        assertExileCount(playerA, spawn, 0);
    }

    @Test
    public void testReanimate() {
        addCard(Zone.HAND, playerA, zombify);
        addCard(Zone.GRAVEYARD, playerA, spawn);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, zombify, spawn);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, spawn, 0);
        assertExileCount(playerA, spawn, 1);
    }

    @Test
    public void testFreeCast() {
        addCard(Zone.HAND, playerA, spawn);
        addCard(Zone.BATTLEFIELD, playerA, omniscience);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, spawn);
        setChoice(playerA, true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, spawn, 0);
        assertExileCount(playerA, spawn, 1);
    }
}
