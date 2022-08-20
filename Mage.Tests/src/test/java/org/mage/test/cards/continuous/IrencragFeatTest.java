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

        // 1
        checkPlayableAbility("can cast before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Dwarven Trader", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Irencrag Feat");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // 2
        checkPlayableAbility("can cast after feat 1", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Dwarven Trader", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dwarven Trader");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // 3
        checkPlayableAbility("can't cast after feat 2", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Dwarven Trader", false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, "Dwarven Trader", 1);
        assertPermanentCount(playerA, "Dwarven Trader", 1);
    }


    @Test
    public void castThird() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 10);
        addCard(Zone.HAND, playerA, "Irencrag Feat", 1);
        addCard(Zone.HAND, playerA, "Dwarven Trader", 4);

        // 1
        checkPlayableAbility("can cast before feat 1", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Dwarven Trader", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dwarven Trader");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // 2
        checkPlayableAbility("can cast before feat 1", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Dwarven Trader", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dwarven Trader");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // 3
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Irencrag Feat");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // 4
        checkPlayableAbility("can cast after feat 1", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Dwarven Trader", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dwarven Trader");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // 5
        checkPlayableAbility("can't cast after feat 2", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Dwarven Trader", false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, "Dwarven Trader", 1);
        assertPermanentCount(playerA, "Dwarven Trader", 3);
    }

    @Test
    public void castThirdOnStack() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 10);
        addCard(Zone.HAND, playerA, "Irencrag Feat", 1);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 4);

        // no restrictions for stack
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Irencrag Feat");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, "Lightning Bolt", 0);
        assertLife(playerB, 20 - 4 * 3);
    }

    @Test
    public void castThirdNotOnStack() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 10);
        addCard(Zone.HAND, playerA, "Irencrag Feat", 1);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 4);

        // on stack before
        checkPlayableAbility("can cast before feat 1", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Lightning Bolt", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        checkPlayableAbility("can cast before feat 2", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Lightning Bolt", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);

        // feat
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Irencrag Feat");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // after resolve
        checkPlayableAbility("can cast after feat 1", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Lightning Bolt", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        checkPlayableAbility("can't cast after feat 2", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Lightning Bolt", false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

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

        assertHandCount(playerA, "Dwarven Trader", 0);
        assertPermanentCount(playerA, "Dwarven Trader", 4);
    }
}
