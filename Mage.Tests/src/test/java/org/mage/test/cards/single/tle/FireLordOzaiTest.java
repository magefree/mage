package org.mage.test.cards.single.tle;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestMultiPlayerBaseWithRangeAll;

public class FireLordOzaiTest extends CardTestMultiPlayerBaseWithRangeAll {

    private static final String ozai = "Fire Lord Ozai";
    private static final String bears = "Grizzly Bears";
    private static final String lion = "Silvercoat Lion";
    private static final String tomb = "Ancient Tomb";
    private static final String bolt = "Lightning Bolt";
    private static final String elves = "Llanowar Elves";
    private static final String vanguard = "Elite Vanguard";
    private static final String memnite = "Memnite";

    @Test
    public void test_CastOneThenOthersNotPlayable() {
        setStrictChooseMode(true);
        skipInitShuffling();

        // Whenever Fire Lord Ozai attacks, you may sacrifice another creature. If you do, add an amount of {R} equal to the sacrificed creature's power. Until end of combat, you don't lose this mana as steps end.
        // {6}: Exile the top card of each opponent's library. Until end of turn, you may play one of those cards without paying its mana cost.
        addCard(Zone.BATTLEFIELD, playerA, ozai);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 6);
        addCard(Zone.LIBRARY, playerB, bears);
        addCard(Zone.LIBRARY, playerC, lion);
        addCard(Zone.LIBRARY, playerD, tomb);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{6}: Exile");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPlayableAbility("bears before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + bears, true);
        checkPlayableAbility("lion before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + lion, true);
        checkPlayableAbility("tomb before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Play " + tomb, true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bears);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPlayableAbility("lion after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + lion, false);
        checkPlayableAbility("tomb after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Play " + tomb, false);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, bears, 1);
        assertExileCount(playerC, lion, 1);
        assertExileCount(playerD, tomb, 1);
    }

    @Test
    public void test_CastAdventureThenOthersNotPlayable() {
        setStrictChooseMode(true);
        skipInitShuffling();

        // {6}: Exile the top card of each opponent's library. Until end of turn, you may play one of those cards without paying its mana cost.
        addCard(Zone.BATTLEFIELD, playerA, ozai);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 6);
        // Bonecrusher Giant {2}{R} / Stomp {1}{R}: Damage can't be prevented this turn. Stomp deals 2 damage to any target.
        addCard(Zone.LIBRARY, playerB, "Bonecrusher Giant");
        addCard(Zone.LIBRARY, playerC, lion);
        addCard(Zone.LIBRARY, playerD, tomb);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{6}: Exile");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Stomp", playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPlayableAbility("lion after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + lion, false);
        checkPlayableAbility("tomb after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Play " + tomb, false);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 20 - 2);
        assertExileCount(playerC, lion, 1);
        assertExileCount(playerD, tomb, 1);
    }

    @Test
    public void test_PlayLandThenOthersNotPlayable() {
        setStrictChooseMode(true);
        skipInitShuffling();

        // {6}: Exile the top card of each opponent's library. Until end of turn, you may play one of those cards without paying its mana cost.
        addCard(Zone.BATTLEFIELD, playerA, ozai);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 6);
        addCard(Zone.LIBRARY, playerB, bears);
        addCard(Zone.LIBRARY, playerC, lion);
        addCard(Zone.LIBRARY, playerD, tomb);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{6}: Exile");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, tomb);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPlayableAbility("bears after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + bears, false);
        checkPlayableAbility("lion after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + lion, false);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, tomb, 1);
        assertExileCount(playerB, bears, 1);
        assertExileCount(playerC, lion, 1);
    }

    @Test
    public void test_EachActivationAllowsOne() {
        setStrictChooseMode(true);
        skipInitShuffling();

        // {6}: Exile the top card of each opponent's library. Until end of turn, you may play one of those cards without paying its mana cost.
        addCard(Zone.BATTLEFIELD, playerA, ozai);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 12);
        addCard(Zone.LIBRARY, playerB, elves);
        addCard(Zone.LIBRARY, playerB, bears);
        addCard(Zone.LIBRARY, playerC, vanguard);
        addCard(Zone.LIBRARY, playerC, lion);
        addCard(Zone.LIBRARY, playerD, memnite);
        addCard(Zone.LIBRARY, playerD, tomb);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{6}: Exile");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bears);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // second activation allows one of the new cards, but not the old ones
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{6}: Exile");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPlayableAbility("lion after second", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + lion, false);
        checkPlayableAbility("vanguard after second", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + vanguard, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, elves);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPlayableAbility("vanguard after elves", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + vanguard, false);
        checkPlayableAbility("memnite after elves", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + memnite, false);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, bears, 1);
        assertPermanentCount(playerA, elves, 1);
        assertExileCount(playerC, lion, 1);
        assertExileCount(playerC, vanguard, 1);
        assertExileCount(playerD, tomb, 1);
        assertExileCount(playerD, memnite, 1);
    }

    @Test
    public void test_DurationEndsAtEndOfTurn() {
        setStrictChooseMode(true);
        skipInitShuffling();

        // {6}: Exile the top card of each opponent's library. Until end of turn, you may play one of those cards without paying its mana cost.
        addCard(Zone.BATTLEFIELD, playerA, ozai);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 6);
        addCard(Zone.LIBRARY, playerB, bolt);
        addCard(Zone.LIBRARY, playerC, lion);
        addCard(Zone.LIBRARY, playerD, tomb);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{6}: Exile");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPlayableAbility("bolt this turn", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + bolt, true);
        checkPlayableAbility("bolt next turn", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + bolt, false);

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertExileCount(playerB, bolt, 1);
    }
}
