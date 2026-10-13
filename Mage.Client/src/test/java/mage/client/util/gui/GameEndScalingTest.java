package mage.client.util.gui;

import com.google.gson.GsonBuilder;
import mage.client.MageFrame;
import mage.client.dialog.GameEndDialog;
import mage.client.dialog.PreferencesDialog;
import mage.client.util.GUISizeHelper;
import mage.view.GameEndView;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;

import static org.junit.Assert.*;

public class GameEndScalingTest extends GuiScalingTestSupport {
    @Test
    public void resultAndStatisticsFitLargeFontsAndStayReachable() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            try {
                JDesktopPane desktop = new JDesktopPane();
                desktop.setSize(1366, 700);
                field(MageFrame.class, "desktopPane").set(null, desktop);
                PreferencesDialog.saveValue(PreferencesDialog.KEY_GAME_LOG_AUTO_SAVE, "false");
                String json = "{\"startTime\":\"2026-10-13T20:00:00\",\"endTime\":\"2026-10-13T20:05:00\","
                        + "\"gameInfo\":\"You lost the game on turn 3.\",\"matchInfo\":\"You need two more wins to win the match.\","
                        + "\"additionalInfo\":\"\",\"players\":[],\"won\":false,\"matchView\":{"
                        + "\"startTime\":\"2026-10-13T20:00:00\",\"result\":\"Computer 1 - Human 0\",\"games\":[]}}";
                GameEndView view = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create().fromJson(json, GameEndView.class);
                for (int font : new int[]{14, 17, 23, 34}) {
                    PreferencesDialog.saveValue(PreferencesDialog.KEY_GUI_DIALOG_FONT_SIZE, String.valueOf(font));
                    GUISizeHelper.calculateGUISizes();
                    GameEndDialog dialog = new GameEndDialog(view);
                    desktop.add(dialog);
                    dialog.pack();
                    layoutTree(dialog);
                    for (String name : new String[]{"lblGameInfo", "lblMatchInfo"}) {
                        Component message = (Component) field(GameEndDialog.class, name).get(dialog);
                        assertTrue(name + " full message at " + font, message.getWidth() >= message.getPreferredSize().width);
                        assertTrue(name + " full height at " + font, message.getHeight() >= message.getPreferredSize().height);
                    }
                    JTabbedPane tabs = (JTabbedPane) field(GameEndDialog.class, "tabPane").get(dialog);
                    tabs.setSelectedIndex(1);
                    layoutTree(dialog);
                    for (String name : new String[]{"lblDurationGame", "txtDurationGame", "lblLife", "txtLife", "lblDurationMatch", "txtDurationMatch", "lblMatchScore", "txtMatchScore", "lblPlayerInfo", "txtPlayerInfo"}) {
                        Component control = (Component) field(GameEndDialog.class, name).get(dialog);
                        assertTrue(name + " height at " + font, control.getHeight() >= control.getPreferredSize().height);
                        if (control instanceof JLabel) assertTrue(name + " width at " + font, control.getWidth() >= control.getPreferredSize().width);
                    }
                    assertTrue(dialog.getWidth() <= desktop.getWidth());
                    assertTrue(dialog.getHeight() <= desktop.getHeight());
                    dialog.dispose();
                }
            } catch (Exception error) { throw new RuntimeException(error); }
        });
    }
}
