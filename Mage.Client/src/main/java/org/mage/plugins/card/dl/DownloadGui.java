/**
 * DownloadGui.java
 * 
 * Created on 25.08.2010
 */

package org.mage.plugins.card.dl;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.IndexedPropertyChangeEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoundedRangeModel;
import javax.swing.BoxLayout;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;

import org.mage.plugins.card.dl.DownloadJob.State;


/**
 * The class DownloadGui.
 * 
 * @version V0.0 25.08.2010
 * @author Clemens Koza
 */
public class DownloadGui extends JPanel {
    private static final long                     serialVersionUID = -7346572382493844327L;

    private final Downloader                      d;
    private final DownloadListener                l                = new DownloadListener();
    private final BoundedRangeModel               model            = new DefaultBoundedRangeModel(0, 0, 0, 0);
    private final JProgressBar                    progress         = new JProgressBar(model);

    private final Map<DownloadJob, DownloadPanel> progresses       = new HashMap<DownloadJob, DownloadPanel>();
    private final JPanel                          panel            = new JPanel();

    public DownloadGui(Downloader d) {
        super(new BorderLayout());
        this.d = d;
        d.addPropertyChangeListener(l);

        JPanel p = new JPanel(new BorderLayout());
        p.setBorder(BorderFactory.createTitledBorder("Progress:"));
        p.add(progress);
        JButton b = new JButton("X");
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getDownloader().dispose();
            }
        });
        p.add(b, BorderLayout.EAST);
        add(p, BorderLayout.NORTH);

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JScrollPane pane = new JScrollPane(panel);
        pane.setPreferredSize(new Dimension(500, 300));
        add(pane);
        for(int i = 0; i < d.getJobs().size(); i++)
            addJob(i, d.getJobs().get(i));
    }

    public Downloader getDownloader() {
        return d;
    }

    private class DownloadListener implements PropertyChangeListener {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            String name = evt.getPropertyName();
            if(evt.getSource() instanceof DownloadJob) {
                DownloadPanel p = progresses.get(evt.getSource());
                if("state".equals(name)) {
                    if(evt.getOldValue() == State.FINISHED || evt.getOldValue() == State.ABORTED) {
                        changeProgress(-1, 0);
                    }
                    if(evt.getNewValue() == State.FINISHED || evt.getOldValue() == State.ABORTED) {
                        changeProgress(+1, 0);
                    }
                    if(p != null) {
                        p.setVisible(p.getJob().getState() != State.FINISHED);
                        p.revalidate();
                    }
                } else if("message".equals(name)) {
                    if(p != null) {
                        JProgressBar bar = p.getBar();
                        String message = p.getJob().getMessage();
                        bar.setStringPainted(message != null);
                        bar.setString(message);
                    }
                }
            } else if(evt.getSource() == d) {
                if("jobs".equals(name)) {
                    IndexedPropertyChangeEvent ev = (IndexedPropertyChangeEvent) evt;
                    int index = ev.getIndex();

                    DownloadJob oldValue = (DownloadJob) ev.getOldValue();
                    if(oldValue != null) removeJob(index, oldValue);

                    DownloadJob newValue = (DownloadJob) ev.getNewValue();
                    if(newValue != null) addJob(index, newValue);
                }
            }
        }
    }

    private synchronized void addJob(int index, DownloadJob job) {
        job.addPropertyChangeListener(l);
        changeProgress(0, +1);
        DownloadPanel p = new DownloadPanel(job);
        progresses.put(job, p);
        panel.add(p, index);
        panel.revalidate();
    }

    private synchronized void removeJob(int index, DownloadJob job) {
        assert progresses.get(job) == panel.getComponent(index);
        job.removePropertyChangeListener(l);
        changeProgress(0, -1);
        progresses.remove(job);
        panel.remove(index);
        panel.revalidate();
    }

    private synchronized void changeProgress(int progress, int total) {
        progress += model.getValue();
        total += model.getMaximum();
        model.setMaximum(total);
        model.setValue(progress);
        this.progress.setStringPainted(true);
        this.progress.setString(progress + "/" + total);
    }

    private class DownloadPanel extends JPanel {
        private static final long serialVersionUID = 1187986738303477168L;

        private DownloadJob       job;
        private JProgressBar      bar;

        public DownloadPanel(DownloadJob job) {
            super(new BorderLayout());
            this.job = job;

            setBorder(BorderFactory.createTitledBorder(job.getName()));
            add(bar = new JProgressBar(job.getProgress()));
            JButton b = new JButton("X");
            b.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    switch(getJob().getState()) {
                        case NEW:
                        case WORKING:
                            getJob().setState(State.ABORTED);
                    }
                }
            });
            add(b, BorderLayout.EAST);

            if(job.getState() == State.FINISHED | job.getState() == State.ABORTED) {
                changeProgress(+1, 0);
            }
            setVisible(job.getState() != State.FINISHED);

            String message = job.getMessage();
            bar.setStringPainted(message != null);
            bar.setString(message);

            Dimension d = getPreferredSize();
            d.width = Integer.MAX_VALUE;
            setMaximumSize(d);
//            d.width = 500;
//            setMinimumSize(d);
        }

        public DownloadJob getJob() {
            return job;
        }

        public JProgressBar getBar() {
            return bar;
        }
    }
}
