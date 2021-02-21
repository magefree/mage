package mage.client.components;

import java.awt.BorderLayout;
import javax.swing.*;

import mage.client.dialog.CardInfoWindowDialog;

/**
 *
 * @author LevelX2
 */
public class MageDesktopManager extends DefaultDesktopManager {

    static final int DESKTOP_ICON_WIDTH = 250;

    @Override
    public void iconifyFrame(JInternalFrame f) {
        super.iconifyFrame(f);
        if (f instanceof CardInfoWindowDialog) {
            JInternalFrame.JDesktopIcon icon = f.getDesktopIcon();
            icon.setBounds(f.getX() + (f.getWidth() - DESKTOP_ICON_WIDTH), f.getY(), DESKTOP_ICON_WIDTH, icon.getHeight());
        }
    }

    @Override
    public void deiconifyFrame(JInternalFrame f) {
        super.deiconifyFrame(f);
        if (f instanceof CardInfoWindowDialog) {
            JInternalFrame.JDesktopIcon icon = f.getDesktopIcon();
            f.setBounds(icon.getX() + (DESKTOP_ICON_WIDTH - f.getWidth()), icon.getY(), f.getWidth(), f.getHeight());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            JDesktopPane desktopPane = new JDesktopPane();
            DesktopManager dm = new MageDesktopManager();
            desktopPane.setDesktopManager(dm);
            JInternalFrame internalFrame = new JInternalFrame("Test Internal Frame", true, false, true, true);
            internalFrame.setSize(200, 150);
            internalFrame.setVisible(true);
            desktopPane.add(internalFrame);

            frame.add(desktopPane, BorderLayout.CENTER);
            frame.setSize(800, 600);
            frame.setVisible(true);
        });
    }
}
