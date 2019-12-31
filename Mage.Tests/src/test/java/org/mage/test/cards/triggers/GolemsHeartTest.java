
package org.mage.test.cards.triggers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class GolemsHeartTest extends CardTestPlayerBase {

    /*
     My opponent and I were both playing artifact decks.
     They had Golem's Heart out.
     They weren't gaining life when I or they cast spells.
     */
    @Test
    public void testFirstTriggeredAbility() {
        // Whenever a player casts an artifact spell, you may gain 1 life.
        addCard(Zone.BATTLEFIELD, playerA, "Golem's Heart", 1);

        addCard(Zone.HAND, playerA, "Expedition Map"); // {1}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);

        addCard(Zone.HAND, playerB, "Darksteel Axe"); // {1}
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Expedition Map");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Darksteel Axe");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 22);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Expedition Map", 1);
        assertPermanentCount(playerB, "Darksteel Axe", 1);

    }
}
