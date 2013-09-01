package org.mage.test.cards.single;

import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.filter.Filter;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class WardenOfTheWallTest extends CardTestPlayerBase {

    /*
     * Warden of the Wall
     *   Warden of the Wall enters the battlefield tapped.
     *   {T}: Add {1} to your mana pool.
     *   As long as it's not your turn, Warden of the Wall is a 2/3 Gargoyle artifact creature with flying.
     *
     */
    @Test
    public void testCard() {
        addCard(Zone.BATTLEFIELD, playerA, "Warden of the Wall");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPowerToughness(playerA, "Warden of the Wall", 0, 0, Filter.ComparisonScope.All);
    }

    @Test
    public void testCard1() {
        addCard(Zone.BATTLEFIELD, playerA, "Warden of the Wall");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPowerToughness(playerA, "Warden of the Wall", 2, 3, Filter.ComparisonScope.All);
        assertType("Warden of the Wall", CardType.CREATURE, "Gargoyle");
    }

}
