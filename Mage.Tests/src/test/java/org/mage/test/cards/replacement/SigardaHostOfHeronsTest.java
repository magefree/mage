package org.mage.test.cards.replacement;

import mage.Constants;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Sigarda, Host of Herons:
 *   Spells and abilities your opponents control can't cause you to sacrifice permanents.
 *
 * @author noxx
 */
public class SigardaHostOfHeronsTest extends CardTestPlayerBase {

    /**
     * Tests that spells don't work for opponents but still work for controller
     */
    @Test
    public void testCard() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Sigarda, Host of Herons");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Devout Chaplain");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Corpse Traders");
        addCard(Constants.Zone.HAND, playerA, "Diabolic Edict");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Swamp", 2);

        addCard(Constants.Zone.HAND, playerB, "Diabolic Edict");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Swamp", 2);
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Sheoldred, Whispering One");

        castSpell(2, Constants.PhaseStep.PRECOMBAT_MAIN, playerB, "Diabolic Edict", playerA);
        castSpell(3, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Diabolic Edict", playerB);

        setStopAt(3, Constants.PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Sigarda, Host of Herons", 1);
        assertPermanentCount(playerA, "Devout Chaplain", 1);
        assertPermanentCount(playerA, "Corpse Traders", 1);
        assertGraveyardCount(playerA, 1);

        assertPermanentCount(playerB, "Sheoldred, Whispering One", 0);
        assertGraveyardCount(playerB, 2);
    }

    /**
     * Tests that sacrifice cost works.
     */
    @Test
    public void testSacrificeCost() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Sigarda, Host of Herons");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Devout Chaplain");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Corpse Traders");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Swamp", 3);

        activateAbility(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "{2}{B}");

        setStopAt(1, Constants.PhaseStep.END_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Devout Chaplain", 0);
        assertPermanentCount(playerA, "Corpse Traders", 1);
    }

}
