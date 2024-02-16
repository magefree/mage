
package org.mage.test.cards.dynamicvalue;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class SewerNemesisTest extends CardTestPlayerBase {


    /**
     * BUG: Sewer Nemesis count's all cards in each player's graveyard to determine it's * / *, not just the chosen player's graveyard.
    */
    @Test
    public void test1() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);

        // As Sewer Nemesis enters the battlefield, choose a player.
        // Sewer Nemesis's power and toughness are each equal to the number of cards in the chosen player's graveyard.
        // Whenever the chosen player casts a spell, that player puts the top card of their library into their graveyard.
        addCard(Zone.HAND, playerA, "Sewer Nemesis");

        addCard(Zone.GRAVEYARD, playerA, "Raging Goblin",4);
        addCard(Zone.GRAVEYARD, playerB, "Raging Goblin",1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sewer Nemesis");
        setChoice(playerA, "PlayerA"); // Starting player

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA,"Sewer Nemesis", 0);
        assertPowerToughness(playerA,  "Sewer Nemesis", 4, 4);
    }
}
