package org.mage.test.cards.abilities.keywords;

import mage.cards.Card;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class DayNightTest extends CardTestPlayerBase {

    private static final String ruffian = "Tavern Ruffian";
    private static final String smasher = "Tavern Smasher";
    private static final String moonmist = "Moonmist";
    private static final String outcasts = "Grizzled Outcasts";
    private static final String wantons = "Krallenhorde Wantons";
    private static final String immerwolf = "Immerwolf";
    private static final String bolt = "Lightning Bolt";
    private static final String curse = "Curse of Leeches";
    private static final String lurker = "Leeching Lurker";
    private static final String vandal = "Brimstone Vandal";

    private void assertDayNight(boolean daytime) {
        Assert.assertTrue("It should not be neither day nor night", currentGame.hasDayNight());
        Assert.assertTrue("It should be " + (daytime ? "day" : "night"), currentGame.checkDayNight(daytime));
        Assert.assertFalse("It should not be " + (daytime ? "night" : "day"), currentGame.checkDayNight(!daytime));
    }

    private void assertRuffianSmasher(boolean daytime) {
        assertDayNight(daytime);
        if (daytime) {
            assertPowerToughness(playerA, ruffian, 2, 5);
            assertPermanentCount(playerA, smasher, 0);
        } else {
            assertPermanentCount(playerA, ruffian, 0);
            assertPowerToughness(playerA, smasher, 6, 5);
        }
    }

    private void setDayNight(int turn, PhaseStep phaseStep, boolean daytime) {
        runCode("set game to " + (daytime ? "day" : "night"), turn, phaseStep, playerA, (i, p, game) -> game.setDaytime(daytime));
    }

    @Test
    public void testRegularDay() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.HAND, playerA, ruffian);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ruffian);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertRuffianSmasher(true);
    }

    @Test
    public void testCopy() {
        // possible bug: stack overflow on copy
        addCard(Zone.HAND, playerA, ruffian);

        runCode("copy", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            Card card = currentGame.getCards().stream().filter(c -> c.getName().equals(ruffian)).findFirst().orElse(null);
            Assert.assertNotNull(card);
            Assert.assertNotNull(card.getSecondCardFace());

            // original
            Assert.assertNotNull(card.getSecondCardFace());
            // copy
            Card copy = card.copy();
            Assert.assertNotNull(copy.getSecondCardFace());
            // deep copy
            copy = CardUtil.deepCopyObject(card);
            Assert.assertNotNull(copy.getSecondCardFace());

            // copied
            Card copied = game.copyCard(card, null, playerA.getId());
            Assert.assertNotNull(copied.getSecondCardFace());
            // copy
            copy = copied.copy();
            Assert.assertNotNull(copy.getSecondCardFace());
            // deep copy
            copy = CardUtil.deepCopyObject(copied);
            Assert.assertNotNull(copy.getSecondCardFace());
        });

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void testNightbound() {
        currentGame.setDaytime(false);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.HAND, playerA, ruffian);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ruffian);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertRuffianSmasher(false);
    }

    @Test
    public void testDayToNightTransform() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.HAND, playerA, ruffian);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ruffian);
        setDayNight(1, PhaseStep.POSTCOMBAT_MAIN, false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertRuffianSmasher(false);
    }

    @Test
    public void testNightToDayTransform() {
        currentGame.setDaytime(false);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.HAND, playerA, ruffian);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ruffian);
        setDayNight(1, PhaseStep.POSTCOMBAT_MAIN, true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertRuffianSmasher(true);
    }

    @Test
    public void testMoonmistFails() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, ruffian);
        addCard(Zone.BATTLEFIELD, playerA, outcasts);
        addCard(Zone.HAND, playerA, moonmist);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, moonmist);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertRuffianSmasher(true);
        assertPermanentCount(playerA, outcasts, 0);
        assertPowerToughness(playerA, wantons, 7, 7);
    }

    @Test
    public void testImmerwolfPreventsTransformation() {
        currentGame.setDaytime(false);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.BATTLEFIELD, playerA, immerwolf);
        addCard(Zone.HAND, playerA, ruffian);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ruffian);
        setDayNight(1, PhaseStep.POSTCOMBAT_MAIN, true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertDayNight(true);
        assertPowerToughness(playerA, smasher, 6 + 1, 5 + 1);
        assertPermanentCount(playerA, ruffian, 0);
    }

    @Test
    public void testImmerwolfRemoved() {
        currentGame.setDaytime(false);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.BATTLEFIELD, playerA, immerwolf);
        addCard(Zone.HAND, playerA, bolt);
        addCard(Zone.HAND, playerA, ruffian);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ruffian);
        setDayNight(1, PhaseStep.BEGIN_COMBAT, true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertDayNight(true);
        assertPowerToughness(playerA, smasher, 6 + 1, 5 + 1);
        assertPermanentCount(playerA, ruffian, 0);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, bolt, immerwolf);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertRuffianSmasher(true);
    }

    @Test
    public void testNoSpellsBecomesNight() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.HAND, playerA, ruffian);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ruffian);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertRuffianSmasher(true);

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertRuffianSmasher(true);

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertRuffianSmasher(false);
    }

    @Test
    public void testTwoSpellsBecomesDay() {
        currentGame.setDaytime(false);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.HAND, playerA, ruffian);
        addCard(Zone.HAND, playerA, bolt);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ruffian);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bolt, playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 3);
        assertGraveyardCount(playerA, bolt, 1);
        assertRuffianSmasher(false);

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertRuffianSmasher(true);

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertRuffianSmasher(false);
    }

    @Test
    public void testCurseOfLeechesRegular() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.HAND, playerA, curse);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, curse, playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertDayNight(true);
        Permanent permanent = getPermanent(curse);
        Assert.assertTrue("Curse is attached to playerB", permanent.isAttachedTo(playerB.getId()));
        assertPermanentCount(playerA, lurker, 0);
    }

    @Test
    public void testCurseOfLeechesNightbound() {
        currentGame.setDaytime(false);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.HAND, playerA, curse);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, curse, playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertDayNight(false);
        assertPermanentCount(playerA, curse, 0);
        assertPermanentCount(playerA, lurker, 1);
    }

    @Test
    public void testCurseOfLeechesDayToNight() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.HAND, playerA, curse);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, curse, playerB);
        setDayNight(1, PhaseStep.POSTCOMBAT_MAIN, false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertDayNight(false);
        assertPermanentCount(playerA, curse, 0);
        assertPermanentCount(playerA, lurker, 1);
    }

    @Test
    public void testCurseOfLeechesNightToDay() {
        currentGame.setDaytime(false);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.HAND, playerA, curse);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, curse, playerB);
        setChoice(playerA, playerB.getName());
        setDayNight(1, PhaseStep.POSTCOMBAT_MAIN, true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertDayNight(true);
        Permanent permanent = getPermanent(curse);
        Assert.assertTrue("Curse is attached to playerB", permanent.isAttachedTo(playerB.getId()));
        assertPermanentCount(playerA, lurker, 0);
    }

    @Test
    public void testBrimstoneVandalBecomeDay() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.HAND, playerA, vandal);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, vandal);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertDayNight(true);
        assertLife(playerB, 20);
    }

    @Test
    public void testBrimstoneVandalTrigger() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.HAND, playerA, bolt, 2);
        addCard(Zone.HAND, playerA, vandal);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, vandal);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.UPKEEP);
        execute();

        assertDayNight(false);
        assertLife(playerB, 20 - 1);

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, bolt, playerB);
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, bolt, playerB);

        setStopAt(4, PhaseStep.UPKEEP);
        execute();

        assertDayNight(true);
        assertLife(playerB, 20 - 1 - 3 - 3 - 1);
    }

    @Test
    @Ignore // debug only, use it to performance profiling only, can be slow
    public void test_TransformDayboundPerformance() {
        // day/night transform can take too much CPU usage, see https://github.com/magefree/mage/issues/11081
        final int TEST_MAX_TURN = 300;
        final int TEST_MAX_SIMPLE_CARDS = 50;
        final int TEST_MAX_DAYBOUND_CARDS = 15;

        // You have no maximum hand size.
        playerA.setMaxCallsWithoutAction(10000);
        playerB.setMaxCallsWithoutAction(10000);
        addCard(Zone.BATTLEFIELD, playerA, "Graceful Adept", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Graceful Adept", 1);
        // skip draw step
        addCard(Zone.BATTLEFIELD, playerA, "Damia, Sage of Stone", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Damia, Sage of Stone", 1);
        // simple cards
        addCard(Zone.BATTLEFIELD, playerA, "Angelfire Crusader", TEST_MAX_SIMPLE_CARDS);
        addCard(Zone.BATTLEFIELD, playerB, "Angelfire Crusader", TEST_MAX_SIMPLE_CARDS);
        // day/night cards
        addCard(Zone.BATTLEFIELD, playerA, "Baneblade Scoundrel", TEST_MAX_DAYBOUND_CARDS);
        addCard(Zone.BATTLEFIELD, playerB, "Baneblade Scoundrel", TEST_MAX_DAYBOUND_CARDS);

        for (int i = 10; i <= TEST_MAX_TURN; i++) {
            if (i % 2 == 0) {
                runCode("on turn " + i, i, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
                    // switch to night: auto on untap
                    // switch to day: here by workaround instead 2+ spells cast
                    game.setDaytime(!game.checkDayNight(true));
                });
            }
            runCode("on turn " + i, i, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
                System.out.println(String.format("turn %d, is day: %s", game.getTurnNum(), game.checkDayNight(true) ? "yes" : "no"));
            });
        }
        showBattlefield("after", TEST_MAX_TURN, PhaseStep.PRECOMBAT_MAIN, playerA);

        setStrictChooseMode(true);
        setStopAt(TEST_MAX_TURN, PhaseStep.END_TURN);
        execute();

        Assert.assertEquals(TEST_MAX_TURN, currentGame.getTurnNum());
    }
}
