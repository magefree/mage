package org.mage.test.cards.single.soi;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class GameTrailTest extends CardTestPlayerBase {

    @Test
    public void test_Reveal() {
        // As Game Trail enters the battlefield, you may reveal a Mountain or Forest card from your hand. If you don't, Game Trail enters the battlefield tapped.
        // {T}: Add {R} or {G}.
        addCard(Zone.HAND, playerA, "Game Trail", 1); // land
        addCard(Zone.HAND, playerA, "Forest", 1);
        addCard(Zone.HAND, playerA, "Mountain", 1);

        // play with reveal
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Game Trail");
        setChoice(playerA, true); // reveal
        setChoice(playerA, "Forest"); // reveal forest

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertTapped("Game Trail", false);
    }

    @Test
    public void test_RevealWithoutCards() {
        // As Game Trail enters the battlefield, you may reveal a Mountain or Forest card from your hand. If you don't, Game Trail enters the battlefield tapped.
        // {T}: Add {R} or {G}.
        addCard(Zone.HAND, playerA, "Game Trail", 1); // land
        addCard(Zone.HAND, playerA, "Swamp", 1);

        // play with reveal
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Game Trail");
        // no reveal choices

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertTapped("Game Trail", true);
    }

    @Test
    public void test_NotReveal() {
        // As Game Trail enters the battlefield, you may reveal a Mountain or Forest card from your hand. If you don't, Game Trail enters the battlefield tapped.
        // {T}: Add {R} or {G}.
        addCard(Zone.HAND, playerA, "Game Trail", 1); // land
        addCard(Zone.HAND, playerA, "Forest", 1);
        addCard(Zone.HAND, playerA, "Mountain", 1);

        // play with reveal
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Game Trail");
        setChoice(playerA, false); // no reveal

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertTapped("Game Trail", true);
    }

    @Test
    public void test_RevealAfterCultivate() {
        removeAllCardsFromLibrary(playerA);

        // As Game Trail enters the battlefield, you may reveal a Mountain or Forest card from your hand. If you don't, Game Trail enters the battlefield tapped.
        // {T}: Add {R} or {G}.
        addCard(Zone.HAND, playerA, "Game Trail", 1); // land
        addCard(Zone.LIBRARY, playerA, "Forest", 1);
        addCard(Zone.LIBRARY, playerA, "Island", 1);
        addCard(Zone.LIBRARY, playerA, "Swamp", 1);
        //
        // Search your library for up to two basic land cards, reveal those cards, and put one onto the battlefield tapped
        // and the other into your hand. Then shuffle your library.
        addCard(Zone.HAND, playerA, "Cultivate", 1); // {2}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);

        // put forest by cultivate
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cultivate");
        addTarget(playerA, "Forest^Swamp");
        setChoice(playerA, "Swamp"); // put tapped
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // play game trail
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Game Trail");
        setChoice(playerA, true); // reveal
        setChoice(playerA, "Forest");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertTapped("Game Trail", false);
    }
}
