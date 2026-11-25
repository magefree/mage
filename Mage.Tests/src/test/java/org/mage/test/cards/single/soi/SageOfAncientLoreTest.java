package org.mage.test.cards.single.soi;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author MaxFreedomPollard
 */
public class SageOfAncientLoreTest extends CardTestPlayerBase {

    /**
     * Sage of Ancient Lore {4}{G}
     * Creature — Human Shaman Werewolf
     * Sage of Ancient Lore's power and toughness are each equal to the number of cards in your hand.
     * When Sage of Ancient Lore enters the battlefield, draw a card.
     * At the beginning of each upkeep, if no spells were cast last turn, transform Sage of Ancient Lore.
     * <p>
     * Werewolf of Ancient Hunger
     * Creature — Werewolf
     * Vigilance, trample
     * Werewolf of Ancient Hunger's power and toughness are each equal to the total number of cards in all players' hands.
     * At the beginning of each upkeep, if a player cast two or more spells last turn, transform Werewolf of Ancient Hunger.
     */
    private static final String sage = "Sage of Ancient Lore";
    private static final String werewolf = "Werewolf of Ancient Hunger";

    @Test
    public void testFrontFacePowerToughness() {
        addCard(Zone.BATTLEFIELD, playerA, sage);
        addCard(Zone.HAND, playerA, "Island", 3);
        addCard(Zone.HAND, playerB, "Island", 2);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        // 3 cards in player A's hand
        assertPermanentCount(playerA, sage, 1);
        assertPowerToughness(playerA, sage, 3, 3);
    }

    @Test
    public void testBackFacePowerToughness() {
        addCard(Zone.BATTLEFIELD, playerA, sage);
        addCard(Zone.HAND, playerA, "Island", 3);
        addCard(Zone.HAND, playerB, "Island", 2);

        // no spells were cast on turn 1, so it transforms at the beginning of turn 2 upkeep
        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        // 3 cards in player A's hand + 2 cards and the turn 2 draw in player B's hand
        assertPermanentCount(playerA, sage, 0);
        assertPermanentCount(playerA, werewolf, 1);
        assertPowerToughness(playerA, werewolf, 6, 6);
    }
}
