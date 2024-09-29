package org.mage.test.cards.triggers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author jimga150
 */
public class EntersBattlefieldOneOrMoreTriggerTest extends CardTestPlayerBase {

    @Test
    public void testZoneChangeMulti() {
        addCard(Zone.BATTLEFIELD, playerA, "Marneus Calgar", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Gor Muldrak, Amphinologist", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Marneus Calgar", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Memnite", 1);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertPermanentCount(playerA, "Salamander Warrior Token", 1);
        assertPermanentCount(playerB, "Salamander Warrior Token", 1);

        assertHandCount(playerA, 1);
        assertHandCount(playerB, 1);
    }
}
