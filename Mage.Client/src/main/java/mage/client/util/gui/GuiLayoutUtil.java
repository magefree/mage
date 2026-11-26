package mage.client.util.gui;

import javax.swing.*;
import java.awt.*;

/** Refresh layouts without replacing the fonts selected by a form or look and feel. */
public final class GuiLayoutUtil {
    private GuiLayoutUtil() {
    }

    private static final String SIZE_HINTS = GuiLayoutUtil.class.getName() + ".sizeHints";

    /** Retain original size hints, expanding them only when the control's content needs more room. */
    public static void prepare(Component component) {
        boolean textButton = component instanceof AbstractButton
                && ((AbstractButton) component).getText() != null
                && !((AbstractButton) component).getText().isEmpty();
        if (component instanceof JComboBox || textButton) {
            JComponent control = (JComponent) component;
            SizeHints hints = (SizeHints) control.getClientProperty(SIZE_HINTS);
            if (hints == null) {
                hints = new SizeHints(control);
                control.putClientProperty(SIZE_HINTS, hints);
            }
            control.setPreferredSize(null);
            control.setMinimumSize(null);
            control.setMaximumSize(null);
            // Preserve compact baseline hints; expand only for an explicitly enlarged font.
            Font standard = UIManager.getFont(component instanceof JComboBox ? "ComboBox.font" : "Button.font");
            Dimension natural = standard != null && control.getFont() != null
                    && control.getFont().getSize2D() > standard.getSize2D() ? control.getPreferredSize() : new Dimension();
            control.setPreferredSize(expand(hints.preferred, natural));
            control.setMinimumSize(expand(hints.minimum, natural));
            control.setMaximumSize(expand(hints.maximum, natural));
        }
        if (component instanceof JMenu) {
            for (Component child : ((JMenu) component).getMenuComponents()) {
                prepare(child);
            }
        } else if (component instanceof Container) {
            for (Component child : ((Container) component).getComponents()) {
                prepare(child);
            }
        }
        component.invalidate();
    }

    private static Dimension expand(Dimension original, Dimension natural) {
        return original == null ? null : new Dimension(Math.max(original.width, natural.width),
                Math.max(original.height, natural.height));
    }

    private static final class SizeHints {
        private final Dimension preferred;
        private final Dimension minimum;
        private final Dimension maximum;

        private SizeHints(JComponent control) {
            preferred = control.isPreferredSizeSet() ? control.getPreferredSize() : null;
            minimum = control.isMinimumSizeSet() ? control.getMinimumSize() : null;
            maximum = control.isMaximumSizeSet() ? control.getMaximumSize() : null;
        }
    }

    public static void refresh(Component component) {
        SwingUtilities.updateComponentTreeUI(component);
        GuiDisplayUtil.invalidateLayoutTree(component);
    }
}
