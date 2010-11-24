/**
 * Downloader.java
 * 
 * Created on 25.08.2010
 */

package org.mage.plugins.card.dl;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.BoundedRangeModel;

import org.apache.log4j.Logger;
import org.jetlang.channels.Channel;
import org.jetlang.channels.MemoryChannel;
import org.jetlang.core.Callback;
import org.jetlang.core.Disposable;
import org.jetlang.fibers.Fiber;
import org.jetlang.fibers.PoolFiberFactory;
import org.mage.plugins.card.dl.DownloadJob.Destination;
import org.mage.plugins.card.dl.DownloadJob.Source;
import org.mage.plugins.card.dl.DownloadJob.State;
import org.mage.plugins.card.dl.lm.AbstractLaternaBean;



/**
 * The class Downloader.
 * 
 * @version V0.0 25.08.2010
 * @author Clemens Koza
 */
public class Downloader extends AbstractLaternaBean implements Disposable {
	
	private static final Logger log = Logger.getLogger(Downloader.class);
	
    private final List<DownloadJob>    jobs    = properties.list("jobs");
    private final Channel<DownloadJob> channel = new MemoryChannel<DownloadJob>();
    
    private final ExecutorService      pool    = Executors.newCachedThreadPool();
    private final List<Fiber>          fibers  = new ArrayList<Fiber>();
    
    public Downloader() {
        PoolFiberFactory f = new PoolFiberFactory(pool);
        //subscribe multiple fibers for parallel execution
        for(int i = 0, numThreads = 10; i < numThreads; i++) {
            Fiber fiber = f.create();
            fiber.start();
            fibers.add(fiber);
            channel.subscribe(fiber, new DownloadCallback());
        }
    }
    
    @Override
    public void dispose() {
        for(DownloadJob j:getJobs()) {
            switch(j.getState()) {
                case NEW:
                case WORKING:
                    j.setState(State.ABORTED);
            }
        }
        
        for(Fiber f:fibers)
            f.dispose();
        pool.shutdown();
    }
    
    @Override
    protected void finalize() throws Throwable {
        dispose();
        super.finalize();
    }
    
    public void add(DownloadJob job) {
        if(job.getState() == State.WORKING) throw new IllegalArgumentException("Job already running");
        if(job.getState() == State.FINISHED) throw new IllegalArgumentException("Job already finished");
        job.setState(State.NEW);
        jobs.add(job);
        channel.publish(job);
    }
    
    public List<DownloadJob> getJobs() {
        return jobs;
    }
    
    /**
     * Performs the download job: Transfers data from {@link Source} to {@link Destination} and updates the
     * download job's state to reflect the progress.
     */
    private class DownloadCallback implements Callback<DownloadJob> {
        @Override
        public void onMessage(DownloadJob job) {
            //the job won't be processed by multiple threads
            synchronized(job) {
                if(job.getState() != State.NEW) return;
                job.setState(State.WORKING);
            }
            try {
                Source src = job.getSource();
                Destination dst = job.getDestination();
                BoundedRangeModel progress = job.getProgress();
                
                if(dst.exists()) {
                    progress.setMaximum(1);
                    progress.setValue(1);
                } else {
                    progress.setMaximum(src.length());
                    InputStream is = new BufferedInputStream(src.open());
                    try {
                        OutputStream os = new BufferedOutputStream(dst.open());
                        try {
                            byte[] buf = new byte[8 * 1024];
                            int total = 0;
                            for(int len; (len = is.read(buf)) != -1;) {
                                if(job.getState() == State.ABORTED) throw new IOException("Job was aborted");
                                progress.setValue(total += len);
                                os.write(buf, 0, len);
                            }
                        } catch(IOException ex) {
                            try {
                                dst.delete();
                            } catch(IOException ex1) {
                                log.warn("While deleting", ex1);
                            }
                            throw ex;
                        } finally {
                            try {
                                os.close();
                            } catch(IOException ex) {
                                log.warn("While closing", ex);
                            }
                        }
                    } finally {
                        try {
                            is.close();
                        } catch(IOException ex) {
                            log.warn("While closing", ex);
                        }
                    }
                }
                job.setState(State.FINISHED);
            } catch(IOException ex) {
                job.setError(ex);
            }
        }
    }
}
