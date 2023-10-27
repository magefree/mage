package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class CraftTest extends CardTestPlayerBase {

    private static final String sawblades = "Spring-Loaded Sawblades";
    private static final String chariot = "Bladewheel Chariot";
    private static final String relic = "Darksteel Relic";

    @Test
    public void testExilePermanent() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerA, sawblades);
        addCard(Zone.BATTLEFIELD, playerA, relic);

        addTarget(playerA, relic);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Craft");

        setStopAt(1, PhaseStep.END_TURN);

        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, sawblades, 0);
        assertPermanentCount(playerA, chariot, 1);
        assertPermanentCount(playerA, relic, 0);
        assertExileCount(playerA, relic, 1);
    }

    @Test
    public void testExileCard() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerA, sawblades);
        addCard(Zone.GRAVEYARD, playerA, relic);

        addTarget(playerA, relic);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Craft");

        setStopAt(1, PhaseStep.END_TURN);

        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, sawblades, 0);
        assertPermanentCount(playerA, chariot, 1);
        assertGraveyardCount(playerA, relic, 0);
        assertExileCount(playerA, relic, 1);
    }
}
