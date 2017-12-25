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

    /*
        Aether Figment   {1}{U}
        Creature - Illusion
        1/1
        Kicker {3} (You may pay an additional as you cast this spell.)
        Aether Figment can't be blocked.
        If Aether Figment was kicked, it enters the battlefield with two +1/+1 counters on it.
     */
    @Test
    public void testEnteringWithCounters() {
        addCard(Zone.LIBRARY, playerA, "Lightning Bolt", 3);
        skipInitShuffling();
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        // When Augur of Bolas enters the battlefield, look at the top three cards of your library.
        // You may reveal an instant or sorcery card from among them and put it into your hand. Put the rest on the bottom of your library in any order.
        addCard(Zone.HAND, playerA, "Augur of Bolas"); // Creature {1}{U}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Augur of Bolas");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Augur of Bolas", 1);
        assertHandCount(playerA, "Lightning Bolt", 1);
    }
}
