package org.mage.test.cards.single.who;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class TheDayOfTheDoctorTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.t.TheDayOfTheDoctor The Day of the Doctor} {3}{R}{W}
     * Enchantment — Saga
     * I, II, III — Exile cards from the top of your library until you exile a legendary card. You may play that card for as long as this Saga remains on the battlefield. Put the rest of those exiled cards on the bottom of your library in a random order.
     * IV — Choose up to three Doctors. You may exile all other creatures. If you do, this Saga deals 13 damage to you.
     */
    private static final String day = "The Day of the Doctor";

    @Test
    public void test_SimplePlay() {
        skipInitShuffling();
        addCard(Zone.BATTLEFIELD, playerA, "Yawgmoth's Bargain"); // skip draw step
        addCard(Zone.LIBRARY, playerA, "Academy Ruins");
        addCard(Zone.LIBRARY, playerA, "Arcade Gannon");
        addCard(Zone.LIBRARY, playerA, "Memnite", 4);
        addCard(Zone.LIBRARY, playerA, "Thalia, Guardian of Thraben");
        addCard(Zone.BATTLEFIELD, playerA, "The Ninth Doctor"); // is a doctor

        addCard(Zone.HAND, playerA, day, 1);

        addCard(Zone.BATTLEFIELD, playerA, "Plateau", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, day);

        checkExileCount("after I, Thalia exiled", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Thalia, Guardian of Thraben", 1);
        checkExileCount("after I, Arcade Gannon not exiled", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Arcade Gannon", 0);
        checkExileCount("after I, Academy Ruins not exiled", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Academy Ruins", 0);

        // turn 3
        checkExileCount("after II, Thalia exiled", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Thalia, Guardian of Thraben", 1);
        checkExileCount("after II, Arcade Gannon exiled", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Arcade Gannon", 1);
        checkExileCount("after II, Academy Ruins not exiled", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Academy Ruins", 0);

        // turn 5
        waitStackResolved(5, PhaseStep.PRECOMBAT_MAIN);
        checkExileCount("after III, Thalia exiled", 5, PhaseStep.PRECOMBAT_MAIN, playerA, "Thalia, Guardian of Thraben", 1);
        checkExileCount("after III, Arcade Gannon exiled", 5, PhaseStep.PRECOMBAT_MAIN, playerA, "Arcade Gannon", 1);
        checkExileCount("after III, Academy Ruins not exiled", 5, PhaseStep.PRECOMBAT_MAIN, playerA, "Academy Ruins", 1);

        playLand(5, PhaseStep.PRECOMBAT_MAIN, playerA, "Academy Ruins");
        castSpell(5, PhaseStep.PRECOMBAT_MAIN, playerA, "Thalia, Guardian of Thraben");
        checkPlayableAbility("after III: can play Arcade Gannon", 5, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast Arcade Gannon", true);

        // turn 7
        setChoice(playerA, "The Ninth Doctor");
        setChoice(playerA, true); // choose to exile others
        checkPlayableAbility("after IV: can not play Arcade Gannon", 7, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast Arcade Gannon", false);

        setStrictChooseMode(true);
        setStopAt(7, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerA, "Thalia, Guardian of Thraben", 1);
        assertPermanentCount(playerA, "The Ninth Doctor", 1);
        assertPermanentCount(playerA, "Academy Ruins", 1);
        assertExileCount(playerA, "Arcade Gannon", 1);
        assertLife(playerA, 20 - 13);
    }
}
