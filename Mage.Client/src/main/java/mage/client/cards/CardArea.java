package mage.client.cards;

import mage.abilities.icon.CardIconColor;
import mage.abilities.icon.CardIconOrder;
import mage.abilities.icon.CardIconPosition;
import mage.abilities.icon.CardIconRenderSettings;
import mage.cards.MageCard;
import mage.client.dialog.PreferencesDialog;
import mage.client.plugins.impl.Plugins;
import mage.client.util.Event;
import mage.client.util.GUISizeHelper;
import mage.client.util.Listener;
import mage.util.DebugUtil;
import mage.view.*;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.UUID;

/**
 * Panel with cards list, can show cards in two modes:
 *  - cards one by one in the line;
 *  - stacked and multicolumns (if many cards);
 *
 *  Uses in some dialogs like pile choose and cards choosing
 */
public class CardArea extends JPanel implements CardEventProducer {

    private static final Logger logger = Logger.getLogger(CardArea.class);

    protected final CardEventSource cardEventSource = new CardEventSource();

    private static int MAX_CARDS_PER_COLUMN = 20; // max cards amount in one column

    private boolean reloaded = false;
    private final javax.swing.JLayeredPane cardArea;
    private final javax.swing.JScrollPane scrollPane;
    private int yCardCaptionOffsetPercent = 0; // card caption offset (use for moving card caption view center, below mana icons -- for more good UI)
    private int xOffsetBetweenCardsOrColumns = 0; // x offset between cards or columns (in many mode)
    private Dimension cardDimension;
    private int verticalCardOffset;

    private int customRenderMode = -1; // custom render mode tests
    private Dimension customCardSize = null; // custom card size for tests
    private boolean customNeedFullPermanentRender = false; // disable permanent render mode, see CardArea for more info
    private int customXOffsetBetweenCardsOrColumns = 0;
    private CardIconPosition customCardIconPosition = null;
    private CardIconOrder customCardIconOrder = null;
    private CardIconColor customCardIconColor = null;
    private int customCardIconsMaxVisibleCount = 0;

    /**
     * Create the panel.
     */
    public CardArea() {
        setLayout(new BorderLayout(0, 0));

        scrollPane = new JScrollPane();
        add(scrollPane, BorderLayout.CENTER);
        setGUISize();
        cardArea = new JLayeredPane();
        scrollPane.setViewportView(cardArea);
        if (DebugUtil.GUI_GAME_DIALOGS_DRAW_CARDS_AREA_BORDER) {
            this.setBorder(BorderFactory.createLineBorder(Color.yellow));
        }

        // ENABLE non card popup menu
        // if you want process popup menu from card then use special event
        cardArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.isPopupTrigger() || SwingUtilities.isRightMouseButton(e)) {
                    Plugins.instance.getActionCallback().popupMenuPanel(e, CardArea.this);
                }
            }
        });
    }

    public void cleanUp() {
        for (Component comp : cardArea.getComponents()) {
            if (comp instanceof MageCard) {
                ((MageCard) comp).cleanUp();
                cardArea.remove(comp);
            }
        }
    }

    public void changeGUISize() {
        setGUISize();
        for (Component component : cardArea.getComponents()) {
            if (component instanceof MageCard) {
                component.setBounds(0, 0, cardDimension.width, cardDimension.height);
            }
        }
    }

    private void setGUISize() {
        if (customCardSize != null) {
            setCardDimension(customCardSize, GUISizeHelper.otherZonesCardVerticalOffset);
        } else {
            setCardDimension(GUISizeHelper.otherZonesCardDimension, GUISizeHelper.otherZonesCardVerticalOffset);
        }
        if (customXOffsetBetweenCardsOrColumns > 0) {
            xOffsetBetweenCardsOrColumns = customXOffsetBetweenCardsOrColumns;
        }
    }

    public void setCardDimension(Dimension dimension, int verticalCardOffset) {
        this.cardDimension = dimension;
        this.verticalCardOffset = verticalCardOffset;
    }

    private void fixDialogSize() {
        // fix panel size (must include scrolls)
        Dimension newSize = new Dimension(cardArea.getPreferredSize());
        newSize.width += 20;
        newSize.height += 20;
        this.setPreferredSize(newSize);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(GUISizeHelper.getCardsScrollbarUnitInc(cardDimension.width));
        scrollPane.getVerticalScrollBar().setUnitIncrement(GUISizeHelper.getCardsScrollbarUnitInc(cardDimension.height));
    }

    public void loadCards(CardsView showCards, BigCard bigCard, UUID gameId) {
        this.reloaded = true;
        cardArea.removeAll();
        if (showCards != null && showCards.size() < 10) {
            yCardCaptionOffsetPercent = 8; // TODO: need to test
            loadCardsFew(showCards, bigCard, gameId);
        } else {
            yCardCaptionOffsetPercent = 0;
            loadCardsMany(showCards, bigCard, gameId);
        }

        redraw();
        fixDialogSize();
    }

    public void redraw() {
        cardArea.revalidate();
        this.revalidate();
        this.repaint();
    }

    public void loadCardsNarrow(CardsView showCards, BigCard bigCard, UUID gameId) {
        this.reloaded = true;
        cardArea.removeAll();

        yCardCaptionOffsetPercent = 0; // TODO: need to test
        loadCardsMany(showCards, bigCard, gameId);

        cardArea.revalidate();

        this.revalidate();
        this.repaint();

        fixDialogSize();
    }

    private void loadCardsFew(CardsView showCards, BigCard bigCard, UUID gameId) {
        Rectangle rectangle = new Rectangle(cardDimension.width, cardDimension.height);
        int cardsAdded = 0;
        for (CardView card : showCards.values()) {
            if (cardsAdded > 0) {
                rectangle.translate(cardDimension.width + xOffsetBetweenCardsOrColumns, 0);
            }
            addCard(card, bigCard, gameId, rectangle);
            cardsAdded++;
        }
        cardArea.setPreferredSize(new Dimension(
                cardDimension.width * showCards.size() + (cardsAdded * xOffsetBetweenCardsOrColumns),
                cardDimension.height + verticalCardOffset
        ));
    }

    private void addCard(CardView card, BigCard bigCard, UUID gameId, Rectangle rectangle) {
        if (card instanceof AbilityView) {
            CardView tmp = ((AbilityView) card).getSourceCard();
            tmp.overrideRules(card.getRules());
            tmp.setIsAbility(true);
            tmp.overrideTargets(card.getTargets());
            tmp.setAbility(card); // cross-reference, required for ability picker
            card = tmp;
        }

        CardIconRenderSettings currentIconsRender;
        if (this.customRenderMode >= 0) {
            // debug
            currentIconsRender = new CardIconRenderSettings()
                    .withDebugMode(true)
                    .withCustomPosition(customCardIconPosition)
                    .withCustomOrder(customCardIconOrder)
                    .withCustomColor(customCardIconColor)
                    .withCustomMaxVisibleCount(customCardIconsMaxVisibleCount)
                    .withCustomIconSizePercent(30);
        } else {
            // default
            currentIconsRender = new CardIconRenderSettings();
        }

        MageCard cardPanel = Plugins.instance.getMageCard(card, bigCard, currentIconsRender, cardDimension, gameId, true, true,
                customRenderMode != -1 ? customRenderMode : PreferencesDialog.getRenderMode(), customNeedFullPermanentRender);
        cardPanel.setCardContainerRef(this);
        cardPanel.update(card);
        cardPanel.setCardBounds(rectangle.x, rectangle.y, cardDimension.width, cardDimension.height);
        cardArea.add(cardPanel);
        cardArea.moveToFront(cardPanel);

        // new card have same settings as current view
        cardPanel.setCardCaptionTopOffset(yCardCaptionOffsetPercent);
        cardPanel.showCardTitle();
    }

    private void loadCardsMany(CardsView showCards, BigCard bigCard, UUID gameId) {
        int columns = 1;
        if (showCards != null && !showCards.isEmpty()) {
            Rectangle rectangle = new Rectangle(cardDimension.width, cardDimension.height);
            int count = 0;
            for (CardView card : showCards.values()) {
                addCard(card, bigCard, gameId, rectangle);
                if (count >= MAX_CARDS_PER_COLUMN) {
                    rectangle.translate(cardDimension.width + xOffsetBetweenCardsOrColumns, -(MAX_CARDS_PER_COLUMN * verticalCardOffset));
                    columns++;
                    count = 0;
                } else {
                    rectangle.translate(0, verticalCardOffset);
                    count++;
                }
            }
        }
        cardArea.setPreferredSize(new Dimension(cardDimension.width * columns + xOffsetBetweenCardsOrColumns * (columns - 1), cardDimension.height + (MAX_CARDS_PER_COLUMN * verticalCardOffset)));
    }

    public boolean isReloaded() {
        return this.reloaded;
    }

    public void clearReloaded() {
        this.reloaded = false;
    }

    public void selectCards(List<UUID> selected) {
        for (Component component : cardArea.getComponents()) {
            if (component instanceof MageCard) {
                MageCard mageCard = (MageCard) component;
                if (selected.contains(mageCard.getOriginal().getId())) {
                    mageCard.setSelected(true);
                }
            }
        }
    }

    public void markCards(List<UUID> marked) {
        for (Component component : cardArea.getComponents()) {
            if (component instanceof MageCard) {
                MageCard mageCard = (MageCard) component;
                if (marked.contains(mageCard.getOriginal().getId())) {
                    mageCard.setChoosable(true);
                }
            }
        }
    }

    public void setPopupMenu(JPopupMenu popupMenu) {
        for (Component component : cardArea.getComponents()) {
            if (component instanceof MageCard) {
                MageCard mageCard = (MageCard) component;
                mageCard.setPopupMenu(popupMenu);
            }
        }
    }

    public void addCardEventListener(Listener<Event> listener) {
        cardEventSource.addListener(listener);
    }

    public void clearCardEventListeners() {
        cardEventSource.clearListeners();
    }

    public void setCustomRenderMode(int customRenderMode) {
        this.customRenderMode = customRenderMode;
    }

    public void setCustomNeedFullPermanentRender(boolean customNeedFullPermanentRender) {
        this.customNeedFullPermanentRender = customNeedFullPermanentRender;
    }

    public void setCustomCardSize(Dimension customCardSize) {
        this.customCardSize = customCardSize;
    }

    public void setCustomXOffsetBetweenCardsOrColumns(int customXOffsetBetweenCardsOrColumns) {
        this.customXOffsetBetweenCardsOrColumns = customXOffsetBetweenCardsOrColumns;
    }

    public void setCustomCardIconsPanelPosition(CardIconPosition panelPosition) {
        this.customCardIconPosition = panelPosition;
    }

    public void setCustomCardIconsPanelOrder(CardIconOrder panelOrder) {
        this.customCardIconOrder = panelOrder;
    }

    public void setCustomCardIconsPanelColor(CardIconColor cardIconColor) {
        this.customCardIconColor = cardIconColor;
    }

    public void setCustomCardIconsMaxVisibleCount(int maxVisibleCount) {
        this.customCardIconsMaxVisibleCount = maxVisibleCount;
    }

    @Override
    public CardEventSource getCardEventSource() {
        return this.cardEventSource;
    }
}
