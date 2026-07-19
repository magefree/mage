package org.mage.test.cards.single.c13;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.permanent.Permanent;
import mage.watchers.common.ManaPaidSourceWatcher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mage.test.serverside.base.CardTestCommanderDuelBase;

/**
 * @author JayDi85
 */
public class MarathWillOfTheWildTest extends CardTestCommanderDuelBase {

    @Test
    public void test_Play() {
        // Marath, Will of the Wild enters the battlefield with a number of +1/+1 counters on it equal
        // to the amount of mana spent to cast it.
        addCard(Zone.COMMAND, playerA, "Marath, Will of the Wild", 1); // {R}{G}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2 + 2); // for second cast
        //
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        // cast first
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}", 1);
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}", 1);
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {W}", 1);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Marath, Will of the Wild");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCounters("after first cast must have 3x counters", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Marath, Will of the Wild", CounterType.P1P1, 3);

        // COPY WATCHER testing (e.g. rollback compatible)
        runCode("test copy watcher", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            ManaPaidSourceWatcher watcher = game.getState().getWatcher(ManaPaidSourceWatcher.class);
            Permanent perm = game.getBattlefield().getAllPermanents()
                    .stream()
                    .filter(p -> p.getName().equals("Marath, Will of the Wild"))
                    .findFirst()
                    .orElse(null);
            Assertions.assertNotNull(perm);

            // check correct copy
            int before = watcher.testsReturnTotal(perm);
            Assertions.assertEquals(3, before, "original must have 3x");
            ManaPaidSourceWatcher copiedWatcher = watcher.copy();
            int after = copiedWatcher.testsReturnTotal(perm);
            Assertions.assertEquals(before, after, "copied must have 3x");

            // check correct refs and changes
            // simulate ai games (changes in copied watcher must not touch original watcher)
            copiedWatcher.testsIncrementManaAmount(game, perm);
            int afterChangeCopied = copiedWatcher.testsReturnTotal(perm);
            int afterChangeOriginal = watcher.testsReturnTotal(perm);
            Assertions.assertEquals(4, afterChangeCopied, "after change, copied");
            Assertions.assertEquals(3, afterChangeOriginal, "after change, original");
        });

        // kill
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Marath, Will of the Wild");
        setChoice(playerA, true); // move to command
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // cast second with commander tax
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Marath, Will of the Wild");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCounters("after second cast must have 5x counters", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Marath, Will of the Wild", CounterType.P1P1, 3 + 2);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
    }
}
