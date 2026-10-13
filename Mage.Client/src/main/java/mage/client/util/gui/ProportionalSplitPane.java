package mage.client.util.gui;

import mage.client.dialog.PreferencesDialog;

import javax.swing.*;
import java.awt.*;

/** A vertical card-area divider that retains a proportion rather than a pixel position. */
public class ProportionalSplitPane extends JSplitPane {
    private double proportion = 0.5;
    private String preferenceKey;
    private int previousHeight = -1;
    private boolean restoring;

    public ProportionalSplitPane() {
        super(VERTICAL_SPLIT);
        setContinuousLayout(true);
        setResizeWeight(0.5);
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
            double saved = Double.parseDouble(PreferencesDialog.getCachedValue(key, "0.5"));
            proportion = Double.isFinite(saved) && saved >= 0 && saved <= 1 ? saved : 0.5;
        } catch (NumberFormatException ignored) {
            proportion = 0.5;
        }
        previousHeight = -1;
    }

    private int getAvailableHeight() {
        Insets insets = getInsets();
        return getHeight() - insets.top - insets.bottom - getDividerSize();
    }

    @Override
    public void doLayout() {
        if (previousHeight != getHeight() && getAvailableHeight() > 0) {
            restoring = true;
            try {
                setDividerLocation(getInsets().top + (int) Math.round(proportion * getAvailableHeight()));
                previousHeight = getHeight();
            } finally {
                restoring = false;
            }
        }
        super.doLayout();
    }
}
