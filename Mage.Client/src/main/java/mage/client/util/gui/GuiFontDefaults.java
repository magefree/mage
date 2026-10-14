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
        Object[] defaults = getFontDefaults();
        UIManager.getDefaults().putDefaults(defaults);
        UIManager.getLookAndFeelDefaults().putDefaults(defaults);
    }

    private static Object[] getFontDefaults() {
        java.util.List<Object> defaults = new java.util.ArrayList<>();
        FontUIResource font = new FontUIResource(GUISizeHelper.dialogFont);
        String[] controls = {"Button", "ToggleButton", "CheckBox", "RadioButton", "Label",
                "ComboBox", "TextField", "PasswordField", "FormattedTextField", "TextArea",
                "TextPane", "EditorPane", "Spinner", "List", "Tree", "Table", "TableHeader",
                "Menu", "MenuItem", "CheckBoxMenuItem", "RadioButtonMenuItem", "MenuBar",
                "PopupMenu", "OptionPane", "TabbedPane", "Slider", "TitledBorder"};
        for (String control : controls) {
            defaults.add(control + ".font");
            defaults.add(font);
        }
        // Nimbus uses separate styles while tabs are selected/hovered and for combo renderers.
        for (String region : new String[]{"TabbedPane:TabbedPaneTab", "ComboBox:\"ComboBox.renderer\"",
                "ComboBox:\"ComboBox.listRenderer\""}) {
            defaults.add(region + ".font");
            defaults.add(font);
        }
        defaults.add("OptionPane.messageFont");
        defaults.add(font);
        defaults.add("OptionPane.buttonFont");
        defaults.add(font);
        FontUIResource title = new FontUIResource(GUISizeHelper.dialogFont.deriveFont(Font.BOLD,
                Math.min(17, 0.8f * GUISizeHelper.dialogFont.getSize())));
        defaults.add("InternalFrame:InternalFrameTitlePane.font");
        defaults.add(title);
        FontUIResource tooltip = new FontUIResource(GUISizeHelper.cardTooltipFont);
        defaults.add("ToolTip.font");
        defaults.add(tooltip);
        return defaults.toArray();
    }

    /** Apply the dialog preference to generated forms on the EDT, retaining bold/italic emphasis. */
    public static void apply(Component component) {
        if (component instanceof JLabel || component instanceof AbstractButton
                || component instanceof JTextComponent || component instanceof JComboBox
                || component instanceof JSpinner || component instanceof JList
                || component instanceof JTree || component instanceof JTabbedPane) {
            Font oldFont = component.getFont();
            int style = oldFont == null ? Font.PLAIN : oldFont.getStyle();
            component.setFont(new FontUIResource(GUISizeHelper.dialogFont.deriveFont(style)));
            // Generated size properties can cap an otherwise font-driven control.
            boolean textButton = component instanceof AbstractButton
                    && ((AbstractButton) component).getText() != null
                    && !((AbstractButton) component).getText().isEmpty();
            if (component instanceof JComboBox || textButton) {
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
    }

    /** Refresh live windows on the EDT after changing font preferences. */
    public static void refresh() {
        // Nimbus caches Synth styles, including fonts, across component UI updates.
        // Recreate its styles on a preference change; theme overrides in UIManager survive.
        if (UIManager.getLookAndFeel() instanceof NimbusLookAndFeel) {
            try {
                NimbusLookAndFeel lookAndFeel = new NimbusLookAndFeel();
                // Populate the replacement before listeners can cache its default 12pt styles.
                lookAndFeel.getDefaults().putDefaults(getFontDefaults());
                UIManager.setLookAndFeel(lookAndFeel);
            } catch (UnsupportedLookAndFeelException error) {
                throw new IllegalStateException("Unable to refresh Nimbus fonts", error);
            }
        }
        install();
        GuiDisplayUtil.configureSplitPaneGameShortcuts();
        for (Window window : Window.getWindows()) {
            // Replaced dialogs no longer need component UI updates or layout validation.
            if (!window.isDisplayable()) {
                continue;
            }
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
        if (component.getFont() instanceof FontUIResource && !(component instanceof JToolTip)) {
            component.setFont(new FontUIResource(GUISizeHelper.dialogFont.deriveFont(component.getFont().getStyle())));
        }
        if (component instanceof Container) {
            for (Component child : ((Container) component).getComponents()) {
                refreshInheritedFonts(child);
            }
        }
    }
}
