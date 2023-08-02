package org.mage.test.cards.single.cmm;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class DemonOfFatesDesignTest extends CardTestPlayerBase {

    // Demon of Fate's Design
    // {4}{B}{B}
    // Enchantment Creature — Demon
    //
    // Flying, trample
    // Once during each of your turns, you may cast an enchantment spell by paying life equal to its mana value rather than paying its mana cost.
    // {2}{B}, Sacrifice another enchantment: Demon of Fate’s Design gets +X/+0 until end of turn, where X is the sacrificed enchantment’s mana value.
    private static final String demon = "Demon of Fate's Design";

    @Test
    public void CastForLife() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, demon);

        addCard(Zone.HAND, playerA, "Glorious Anthem"); // Enchantment {1}{W}{W}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Glorious Anthem");
        setChoice(playerA, true); // yes to alt cast

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Glorious Anthem", 1);
        assertLife(playerA, 20 - 3);
    }

    @Test
    public void SayNoToTheDemon() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, demon);

        addCard(Zone.HAND, playerA, "Glorious Anthem"); // Enchantment {1}{W}{W}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Glorious Anthem");
        setChoice(playerA, false); // no to alt cast

        boolean hadError = false;
        try {
            setStopAt(1, PhaseStep.BEGIN_COMBAT);
            execute();
        } catch (AssertionError e) {
            hadError = true;
            assert e.getMessage().equals("Can't find ability to activate command: Cast Glorious Anthem");
        } finally {
            assert hadError;
        }
    }

    @Test
    public void CantCastBothInSameTurn() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, demon);

        addCard(Zone.HAND, playerA, "Glorious Anthem"); // Enchantment {1}{W}{W}
        addCard(Zone.HAND, playerA, "Absolute Law"); // Enchantment {1}{W}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Glorious Anthem");
        setChoice(playerA, true); // yes to alt cast

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Glorious Anthem", 1);
        assertLife(playerA, 20 - 3);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Absolute Law");

        boolean hadError = false;
        try {
            setStopAt(1, PhaseStep.END_TURN);
            execute();
        } catch (AssertionError e) {
            hadError = true;
            assert e.getMessage().equals("Can't find ability to activate command: Cast Absolute Law");
        } finally {
            assert hadError;
        }
    }

    @Test
    public void CantCastBothInDifferentTurn() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, demon, 1);

        addCard(Zone.HAND, playerA, "Glorious Anthem"); // Enchantment {1}{W}{W}
        addCard(Zone.HAND, playerA, "Absolute Law"); // Enchantment {1}{W}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Glorious Anthem");
        setChoice(playerA, true); // yes to alt cast

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Glorious Anthem", 1);
        assertLife(playerA, 20 - 3);

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Absolute Law");
        setChoice(playerA, true); // yes to alt cast

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Absolute Law", 1);
        assertLife(playerA, 20 - 3 - 2);
    }

    @Test
    public void BlinkTest() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, demon);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);

        // Instant {W}
        // Exile target creature you control, then return that card to the battlefield under your control.
        addCard(Zone.HAND, playerA, "Cloudshift");
        addCard(Zone.HAND, playerA, "Glorious Anthem"); // Enchantment {1}{W}{W}
        addCard(Zone.HAND, playerA, "Absolute Law"); // Enchantment {1}{W}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Glorious Anthem", true);
        setChoice(playerA, true); // yes to alt cast
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cloudshift", demon, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Absolute Law");
        setChoice(playerA, true); // yes to alt cast

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Glorious Anthem", 1);
        assertPermanentCount(playerA, "Absolute Law", 1);
        assertLife(playerA, 20 - 3 - 2);
    }

    @Test
    public void UnsubstantiateTest() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, demon, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        // Instant {U}{U}
        // Return target spell or creature to its owner’s hand.
        addCard(Zone.HAND, playerA, "Unsubstantiate");
        addCard(Zone.HAND, playerA, "Glorious Anthem"); // Enchantment {1}{W}{W}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Glorious Anthem");
        setChoice(playerA, true); // yes to alt cast
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Unsubstantiate", "Glorious Anthem");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, "Glorious Anthem", 1);
        assertLife(playerA, 20 - 3);

        // Did not keep the alt cost from the first cast
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Glorious Anthem");

        boolean hadError = false;
        try {
            setStopAt(1, PhaseStep.END_TURN);
            execute();
        } catch (AssertionError e) {
            hadError = true;
            assert e.getMessage().equals("Can't find ability to activate command: Cast Glorious Anthem");
        } finally {
            assert hadError;
        }
    }

    @Test
    public void DoubleDemonDoubleCast() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, demon, 2);

        addCard(Zone.HAND, playerA, "Glorious Anthem"); // Enchantment {1}{W}{W}
        addCard(Zone.HAND, playerA, "Absolute Law"); // Enchantment {1}{W}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Glorious Anthem", true);
        setChoice(playerA, false); // no to the first one
        setChoice(playerA, true); // yes to second one
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Absolute Law");
        setChoice(playerA, true); // yes to first one

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Glorious Anthem", 1);
        assertPermanentCount(playerA, "Absolute Law", 1);
        assertLife(playerA, 20 - 3 - 2);
    }

    //118.9a Only one alternative cost can be applied to any one spell as it’s being cast.
    @Test
    public void DoubleDemon() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, demon, 2);

        addCard(Zone.HAND, playerA, "Glorious Anthem"); // Enchantment {1}{W}{W}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Glorious Anthem");
        setChoice(playerA, true); // yes to alt cast of first demon
        setChoice(playerA, true); // yes to alt cast of second demon ???

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Glorious Anthem", 1);
        assertLife(playerA, 20 - 3 - 3);
    }
}
