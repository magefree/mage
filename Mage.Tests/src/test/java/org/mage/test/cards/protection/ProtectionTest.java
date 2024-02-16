
package org.mage.test.cards.protection;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class ProtectionTest extends CardTestPlayerBase {

    @Test
    public void testProtectionFromColoredSpells() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        // Delve
        // Destroy target creature.
        addCard(Zone.HAND, playerA, "Murderous Cut"); // {4}{B}

        // Emrakul, the Aeons Torn can't be countered.
        // When you cast Emrakul, take an extra turn after this one.
        // Flying, protection from colored spells, annihilator 6
        // When Emrakul is put into a graveyard from anywhere, its owner shuffles their graveyard into their library.
        addCard(Zone.BATTLEFIELD, playerB, "Emrakul, the Aeons Torn");

        checkPlayableAbility("Can't murder", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Murderous", false);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerB, "Emrakul, the Aeons Torn", 1);
        assertHandCount(playerA, "Murderous Cut", 1);
    }

}
