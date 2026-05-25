package org.mage.test.cards.single.nem;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author xenohedron
 */
public class ParallaxNexusTest extends CardTestPlayerBase {

    private static final String nexus = "Parallax Nexus";
    // Fading 5 (This enchantment enters with five fade counters on it. At the beginning of your upkeep, remove a fade counter from it. If you can’t, sacrifice it.)
    // Remove a fade counter from this enchantment: Target opponent exiles a card from their hand. Activate only as a sorcery.
    // When this enchantment leaves the battlefield, each player returns to their hand all cards they own exiled with it.
    private static final String crab = "Fortress Crab";
    private static final String wurm = "Craw Wurm";
    private static final String bear = "Runeclaw Bear";
    private static final String hexmage = "Vampire Hexmage"; // sac to remove counters
    private static final String conscripts = "Zealous Conscripts"; // temporary steal on ETB

    @Test
    public void testExileAndReturn() {
        addCard(Zone.HAND, playerA, nexus);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.HAND, playerA, crab);
        addCard(Zone.HAND, playerB, wurm);
        addCard(Zone.HAND, playerB, bear);
        addCard(Zone.BATTLEFIELD, playerB, hexmage);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 5);
        addCard(Zone.HAND, playerB, conscripts);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, nexus);
        checkPermanentCounters("5 fade", 1, PhaseStep.BEGIN_COMBAT, playerA, nexus, CounterType.FADE, 5);

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Remove a fade");
        addTarget(playerA, playerB);
        addTarget(playerB, bear);

        checkExileCount("bear", 1, PhaseStep.END_TURN, playerB, bear, 1);
        checkPermanentCounters("4 fade", 1, PhaseStep.END_TURN, playerA, nexus, CounterType.FADE, 4);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, conscripts);
        addTarget(playerB, nexus);

        activateAbility(2, PhaseStep.POSTCOMBAT_MAIN, playerB, "Remove a fade");
        addTarget(playerB, playerA);
        addTarget(playerA, crab);

        checkExileCount("crab", 2, PhaseStep.END_TURN, playerA, crab, 1);
        checkPermanentCounters("3 fade", 2, PhaseStep.END_TURN, playerB, nexus, CounterType.FADE, 3);

        checkPermanentCounters("2 fade", 3, PhaseStep.DRAW, playerA, nexus, CounterType.FADE, 2);
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Remove a fade");
        addTarget(playerA, playerB);
        addTarget(playerB, wurm);

        checkExileCount("wurm", 3, PhaseStep.BEGIN_COMBAT, playerB, wurm, 1);
        checkPermanentCounters("1 fade", 3, PhaseStep.BEGIN_COMBAT, playerA, nexus, CounterType.FADE, 1);

        activateAbility(4, PhaseStep.POSTCOMBAT_MAIN, playerB, "Sacrifice");
        addTarget(playerB, nexus);
        // no counters left, so sac on start of turn 5

        setStrictChooseMode(true);
        setStopAt(5, PhaseStep.DRAW);
        execute();

        assertGraveyardCount(playerA, nexus, 1);
        assertGraveyardCount(playerB, hexmage, 1);
        assertHandCount(playerA, crab, 1);
        assertHandCount(playerB, bear, 1);
        assertHandCount(playerB, wurm, 1);
    }

}
