package mage.client.util.gui;

import mage.client.dialog.PreferencesDialog;
import org.junit.*;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

import static org.junit.Assert.*;

public class DraftScalingTest extends GuiScalingTestSupport {
    @Test
    public void draftDividerRetainsProportionAcrossResizeAndRecreation() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            ProportionalSplitPane split = createSplit();
            split.setPreferenceKey("testDraftRatio");
            split.setSize(800, 600);
            split.doLayout();
            split.setDividerLocation(200);
            double ratio = (200 - split.getInsets().top) / (double) (600 - split.getDividerSize() - split.getInsets().top - split.getInsets().bottom);
            split.setSize(800, 1000);
            split.doLayout();
            assertEquals(split.getInsets().top + Math.round(ratio * (1000 - split.getDividerSize() - split.getInsets().top - split.getInsets().bottom)), split.getDividerLocation());
            ProportionalSplitPane reopened = createSplit();
            reopened.setPreferenceKey("testDraftRatio");
            reopened.setSize(800, 1000);
            reopened.doLayout();
            assertEquals(split.getDividerLocation(), reopened.getDividerLocation());
            PreferencesDialog.saveValue("testDraftRatio", "NaN");
            reopened.setPreferenceKey("testDraftRatio");
            reopened.doLayout();
            assertEquals(0.5, reopened.getDividerLocation() / 1000.0, 0.02);
        });
    }

    private static ProportionalSplitPane createSplit() {
        ProportionalSplitPane split = new ProportionalSplitPane();
        JPanel top = new JPanel();
        JPanel bottom = new JPanel();
        top.setMinimumSize(new Dimension(0, 0));
        bottom.setMinimumSize(new Dimension(0, 0));
        split.setTopComponent(top);
        split.setBottomComponent(bottom);
        return split;
    }

    @Test
    public void boosterCardsFitBothNarrowAndWidePanelsAfterResize() {
        for (Dimension panel : new Dimension[]{new Dimension(700, 200), new Dimension(700, 440),
                new Dimension(1500, 500), new Dimension(3200, 900)}) {
            List<Rectangle> cards = mage.client.draft.DraftGrid.calculateCardBounds(15, panel.width, panel.height);
            assertEquals(15, cards.size());
            for (Rectangle card : cards) {
                assertTrue(card.width > 0 && card.height > 0);
                assertTrue(new Rectangle(panel).contains(card));
            }
        }
    }
}
