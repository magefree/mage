package org.mage.test.cards.single.m21;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class SeeTheTruthTest extends CardTestPlayerBase {

    // See the Truth {1}{U}
    // Sorcery
    //
    // Look at the top three cards of your library. Put one of those cards into your hand and the rest on the bottom of your library in any order. If this spell was cast from anywhere other than your hand, put each of those cards into your hand instead.
    private static final String seeTheTruth = "See the Truth";

    @Test
    public void castFromHand() {
        setStrictChooseMode(true);

        addCard(Zone.LIBRARY, playerA, "Centaur Courser");
        addCard(Zone.LIBRARY, playerA, "Bear Cub");
        addCard(Zone.LIBRARY, playerA, "Alpine Grizzly");
        skipInitShuffling();

        addCard(Zone.HAND, playerA, seeTheTruth);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, seeTheTruth);
        addTarget(playerA, "Bear Cub");         // card chosen to be put in hand

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, 1);

        assertLibraryCount(playerA, "Alpine Grizzly", 1);
        assertHandCount(playerA, "Bear Cub", 1);
        assertLibraryCount(playerA, "Centaur Courser", 1);
    }

    @Test
    public void castFromGraveyard() {
        setStrictChooseMode(true);

        addCard(Zone.LIBRARY, playerA, "Centaur Courser");
        addCard(Zone.LIBRARY, playerA, "Bear Cub");
        addCard(Zone.LIBRARY, playerA, "Alpine Grizzly");
        skipInitShuffling();

        addCard(Zone.GRAVEYARD, playerA, seeTheTruth);
        // Snapcaster Mage {1}{U}
        // Flash
        // When Snapcaster Mage enters the battlefield, target instant or sorcery card in your graveyard gains flashback until end of turn. The flashback cost is equal to its mana cost.
        addCard(Zone.HAND, playerA, "Snapcaster Mage");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Snapcaster Mage");
        addTarget(playerA, seeTheTruth);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Flashback", null, seeTheTruth);
        // Cast from graveyard, all 3 cards are going to hand. No choice involved.

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, 3);

        assertHandCount(playerA, "Alpine Grizzly", 1);
        assertHandCount(playerA, "Bear Cub", 1);
        assertHandCount(playerA, "Centaur Courser", 1);
    }

    @Test
    public void copyOnStack() {
        setStrictChooseMode(true);

        addCard(Zone.LIBRARY, playerA, "Feral Krushok");
        addCard(Zone.LIBRARY, playerA, "Enormous Baloth");
        addCard(Zone.LIBRARY, playerA, "Durkwood Boars");
        addCard(Zone.LIBRARY, playerA, "Centaur Courser");
        addCard(Zone.LIBRARY, playerA, "Bear Cub");
        addCard(Zone.LIBRARY, playerA, "Alpine Grizzly");
        skipInitShuffling();

        addCard(Zone.HAND, playerA, seeTheTruth);
        // Twincast {U}{U}
        // Instant
        //
        // Copy target instant or sorcery spell. You may choose new targets for the copy.
        addCard(Zone.HAND, playerA, "Twincast");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, seeTheTruth);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Twincast", seeTheTruth);
        addTarget(playerA, "Bear Cub");         // card chosen to be put in hand   -- for the copy.
        addTarget(playerA, "Enormous Baloth");  // card chosen to be put in hand   -- for the initial cast.

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, 2);

        assertLibraryCount(playerA, "Alpine Grizzly", 1);
        assertHandCount(playerA, "Bear Cub", 1);
        assertLibraryCount(playerA, "Centaur Courser", 1);
        assertLibraryCount(playerA, "Durkwood Boars", 1);
        assertHandCount(playerA, "Enormous Baloth", 1);
        assertLibraryCount(playerA, "Feral Krushok", 1);
    }

    @Test
    public void castCopyFromExile() {
        setStrictChooseMode(true);

        addCard(Zone.LIBRARY, playerA, "Centaur Courser");
        addCard(Zone.LIBRARY, playerA, "Bear Cub");
        addCard(Zone.LIBRARY, playerA, "Alpine Grizzly");
        skipInitShuffling();

        addCard(Zone.GRAVEYARD, playerA, "See the Truth");
        // Mizzix's Mastery {3}{R}
        // Sorcery
        //
        // Exile target card that’s an instant or sorcery from your graveyard. For each card exiled this way, copy it, and you may cast the copy without paying its mana cost. Exile Mizzix’s Mastery.
        // Overload {5}{R}{R}{R} (You may cast this spell for its overload cost. If you do, change “target” in its text to “each.”)
        addCard(Zone.HAND, playerA, "Mizzix's Mastery");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mizzix's Mastery", seeTheTruth);
        setChoice(playerA, true); // answering 'yes' to: you MAY cast the copy.
        // No card choice, the copy is cast so all 3 cards go to hand.

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, 3);

        assertHandCount(playerA, "Alpine Grizzly", 1);
        assertHandCount(playerA, "Bear Cub", 1);
        assertHandCount(playerA, "Centaur Courser", 1);
    }
}
