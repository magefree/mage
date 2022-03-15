
package org.mage.test.cards.triggers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class OneOrMoreCardsGoToGraveyardTest extends CardTestPlayerBase {

    @Test
    public void TestCrawlingSensation() {
        // At the beginning of your upkeep, you may put the top two cards of your library into your graveyard.
        // Whenever one or more land cards are put into your graveyard from anywhere for the first time each turn, put a 1/1 green Insect creature token onto the battlefield.
        addCard(Zone.BATTLEFIELD, playerA, "Crawling Sensation");

        addCard(Zone.LIBRARY, playerA, "Mountain");
        // {T}, Sacrifice Evolving Wilds: Search your library for a basic land card and put it onto the battlefield tapped. Then shuffle your library.
        addCard(Zone.HAND, playerA, "Evolving Wilds");

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Evolving Wilds");

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}, Sacrifice");
        setChoice(playerA, "Mountain");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Evolving Wilds", 1);
        assertPermanentCount(playerA, "Mountain", 1);
        assertPermanentCount(playerA, "Insect Token", 2);

    }
}
