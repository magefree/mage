
package org.mage.test.cards.single.woe;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class HeartflameDuelistTest extends CardTestPlayerBase {

    /**
     * Heartflame Duelist {1}{W}
     * Creature — Human Knight
     * Instant and sorcery spells you control have lifelink.
     * 3/1
     * <p>
     * //
     * Heartflame Slash {2}{R}
     * Instant — Adventure
     * Heartflame Slash deals 3 damage to any target.
     */
    private final String duelist = "Heartflame Duelist";

    @Test
    public void lifelinkOnPlayer() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, duelist, 1);
        addCard(Zone.HAND, playerA, duelist, 1);

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Heartflame Slash", playerB);

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20 + 3);
        assertLife(playerB, 20 - 3);
        assertPermanentCount(playerA, duelist, 1);
        assertExileCount(playerA, duelist, 1);
    }

    @Test
    public void lifelinkOnPermanent() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, duelist, 1);
        addCard(Zone.HAND, playerA, duelist, 1);

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Heartflame Slash", duelist);

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20 + 3);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, duelist, 0);
        assertExileCount(playerA, duelist, 1);
        assertGraveyardCount(playerA, duelist, 1);
    }
}