
package org.mage.test.cards.triggers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class DayOfTheDragonsTest extends CardTestPlayerBase {

    @Test
    public void TestTokensAreCreated() {
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Pillarfield Ox", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 7);
        // When Day of the Dragons enters the battlefield, exile all creatures you control. Then put that many 5/5 red Dragon creature tokens with flying onto the battlefield.
        // When Day of the Dragons leaves the battlefield, sacrifice all Dragons you control. Then return the exiled cards to the battlefield under your control.
        addCard(Zone.HAND, playerA, "Day of the Dragons"); // Enchantment - {4}{U}{U}{U}

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerB, "Shivan Dragon");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Day of the Dragons");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertExileCount("Silvercoat Lion", 1);
        assertExileCount("Pillarfield Ox", 1);
        assertPermanentCount(playerA, "Dragon Token", 2);

        assertPermanentCount(playerB, "Silvercoat Lion", 1);
        assertPermanentCount(playerB, "Shivan Dragon", 1);
    }

    @Test
    public void TestTokensAreCreatedAndExiled() {
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Pillarfield Ox", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 7);
        // When Day of the Dragons enters the battlefield, exile all creatures you control. Then put that many 5/5 red Dragon creature tokens with flying onto the battlefield.
        // When Day of the Dragons leaves the battlefield, sacrifice all Dragons you control. Then return the exiled cards to the battlefield under your control.
        addCard(Zone.HAND, playerA, "Day of the Dragons"); // Enchantment - {4}{U}{U}{U}

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerB, "Shivan Dragon");
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);
        addCard(Zone.HAND, playerB, "Disenchant");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Day of the Dragons");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Disenchant", "Day of the Dragons");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Day of the Dragons", 1);
        assertGraveyardCount(playerB, "Disenchant", 1);

        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertPermanentCount(playerA, "Pillarfield Ox", 1);
        assertPermanentCount(playerA, "Dragon Token", 0);

        assertPermanentCount(playerB, "Silvercoat Lion", 1);
        assertPermanentCount(playerB, "Shivan Dragon", 1);
    }
}
