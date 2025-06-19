package org.mage.test.AI.basic;

import mage.MageItem;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.InfoEffect;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.player.ai.PossibleTargetsSelector;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetDiscard;
import mage.target.common.TargetPermanentOrPlayer;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author JayDi85
 */
public class PossibleTargetsSelectorAITest extends CardTestPlayerBase {

    @Test
    public void test_SortByOutcome() {
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 1); // 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Arbor Elf", 1); // 1/1
        addCard(Zone.BATTLEFIELD, playerA, "Spectral Bears", 1); // 3/3
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1); // land
        addCard(Zone.BATTLEFIELD, playerA, "Gideon, Martial Paragon", 1);  // planeswalker
        //
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 1); // land
        addCard(Zone.BATTLEFIELD, playerB, "Goblin Brigand", 1); // 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Battering Sliver", 1); // 4/4


        runCode("check", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            // most valuable (planeswalker -> player -> bigger -> smaller)

            // good effect
            PossibleTargetsSelector selector = prepareAnyTargetSelector(Outcome.Benefit);
            selector.findNewTargets(null);
            assertTargets("good effect must return my most valuable and biggest as priority", selector.getGoodTargets(), Arrays.asList(
                    "Gideon, Martial Paragon", // pw
                    "PlayerA", // p
                    "Spectral Bears", // 3/3
                    "Balduvian Bears", // 2/2
                    "Arbor Elf", // 1/1
                    "Forest" // l
            ));
            assertTargets("good effect must return opponent's lowest as optional", selector.getBadTargets(), Arrays.asList(
                    "Forest", // l
                    "Goblin Brigand", // 2/2
                    "Battering Sliver", // 4/4
                    "PlayerB" // p
            ));

            // bad effect - must be inverted
            selector = prepareAnyTargetSelector(Outcome.Detriment);
            selector.findNewTargets(null);
            assertTargets("bad effect must return opponent's most valuable and biggest as priority", selector.getGoodTargets(), Arrays.asList(
                    "PlayerB", // p
                    "Battering Sliver", // 4/4
                    "Goblin Brigand", // 1/1
                    "Forest" // l
            ));
            assertTargets("bad effect must return my lowest as optional", selector.getBadTargets(), Arrays.asList(
                    "Forest", // l
                    "Arbor Elf", // 1/1
                    "Balduvian Bears", // 2/2
                    "Spectral Bears", // 3/3
                    "PlayerA", // p
                    "Gideon, Martial Paragon" // pw
            ));
        });

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();
    }

    @Test
    public void test_SortByPlayable() {
        addCard(Zone.HAND, playerA, "Balduvian Bears", 1); // 2/2, {1}{G}
        addCard(Zone.HAND, playerA, "Arbor Elf", 1); // 1/1, {G}, playable
        addCard(Zone.HAND, playerA, "Spectral Bears", 1); // 3/3, {1}{G}
        addCard(Zone.HAND, playerA, "Forest", 1); // land
        addCard(Zone.HAND, playerA, "Gideon, Martial Paragon", 1);  // planeswalker, {4}{W}
        //
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);

        runCode("check", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            // discard logic (remove the biggest unplayable first, land at the end)

            PossibleTargetsSelector selector = prepareDiscardCardSelector();
            selector.findNewTargets(null);
            assertTargets("discard must return biggest unplayable first", selector.getGoodTargets(), Arrays.asList(
                    "Gideon, Martial Paragon", // pw
                    "Spectral Bears", // 3/3
                    "Balduvian Bears", // 2/2
                    "Arbor Elf", // 1/1 - playable
                    "Forest" // l
            ));
        });

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();
    }

    private PossibleTargetsSelector prepareAnyTargetSelector(Outcome outcome) {
        Target target = new TargetPermanentOrPlayer();
        Ability fakeAbility = new SimpleStaticAbility(new InfoEffect("fake"));
        return new PossibleTargetsSelector(outcome, target, playerA.getId(), fakeAbility, currentGame);
    }

    private PossibleTargetsSelector prepareDiscardCardSelector() {
        Target target = new TargetDiscard(playerA.getId());
        Ability fakeAbility = new SimpleStaticAbility(new InfoEffect("fake"));
        // discard sorting do not use outcome
        return new PossibleTargetsSelector(Outcome.Benefit, target, playerA.getId(), fakeAbility, currentGame);
    }

    private void assertTargets(String info, List<MageItem> targets, List<String> needTargets) {
        List<String> currentTargets = targets.stream()
                .map(item -> {
                    if (item instanceof Player) {
                        return ((Player) item).getName();
                    } else if (item instanceof MageObject) {
                        return ((MageObject) item).getName();
                    } else {
                        return "unknown item";
                    }
                })
                .collect(Collectors.toList());
        String current = String.join("\n", currentTargets);
        String need = String.join("\n", needTargets);
        if (!current.equals(need)) {
            Assert.fail(info + "\n\n"
                    + "NEED targets:\n" + need + "\n\n"
                    + "FOUND targets:\n" + current + "\n");
        }
    }
}
