package mage.client.util.gui;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import mage.constants.CardType;
import mage.constants.MageObjectType;
import mage.utils.CardUtil;
import mage.view.CardView;
import mage.view.CounterView;
import mage.view.PermanentView;

import org.jdesktop.swingx.JXPanel;
import org.mage.card.arcane.ManaSymbols;
import org.mage.card.arcane.UI;

public class GuiDisplayUtil {
    private static final Font cardNameFont = new Font("Calibri", Font.BOLD, 15);
    private static final Insets DEFAULT_INSETS = new Insets(0, 0, 70, 25);
    private static final Insets COMPONENT_INSETS = new Insets(0, 0, 40, 40);

    public static JXPanel getDescription(CardView card, int width, int height) {
        JXPanel descriptionPanel = new JXPanel();

        //descriptionPanel.setAlpha(.8f);
        descriptionPanel.setBounds(0, 0,  width, height);
        descriptionPanel.setVisible(false);
        descriptionPanel.setLayout(null);

        //descriptionPanel.setBorder(BorderFactory.createLineBorder(Color.green));

        JButton j = new JButton("");
        j.setBounds(0, 0,  width, height);
        j.setBackground(Color.black);
        j.setLayout(null);

        JLabel cardText = new JLabel();
        cardText.setBounds(5, 5,  width - 10, height - 10);
        cardText.setForeground(Color.white);
        cardText.setFont(cardNameFont);
        cardText.setVerticalAlignment(SwingConstants.TOP);
        j.add(cardText);
       
        cardText.setText(getRulefromCardView(card).toString());

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
        return out.toString().toLowerCase();
    }


    public static void keepComponentInsideScreen(int x, int y, Component c) {
        Dimension screenDim = c.getToolkit().getScreenSize();
        GraphicsConfiguration g = c.getGraphicsConfiguration();
        if (g != null) {
            Insets insets =    c.getToolkit().getScreenInsets(g);

            if (x + c.getWidth() > screenDim.width - insets.right) {
                x = (screenDim.width - insets.right) - c.getWidth();
            } else if (x < insets.left) {
                x = insets.left;
            }

            if (y + c.getHeight() > screenDim.height - insets.bottom) {
                y = (screenDim.height - insets.bottom) - c.getHeight();
            } else if (y < insets.top) {
                y = insets.top;
            }

            c.setLocation(x, y);
        } else {
            System.out.println("GuiDisplayUtil::keepComponentInsideScreen -> no GraphicsConfiguration");
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
    
    public static StringBuilder getRulefromCardView(CardView card) {
        String manaCost = "";
        for (String m : card.getManaCost()) {
            manaCost += m;
        }
        String castingCost = UI.getDisplayManaCost(manaCost);
        castingCost = ManaSymbols.replaceSymbolsWithHTML(castingCost, ManaSymbols.Type.CARD);

        int symbolCount = 0;
        int offset = 0;
        while ((offset = castingCost.indexOf("<img", offset) + 1) != 0) {
            symbolCount++;
        }

        
        ArrayList<String> rulings = new ArrayList<>(card.getRules());

        if (card.getMageObjectType().equals(MageObjectType.PERMANENT)) {
            if (card.getPairedCard() != null) {
                rulings.add("<span color='green'><i>Paired with another creature</i></span>");
            }
        }
        if (card.getMageObjectType().canHaveCounters()) {
            ArrayList<CounterView> counters = new ArrayList<>();
            if (card instanceof PermanentView) {
                if (((PermanentView) card).getCounters() != null) {
                    counters = new ArrayList<>(((PermanentView) card).getCounters());
                }                
            } else {
                if (card.getCounters() != null) {
                    counters = new ArrayList<>(card.getCounters());
                }
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
                rulings.add(sb.toString());
            }
        }
        if (card.getMageObjectType().isPermanent() && card instanceof PermanentView) {
            int damage = ((PermanentView)card).getDamage();
            if (damage > 0) {
                rulings.add("<span color='red'><b>Damage dealt:</b> " + damage + "</span>");
            }
        }

        int fontSize = 11;

        String fontFamily = "tahoma";
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
        buffer.append("</b></td><td align='right' valign='top' style='width:");
        buffer.append(symbolCount * 11 + 1);
        buffer.append("px'>");
        if (!card.isSplitCard()) {
            buffer.append(castingCost);
        }
        buffer.append("</td></tr></table>");
        buffer.append("<table cellspacing=0 cellpadding=0 border=0 width='100%'><tr><td style='margin-left: 1px'>");
        buffer.append(getTypes(card));
        buffer.append("</td><td align='right'>");
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
        String rarity = card.getRarity().getCode();
        if (card.getExpansionSetCode() != null) {
            buffer.append(ManaSymbols.replaceSetCodeWithHTML(card.getExpansionSetCode().toUpperCase(), rarity));
        }
        buffer.append("</td></tr></table>");

        String pt = "";
        if (CardUtil.isCreature(card)) {
            pt = card.getPower() + "/" + card.getToughness();
        } else if (CardUtil.isPlaneswalker(card)) {
            pt = card.getLoyalty().toString();
        }

        buffer.append("<table cellspacing=0 cellpadding=0 border=0 width='100%' valign='bottom'><tr><td><b>");
        buffer.append(pt).append("</b></td>");
        buffer.append("<td align='right'>");
        if (!card.isControlledByOwner()) {
            buffer.append("[only controlled] ");
        }
        buffer.append(card.getMageObjectType().toString()).append("</td>");
        buffer.append("</tr></table>");

        StringBuilder rule = new StringBuilder("<br/>");
        if (card.isSplitCard()) {
            rule.append("<table cellspacing=0 cellpadding=0 border=0 width='100%'>");
            rule.append("<tr><td valign='top'><b>");
            rule.append(card.getLeftSplitName());
            rule.append("</b></td><td align='right' valign='top' style='width:");
            rule.append(card.getLeftSplitCosts().getSymbols().size() * 11 + 1);
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
            rule.append(card.getRightSplitCosts().getSymbols().size() * 11 + 1);
            rule.append("px'>");
            rule.append(card.getRightSplitCosts().getText());
            rule.append("</td></tr></table>");
            for (String ruling : card.getRightSplitRules()) {
                if (ruling != null && !ruling.replace(".", "").trim().isEmpty()) {
                    rule.append("<p style='margin: 2px'>").append(ruling).append("</p>");
                }
            }
        }
        if (rulings.size() > 0) {
            for (String ruling : rulings) {
                if (ruling != null && !ruling.replace(".", "").trim().isEmpty()) {
                    rule.append("<p style='margin: 2px'>").append(ruling).append("</p>");
                }
            }                        
        }

        String legal = rule.toString();
        if (legal.length() > 0) {
// this 2 replaces were only done with the empty string, is it any longer needed? (LevelX2)
//                        legal = legal.replaceAll("#([^#]+)#", "<i>$1</i>");
//                        legal = legal.replaceAll("\\s*//\\s*", "<hr width='50%'>");
//                        legal = legal.replace("\r\n", "<div style='font-size:5pt'></div>");
            legal = legal.replaceAll("\\{this\\}", card.getName());
            legal = legal.replaceAll("\\{source\\}", card.getName());
            buffer.append(ManaSymbols.replaceSymbolsWithHTML(legal, ManaSymbols.Type.CARD));
        }

        buffer.append("<br></body></html>");    
        return buffer;
    }
    
    private static String getTypes(CardView card) {
        String types = "";
        for (String superType : card.getSuperTypes()) {
            types += superType + " ";
        }
        for (CardType cardType : card.getCardTypes()) {
            types += cardType.toString() + " ";
        }
        if (card.getSubTypes().size() > 0) {
            types += "- ";
        }
        for (String subType : card.getSubTypes()) {
            types += subType + " ";
        }
        return types.trim();
    }    
}
