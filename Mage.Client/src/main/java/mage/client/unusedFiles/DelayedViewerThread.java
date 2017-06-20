
package mage.client.unusedFiles;
//package mage.client.thread;

import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class DelayedViewerThread extends Thread {
    private static final DelayedViewerThread instance = new DelayedViewerThread();

    public static DelayedViewerThread getInstance() {
        return instance;
    }

    private final Map<Component, Long> delayedViewers;

    protected DelayedViewerThread() {
        delayedViewers = new HashMap<>();
        start();
    }

    public synchronized void show(Component component, long delay) {
        delayedViewers.put(component, System.currentTimeMillis() + delay);
        notifyAll();
    }

    public synchronized void hide(Component component) {
        delayedViewers.remove(component);
        component.setVisible(false);
    }

    @Override
    public synchronized void run() {
        while (true) {
            try {
                if (delayedViewers.isEmpty()) {
                    wait();
                }
                final long time = System.currentTimeMillis();
                for (Iterator<Component> it = delayedViewers.keySet().iterator(); it.hasNext();) {
                    Component component = it.next();
                    final long delayedTime = delayedViewers.get(component);
                    if (delayedTime <= time) {
                        component.setVisible(true);
                        it.remove();
                    }
                }
                wait(100);
            } catch (final InterruptedException ex) {
                System.out.println("Interrupted : " + ex.getMessage());
            }
        }
    }
}
