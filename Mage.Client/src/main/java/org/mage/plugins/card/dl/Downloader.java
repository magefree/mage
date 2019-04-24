package org.mage.plugins.card.dl;

import org.apache.log4j.Logger;
import org.jetlang.channels.Channel;
import org.jetlang.channels.MemoryChannel;
import org.jetlang.core.Callback;
import org.jetlang.fibers.Fiber;
import org.jetlang.fibers.PoolFiberFactory;
import org.mage.plugins.card.dl.DownloadJob.Destination;
import org.mage.plugins.card.dl.DownloadJob.Source;
import org.mage.plugins.card.dl.DownloadJob.State;
import org.mage.plugins.card.dl.lm.AbstractLaternaBean;

import javax.swing.*;
import java.io.*;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Downloader
 *
 * @author Clemens Koza, JayDi85
 */
public class Downloader extends AbstractLaternaBean {

    private static final Logger logger = Logger.getLogger(Downloader.class);

    private final List<DownloadJob> jobs = properties.list("jobs");
    private final Channel<DownloadJob> jobsQueue = new MemoryChannel<>();
    private CountDownLatch worksCount = null;

    private final ExecutorService pool = Executors.newCachedThreadPool();
    private final List<Fiber> fibers = new ArrayList<>();

    public Downloader() {
        // prepare 10 threads and start to waiting new download jobs from queue
        PoolFiberFactory f = new PoolFiberFactory(pool);
        for (int i = 0, numThreads = 10; i < numThreads; i++) {
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
                    logger.warn("Symbols download too long...");
                }
            }
        } catch (InterruptedException e) {
            logger.error("Need to stop symbols download...");
        }
    }

    public List<DownloadJob> getJobs() {
        return jobs;
    }

    /**
     * Performs the download job: Transfers data from {@link Source} to
     * {@link Destination} and updates the download job's state to reflect the
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
                        logger.warn("Can't prepare symbols download job: " + job.getName());
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
                Source src = job.getSource();
                Destination dst = job.getDestination();
                BoundedRangeModel progress = job.getProgress();

                if (dst.isValid()) {
                    // already done
                    progress.setMaximum(1);
                    progress.setValue(1);
                } else {
                    // downloading
                    if (dst.exists()) {
                        try {
                            dst.delete();
                        } catch (IOException ex1) {
                            logger.warn("While deleting not valid file", ex1);
                        }
                    }
                    progress.setMaximum(src.length());
                    InputStream is = new BufferedInputStream(src.open());
                    try {
                        OutputStream os = new BufferedOutputStream(dst.open());
                        try {
                            byte[] buf = new byte[8 * 1024];
                            int total = 0;
                            for (int len; (len = is.read(buf)) != -1; ) {
                                if (job.getState() == State.ABORTED) {
                                    throw new IOException("Job was aborted");
                                }
                                progress.setValue(total += len);
                                os.write(buf, 0, len);
                            }
                        } catch (IOException ex) {
                            try {
                                dst.delete();
                            } catch (IOException ex1) {
                                logger.warn("While deleting", ex1);
                            }
                            throw ex;
                        } finally {
                            try {
                                os.close();
                                if (!dst.isValid()) {
                                    dst.delete();
                                    logger.warn("Resource not found " + job.getName() + " from " + job.getSource().toString());
                                }
                            } catch (IOException ex) {
                                logger.warn("While closing", ex);
                            }
                        }
                    } finally {
                        try {
                            is.close();
                        } catch (IOException ex) {
                            logger.warn("While closing", ex);
                        }
                    }
                }
                job.setState(State.FINISHED);
            } catch (ConnectException ex) {
                String message;
                if (ex.getMessage() != null) {
                    message = ex.getMessage();
                } else {
                    message = "Unknown error";
                }
                logger.warn("Error resource download " + job.getName() + " from " + job.getSource().toString() + ": " + message);
            } catch (IOException ex) {
                job.setError(ex);
            } finally {
                worksCount.countDown();
            }
        }
    }
}
