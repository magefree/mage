package org.mage.test.cards.restriction;

import mage.constants.EmptyNames;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class MeddlingMageTest extends CardTestPlayerBase {

    //As Meddling Mage enters the battlefield, choose a nonland card name. Spells with the chosen name can't be cast.

    @Test
    public void testMeddlingMageDefaultScenario() {
        addCard(Zone.HAND, playerA, "Meddling Mage");
        addCard(Zone.HAND, playerA, "Savannah Lions");

        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        checkPlayableAbility("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Savannah Lions", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Meddling Mage");
        setChoice(playerA, "Savannah Lions"); // name a spell that can't be cast

        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPlayableAbility("after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Savannah Lions", false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Meddling Mage", 1);
        assertPermanentCount(playerA, "Savannah Lions", 0);
        assertHandCount(playerA, "Meddling Mage", 0);
        assertHandCount(playerA, "Savannah Lions", 1);
    }

    @Test
    public void testMeddlingMageIsochronScepterScenario() {
        addCard(Zone.HAND, playerA, "Meddling Mage");
        addCard(Zone.HAND, playerA, "Isochron Scepter");
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Meddling Mage");
        setChoice(playerA, "Lightning Bolt"); // name a spell that can't be cast
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Isochron Scepter");
        setChoice(playerA, true); // use imprint
        setChoice(playerA, "Lightning Bolt"); // target for imprint

        // copy and cast imprinted card
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{2}, {T}:");
        setChoice(playerA, true); // create copy
        setChoice(playerA, true); // cast copy
        addTarget(playerA, "Meddling Mage");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Meddling Mage", 1);
        assertPermanentCount(playerA, "Isochron Scepter", 1);
        assertExileCount("Lightning Bolt", 1);
        assertLife(playerA, 20);
        assertLife(playerB, 20);

    }

    @Test
    public void testMeddlingMageFaceDownCreature() {
        addCard(Zone.HAND, playerA, "Meddling Mage");
        addCard(Zone.HAND, playerA, "Ainok Tracker"); // red morph creature to prevent it casting from Islands and Plains

        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Meddling Mage");
        setChoice(playerA, "Ainok Tracker"); // name a spell that can't be cast

        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ainok Tracker");
        setChoice(playerA, true); // cast it face down as 2/2 creature

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Meddling Mage", 1);
        assertPermanentCount(playerA, EmptyNames.FACE_DOWN_CREATURE.toString(), 1);
        assertHandCount(playerA, "Meddling Mage", 0);
        assertHandCount(playerA, "Ainok Tracker", 0);
    }

    @Test
    public void testMeddlingMageFuseCardStopAndCastWell() {
        addCard(Zone.HAND, playerA, "Meddling Mage");

        // Create a 3/3 green Centaur creature token.
        // You gain 2 life for each creature you control.
        addCard(Zone.HAND, playerA, "Alive // Well"); // {3}{G} // {W}

        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Meddling Mage");
        setChoice(playerA, "Well"); // name a spell that can't be cast

        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPlayableAbility("all", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Alive", true);
        checkPlayableAbility("all", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Well", false);
        checkPlayableAbility("all", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "fused Alive // Well", false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();


        assertPermanentCount(playerA, "Meddling Mage", 1);
        assertHandCount(playerA, "Meddling Mage", 0);
        assertHandCount(playerA, "Alive // Well", 1);
        assertLife(playerA, 20);

    }

    @Test
    public void testMeddlingMageFuseCardStopAliveAndCastWell() {
        addCard(Zone.HAND, playerA, "Meddling Mage");

        // Create a 3/3 green Centaur creature token.
        // You gain 2 life for each creature you control.
        addCard(Zone.HAND, playerA, "Alive // Well"); // {3}{G} // {W}

        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Meddling Mage");
        setChoice(playerA, "Alive"); // name a spell that can't be cast

        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPlayableAbility("all", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Alive", false);
        checkPlayableAbility("all", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Well", true);
        checkPlayableAbility("all", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "fused Alive // Well", false);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Well");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Meddling Mage", 1);
        assertHandCount(playerA, "Meddling Mage", 0);
        assertHandCount(playerA, "Alive // Well", 0);
        assertLife(playerA, 22);
    }

    @Test
    public void testMeddlingMageFuseCardStopAliveAndCastFused() {
        addCard(Zone.HAND, playerA, "Meddling Mage");

        // Create a 3/3 green Centaur creature token.
        // You gain 2 life for each creature you control.
        addCard(Zone.HAND, playerA, "Alive // Well"); // {3}{G} // {W}

        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Meddling Mage");
        setChoice(playerA, "Alive"); // name a spell that can't be cast

        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPlayableAbility("all", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Alive", false);
        checkPlayableAbility("all", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Well", true);
        checkPlayableAbility("all", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "fused Alive // Well", false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Meddling Mage", 1);
        assertHandCount(playerA, "Meddling Mage", 0);

        assertHandCount(playerA, "Alive // Well", 1);
        assertLife(playerA, 20);
    }
}
