package org.mage.test.cards.control;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.replacement.YouControlYourOpponentsWhileSearchingReplacementEffect;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class TakeControlWhileSearchingLibraryTest extends CardTestPlayerBase {

    @Test
    public void test_SimpleSearchingLibrary_Normal() {
        removeAllCardsFromLibrary(playerA);
        removeAllCardsFromLibrary(playerB);

        addCard(Zone.LIBRARY, playerA, "Balduvian Bears", 1);
        addCard(Zone.LIBRARY, playerB, "Kitesail Corsair", 1);
        //
        // Search your library for up to three creature cards and put them into your graveyard. Then shuffle your library.
        addCard(Zone.HAND, playerA, "Buried Alive", 1); // {2}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);

        // before
        checkGraveyardCount("before a", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Balduvian Bears", 0);
        checkGraveyardCount("before b", 1, PhaseStep.PRECOMBAT_MAIN, playerB, "Kitesail Corsair", 0);

        // search as normal
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {B}", 3);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Buried Alive");
        addTarget(playerA, "Balduvian Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // after
        checkGraveyardCount("after a", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Balduvian Bears", 1);
        checkGraveyardCount("after b", 1, PhaseStep.PRECOMBAT_MAIN, playerB, "Kitesail Corsair", 0);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
    }

    @Test
    public void test_SimpleSearchingLibrary_TakeControl() {
        removeAllCardsFromLibrary(playerA);
        removeAllCardsFromLibrary(playerB);

        addCard(Zone.LIBRARY, playerA, "Balduvian Bears", 1);
        addCard(Zone.LIBRARY, playerB, "Kitesail Corsair", 1);
        //
        // Search your library for up to three creature cards and put them into your graveyard. Then shuffle your library.
        addCard(Zone.HAND, playerA, "Buried Alive", 1); // {2}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        //
        // You control your opponents while they’re searching their libraries.
        addCustomCardWithAbility("control", playerB, new SimpleStaticAbility(
                new YouControlYourOpponentsWhileSearchingReplacementEffect())
        );
        //
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        // before
        checkGraveyardCount("before a", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Balduvian Bears", 0);
        checkGraveyardCount("before b", 1, PhaseStep.PRECOMBAT_MAIN, playerB, "Kitesail Corsair", 0);

        // search under control of B
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {B}", 3);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Buried Alive");
        setChoice(playerB, true); // continue
        addTarget(playerB, "Balduvian Bears"); // player B must take control for searching
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // after
        checkGraveyardCount("after a", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Balduvian Bears", 1);
        checkGraveyardCount("after b", 1, PhaseStep.PRECOMBAT_MAIN, playerB, "Kitesail Corsair", 0);

        // check that control returned
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt");
        addTarget(playerA, playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
    }

    @Test
    public void test_CastCardWhileSearchingLibrary_Normal() {
        removeAllCardsFromLibrary(playerA);
        removeAllCardsFromLibrary(playerB);

        addCard(Zone.LIBRARY, playerA, "Balduvian Bears", 1);
        addCard(Zone.LIBRARY, playerB, "Kitesail Corsair", 1);
        //
        // While you're searching your library, you may cast Panglacial Wurm from your library.
        addCard(Zone.LIBRARY, playerA, "Panglacial Wurm", 1); // {5}{G}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Panglacial Wurm", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 7);
        //
        // Search your library for up to three creature cards and put them into your graveyard. Then shuffle your library.
        addCard(Zone.HAND, playerA, "Buried Alive", 1); // {2}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        //

        // before
        checkGraveyardCount("before a", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Balduvian Bears", 0);
        checkGraveyardCount("before b", 1, PhaseStep.PRECOMBAT_MAIN, playerB, "Kitesail Corsair", 0);

        // search as normal and cast
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {B}", 3);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Buried Alive");
        setChoice(playerA, true); // yes, try to cast a creature card from lib
        setChoice(playerA, "Panglacial Wurm"); // try to cast
        addTarget(playerA, "Balduvian Bears"); // choice for searching
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // after
        checkGraveyardCount("after a", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Balduvian Bears", 1);
        checkGraveyardCount("after b", 1, PhaseStep.PRECOMBAT_MAIN, playerB, "Kitesail Corsair", 0);
        checkPermanentCount("must cast Panglacial Wurm", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Panglacial Wurm", 2);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
    }

    @Test
    @Ignore // unsupported by unit tests, see test_UnderControlMustUseTestCommandsCorrectrly
    public void test_CastCardWhileSearchingLibrary_TakeControl() {
        /*
        [test control manually]
        // use case: login by p1 and p2 clients, cast Buried Alive -> control under p2 -> try cast Panglacial Wurm and search for Balduvian Bears
        library:p1:Balduvian Bears:1
        library:p2:Kitesail Corsair:1
        library:p1:Panglacial Wurm:1
        battlefield:p1:Panglacial Wurm:1
        battlefield:p1:Forest:7
        hand:p1:Buried Alive:1
        battlefield:p1:Swamp:3
        battlefield:p2:Opposition Agent:1
         */

        removeAllCardsFromLibrary(playerA);
        removeAllCardsFromLibrary(playerB);

        addCard(Zone.LIBRARY, playerA, "Balduvian Bears", 1);
        addCard(Zone.LIBRARY, playerB, "Kitesail Corsair", 1);
        //
        // While you're searching your library, you may cast Panglacial Wurm from your library.
        addCard(Zone.LIBRARY, playerA, "Panglacial Wurm", 1); // {5}{G}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Panglacial Wurm", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 7);
        //
        // Search your library for up to three creature cards and put them into your graveyard. Then shuffle your library.
        addCard(Zone.HAND, playerA, "Buried Alive", 1); // {2}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);

        // before
        checkGraveyardCount("before a", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Balduvian Bears", 0);
        checkGraveyardCount("before b", 1, PhaseStep.PRECOMBAT_MAIN, playerB, "Kitesail Corsair", 0);

        // search under control of B and cast under B too
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {B}", 3);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Buried Alive");
        setChoice(playerB, true); // continue
        setChoice(playerB, true); // yes, try to cast a creature card from lib
        setChoice(playerB, "Panglacial Wurm"); // try to cast
        addTarget(playerB, "Balduvian Bears"); // choice for searching
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // after
        checkGraveyardCount("after a", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Balduvian Bears", 1);
        checkGraveyardCount("after b", 1, PhaseStep.PRECOMBAT_MAIN, playerB, "Kitesail Corsair", 0);
        checkPermanentCount("must cast Panglacial Wurm", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Panglacial Wurm", 2);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
    }

    @Test
    @Ignore
    // TODO: current "take player under control" implemented in GameController and HumanPlayer,
    //  not "game part" - so tests and AI ignore it and must be tested manually
    //  see another problems with control in HumanPlayer.priority(Game game) and https://github.com/magefree/mage/issues/2088
    public void test_UnderControlMustUseTestCommandsCorrectrly() {
        // {4}, {T}, Sacrifice Mindslaver: You control target player during that player's next turn.
        addCard(Zone.BATTLEFIELD, playerA, "Mindslaver", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        //
        addCard(Zone.HAND, playerB, "Lightning Bolt", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);

        // activate and take control
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{4}, {T}");
        addTarget(playerA, playerB);

        // check control
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt");
        addTarget(playerA, playerB);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();
    }

    @Test
    public void test_OppositionAgent() {
        // You control your opponents while they’re searching their libraries.
        // While an opponent is searching their library, they exile each card they find. You may play those cards
        // for as long as they remain exiled, and you may spend mana as though it were mana of any color to cast them.
        addCard(Zone.BATTLEFIELD, playerB, "Opposition Agent", 1);
        //
        // Search your library for up to three creature cards and put them into your graveyard. Then shuffle your library.
        addCard(Zone.HAND, playerA, "Buried Alive", 1); // {2}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        //
        addCard(Zone.LIBRARY, playerA, "Balduvian Bears", 1); // {1}{G}
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 2); // B can cast green bear for red mana

        // before
        checkPermanentCount("before a", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Balduvian Bears", 0);
        checkPermanentCount("before b", 1, PhaseStep.PRECOMBAT_MAIN, playerB, "Balduvian Bears", 0);

        // start searching under B (bears must go to exile instead graveyard)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Buried Alive");
        setChoice(playerB, true); // continue after new control
        addTarget(playerB, "Balduvian Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkGraveyardCount("after grave a", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Balduvian Bears", 0);
        checkGraveyardCount("after grave b", 1, PhaseStep.PRECOMBAT_MAIN, playerB, "Balduvian Bears", 0);
        checkExileCount("after exile", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Balduvian Bears", 1);

        // B can cast green bear for red mana
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Balduvian Bears");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, "Balduvian Bears", 1);
    }
}
