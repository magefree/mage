
package org.mage.test.commander.FFA3;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommander3PlayersFFA;

/**
 *
 * @author LevelX2
 */
public class PrimordialTest extends CardTestCommander3PlayersFFA {

    /**
     * Diluvian Primordial ETB trigger never happened in a 3 player FFA
     * commander game. He just resolved, no ETB trigger occurred.
     */
    @Test
    public void DiluvianPrimordialTest() {
        // Flying
        // When Diluvian Primordial enters the battlefield, for each opponent, you may cast up to one target instant or sorcery card from that player's graveyard without paying its mana cost. If a card cast this way would be put into a graveyard this turn, exile it instead.
        addCard(Zone.HAND, playerA, "Diluvian Primordial"); // {5}{U}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 7);

        addCard(Zone.GRAVEYARD, playerB, "Lightning Bolt");
        addCard(Zone.GRAVEYARD, playerC, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Diluvian Primordial");
        addTarget(playerA, "Lightning Bolt");
        addTarget(playerA, "Lightning Bolt");

        addTarget(playerA, playerB);
        addTarget(playerA, playerC);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Diluvian Primordial", 1);
        assertExileCount("Lightning Bolt", 2);
        assertLife(playerA, 40);
        assertLife(playerB, 37);
        assertLife(playerC, 37);
    }

}
