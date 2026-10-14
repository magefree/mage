package mage.client.util.gui;

import mage.client.dialog.PreferencesDialog;

import javax.swing.*;
import java.awt.*;

/** A vertical card-area divider that retains a proportion rather than a pixel position. */
public class ProportionalSplitPane extends JSplitPane {
    private static final double DEFAULT_PROPORTION = 0.5;

    private double proportion = DEFAULT_PROPORTION;
    private String preferenceKey;
    private int previousAvailableHeight = -1;
    private boolean restoring;

    public ProportionalSplitPane() {
        super(VERTICAL_SPLIT);
        setContinuousLayout(true);
        setResizeWeight(DEFAULT_PROPORTION);
        setOneTouchExpandable(true);
        addPropertyChangeListener(DIVIDER_LOCATION_PROPERTY, event -> {
            int space = getAvailableHeight();
            if (!restoring && space > 0) {
                proportion = Math.max(0, Math.min(1, (getDividerLocation() - getInsets().top) / (double) space));
                if (preferenceKey != null) {
                    PreferencesDialog.saveValue(preferenceKey, Double.toString(proportion));
                }
            }
        });
    }

    public void setPreferenceKey(String key) {
        preferenceKey = key;
        try {
            double saved = Double.parseDouble(PreferencesDialog.getCachedValue(key, Double.toString(DEFAULT_PROPORTION)));
            proportion = Double.isFinite(saved) && saved >= 0 && saved <= 1 ? saved : DEFAULT_PROPORTION;
        } catch (NumberFormatException ignored) {
            proportion = DEFAULT_PROPORTION;
        }
        previousAvailableHeight = -1;
    }

    private int getAvailableHeight() {
        Insets insets = getInsets();
        return getHeight() - insets.top - insets.bottom - getDividerSize();
    }

    @Override
    public void doLayout() {
        int availableHeight = getAvailableHeight();
        if (previousAvailableHeight != availableHeight && availableHeight > 0) {
            restoring = true;
            try {
                setDividerLocation(getInsets().top + (int) Math.round(proportion * availableHeight));
                previousAvailableHeight = availableHeight;
            } finally {
                restoring = false;
            }
        }
        super.doLayout();
    }
}
