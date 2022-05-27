package org.mage.test.cards.single.znc;

import javafx.geometry.Pos;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Whispersteel Dagger
 * {2}{B}
 * Artifact — Equipment
 * Equipped creature gets +2/+0.
 * Whenever equipped creature deals combat damage to a player,
 * you may cast a creature spell from that player’s graveyard this turn,
 * and you may spend mana as though it were mana of any color to cast that spell.
 *
 * Equip {3}
 *
 * @author TheElk801
 */
public class WhispersteelDaggerTest extends CardTestPlayerBase {

    private static final String dagger = "Whispersteel Dagger";
    private static final String goblin = "Raging Goblin";
    private static final String bear = "Grizzly Bears";
    private static final String lion = "Silvercoat Lion";
    private static final String forest = "Forest";

    @Test
    public void testDagger() {
        addCard(Zone.BATTLEFIELD, playerA, dagger);
        addCard(Zone.BATTLEFIELD, playerA, forest, 7);
        addCard(Zone.BATTLEFIELD, playerA, goblin);

        addCard(Zone.GRAVEYARD, playerA, bear);               // 2 mana

        addCard(Zone.GRAVEYARD, playerB, lion, 2);      // 2 mana

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", goblin); // 3 mana (4 left)

        attack(1, playerA, goblin, playerB);

        // The Grizzly Bears shouldn't be playable since they are in playerA's graveyard not playerB's.
        checkPlayableAbility("Bear not playable", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast Grizzly", false);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, lion); // 2 mana (2 left)
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN);

        // The 2nd lion in playerB's graveyard is not playable since Whispersteel Dagger lets you play one card.
        checkPlayableAbility("Lion playable", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast Silvercoat", false);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, lion, 1); // The one cast

        assertLife(playerB, 20 - 3);
    }
}
