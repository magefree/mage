package mage.client.util.gui;

import java.awt.*;
import java.util.ArrayList;
import java.util.Locale;
import javax.swing.*;
import mage.client.dialog.PreferencesDialog;
import static mage.client.dialog.PreferencesDialog.KEY_MAGE_PANEL_LAST_SIZE;
import mage.client.MageFrame;
import mage.client.util.GUISizeHelper;
import mage.client.table.*;
import mage.constants.*;
import mage.view.CardView;
import mage.view.CounterView;
import mage.view.PermanentView;
import org.jdesktop.swingx.JXPanel;
import org.mage.card.arcane.ManaSymbols;
import org.mage.card.arcane.UI;

public final class GuiDisplayUtil {

    private static final Font cardNameFont = new Font("Calibri", Font.BOLD, 15);
    private static final Insets DEFAULT_INSETS = new Insets(0, 0, 70, 25);
    private static final Insets COMPONENT_INSETS = new Insets(0, 0, 40, 40);

    public static class TextLines {

        public int basicTextLength;
        public ArrayList<String> lines;
    }

    public static void restoreDividerLocations(Rectangle bounds, String lastDividerLocation, JComponent component) {
      String currentBounds = Double.toString(bounds.getWidth()) + 'x' + Double.toString(bounds.getHeight());
      String savedBounds = PreferencesDialog.getCachedValue(KEY_MAGE_PANEL_LAST_SIZE, null);
      // use divider positions only if screen size is the same as it was the time the settings were saved
      if (savedBounds != null && savedBounds.equals(currentBounds)) {
        if (lastDividerLocation != null && component != null) {
          if (component instanceof JSplitPane) {
            JSplitPane jSplitPane = (JSplitPane) component;
            jSplitPane.setDividerLocation(Integer.parseInt(lastDividerLocation));
          }

          if (component instanceof PlayersChatPanel) {
            PlayersChatPanel playerChatPanel = (PlayersChatPanel) component;
            playerChatPanel.setSplitDividerLocation(Integer.parseInt(lastDividerLocation));
          }
        }
      }
    }

    public static void saveCurrentBoundsToPrefs() {
      Rectangle rec = MageFrame.getDesktop().getBounds();
      String currentBounds = Double.toString(rec.getWidth()) + 'x' + Double.toString(rec.getHeight());
      PreferencesDialog.saveValue(KEY_MAGE_PANEL_LAST_SIZE, currentBounds);
    }

    public static void saveDividerLocationToPrefs(String dividerPrefKey, int position) {
      PreferencesDialog.saveValue(dividerPrefKey, Integer.toString(position));
    }

    public static JXPanel getDescription(CardView card, int width, int height) {
        JXPanel descriptionPanel = new JXPanel();

        //descriptionPanel.setAlpha(.8f);
        descriptionPanel.setBounds(0, 0, width, height);
        descriptionPanel.setVisible(false);
        descriptionPanel.setLayout(null);

        //descriptionPanel.setBorder(BorderFactory.createLineBorder(Color.green));
        JButton j = new JButton("");
        j.setBounds(0, 0, width, height);
        j.setBackground(Color.black);
        j.setLayout(null);

        JLabel cardText = new JLabel();
        cardText.setBounds(5, 5, width - 10, height - 10);
        cardText.setForeground(Color.white);
        cardText.setFont(cardNameFont);
        cardText.setVerticalAlignment(SwingConstants.TOP);
        j.add(cardText);

        TextLines textLines = GuiDisplayUtil.getTextLinesfromCardView(card);
        cardText.setText(getRulefromCardView(card, textLines).toString());

        descriptionPanel.add(j);

        return descriptionPanel;
    }

    public static String cleanString(String in) {
        StringBuilder out = new StringBuilder();
        char c;
        for (int i = 0; i < in.length(); i++) {
            c = in.charAt(i);
            if (c == ' ' || c == '-') {
                out.append('_');
            } else if (Character.isLetterOrDigit(c)) {
                out.append(c);
            }
        }
        return out.toString().toLowerCase(Locale.ENGLISH);
    }

    public static void keepComponentInsideScreen(int centerX, int centerY, Component component) {
        Dimension screenDim = component.getToolkit().getScreenSize();
        GraphicsConfiguration g = component.getGraphicsConfiguration();

        if (g != null) {
            Insets insets = component.getToolkit().getScreenInsets(g); // no usable space like toolbar
            boolean setLocation = false;
            if (centerX + component.getWidth() > screenDim.width - insets.right) {
                centerX = (screenDim.width - insets.right) - component.getWidth();
                setLocation = true;
            } else if (centerX < insets.left) {
                centerX = insets.left;
                setLocation = true;
            }

            if (centerY + component.getHeight() > screenDim.height - insets.bottom) {
                centerY = (screenDim.height - insets.bottom) - component.getHeight();
                setLocation = true;
            } else if (centerY < insets.top) {
                centerY = insets.top;
                setLocation = true;
            }
            if (setLocation) {
                component.setLocation(centerX, centerY);
            }
        } else {
            System.out.println("GuiDisplayUtil::keepComponentInsideScreen -> no GraphicsConfiguration");
        }
    }

    static final int OVERLAP_LIMIT = 10;

    public static void keepComponentInsideFrame(int centerX, int centerY, Component component) {
        Rectangle frameRec = MageFrame.getInstance().getBounds();
        boolean setLocation = false;
        if (component.getX() > (frameRec.width - OVERLAP_LIMIT)) {
            setLocation = true;
        }
        if (component.getY() > (frameRec.height - OVERLAP_LIMIT)) {
            setLocation = true;
        }
        if (setLocation) {
            component.setLocation(centerX, centerY);
        }
    }

    public static Point keepComponentInsideParent(Point l, Point parentPoint, Component c, Component parent) {
        int dx = parentPoint.x + parent.getWidth() - DEFAULT_INSETS.right - COMPONENT_INSETS.right;
        if (l.x + c.getWidth() > dx) {
            l.x = dx - c.getWidth();
        }

        int dy = parentPoint.y + parent.getHeight() - DEFAULT_INSETS.bottom - COMPONENT_INSETS.bottom;
        if (l.y + c.getHeight() > dy) {
            l.y = Math.max(10, dy - c.getHeight());
        }

        return l;
    }

    public static TextLines getTextLinesfromCardView(CardView card) {
        TextLines textLines = new TextLines();
        textLines.lines = new ArrayList<>(card.getRules());
        for (String rule : card.getRules()) {
            textLines.basicTextLength += rule.length();
        }
        if (card.getMageObjectType().canHaveCounters()) {
            ArrayList<CounterView> counters = new ArrayList<>();
            if (card instanceof PermanentView) {
                if (card.getCounters() != null) {
                    counters = new ArrayList<>(card.getCounters());
                }
            } else if (card.getCounters() != null) {
                counters = new ArrayList<>(card.getCounters());
            }
            if (!counters.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                int index = 0;
                for (CounterView counter : counters) {
                    if (counter.getCount() > 0) {
                        if (index == 0) {
                            sb.append("<b>Counters:</b> ");
                        } else {
                            sb.append(", ");
                        }
                        sb.append(counter.getCount()).append(" x <i>").append(counter.getName()).append("</i>");
                        index++;
                    }
                }
                textLines.lines.add(sb.toString());
                textLines.basicTextLength += 50;
            }
        }
        if (card.getMageObjectType().isPermanent() && card instanceof PermanentView) {
            int damage = ((PermanentView) card).getDamage();
            if (damage > 0) {
                textLines.lines.add("<span color='red'><b>Damage dealt:</b> " + damage + "</span>");
                textLines.basicTextLength += 50;
            }
        }
        return textLines;
    }

    public static StringBuilder getRulefromCardView(CardView card, TextLines textLines) {
        String manaCost = "";
        for (String m : card.getManaCost()) {
            manaCost += m;
        }
        String castingCost = UI.getDisplayManaCost(manaCost);
        castingCost = ManaSymbols.replaceSymbolsWithHTML(castingCost, ManaSymbols.Type.TOOLTIP);

        int symbolCount = 0;
        int offset = 0;
        while ((offset = castingCost.indexOf("<img", offset) + 1) != 0) {
            symbolCount++;
        }

        String fontFamily = "tahoma";
        int fontSize = GUISizeHelper.cardTooltipFontSize;

        /*if (prefs.fontFamily == CardFontFamily.arial)
         fontFamily = "arial";
         else if (prefs.fontFamily == CardFontFamily.verdana) {
         fontFamily = "verdana";
         }*/
        final StringBuilder buffer = new StringBuilder(512);
        buffer.append("<html><body style='font-family:");
        buffer.append(fontFamily);
        buffer.append(";font-size:");
        buffer.append(fontSize);
        buffer.append("pt;margin:0px 1px 0px 1px'>");
        buffer.append("<table cellspacing=0 cellpadding=0 border=0 width='100%'>");
        buffer.append("<tr><td valign='top'><b>");
        buffer.append(card.getDisplayName());
        if (card.isGameObject()) {
            buffer.append(" [").append(card.getId().toString().substring(0, 3)).append(']');
        }
        buffer.append("</b></td><td align='right' valign='top' style='width:");
        buffer.append(symbolCount * GUISizeHelper.cardTooltipFontSize);
        buffer.append("px'>");
        if (!card.isSplitCard()) {
            buffer.append(castingCost);
        }
        buffer.append("</td></tr></table>");
        buffer.append("<table cellspacing=0 cellpadding=0 border=0 width='100%'><tr><td style='margin-left: 1px'>");
        String imageSize = " width=" + GUISizeHelper.cardTooltipFontSize + " height=" + GUISizeHelper.cardTooltipFontSize + '>';
        if (card.getColor().isWhite()) {
            buffer.append("<img src='").append(getResourcePath("card/color_ind_white.png")).append("' alt='W' ").append(imageSize);
        }
        if (card.getColor().isBlue()) {
            buffer.append("<img src='").append(getResourcePath("card/color_ind_blue.png")).append("' alt='U' ").append(imageSize);
        }
        if (card.getColor().isBlack()) {
            buffer.append("<img src='").append(getResourcePath("card/color_ind_black.png")).append("' alt='B' ").append(imageSize);
        }
        if (card.getColor().isRed()) {
            buffer.append("<img src='").append(getResourcePath("card/color_ind_red.png")).append("' alt='R' ").append(imageSize);
        }
        if (card.getColor().isGreen()) {
            buffer.append("<img src='").append(getResourcePath("card/color_ind_green.png")).append("' alt='G' ").append(imageSize);
        }
        if (!card.getColor().isColorless()) {
            buffer.append("&nbsp;&nbsp;");
        }
        buffer.append(getTypes(card));
        buffer.append("</td><td align='right'>");
        String rarity;
        if (card.getRarity() == null) {
            rarity = Rarity.COMMON.getCode();
            buffer.append("<b color='black'>");
        } else {
            switch (card.getRarity()) {
                case RARE:
                    buffer.append("<b color='#FFBF00'>");
                    break;
                case UNCOMMON:
                    buffer.append("<b color='silver'>");
                    break;
                case COMMON:
                    buffer.append("<b color='black'>");
                    break;
                case MYTHIC:
                    buffer.append("<b color='#D5330B'>");
                    break;
            }
            rarity = card.getRarity().getCode();
        }
        if (card.getExpansionSetCode() != null) {
            buffer.append(ManaSymbols.replaceSetCodeWithHTML(card.getExpansionSetCode().toUpperCase(Locale.ENGLISH), rarity, GUISizeHelper.symbolTooltipSize));
        }
        buffer.append("</td></tr></table>");

        String pt = "";
        if (card.isCreature()) {
            pt = card.getPower() + '/' + card.getToughness();
        } else if (card.isPlanesWalker()) {
            pt = card.getLoyalty();
        }

        buffer.append("<table cellspacing=0 cellpadding=0 border=0 width='100%' valign='bottom'><tr><td><b>");
        buffer.append(pt).append("</b></td>");
        buffer.append("<td align='right'>");
        if (!card.isControlledByOwner()) {
            if (card instanceof PermanentView) {
                buffer.append('[').append(((PermanentView) card).getNameOwner()).append("] ");
            } else {
                buffer.append("[only controlled] ");
            }
        }
        if (card.getMageObjectType() != MageObjectType.NULL) {
            buffer.append(card.getMageObjectType().toString());
        }
        buffer.append("</td></tr></table>");

        StringBuilder rule = new StringBuilder("<br/>");
        if (card.isSplitCard()) {
            rule.append("<table cellspacing=0 cellpadding=0 border=0 width='100%'>");
            rule.append("<tr><td valign='top'><b>");
            rule.append(card.getLeftSplitName());
            rule.append("</b></td><td align='right' valign='top' style='width:");
            rule.append(card.getLeftSplitCosts().getSymbols().size() * GUISizeHelper.symbolTooltipSize + 1);
            rule.append("px'>");
            rule.append(card.getLeftSplitCosts().getText());
            rule.append("</td></tr></table>");
            for (String ruling : card.getLeftSplitRules()) {
                if (ruling != null && !ruling.replace(".", "").trim().isEmpty()) {
                    rule.append("<p style='margin: 2px'>").append(ruling).append("</p>");
                }
            }
            rule.append("<table cellspacing=0 cellpadding=0 border=0 width='100%'>");
            rule.append("<tr><td valign='top'><b>");
            rule.append(card.getRightSplitName());
            rule.append("</b></td><td align='right' valign='top' style='width:");
            rule.append(card.getRightSplitCosts().getSymbols().size() * GUISizeHelper.symbolTooltipSize + 1);
            rule.append("px'>");
            rule.append(card.getRightSplitCosts().getText());
            rule.append("</td></tr></table>");
            for (String ruling : card.getRightSplitRules()) {
                if (ruling != null && !ruling.replace(".", "").trim().isEmpty()) {
                    rule.append("<p style='margin: 2px'>").append(ruling).append("</p>");
                }
            }
        }
        if (!textLines.lines.isEmpty()) {
            for (String textLine : textLines.lines) {
                if (textLine != null && !textLine.replace(".", "").trim().isEmpty()) {
                    rule.append("<p style='margin: 2px'>").append(textLine).append("</p>");
                }
            }
        }

        String legal = rule.toString();
        if (!legal.isEmpty()) {
            legal = legal.replaceAll("\\{this\\}", card.getName().isEmpty() ? "this" : card.getName());
            legal = legal.replaceAll("\\{source\\}", card.getName().isEmpty() ? "this" : card.getName());
            buffer.append(ManaSymbols.replaceSymbolsWithHTML(legal, ManaSymbols.Type.TOOLTIP));
        }

        Zone zone = card.getZone();
        if (zone != null) {
            buffer.append("<p style='margin: 2px'><b>Card Zone:</b> ").append(zone).append("</p>");
        }

        buffer.append("<br></body></html>");
        return buffer;
    }

    private static String getResourcePath(String image) {
        return GuiDisplayUtil.class.getClassLoader().getResource(image).toString();
    }

    private static String getTypes(CardView card) {
        String types = "";
        for (SuperType superType : card.getSuperTypes()) {
            types += superType.toString() + ' ';
        }
        for (CardType cardType : card.getCardTypes()) {
            types += cardType.toString() + ' ';
        }
        if (!card.getSubTypes().isEmpty()) {
            types += "- ";
        }
        for (SubType subType : card.getSubTypes()) {
            types += subType + " ";
        }
        return types.trim();
    }
}
