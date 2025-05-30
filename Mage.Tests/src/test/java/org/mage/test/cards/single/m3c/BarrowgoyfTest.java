package org.mage.test.cards.single.m3c;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class BarrowgoyfTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.b.Barrowgoyf Barrowgoyf} {2}{B}
     * Deathtouch, Lifelink
     * Barrowgoyf's power is equal to the number of card types among cards in all graveyards and its toughness is equal to that number plus 1.
     * Whenever Barrowgoyf deals combat damage to a player, you may mill that many cards. If you do, you may put a creature card from among them into your hand.
     * * / 1+*
     */
    private static final String barrowgoyf = "Barrowgoyf";

    @Test
    public void test_Simple() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, barrowgoyf);
        addCard(Zone.GRAVEYARD, playerB, "Bitterblossom"); // Tribal Enchantment
        addCard(Zone.LIBRARY, playerA, "Baneslayer Angel");
        addCard(Zone.LIBRARY, playerA, "Plains");

        attack(1, playerA, barrowgoyf, playerB);
        setChoice(playerA, true);
        setChoice(playerA, "Baneslayer Angel"); // return this to hand.

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 20 - 2);
        assertGraveyardCount(playerA, 1);
        assertHandCount(playerA, "Baneslayer Angel", 1);
        assertPowerToughness(playerA, barrowgoyf, 3, 4);
    }

    @Test
    public void test_Simple_NoMill() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, barrowgoyf);
        addCard(Zone.GRAVEYARD, playerB, "Bitterblossom"); // Tribal Enchantment
        addCard(Zone.LIBRARY, playerA, "Baneslayer Angel");
        addCard(Zone.LIBRARY, playerA, "Plains");

        attack(1, playerA, barrowgoyf, playerB);
        setChoice(playerA, false);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 20 - 2);
        assertGraveyardCount(playerA, 0);
        assertPowerToughness(playerA, barrowgoyf, 2, 3);
    }

    @Test
    public void test_Simple_NoReturn() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, barrowgoyf);
        addCard(Zone.GRAVEYARD, playerB, "Bitterblossom"); // Tribal Enchantment
        addCard(Zone.LIBRARY, playerA, "Baneslayer Angel");
        addCard(Zone.LIBRARY, playerA, "Plains");

        attack(1, playerA, barrowgoyf, playerB);
        setChoice(playerA, true);
        setChoice(playerA, TestPlayer.CHOICE_SKIP); // decide to not return.

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 20 - 2);
        assertGraveyardCount(playerA, 2);
        assertPowerToughness(playerA, barrowgoyf, 4, 5);
    }

    @Test
    public void test_Simple_CantReturn() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, barrowgoyf);
        addCard(Zone.GRAVEYARD, playerB, "Bitterblossom"); // Tribal Enchantment
        addCard(Zone.GRAVEYARD, playerB, "Grizzly Bears");
        addCard(Zone.LIBRARY, playerA, "Brainstorm");
        addCard(Zone.LIBRARY, playerA, "Plains");
        addCard(Zone.LIBRARY, playerA, "Invasion of Zendikar");

        attack(1, playerA, barrowgoyf, playerB);
        setChoice(playerA, true);
        setChoice(playerA, TestPlayer.CHOICE_SKIP); // decide to not return. There was no choice anyway.

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 20 - 3);
        assertGraveyardCount(playerA, 3);
        assertPowerToughness(playerA, barrowgoyf, 6, 7);
    }
}
