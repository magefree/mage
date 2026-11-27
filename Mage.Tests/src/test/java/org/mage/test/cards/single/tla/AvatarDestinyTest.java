package org.mage.test.cards.single.tla;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Shaun Adams
 */
public class AvatarDestinyTest extends CardTestPlayerBase {

    /**
     * When enchanted creature dies, mill cards equal to its power. Return this card to its
     * owner's hand and up to one creature card milled this way to the battlefield under your control.
     * <p>
     * Nothing says the creature enters tapped.
     */
    @Test
    public void testReturnedCreatureEntersUntapped() {
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.HAND, playerA, "Avatar Destiny");
        addCard(Zone.HAND, playerB, "Lightning Bolt");
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Island");
        addCard(Zone.LIBRARY, playerA, "Memnite");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Avatar Destiny", "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", "Grizzly Bears");
        setChoice(playerA, "Memnite");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Grizzly Bears", 1);
        assertHandCount(playerA, "Avatar Destiny", 1);
        assertPermanentCount(playerA, "Memnite", 1);
        assertTapped("Memnite", false);
    }
}
