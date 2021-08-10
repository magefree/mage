
package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class TributeTest extends CardTestPlayerBase {

    /**
     * When returning a Pharagax Giant from the graveyard to the battlefield via
     * the ability of Ink-Eyes, Servant of Oni the Tribute mechanic did not
     * trigger, even though it says 'enters the battlefield' and not 'cast' on
     * the card.
     */
    @Test
    public void testPharagaxGiant() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);

        // Ninjutsu {3}{B}{B} ({3}{B}{B}, Return an unblocked attacker you control to hand: Put this card onto the battlefield from your hand tapped and attacking.)
        // Whenever Ink-Eyes, Servant of Oni deals combat damage to a player, you may put target creature card from that player's graveyard onto the battlefield under your control.
        // {1}{B}: Regenerate Ink-Eyes.
        addCard(Zone.BATTLEFIELD, playerA, "Ink-Eyes, Servant of Oni"); // 5/4

        // Tribute 2 ((As this creature enters the battlefield, an opponent of your choice may put two +1/+1 counter on it.)
        // When Pharagax Giant enters the battlefield, if tribute wasn't paid, Pharagax Giant deals 5 damage to each opponent.
        addCard(Zone.GRAVEYARD, playerB, "Pharagax Giant"); // Creature 3/3

        attack(1, playerA, "Ink-Eyes, Servant of Oni");
        setChoice(playerB, true); // pay tribute

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertTapped("Ink-Eyes, Servant of Oni", true);
        assertLife(playerB, 15);

        assertPermanentCount(playerA, "Pharagax Giant", 1);
        assertPowerToughness(playerA, "Pharagax Giant", 5, 5);

    }

}
