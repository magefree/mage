package org.mage.test.cards.continuous;

import mage.Constants;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Wight of Precinct
 *   Wight of Precinct Six gets +1/+1 for each creature card in your opponents' graveyards.
 *
 * @author LevelX2
 */
public class WightOfPrecintSixTest extends CardTestPlayerBase {

    /**
     * Tests no creature cards in opponents graveyard -> no boost
     */
    @Test
    public void testNoCreatureCardsInOpponentsGraveyard() {
        addCard(Constants.Zone.GRAVEYARD, playerA, "Angelic Edict");
        addCard(Constants.Zone.GRAVEYARD, playerA, "Runeclaw Bear");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Wight of Precinct Six");

        addCard(Constants.Zone.GRAVEYARD, playerB, "Angelic Edict");

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Wight of Precinct Six", 1, 1);

    }

    /**
     * Tests two creature cards in opponents graveyard -> boost +2/+2
     */
    @Test
    public void testCreatureCardsInOpponentsGraveyard() {
        addCard(Constants.Zone.GRAVEYARD, playerA, "Angelic Edict");
        addCard(Constants.Zone.GRAVEYARD, playerA, "Runeclaw Bear");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Wight of Precinct Six");

        addCard(Constants.Zone.GRAVEYARD, playerB, "Angelic Edict");
        addCard(Constants.Zone.GRAVEYARD, playerB, "Runeclaw Bear");
        addCard(Constants.Zone.GRAVEYARD, playerB, "Wight of Precinct Six");

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Wight of Precinct Six", 3, 3);

    }

}
