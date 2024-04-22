package org.mage.test.cards.emblems;

import mage.MageObject;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.command.emblems.TheRingEmblem;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.watchers.common.TemptedByTheRingWatcher;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author JayDi85
 */
public class TheRingEmblemTest extends CardTestPlayerBase {

    private void assertRing(String info, Player player, int totalGameEmblems, int needTemptsYou, boolean needEmblem, String needBearer) {
        List<TheRingEmblem> ringEmblems = currentGame.getState().getCommand()
                .stream()
                .filter(com -> com instanceof TheRingEmblem)
                .map(com -> (TheRingEmblem) com)
                .collect(Collectors.toList());
        Assert.assertEquals(info + " - total game emblems", totalGameEmblems, ringEmblems.size());

        Assert.assertEquals(info + " - tempts for " + player.getName(), needTemptsYou, TemptedByTheRingWatcher.getCount(player.getId(), currentGame));

        boolean hasEmblem = ringEmblems.stream().anyMatch(e -> e.isControlledBy(player.getId()));
        Assert.assertEquals(info + " - ring emblem for " + player.getName(), needEmblem, hasEmblem);

        String hasBearer = Optional.ofNullable(player.getRingBearer(currentGame)).map(MageObject::getName).orElse(null);
        Assert.assertEquals(info + " - ring bearer for " + player.getName(), needBearer, hasBearer);
    }

    private void assertPermanent(String info, Player player, String needName, boolean needLegendary) {
        Permanent permanent = currentGame.getBattlefield().getAllActivePermanents(player.getId())
                .stream()
                .filter(p -> p.isControlledBy(player.getId()))
                .filter(p -> p.getName().equals(needName))
                .findFirst()
                .orElse(null);
        Assert.assertNotNull(info + " - permanent " + needName + " for player " + player.getName() + " must exist", permanent);
        Assert.assertEquals(info + " - permanent " + needName + " for player " + player.getName() + " has wrong legendary", needLegendary, permanent.isLegendary(currentGame));
    }

    @Test
    public void test_Tempts_WithoutCreatures() {
        // Draw a card. The Ring tempts you.
        addCard(Zone.HAND, playerA, "Birthday Escape", 5); // sorcery, {U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        //
        addCard(Zone.HAND, playerB, "Birthday Escape", 2); // sorcery, {U}
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);

        runCode("before tempts", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            assertRing(info, playerA, 0, 0, false, null);
            assertRing(info, playerB, 0, 0, false, null);
        });

        // A tempts 1
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Birthday Escape");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        runCode("after A tempts 1", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            assertRing(info, playerA, 1, 1, true, null);
            assertRing(info, playerB, 1, 0, false, null);
        });

        // A tempts 2
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Birthday Escape");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        runCode("after A tempts 2", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            assertRing(info, playerA, 1, 2, true, null);
            assertRing(info, playerB, 1, 0, false, null);
        });

        // A tempts 3
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Birthday Escape");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        runCode("after A tempts 3", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            assertRing(info, playerA, 1, 3, true, null);
            assertRing(info, playerB, 1, 0, false, null);
        });

        // A tempts 4
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Birthday Escape");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        runCode("after A tempts 4", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            assertRing(info, playerA, 1, 4, true, null);
            assertRing(info, playerB, 1, 0, false, null);
        });

        // A tempts 5
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Birthday Escape");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        runCode("after A tempts 5", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            assertRing(info, playerA, 1, 5, true, null);
            assertRing(info, playerB, 1, 0, false, null);
        });

        // turn 2

        // B tempts 1
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Birthday Escape");
        waitStackResolved(2, PhaseStep.PRECOMBAT_MAIN);
        runCode("after B tempts 1", 2, PhaseStep.PRECOMBAT_MAIN, playerB, (info, player, game) -> {
            assertRing(info, playerA, 2, 5, true, null);
            assertRing(info, playerB, 2, 1, true, null);
        });

        // B tempts 2
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Birthday Escape");
        waitStackResolved(2, PhaseStep.PRECOMBAT_MAIN);
        runCode("after B tempts 2", 2, PhaseStep.PRECOMBAT_MAIN, playerB, (info, player, game) -> {
            assertRing(info, playerA, 2, 5, true, null);
            assertRing(info, playerB, 2, 2, true, null);
        });

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
    }

    @Test
    public void test_RingEffect_1_And_2_LegendaryAndDraw() {
        // 1 - Your Ring-bearer is legendary and can't be blocked by creatures with greater power.
        // 2 - Whenever your Ring-bearer attacks, draw a card, then discard a card.

        // testing:
        // * effect 1 and 2
        // * tempts with ring bear choose
        // * tempts with ring bear change

        // At the beginning of your upkeep, the Ring tempts you.
        // Whenever you choose a creature as your Ring-bearer, you may pay 2 life. If you do, draw a card.
        addCard(Zone.BATTLEFIELD, playerA, "Call of the Ring", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 1); // 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Academy Manufactor", 1); // 1/3
        addCard(Zone.HAND, playerA, "Mountain", 1);

        // turn 1

        runCode("before tempts", 1, PhaseStep.UPKEEP, playerA, (info, player, game) -> {
            assertRing(info, playerA, 0, 0, false, null);
            assertRing(info, playerB, 0, 0, false, null);
            assertPermanent(info, playerA, "Balduvian Bears", false);
            assertPermanent(info, playerA, "Academy Manufactor", false);
        });

        setChoice(playerA, "Balduvian Bears");
        setChoice(playerA, false); // no pay for draw on upkeep tempts
        runCode("on turn 1", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            assertRing(info, playerA, 1, 1, true, "Balduvian Bears");
            assertRing(info, playerB, 1, 0, false, null);
            assertPermanent(info, playerA, "Balduvian Bears", true);
            assertPermanent(info, playerA, "Academy Manufactor", false);
        });

        // attack without second effect on turn 1
        attack(1, playerA, "Balduvian Bears");


        // turn 3

        setChoice(playerA, "Academy Manufactor"); // change bearer
        setChoice(playerA, false); // no pay for draw on upkeep tempts
        runCode("on turn 3", 3, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            assertRing(info, playerA, 1, 2, true, "Academy Manufactor");
            assertRing(info, playerB, 1, 0, false, null);
            assertPermanent(info, playerA, "Balduvian Bears", false);
            assertPermanent(info, playerA, "Academy Manufactor", true);
        });

        // attack with second effect (draw - discard)
        attack(3, playerA, "Academy Manufactor");
        setChoice(playerA, "Mountain"); // discard

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20 - 2 - 1);
    }

    @Test
    public void test_RingEffect_3_and_4_BlockSacrificeAndLifeLose() {
        // 3 - Whenever your Ring-bearer becomes blocked by a creature, that creature's controller sacrifices it at end of combat.
        // 4 - Whenever your Ring-bearer deals combat damage to a player, each opponent loses 3 life.

        // testing:
        // * effect 3 and 4
        // * attack without block
        // * attack with one blocker
        // * attack with multiple blockers


        addCard(Zone.HAND, playerA, "Birthday Escape", 4); // sorcery, {U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Ashiok's Skulker", 1); // 3/5
        addCard(Zone.BATTLEFIELD, playerB, "Academy Manufactor", 1); // 1/3, for first block
        addCard(Zone.BATTLEFIELD, playerB, "Alabaster Kirin", 1); // 2/3, for second block
        addCard(Zone.BATTLEFIELD, playerB, "Alaborn Trooper", 1); // 2/3, for second block

        runCode("before tempts", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            assertRing(info, playerA, 0, 0, false, null);
            assertRing(info, playerB, 0, 0, false, null);
        });

        // A tempts 1, 2, 3, 4
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Birthday Escape");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Birthday Escape");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Birthday Escape");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Birthday Escape");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        runCode("after A tempts 1-4", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            assertRing(info, playerA, 1, 4, true, "Ashiok's Skulker");
            assertRing(info, playerB, 1, 0, false, null);
        });
        checkPermanentCount("before attack", 1, PhaseStep.PRECOMBAT_MAIN, playerA, playerA, "Ashiok's Skulker", 1);
        checkPermanentCount("before attack", 1, PhaseStep.PRECOMBAT_MAIN, playerA, playerB, "Academy Manufactor", 1);
        checkPermanentCount("before attack", 1, PhaseStep.PRECOMBAT_MAIN, playerA, playerB, "Alabaster Kirin", 1);
        checkPermanentCount("before attack", 1, PhaseStep.PRECOMBAT_MAIN, playerA, playerB, "Alaborn Trooper", 1);

        // attack and block (must sacrifice single blocker)
        attack(1, playerA, "Ashiok's Skulker");
        block(1, playerB, "Academy Manufactor", "Ashiok's Skulker");
        setChoice(playerA, "Mountain"); // draw/discard on attack trigger
        checkPermanentCount("after attack on 1", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, playerA, "Ashiok's Skulker", 1);
        checkPermanentCount("after attack on 1", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, playerB, "Academy Manufactor", 0);
        checkPermanentCount("after attack on 1", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, playerB, "Alabaster Kirin", 1);
        checkPermanentCount("after attack on 1", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, playerB, "Alaborn Trooper", 1);

        // turn 3

        // attack and block (must sacrifice all blockers)
        attack(3, playerA, "Ashiok's Skulker");
        block(3, playerB, "Alabaster Kirin", "Ashiok's Skulker");
        block(3, playerB, "Alaborn Trooper", "Ashiok's Skulker");
        setChoice(playerA, "Whenever your Ring-bearer becomes blocked"); // 2x triggers from two blockers
        setChoice(playerA, "At end of combat, that permanent"); // 2x triggers from two blockers
        setChoice(playerA, "Mountain"); // draw/discard on attack trigger
        checkPermanentCount("after attack on 3", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, playerA, "Ashiok's Skulker", 1);
        checkPermanentCount("after attack on 3", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, playerB, "Academy Manufactor", 0);
        checkPermanentCount("after attack on 3", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, playerB, "Alabaster Kirin", 0);
        checkPermanentCount("after attack on 3", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, playerB, "Alaborn Trooper", 0);

        // turn 5

        // attack without blocks
        attack(5, playerA, "Ashiok's Skulker");
        setChoice(playerA, "Mountain"); // draw/discard on attack trigger

        setStrictChooseMode(true);
        setStopAt(5, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20 - 3 - 3); // 3 from attacker, 3 from bearer trigger
    }
}
