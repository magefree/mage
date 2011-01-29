package org.mage.plugins.card.info;

import mage.Constants;
import mage.components.CardInfoPane;
import mage.game.permanent.Permanent;
import mage.utils.CardUtil;
import mage.utils.ThreadUtils;
import mage.view.CardView;
import mage.view.CounterView;
import mage.view.PermanentView;
import org.mage.card.arcane.ManaSymbols;
import org.mage.card.arcane.UI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Card info pane for displaying card rules.
 * Supports drawing mana symbols.
 *
 * @author nantuko
 */
public class CardInfoPaneImpl extends JEditorPane implements CardInfoPane {

    private CardView currentCard;

    public CardInfoPaneImpl() {
        UI.setHTMLEditorKit(this);
        setEditable(false);
        setBackground(Color.white);
    }

    public void setCard(final CardView card) {
        if (card == null) return;
        if (isCurrentCard(card)) return;
        currentCard = card;

        ThreadUtils.threadPool.submit(new Runnable() {
            public void run() {
                if (!card.equals(currentCard)) return;

                String manaCost = "";
                for (String m : card.getManaCost()) {
                    manaCost += m;
                }
                String castingCost = UI.getDisplayManaCost(manaCost);
                castingCost = ManaSymbols.replaceSymbolsWithHTML(castingCost, false);

                int symbolCount = 0;
                int offset = 0;
                while ((offset = castingCost.indexOf("<img", offset) + 1) != 0)
                    symbolCount++;

                List<String> rules = card.getRules();
				List<String> rulings = new ArrayList<String>(rules);
				if (card instanceof PermanentView) {
					int count = ((PermanentView)card).getCounters().size();
					if (count > 0) {
						StringBuilder sb = new StringBuilder();
						int index = 0;
						for (CounterView counter: ((PermanentView)card).getCounters()) {
							if (counter.getCount() > 0) {
								if (index == 0) {
									sb.append("<b>Counters:</b> ");
								} else {
									sb.append(", ");
								}
								sb.append(counter.getCount() + "x<i>" + counter.getName() + "</i>");
								index++;
							}
						}
						rulings.add(sb.toString());
					}
				}

                boolean smallImages = true;
                int fontSize = 11;

                String fontFamily = "tahoma";
                /*if (prefs.fontFamily == CardFontFamily.arial)
                        fontFamily = "arial";
                    else if (prefs.fontFamily == CardFontFamily.verdana) {
                        fontFamily = "verdana";
                    }*/

                final StringBuffer buffer = new StringBuffer(512);
                buffer.append("<html><body style='font-family:");
                buffer.append(fontFamily);
                buffer.append(";font-size:");
                buffer.append(fontSize);
                buffer.append("pt;margin:0px 1px 0px 1px'>");
                buffer.append("<table cellspacing=0 cellpadding=0 border=0 width='100%'>");
                buffer.append("<tr><td valign='top'><b>");
                buffer.append(card.getName());
                buffer.append("</b></td><td align='right' valign='top' style='width:");
                buffer.append(symbolCount * 11 + 1);
                buffer.append("px'>");
                buffer.append(castingCost);
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
				buffer.append(ManaSymbols.replaceSetCodeWithHTML(card.getExpansionSetCode().toUpperCase(), rarity));
				buffer.append("</td></tr></table>");

                String pt = "";
                if (CardUtil.isCreature(card)) {
                    pt = card.getPower() + "/" + card.getToughness();
                } else if (CardUtil.isPlaneswalker(card)) {
                    pt = card.getLoyalty().toString();
                }
                if (pt.length() > 0) {
                    buffer.append("<table cellspacing=0 cellpadding=0 border=0 width='100%' valign='bottom'><tr><td>");
                    buffer.append("<b>");
                    buffer.append(pt);
                    buffer.append("</b>");
                    buffer.append("</td></tr></table>");
                }

                String legal = "";
                if (rulings.size() > 0) {
                    legal = legal.replaceAll("#([^#]+)#", "<i>$1</i>");
                    legal = legal.replaceAll("\\s*//\\s*", "<hr width='50%'>");
                    legal = legal.replace("\r\n", "<div style='font-size:5pt'></div>");
                    legal += "<br>";
                    for (String ruling : rulings) {
                        legal += "<p style='margin: 2px'>";
                        legal += ruling;
                        legal += "</p>";
                    }
                }



                if (legal.length() > 0) {
                    //buffer.append("<br>");
                    legal = legal.replaceAll("\\{this\\}", card.getName());
                    legal = legal.replaceAll("\\{source\\}", card.getName());
                    buffer.append(ManaSymbols.replaceSymbolsWithHTML(legal, smallImages));
                }

                buffer.append("<br></body></html>");

                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        if (!card.equals(currentCard)) return;
                        setText(buffer.toString());
                        //System.out.println(buffer.toString());
                        setCaretPosition(0);
						//ThreadUtils.sleep(300);
                    }
                });
            }
        });
    }

    private String getTypes(CardView card) {
        String types = "";
        for (String superType : card.getSuperTypes()) {
            types += superType + " ";
        }
        for (Constants.CardType cardType : card.getCardTypes()) {
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

    public boolean isCurrentCard(CardView card) {
        return currentCard != null && card.equals(currentCard);
    }
}
