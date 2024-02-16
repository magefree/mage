package org.mage.test.cards.watchers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class WatchersFromDelayedTriggeredAbilitiesTest extends CardTestPlayerBase {

    @Test
    public void test_PsychicTheft_WatcherFromDelayedAbility() {

        // Target player reveals their hand. You choose an instant or sorcery card from it and exile that card.
        // You may cast that card for as long as it remains exiled. At the beginning of the next end step,
        // if you haven't cast the card, return it to its owner's hand.
        addCard(Zone.HAND, playerA, "Psychic Theft", 1); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        //
        addCard(Zone.HAND, playerB, "Lightning Bolt");

        // reveal
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}", 2);
        checkPlayableAbility("can't play bolt", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Lightning Bolt", false);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Psychic Theft", playerB);
        setChoice(playerA, "Lightning Bolt");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // can play until end step
        checkPlayableAbility("can play bolt on 1", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Lightning Bolt", true);
        checkPlayableAbility("can't play bolt on 2", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Lightning Bolt", false);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerB, "Lightning Bolt", 1);
    }
}
