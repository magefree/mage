package org.mage.test.cards.continuous;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class IrencragFeatTest extends CardTestPlayerBase {
    @Test
    public void castFirst() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 10);
        addCard(Zone.HAND, playerA, "Irencrag Feat", 1);
        addCard(Zone.HAND, playerA, "Dwarven Trader", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Irencrag Feat");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dwarven Trader");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dwarven Trader");
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

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dwarven Trader");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dwarven Trader");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Irencrag Feat");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dwarven Trader");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dwarven Trader");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);

        execute();

        assertHandCount(playerA, "Dwarven Trader", 1);
        assertPermanentCount(playerA, "Dwarven Trader", 3);
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
        setStopAt(3, PhaseStep.BEGIN_COMBAT);

        execute();

        assertHandCount(playerA, "Dwarven Trader", 0);
        assertPermanentCount(playerA, "Dwarven Trader", 4);
    }
}
