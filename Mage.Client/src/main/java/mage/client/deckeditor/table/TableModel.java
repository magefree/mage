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
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;
import java.util.Map.Entry;

/**
 * Table Model for card list.
 * 
 * @author nantuko
 */
public class TableModel extends AbstractTableModel implements ICardGrid {

	private static final long serialVersionUID = -528008802935423088L;

	private static final Logger log = Logger.getLogger(TableModel.class);

	protected CardEventSource cardEventSource = new CardEventSource();
	protected BigCard bigCard;
	protected UUID gameId;
	private Map<UUID, CardView> cards = new LinkedHashMap<UUID, CardView>();
	private List<CardView> view = new ArrayList<CardView>();
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
		for (Iterator<Entry<UUID, CardView>> i = cards.entrySet().iterator(); i
				.hasNext();) {
			Entry<UUID, CardView> entry = i.next();
			if (!showCards.containsKey(entry.getKey())) {
				i.remove();
				for (CardView cv : view) {
					if (cv.getId().equals(entry.getKey())) {
						view.remove(cv);
						break;
					}
				}
			}
		}
		sort(1, true);
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
		CardView c = (CardView) obj;
		switch (column) {
		case 0:
			return "";
		case 1:
			return c.getName();
		case 2:
			StringBuilder s = new StringBuilder();
			for (String cost : c.getManaCost()) {
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
			return CardHelper.isCreature(c) ? c.getPower() + "/"
					+ c.getToughness() : "-";
		case 6:
			return c.getRarity().toString();
		case 7:
			return c.getExpansionSetCode();
		default:
			return "error";
		}
	}

	private void addCard(CardView card, BigCard bigCard, UUID gameId) {
		if (cardDimension == null) {
			cardDimension = new Dimension(Config.dimensions.frameWidth,
					Config.dimensions.frameHeight);
		}
		cards.put(card.getId(), card);
		view.add(card);
	}

	public void drawCards(SortBy sortBy, boolean piles) {
		fireTableDataChanged();
	}

	public void removeCard(UUID cardId) {
		cards.remove(cardId);
		for (CardView cv : view) {
			if (cv.getId().equals(cardId)) {
				view.remove(cv);
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

		table.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent ev) {}
			public void keyTyped(KeyEvent ev) {}

			public void keyReleased(KeyEvent ev) {
				int row = table.getSelectedRow();
				if (row != -1) {
					showImage(row);
				}
			}
		});

		// updates card detail, listens to any mouse clicks
		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				int row = table.getSelectedRow();
				if (row != -1) {
					showImage(row);
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
					sort(column, asc);
					fireTableDataChanged();
				}
			}
		};
		table.getTableHeader().addMouseListener(mouse);
	}

	private void showImage(int row) {
		CardView card = view.get(row);
		if (!card.getId().equals(bigCard.getCardId())) {
			Image image = Plugins.getInstance().getOriginalImage(card);
			if (image != null && image instanceof BufferedImage) {
				image = ImageHelper.getResizedImage((BufferedImage) image, bigCard.getWidth(),
						bigCard.getHeight());
				bigCard.setCard(card.getId(), image, new ArrayList<String>(), false);
			}
		}
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