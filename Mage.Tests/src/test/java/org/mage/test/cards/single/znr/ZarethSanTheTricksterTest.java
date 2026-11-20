package org.mage.test.cards.single.znr;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class ZarethSanTheTricksterTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.z.ZarethSanTheTrickster}
     * <p>
     * {3}{U}{B} Legendary Creature - Merfolk Rogue 4/4<br>
     * Flash<br>
     * {2}{U}{B}, Return an unblocked attacking Rogue you control to its owner's hand: Put Zareth San, the Trickster
     * from your hand onto the battlefield tapped and attacking.<br>
     * Whenever Zareth San deals combat damage to a player, you may put target permanent card from that player's
     * graveyard onto the battlefield under your control.
     */
    private static final String ZARETH_SAN = "Zareth San, the Trickster";

    @Test
    public void test_PutTappedAndAttacking() {
        addCard(Zone.HAND, playerA, ZARETH_SAN);
        addCard(Zone.BATTLEFIELD, playerA, "Underground Sea", 4);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Merfolk Windrobber"); // rogue 1/1

        attack(1, playerA, "Merfolk Windrobber");
        activateAbility(1, PhaseStep.DECLARE_BLOCKERS, playerA, "{2}{U}{B}, Return");
        setChoice(playerA, "Merfolk Windrobber"); // rogue to return

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, "Merfolk Windrobber", 1);
        assertTapped(ZARETH_SAN, true);
        assertLife(playerB, 20 - 4);
    }
}
