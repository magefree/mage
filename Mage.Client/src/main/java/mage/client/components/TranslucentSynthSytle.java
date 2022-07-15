package mage.client.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.Icon;
import javax.swing.plaf.synth.ColorType;
import javax.swing.plaf.synth.Region;
import javax.swing.plaf.synth.SynthContext;
import javax.swing.plaf.synth.SynthGraphicsUtils;
import javax.swing.plaf.synth.SynthPainter;
import javax.swing.plaf.synth.SynthStyle;

class TranslucentSynthSytle extends SynthStyle {
    private final SynthStyle style;

    public TranslucentSynthSytle(SynthStyle s) {
        style = s;
    }

    @Override
    public Object get(SynthContext context, Object key) {
        return style.get(context, key);
    }

    @Override
    public boolean getBoolean(SynthContext context, Object key,
            boolean defaultValue) {
        return style.getBoolean(context, key, defaultValue);
    }

    @Override
    public Color getColor(SynthContext context, ColorType type) {
        return style.getColor(context, type);
    }

    @Override
    public Font getFont(SynthContext context) {
        return style.getFont(context);
    }

    @Override
    public SynthGraphicsUtils getGraphicsUtils(SynthContext context) {
        return style.getGraphicsUtils(context);
    }

    @Override
    public Icon getIcon(SynthContext context, Object key) {
        return style.getIcon(context, key);
    }

    @Override
    public Insets getInsets(SynthContext context, Insets insets) {
        return style.getInsets(context, insets);
    }

    @Override
    public int getInt(SynthContext context, Object key, int defaultValue) {
        return style.getInt(context, key, defaultValue);
    }

    @Override
    public SynthPainter getPainter(final SynthContext context) {
        return new SynthPainter() {
            public void paintInternalFrameBackground(SynthContext context,
                    Graphics g, int x, int y, int w, int h) {
                g.setColor(new Color(50, 50, 50, 100));
                g.fillRoundRect(x, y, w, h, 5, 5);
            }
        };
    }

    @Override
    public String getString(SynthContext context, Object key,
            String defaultValue) {
        return style.getString(context, key, defaultValue);
    }

    @Override
    public void installDefaults(SynthContext context) {
        style.installDefaults(context);
    }

    @Override
    public void uninstallDefaults(SynthContext context) {
        style.uninstallDefaults(context);
    }

    @Override
    public boolean isOpaque(SynthContext context) {
        if (Region.INTERNAL_FRAME.equals(context.getRegion())) {
            return false;
        } else {
            return style.isOpaque(context);
        }
    }

    public Color getColorForState(SynthContext context, ColorType type) {
        return null;
    }

    public Font getFontForState(SynthContext context) {
        return null;
    }
}