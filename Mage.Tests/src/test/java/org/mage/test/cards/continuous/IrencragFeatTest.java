package org.mage.test.cards.continuous;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class IrencragFeatTest extends CardTestPlayerBase {

    /*
    Irencrag Feat
    Add seven {R}. You can cast only one more spell this turn.
     */

    @Test
    public void castFirst() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 10);
        addCard(Zone.HAND, playerA, "Irencrag Feat", 1);
        addCard(Zone.HAND, playerA, "Dwarven Trader", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Irencrag Feat");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dwarven Trader");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dwarven Trader");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        //assertAllCommandsUsed(); // second trader must be restricted to cast

        assertHandCount(playerA, "Dwarven Trader", 1);
        assertPermanentCount(playerA, "Dwarven Trader", 1);
    }


    @Test
    public void castThird() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 10);
        addCard(Zone.HAND, playerA, "Irencrag Feat", 1);
        addCard(Zone.HAND, playerA, "Dwarven Trader", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dwarven Trader");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dwarven Trader");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Irencrag Feat");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dwarven Trader");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dwarven Trader");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        //assertAllCommandsUsed(); // second trader must be restricted to cast

        assertHandCount(playerA, "Dwarven Trader", 1);
        assertPermanentCount(playerA, "Dwarven Trader", 3);
    }

    @Test
    public void castThirdOnStack() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 10);
        addCard(Zone.HAND, playerA, "Irencrag Feat", 1);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Irencrag Feat");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAllCommandsUsed();

        assertHandCount(playerA, "Lightning Bolt", 0);
        assertLife(playerB, 20 - 4 * 3);
    }

    @Test
    public void castThirdNotOnStack() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 10);
        addCard(Zone.HAND, playerA, "Irencrag Feat", 1);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Irencrag Feat");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        //assertAllCommandsUsed(0); // second bolt must be restricted to cast

        assertHandCount(playerA, "Lightning Bolt", 4 - 3);
        assertLife(playerB, 20 - 3 * 3);
    }


    @Test
    public void nextTurn() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 10);
        addCard(Zone.HAND, playerA, "Irencrag Feat", 1);
        addCard(Zone.HAND, playerA, "Dwarven Trader", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Irencrag Feat");
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Dwarven Trader");
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Dwarven Trader");
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Dwarven Trader");
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Dwarven Trader");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAllCommandsUsed();

        assertHandCount(playerA, "Dwarven Trader", 0);
        assertPermanentCount(playerA, "Dwarven Trader", 4);
    }
}
