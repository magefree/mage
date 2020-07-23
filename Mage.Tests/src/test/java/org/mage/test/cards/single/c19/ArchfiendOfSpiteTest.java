package org.mage.test.cards.single.c19;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class ArchfiendOfSpiteTest extends CardTestPlayerBase {

    @Test
    public void damageTriggerTest() {
        addCard(Zone.BATTLEFIELD, playerA, "Archfiend of Spite");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.HAND, playerB, "Lightning Bolt", 1);
        setStopAt(1, PhaseStep.UNTAP);
        execute();

        assertPermanentCount(playerA, "Archfiend of Spite", 1);
        assertLife(playerB, 20);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", "Archfiend of Spite");
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, "Archfiend of Spite", 1);
        assertPermanentCount(playerB, "Mountain", 1);
        assertDamageReceived(playerA, "Archfiend of Spite", 3);
        assertLife(playerB, 17);
    }
}
