
package org.mage.test.cards.replacement.entersBattlefield;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class KjeldoranOutpostTest extends CardTestPlayerBase {

    @Test
    public void testNoPlainsAvailable() {
        // If Kjeldoran Outpost would enter the battlefield, sacrifice a Plains instead. If you do, put Kjeldoran Outpost onto the battlefield. If you don't, put it into its owner's graveyard.
        // {T}: Add {W}.
        // {1}{W}, {tap}: Put a 1/1 white Soldier creature token onto the battlefield.
        addCard(Zone.HAND, playerA, "Kjeldoran Outpost");

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Kjeldoran Outpost");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Kjeldoran Outpost", 0);
        assertGraveyardCount(playerA, "Kjeldoran Outpost", 1);
    }

    @Test
    public void testPlainsAvailable() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        // If Kjeldoran Outpost would enter the battlefield, sacrifice a Plains instead. If you do, put Kjeldoran Outpost onto the battlefield. If you don't, put it into its owner's graveyard.
        // {T}: Add {W}.
        // {1}{W}, {tap}: Put a 1/1 white Soldier creature token onto the battlefield.
        addCard(Zone.HAND, playerA, "Kjeldoran Outpost");

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Kjeldoran Outpost");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Kjeldoran Outpost", 1);
        assertGraveyardCount(playerA, "Kjeldoran Outpost", 0);
        assertGraveyardCount(playerA, "Plains", 1);
    }

    @Test
    public void testOnlySnowcoveredPlainsAvailable() {
        addCard(Zone.BATTLEFIELD, playerA, "Snow-Covered Plains");
        // If Kjeldoran Outpost would enter the battlefield, sacrifice a Plains instead. If you do, put Kjeldoran Outpost onto the battlefield. If you don't, put it into its owner's graveyard.
        // {T}: Add {W}.
        // {1}{W}, {tap}: Put a 1/1 white Soldier creature token onto the battlefield.
        addCard(Zone.HAND, playerA, "Kjeldoran Outpost");

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Kjeldoran Outpost");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Kjeldoran Outpost", 1);
        assertGraveyardCount(playerA, "Kjeldoran Outpost", 0);
        assertGraveyardCount(playerA, "Snow-Covered Plains", 1);
    }

}
