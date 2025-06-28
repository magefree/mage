package org.mage.plugins.card.dl;

import mage.client.remote.XmageURLConnection;
import mage.util.ThreadUtils;
import mage.util.XmageThreadFactory;
import org.apache.log4j.Logger;
import org.jetlang.channels.Channel;
import org.jetlang.channels.MemoryChannel;
import org.jetlang.core.Callback;
import org.jetlang.fibers.Fiber;
import org.jetlang.fibers.PoolFiberFactory;
import org.mage.plugins.card.dl.DownloadJob.Destination;
import org.mage.plugins.card.dl.DownloadJob.State;
import org.mage.plugins.card.dl.lm.AbstractLaternaBean;

import javax.swing.*;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Download: symbols download service
 *
 * @author Clemens Koza, JayDi85
 */
public class Downloader extends AbstractLaternaBean {

    private static final Logger logger = Logger.getLogger(Downloader.class);

    private final List<DownloadJob> jobs = properties.list("jobs");
    private final Channel<DownloadJob> jobsQueue = new MemoryChannel<>();
    private CountDownLatch worksCount = null;

    private final ExecutorService pool = Executors.newCachedThreadPool(
            new XmageThreadFactory(ThreadUtils.THREAD_PREFIX_CLIENT_SYMBOLS_DOWNLOADER, false)
    );
    private final List<Fiber> fibers = new ArrayList<>();

    public Downloader() {
        // prepare 10 threads and start to waiting new download jobs from queue
        // TODO: gatherer website has download rate limits, so limit max threads as temporary solution
        int maxThreads = 3;
        PoolFiberFactory f = new PoolFiberFactory(pool);
        for (int i = 0, numThreads = maxThreads; i < numThreads; i++) {
            Fiber fiber = f.create();
            fiber.start();
            fibers.add(fiber);
            jobsQueue.subscribe(fiber, new DownloadCallback());
        }
    }

    public void cleanup() {
        // close all threads and jobs
        for (DownloadJob j : jobs) {
            switch (j.getState()) {
                case NEW:
                case PREPARING:
                case WORKING:
                    j.setState(State.ABORTED);
                    break;
                case ABORTED:
                case FINISHED:
                    // don't change state
                    break;
            }
        }

        for (Fiber f : fibers) {
            f.dispose();
        }
        pool.shutdown();

        while (worksCount.getCount() != 0) {
            worksCount.countDown();
        }
    }

    public void add(DownloadJob job) {
        if (job.getState() == State.PREPARING) {
            throw new IllegalArgumentException("Job already preparing");
        }
        if (job.getState() == State.WORKING) {
            throw new IllegalArgumentException("Job already working");
        }
        if (job.getState() == State.FINISHED) {
            throw new IllegalArgumentException("Job already finished");
        }
        job.setState(State.NEW);
        jobs.add(job);
    }

    public void publishAllJobs() {
        worksCount = new CountDownLatch(jobs.size());
        for (DownloadJob job : jobs) {
            jobsQueue.publish(job);
        }
    }

    public void waitFinished() {
        try {
            while (worksCount.getCount() != 0) {
                worksCount.await(60, TimeUnit.SECONDS);

                if (worksCount.getCount() != 0) {
                    logger.warn("Download: symbols downloading too long");
                }
            }
        } catch (InterruptedException e) {
            logger.error("Download: symbols downloading must be stopped");
        }
    }

    public List<DownloadJob> getJobs() {
        return jobs;
    }

    /**
     * Performs the download job: Transfers data from source to
     * destination and updates the download job's state to reflect the
     * progress.
     */
    private class DownloadCallback implements Callback<DownloadJob> {

        @Override
        public void onMessage(DownloadJob job) {

            // each 10 threads gets same jobs, but take to work only one NEW
            synchronized (job) {
                if (job.getState() == State.NEW) {
                    // take new job
                    job.doPrepareAndStartWork();
                    if (job.getState() != State.WORKING) {
                        logger.warn("Download: can't prepare symbols download job: " + job.getName() + ", reason: " + job.getError());
                        worksCount.countDown();
                        return;
                    }
                } else {
                    // skip job (other thread takes it)
                    return;
                }
            }

            // real work for new job
            // download and save data
            try {
                Destination dst = job.getDestination();
                BoundedRangeModel progress = job.getProgress();

                if (dst.isValid() && !job.isForceToDownload()) {
                    // already done
                    progress.setMaximum(1);
                    progress.setValue(1);
                } else {
                    // need to download

                    // clean local file
                    if (dst.exists()) {
                        try {
                            dst.delete();
                        } catch (IOException e) {
                            logger.warn("Download: can't delete old file " + e, e);
                        }
                    }

                    // download
                    // start debug here with breakpoint like job.getName().contains("C/B")
                    XmageURLConnection connection = new XmageURLConnection(job.getUrl());
                    connection.startConnection();
                    if (connection.isConnected()) {
                        // start downloading
                        connection.connect();
                        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                            // all fine, can continue and save
                            progress.setMaximum(connection.getContentLength());
                            try (InputStream inputStream = connection.getGoodResponseAsStream();
                                 OutputStream outputStream = new BufferedOutputStream(dst.open())) {
                                byte[] buf = new byte[8 * 1024];
                                int total = 0;
                                for (int len; (len = inputStream.read(buf)) != -1; ) {
                                    // fast cancel
                                    if (job.getState() == State.ABORTED) {
                                        throw new IOException("job was aborted");
                                    }
                                    progress.setValue(total += len);
                                    outputStream.write(buf, 0, len);
                                }
                            } catch (IOException e) {
                                // something bad on downloading
                                logger.warn("Download: " + job.getName() + " - catch error on downloading network resource "
                                        + job.getUrl() + " - " + e, e);
                            } finally {
                                // clean up
                                if (!dst.isValid()) {
                                    logger.warn("Download: " + job.getName() + " - downloaded invalid network resource (not exists?) " + job.getUrl());
                                    dst.delete();
                                }
                            }
                        } else {
                            // something bad with resource on server (example: wrong url)
                            logger.warn("Download: " + job.getName() + " - can't find network resource " + job.getUrl());
                        }
                    } else {
                        // something bad with network (example: can't connect due bad network)
                        logger.warn("Download: " + job.getName() + " - can't connect to network resource " + job.getUrl());
                    }
                }

                // all done
                job.setState(State.FINISHED);
            } catch (Exception e) {
                // TODO: save error in other logger.warn?
                job.setError(e);
                logger.warn("Download: " + job.getName() + " - unknown error for network resource " + job.getUrl() + " - " + e, e);
            } finally {
                worksCount.countDown();
            }
        }
    }
}
