package org.mage.test.cards.copy;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author DarioBarrabinoDev
 */
public class CantBeCopiedTest extends CardTestPlayerBase {

    @Test
    public void test_SeeDouble_CantBeCopied() {
        // This spell can't be copied.
        // Choose one. ...
        // * Create a token that's a copy of target creature.
        addCard(Zone.HAND, playerA, "See Double"); // {2}{U}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        //
        // Copy target instant or sorcery spell. You may choose new targets for the copy.
        addCard(Zone.HAND, playerB, "Reverberate"); // {R}{R}
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "See Double", "Grizzly Bears");
        setModeChoice(playerA, "2");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Reverberate", "See Double", "See Double");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // Reverberate resolves but makes no copy, so only See Double's token is created
        assertGraveyardCount(playerB, "Reverberate", 1);
        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertPermanentCount(playerB, "Grizzly Bears", 1);
    }

    @Test
    public void test_NormalSpell_CanBeCopied() {
        // the other spells are still copied
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerB, "Reverberate");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Reverberate", "Lightning Bolt", "Lightning Bolt");
        setChoice(playerB, true); // change targets of the copy
        addTarget(playerB, playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20 - 3);
        assertLife(playerB, 20 - 3);
    }
}
