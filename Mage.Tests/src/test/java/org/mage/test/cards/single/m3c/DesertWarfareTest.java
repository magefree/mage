package org.mage.test.cards.single.m3c;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author balazskristof
 */
public class DesertWarfareTest extends CardTestPlayerBase {

    @Test
    public void TestTokens() {
        addCard(Zone.BATTLEFIELD, playerA, "Desert Warfare");
        addCard(Zone.BATTLEFIELD, playerA, "Desert", 4);
        addCard(Zone.HAND, playerA, "Desert");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertTokenCount(playerA, "Sand Warrior Token", 0);

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();
        assertTokenCount(playerA, "Sand Warrior Token", 0);

        playLand(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Desert");
        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();
        assertTokenCount(playerA, "Sand Warrior Token", 5);

        setStopAt(4, PhaseStep.BEGIN_COMBAT);
        execute();
        assertTokenCount(playerA, "Sand Warrior Token", 5);

        setStopAt(5, PhaseStep.BEGIN_COMBAT);
        execute();
        assertTokenCount(playerA, "Sand Warrior Token", 10);
    }

    @Test
    public void TestSacrifice() {
        addCard(Zone.BATTLEFIELD, playerA, "Desert Warfare");

        addCard(Zone.BATTLEFIELD, playerA, "Desert");
        addCard(Zone.BATTLEFIELD, playerA, "Akki Avalanchers");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice");
        setChoice(playerA, "Desert");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Desert", 1);
        assertPermanentCount(playerA, "Desert", 0);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Desert", 0);
        assertPermanentCount(playerA, "Desert", 1);
    }

    @Test
    public void TestSacrificeOpponentsTurn() {
        addCard(Zone.BATTLEFIELD, playerA, "Desert Warfare");

        addCard(Zone.BATTLEFIELD, playerA, "Desert");
        addCard(Zone.BATTLEFIELD, playerA, "Akki Avalanchers");

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice");
        setChoice(playerA, "Desert");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Desert", 1);
        assertPermanentCount(playerA, "Desert", 0);

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Desert", 0);
        assertPermanentCount(playerA, "Desert", 1);
    }

    @Test
    public void TestDestroy() {
        addCard(Zone.BATTLEFIELD, playerA, "Desert Warfare");
        addCard(Zone.BATTLEFIELD, playerA, "Desert");

        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 4);
        addCard(Zone.HAND, playerB, "Volcanic Upheaval");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Volcanic Upheaval");
        addTarget(playerB, "Desert");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Desert", 1);
        assertPermanentCount(playerA, "Desert", 0);
    }

    @Test
    public void TestDiscard() {
        addCard(Zone.BATTLEFIELD, playerA, "Desert Warfare");

        skipInitShuffling();
        addCard(Zone.LIBRARY, playerA, "Island");
        addCard(Zone.LIBRARY, playerA, "Desert");

        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.HAND, playerA, "Mountain", 2);
        addCard(Zone.HAND, playerA, "Faithless Looting");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Faithless Looting");
        setChoice(playerA, "Island^Desert");
        //setChoice(playerA, "Desert");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Desert", 0);
        assertGraveyardCount(playerA, "Island", 1);
        assertPermanentCount(playerA, "Desert", 1);
    }

    @Test
    public void TestDiscardOpponentsTurn() {
        addCard(Zone.BATTLEFIELD, playerA, "Desert Warfare");

        skipInitShuffling();
        addCard(Zone.LIBRARY, playerA, "Island");
        addCard(Zone.LIBRARY, playerA, "Desert");

        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.HAND, playerA, "Mountain", 2);
        addCard(Zone.HAND, playerA, "Faithless Looting");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Faithless Looting");
        setChoice(playerA, "Island");
        setChoice(playerA, "Desert");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertGraveyardCount(playerA, "Desert", 0);
        assertGraveyardCount(playerA, "Island", 1);
        assertPermanentCount(playerA, "Desert", 1);
    }

    @Test
    public void TestCycling() {
        addCard(Zone.BATTLEFIELD, playerA, "Desert Warfare");

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.HAND, playerA, "Desert of the Fervent");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cycling {1}{R}");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Desert of the Fervent", 0);
        assertPermanentCount(playerA, "Desert of the Fervent", 1);
    }

    @Test
    public void TestCyclingOpponentsTurn() {
        addCard(Zone.BATTLEFIELD, playerA, "Desert Warfare");

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.HAND, playerA, "Desert of the Fervent");

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Cycling {1}{R}");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Desert of the Fervent", 1);
        assertPermanentCount(playerA, "Desert of the Fervent", 0);

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Desert of the Fervent", 0);
        assertPermanentCount(playerA, "Desert of the Fervent", 1);
    }

    @Test
    public void TestMill() {
        addCard(Zone.BATTLEFIELD, playerA, "Desert Warfare");

        skipInitShuffling();
        addCard(Zone.LIBRARY, playerA, "Plains");
        addCard(Zone.LIBRARY, playerA, "Island");
        addCard(Zone.LIBRARY, playerA, "Desert");

        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.HAND, playerA, "Stitcher's Supplier");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Stitcher's Supplier");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Desert", 0);
        assertPermanentCount(playerA, "Desert", 1);
        assertGraveyardCount(playerA, "Plains", 1);
        assertGraveyardCount(playerA, "Island", 1);
    }

    @Test
    public void TestMillOpponentsTurn() {
        addCard(Zone.BATTLEFIELD, playerA, "Desert Warfare");

        skipInitShuffling();
        addCard(Zone.LIBRARY, playerA, "Plains");
        addCard(Zone.LIBRARY, playerA, "Island");
        addCard(Zone.LIBRARY, playerA, "Desert");

        addCard(Zone.BATTLEFIELD, playerA, "Stitcher's Supplier");

        addCard(Zone.BATTLEFIELD, playerB, "Mountain");
        addCard(Zone.HAND, playerB, "Shock");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Shock");
        addTarget(playerB, "Stitcher's Supplier");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Desert", 0);
        assertGraveyardCount(playerA, "Desert", 1);
        assertGraveyardCount(playerA, "Plains", 1);
        assertGraveyardCount(playerA, "Island", 1);

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Desert", 1);
        assertGraveyardCount(playerA, "Desert", 0);
        assertGraveyardCount(playerA, "Plains", 1);
        assertGraveyardCount(playerA, "Island", 1);
    }

    @Test
    public void TestDuneChanter() {
        addCard(Zone.BATTLEFIELD, playerA, "Desert Warfare");
        addCard(Zone.BATTLEFIELD, playerA, "Dune Chanter");

        skipInitShuffling();
        addCard(Zone.LIBRARY, playerA, "Plains");
        addCard(Zone.LIBRARY, playerA, "Island");
        addCard(Zone.LIBRARY, playerA, "Desert");

        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.HAND, playerA, "Stitcher's Supplier");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Stitcher's Supplier");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Desert", 0);
        assertGraveyardCount(playerA, "Plains", 0);
        assertGraveyardCount(playerA, "Island", 0);
        assertPermanentCount(playerA, "Desert", 1);
        assertPermanentCount(playerA, "Plains", 1);
        assertPermanentCount(playerA, "Island", 1);

    }
}

