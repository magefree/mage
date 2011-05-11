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

package mage.client.deckeditor.table;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel;

import mage.cards.MageCard;
import mage.client.cards.BigCard;
import mage.client.cards.CardEventSource;
import mage.client.cards.ICardGrid;
import mage.client.constants.Constants.SortBy;
import mage.client.plugins.impl.Plugins;
import mage.client.util.Config;
import mage.client.util.Event;
import mage.client.util.ImageHelper;
import mage.client.util.Listener;
import mage.view.CardView;
import mage.view.CardsView;

/**
 * Table Model for card list.
 * 
 * @author nantuko
 */
public class TableModel extends AbstractTableModel implements ICardGrid {

	private static final long serialVersionUID = -528008802935423088L;

	protected CardEventSource cardEventSource = new CardEventSource();
	protected BigCard bigCard;
	protected UUID gameId;
	private Map<UUID, MageCard> cards = new LinkedHashMap<UUID, MageCard>();
	private List<MageCard> view = new ArrayList<MageCard>();
	private Dimension cardDimension;

	private String column[] = { "", "Name", "Cost", "Color", "Type", "Stats",
			"Rarity", "Set" };

	private int recentSortedColumn;
	private boolean recentAscending;

	public void loadCards(CardsView showCards, SortBy sortBy, boolean piles,
			BigCard bigCard, UUID gameId) {
		this.bigCard = bigCard;
		this.gameId = gameId;
		for (CardView card : showCards.values()) {
			if (!cards.containsKey(card.getId())) {
				addCard(card, bigCard, gameId);
			}
		}
		for (Iterator<Entry<UUID, MageCard>> i = cards.entrySet().iterator(); i
				.hasNext();) {
			Entry<UUID, MageCard> entry = i.next();
			if (!showCards.containsKey(entry.getKey())) {
				i.remove();
				for (MageCard v : view) {
					if (v.getOriginal().getId().equals(entry.getKey())) {
						view.remove(v);
						break;
					}
				}
			}
		}
		drawCards(sortBy, piles);
	}

	public void clear() {
		view.clear();
		cards.clear();
	}

	public int getRowCount() {
		return view.size();
	}

	public int getColumnCount() {
		return column.length;
	}

	public String getColumnName(int n) {
		return column[n];
	}

	public Object getValueAt(int row, int column) {
		return getColumn(view.get(row), column);
	}

	private Object getColumn(Object obj, int column) {
		MageCard c = (MageCard) obj;
		switch (column) {
		case 0:
			return "";
		case 1:
			return c.getOriginal().getName();
		case 2:
			StringBuilder s = new StringBuilder();
			for (String cost : c.getOriginal().getManaCost()) {
				s.append(cost);
			}
			String cost = s.toString();
			if (cost.isEmpty()) {
				return "0";
			}
			return cost;
		case 3:
			return CardHelper.getColor(c);
		case 4:
			return CardHelper.getType(c);
		case 5:
			return CardHelper.isCreature(c) ? c.getOriginal().getPower() + "/"
					+ c.getOriginal().getToughness() : "-";
		case 6:
			return c.getOriginal().getRarity().toString();
		case 7:
			return c.getOriginal().getExpansionSetCode();
		default:
			return "error";
		}
	}

	private void addCard(CardView card, BigCard bigCard, UUID gameId) {
		if (cardDimension == null) {
			cardDimension = new Dimension(Config.dimensions.frameWidth,
					Config.dimensions.frameHeight);
		}
		MageCard cardImg = Plugins.getInstance().getMageCard(card, bigCard,
				cardDimension, gameId, true);
		cards.put(card.getId(), cardImg);
		cardImg.update(card);
		view.add(cardImg);
	}

	public void drawCards(SortBy sortBy, boolean piles) {
		fireTableDataChanged();
	}

	public void removeCard(UUID cardId) {
		cards.remove(cardId);
		for (MageCard v : view) {
			if (v.getOriginal().getId().equals(cardId)) {
				view.remove(v);
				break;
			}
		}
	}

	public void addCardEventListener(Listener<Event> listener) {
		cardEventSource.addListener(listener);
	}

	public void clearCardEventListeners() {
		cardEventSource.clearListeners();
	}

	public void addListeners(final JTable table) {
		// updates card detail, listens to any key strokes
		/*
		 * table.addKeyListener(new KeyListener() { public void
		 * keyPressed(KeyEvent ev) { }
		 * 
		 * public void keyTyped(KeyEvent ev) { }
		 * 
		 * public void keyReleased(KeyEvent ev) { int row =
		 * table.getSelectedRow(); if (row != -1) { MageCard card =
		 * (MageCard)cards.values().toArray()[row];
		 * bigCard.setCard(card.getOriginal().getId(), card.getImage(), new
		 * ArrayList<String>(), false); } } });
		 */
		// updates card detail, listens to any mouse clicks
		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				int row = table.getSelectedRow();
				if (row != -1) {
					MageCard card = view.get(row);
					if (card.getOriginal().getId().equals(bigCard.getCardId())) {
						Image image = card.getImage();
						if (image != null && image instanceof BufferedImage) {
							image = ImageHelper.getResizedImage(
									(BufferedImage) image, bigCard.getWidth(),
									bigCard.getHeight());
						}
						bigCard.setCard(card.getOriginal().getId(), image,
								new ArrayList<String>(), false);
					}
				}
			}
		});

		// sorts
		MouseListener mouse = new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				TableColumnModel columnModel = table.getColumnModel();
				int viewColumn = columnModel.getColumnIndexAtX(e.getX());
				int column = table.convertColumnIndexToModel(viewColumn);

				if (column != -1) {
					// sort ascending
					boolean asc = true;
					if (recentSortedColumn == column) {
						asc = !recentAscending;
					}
					boolean change = sort(column, asc);

					fireTableDataChanged();
				}
			}
		};
		table.getTableHeader().addMouseListener(mouse);
	}

	public boolean sort(int column, boolean ascending) {
		// used by addCard() to resort the cards
		recentSortedColumn = column;
		recentAscending = ascending;

		MageCardComparator sorter = new MageCardComparator(column, ascending);
		Collections.sort(view, sorter);

		fireTableDataChanged();

		return true;
	}
}