package org.mage.plugins.theme;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Locale;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.*;
import mage.client.dialog.PreferencesDialog;
import mage.components.ImagePanel;
import mage.components.ImagePanelStyle;
import mage.interfaces.plugin.ThemePlugin;
import mage.util.RandomUtil;
import net.xeoh.plugins.base.annotations.PluginImplementation;
import net.xeoh.plugins.base.annotations.events.PluginLoaded;
import net.xeoh.plugins.base.annotations.meta.Author;
import org.apache.log4j.Logger;

@PluginImplementation
@Author(name = "nantuko")
/* udpated by Noahsark */
public class ThemePluginImpl implements ThemePlugin {

    private static final Logger log = Logger.getLogger(ThemePluginImpl.class);
    private static BufferedImage background;
    private final List flist = new List();
    private final String BackgroundDir = "backgrounds" + File.separator;

    @PluginLoaded
    public void newPlugin(ThemePlugin plugin) {
        log.info(plugin.toString() + " has been loaded.");
    }

    public String toString() {
        return "[Theme plugin, version 0.5]";
    }

    public boolean loadimages() {
        File filedir = new File(BackgroundDir);
        File[] filelist = filedir.listFiles();
        if (filelist == null) {
            return false;
        }
        if (filelist.length == 0) {
            return false;
        }
        for (File f : filelist) {
            String filename = f.getName().toLowerCase(Locale.ENGLISH);
            if (filename != null && (filename.endsWith(".png") || filename.endsWith(".jpg")
                    || filename.endsWith(".bmp"))) {
                flist.add(filename);
            }
        }
        if (flist.getItemCount() == 0) {
            return false;
        }
        return true;
    }

    @Override
    public void applyInGame(Map<String, JComponent> ui) {
        BufferedImage backgroundImage;
        try {
            if (PreferencesDialog.getCachedValue(PreferencesDialog.KEY_BATTLEFIELD_IMAGE_DEFAULT, "true").equals("true")) {
                backgroundImage = loadbuffer_default();
            } else if (PreferencesDialog.getCachedValue(PreferencesDialog.KEY_BATTLEFIELD_IMAGE_RANDOM, "true").equals("true")) {
                backgroundImage = loadbuffer_random();
            } else if (PreferencesDialog.getCachedValue(PreferencesDialog.KEY_BATTLEFIELD_IMAGE, "") != null) {
                backgroundImage = loadbuffer_selected();
            } else {
                backgroundImage = loadbuffer_default();
            }

            if (backgroundImage == null) {
                backgroundImage = loadbuffer_default();
            }
            if (backgroundImage == null) {
                throw new FileNotFoundException("Couldn't find in resources.");
            }

            if (ui.containsKey("gamePanel") && ui.containsKey("jLayeredPane")) {
                ImagePanel bgPanel = new ImagePanel(backgroundImage, ImagePanelStyle.TILED);

                unsetOpaque(ui.get("jSplitPane1"));
                unsetOpaque(ui.get("pnlBattlefield"));
                unsetOpaque(ui.get("pnlHelperHandButtonsStackArea"));
                unsetOpaque(ui.get("hand"));
                unsetOpaque(ui.get("gameChatPanel"));
                unsetOpaque(ui.get("userChatPanel"));

                ui.get("gamePanel").remove(ui.get("jLayeredPane"));
                bgPanel.add(ui.get("jLayeredPane"));
                ui.get("gamePanel").add(bgPanel);
            } else {
                log.error("error: no components");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    // Sets background for in-battle
    // loadbuffer_default - Only apply theme background if no custom user background set
    private BufferedImage loadbuffer_default() throws IOException {
        BufferedImage res;
        InputStream is = this.getClass().getResourceAsStream(PreferencesDialog.getCurrentTheme().getBattleBackgroundPath());
        res = ImageIO.read(is);
        return res;
    }

    private BufferedImage loadbuffer_random() throws IOException {
        BufferedImage res;
        if (loadimages()) {
            int it = (int) Math.abs(RandomUtil.nextDouble() * (flist.getItemCount()));
            String filename = BackgroundDir + flist.getItem(it);
            res = ImageIO.read(new File(filename));
            return res;
        }
        return null;
    }

    private BufferedImage loadbuffer_selected() throws IOException {
        BufferedImage res;
        String path = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_BATTLEFIELD_IMAGE, "");
        if (path != null && !path.isEmpty()) {
            try {
                res = ImageIO.read(new File(path));
                return res;
            } catch (Exception e) {
                res = null;
            }
        }
        return null;
    }

    public JComponent updateTable(Map<String, JComponent> ui) {
        ImagePanel bgPanel = createImagePanelInstance();

        unsetOpaque(ui.get("jScrollPane1"));
        unsetOpaque(ui.get("jPanel1"));
        unsetOpaque(ui.get("tablesPanel"));
        JComponent viewport = ui.get("jScrollPane1ViewPort");
        if (viewport != null) {
            viewport.setBackground(new Color(255, 255, 255, 50));
        }
        return bgPanel;
    }

    // Sets background for logged in user for tables/deck editor/card viewer/etc
    private synchronized ImagePanel createImagePanelInstance() {
        if (background == null) {
            try {
                if (PreferencesDialog.getCachedValue(PreferencesDialog.KEY_BACKGROUND_IMAGE_DEFAULT, "true").equals("true")) {
                    InputStream is = this.getClass().getResourceAsStream(PreferencesDialog.getCurrentTheme().getBackgroundPath());
                    if (is == null) {
                        throw new FileNotFoundException("Couldn't find " + PreferencesDialog.getCurrentTheme().getBackgroundPath() + " in resources.");
                    }
                    background = ImageIO.read(is);
                } else {
                    String path = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_BACKGROUND_IMAGE, "");
                    if (path != null && !path.isEmpty()) {
                        try {
                            File f = new File(path);
                            if (f != null) {
                                background = ImageIO.read(f);
                            }
                        } catch (Exception e) {
                            background = null;
                        }
                    }
                }
                if (background == null) {
                    String filename = "/background/background.png";
                    InputStream is = this.getClass().getResourceAsStream(filename);
                    if (is == null) {
                        throw new FileNotFoundException("Couldn't find " + filename + " in resources.");
                    }
                    background = ImageIO.read(is);
                    if (background == null) {
                        throw new FileNotFoundException("Couldn't find " + filename + " in resources.");
                    }
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                return null;
            }
        }
        return new ImagePanel(background, ImagePanelStyle.SCALED);
    }

    private void unsetOpaque(JComponent c) {
        if (c != null) {
            c.setOpaque(false);
        }
    }
}
