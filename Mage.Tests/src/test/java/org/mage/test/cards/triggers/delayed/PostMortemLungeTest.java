package org.mage.test.cards.triggers.delayed;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class PostMortemLungeTest extends CardTestPlayerBase {

    /*
    Reported bug: "Postmortem Lunge returns a creature to the battlefield, but does not seem to exile it afterwards."
    */
    @Test
    public void testExilesCreatureAtEndStep() {
        /*
        {X}{B/P} - Sorcery
        Return target creature card with converted mana cost X from your graveyard to the battlefield. 
        It gains haste. Exile it at the beginning of the next end step.
        */
        addCard(Zone.HAND, playerA, "Postmortem Lunge");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.GRAVEYARD, playerA, "Elite Vanguard"); // {W} 2/1 creature

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Postmortem Lunge");

        attack(1, playerA, "Elite Vanguard");

        setStopAt(1, PhaseStep.CLEANUP);
        execute();

        assertLife(playerB, 18);
        assertGraveyardCount(playerA, "Postmortem Lunge", 1);
        assertExileCount("Elite Vanguard", 1);
        assertPermanentCount(playerA, "Elite Vanguard", 0);
    }
}
