package mage.client.deckeditor.table;

import mage.client.MageFrame;
import mage.client.cards.BigCard;
import mage.client.cards.CardEventSource;
import mage.client.cards.ICardGrid;
import mage.client.deckeditor.SortSetting;
import mage.client.plugins.impl.Plugins;
import mage.client.util.ClientDefaultSettings;
import mage.client.util.ClientEventType;
import mage.client.util.Event;
import mage.client.util.Listener;
import mage.client.util.gui.GuiDisplayUtil;
import mage.constants.EnlargeMode;
import mage.cards.RateCard;
import mage.view.CardView;
import mage.view.CardsView;
import org.apache.log4j.Logger;
import org.jdesktop.swingx.JXPanel;
import org.mage.card.arcane.ManaSymbols;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.*;
import java.util.Map.Entry;

/**
 * Table Model for card list.
 *
 * @author nantuko
 */
public class TableModel extends AbstractTableModel implements ICardGrid {

    private static final long serialVersionUID = -528008802935423088L;

    private static final Logger log = Logger.getLogger(TableModel.class);

    protected final CardEventSource cardEventSource = new CardEventSource();
    protected BigCard bigCard;
    protected UUID gameId;
    private final Map<UUID, CardView> cards = new LinkedHashMap<>();
    private final Map<String, Integer> cardsNoCopies = new LinkedHashMap<>();
    private final List<CardView> view = new ArrayList<>();
    private Dimension cardDimension;

    private boolean displayNoCopies = false;
    private UpdateCountsCallback updateCountsCallback;

    private final String[] column = {"Qty", "Name", "Cost", "Color", "Type", "Stats", "Rarity", "Set", "Card number", "Draft Rating",  "Color Identity"};
    public final int COLUMN_INDEX_COST = 2;
    public final int COLUMN_INDEX_RATING = 9;
    public final int COLUMN_INDEX_COLOR_IDENTITY = 10;

    private SortSetting sortSetting;
    private int recentSortedColumn;
    private boolean recentAscending;

    private boolean numberEditable;

    public TableModel() {
        this.numberEditable = false;
    }

    public void clear() {
        this.clearCardEventListeners();
        this.clearCards();
        this.view.clear();
    }

    @Override
    public Object getCardsStore() {
        return this.cards;
    }

    @Override
    public void clearCardsStoreBeforeUpdate() {
        this.cards.clear();
        this.view.clear();
    }

    @Override
    public void loadCards(CardsView showCards, SortSetting sortSetting, BigCard bigCard, UUID gameId) {
        this.loadCards(showCards, sortSetting, bigCard, gameId, true);
    }

    @Override
    public void loadCards(CardsView showCards, SortSetting sortSetting, BigCard bigCard, UUID gameId, boolean merge) {
        if (this.sortSetting == null) {
            this.sortSetting = sortSetting;
        }
        this.bigCard = bigCard;
        this.gameId = gameId;
        int landCount = 0;
        int creatureCount = 0;
        int instantCount = 0;
        int sorceryCount = 0;
        int enchantmentCount = 0;
        int artifactCount = 0;
        if (!merge) {
            this.clearCards();
            for (CardView card : showCards.values()) {
                addCard(card, bigCard, gameId);
            }
        } else {
            for (CardView card : showCards.values()) {
                if (!cards.containsKey(card.getId())) {
                    addCard(card, bigCard, gameId);
                }
                if (updateCountsCallback != null) {
                    if (card.isLand()) {
                        landCount++;
                    }
                    if (card.isCreature()) {
                        creatureCount++;
                    }
                    if (card.isInstant()) {
                        instantCount++;
                    }
                    if (card.isSorcery()) {
                        sorceryCount++;
                    }
                    if (card.isEnchantment()) {
                        enchantmentCount++;
                    }
                    if (card.isArtifact()) {
                        artifactCount++;
                    }
                }
            }

            // no easy logic for merge :)
            for (Iterator<Entry<UUID, CardView>> i = cards.entrySet().iterator(); i.hasNext(); ) {
                Entry<UUID, CardView> entry = i.next();
                if (!showCards.containsKey(entry.getKey())) {
                    i.remove();
                    if (displayNoCopies) {
                        String key = entry.getValue().getName() + entry.getValue().getExpansionSetCode() + entry.getValue().getCardNumber();
                        if (cardsNoCopies.containsKey(key)) {
                            Integer count = cardsNoCopies.get(key);
                            count--;
                            if (count > 0) {
                                cardsNoCopies.put(key, count);
                            } else {
                                cardsNoCopies.remove(key);
                            }
                            for (int j = 0; j < view.size(); j++) {
                                CardView cv = view.get(j);
                                if (cv.getId().equals(entry.getValue().getId())) {
                                    if (count > 0) {
                                        // replace by another card with the same name+setCode
                                        String key1 = cv.getName() + cv.getExpansionSetCode() + cv.getCardNumber();
                                        for (CardView cardView : cards.values()) {
                                            String key2 = cardView.getName() + cardView.getExpansionSetCode() + cardView.getCardNumber();
                                            if (key1.equals(key2)) {
                                                view.set(j, cardView);
                                                break;
                                            }
                                        }
                                    } else {
                                        view.remove(j);
                                    }
                                    break;
                                }
                            }
                        }
                    } else {
                        view.removeIf(cardView -> cardView.getId().equals(entry.getKey()));
                    }
                }
            }

            if (updateCountsCallback != null) {
                updateCountsCallback.update(cards.size(), creatureCount, landCount, sorceryCount, instantCount, enchantmentCount, artifactCount);
            }
        }

        sort(this.sortSetting.getSortIndex(), this.sortSetting.isAscending());
        drawCards(sortSetting);
    }

    @Override
    public void refresh() {
        fireTableDataChanged();
    }

    public void clearCards() {
        view.clear();
        cards.clear();
    }

    @Override
    public int getRowCount() {
        return view.size();
    }

    @Override
    public int getColumnCount() {
        return column.length;
    }

    @Override
    public String getColumnName(int n) {
        return column[n];
    }

    @Override
    public Object getValueAt(int row, int column) {
        return getColumn(view.get(row), column);
    }

    private Object getColumn(Object obj, int column) {
        CardView c = (CardView) obj;
        switch (column) {
            case 0:
                if (displayNoCopies) {
                    String key = c.getName() + c.getExpansionSetCode() + c.getCardNumber();
                    Integer count = cardsNoCopies.get(key);
                    return count != null ? count : "";
                }
                return "";
            case 1:
                return c.getDisplayFullName(); // show full name in deck editor table, e.g. adventure with spell name
            case 2:
                // new svg images version
                return ManaSymbols.getClearManaCost(c.getManaCostStr());
                /*
                // old html images version
                String manaCost = "";
                for (String m : c.getManaCost()) {
                    manaCost += m;
                }
                String castingCost = UI.getDisplayManaCost(manaCost);
                castingCost = ManaSymbols.replaceSymbolsWithHTML(castingCost, ManaSymbols.Type.TABLE);
                return "<html>" + castingCost + "</html>";
                return castingCost;
                */
            case 3:
                return c.getColorText();
            case 4:
                return c.getTypeText();
            case 5:
                return c.isCreature() ? c.getPower() + '/'
                        + c.getToughness() : "-";
            case 6:
                return c.getRarity() == null ? "" : c.getRarity().toString();
            case 7:
                return c.getExpansionSetCode();
            case 8:
                return c.getCardNumber();
            case 9:
                return RateCard.rateCard(c, null);
            case 10:
                return ManaSymbols.getClearManaCost(c.getColorIdentityStr());
            default:
                return "error";
        }
    }

    private void addCard(CardView card, BigCard bigCard, UUID gameId) {
        if (cardDimension == null) {
            cardDimension = new Dimension(ClientDefaultSettings.dimensions.getFrameWidth(),
                    ClientDefaultSettings.dimensions.getFrameHeight());
        }
        cards.put(card.getId(), card);

        if (displayNoCopies) {
            String key = card.getName() + card.getExpansionSetCode() + card.getCardNumber();
            Integer count = 1;
            if (cardsNoCopies.containsKey(key)) {
                count = cardsNoCopies.get(key) + 1;
            } else {
                view.add(card);
            }
            cardsNoCopies.put(key, count);
        } else {
            view.add(card);
        }
    }

    @Override
    public void drawCards(SortSetting sortSetting) {
        fireTableDataChanged();
    }

    public void removeCard(UUID cardId) {
        cards.remove(cardId);
        view.removeIf(cardView -> cardView.getId().equals(cardId));
    }

    @Override
    public void addCardEventListener(Listener<Event> listener) {
        cardEventSource.addListener(listener);
    }

    @Override
    public void clearCardEventListeners() {
        cardEventSource.clearListeners();
    }

    public void setNumber(int index, int number) {
        CardView card = view.get(index);
        cardEventSource.fireEvent(card, ClientEventType.SET_NUMBER, number);
    }

    public void doubleClick(int index, MouseEvent e, boolean forceFakeAltDown) {
        CardView card = view.get(index);
        cardEventSource.fireEvent(card, ClientEventType.CARD_DOUBLE_CLICK, e, forceFakeAltDown);
    }

    public void removeFromMainEvent(int index) {
        cardEventSource.fireEvent(ClientEventType.DECK_REMOVE_SELECTION_MAIN);
    }

    public void removeFromSideEvent(int index) {
        cardEventSource.fireEvent(ClientEventType.DECK_REMOVE_SELECTION_SIDEBOARD);
    }

    public void addListeners(final JTable table) {
        // updates card detail, listens to any key strokes

        table.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent ev) {
            }

            @Override
            public void keyTyped(KeyEvent ev) {
            }

            @Override
            public void keyReleased(KeyEvent ev) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    showImage(row);
                }
            }
        });

        // updates card detail, listens to any mouse clicks
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (!SwingUtilities.isLeftMouseButton(e)) {
                    return;
                }
                int row = table.getSelectedRow();
                if (row != -1) {
                    showImage(row);
                }
            }
        });

        // sorts
        MouseListener mouse = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (!SwingUtilities.isLeftMouseButton(e)) {
                    return;
                }
                TableColumnModel columnModel = table.getColumnModel();
                int viewColumn = columnModel.getColumnIndexAtX(e.getX());
                int column = table.convertColumnIndexToModel(viewColumn);

                if (column != -1) {
                    // sort ascending
                    boolean asc = true;
                    if (recentSortedColumn == column) {
                        asc = !recentAscending;
                    }
                    sortSetting.setSortIndex(column);
                    sortSetting.setAscending(asc);
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
            if (!MageFrame.isLite()) {
                Image image = Plugins.instance.getOriginalImage(card);
                if (image instanceof BufferedImage) {
                    // XXX: scaled to fit width
                    bigCard.setCard(card.getId(), EnlargeMode.NORMAL, image, new ArrayList<>(), false);
                } else {
                    drawCardText(card);
                }
            } else {
                drawCardText(card);
            }
        }
    }

    private void drawCardText(CardView card) {
        JXPanel panel = GuiDisplayUtil.getDescription(card, bigCard.getWidth(), bigCard.getHeight());
        panel.setVisible(true);
        bigCard.hideTextComponent();
        bigCard.addJXPanel(card.getId(), panel);
    }

    public List<CardView> getCardsView() {
        return view;
    }

    public boolean sort(int column, boolean ascending) {
        // used by addCard() to resort the cards
        recentSortedColumn = column;
        recentAscending = ascending;

        MageCardComparator sorter = new MageCardComparator(column, ascending);
        view.sort(sorter);

        fireTableDataChanged();

        return true;
    }

    public int getRecentSortedColumn() {
        return recentSortedColumn;
    }

    public boolean isRecentAscending() {
        return recentAscending;
    }

    public void setDisplayNoCopies(boolean value) {
        this.displayNoCopies = value;
    }

    public void setUpdateCountsCallback(UpdateCountsCallback callback) {
        this.updateCountsCallback = callback;
    }

    public void setNumberEditable(boolean numberEditable) {
        this.numberEditable = numberEditable;
    }

    @Override
    public int cardsSize() {
        return cards.size();
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        if (numberEditable && col == 0) {
            return true;
        }
        return super.isCellEditable(row, col);
    }

}
