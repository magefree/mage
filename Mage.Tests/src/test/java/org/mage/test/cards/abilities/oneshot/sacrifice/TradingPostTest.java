
package org.mage.test.cards.abilities.oneshot.sacrifice;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class TradingPostTest extends CardTestPlayerBase {

    /**
     * Trading Post doesn't let me sacrifice a creature owned by my opponent,
     * but controlled by me. I get an error message saying You cannot sacrifice
     * this creature.
     */
    @Test
    public void testSacrifice() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        // {1}, {T}, Discard a card: You gain 4 life.
        // {1}, {T}, Pay 1 life: Create a 0/1 white Goat creature token onto the battlefield.
        // {1}, {T}, Sacrifice a creature: Return target artifact card from your graveyard to your hand.
        // {1}, {T}, Sacrifice an artifact: Draw a card.
        addCard(Zone.BATTLEFIELD, playerA, "Trading Post", 1);
        // Gain control of target creature until end of turn. Untap that creature. It gains haste until end of turn. (It can attack and this turn.)
        addCard(Zone.HAND, playerA, "Act of Treason"); // Sorcery {2}{R}
        addCard(Zone.GRAVEYARD, playerA, "Helm of Possession");

        addCard(Zone.BATTLEFIELD, playerB, "Savannah Lions");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Act of Treason", "Savannah Lions");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}, {T}, Sacrifice a creature", "Helm of Possession", "Act of Treason", StackClause.WHILE_NOT_ON_STACK);
        setChoice(playerA, "Savannah Lions");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Act of Treason", 1);

        assertPermanentCount(playerB, "Savannah Lions", 0);
        assertGraveyardCount(playerB, "Savannah Lions", 1);

        assertTapped("Trading Post", true);
        assertHandCount(playerA, 1);

    }
}
