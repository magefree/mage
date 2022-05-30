
package org.mage.test.cards.triggers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Obstinate Baloth {2}{G}{G}
 * Creature - Beast
 * When Obstinate Baloth enters the battlefield, you gain 4 life.
 * If a spell or ability an opponent controls causes you to discard Obstinate Baloth,
 * put it onto the battlefield instead of putting it into your graveyard.
 *
 * @author LevelX2
 */
public class ObstinateBalothTest extends CardTestPlayerBase {

    @Test
    public void TestLifeGainIfForcedToDiscard() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        // Target player discards a card.
        addCard(Zone.HAND, playerA, "Raven's Crime");

        addCard(Zone.HAND, playerB, "Obstinate Baloth");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Raven's Crime", playerB);
        // Let choice be autopicked since there is only one option

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Raven's Crime", 1);
        assertPermanentCount(playerB, "Obstinate Baloth", 1);

        assertLife(playerA, 20);
        assertLife(playerB, 24); // + 4 from Obstinate Baloth entering the battlefield
    }

    /**
     * Obstinate Baloth enters the battlefield and will also be sacrificed during the resolution of Smallpox.
     * So it's triggered ability goes to the stack as the Obstinate Baloth has already left the battlefield again.
     * So EntersBattlefieldTriggeredAbility has to work in all zones as a consequence.
    */
    @Test
    public void TestWithSmallpox() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        // Each player loses 1 life, discards a card, sacrifices a creature, then sacrifices a land.
        addCard(Zone.HAND, playerA, "Smallpox");

        addCard(Zone.HAND, playerB, "Obstinate Baloth");

        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Smallpox");
        // playerB - Discard a card - Its ability will send it to the battlefield instead of the graveyard
        addTarget(playerB, "Obstinate Baloth");
        // playerB - Sacrifice a creature
        setChoice(playerB, "Obstinate Baloth");

        // playerA - Sacrifice a land
        setChoice(playerA, "Swamp");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Smallpox", 1);
        assertPermanentCount(playerB, "Obstinate Baloth", 0);
        assertGraveyardCount(playerB, "Obstinate Baloth", 1);

        assertLife(playerA, 19);
        assertLife(playerB, 23); // -1 from Smallpox + 4 from Obstinate Baloth entering the battlefield
    }
}
