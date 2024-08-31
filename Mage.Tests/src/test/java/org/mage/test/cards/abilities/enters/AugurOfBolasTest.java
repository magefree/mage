package org.mage.test.cards.abilities.enters;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class AugurOfBolasTest extends CardTestPlayerBase {

    @Test
    public void testAbility() {
        addCard(Zone.LIBRARY, playerA, "Lightning Bolt", 3);
        skipInitShuffling();
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        // When Augur of Bolas enters the battlefield, look at the top three cards of your library.
        // You may reveal an instant or sorcery card from among them and put it into your hand. Put the rest on the bottom of your library in any order.
        addCard(Zone.HAND, playerA, "Augur of Bolas"); // Creature {1}{U}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Augur of Bolas");
        setChoice(playerA, true);
        addTarget(playerA, "Lightning Bolt"); // to hand
        setChoice(playerA, "Lightning Bolt"); // order to bottom

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Augur of Bolas", 1);
        assertHandCount(playerA, "Lightning Bolt", 1);
    }
}
