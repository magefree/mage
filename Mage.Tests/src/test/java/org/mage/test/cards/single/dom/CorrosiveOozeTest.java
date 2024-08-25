package org.mage.test.cards.single.dom;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class CorrosiveOozeTest extends CardTestPlayerBase {

    private static final String ooze = "Corrosive Ooze"; // 1G 2/2
    // Whenever Corrosive Ooze blocks or becomes blocked by an equipped creature,
    // destroy all Equipment attached to that creature at end of combat.
    /*
     * The set of Equipment to be destroyed is determined only as Corrosive Ooze’s delayed triggered ability resolves
     * at the end of combat. The Equipment will be destroyed even if Corrosive Ooze leaves the battlefield before that time.
     * --
     * If the creature Corrosive Ooze blocks or is blocking leaves the battlefield, the Equipment that was attached to
     * that creature immediately before it left the battlefield will be destroyed as Corrosive Ooze’s delayed triggered
     * ability resolves at the end of combat.
     */

    private static final String sword = "Short Sword"; // Equip 1: +1/+1
    private static final String wizard = "Fugitive Wizard"; // 1/1
    private static final String kraken = "Kraken Hatchling"; // 0/4
    private static final String centaur = "Centaur Courser"; // 3/3
    private static final String acolyte = "Acolyte of Xathrid"; // 0/1

    @Test
    public void testTradeAttacking() {
        addCard(Zone.BATTLEFIELD, playerA, ooze);
        addCard(Zone.BATTLEFIELD, playerB, wizard);
        addCard(Zone.BATTLEFIELD, playerB, sword);
        addCard(Zone.BATTLEFIELD, playerB, "Wastes");

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Equip", wizard);

        attack(3, playerA, ooze, playerB);
        block(3, playerB, wizard, ooze);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, ooze, 1);
        assertGraveyardCount(playerB, wizard, 1);
        assertGraveyardCount(playerB, sword, 1);
    }

    @Test
    public void testTradeBlocking() {
        addCard(Zone.BATTLEFIELD, playerA, ooze);
        addCard(Zone.BATTLEFIELD, playerB, wizard);
        addCard(Zone.BATTLEFIELD, playerB, sword);
        addCard(Zone.BATTLEFIELD, playerB, "Wastes");

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Equip", wizard);

        attack(2, playerB, wizard, playerA);
        block(2, playerA, ooze, wizard);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, ooze, 1);
        assertGraveyardCount(playerB, wizard, 1);
        assertGraveyardCount(playerB, sword, 1);
    }

    @Test
    public void testBounceAttacking() {
        addCard(Zone.BATTLEFIELD, playerA, ooze);
        addCard(Zone.BATTLEFIELD, playerB, kraken);
        addCard(Zone.BATTLEFIELD, playerB, sword);
        addCard(Zone.BATTLEFIELD, playerB, "Wastes");

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Equip", kraken);

        attack(3, playerA, ooze, playerB);
        block(3, playerB, kraken, ooze);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, ooze, 1);
        assertPermanentCount(playerB, kraken, 1);
        assertGraveyardCount(playerB, sword, 1);
    }

    @Test
    public void testBounceBlocking() {
        addCard(Zone.BATTLEFIELD, playerA, ooze);
        addCard(Zone.BATTLEFIELD, playerB, kraken);
        addCard(Zone.BATTLEFIELD, playerB, sword);
        addCard(Zone.BATTLEFIELD, playerB, "Wastes");

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Equip", kraken);

        attack(2, playerB, kraken, playerA);
        block(2, playerA, ooze, kraken);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, ooze, 1);
        assertPermanentCount(playerB, kraken, 1);
        assertGraveyardCount(playerB, sword, 1);
    }

    @Test
    public void testEatenAttacking() {
        addCard(Zone.BATTLEFIELD, playerA, ooze);
        addCard(Zone.BATTLEFIELD, playerB, centaur);
        addCard(Zone.BATTLEFIELD, playerB, sword);
        addCard(Zone.BATTLEFIELD, playerB, "Wastes");

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Equip", centaur);

        attack(3, playerA, ooze, playerB);
        block(3, playerB, centaur, ooze);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, ooze, 1);
        assertPermanentCount(playerB, centaur, 1);
        assertGraveyardCount(playerB, sword, 1);
    }

    @Test
    public void testEatenBlocking() {
        addCard(Zone.BATTLEFIELD, playerA, ooze);
        addCard(Zone.BATTLEFIELD, playerB, centaur);
        addCard(Zone.BATTLEFIELD, playerB, sword);
        addCard(Zone.BATTLEFIELD, playerB, "Wastes");

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Equip", centaur);

        attack(2, playerB, centaur, playerA);
        block(2, playerA, ooze, centaur);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, ooze, 1);
        assertPermanentCount(playerB, centaur, 1);
        assertGraveyardCount(playerB, sword, 1);
    }

    @Test
    public void testEatAttacking() {
        addCard(Zone.BATTLEFIELD, playerA, ooze);
        addCard(Zone.BATTLEFIELD, playerB, acolyte);
        addCard(Zone.BATTLEFIELD, playerB, sword);
        addCard(Zone.BATTLEFIELD, playerB, "Wastes");

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Equip", acolyte);

        attack(3, playerA, ooze, playerB);
        block(3, playerB, acolyte, ooze);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, ooze, 1);
        assertGraveyardCount(playerB, acolyte, 1);
        assertGraveyardCount(playerB, sword, 1);
    }

    @Test
    public void testEatBlocking() {
        addCard(Zone.BATTLEFIELD, playerA, ooze);
        addCard(Zone.BATTLEFIELD, playerB, acolyte);
        addCard(Zone.BATTLEFIELD, playerB, sword);
        addCard(Zone.BATTLEFIELD, playerB, "Wastes");

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Equip", acolyte);

        attack(2, playerB, acolyte, playerA);
        block(2, playerA, ooze, acolyte);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, ooze, 1);
        assertGraveyardCount(playerB, acolyte, 1);
        assertGraveyardCount(playerB, sword, 1);
    }

}
