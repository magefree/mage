package mage.client.util.stats;

import java.awt.*;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import javax.swing.*;
import org.apache.log4j.Logger;

/**
 * This updates the mem usage info in the Mage client every
 * MEM_USAGE_UPDATE_TIME ms.
 *
 * @author noxx, JayDi85
 */

public class UpdateMemUsageTask extends SwingWorker<Void, MemoryStats> {

    private static final int MEM_USAGE_UPDATE_TIME = 2000;
    private static final int MEM_USAGE_WARNING_PERCENT = 80; // red color for mem used more than xx%

    private final JLabel jLabelToDisplayInfo;

    private static final Logger logger = Logger.getLogger(UpdateMemUsageTask.class);

    public UpdateMemUsageTask(JLabel jLabelToDisplayInfo) {
        this.jLabelToDisplayInfo = jLabelToDisplayInfo;
        this.jLabelToDisplayInfo.setToolTipText("<html>Memory usage statistics");
    }

    @Override
    protected Void doInBackground() throws Exception {
        while (!isCancelled()) {
            MemoryStats memoryStats = new MemoryStats(0, 0, 0, 0);

            Runtime runtime = Runtime.getRuntime();
            if (runtime.maxMemory() != 0) {
                memoryStats.setMaxAvailable(runtime.maxMemory());
                memoryStats.setAvailable(runtime.totalMemory());
                memoryStats.setFree(runtime.freeMemory());
                memoryStats.setUsed(runtime.totalMemory() - runtime.freeMemory());
            }

            this.publish(memoryStats);
            TimeUnit.MILLISECONDS.sleep(MEM_USAGE_UPDATE_TIME);
        }
        return null;
    }

    @Override
    protected void process(List<MemoryStats> chunks) {
        if (chunks != null && !chunks.isEmpty()) {
            MemoryStats memoryStats = chunks.get(chunks.size() - 1);
            if (memoryStats != null) {
                int max = Math.round(memoryStats.getMaxAvailable() / (1000 * 1000));
                int used = Math.round(memoryStats.getUsed() / (1000 * 1000));
                int total = Math.round(memoryStats.getAvailable() / (1000 * 1000));
                int percent = 0;
                if(max != 0){
                    percent = Math.round((used * 100) / max);
                }

                jLabelToDisplayInfo.setText("Memory used: " + percent + "% (" + used + " of " + max + " MB)");
                String warning = "";
                String optimizeHint = "<br><br>If you see low memory warning and have free system memory then try to increase max limit in launcher settings:<br>"
                        + " - Go to <i>launcher -> settings -> java tab</i>;<br>"
                        + " - Find <i>client java options</i> (it's may contain many commands);<br>"
                        + " - Find max available memory setting: <i>-Xmx256m</i> (it's must start with <b>-Xmx</b>);<br>"
                        + " - Increase number in that value from 256 to 512, or 512 to 1024;<br>"
                        + " - Save new settings and restart application.";
                if(percent >= MEM_USAGE_WARNING_PERCENT){
                    jLabelToDisplayInfo.setForeground(Color.red);
                    warning = "<br><br><b>WARNING</b><br>"
                            + "Application memory limit almost reached. Errors and freezes are very possible.";

                }else{
                    jLabelToDisplayInfo.setForeground(Color.black);
                }

                this.jLabelToDisplayInfo.setToolTipText("<html>Memory usage statistics" + warning + optimizeHint);

                return;
            }
        }
        jLabelToDisplayInfo.setText("Mem usage unknown");
    }

    @Override
    protected void done() {
        try {
            get();
        } catch (InterruptedException | ExecutionException ex) {
            logger.fatal("Update Memory Usage error", ex);
        } catch (CancellationException ex) {
        }
    }

}
