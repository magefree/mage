package org.mage.plugins.card.info;

import mage.Constants;
import mage.cards.Card;
import mage.components.CardInfoPane;
import mage.utils.CardUtil;
import org.mage.card.arcane.ManaSymbols;
import org.mage.card.arcane.ThreadUtils;
import org.mage.card.arcane.UI;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Card info pane for displaying card rules.
 * Supports drawing mana symbols.
 *
 * @author nantuko
 */
public class CardInfoPaneImpl extends JEditorPane implements CardInfoPane {

    private Card currentCard;

    public CardInfoPaneImpl() {
        UI.setHTMLEditorKit(this);
		setEditable(false);
		setBackground(Color.white);
    }

    public void setCard (final Card card) {
        if (card == null) return;
		if (isCurrentCard(card)) return;
		currentCard = card;

        ThreadUtils.threadPool.submit(new Runnable() {
			public void run () {
				if (!card.equals(currentCard)) return;

                String castingCost = UI.getDisplayManaCost(card.getManaCost().getText());
				castingCost = ManaSymbols.replaceSymbolsWithHTML(castingCost, false);

                int symbolCount = 0;
				int offset = 0;
				while ((offset = castingCost.indexOf("<img", offset) + 1) != 0)
					symbolCount++;

                List<String> rulings = card.getRules();

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
				buffer.append("<table cellspacing=0 cellpadding=0 border=0 width='100%'><tr><td>");
				buffer.append(getTypes(card));
                buffer.append("</td><td align='right'>");
                switch (card.getRarity()) {
					case RARE:
						buffer.append("<b color='#E1D519'>");
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
				buffer.append(card.getExpansionSetCode().toUpperCase());
                buffer.append("</td></tr></table>");

                String legal = "";
                if (rulings.size() > 0) {
                    legal = legal.replaceAll("#([^#]+)#", "<i>$1</i>");
				    legal = legal.replaceAll("\\s*//\\s*", "<hr width='50%'>");
				    legal = legal.replace("\r\n", "<div style='font-size:5pt'></div>");
					legal += "<br>";
					for (String ruling : rulings) {
						legal += ruling;
						legal += "<br><br>";
					}
				}

                if (legal.length() > 0) {
					buffer.append("<br>");
                    legal = legal.replaceAll("\\{this\\}", card.getName());
                    legal = legal.replaceAll("\\{source\\}", card.getName());
					buffer.append(ManaSymbols.replaceSymbolsWithHTML(legal, smallImages));
				}

                String pt = "";
                if (card.getCardType().contains(Constants.CardType.CREATURE)) {
                    pt = card.getPower() + "/" + card.getToughness();
                } else if (card.getCardType().contains(Constants.CardType.PLANESWALKER)) {
                    pt = card.getLoyalty().toString();
                }
                if (pt.length() > 0) {
                    buffer.append("<table cellspacing=0 cellpadding=0 border=0 width='100%'><tr><td>");
                    buffer.append("</td><td align='right'>");
					buffer.append("<b>");
					buffer.append(pt);
					buffer.append("</b>");
					buffer.append("</td></tr></table>");
                }

                buffer.append("<br></body></html>");

                SwingUtilities.invokeLater(new Runnable() {
					public void run () {
						if (!card.equals(currentCard)) return;
						setText(buffer.toString());
                        System.out.println(buffer.toString());
						setCaretPosition(0);
					}
				});
            }
        });
    }

    private String getTypes(Card card) {
        String types = "";
        for (String superType : card.getSupertype()) {
            types += superType + " ";
        }
        for (Constants.CardType cardType : card.getCardType()) {
            types += cardType.toString() + " ";
        }
        if (card.getSubtype().size() > 0) {
            types += "- ";
        }
        for (String subType : card.getSubtype()) {
            types += subType + " ";
        }
        return types.trim();
    }

    public boolean isCurrentCard (Card card) {
		return currentCard != null && card.equals(currentCard);
	}
}
