
package org.mage.test.cards.single.clu;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class SludgeTitanTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.s.SludgeTitan Sludge Titan} {4}{B/G}{B/G}
     * Creature — Zombie Giant
     * Trample
     * Whenever Sludge Titan enters the battlefield or attacks, mill five cards. You may put a creature card and/or a land card from among them into your hand.
     * 6/6
     */
    private static final String titan = "Sludge Titan";

    private static final String piker = "Goblin Piker";      // creature 1
    private static final String vanguard = "Elite Vanguard"; // creature 2
    private static final String divination = "Divination";   // sorcery
    private static final String dryad = "Dryad Arbor";       // land creature
    private static final String plateau = "Plateau";         // land 1
    private static final String savannah = "Savannah";       // land 2

    // When Golgari Brownscale is put into your hand from your graveyard, you gain 2 life.
    private static final String brownscale = "Golgari Brownscale";

    @Test
    public void testNoValidChoice() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, titan);
        addCard(Zone.LIBRARY, playerA, divination, 5);

        attack(1, playerA, titan, playerB);
        setChoice(playerA, playerA.CHOICE_SKIP);

        setStopAt(1, PhaseStep.DECLARE_BLOCKERS);
        execute();

        assertGraveyardCount(playerA, divination, 5);
    }

    @Test
    public void testNoValidChoiceInvalid() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, titan);
        addCard(Zone.LIBRARY, playerA, divination, 5);

        attack(1, playerA, titan, playerB);
        setChoice(playerA, divination); // invalid, titan doesn't allow for choosing sorcery

        setStopAt(1, PhaseStep.DECLARE_BLOCKERS);

        try {
            execute();
            Assert.fail("must throw exception on execute");
        } catch (Throwable e) {
            if (!e.getMessage().startsWith("Missing CHOICE def")) {
                Assert.fail("Unexpected exception " + e.getMessage());
            }
        }
    }

    @Test
    public void testCreatureOnly_ChooseNone() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, titan);
        addCard(Zone.LIBRARY, playerA, piker, 5);

        attack(1, playerA, titan, playerB);
        setChoice(playerA, playerA.CHOICE_SKIP);

        setStopAt(1, PhaseStep.DECLARE_BLOCKERS);
        execute();

        assertGraveyardCount(playerA, piker, 5);
    }

    @Test
    public void testCreatureOnly_ChooseOne() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, titan);
        addCard(Zone.LIBRARY, playerA, piker, 5);

        attack(1, playerA, titan, playerB);
        setChoice(playerA, piker);

        setStopAt(1, PhaseStep.DECLARE_BLOCKERS);
        execute();

        assertGraveyardCount(playerA, piker, 4);
        assertHandCount(playerA, piker, 1);
    }

    @Ignore
    // The test suite does not fully validate the choice, and accept it. Work properly live preventing such choice.
    @Test
    public void testCreatureOnly_ChooseTwoInvalid() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, titan);
        addCard(Zone.LIBRARY, playerA, piker, 3);
        addCard(Zone.LIBRARY, playerA, vanguard, 2);

        attack(1, playerA, titan, playerB);
        setChoice(playerA, piker + "^" + vanguard); // invalid, titan doesn't allow for choosing 2 creatures

        setStopAt(1, PhaseStep.DECLARE_BLOCKERS);

        try {
            execute();
            Assert.fail("must throw exception on execute");
        } catch (Throwable e) {
            if (!e.getMessage().startsWith("Missing CHOICE def")) {
                Assert.fail("Unexpected exception " + e.getMessage());
            }
        }
    }

    @Test
    public void testBoth_ChooseTwo() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, titan);
        addCard(Zone.LIBRARY, playerA, piker);
        addCard(Zone.LIBRARY, playerA, vanguard);
        addCard(Zone.LIBRARY, playerA, divination);
        addCard(Zone.LIBRARY, playerA, savannah);
        addCard(Zone.LIBRARY, playerA, plateau);

        attack(1, playerA, titan, playerB);
        setChoice(playerA, piker + "^" + savannah);

        setStopAt(1, PhaseStep.DECLARE_BLOCKERS);
        execute();

        assertGraveyardCount(playerA, 3);
        assertHandCount(playerA, savannah, 1);
        assertHandCount(playerA, piker, 1);
    }

    @Test
    public void testBoth_ChooseTwo_DryadArbor() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, titan);
        addCard(Zone.LIBRARY, playerA, dryad, 5);

        attack(1, playerA, titan, playerB);
        setChoice(playerA, dryad + "^" + dryad);

        setStopAt(1, PhaseStep.DECLARE_BLOCKERS);
        execute();

        assertGraveyardCount(playerA, dryad, 3);
        assertHandCount(playerA, dryad, 2);
    }

    @Ignore
    // The test suite does not fully validate the choice, and accept it. Work properly live preventing selecting 3 dryads.
    @Test
    public void testBoth_ChooseThree_DryadArbor_Invalid() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, titan);
        addCard(Zone.LIBRARY, playerA, dryad, 5);

        attack(1, playerA, titan, playerB);
        setChoice(playerA, dryad + "^" + dryad + "^" + dryad); // invalid, titan doesn't allow for choosing a creature and/or a land

        setStopAt(1, PhaseStep.DECLARE_BLOCKERS);

        try {
            execute();
            Assert.fail("must throw exception on execute");
        } catch (Throwable e) {
            if (!e.getMessage().startsWith("Missing CHOICE def")) {
                Assert.fail("Unexpected exception " + e.getMessage());
            }
        }
    }

    @Test
    public void test_Brownscale_triggers() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, titan);
        addCard(Zone.LIBRARY, playerA, brownscale, 5);

        attack(1, playerA, titan, playerB);
        setChoice(playerA, brownscale);

        setStopAt(1, PhaseStep.DECLARE_BLOCKERS);
        execute();

        assertGraveyardCount(playerA, brownscale, 4);
        assertHandCount(playerA, brownscale, 1);
        assertLife(playerA, 20 + 2);
    }
}
