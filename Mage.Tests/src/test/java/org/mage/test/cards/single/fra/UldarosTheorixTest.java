package org.mage.test.cards.single.fra;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author DarioBarrabinoDev
 */
public class UldarosTheorixTest extends CardTestPlayerBase {

    /**
     * Uldaros Theorix {3}{U}{B}{B}
     * Legendary Creature — Elder Sphinx 5/5
     * Flying
     * When Uldaros Theorix enters, if you cast him, exile up to one target nonland card of each card type from your
     * graveyard. Copy those cards. You may cast any number of spells with total mana value 6 or less from among the
     * copies without paying their mana costs. (Permanent spells cast this way become tokens.)
     */
    @Test
    public void test_ExileOneCardOfEachCardType() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.HAND, playerA, "Uldaros Theorix");
        addCard(Zone.GRAVEYARD, playerA, "Gray Ogre"); // creature, {2}{R}
        addCard(Zone.GRAVEYARD, playerA, "Shock"); // instant, {R}
        addCard(Zone.GRAVEYARD, playerA, "Propaganda"); // enchantment, {2}{U}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Uldaros Theorix");
        // one target of each card type: creature, instant and enchantment
        addTarget(playerA, "Gray Ogre^Shock^Propaganda");
        setChoice(playerA, TestPlayer.CHOICE_SKIP); // cast nothing for free
        setChoice(playerA, false); // stop the free casting

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Uldaros Theorix", 1);
        assertExileCount(playerA, "Gray Ogre", 1);
        assertExileCount(playerA, "Shock", 1);
        assertExileCount(playerA, "Propaganda", 1);
        assertGraveyardCount(playerA, 0);
    }

    /**
     * Uldaros Theorix {3}{U}{B}{B}
     * Legendary Creature — Elder Sphinx 5/5
     * Flying
     * When Uldaros Theorix enters, if you cast him, exile up to one target nonland card of each card type from your
     * graveyard. Copy those cards. You may cast any number of spells with total mana value 6 or less from among the
     * copies without paying their mana costs. (Permanent spells cast this way become tokens.)
     */
    @Test
    public void test_CanNotExileTwoCardsOfTheSameCardType() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.HAND, playerA, "Uldaros Theorix");
        addCard(Zone.GRAVEYARD, playerA, "Gray Ogre"); // creature, {2}{R}
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears"); // creature too, so only one of them can be targeted

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Uldaros Theorix");
        // the second creature card is not a legal target anymore, so only Gray Ogre is exiled
        addTarget(playerA, "Gray Ogre^Grizzly Bears");
        setChoice(playerA, true); // cast the copy of Gray Ogre for free

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerA, "Gray Ogre", 1);
        assertGraveyardCount(playerA, "Grizzly Bears", 1);
        assertPermanentCount(playerA, "Gray Ogre", 1); // the copy was cast as a token
    }

    /**
     * Uldaros Theorix {3}{U}{B}{B}
     * Legendary Creature — Elder Sphinx 5/5
     * Flying
     * When Uldaros Theorix enters, if you cast him, exile up to one target nonland card of each card type from your
     * graveyard. Copy those cards. You may cast any number of spells with total mana value 6 or less from among the
     * copies without paying their mana costs. (Permanent spells cast this way become tokens.)
     */
    @Test
    public void test_FreeCastsAreLimitedToTotalManaValueOfSix() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.HAND, playerA, "Uldaros Theorix");
        addCard(Zone.GRAVEYARD, playerA, "Divination"); // sorcery, {2}{U}, draw two cards
        addCard(Zone.GRAVEYARD, playerA, "Gray Ogre"); // creature, {2}{R}
        addCard(Zone.GRAVEYARD, playerA, "Shock"); // instant, {R}, would bring the total to 7

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Uldaros Theorix");
        // one target of each card type: sorcery, creature and instant
        addTarget(playerA, "Divination^Gray Ogre^Shock");
        setChoice(playerA, "Divination"); // free cast 1 of 2, mana value 3
        setChoice(playerA, true); // confirm the free cast
        setChoice(playerA, "Gray Ogre"); // free cast 2 of 2, mana value 3, total of 6
        setChoice(playerA, true); // confirm the free cast

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, 2); // drawn by the copy of Divination
        assertPermanentCount(playerA, "Gray Ogre", 1);
        assertLife(playerB, 20); // the copy of Shock was over the budget, so it could not be cast
        assertExileCount(playerA, "Shock", 1); // the original card stays exiled
    }

    /**
     * Uldaros Theorix {3}{U}{B}{B}
     * Legendary Creature — Elder Sphinx 5/5
     * Flying
     * When Uldaros Theorix enters, if you cast him, exile up to one target nonland card of each card type from your
     * graveyard. Copy those cards. You may cast any number of spells with total mana value 6 or less from among the
     * copies without paying their mana costs. (Permanent spells cast this way become tokens.)
     */
    @Test
    public void test_PermanentCastThisWayIsAToken() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerA, "Uldaros Theorix");
        addCard(Zone.HAND, playerA, "Lightning Bolt"); // {R}, kills the 2/2 token
        addCard(Zone.GRAVEYARD, playerA, "Gray Ogre"); // creature, {2}{R}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Uldaros Theorix");
        addTarget(playerA, "Gray Ogre"); // the only nonland card in the graveyard
        setChoice(playerA, true); // cast the copy of Gray Ogre for free

        // on a later turn, so that the lands tapped for Uldaros Theorix can't leave the bolt without red mana
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt");
        addTarget(playerA, "Gray Ogre"); // the token on the battlefield

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Gray Ogre", 0);
        assertGraveyardCount(playerA, "Gray Ogre", 0); // a token ceases to exist instead of going to the graveyard
        assertExileCount(playerA, "Gray Ogre", 1); // only the original card is still in exile
    }

    /**
     * Uldaros Theorix {3}{U}{B}{B}
     * Legendary Creature — Elder Sphinx 5/5
     * Flying
     * When Uldaros Theorix enters, if you cast him, exile up to one target nonland card of each card type from your
     * graveyard. Copy those cards. You may cast any number of spells with total mana value 6 or less from among the
     * copies without paying their mana costs. (Permanent spells cast this way become tokens.)
     */
    @Test
    public void test_NoLegalTargetsDoesNothing() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.HAND, playerA, "Uldaros Theorix");
        addCard(Zone.GRAVEYARD, playerA, "Forest"); // land card, so it can't be targeted

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Uldaros Theorix");
        addTarget(playerA, TestPlayer.TARGET_SKIP); // the trigger is put on the stack with no targets

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Uldaros Theorix", 1);
        assertGraveyardCount(playerA, "Forest", 1);
        assertExileCount(playerA, 0);
    }

    /**
     * Uldaros Theorix {3}{U}{B}{B}
     * Legendary Creature — Elder Sphinx 5/5
     * Flying
     * When Uldaros Theorix enters, if you cast him, exile up to one target nonland card of each card type from your
     * graveyard. Copy those cards. You may cast any number of spells with total mana value 6 or less from among the
     * copies without paying their mana costs. (Permanent spells cast this way become tokens.)
     */
    @Test
    public void test_DoesNotTriggerWhenPutOntoBattlefieldWithoutBeingCast() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.HAND, playerA, "Zombify"); // {3}{B}, return target creature card from your graveyard
        addCard(Zone.GRAVEYARD, playerA, "Uldaros Theorix");
        addCard(Zone.GRAVEYARD, playerA, "Gray Ogre"); // creature, {2}{R}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Zombify");
        addTarget(playerA, "Uldaros Theorix");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Uldaros Theorix", 1);
        assertGraveyardCount(playerA, "Gray Ogre", 1); // the trigger did not happen, so nothing was exiled
        assertExileCount(playerA, 0);
    }
}
