package mage.client.components.tray;

import mage.client.MageFrame;
import org.apache.log4j.Logger;
import org.mage.plugins.card.utils.impl.ImageManagerImpl;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author noxx
 */
public class MageTray {

    private static final MageTray instance = new MageTray();

    private static final Logger log = Logger.getLogger(MageTray.class);

    private Image mainImage;
    private Image flashedImage;
    private TrayIcon trayIcon;

    private static int state = 0;

    public static MageTray getInstance() {
        return instance;
    }

    public void install() {
        if (!SystemTray.isSupported()) {
            log.warn("SystemTray is not supported");
            return;
        }

        try {
            mainImage = ImageManagerImpl.getInstance().getAppSmallImage();
            flashedImage = ImageManagerImpl.getInstance().getAppFlashedImage();
            trayIcon = new TrayIcon(mainImage);
            trayIcon.setImageAutoSize(true);

            trayIcon.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    stopBlink();
                    MageFrame frame = MageFrame.getInstance();
                    frame.setVisible(true);
                    frame.setState(Frame.NORMAL);
                }
            });

            final SystemTray tray = SystemTray.getSystemTray();

            final PopupMenu popup = new PopupMenu();

            MenuItem imagesItem = new MenuItem("Download images");
            MenuItem iconsItem = new MenuItem("Download icons");
            MenuItem stopBlinkItem = new MenuItem("Stop blinking");
            MenuItem preferencesItem = new MenuItem("Preferences...");
            MenuItem aboutItem = new MenuItem("About Mage");
            MenuItem exitItem = new MenuItem("Exit");

            imagesItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    MageFrame.getInstance().btnImagesActionPerformed(null);
                }
            });

            iconsItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    MageFrame.getInstance().btnSymbolsActionPerformed(null);
                }
            });

            stopBlinkItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    stopBlink();
                }
            });

            preferencesItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    MageFrame.getInstance().btnPreferencesActionPerformed(null);
                }
            });

            aboutItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    MageFrame.getInstance().btnAboutActionPerformed(null);
                }
            });

            exitItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    MageFrame.getInstance().exitApp();
                }
            });

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
                return;
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
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                int i = 0;
                                while (state != 3) {
                                    trayIcon.setImage(i == 0 ? mainImage : flashedImage);
                                    Thread.sleep(600);
                                    i = i == 0 ? 1 : 0;
                                }
                                trayIcon.setImage(mainImage);
                                state = 0;
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

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
}
