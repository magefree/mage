package org.mage.test.cards.single;

import mage.Constants;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class SecretsOfTheDeadTest extends CardTestPlayerBase {

    @Test
    public void testCard() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Secrets of the Dead");
        addCard(Constants.Zone.GRAVEYARD, playerA, "Lingering Souls");

        activateAbility(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Flashback {1}{B}");
        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Spirit", 2);
        assertHandCount(playerA, 1);
    }

    @Test
    public void testCard1() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Constants.Zone.GRAVEYARD, playerA, "Lingering Souls");

        activateAbility(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Flashback {1}{B}");
        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Spirit", 2);
        assertHandCount(playerA, 0);
    }

}
