package mage.client.util.gui;

import org.junit.*;

import javax.swing.*;
import java.awt.*;
import java.util.*;

import static org.junit.Assert.*;

public class GuiDisplayUtilTest extends GuiScalingTestSupport {
    @Test
    public void popupPlacementConvertsThroughItsActualNestedParent() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            try {
                JPanel root = new JPanel(null);
                root.setBounds(-1200, 106, 800, 600);
                JPanel nested = new JPanel(null);
                nested.setBounds(30, 40, 700, 500);
                root.add(nested);
                JPanel popup = new JPanel();
                popup.setSize(300, 200);
                nested.add(popup);
                mage.cards.action.TransferData data = new mage.cards.action.TransferData();
                data.setPopupAutoLocationMode(mage.cards.action.TransferData.PopupAutoLocationMode.PUT_INSIDE_PARENT);
                data.setLocationOnScreen(new Point(100, 2000));
                data.setPopupOffsetX(40);
                data.setPopupOffsetY(40);
                java.lang.reflect.Method place = mage.client.plugins.adapters.MageActionCallback.class.getDeclaredMethod(
                        "preparePopupContainerLocation", Component.class, Component.class,
                        mage.cards.action.TransferData.class, Point.class, Component.class);
                place.setAccessible(true);
                Point result = (Point) place.invoke(new mage.client.plugins.adapters.MageActionCallback(),
                        popup, new JPanel(), data, new Point(-1200, 106), root);
                assertEquals(new Point(400, 300), result);
                data.setLocationOnScreen(new Point(-2000, -2000));
                assertEquals(new Point(0, 0), place.invoke(new mage.client.plugins.adapters.MageActionCallback(),
                        popup, new JPanel(), data, new Point(-1200, 106), root));
            } catch (Exception error) { throw new RuntimeException(error); }
        });
    }

    @Test
    public void popupCoordinatesRespectNegativeOriginsAndOversizedContent() {
        Rectangle leftDisplay = new Rectangle(-1200, 106, 1200, 1840);
        assertEquals(new Point(-1200, 106), GuiDisplayUtil.clampLocation(new Point(-1500, -200), new Dimension(400, 500), leftDisplay));
        assertEquals(new Point(-400, 1446), GuiDisplayUtil.clampLocation(new Point(30, 2000), new Dimension(400, 500), leftDisplay));
        assertEquals(new Point(-1200, 106), GuiDisplayUtil.clampLocation(new Point(0, 0), new Dimension(2000, 2200), leftDisplay));
        JPanel parent = new JPanel();
        parent.setSize(800, 600);
        JPanel popup = new JPanel();
        popup.setSize(300, 200);
        assertEquals(new Point(-690, -390), GuiDisplayUtil.keepComponentInsideParent(new Point(-2000, -900), new Point(-700, -400), popup, parent));
    }
}
