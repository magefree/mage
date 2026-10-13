package mage.client.util.gui;

import org.junit.*;

import javax.swing.*;
import java.awt.*;
import java.util.*;

import static org.junit.Assert.*;

public class GuiDisplayUtilTest extends GuiScalingTestSupport {
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
