package mage.client.util.gui;

import mage.client.util.GUISizeHelper;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.text.JTextComponent;
import java.awt.*;

/** Fonts for standard Swing controls. Card, chat and player fonts remain independent. */
public final class GuiFontDefaults {
    private GuiFontDefaults() {
    }

    public static void install() {
        FontUIResource font = new FontUIResource(GUISizeHelper.dialogFont);
        String[] controls = {"Button", "ToggleButton", "CheckBox", "RadioButton", "Label",
                "ComboBox", "TextField", "PasswordField", "FormattedTextField", "TextArea",
                "TextPane", "EditorPane", "Spinner", "List", "Tree", "Table", "TableHeader",
                "Menu", "MenuItem", "CheckBoxMenuItem", "RadioButtonMenuItem", "MenuBar",
                "PopupMenu", "OptionPane", "TabbedPane", "Slider", "TitledBorder"};
        for (String control : controls) {
            UIManager.put(control + ".font", font);
            UIManager.getLookAndFeelDefaults().put(control + ".font", font);
        }
        UIManager.put("OptionPane.messageFont", font);
        UIManager.put("OptionPane.buttonFont", font);
        FontUIResource title = new FontUIResource(GUISizeHelper.dialogFont.deriveFont(Font.BOLD,
                Math.min(17, 0.8f * GUISizeHelper.dialogFont.getSize())));
        UIManager.put("InternalFrame:InternalFrameTitlePane.font", title);
        UIManager.getLookAndFeelDefaults().put("InternalFrame:InternalFrameTitlePane.font", title);
        FontUIResource tooltip = new FontUIResource(GUISizeHelper.cardTooltipFont);
        UIManager.put("ToolTip.font", tooltip);
        UIManager.getLookAndFeelDefaults().put("ToolTip.font", tooltip);
        Object map = UIManager.get("SplitPane.ancestorInputMap");
        if (map instanceof InputMap) {
            for (int key = java.awt.event.KeyEvent.VK_F2; key <= java.awt.event.KeyEvent.VK_F12; key++) {
                ((InputMap) map).remove(KeyStroke.getKeyStroke(key, 0));
            }
        }
    }

    /** Apply the dialog preference to generated forms while retaining bold/italic emphasis. */
    public static void apply(Component component) {
        if (component instanceof JLabel || component instanceof AbstractButton
                || component instanceof JTextComponent || component instanceof JComboBox
                || component instanceof JSpinner || component instanceof JList
                || component instanceof JTree || component instanceof JTabbedPane) {
            Font oldFont = component.getFont();
            int style = oldFont == null ? Font.PLAIN : oldFont.getStyle();
            component.setFont(new FontUIResource(GUISizeHelper.dialogFont.deriveFont(style)));
            // Generated size properties can cap an otherwise font-driven control.
            if (component instanceof JComboBox || component instanceof AbstractButton
                    && ((AbstractButton) component).getText() != null && !((AbstractButton) component).getText().isEmpty()) {
                component.setPreferredSize(null);
                component.setMinimumSize(null);
                component.setMaximumSize(null);
            }
        }
        if (component instanceof JSlider) {
            JSlider slider = (JSlider) component;
            if (slider.getLabelTable() != null) {
                java.util.Enumeration<?> labels = slider.getLabelTable().elements();
                while (labels.hasMoreElements()) {
                    Object label = labels.nextElement();
                    if (label instanceof Component) {
                        apply((Component) label);
                    }
                }
            }
        }
        if (component instanceof JComponent && ((JComponent) component).getBorder() instanceof TitledBorder) {
            ((TitledBorder) ((JComponent) component).getBorder()).setTitleFont(GUISizeHelper.dialogFont);
        }
        if (component instanceof JMenu) {
            for (Component child : ((JMenu) component).getMenuComponents()) {
                apply(child);
            }
        } else if (component instanceof Container) {
            for (Component child : ((Container) component).getComponents()) {
                apply(child);
            }
        }
        component.invalidate();
        if (component instanceof Container && ((Container) component).getLayout() instanceof LayoutManager2) {
            // Hidden generated forms can retain GroupLayout's old preferred-size cache.
            ((LayoutManager2) ((Container) component).getLayout()).invalidateLayout((Container) component);
        }
    }

    public static void refresh() {
        // Nimbus caches Synth styles, including fonts, across component UI updates.
        // Recreate its styles on a preference change; theme overrides in UIManager survive.
        if (UIManager.getLookAndFeel() instanceof NimbusLookAndFeel) {
            try {
                UIManager.setLookAndFeel(new NimbusLookAndFeel());
            } catch (UnsupportedLookAndFeelException error) {
                throw new IllegalStateException("Unable to refresh Nimbus fonts", error);
            }
        }
        install();
        for (Window window : Window.getWindows()) {
            refresh(window);
            window.invalidate();
            window.validate();
            window.repaint();
        }
    }

    public static void refresh(Component component) {
        SwingUtilities.updateComponentTreeUI(component);
        refreshInheritedFonts(component);
    }

    private static void refreshInheritedFonts(Component component) {
        if (component instanceof JInternalFrame) {
            refreshInheritedFonts(((JInternalFrame) component).getContentPane());
            return;
        }
        // Nimbus can keep a cached Synth font after updateComponentTreeUI. Replace only
        // look-and-feel fonts, leaving explicit chat, card and player fonts untouched.
        if (component.getFont() instanceof FontUIResource
                && !(component instanceof JToolTip) && !(component instanceof JInternalFrame)) {
            component.setFont(new FontUIResource(GUISizeHelper.dialogFont.deriveFont(component.getFont().getStyle())));
        }
        if (component instanceof Container) {
            for (Component child : ((Container) component).getComponents()) {
                refreshInheritedFonts(child);
            }
        }
    }
}
