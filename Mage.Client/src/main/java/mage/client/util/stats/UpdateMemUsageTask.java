package mage.client.util.stats;

import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import javax.swing.*;
import org.apache.log4j.Logger;

/**
 * This updates the mem usage info in the Mage client every
 * MEM_USAGE_UPDATE_TIME ms.
 *
 * @author noxx
 */
public class UpdateMemUsageTask extends SwingWorker<Void, Float> {

    private static final int MEM_USAGE_UPDATE_TIME = 2000;

    private final JLabel jLabelToDisplayInfo;

    private static final Logger logger = Logger.getLogger(UpdateMemUsageTask.class);

    public UpdateMemUsageTask(JLabel jLabelToDisplayInfo) {
        this.jLabelToDisplayInfo = jLabelToDisplayInfo;
    }

    @Override
    protected Void doInBackground() throws Exception {
        while (!isCancelled()) {
            float memUsage = MemoryUsageStatUtil.getMemoryFreeStatPercentage();
            this.publish(memUsage >= 0 ? memUsage : null);
            Thread.sleep(MEM_USAGE_UPDATE_TIME);
        }
        return null;
    }

    @Override
    protected void process(List<Float> chunks) {
        if (chunks != null && chunks.size() > 0) {
            Float memUsage = chunks.get(chunks.size() - 1);
            if (memUsage != null) {
                jLabelToDisplayInfo.setText(Math.round(memUsage) + "% Mem free");
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
