package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2, JayDi85
 */
public class OverloadTest extends CardTestPlayerBase {

    /**
     * My opponent cast an overloaded Vandalblast, and Xmage would not let me
     * cast Mental Misstep on it.
     * <p>
     * The CMC of a card never changes, and Vandalblast's CMC is always 1.
     * <p>
     * 4/15/2013 Casting a spell with overload doesn't change that spell's mana
     * cost. You just pay the overload cost instead.
     */
    @Test
    public void test_CastByOverloadDoesNotChangeCMC() {
        // Destroy target artifact you don't control.
        // Overload {4}{R} (You may cast this spell for its overload cost. If you do, change its text by replacing all instances of "target" with "each.")
        addCard(Zone.HAND, playerA, "Vandalblast");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        //
        // Counter target spell with converted mana cost 1.
        addCard(Zone.HAND, playerB, "Mental Misstep", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerB, "War Horn", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Vandalblast with overload");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Mental Misstep", "Vandalblast");
        setChoice(playerB, false); // pay mana instead life

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAllCommandsUsed();

        assertGraveyardCount(playerA, "Vandalblast", 1);
        assertGraveyardCount(playerB, "Mental Misstep", 1);
        assertPermanentCount(playerB, "War Horn", 2);
    }

    @Test
    public void test_CyclonicRift_NormalPlay() {
        // Return target nonland permanent you don't control to its owner's hand.
        // Overload {6}{U} (You may cast this spell for its overload cost. If you do, change its text
        // by replacing all instances of "target" with "each.")
        addCard(Zone.HAND, playerA, "Cyclonic Rift"); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 7);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Spectral Bears", 1);

        checkPlayableAbility("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Cyclonic Rift", true);
        checkPlayableAbility("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Cyclonic Rift with overload", true);

        // cast and remove 1 target
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cyclonic Rift");
        addTarget(playerA, "Balduvian Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertGraveyardCount(playerA, "Cyclonic Rift", 1);
        assertPermanentCount(playerB, "Swamp", 1);
        assertHandCount(playerB, "Balduvian Bears", 1);
        assertPermanentCount(playerB, "Spectral Bears", 1);
    }

    @Test
    public void test_CyclonicRift_OverloadPlay() {
        // Return target nonland permanent you don't control to its owner's hand.
        // Overload {6}{U} (You may cast this spell for its overload cost. If you do, change its text
        // by replacing all instances of "target" with "each.")
        addCard(Zone.HAND, playerA, "Cyclonic Rift"); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 7);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Spectral Bears", 1);

        checkPlayableAbility("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Cyclonic Rift", true);
        checkPlayableAbility("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Cyclonic Rift with overload", true);

        // cast and remove all possible targets (all bears)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cyclonic Rift with overload");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertGraveyardCount(playerA, "Cyclonic Rift", 1);
        assertPermanentCount(playerB, "Swamp", 1);
        assertHandCount(playerB, "Balduvian Bears", 1);
        assertHandCount(playerB, "Spectral Bears", 1);
    }

    @Test
    public void test_CyclonicRift_CantUseAlternativeSpellOnFreeCast() {
        // bug: https://github.com/magefree/mage/issues/6925
        // Casting Overloaded Cyclonic Rift with Isochron Scepter - This should not be possible,
        // You can't pay two alternate costs for the same thing.

        // Return target nonland permanent you don't control to its owner's hand.
        // Overload {6}{U} (You may cast this spell for its overload cost. If you do, change its text
        // by replacing all instances of "target" with "each.")
        addCard(Zone.HAND, playerA, "Cyclonic Rift"); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 7);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Spectral Bears", 1);
        //
        // Imprint - When Isochron Scepter enters the battlefield, you may exile an instant card with converted mana cost 2 or less from your hand.
        // {2}, {T}: You may copy the exiled card. If you do, you may cast the copy without paying its mana cost.
        addCard(Zone.HAND, playerA, "Isochron Scepter"); // {2}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        // prepare scepter for imprint
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Isochron Scepter");
        setChoice(playerA, true); // use imprint
        setChoice(playerA, "Cyclonic Rift");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // cast rift for free
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}, {T}:");
        setChoice(playerA, true); // create copy
        setChoice(playerA, true); // use free cast
        //setChoice(playerA, "Cast Cyclonic Rift with overload"); // must be NO choices, cause only normal cast allows here
        addTarget(playerA, "Balduvian Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertExileCount(playerA, "Cyclonic Rift", 1);
        assertGraveyardCount(playerA, "Cyclonic Rift", 0); // imprinted copy discarded
        assertPermanentCount(playerB, "Swamp", 1);
        assertHandCount(playerB, "Balduvian Bears", 1);
        assertPermanentCount(playerB, "Spectral Bears", 1);
    }
}
