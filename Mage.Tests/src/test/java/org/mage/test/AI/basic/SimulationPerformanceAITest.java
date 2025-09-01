package org.mage.test.AI.basic;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBaseAI;

import java.util.Arrays;
import java.util.List;

/**
 * Possible problems:
 * - big memory consumption on sims prepare (memory overflow)
 * - too many sims to calculate (AI fail on time out and do nothing)
 * <p>
 *  TODO: add tests and implement best choice selection on timeout
 *    (AI must make any good/bad choice on timeout with game log - not a skip)
 *
 *  TODO: AI do not support game sims from triggered (it's run, but do not use results)
 * <p>
 *
 * @author JayDi85
 */
public class SimulationPerformanceAITest extends CardTestPlayerBaseAI {

    @Override
    public List<String> getFullSimulatedPlayers() {
        return Arrays.asList("PlayerA", "PlayerB");
    }

    @Test
    public void test_Simple_ShortGame() {
        // both must kill x2 bears by x2 bolts
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears", 2);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 2);
        addCard(Zone.HAND, playerB, "Lightning Bolt", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 2);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Balduvian Bears", 2);
        assertGraveyardCount(playerB, "Balduvian Bears", 2);
    }

    @Test
    public void test_Simple_LongGame() {
        // many bears and bolts must help to end game fast
        int maxTurn = 50;
        removeAllCardsFromLibrary(playerA);
        removeAllCardsFromLibrary(playerB);

        addCard(Zone.LIBRARY, playerA, "Mountain", 10);
        addCard(Zone.LIBRARY, playerA, "Forest", 10);
        addCard(Zone.LIBRARY, playerA, "Lightning Bolt", 20);
        addCard(Zone.LIBRARY, playerA, "Balduvian Bears", 10);
        //
        addCard(Zone.LIBRARY, playerB, "Mountain", 10);
        addCard(Zone.LIBRARY, playerA, "Forest", 10);
        addCard(Zone.LIBRARY, playerB, "Lightning Bolt", 20);
        addCard(Zone.LIBRARY, playerB, "Balduvian Bears", 10);

        // full ai simulation
        setStrictChooseMode(true);
        setStopAt(maxTurn, PhaseStep.END_TURN);
        execute();

        Assert.assertTrue("One of player must won a game before turn " + maxTurn + ", but it ends on " + currentGame, currentGame.hasEnded());
    }

    private void runManyTargetOptionsInTrigger(String info, int totalCreatures, int needDiedCreatures, boolean isDamageRandomCreature, int needPlayerLife) {
        // When Bogardan Hellkite enters, it deals 5 damage divided as you choose among any number of targets.
        addCard(Zone.HAND, playerA, "Bogardan Hellkite", 1); // {6}{R}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 8);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears", totalCreatures);

        if (isDamageRandomCreature) {
            runCode("damage creature", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (s, player, game) -> {
                Permanent creature = game.getBattlefield().getAllPermanents().stream()
                        .filter(p -> p.getName().equals("Balduvian Bears"))
                        .findAny()
                        .orElse(null);
                Assert.assertNotNull(creature);
                Ability fakeAbility = new SimpleStaticAbility(null);
                fakeAbility.setControllerId(player.getId());
                fakeAbility.setSourceId(creature.getId());
                creature.damage(1, fakeAbility, game);
            });
        }

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Bogardan Hellkite", 1); // if fail then AI stops before all sims ends
        assertGraveyardCount(playerB, "Balduvian Bears", needDiedCreatures);
        assertLife(playerB, needPlayerLife);
    }

    @Test
    @Ignore // enable after triggered supported or need performance test
    public void test_ManyTargetOptions_Triggered_Single() {
        // 2 damage to bear and 3 damage to player B
        runManyTargetOptionsInTrigger("1 target creature", 1, 1, false, 20 - 3);
    }

    @Test
    @Ignore // enable after triggered supported or need performance test
    public void test_ManyTargetOptions_Triggered_Few() {
        // 4 damage to x2 bears and 1 damage to player B
        runManyTargetOptionsInTrigger("2 target creatures", 2, 2, false, 20 - 1);
    }

    @Test
    @Ignore // enable after triggered supported or need performance test
    public void test_ManyTargetOptions_Triggered_Many() {
        // 4 damage to x2 bears and 1 damage to player B
        runManyTargetOptionsInTrigger("5 target creatures", 5, 2, false, 20 - 1);
    }

    @Test
    @Ignore // enable after triggered supported or need performance test
    public void test_ManyTargetOptions_Triggered_TooMuch() {
        // warning, can be slow

        // make sure targets optimization works
        // (must ignore same targets for faster calc)

        // 4 damage to x2 bears and 1 damage to player B
        runManyTargetOptionsInTrigger("50 target creatures", 50, 2, false, 20 - 1);
    }

    @Test
    @Ignore // enable after triggered supported or need performance test
    public void test_ManyTargetOptions_Triggered_TargetGroups() {
        // make sure targets optimization can find unique creatures, e.g. damaged

        // 4 damage to x2 bears and 1 damage to damaged bear
        runManyTargetOptionsInTrigger("5 target creatures with one damaged", 5, 3, true, 20);
    }

    private void runManyTargetOptionsInActivate(String info, int totalCreatures, int needDiedCreatures, boolean isDamageRandomCreature, int needPlayerLife) {
        // Boulderfall deals 5 damage divided as you choose among any number of targets.
        addCard(Zone.HAND, playerA, "Boulderfall", 1); // {6}{R}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 8);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears", totalCreatures);

        if (isDamageRandomCreature) {
            runCode("damage creature", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (s, player, game) -> {
                Permanent creature = game.getBattlefield().getAllPermanents().stream()
                        .filter(p -> p.getName().equals("Balduvian Bears"))
                        .findAny()
                        .orElse(null);
                Assert.assertNotNull(creature);
                Ability fakeAbility = new SimpleStaticAbility(null);
                fakeAbility.setControllerId(player.getId());
                fakeAbility.setSourceId(creature.getId());
                creature.damage(1, fakeAbility, game);
            });
        }

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Boulderfall", 1); // if fail then AI stops before all sims ends
        assertGraveyardCount(playerB, "Balduvian Bears", needDiedCreatures);
        assertLife(playerB, needPlayerLife);
    }

    @Test
    public void test_ManyTargetOptions_Activated_Single() {
        // 2 damage to bear and 3 damage to player B
        runManyTargetOptionsInActivate("1 target creature", 1, 1, false, 20 - 3);
    }

    @Test
    public void test_ManyTargetOptions_Activated_Few() {
        // 4 damage to x2 bears and 1 damage to player B
        runManyTargetOptionsInActivate("2 target creatures", 2, 2, false, 20 - 1);
    }

    @Test
    public void test_ManyTargetOptions_Activated_Many() {
        // 4 damage to x2 bears and 1 damage to player B
        runManyTargetOptionsInActivate("5 target creatures", 5, 2, false, 20 - 1);
    }

    @Test
    public void test_ManyTargetOptions_Activated_TooMuch() {
        // warning, can be slow

        // make sure targets optimization works
        // (must ignore same targets for faster calc)

        // 4 damage to x2 bears and 1 damage to player B
        runManyTargetOptionsInActivate("50 target creatures", 50, 2, false, 20 - 1);
    }

    @Test
    public void test_ManyTargetOptions_Activated_TargetGroups() {
        // make sure targets optimization can find unique creatures, e.g. damaged

        // 4 damage to x2 bears and 1 damage to damaged bear
        runManyTargetOptionsInActivate("5 target creatures with one damaged", 5, 3, true, 20);
    }

    @Test
    @Ignore // TODO: enable and fix random error with too many sim nodes (depends on machine performance?)
    public void test_ElderDeepFiend_TooManyUpToChoices() {
        // bug: game freeze with 100% CPU usage
        // https://github.com/magefree/mage/issues/9518
        int cardsCount = 2; // 2+ cards will generate too much target options for simulations

        // Boulderfall deals 5 damage divided as you choose among any number of targets.
        // Flash
        // Emerge {5}{U}{U} (You may cast this spell by sacrificing a creature and paying the emerge cost reduced by that creature's mana value.)
        // When you cast this spell, tap up to four target permanents.
        addCard(Zone.HAND, playerA, "Elder Deep-Fiend", cardsCount); // {8}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 8 * cardsCount);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Kitesail Corsair", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Alpha Tyrranax", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Abbey Griffin", 2);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Elder Deep-Fiend", cardsCount); // ai must cast it
    }
}
