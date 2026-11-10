package org.mage.test.cards.single.ktk;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class RakshasaVizierTest extends CardTestPlayerBase {

    @Test
    public void test_CardsExiledFromGraveyard_ThatManyCounters() {
        // Whenever one or more cards are put into exile from your graveyard, put that many +1/+1 counters on Rakshasa Vizier.
        addCard(Zone.BATTLEFIELD, playerA, "Rakshasa Vizier"); // 4/4
        //
        // When Bojuka Bog enters the battlefield, exile all cards from target player's graveyard.
        addCard(Zone.HAND, playerA, "Bojuka Bog");
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears", 2);
        addCard(Zone.GRAVEYARD, playerA, "Forest", 1);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bojuka Bog");
        addTarget(playerA, playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerA, 3);
        assertCounterCount(playerA, "Rakshasa Vizier", CounterType.P1P1, 3);
    }

    @Test
    public void test_CardsExiledFromOpponentGraveyard_NoTrigger() {
        // Whenever one or more cards are put into exile from your graveyard, put that many +1/+1 counters on Rakshasa Vizier.
        addCard(Zone.BATTLEFIELD, playerA, "Rakshasa Vizier"); // 4/4
        //
        // When Bojuka Bog enters the battlefield, exile all cards from target player's graveyard.
        addCard(Zone.HAND, playerA, "Bojuka Bog");
        addCard(Zone.GRAVEYARD, playerB, "Grizzly Bears", 2);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bojuka Bog");
        addTarget(playerA, playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerB, 2);
        assertCounterCount(playerA, "Rakshasa Vizier", CounterType.P1P1, 0);
    }
}
