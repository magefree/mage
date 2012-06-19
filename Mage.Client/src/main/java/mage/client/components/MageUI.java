package mage.client.components;

import java.awt.Component;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;

public class MageUI {

    private Map<MageComponents, Component> ui = new HashMap<MageComponents, Component>();
    private Map<MageComponents, Object> sync = new HashMap<MageComponents, Object>();

    public JButton getButton(MageComponents name) throws InterruptedException {
        //System.out.println("request for " + name);
        Object buttonSync;
        synchronized (ui) {
            if (ui.containsKey(name)) {
                //System.out.println("clicking " + name);
                return (JButton) ui.get(name);
            } else {
                buttonSync = new Object();
                sync.put(name, buttonSync);
            }
        }

        synchronized (buttonSync) {
            //System.out.println("waiting " + name + " to be created");
            buttonSync.wait();
            //System.out.println(name + "was created");
            if (!ui.containsKey(name)) {
                throw new IllegalStateException("Component wasn't initialized. This should not happen.");
            }
            return (JButton) ui.get(name);
        }

    }

    public Component getComponent(MageComponents name) throws InterruptedException {
        Object componentSync;
        synchronized (ui) {
            if (ui.containsKey(name)) {
                return ui.get(name);
            } else {
                componentSync = new Object();
                sync.put(name, componentSync);
            }
        }

        synchronized (componentSync) {
            componentSync.wait();
            if (!ui.containsKey(name)) {
                throw new IllegalStateException("Component wasn't initialized. This should not happen.");
            }
            return ui.get(name);
        }

    }

    public void addButton(MageComponents name, JButton button) {
        synchronized (ui) {
            //System.out.println("added " + name);
            ui.put(name, button);
            if (sync.containsKey(name)) {
                synchronized (sync.get(name)) {
                    //System.out.println("notifyAll - " + name);
                    sync.get(name).notifyAll();    
                }
            }
        }
    }

    public void addComponent(MageComponents name, Component component) {
        synchronized (ui) {
            ui.put(name, component);
            if (sync.containsKey(name)) {
                synchronized (sync.get(name)) {
                    sync.get(name).notifyAll();    
                }
            }
        }
    }

    public void doClick(MageComponents name) throws InterruptedException {
        doClick(name, 0);
    }

    public void doClick(MageComponents name, int waitBeforeClick) throws InterruptedException {
        final JButton j = getButton(name);
        Thread.sleep(waitBeforeClick);
        while (!j.isEnabled()) {
            Thread.sleep(10);
        }
        Thread t = new Thread(new Runnable()  {
            @Override
            public void run() {
                j.doClick();
            }
        });
        t.start();
    }
}
