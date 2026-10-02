package mage.remote;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * JBoss remoting creates a thread pool for every connection (Client and ServerInvoker) and never
 * stops it: neither Client.disconnect() nor ServerInvoker.stop()/destroy() touch the pool, so every
 * closed connection used to leak its parked worker threads for the rest of the process lifetime.
 * CustomThreadPool lets idle threads die instead, bounding the leak to active connections.
 */
public class CustomThreadPoolTest {

    @Test
    public void idlePoolThreadsExitAfterKeepAlive() throws Exception {
        CustomThreadPool pool = new CustomThreadPool();
        pool.setKeepAliveTime(1000);

        CountDownLatch started = new CountDownLatch(1);
        CountDownLatch finish = new CountDownLatch(1);
        pool.run(() -> {
            started.countDown();
            try {
                finish.await();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        assertThat(started.await(5, TimeUnit.SECONDS)).isTrue();
        assertThat(pool.getPoolSize()).isGreaterThanOrEqualTo(1);

        finish.countDown();
        long deadline = System.currentTimeMillis() + 15_000;
        while (pool.getPoolSize() > 0 && System.currentTimeMillis() < deadline) {
            Thread.sleep(200);
        }
        assertThat(pool.getPoolSize()).isEqualTo(0);

        CountDownLatch done = new CountDownLatch(1);
        pool.run(done::countDown);
        assertThat(done.await(5, TimeUnit.SECONDS)).isTrue();
    }
}
