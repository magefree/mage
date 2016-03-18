/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */

package mage.client.deck.generator;

import mage.client.deck.generator.DeckGeneratorPool;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * @author Simown
 */
public class RatioAdjustingSliderPanel extends JPanel {

    private static final int MAXIMUM = 100;
    private int adjustableCount;
    private JStorageSlider creatureSlider, nonCreatureSlider, landSlider;
    private List<JLabel> textLabels = new ArrayList<>();
    private AdjustingSliderGroup sg;

    private class JStorageSlider extends JSlider {

        // Slider stores its initial value to revert to when reset
        private int defaultValue;

        public JStorageSlider(int min, int max, int value) {
            super(min, max, value);
            defaultValue = value;
            setMinorTickSpacing(5);
            setMajorTickSpacing(10);
            setPaintTicks(true);
            setPaintLabels(true);
            setLabelTable(createStandardLabels(10));
        }

        public void resetDefault() {
            this.setValue(defaultValue);
        }

    }

    private class AdjustingSliderGroup {
        private final ArrayList<JStorageSlider> storageSliders;
        private int sliderIndex = 0;

        AdjustingSliderGroup(JStorageSlider... sliders) {
            storageSliders = new ArrayList<>();
            for (JStorageSlider slider : sliders) {
                storageSliders.add(slider);
                slider.addChangeListener(new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {
                        fireSliderChangedEvent((JStorageSlider) e.getSource());
                    }
                });
            }
            adjustableCount = storageSliders.size() - 1;
        }

        public void fireSliderChangedEvent(JStorageSlider source) {
            // We don't want to do anything if the value isn't changing
            if (!source.getValueIsAdjusting())
                return;
            // Update the slider depending on how much it's changed relative to its previous position
            updateSliderPosition(source);
        }

        private void updateSliderPosition(JStorageSlider source) {
            int maximum = MAXIMUM;
            int excess = MAXIMUM;
            int sign = 0;
            for (JStorageSlider slider : storageSliders) {
                excess -= slider.getValue();
            }
            sign = Integer.signum(excess);
            excess = Math.abs(excess);
            int addition = excess / (adjustableCount - 1); // divide the deficit between all adjustable sliders except this one
            if (addition == 0 && excess != 0) {
                addition = 1;
            }
            for (int i = storageSliders.size() - 1; i >= 0; i--) {
                JStorageSlider slider = storageSliders.get(i);
                int value = slider.getValue();
                if (slider != source && maximum < MAXIMUM) {
                    slider.setMaximum(maximum);
                    if (excess >= addition) {
                        value += addition * sign;
                        excess -= addition;
                    } else {
                        value += excess * sign;
                        excess = 0;
                    }
                    if (value < 0) {
                        excess += value;
                        value = 0;
                    }
                    slider.setValue(value);
                }
                maximum -= value;
            }
        }

        List<JStorageSlider> getSliders() {
            return storageSliders;
        }

    }

    public RatioAdjustingSliderPanel() {
        initPanel();
    }

    private void initPanel() {

        // Create three sliders with default values
        creatureSlider = new JStorageSlider(0, MAXIMUM, DeckGeneratorPool.DEFAULT_CREATURE_PERCENTAGE);
        nonCreatureSlider = new JStorageSlider(0, MAXIMUM, DeckGeneratorPool.DEFAULT_NON_CREATURE_PERCENTAGE);
        landSlider = new JStorageSlider(0, MAXIMUM, DeckGeneratorPool.DEFAULT_LAND_PERCENTAGE);

        sg = new AdjustingSliderGroup(creatureSlider, nonCreatureSlider, landSlider);

        this.setLayout(new GridLayout(3, 1));

        this.add(createSliderPanel("Creatures          ", creatureSlider));
        this.add(createSliderPanel("Non-creatures   ", nonCreatureSlider));
        this.add(createSliderPanel("Lands                ", landSlider));

        setEnabled(true);
    }

    private JPanel createSliderPanel(String label, JStorageSlider slider) {

        JPanel sliderPanel = new JPanel(new BorderLayout());

        // Title
        JLabel titleLabel = new JLabel(label);
        textLabels.add(titleLabel);
        sliderPanel.add(titleLabel, BorderLayout.WEST);
        // Slider
        slider.setToolTipText("Percentage of " + label.trim().toLowerCase() + " in the generated deck.");
        sliderPanel.add(slider, BorderLayout.CENTER);
        // Percentage
        JLabel percentageLabel = createChangingPercentageLabel(slider);
        textLabels.add(percentageLabel);
        sliderPanel.add(percentageLabel, BorderLayout.EAST);

        return sliderPanel;
    }

    private static JLabel createChangingPercentageLabel(final JSlider slider) {

        final JLabel label = new JLabel("      " + String.valueOf(slider.getValue()) + "%");

        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                String value = String.valueOf(slider.getValue());
                StringBuilder labelBuilder = new StringBuilder();
                // Pad with spaces so all percentage labels are of equal size
                for (int i = 0; i < (5 - value.length()); i++) {
                    labelBuilder.append("  ");
                }
                labelBuilder.append(value);
                labelBuilder.append("%");
                label.setText(labelBuilder.toString());
            }
        });
        return label;
    }

    @Override
    public void setEnabled(boolean enabled) {
        for (JStorageSlider slider : sg.getSliders()) {
            slider.setEnabled(enabled);
        }
        for (JLabel label : textLabels) {
            label.setEnabled(enabled);
        }
    }

    public void resetValues() {
        for (JStorageSlider slider : sg.getSliders()) {
            slider.resetDefault();
        }
        sg.updateSliderPosition(landSlider);
    }

    public int getCreaturePercentage() {
        return creatureSlider.getValue();
    }

    public int getNonCreaturePercentage() {
        return nonCreatureSlider.getValue();
    }

    public int getLandPercentage() {
        return landSlider.getValue();
    }

    public void setCreaturePercentage(int percentage) {
        creatureSlider.setValue(percentage);
        sg.updateSliderPosition(creatureSlider);
    }

    public void setNonCreaturePercentage(int percentage) {
        nonCreatureSlider.setValue(percentage);
        sg.updateSliderPosition(nonCreatureSlider);
    }

    public void setLandPercentage(int percentage) {
        landSlider.setValue(percentage);
        sg.updateSliderPosition(landSlider);
    }

}
