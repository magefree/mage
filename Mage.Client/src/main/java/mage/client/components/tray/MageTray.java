package mage.client.components.tray;

import java.awt.*;
import java.util.concurrent.TimeUnit;
import mage.client.MageFrame;
import org.apache.log4j.Logger;
import org.mage.plugins.card.utils.impl.ImageManagerImpl;

/**
 * @author noxx
 */
public enum MageTray {

    instance;

    private static final Logger log = Logger.getLogger(MageTray.class);

    private Image mainImage;
    private Image flashedImage;
    private TrayIcon trayIcon;

    private int state = 0;

    public void install() {
        if (!SystemTray.isSupported()) {
            log.warn("SystemTray is not supported");
            return;
        }

        try {
            mainImage = ImageManagerImpl.instance.getAppSmallImage();
            flashedImage = ImageManagerImpl.instance.getAppFlashedImage();
            trayIcon = new TrayIcon(mainImage);
            trayIcon.setImageAutoSize(true);

            trayIcon.addActionListener(e -> {
                stopBlink();
                MageFrame frame = MageFrame.getInstance();
                frame.setVisible(true);
                frame.setState(Frame.NORMAL);
            });

            final SystemTray tray = SystemTray.getSystemTray();

            final PopupMenu popup = new PopupMenu();

            MenuItem imagesItem = new MenuItem("Download images");
            MenuItem iconsItem = new MenuItem("Download icons");
            MenuItem stopBlinkItem = new MenuItem("Stop blinking");
            MenuItem preferencesItem = new MenuItem("Preferences...");
            MenuItem aboutItem = new MenuItem("About Mage");
            MenuItem exitItem = new MenuItem("Exit");

            imagesItem.addActionListener(e -> MageFrame.getInstance().downloadImages());

            iconsItem.addActionListener(e -> MageFrame.getInstance().downloadAdditionalResources());

            stopBlinkItem.addActionListener(e -> stopBlink());

            preferencesItem.addActionListener(e -> MageFrame.getInstance().btnPreferencesActionPerformed(null));

            aboutItem.addActionListener(e -> MageFrame.getInstance().btnAboutActionPerformed(null));

            exitItem.addActionListener(e -> MageFrame.getInstance().exitApp());

            popup.add(imagesItem);
            popup.add(iconsItem);
            popup.add(stopBlinkItem);
            popup.add(preferencesItem);
            popup.addSeparator();
            popup.add(aboutItem);
            popup.addSeparator();
            popup.add(exitItem);

            trayIcon.setPopupMenu(popup);

            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                log.error("TrayIcon could not be added: ", e);
            }

        } catch (Exception e) {
            log.error(e);
        }
    }

    public synchronized void blink() {
        if (state == 0) {
            synchronized (MageTray.class) {
                if (state == 0) {
                    state = 1;
                    new Thread(() -> {
                        try {
                            int i = 0;
                            while (state != 3) {
                                trayIcon.setImage(i == 0 ? mainImage : flashedImage);
                                TimeUnit.MILLISECONDS.sleep(600);
                                i = i == 0 ? 1 : 0;
                            }
                            trayIcon.setImage(mainImage);
                            state = 0;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }).start();
                }
            }
        }
    }

    public void stopBlink() {
        if (state == 1) {
            state = 3;
        }
    }

    public void displayMessage(String message) {
        if (trayIcon != null) {
            trayIcon.displayMessage("Mage", message, TrayIcon.MessageType.INFO);
        }
    }
}
