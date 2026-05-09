package org.mage.test.cards.single.mkc;

import mage.constants.EmptyNames;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;

import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class VeiledAscensionTest extends CardTestPlayerBase {

    /*
    Veiled Ascension
    {1}{U}
    Enchantment
    Face-down creatures you control enter the battlefield with a flying counter on them.
    At the beginning of your upkeep, you may cloak the top card of your library.
     */
    private static final String veiledAscension = "Veiled Ascension";

    @Test
    public void testCloakTheTopCardOfYourLibrary() {
        skipInitShuffling();
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, veiledAscension);
        addCard(Zone.LIBRARY, playerA, "Boltbender");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);
        setChoice(playerA, true); // yes to cloak

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        showBattlefield(ACTIVATE_ABILITY, 1, stopAtStep, playerA);

        execute();
    }

    @Test
    public void testPutAFlyingCounterOnFaceDownCreature() {
        skipInitShuffling();
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, veiledAscension);
        addCard(Zone.HAND, playerA, "Boltbender");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Boltbender using Disguise", true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, veiledAscension, true);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        showBattlefield(ACTIVATE_ABILITY, 1, stopAtStep, playerA);

        execute();

        assertCounterCount(playerA, EmptyNames.FACE_DOWN_CREATURE.getTestCommand(), CounterType.FLYING, 1);

    }

    @Test
    public void testFaceDownCreatureEnterWithFlyingCounter() {
        skipInitShuffling();
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, veiledAscension);
        addCard(Zone.HAND, playerA, "Boltbender");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);
        setChoice(playerA, false); // no to cloak


        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Boltbender using Disguise", true);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        showBattlefield(ACTIVATE_ABILITY, 1, stopAtStep, playerA);

        execute();

        assertCounterCount(playerA, EmptyNames.FACE_DOWN_CREATURE.getTestCommand(), CounterType.FLYING, 1);
    }
}
