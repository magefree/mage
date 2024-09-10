
package org.mage.test.cards.control;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class VengefulPharaohTest extends CardTestPlayerBase {

    @Test
    public void controlledByOtherBeforeGraveyardTriggerTest() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        // Deathtouch (Any amount of damage this deals to a creature is enough to destroy it.)
        // Whenever combat damage is dealt to you or a planeswalker you control, if Vengeful Pharaoh is in your graveyard, destroy target attacking creature, then put Vengeful Pharaoh on top of your library.
        addCard(Zone.HAND, playerA, "Vengeful Pharaoh", 1); // Creature 5/4 {2}{B}{B}{B}
        // Destroy target permanent.
        addCard(Zone.HAND, playerA, "Vindicate", 1); // Sorcery {1}{W}{B}

        // Enchant creature
        // You control enchanted creature.
        addCard(Zone.HAND, playerB, "Control Magic", 1); // Enchantment
        addCard(Zone.BATTLEFIELD, playerB, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Vengeful Pharaoh");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Control Magic", "Vengeful Pharaoh");

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Vindicate", "Vengeful Pharaoh");

        attack(4, playerB, "Silvercoat Lion");

        setStopAt(4, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Vindicate", 1);
        assertGraveyardCount(playerB, "Control Magic", 1);
        assertGraveyardCount(playerB, "Silvercoat Lion", 1);
        assertGraveyardCount(playerA, "Vengeful Pharaoh", 0);
        assertLibraryCount(playerA, "Vengeful Pharaoh", 1);

        assertLife(playerA, 18);
    }

    @Test
    public void doubleTriggerTest() {
        // If multiple creatures deal combat damage to you and to a planeswalker you control simultaneously,
        // Vengeful Pharaoh will trigger twice. The first trigger will cause Vengeful Pharaoh to be put on top of your
        // library. The second trigger will then do nothing, as Vengeful Pharaoh is no longer in your graveyard when it
        // tries to resolve. Note that the second trigger will do nothing even if Vengeful Pharaoh is put back into
        // your graveyard before it tries to resolve, as it’s a different Vengeful Pharaoh than the one that was there
        // before. (2011-09-22)

        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        // Deathtouch (Any amount of damage this deals to a creature is enough to destroy it.)
        // Whenever combat damage is dealt to you or a planeswalker you control, if Vengeful Pharaoh is in your
        // graveyard, destroy target attacking creature, then put Vengeful Pharaoh on top of your library.
        addCard(Zone.BATTLEFIELD, playerA, "Vengeful Pharaoh", 1); // Creature 5/4 {2}{B}{B}{B}

        // Legendary Planeswalker — Tibalt {R}{R}
        // +1: Draw a card, then discard a card at random.
        // −4: Tibalt, the Fiend-Blooded deals damage equal to the number of cards in target player’s hand to that player.
        // −6: Gain control of all creatures until end of turn. Untap them. They gain haste until end of turn.
        addCard(Zone.BATTLEFIELD, playerA, "Tibalt, the Fiend-Blooded", 1);

        // Destroy target permanent.
        addCard(Zone.HAND, playerA, "Vindicate", 1); // Sorcery {1}{W}{B}

        // 2/1
        addCard(Zone.BATTLEFIELD, playerB, "Expedition Envoy", 1);

        // 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Vindicate", "Vengeful Pharaoh");

        attack(2, playerB, "Expedition Envoy", playerA);
        attack(2, playerB, "Grizzly Bears", "Tibalt, the Fiend-Blooded");

        // choose trigger to go on the stack first
        setChoice(playerA, "Whenever combat damage");

        addTarget(playerA, "Expedition Envoy");
        addTarget(playerA, "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, currentGame.getStartingLife() - 2);
        assertLibraryCount(playerA, "Vengeful Pharaoh", 1);
        assertGraveyardCount(playerB, "Grizzly Bears", 1);
        assertPermanentCount(playerB, "Expedition Envoy", 1);
    }
}
