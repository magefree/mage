package org.mage.test.cards.single.fic;

import mage.cards.l.LockeTreasureHunter;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public class LockeTreasureHunterTest extends CardTestPlayerBase {

    private static final String dwarvenGrunt = "Dwarven Grunt";
    private static final String goblinMountaineer = "Goblin Mountaineer";
    private static final String mountainGoat = "Mountain Goat";
    private static final String zodiacGoat = "Zodiac Goat";

    private void makeTester() {
        addCustomCardWithAbility("tester", playerA, LockeTreasureHunter.makeTestAbility());
    }

    private void assertOptions(String... optionsToExpect) {
        Set<String> options = playerA
                .getPlayable(currentGame, false)
                .stream()
                .map(Objects::toString)
                .collect(Collectors.toSet());
        Set<String> failures = new HashSet<>();
        for (String option : optionsToExpect) {
            if (options.stream().noneMatch(s -> s.contains(option))) {
                failures.add(option);
            }
        }
        Assert.assertEquals(
                "The following cards should be available to cast but aren't: " +
                        failures.stream().collect(Collectors.joining(", ")), 0, failures.size()
        );
        Assert.assertEquals(
                "There should be " + (2 + optionsToExpect.length) + " available actions",
                2 + optionsToExpect.length, options.size()
        );
    }

    @Test
    public void testCastSome() {
        skipInitShuffling();
        makeTester();
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.LIBRARY, playerA, dwarvenGrunt);
        addCard(Zone.LIBRARY, playerA, goblinMountaineer);
        addCard(Zone.LIBRARY, playerB, mountainGoat);
        addCard(Zone.LIBRARY, playerB, zodiacGoat);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{0}");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{0}");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, dwarvenGrunt);
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertOptions(goblinMountaineer, zodiacGoat);
        assertPermanentCount(playerA, dwarvenGrunt, 1);
    }

    @Test
    public void testCastAll() {
        skipInitShuffling();
        makeTester();
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.LIBRARY, playerA, dwarvenGrunt);
        addCard(Zone.LIBRARY, playerA, goblinMountaineer);
        addCard(Zone.LIBRARY, playerB, mountainGoat);
        addCard(Zone.LIBRARY, playerB, zodiacGoat);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{0}");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{0}");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, dwarvenGrunt);
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, zodiacGoat);
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertOptions();
        assertPermanentCount(playerA, dwarvenGrunt, 1);
        assertPermanentCount(playerA, zodiacGoat, 1);
    }
}
