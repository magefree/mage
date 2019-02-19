
package mage.client.deck.generator;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.swing.*;

/**
 * @author Simown
 */
public class RatioAdjustingSliderPanel extends JPanel {

    private JStorageSlider creatureSlider, nonCreatureSlider, landSlider;
    private final List<JLabel> textLabels = new ArrayList<>();
    private AdjustingSliderGroup sg;

    private static class JStorageSlider extends JSlider {

        // Slider stores its initial value to revert to when reset
        private final int defaultValue;
        private int previousValue;

        public JStorageSlider(int min, int max, int value) {
            super(min, max, value);
            previousValue = value;
            defaultValue = value;
            setMinorTickSpacing(5);
            setMajorTickSpacing(10);
            setPaintTicks(true);
            setPaintLabels(true);
            setLabelTable(createStandardLabels(10));
        }

        public int getPreviousValue() {
            return previousValue;
        }

        public void setPreviousValue(int value) {
            previousValue = value;
        }

        public void resetDefault() {
            this.setValue(defaultValue);
            previousValue = defaultValue;
        }

    }

    private static class AdjustingSliderGroup {

        private final ArrayList<JStorageSlider> storageSliders;
        private int sliderIndex = 0;

        AdjustingSliderGroup(JStorageSlider... sliders) {
            storageSliders = new ArrayList<>();
            for (JStorageSlider slider : sliders) {
                storageSliders.add(slider);
                slider.addChangeListener(e -> fireSliderChangedEvent((JStorageSlider) e.getSource()));
            }
        }

        public void fireSliderChangedEvent(JStorageSlider source) {
            // We don't want to do anything if the value isn't changing
            if (!source.getValueIsAdjusting()) {
                return;
            }
            // Update the slider depending on how much it's changed relative to its previous position
            int change = (source.getValue() - source.getPreviousValue());
            updateSliderPosition(change, source);
        }

        private void updateSliderPosition(int change, JStorageSlider source) {
            int remaining = change;
            while (remaining != 0) {
                // Get the currently indexed slider
                JStorageSlider slider = storageSliders.get(sliderIndex);
                // If it's not the slider that fired the event
                if (slider != source) {
                    // Check we don't go over the upper and lower bounds
                    if (remaining < 0 || (remaining > 0 && slider.getValue() > 0)) {
                        // Adjust the currently selected slider by +/- 1
                        int adjustment = Integer.signum(remaining);
                        slider.setValue(slider.getValue() - adjustment);
                        remaining -= adjustment;
                    }
                }
                // Select the next slider in the list of sliders
                sliderIndex = (sliderIndex + 1) % storageSliders.size();
            }
            for (JStorageSlider slider : storageSliders) {
                slider.setPreviousValue(slider.getValue());
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
        creatureSlider = new JStorageSlider(0, 100, DeckGeneratorPool.DEFAULT_CREATURE_PERCENTAGE);
        nonCreatureSlider = new JStorageSlider(0, 100, DeckGeneratorPool.DEFAULT_NON_CREATURE_PERCENTAGE);
        landSlider = new JStorageSlider(0, 100, DeckGeneratorPool.DEFAULT_LAND_PERCENTAGE);

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
        slider.setToolTipText("Percentage of " + label.trim().toLowerCase(Locale.ENGLISH) + " in the generated deck.");
        sliderPanel.add(slider, BorderLayout.CENTER);
        // Percentage
        JLabel percentageLabel = createChangingPercentageLabel(slider);
        textLabels.add(percentageLabel);
        sliderPanel.add(percentageLabel, BorderLayout.EAST);

        return sliderPanel;
    }

    private static JLabel createChangingPercentageLabel(final JSlider slider) {

        final JLabel label = new JLabel("      " + slider.getValue() + '%');

        slider.addChangeListener(e -> {
            String value = String.valueOf(slider.getValue());
            StringBuilder labelBuilder = new StringBuilder();
            // Pad with spaces so all percentage labels are of equal size
            for (int i = 0; i < (5 - value.length()); i++) {
                labelBuilder.append("  ");
            }
            labelBuilder.append(value);
            labelBuilder.append('%');
            label.setText(labelBuilder.toString());
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
        creatureSlider.previousValue = percentage;
    }

    public void setNonCreaturePercentage(int percentage) {
        nonCreatureSlider.setValue(percentage);
        nonCreatureSlider.previousValue = percentage;
    }

    public void setLandPercentage(int percentage) {
        landSlider.setValue(percentage);
        landSlider.previousValue = percentage;
    }

}
