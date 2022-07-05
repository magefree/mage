
package org.mage.test.cards.abilities.oneshot.exile;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class RoonOfTheHiddenRealmTest extends CardTestPlayerBase {

    /**
     * Roon of the Hidden Realm is returning cards to their controller's control
     * instead of the owner's control at the end of the turn. I used his ability
     * on a Perplexing Chimera I gave my opponent and in the end of the turn it
     * returned to the battlefield in their control.
     */
    @Test
    public void testReturnToBattlefieldForOwner() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        // Vigilance, Trample
        // {2}, {T}: Exile another target creature. Return that card to the battlefield under its owner's control at the beginning of the next end step.
        addCard(Zone.BATTLEFIELD, playerA, "Roon of the Hidden Realm");

        // Whenever an opponent casts a spell, you may exchange control of Perplexing Chimera and that spell. If you do, you may choose new targets for the spell.
        addCard(Zone.BATTLEFIELD, playerA, "Perplexing Chimera");

        addCard(Zone.HAND, playerB, "Silvercoat Lion", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Silvercoat Lion");
        setChoice(playerA, true);

        activateAbility(2, PhaseStep.POSTCOMBAT_MAIN, playerA, "{2}", "Perplexing Chimera");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertTapped("Roon of the Hidden Realm", true);
        assertPermanentCount(playerA, "Roon of the Hidden Realm", 1);
        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertPermanentCount(playerB, "Perplexing Chimera", 0);
        assertPermanentCount(playerA, "Perplexing Chimera", 1);

    }
}
