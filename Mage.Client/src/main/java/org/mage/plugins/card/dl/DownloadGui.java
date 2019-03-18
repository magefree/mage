package org.mage.plugins.card.dl;

import org.mage.plugins.card.dl.DownloadJob.State;

import javax.swing.*;
import java.awt.*;
import java.beans.IndexedPropertyChangeEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;


/**
 * Downloader GUI to control and show progress
 *
 * @author Clemens Koza
 */
public class DownloadGui extends JPanel {
    private static final long serialVersionUID = -7346572382493844327L;

    private final Downloader downloader;
    private final DownloadListener listener = new DownloadListener();
    private final BoundedRangeModel progressModel = new DefaultBoundedRangeModel(0, 0, 0, 0);
    private final JProgressBar progressBar = new JProgressBar(progressModel);

    private final Map<DownloadJob, DownloadPanel> jobPanels = new HashMap<>();
    private final JPanel basicPanel = new JPanel();

    public DownloadGui(Downloader downloader) {
        super(new BorderLayout());
        this.downloader = downloader;
        downloader.addPropertyChangeListener(listener);

        JPanel p = new JPanel(new BorderLayout());
        p.setBorder(BorderFactory.createTitledBorder("Progress:"));
        p.add(progressBar);
        JButton closeButton = new JButton("X");
        closeButton.addActionListener(e -> {
            this.downloader.cleanup();
        });
        p.add(closeButton, BorderLayout.EAST);
        add(p, BorderLayout.NORTH);

        basicPanel.setLayout(new BoxLayout(basicPanel, BoxLayout.Y_AXIS));
        JScrollPane pane = new JScrollPane(basicPanel);
        pane.setPreferredSize(new Dimension(500, 300));
        add(pane);
        for (int i = 0; i < downloader.getJobs().size(); i++) {
            addJob(i, downloader.getJobs().get(i));
        }
    }

    public Downloader getDownloader() {
        return downloader;
    }

    private class DownloadListener implements PropertyChangeListener {

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            String name = evt.getPropertyName();
            if (evt.getSource() instanceof DownloadJob) {
                // one job changes
                DownloadPanel panel = jobPanels.get(evt.getSource());
                switch (name) {
                    case "state":
                        if (evt.getOldValue() == State.FINISHED || evt.getOldValue() == State.ABORTED) {
                            // started
                            changeProgress(-1, 0);
                        }
                        if (evt.getNewValue() == State.FINISHED || evt.getOldValue() == State.ABORTED) {
                            // finished
                            changeProgress(+1, 0);
                        }
                        if (panel != null) {
                            panel.setVisible(panel.getJob().getState() != State.FINISHED);
                            panel.revalidate();
                        }
                        break;
                    case "message":
                        if (panel != null) {
                            JProgressBar bar = panel.getBar();
                            String message = panel.getJob().getMessage();
                            bar.setStringPainted(message != null);
                            bar.setString(message);
                        }
                        break;
                }
            } else if (evt.getSource() == downloader) {
                // all jobs changes (add/delete)
                if ("jobs".equals(name)) {
                    IndexedPropertyChangeEvent ev = (IndexedPropertyChangeEvent) evt;
                    int index = ev.getIndex();

                    DownloadJob oldValue = (DownloadJob) ev.getOldValue();
                    if (oldValue != null) {
                        removeJob(index, oldValue);
                    }

                    DownloadJob newValue = (DownloadJob) ev.getNewValue();
                    if (newValue != null) {
                        addJob(index, newValue);
                    }
                }
            }
        }
    }

    private synchronized void addJob(int index, DownloadJob job) {
        job.addPropertyChangeListener(listener);
        changeProgress(0, +1);
        DownloadPanel p = new DownloadPanel(job);
        jobPanels.put(job, p);
        basicPanel.add(p, index);
        basicPanel.revalidate();
    }

    private synchronized void removeJob(int index, DownloadJob job) {
        assert jobPanels.get(job) == basicPanel.getComponent(index);
        job.removePropertyChangeListener(listener);
        changeProgress(0, -1);
        jobPanels.remove(job);
        basicPanel.remove(index);
        basicPanel.revalidate();
    }

    private synchronized void changeProgress(int progress, int total) {
        progress += progressModel.getValue();
        total += progressModel.getMaximum();
        progressModel.setMaximum(total);
        progressModel.setValue(progress);
        this.progressBar.setStringPainted(true);
        this.progressBar.setString(progress + "/" + total);
    }

    private class DownloadPanel extends JPanel {
        private static final long serialVersionUID = 1187986738303477168L;

        private final DownloadJob job;
        private final JProgressBar bar;

        DownloadPanel(DownloadJob job) {
            super(new BorderLayout());
            this.job = job;

            setBorder(BorderFactory.createTitledBorder(job.getName()));
            add(bar = new JProgressBar(job.getProgress()));
            JButton b = new JButton("X");
            b.addActionListener(e -> {
                switch (this.job.getState()) {
                    case NEW:
                    case PREPARING:
                    case WORKING:
                        this.job.setState(State.ABORTED);
                }
            });
            add(b, BorderLayout.EAST);

            if (job.getState() == State.FINISHED | job.getState() == State.ABORTED) {
                changeProgress(+1, 0);
            }
            setVisible(job.getState() != State.FINISHED);

            String message = job.getMessage();
            bar.setStringPainted(message != null);
            bar.setString(message);

            Dimension d = getPreferredSize();
            d.width = Integer.MAX_VALUE;
            setMaximumSize(d);
        }

        DownloadJob getJob() {
            return job;
        }

        JProgressBar getBar() {
            return bar;
        }
    }
}
