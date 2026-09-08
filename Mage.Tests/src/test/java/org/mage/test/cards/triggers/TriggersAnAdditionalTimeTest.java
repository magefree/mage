package org.mage.test.cards.triggers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class TriggersAnAdditionalTimeTest extends CardTestPlayerBase {

    @Test
    public void testTriggerMultiplier() {
        addCard(Zone.BATTLEFIELD, playerA, "Annie Joins Up");
        addCard(Zone.BATTLEFIELD, playerA, "Alesha, Who Laughs at Fate");

        attack(1, playerA, "Alesha, Who Laughs at Fate");
        setChoice(playerA, "Whenever");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, "Alesha, Who Laughs at Fate", 4, 4);
    }

    @Test
    public void testCastTrigger() {
        addCard(Zone.BATTLEFIELD, playerA, "Wastes", 8);
        addCard(Zone.BATTLEFIELD, playerA, "Echoes of Eternity");
        addCard(Zone.HAND, playerA, "Breaker of Creation");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Breaker of Creation");
        setChoice(playerA, "Whenever you cast");
        setChoice(playerA, "When you cast this spell");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Breaker of Creation", 2);
        assertLife(playerA, 20 + 9 + 9);
    }
}
