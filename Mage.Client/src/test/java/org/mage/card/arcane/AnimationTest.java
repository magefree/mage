package org.mage.card.arcane;

import mage.cards.MageCardSpace;
import mage.client.cards.CardIconsPanel;
import mage.client.cards.CardIconsPanelFactory;
import mage.client.util.GUISizeHelper;
import org.junit.Assert;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.lang.reflect.Field;
import java.util.*;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * GUI: tests for card animations engine (headless, uses real swing timer and swing thread)
 * <p>
 * Current features:
 * - all animation callbacks run in the swing thread only (fix random GUI errors like NPE in layouts);
 * - one shared timer for all animations, it stops on idle;
 * - one animation per target (a new animation cancels the old one, the old one jumps to the final state);
 * - cancel all animations of a game on the game close;
 * - a busy swing thread (big game update) does not break animations: limited time jump per frame, limited total time;
 * - fast games (ai): many state changes of the same card and many cards at once (token swarm);
 * - start/end callbacks are always balanced (cancel of a not started animation calls start too);
 * - no leaks: registries and lists are empty after the last animation;
 * - card's outer space: the card and its icons fit the component after any rotation (tap, flip, tap + flip).
 *
 * @author johannes-wolf, JayDi85
 */
public class AnimationTest {

    /**
     * Records all callbacks with the thread info
     */
    private static class TestAnimation extends Animation {

        final List<Float> percents = Collections.synchronizedList(new ArrayList<>());
        final AtomicInteger starts = new AtomicInteger();
        final AtomicInteger ends = new AtomicInteger();
        final AtomicBoolean callbackOutsideSwing = new AtomicBoolean();
        final AtomicLong endedAtNanos = new AtomicLong();
        final long createdAtNanos = System.nanoTime();
        Runnable onEnd = () -> {
        };

        TestAnimation(UUID gameId, Object target, long duration) {
            super(gameId, target, duration);
        }

        TestAnimation(UUID gameId, Object target, long duration, long delay) {
            super(gameId, target, duration, delay);
        }

        private void checkThread() {
            if (!SwingUtilities.isEventDispatchThread()) {
                callbackOutsideSwing.set(true);
            }
        }

        @Override
        protected void start() {
            checkThread();
            starts.incrementAndGet();
        }

        @Override
        protected void update(float percentage) {
            checkThread();
            percents.add(percentage);
        }

        @Override
        protected void end() {
            checkThread();
            ends.incrementAndGet();
            endedAtNanos.set(System.nanoTime());
            onEnd.run();
        }

        void doCancel() {
            cancel();
        }

        boolean isDone() {
            return ends.get() > 0;
        }

        long getTotalMillis() {
            return (endedAtNanos.get() - createdAtNanos) / 1_000_000;
        }

        float getLastPercent() {
            return percents.get(percents.size() - 1);
        }
    }

    private static <T> T onSwing(Callable<T> task) throws Exception {
        List<T> res = new ArrayList<>();
        List<Exception> errors = new ArrayList<>();
        SwingUtilities.invokeAndWait(() -> {
            try {
                res.add(task.call());
            } catch (Exception e) {
                errors.add(e);
            }
        });
        if (!errors.isEmpty()) {
            throw errors.get(0);
        }
        return res.get(0);
    }

    private static void waitFor(String what, Callable<Boolean> condition, long timeoutMs) throws Exception {
        long end = System.currentTimeMillis() + timeoutMs;
        while (System.currentTimeMillis() < end) {
            if (condition.call()) {
                return;
            }
            Thread.sleep(5);
        }
        Assert.fail("timeout " + timeoutMs + " ms: " + what);
    }

    @SuppressWarnings("unchecked")
    private static <T> T staticField(String name) throws Exception {
        Field f = Animation.class.getDeclaredField(name);
        f.setAccessible(true);
        return (T) f.get(null);
    }

    private static int registrySize(String name) throws Exception {
        return onSwing(() -> {
            Object value = staticField(name);
            return value instanceof Map ? ((Map<?, ?>) value).size() : ((Collection<?>) value).size();
        });
    }

    private static boolean timerRunning() throws Exception {
        return ((javax.swing.Timer) staticField("timer")).isRunning();
    }

    private static void assertAllClean(String stage) throws Exception {
        // one more tick is needed after the last end() to stop the timer
        waitFor("timer stops after all animations (" + stage + ")", () -> !timerRunning(), 2000);
        Assert.assertEquals("no running animations left (" + stage + ")", 0, registrySize("animations"));
        Assert.assertEquals("no pending animations left (" + stage + ")", 0, registrySize("pendingAdd"));
        Assert.assertEquals("no targets left (" + stage + ")", 0, registrySize("activeByTarget"));
        Assert.assertEquals("no games left (" + stage + ")", 0, registrySize("activeByGameId"));
    }

    private static boolean isMonotonic(List<Float> values) {
        for (int i = 1; i < values.size(); i++) {
            if (values.get(i) < values.get(i - 1)) {
                return false;
            }
        }
        return true;
    }

    @Test
    public void test_Single_RunsInSwingThreadAndEndsOnTime() throws Exception {
        TestAnimation a = onSwing(() -> new TestAnimation(UUID.randomUUID(), new Object(), 300));
        waitFor("animation end", a::isDone, 2000);

        Assert.assertFalse("all callbacks must run in the swing thread", a.callbackOutsideSwing.get());
        Assert.assertEquals(1, a.starts.get());
        Assert.assertEquals(1, a.ends.get());
        Assert.assertTrue("percents must grow: " + a.percents, isMonotonic(a.percents));
        Assert.assertEquals("first update must be 0.0", 0f, a.percents.get(0), 0.0001f);
        Assert.assertEquals("last update must be 1.0", 1f, a.getLastPercent(), 0.0001f);
        Assert.assertTrue("300 ms animation must end in 300..600 ms, got " + a.getTotalMillis(), a.getTotalMillis() >= 290 && a.getTotalMillis() < 600);
        Assert.assertTrue("frames must be ~30 ms, got " + a.percents.size(), a.percents.size() >= 6 && a.percents.size() <= 15);
        assertAllClean("single");
    }

    @Test
    public void test_Delay_StartsLater() throws Exception {
        TestAnimation a = onSwing(() -> new TestAnimation(null, new Object(), 100, 300));
        Thread.sleep(150);
        Assert.assertEquals("must not start before the delay", 0, a.starts.get());
        waitFor("animation end", a::isDone, 2000);
        Assert.assertTrue("delay 300 + duration 100 must end in 400..700 ms, got " + a.getTotalMillis(), a.getTotalMillis() >= 390 && a.getTotalMillis() < 700);
        assertAllClean("delay");
    }

    @Test
    public void test_ZeroDuration_JumpsToFinalStateWithoutTimer() throws Exception {
        TestAnimation a = onSwing(() -> new TestAnimation(null, new Object(), 0));
        waitFor("animation end", a::isDone, 2000);
        Assert.assertEquals(1, a.starts.get());
        Assert.assertEquals(1, a.ends.get());
        Assert.assertEquals(Collections.singletonList(1f), a.percents);
        Assert.assertEquals("zero duration animation must not be registered", 0, registrySize("activeByTarget"));
    }

    @Test
    public void test_SameTarget_NewAnimationCancelsTheOld() throws Exception {
        // example: tap of a permanent while its fade in animation runs
        Object target = new Object();
        UUID gameId = UUID.randomUUID();
        TestAnimation first = onSwing(() -> new TestAnimation(gameId, target, 300));
        Thread.sleep(100);
        TestAnimation second = onSwing(() -> new TestAnimation(gameId, target, 300));

        waitFor("second animation end", second::isDone, 2000);
        Assert.assertEquals("the old animation must be ended on cancel", 1, first.ends.get());
        Assert.assertEquals("the old animation must jump to the final state", 1f, first.getLastPercent(), 0.0001f);
        Assert.assertTrue("the old animation must end on the cancel tick (~100 ms), got " + first.getTotalMillis(), first.getTotalMillis() < 250);
        Assert.assertTrue("the old animation must end before the new one", first.endedAtNanos.get() < second.endedAtNanos.get());
        assertAllClean("same target");
    }

    @Test
    public void test_FastGame_SameTargetChangesFasterThanAnimation() throws Exception {
        // ai game: 50 state changes of one permanent in 500 ms (faster than a 300 ms animation)
        Object target = new Object();
        UUID gameId = UUID.randomUUID();
        List<TestAnimation> all = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            all.add(onSwing(() -> new TestAnimation(gameId, target, 300)));
            Thread.sleep(10);
        }
        waitFor("last animation end", all.get(all.size() - 1)::isDone, 3000);
        for (TestAnimation a : all) {
            Assert.assertEquals("every animation must start once", 1, a.starts.get());
            Assert.assertEquals("every animation must end once (cancelled or finished)", 1, a.ends.get());
            Assert.assertEquals("every animation must reach the final state", 1f, a.getLastPercent(), 0.0001f);
        }
        assertAllClean("fast game");
    }

    @Test
    public void test_CancelBeforeStart_StartAndEndAreBalanced() throws Exception {
        // example: onBeginAnimation/onEndAnimation of a card panel must be called in pairs
        Object target = new Object();
        TestAnimation a = onSwing(() -> {
            TestAnimation created = new TestAnimation(null, target, 300, 1000); // delayed, so it stays scheduled
            created.doCancel();
            return created;
        });
        waitFor("cancelled animation end", a::isDone, 2000);
        Assert.assertEquals("start must be called for a cancelled animation too", 1, a.starts.get());
        Assert.assertEquals(1, a.ends.get());
        Assert.assertEquals(Collections.singletonList(1f), a.percents);
        assertAllClean("cancel before start");
    }

    @Test
    public void test_CancelByGame_EndsGameAnimationsOnly() throws Exception {
        UUID game1 = UUID.randomUUID();
        UUID game2 = UUID.randomUUID();
        List<TestAnimation> g1 = new ArrayList<>();
        List<TestAnimation> g2 = new ArrayList<>();
        onSwing(() -> {
            for (int i = 0; i < 20; i++) {
                g1.add(new TestAnimation(game1, new Object(), 2000));
                g2.add(new TestAnimation(game2, new Object(), 2000));
            }
            return null;
        });
        Thread.sleep(100);
        onSwing(() -> {
            Animation.cancelRunningAnimations(game1);
            return null;
        });
        Thread.sleep(100);
        g1.forEach(a -> Assert.assertEquals("game 1 animations must be ended", 1, a.ends.get()));
        g2.forEach(a -> Assert.assertEquals("game 2 animations must keep running", 0, a.ends.get()));
        Assert.assertEquals("game 1 must be removed from the registry", 1, registrySize("activeByGameId"));

        // animations api is for swing thread only
        try {
            Animation.cancelRunningAnimations(game2);
            Assert.fail("cancel outside the swing thread must raise error");
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(e.getMessage().contains("SWING"));
        }

        onSwing(() -> {
            Animation.cancelRunningAnimations(game2);
            return null;
        });
        waitFor("all ended", () -> g2.stream().allMatch(TestAnimation::isDone), 2000);
        assertAllClean("cancel by game");
    }

    @Test
    public void test_ManyAnimationsAtOnce_AllEndAndNoLeaks() throws Exception {
        // battlefield with hundreds of permanents at once (token swarm)
        int count = 2000;
        UUID gameId = UUID.randomUUID();
        List<TestAnimation> all = new ArrayList<>();
        long t0 = System.nanoTime();
        onSwing(() -> {
            for (int i = 0; i < count; i++) {
                all.add(new TestAnimation(gameId, new Object(), 300));
            }
            return null;
        });
        long scheduledMs = (System.nanoTime() - t0) / 1_000_000;
        waitFor("all ended", () -> all.stream().allMatch(TestAnimation::isDone), 5000);
        long totalMs = (System.nanoTime() - t0) / 1_000_000;
        Assert.assertTrue("scheduling must be fast, got " + scheduledMs + " ms", scheduledMs < 1000);
        Assert.assertTrue("300 ms animations must all end in < 2 s, got " + totalMs + " ms", totalMs < 2000);
        Assert.assertTrue("no frame drops, got " + all.get(0).percents.size(), all.get(0).percents.size() >= 5);
        all.forEach(a -> Assert.assertFalse(a.callbackOutsideSwing.get()));
        assertAllClean("many animations");
    }

    @Test
    public void test_BusySwingThread_AnimationContinuesSmoothlyAndEndsInLimitedTime() throws Exception {
        // a heavy game update blocks the swing thread for 400 ms in the middle of a 600 ms animation
        TestAnimation a = onSwing(() -> new TestAnimation(null, new Object(), 600));
        Thread.sleep(150);
        onSwing(() -> {
            Thread.sleep(400); // swing thread is busy
            return null;
        });
        waitFor("animation end", a::isDone, 3000);
        Assert.assertTrue("percents must grow: " + a.percents, isMonotonic(a.percents));
        // no jumps: the animation continues from the last frame, max 100 ms per frame (1/6 of 600 ms), the last frame is forced 1.0
        float maxJump = 0;
        for (int i = 1; i < a.percents.size() - 1; i++) {
            maxJump = Math.max(maxJump, a.percents.get(i) - a.percents.get(i - 1));
        }
        Assert.assertTrue("time jumps must be limited to 100 ms per frame, got max " + maxJump * 600 + " ms", maxJump * 600 <= 101);
        // ends later than 600 ms (continues after the block), but never later than x2 duration
        Assert.assertTrue("busy swing thread must not stop the animation for too long, got " + a.getTotalMillis() + " ms",
                a.getTotalMillis() >= 600 && a.getTotalMillis() <= 600 * 2 + 100);
        assertAllClean("busy swing thread");
    }

    @Test
    public void test_BusySwingThread_TooLongAnimationIsForcedToEnd() throws Exception {
        // blocks longer than the whole animation: must end on the first tick after the block (x2 duration limit)
        TestAnimation a = onSwing(() -> new TestAnimation(null, new Object(), 200));
        Thread.sleep(50);
        onSwing(() -> {
            Thread.sleep(500);
            return null;
        });
        waitFor("animation end", a::isDone, 3000);
        Assert.assertTrue("animation must be forced to end after x2 duration, got " + a.getTotalMillis() + " ms", a.getTotalMillis() < 700);
        Assert.assertEquals(1f, a.getLastPercent(), 0.0001f);
        assertAllClean("forced end");
    }

    @Test
    public void test_NewAnimationFromEndCallback_Runs() throws Exception {
        // an animation created inside a callback of the last running animation must run too (timer must not stop)
        List<TestAnimation> chained = Collections.synchronizedList(new ArrayList<>());
        TestAnimation first = onSwing(() -> new TestAnimation(null, new Object(), 100));
        first.onEnd = () -> chained.add(new TestAnimation(null, new Object(), 100));
        waitFor("first end", first::isDone, 2000);
        waitFor("chained animation end", () -> !chained.isEmpty() && chained.get(0).isDone(), 2000);
        Assert.assertEquals(1, chained.get(0).starts.get());
        assertAllClean("chained");
    }

    @Test
    public void test_OuterSpace_CardAndIconsFitAfterAnyRotation() throws Exception {
        // icons panels are drawn partly outside the card (TOP, LEFT, corners), a card rotates around
        // (width / 2, height - width / 2) by 90 (tap), 180 (flip) and 270 (tap + flip) degrees
        GUISizeHelper.calculateGUISizes();

        List<String> problems = new ArrayList<>();
        int minW = GUISizeHelper.battlefieldCardMinDimension.width;
        int maxW = GUISizeHelper.battlefieldCardMaxDimension.width;
        for (int w : new int[]{minW, (minW + maxW) / 2, maxW, 60, 100, 150, 250}) {
            int h = Math.round(w * CardPanel.ASPECT_RATIO);
            MageCardSpace outer = MageLayer.getAnimationOuterSpace(w, h);
            Rectangle component = new Rectangle(-outer.getLeft(), -outer.getTop(), w + outer.getWidth(), h + outer.getHeight());
            Rectangle card = new Rectangle(0, 0, w, h);

            // component must not be too big (old version: x2 width and x2 height on each side)
            Assert.assertTrue("component must be smaller than x3 of the card: " + component, component.width < w * 3 && component.height < h * 3);

            List<Rectangle> drawn = new ArrayList<>();
            drawn.add(card);
            for (CardIconsPanel panel : Arrays.asList(
                    CardIconsPanelFactory.createAbilitiesPanel(),
                    CardIconsPanelFactory.createPlayablePanel(),
                    CardIconsPanelFactory.createCommanderPanel())) {
                panel.updateSizes(card);
                drawn.add(panel.getBounds());
            }

            double pivotX = w / 2d;
            double pivotY = h - w / 2d;
            for (double angle : new double[]{0, CardPanel.TAPPED_ANGLE, CardPanel.FLIPPED_ANGLE, CardPanel.TAPPED_ANGLE + CardPanel.FLIPPED_ANGLE}) {
                AffineTransform at = AffineTransform.getRotateInstance(angle, pivotX, pivotY);
                for (Rectangle r : drawn) {
                    Rectangle rotated = at.createTransformedShape(r).getBounds();
                    if (!component.contains(rotated)) {
                        problems.add(String.format("card %dx%d, angle %d: %s %s is outside the component %s",
                                w, h, Math.round(Math.toDegrees(angle)), r == card ? "card" : "icons", rotated, component));
                    }
                }
            }
        }
        Assert.assertTrue("card or icons are clipped after rotation:\n" + String.join("\n", problems), problems.isEmpty());
    }
}
