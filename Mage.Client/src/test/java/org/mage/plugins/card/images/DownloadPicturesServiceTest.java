package org.mage.plugins.card.images;

import net.java.truevfs.access.TFile;
import net.java.truevfs.access.TFileInputStream;
import net.java.truevfs.access.TFileOutputStream;
import net.java.truevfs.access.TVFS;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Headless coverage for download-worker termination. Archive cleanup must not
 * start while a worker is still active, even if a single awaitTermination poll
 * times out or the coordinator is interrupted.
 *
 * @author JayDi85
 */
public class DownloadPicturesServiceTest {

    private static final long TEST_WAIT_SECONDS = 10;
    private static final long SHORT_WAIT_MILLIS = 200;

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void test_falseAwaitTerminationDoesNotReleaseActiveWorker() throws Exception {
        CountDownLatch workerStarted = new CountDownLatch(1);
        CountDownLatch releaseWorker = new CountDownLatch(1);
        CountDownLatch workerFinished = new CountDownLatch(1);
        CountDownLatch falseTimeoutsObserved = new CountDownLatch(2);
        AtomicInteger awaitCalls = new AtomicInteger();
        AtomicBoolean returnedWhileWorkerActive = new AtomicBoolean(true);

        ExecutorService pool = Executors.newSingleThreadExecutor(daemonThreads("download-timeout-worker"));
        TimeoutControllingExecutor executor = new TimeoutControllingExecutor(pool, 2, falseTimeoutsObserved, awaitCalls);
        try {
            executor.execute(() -> {
                workerStarted.countDown();
                awaitQuietly(releaseWorker);
                workerFinished.countDown();
            });
            Assert.assertTrue("worker should start", workerStarted.await(TEST_WAIT_SECONDS, TimeUnit.SECONDS));

            Thread coordinator = startCoordinator(() -> {
                DownloadPicturesService.awaitDownloadWorkersTerminated(executor);
                returnedWhileWorkerActive.set(workerFinished.getCount() != 0);
            });
            coordinator.start();

            Assert.assertTrue("production wait must poll more than once",
                    falseTimeoutsObserved.await(TEST_WAIT_SECONDS, TimeUnit.SECONDS));
            coordinator.join(SHORT_WAIT_MILLIS);
            Assert.assertTrue("false awaitTermination must not finish the wait while a worker is active",
                    coordinator.isAlive());
            Assert.assertTrue(awaitCalls.get() >= 2);

            releaseWorker.countDown();
            joinOrFail(coordinator, pool, "coordinator did not finish after worker release");
            Assert.assertFalse("termination routine returned while the worker was still active",
                    returnedWhileWorkerActive.get());
            Assert.assertTrue("must retry after more than one polling timeout", awaitCalls.get() >= 3);
            Assert.assertEquals(0, workerFinished.getCount());
        } finally {
            releaseWorker.countDown();
            pool.shutdownNow();
        }
    }

    @Test
    public void test_trueVfsStreamStaysOpenUntilWorkerCloses() throws Exception {
        String zipPath = tempFolder.newFolder().getAbsolutePath();
        TFile entry = new TFile(Paths.get(zipPath, "images.zip", "SET", "card.jpg").toString());
        byte[] payload = "xmage-image-bytes".getBytes(StandardCharsets.UTF_8);

        CountDownLatch streamOpen = new CountDownLatch(1);
        CountDownLatch releaseStream = new CountDownLatch(1);
        CountDownLatch workerFinished = new CountDownLatch(1);
        AtomicBoolean streamClosed = new AtomicBoolean(false);
        AtomicBoolean returnedBeforeClose = new AtomicBoolean(true);
        AtomicReference<Throwable> workerError = new AtomicReference<>();

        ExecutorService executor = Executors.newSingleThreadExecutor(daemonThreads("download-tvfs-worker"));
        try {
            executor.execute(() -> {
                try {
                    TFile parent = entry.getParentFile();
                    if (parent != null && !parent.exists()) {
                        parent.mkdirs();
                    }
                    try (TFileOutputStream out = new TFileOutputStream(entry)) {
                        out.write(payload);
                        out.flush();
                        streamOpen.countDown();
                        if (!releaseStream.await(TEST_WAIT_SECONDS, TimeUnit.SECONDS)) {
                            workerError.compareAndSet(null, new AssertionError("worker was not released before test timeout"));
                        }
                    }
                    streamClosed.set(true);
                } catch (Exception e) {
                    workerError.compareAndSet(null, e);
                } finally {
                    workerFinished.countDown();
                }
            });

            Assert.assertTrue("TrueVFS stream should open", streamOpen.await(TEST_WAIT_SECONDS, TimeUnit.SECONDS));

            Thread coordinator = startCoordinator(() -> {
                DownloadPicturesService.awaitDownloadWorkersTerminated(executor);
                returnedBeforeClose.set(!streamClosed.get());
            });
            coordinator.start();

            coordinator.join(SHORT_WAIT_MILLIS);
            Assert.assertTrue("termination barrier must stay blocked while the ZIP entry stream is open",
                    coordinator.isAlive());
            Assert.assertFalse(streamClosed.get());

            releaseStream.countDown();
            joinOrFail(coordinator, executor, "coordinator did not finish after stream close");
            Assert.assertFalse("cleanup became eligible before the worker closed the stream", returnedBeforeClose.get());
            Assert.assertTrue(streamClosed.get());
            Assert.assertEquals(0, workerFinished.getCount());
            if (workerError.get() != null) {
                throw new AssertionError("worker failed", workerError.get());
            }

            TVFS.umount();
            try (TFileInputStream in = new TFileInputStream(entry);
                 ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                byte[] buf = new byte[64];
                int len;
                while ((len = in.read(buf)) != -1) {
                    out.write(buf, 0, len);
                }
                Assert.assertArrayEquals(payload, out.toByteArray());
            }
        } finally {
            releaseStream.countDown();
            executor.shutdownNow();
            try {
                TVFS.umount();
            } catch (Exception ignored) {
            }
        }
    }

    @Test
    public void test_interruptWaitsForWorkersAndRestoresFlag() throws Exception {
        CountDownLatch workerStarted = new CountDownLatch(1);
        CountDownLatch workerFinished = new CountDownLatch(1);
        CountDownLatch enteredWait = new CountDownLatch(1);
        AtomicBoolean queuedTaskRan = new AtomicBoolean(false);
        AtomicBoolean interruptRestored = new AtomicBoolean(false);
        AtomicBoolean workerActiveAtReturn = new AtomicBoolean(true);

        ExecutorService pool = Executors.newSingleThreadExecutor(daemonThreads("download-interrupt-worker"));
        ExecutorService executor = new WaitSignallingExecutor(pool, enteredWait);
        try {
            executor.execute(() -> {
                workerStarted.countDown();
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            break;
                        }
                    }
                } finally {
                    workerFinished.countDown();
                }
            });
            executor.execute(() -> queuedTaskRan.set(true));

            Assert.assertTrue("running worker should start", workerStarted.await(TEST_WAIT_SECONDS, TimeUnit.SECONDS));

            Thread coordinator = startCoordinator(() -> {
                DownloadPicturesService.awaitDownloadWorkersTerminated(executor);
                interruptRestored.set(Thread.currentThread().isInterrupted());
                workerActiveAtReturn.set(workerFinished.getCount() != 0);
            });
            coordinator.start();

            Assert.assertTrue("coordinator should enter the termination wait",
                    enteredWait.await(TEST_WAIT_SECONDS, TimeUnit.SECONDS));
            coordinator.interrupt();

            joinOrFail(coordinator, pool, "interrupted coordinator did not finish after workers exited");
            Assert.assertTrue("interrupt flag must be restored after the termination barrier",
                    interruptRestored.get());
            Assert.assertFalse("cleanup must not start while a worker is still active", workerActiveAtReturn.get());
            Assert.assertEquals(0, workerFinished.getCount());
            Assert.assertFalse("queued work must be dropped on coordinator interrupt", queuedTaskRan.get());
        } finally {
            pool.shutdownNow();
        }
    }

    @Test
    public void test_alreadyTerminatedExecutorPreservesInterrupt() throws Exception {
        ExecutorService executor = Executors.newSingleThreadExecutor(daemonThreads("download-already-terminated"));
        try {
            CountDownLatch ran = new CountDownLatch(1);
            executor.execute(ran::countDown);
            Assert.assertTrue(ran.await(TEST_WAIT_SECONDS, TimeUnit.SECONDS));
            executor.shutdown();
            Assert.assertTrue(executor.awaitTermination(TEST_WAIT_SECONDS, TimeUnit.SECONDS));

            DownloadPicturesService.awaitDownloadWorkersTerminated(executor);
            Assert.assertTrue(executor.isTerminated());

            Thread.currentThread().interrupt();
            try {
                DownloadPicturesService.awaitDownloadWorkersTerminated(executor);
                Assert.assertTrue("already-terminated wait must preserve the coordinator interrupt flag",
                        Thread.currentThread().isInterrupted());
            } finally {
                Thread.interrupted();
            }
        } finally {
            Thread.interrupted();
            executor.shutdownNow();
        }
    }

    @Test
    public void test_submissionFailureStillWaitsForActiveWorker() throws Exception {
        CountDownLatch workerStarted = new CountDownLatch(1);
        CountDownLatch releaseWorker = new CountDownLatch(1);
        CountDownLatch workerFinished = new CountDownLatch(1);
        AtomicBoolean returnedWhileWorkerActive = new AtomicBoolean(true);

        ExecutorService executor = Executors.newSingleThreadExecutor(daemonThreads("download-reject-worker"));
        try {
            executor.execute(() -> {
                workerStarted.countDown();
                awaitQuietly(releaseWorker);
                workerFinished.countDown();
            });
            Assert.assertTrue(workerStarted.await(TEST_WAIT_SECONDS, TimeUnit.SECONDS));
            executor.shutdown();

            try {
                executor.execute(() -> {
                });
                Assert.fail("expected RejectedExecutionException after shutdown");
            } catch (RejectedExecutionException expected) {
                // submission-failure cleanup still has to wait for the running worker
            }

            Thread coordinator = startCoordinator(() -> {
                DownloadPicturesService.awaitDownloadWorkersTerminated(executor);
                returnedWhileWorkerActive.set(workerFinished.getCount() != 0);
            });
            coordinator.start();

            coordinator.join(SHORT_WAIT_MILLIS);
            Assert.assertTrue("submission-failure cleanup must wait for the active worker", coordinator.isAlive());

            releaseWorker.countDown();
            joinOrFail(coordinator, executor, "submission-failure cleanup did not finish after worker release");
            Assert.assertFalse(returnedWhileWorkerActive.get());
            Assert.assertEquals(0, workerFinished.getCount());
        } finally {
            releaseWorker.countDown();
            executor.shutdownNow();
        }
    }

    private static Thread startCoordinator(Runnable action) {
        Thread thread = new Thread(action, "download-pictures-test-coordinator");
        thread.setDaemon(true);
        return thread;
    }

    private static void joinOrFail(Thread thread, ExecutorService executor, String message) throws InterruptedException {
        thread.join(TimeUnit.SECONDS.toMillis(TEST_WAIT_SECONDS));
        if (thread.isAlive()) {
            executor.shutdownNow();
            thread.interrupt();
            Assert.fail(message);
        }
    }

    private static void awaitQuietly(CountDownLatch latch) {
        try {
            if (!latch.await(TEST_WAIT_SECONDS, TimeUnit.SECONDS)) {
                throw new IllegalStateException("timed out waiting for test latch");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static ThreadFactory daemonThreads(String name) {
        return runnable -> {
            Thread thread = new Thread(runnable, name);
            thread.setDaemon(true);
            return thread;
        };
    }

    /**
     * Delegates real worker execution while forcing a number of false
     * awaitTermination results so tests can exercise the production retry loop
     * without sleeping for the 30-second poll.
     */
    private static final class TimeoutControllingExecutor extends DelegatingExecutorService {

        private final AtomicInteger remainingFalseTimeouts;
        private final CountDownLatch falseTimeoutsObserved;
        private final AtomicInteger awaitCalls;

        TimeoutControllingExecutor(ExecutorService delegate, int falseTimeouts,
                                   CountDownLatch falseTimeoutsObserved, AtomicInteger awaitCalls) {
            super(delegate);
            this.remainingFalseTimeouts = new AtomicInteger(falseTimeouts);
            this.falseTimeoutsObserved = falseTimeoutsObserved;
            this.awaitCalls = awaitCalls;
        }

        @Override
        public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
            if (Thread.interrupted()) {
                throw new InterruptedException();
            }
            awaitCalls.incrementAndGet();
            if (remainingFalseTimeouts.getAndDecrement() > 0) {
                falseTimeoutsObserved.countDown();
                return false;
            }
            return super.awaitTermination(timeout, unit);
        }
    }

    private static final class WaitSignallingExecutor extends DelegatingExecutorService {

        private final CountDownLatch enteredWait;

        WaitSignallingExecutor(ExecutorService delegate, CountDownLatch enteredWait) {
            super(delegate);
            this.enteredWait = enteredWait;
        }

        @Override
        public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
            enteredWait.countDown();
            return super.awaitTermination(timeout, unit);
        }
    }

    private static class DelegatingExecutorService implements ExecutorService {

        private final ExecutorService delegate;

        DelegatingExecutorService(ExecutorService delegate) {
            this.delegate = delegate;
        }

        @Override
        public void shutdown() {
            delegate.shutdown();
        }

        @Override
        public List<Runnable> shutdownNow() {
            return delegate.shutdownNow();
        }

        @Override
        public boolean isShutdown() {
            return delegate.isShutdown();
        }

        @Override
        public boolean isTerminated() {
            return delegate.isTerminated();
        }

        @Override
        public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
            return delegate.awaitTermination(timeout, unit);
        }

        @Override
        public <T> Future<T> submit(Callable<T> task) {
            return delegate.submit(task);
        }

        @Override
        public <T> Future<T> submit(Runnable task, T result) {
            return delegate.submit(task, result);
        }

        @Override
        public Future<?> submit(Runnable task) {
            return delegate.submit(task);
        }

        @Override
        public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
            return delegate.invokeAll(tasks);
        }

        @Override
        public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit)
                throws InterruptedException {
            return delegate.invokeAll(tasks, timeout, unit);
        }

        @Override
        public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, java.util.concurrent.ExecutionException {
            return delegate.invokeAny(tasks);
        }

        @Override
        public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit)
                throws InterruptedException, java.util.concurrent.ExecutionException, java.util.concurrent.TimeoutException {
            return delegate.invokeAny(tasks, timeout, unit);
        }

        @Override
        public void execute(Runnable command) {
            delegate.execute(command);
        }
    }
}
