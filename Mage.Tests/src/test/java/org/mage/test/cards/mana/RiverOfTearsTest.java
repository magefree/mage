package org.mage.test.cards.mana;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class RiverOfTearsTest extends CardTestPlayerBase {

    /**
     * River of Tears doesn't produce black mana any time. Gatherer says:
     *
     * 17/11/2017 The turn you play River of Tears, it will produce Black when
     * tapped for mana.
     *
     * 17/11/2017 River of Tears produces Black only after you've played a land,
     * not after you've put a land onto the battlefield (such as with Evolving
     * Wilds).
     */
    @Test
    public void testBlackAfterPlayed() {
        // {T}: Add {U}. If you played a land this turn, add {B} instead.
        addCard(Zone.HAND, playerA, "River of Tears", 1); // Land

        addCard(Zone.HAND, playerA, "Nightshade Stinger", 1); // Creature {B}

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "River of Tears");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Nightshade Stinger");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "River of Tears", 1);
        assertTapped("River of Tears", true);
        assertPermanentCount(playerA, "Nightshade Stinger", 1);
    }

    @Test
    public void testBlueInSecondTurn() {
        // {T}: Add {U}. If you played a land this turn, add {B} instead.
        addCard(Zone.HAND, playerA, "River of Tears", 1); // Land

        addCard(Zone.HAND, playerA, "Aven Envoy", 1); // Creature {B}

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "River of Tears");

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Aven Envoy");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "River of Tears", 1);
        assertTapped("River of Tears", true);
        assertPermanentCount(playerA, "Aven Envoy", 1);
    }
}
