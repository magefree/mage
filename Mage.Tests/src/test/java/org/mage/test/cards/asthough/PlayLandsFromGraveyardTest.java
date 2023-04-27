package org.mage.test.cards.asthough;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBaseWithAIHelps;

/**
 * @author JayDi85
 */
public class PlayLandsFromGraveyardTest extends CardTestPlayerBaseWithAIHelps {

    @Test
    public void test_CrucibleOfWorlds() {
        removeAllCardsFromHand(playerA);
        removeAllCardsFromLibrary(playerA);

        // You may play lands from your graveyard.
        addCard(Zone.HAND, playerA, "Crucible of Worlds"); // {3}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        //
        addCard(Zone.GRAVEYARD, playerA, "Island", 1);
        //
        addCard(Zone.GRAVEYARD, playerA, "Balduvian Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        checkGraveyardCount("graveyard before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Balduvian Bears", 1);
        checkGraveyardCount("graveyard before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Island", 1);
        checkPlayableAbility("can't play as creature", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Balduvian Bears", false);
        checkPlayableAbility("can't play as land", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Play Island", false);

        // play artifact and apply play from graveyard effect
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Crucible of Worlds");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPlayableAbility("can't play as creature", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Balduvian Bears", false);
        checkPlayableAbility("can play as land", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Play Island", true);

        // play land
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Island");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Island", 1);
    }
}
