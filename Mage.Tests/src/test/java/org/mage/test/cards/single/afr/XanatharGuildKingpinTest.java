package org.mage.test.cards.single.afr;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class XanatharGuildKingpinTest extends CardTestPlayerBase {

    @Test
    public void test_Play() {
        removeAllCardsFromLibrary(playerA);
        removeAllCardsFromLibrary(playerB);
        skipInitShuffling();

        // At the beginning of your upkeep, choose target opponent.
        // Until end of turn, that player can’t cast spells, you may look at the top card of their library any time,
        // you may play the top card of their library, and you may spend mana as though it were mana of any color
        // to cast spells this way.
        addCard(Zone.BATTLEFIELD, playerA, "Xanathar, Guild Kingpin");
        //
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        //
        addCard(Zone.HAND, playerA, "Thunderbolt");
        //
        addCard(Zone.HAND, playerB, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 2);
        //
        addCard(Zone.LIBRARY, playerB, "Mountain");
        addCard(Zone.LIBRARY, playerB, "Lightning Bolt");
        addCard(Zone.LIBRARY, playerB, "Grizzly Bears");

        // activate on opponent
        addTarget(playerA, playerB);

        // B can't cast spells
        checkPlayableAbility("B can't cast", 1, PhaseStep.PRECOMBAT_MAIN, playerB, "Cast Lightning Bolt", false);
        // A can cast own and from B library
        checkPlayableAbility("A can cast own", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Thunderbolt", true);
        checkPlayableAbility("A can cast from B", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Grizzly Bears", true);

        // cast from B and try another one with any color and full stack
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {B}", 2); // pay for {G} as any
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", null, "Grizzly Bears");
        addTarget(playerA, playerB); // bolt
        checkStackSize("multi cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 2);

        // B can cast again
        checkPlayableAbility("B can cast", 2, PhaseStep.PRECOMBAT_MAIN, playerB, "Cast Lightning Bolt", true);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_PlayLand() {
        setStrictChooseMode(true);
        skipInitShuffling();

        // At the beginning of your upkeep, choose target opponent.
        // Until end of turn, that player can’t cast spells, you may look at the top card of their library any time,
        // you may play the top card of their library, and you may spend mana as though it were mana of any color
        // to cast spells this way.
        addCard(Zone.BATTLEFIELD, playerA, "Xanathar, Guild Kingpin");
        addCard(Zone.LIBRARY, playerB, "Taiga");

        // activate on opponent
        addTarget(playerA, playerB);

        // play from B
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Taiga");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Taiga", 1);
    }

    @Test
    public void test_CastSeveralFromTopWithAnyColor() {
        setStrictChooseMode(true);
        removeAllCardsFromLibrary(playerB);
        skipInitShuffling();

        // At the beginning of your upkeep, choose target opponent.
        // Until end of turn, that player can’t cast spells, you may look at the top card of their library any time,
        // you may play the top card of their library, and you may spend mana as though it were mana of any color
        // to cast spells this way.
        addCard(Zone.BATTLEFIELD, playerA, "Xanathar, Guild Kingpin");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        //
        addCard(Zone.LIBRARY, playerB, "Island");
        addCard(Zone.LIBRARY, playerB, "Silvercoat Lion"); // {1}{W}
        addCard(Zone.LIBRARY, playerB, "Llanowar Elves"); // {G}
        addCard(Zone.LIBRARY, playerB, "Grizzly Bears"); // {1}{G}

        // activate on opponent
        addTarget(playerA, playerB);

        // first card
        checkPlayableAbility("can cast first", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Grizzly Bears", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // second card
        checkPlayableAbility("can cast second", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Llanowar Elves", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Llanowar Elves");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // third card
        checkPlayableAbility("can cast third", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Silvercoat Lion", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Silvercoat Lion");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertPermanentCount(playerA, "Llanowar Elves", 1);
        assertPermanentCount(playerA, "Silvercoat Lion", 1);
    }

    @Test
    public void test_CastFromTopWithAnyColorAfterPlayLand() {
        setStrictChooseMode(true);
        removeAllCardsFromLibrary(playerB);
        skipInitShuffling();

        // At the beginning of your upkeep, choose target opponent.
        // Until end of turn, that player can’t cast spells, you may look at the top card of their library any time,
        // you may play the top card of their library, and you may spend mana as though it were mana of any color
        // to cast spells this way.
        addCard(Zone.BATTLEFIELD, playerA, "Xanathar, Guild Kingpin");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        //
        addCard(Zone.LIBRARY, playerB, "Island");
        addCard(Zone.LIBRARY, playerB, "Grizzly Bears"); // {1}{G}
        addCard(Zone.LIBRARY, playerB, "Plains");

        // activate on opponent
        addTarget(playerA, playerB);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Plains");
        checkPlayableAbility("can cast after land", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Grizzly Bears", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Plains", 1);
        assertPermanentCount(playerA, "Grizzly Bears", 1);
    }

    @Test
    public void test_CastFromTopWithAnyColorOnNextTurn() {
        setStrictChooseMode(true);
        removeAllCardsFromLibrary(playerB);
        skipInitShuffling();

        // At the beginning of your upkeep, choose target opponent.
        // Until end of turn, that player can’t cast spells, you may look at the top card of their library any time,
        // you may play the top card of their library, and you may spend mana as though it were mana of any color
        // to cast spells this way.
        addCard(Zone.BATTLEFIELD, playerA, "Xanathar, Guild Kingpin");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        //
        addCard(Zone.HAND, playerB, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        //
        addCard(Zone.LIBRARY, playerB, "Island");
        addCard(Zone.LIBRARY, playerB, "Llanowar Elves"); // {G}
        addCard(Zone.LIBRARY, playerB, "Island"); // drawn by B on turn 2
        addCard(Zone.LIBRARY, playerB, "Grizzly Bears"); // {1}{G}

        // activate on opponent on turn 1
        addTarget(playerA, playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears");

        // B can cast on own turn
        checkPlayableAbility("B can cast", 2, PhaseStep.PRECOMBAT_MAIN, playerB, "Cast Lightning Bolt", true);

        // activate on opponent on turn 3
        addTarget(playerA, playerB);
        checkPlayableAbility("B can't cast", 3, PhaseStep.PRECOMBAT_MAIN, playerB, "Cast Lightning Bolt", false);
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Llanowar Elves");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertPermanentCount(playerA, "Llanowar Elves", 1);
    }

    @Test
    public void test_CastLastCardOfLibraryWithAnyColor() {
        setStrictChooseMode(true);
        removeAllCardsFromLibrary(playerB);
        skipInitShuffling();

        // At the beginning of your upkeep, choose target opponent.
        // Until end of turn, that player can’t cast spells, you may look at the top card of their library any time,
        // you may play the top card of their library, and you may spend mana as though it were mana of any color
        // to cast spells this way.
        addCard(Zone.BATTLEFIELD, playerA, "Xanathar, Guild Kingpin");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        //
        addCard(Zone.LIBRARY, playerB, "Grizzly Bears"); // {1}{G}

        // activate on opponent
        addTarget(playerA, playerB);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 1);
    }
}
