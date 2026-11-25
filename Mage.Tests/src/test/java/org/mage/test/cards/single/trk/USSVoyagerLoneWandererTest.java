package org.mage.test.cards.single.trk;

import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author DejviFiala
 */
public class USSVoyagerLoneWandererTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.u.USSVoyagerLoneWanderer}
     * <p>
     * {3}{G} Legendary Artifact - Spacecraft<br>
     * When U.S.S. Voyager enters, reveal cards from the top of your library until you reveal two land cards.
     * Put those land cards onto the battlefield tapped and the rest on the bottom of your library in a random order.<br>
     * Station<br>
     * STATION 8+<br>
     * Flying, vigilance<br>
     * 3/3
     */
    private static final String VOYAGER = "U.S.S. Voyager, Lone Wanderer";

    @Test
    public void test_Enters_TwoLandsTappedRestOnBottom() {
        addCard(Zone.HAND, playerA, VOYAGER);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        removeAllCardsFromLibrary(playerA);
        skipInitShuffling();
        // library from bottom to top (last added is on top)
        addCard(Zone.LIBRARY, playerA, "Plains"); // not revealed, stays and becomes the top card
        addCard(Zone.LIBRARY, playerA, "Island"); // second land, stop here
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears");
        addCard(Zone.LIBRARY, playerA, "Swamp"); // first land
        addCard(Zone.LIBRARY, playerA, "Memnite");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, VOYAGER);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, VOYAGER, 1);
        assertType(VOYAGER, CardType.CREATURE, false);
        assertPermanentCount(playerA, "Swamp", 1);
        assertPermanentCount(playerA, "Island", 1);
        // entered tapped, untapped on turn 3
        assertTapped("Swamp", false);
        assertTapped("Island", false);
        // Plains was the top card after the trigger, the rest went to the bottom
        assertHandCount(playerA, "Plains", 1);
        assertHandCount(playerA, 1);
        assertLibraryCount(playerA, "Memnite", 1);
        assertLibraryCount(playerA, "Grizzly Bears", 1);
        assertLibraryCount(playerA, 2);
    }

    @Test
    public void test_Enters_LandsEnterTapped() {
        addCard(Zone.HAND, playerA, VOYAGER);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        removeAllCardsFromLibrary(playerA);
        skipInitShuffling();
        addCard(Zone.LIBRARY, playerA, "Island");
        addCard(Zone.LIBRARY, playerA, "Swamp");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, VOYAGER);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertTapped("Swamp", true);
        assertTapped("Island", true);
        assertLibraryCount(playerA, 0);
    }

    @Test
    public void test_Enters_OnlyOneLandInLibrary() {
        addCard(Zone.HAND, playerA, VOYAGER);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        removeAllCardsFromLibrary(playerA);
        skipInitShuffling();
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears");
        addCard(Zone.LIBRARY, playerA, "Island");
        addCard(Zone.LIBRARY, playerA, "Memnite");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, VOYAGER);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Island", 1);
        assertTapped("Island", true);
        assertLibraryCount(playerA, "Memnite", 1);
        assertLibraryCount(playerA, "Grizzly Bears", 1);
        assertLibraryCount(playerA, 2);
    }

    @Test
    public void test_Enters_NoLandsInLibrary() {
        addCard(Zone.HAND, playerA, VOYAGER);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        removeAllCardsFromLibrary(playerA);
        skipInitShuffling();
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears");
        addCard(Zone.LIBRARY, playerA, "Memnite");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, VOYAGER);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, VOYAGER, 1);
        assertPermanentCount(playerA, 4 + 1);
        assertLibraryCount(playerA, "Memnite", 1);
        assertLibraryCount(playerA, "Grizzly Bears", 1);
        assertLibraryCount(playerA, 2);
    }

    @Test
    public void test_Enters_MdfcWithLandBackIsNotALand() {
        addCard(Zone.HAND, playerA, VOYAGER);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        removeAllCardsFromLibrary(playerA);
        skipInitShuffling();
        addCard(Zone.LIBRARY, playerA, "Swamp");
        addCard(Zone.LIBRARY, playerA, "Island");
        // modal double-faced card: only its front face (sorcery) counts in the library
        addCard(Zone.LIBRARY, playerA, "Turntimber Symbiosis");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, VOYAGER);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Island", 1);
        assertPermanentCount(playerA, "Swamp", 1);
        assertLibraryCount(playerA, "Turntimber Symbiosis", 1);
        assertLibraryCount(playerA, 1);
    }

    @Test
    public void test_Station8_FlyingVigilanceCreature() {
        addCard(Zone.BATTLEFIELD, playerA, VOYAGER);
        addCard(Zone.BATTLEFIELD, playerA, "Colossal Dreadmaw"); // 6/6
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // 2/2

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Station");
        setChoice(playerA, "Colossal Dreadmaw");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        // 6 counters: not a creature yet
        runCode("not a creature at 6", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            assertType(VOYAGER, CardType.CREATURE, false);
        });
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Station");
        setChoice(playerA, "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        attack(1, playerA, VOYAGER, playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertCounterCount(playerA, VOYAGER, CounterType.CHARGE, 8);
        assertType(VOYAGER, CardType.CREATURE, true);
        assertPowerToughness(playerA, VOYAGER, 3, 3);
        assertAbility(playerA, VOYAGER, FlyingAbility.getInstance(), true);
        assertAbility(playerA, VOYAGER, VigilanceAbility.getInstance(), true);
        assertTapped(VOYAGER, false);
        assertLife(playerB, 20 - 3);
    }
}
