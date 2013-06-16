package org.mage.test.cards.single;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class SeanceTest extends CardTestPlayerBase {

    @Test
    public void testCard() {
        addCard(Zone.BATTLEFIELD, playerA, "Seance");
        addCard(Zone.GRAVEYARD, playerA, "Craw Wurm");

        setStopAt(1, PhaseStep.DRAW);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertExileCount("Craw Wurm", 1);
        assertPermanentCount(playerA, "Craw Wurm", 1);
    }

    @Test
    public void testCard1() {
        addCard(Zone.BATTLEFIELD, playerA, "Seance");
        addCard(Zone.GRAVEYARD, playerA, "Craw Wurm");

        setStopAt(2, PhaseStep.DRAW);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertExileCount("Craw Wurm", 1);
        assertPermanentCount(playerA, "Craw Wurm", 0);
    }

}
