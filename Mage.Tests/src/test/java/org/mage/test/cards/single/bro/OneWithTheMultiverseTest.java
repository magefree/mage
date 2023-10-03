package org.mage.test.cards.single.bro;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class OneWithTheMultiverseTest extends CardTestPlayerBase {

    /** One with the Multiverse
     * {6}{U}{U}
     * Enchantment
     *
     * You may look at the top card of your library any time.
     * You may play lands and cast spells from the top of your library.
     * Once during each of your turns, you may cast a spell from your hand or the top of your library without paying its mana cost.
     */
    private final String owtm = "One with the Multiverse";
    private final String ogre = "Gray Ogre"; // 2/2 {2}{R}
    private final String piker = "Goblin Piker"; // 2/1 {1}{R}

    @Test
    public void castFromTopForFree() {
        setStrictChooseMode(true);
        skipInitShuffling();

        // The "You may look at the top card of your library any time."
        // is not set up properly if starting directly on the battlefield.
        // So we do cast it in those tests.
        addCard(Zone.HAND, playerA, owtm);
        addCard(Zone.BATTLEFIELD, playerA, "Volcanic Island", 8);

        addCard(Zone.LIBRARY, playerA, ogre, 3);
        addCard(Zone.HAND, playerA, piker, 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, owtm, true);
        checkPlayableAbility("can cast for free", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Gray Ogre", true);
        checkPlayableAbility("can cast for free", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Goblin Piker", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ogre, true);
        setChoice(playerA, "Without paying manacost");
        checkPlayableAbility("can cast for free", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Gray Ogre", false);
        checkPlayableAbility("can cast for free", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Goblin Piker", false);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, ogre, 1);
        assertPermanentCount(playerA, piker, 0);
        assertLibraryCount(playerA, ogre, 2);
        assertHandCount(playerA, piker, 2);
    }

    @Test
    public void castFromHandForFree() {
        setStrictChooseMode(true);
        skipInitShuffling();

        // The "You may look at the top card of your library any time."
        // is not set up properly if starting directly on the battlefield.
        // So we do cast it in those tests.
        addCard(Zone.HAND, playerA, owtm);
        addCard(Zone.BATTLEFIELD, playerA, "Volcanic Island", 8);

        addCard(Zone.LIBRARY, playerA, ogre, 3);
        addCard(Zone.HAND, playerA, piker, 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, owtm, true);
        checkPlayableAbility("can cast for free", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Gray Ogre", true);
        checkPlayableAbility("can cast for free", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Goblin Piker", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, piker, true);
        setChoice(playerA, "Without paying manacost");
        checkPlayableAbility("can cast for free", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Gray Ogre", false);
        checkPlayableAbility("can cast for free", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Goblin Piker", false);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, ogre, 0);
        assertPermanentCount(playerA, piker, 1);
        assertLibraryCount(playerA, ogre, 3);
        assertHandCount(playerA, piker, 1);
    }

    @Test
    public void castFromTopForFreeThenNormalFromTop() {
        setStrictChooseMode(true);
        skipInitShuffling();

        // The "You may look at the top card of your library any time."
        // is not set up properly if starting directly on the battlefield.
        // So we do cast it in those tests.
        addCard(Zone.HAND, playerA, owtm);
        addCard(Zone.BATTLEFIELD, playerA, "Volcanic Island", 11);

        addCard(Zone.LIBRARY, playerA, ogre, 3);
        addCard(Zone.HAND, playerA, piker, 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, owtm, true);
        checkPlayableAbility("can cast for free", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Gray Ogre", true);
        checkPlayableAbility("can cast for free", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Goblin Piker", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ogre, true);
        setChoice(playerA, "Without paying manacost");
        checkPlayableAbility("can cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Gray Ogre", true);
        checkPlayableAbility("can cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Goblin Piker", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ogre, true);
        checkPlayableAbility("can't cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Gray Ogre", false);
        checkPlayableAbility("can't cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Goblin Piker", false);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, ogre, 2);
        assertPermanentCount(playerA, piker, 0);
        assertLibraryCount(playerA, ogre, 1);
        assertHandCount(playerA, piker, 2);
        assertTappedCount("Volcanic Island", true, 8 + 3);
    }

    @Test
    public void castFromTopForFreeThenNormalFromHand() {
        setStrictChooseMode(true);
        skipInitShuffling();

        // The "You may look at the top card of your library any time."
        // is not set up properly if starting directly on the battlefield.
        // So we do cast it in those tests.
        addCard(Zone.HAND, playerA, owtm);
        addCard(Zone.BATTLEFIELD, playerA, "Volcanic Island", 11);

        addCard(Zone.LIBRARY, playerA, ogre, 3);
        addCard(Zone.HAND, playerA, piker, 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, owtm, true);
        checkPlayableAbility("can cast for free", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Gray Ogre", true);
        checkPlayableAbility("can cast for free", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Goblin Piker", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ogre, true);
        setChoice(playerA, "Without paying manacost");
        checkPlayableAbility("can cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Gray Ogre", true);
        checkPlayableAbility("can cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Goblin Piker", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, piker, true);
        checkPlayableAbility("can't cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Gray Ogre", false);
        checkPlayableAbility("can't cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Goblin Piker", false);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, ogre, 1);
        assertPermanentCount(playerA, piker, 1);
        assertLibraryCount(playerA, ogre, 2);
        assertHandCount(playerA, piker, 1);
        assertTappedCount("Volcanic Island", true, 8 + 2);
    }

    @Test
    public void castFromHandForFreeThenNormalFromHand() {
        setStrictChooseMode(true);
        skipInitShuffling();

        // The "You may look at the top card of your library any time."
        // is not set up properly if starting directly on the battlefield.
        // So we do cast it in those tests.
        addCard(Zone.HAND, playerA, owtm);
        addCard(Zone.BATTLEFIELD, playerA, "Volcanic Island", 11);

        addCard(Zone.LIBRARY, playerA, ogre, 3);
        addCard(Zone.HAND, playerA, piker, 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, owtm, true);
        checkPlayableAbility("can cast for free", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Gray Ogre", true);
        checkPlayableAbility("can cast for free", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Goblin Piker", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, piker, true);
        setChoice(playerA, "Without paying manacost");
        checkPlayableAbility("can cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Gray Ogre", true);
        checkPlayableAbility("can cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Goblin Piker", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, piker, true);
        checkPlayableAbility("can't cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Gray Ogre", false);
        checkPlayableAbility("can't cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Goblin Piker", false);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, ogre, 0);
        assertPermanentCount(playerA, piker, 2);
        assertLibraryCount(playerA, ogre, 3);
        assertHandCount(playerA, piker, 0);
        assertTappedCount("Volcanic Island", true, 8 + 2);
    }

    @Test
    public void castFromHandForFreeThenNormalFromTop() {
        setStrictChooseMode(true);
        skipInitShuffling();

        // The "You may look at the top card of your library any time."
        // is not set up properly if starting directly on the battlefield.
        // So we do cast it in those tests.
        addCard(Zone.HAND, playerA, owtm);
        addCard(Zone.BATTLEFIELD, playerA, "Volcanic Island", 11);

        addCard(Zone.LIBRARY, playerA, ogre, 3);
        addCard(Zone.HAND, playerA, piker, 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, owtm, true);
        checkPlayableAbility("can cast for free", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Gray Ogre", true);
        checkPlayableAbility("can cast for free", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Goblin Piker", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, piker, true);
        setChoice(playerA, "Without paying manacost");
        checkPlayableAbility("can cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Gray Ogre", true);
        checkPlayableAbility("can cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Goblin Piker", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ogre, true);
        checkPlayableAbility("can't cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Gray Ogre", false);
        checkPlayableAbility("can't cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Goblin Piker", false);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, ogre, 1);
        assertPermanentCount(playerA, piker, 1);
        assertLibraryCount(playerA, ogre, 2);
        assertHandCount(playerA, piker, 1);
        assertTappedCount("Volcanic Island", true, 8 + 3);
    }

    @Test
    public void castNormalFromTopThenFreeFromHand() {
        setStrictChooseMode(true);
        skipInitShuffling();

        // The "You may look at the top card of your library any time."
        // is not set up properly if starting directly on the battlefield.
        // So we do cast it in those tests.
        addCard(Zone.HAND, playerA, owtm);
        addCard(Zone.BATTLEFIELD, playerA, "Volcanic Island", 11);

        addCard(Zone.LIBRARY, playerA, ogre, 3);
        addCard(Zone.HAND, playerA, piker, 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, owtm, true);
        checkPlayableAbility("can cast for free", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Gray Ogre", true);
        checkPlayableAbility("can cast for free", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Goblin Piker", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ogre, true);
        setChoice(playerA, owtm);
        checkPlayableAbility("can cast for free", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Gray Ogre", true);
        checkPlayableAbility("can cast for free", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Goblin Piker", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, piker, true);
        setChoice(playerA, "Without paying manacost");
        checkPlayableAbility("can't cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Gray Ogre", false);
        checkPlayableAbility("can't cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Goblin Piker", false);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, ogre, 1);
        assertPermanentCount(playerA, piker, 1);
        assertLibraryCount(playerA, ogre, 2);
        assertHandCount(playerA, piker, 1);
        assertTappedCount("Volcanic Island", true, 8 + 3);
    }

    @Test
    public void castNormalFromTopThenFreeFromTop() {
        setStrictChooseMode(true);
        skipInitShuffling();

        // The "You may look at the top card of your library any time."
        // is not set up properly if starting directly on the battlefield.
        // So we do cast it in those tests.
        addCard(Zone.HAND, playerA, owtm);
        addCard(Zone.BATTLEFIELD, playerA, "Volcanic Island", 11);

        addCard(Zone.LIBRARY, playerA, ogre, 3);
        addCard(Zone.HAND, playerA, piker, 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, owtm, true);
        checkPlayableAbility("can cast for free", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Gray Ogre", true);
        checkPlayableAbility("can cast for free", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Goblin Piker", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ogre, true);
        setChoice(playerA, owtm);
        checkPlayableAbility("can cast for free", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Gray Ogre", true);
        checkPlayableAbility("can cast for free", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Goblin Piker", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ogre, true);
        setChoice(playerA, "Without paying manacost");
        checkPlayableAbility("can't cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Gray Ogre", false);
        checkPlayableAbility("can't cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Goblin Piker", false);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, ogre, 2);
        assertPermanentCount(playerA, piker, 0);
        assertLibraryCount(playerA, ogre, 1);
        assertHandCount(playerA, piker, 2);
        assertTappedCount("Volcanic Island", true, 8 + 3);
    }

    @Test
    public void castNormalFromHandThenFreeFromHand() {
        setStrictChooseMode(true);
        skipInitShuffling();

        // The "You may look at the top card of your library any time."
        // is not set up properly if starting directly on the battlefield.
        // So we do cast it in those tests.
        addCard(Zone.HAND, playerA, owtm);
        addCard(Zone.BATTLEFIELD, playerA, "Volcanic Island", 11);

        addCard(Zone.LIBRARY, playerA, ogre, 3);
        addCard(Zone.HAND, playerA, piker, 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, owtm, true);
        checkPlayableAbility("can cast for free", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Gray Ogre", true);
        checkPlayableAbility("can cast for free", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Goblin Piker", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, piker, true);
        setChoice(playerA, piker);
        checkPlayableAbility("can cast for free", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Gray Ogre", true);
        checkPlayableAbility("can cast for free", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Goblin Piker", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, piker, true);
        setChoice(playerA, "Without paying manacost");
        checkPlayableAbility("can't cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Gray Ogre", false);
        checkPlayableAbility("can't cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Goblin Piker", false);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, ogre, 0);
        assertPermanentCount(playerA, piker, 2);
        assertLibraryCount(playerA, ogre, 3);
        assertHandCount(playerA, piker, 0);
        assertTappedCount("Volcanic Island", true, 8 + 2);
    }

    @Test
    public void castNormalFromHandThenFreeFromTop() {
        setStrictChooseMode(true);
        skipInitShuffling();

        // The "You may look at the top card of your library any time."
        // is not set up properly if starting directly on the battlefield.
        // So we do cast it in those tests.
        addCard(Zone.HAND, playerA, owtm);
        addCard(Zone.BATTLEFIELD, playerA, "Volcanic Island", 11);

        addCard(Zone.LIBRARY, playerA, ogre, 3);
        addCard(Zone.HAND, playerA, piker, 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, owtm, true);
        checkPlayableAbility("can cast for free", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Gray Ogre", true);
        checkPlayableAbility("can cast for free", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Goblin Piker", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, piker, true);
        setChoice(playerA, piker);
        checkPlayableAbility("can cast for free", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Gray Ogre", true);
        checkPlayableAbility("can cast for free", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Goblin Piker", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ogre, true);
        setChoice(playerA, "Without paying manacost");
        checkPlayableAbility("can't cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Gray Ogre", false);
        checkPlayableAbility("can't cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Goblin Piker", false);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, ogre, 1);
        assertPermanentCount(playerA, piker, 1);
        assertLibraryCount(playerA, ogre, 2);
        assertHandCount(playerA, piker, 1);
        assertTappedCount("Volcanic Island", true, 8 + 2);
    }
}
