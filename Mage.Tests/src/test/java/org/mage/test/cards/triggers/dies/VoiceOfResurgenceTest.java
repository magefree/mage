
package org.mage.test.cards.triggers.dies;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.filter.Filter;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class VoiceOfResurgenceTest extends CardTestPlayerBase {

    /**
     * Voice of Resurgence creates it's token under owner's control upon death
     * even when it is controlled by an opponent when it dies. Happened with
     * Treachery.
     *
     */
    @Test
    public void testDiesTriggeredAbility() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);
        // Enchant creature
        // When Treachery enters the battlefield, untap up to five lands.
        // You control enchanted creature.
        addCard(Zone.HAND, playerA, "Treachery"); // {3}{U}{U}

        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        // Whenever an opponent casts a spell during your turn or when Voice of Resurgence dies,
        // put a green and white Elemental creature token onto the battlefield with "This creature's power and toughness are each equal to the number of creatures you control."
        addCard(Zone.BATTLEFIELD, playerB, "Voice of Resurgence", 1); // 2/2
        addCard(Zone.HAND, playerB, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Treachery", "Voice of Resurgence");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", "Voice of Resurgence");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertGraveyardCount(playerA, "Treachery", 1);
        assertGraveyardCount(playerB, "Voice of Resurgence", 1);

        assertPermanentCount(playerA, "Elemental Token", 2); // one from the Lightning Bolt and one from the Voice of Resurgence dying
        assertPowerToughness(playerA, "Elemental Token", 3, 3, Filter.ComparisonScope.All);
        assertTappedCount("Island", true, 0);
    }

}
