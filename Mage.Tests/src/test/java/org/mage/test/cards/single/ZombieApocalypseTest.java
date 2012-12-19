package org.mage.test.cards.single;

import mage.Constants;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class ZombieApocalypseTest extends CardTestPlayerBase {

    @Test
    public void testCard() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Swamp", 6);
        addCard(Constants.Zone.HAND, playerA, "Zombie Apocalypse");
        addCard(Constants.Zone.GRAVEYARD, playerA, "Bog Raiders", 2);
        addCard(Constants.Zone.GRAVEYARD, playerA, "Toxic Nim", 1);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "White Knight");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Black Knight", 2);

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Zombie Apocalypse");

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, 2);
        assertGraveyardCount(playerB, 2);
        assertPermanentCount(playerA, "Bog Raiders", 2);
        assertPermanentCount(playerA, "Toxic Nim", 1);
        assertPermanentCount(playerA, "White Knight", 0);
        assertPermanentCount(playerA, "Black Knight", 0);

    }


}
