package mage.client.cards;

import mage.cards.MageCard;
import mage.client.dialog.PreferencesDialog;
import mage.client.plugins.impl.Plugins;
import mage.client.util.ClientEventType;
import mage.client.util.Event;
import mage.client.util.GUISizeHelper;
import mage.client.util.Listener;
import mage.view.AbilityView;
import mage.view.CardView;
import mage.view.CardsView;
import mage.view.SimpleCardView;
import org.mage.card.arcane.CardPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.UUID;

public class CardArea extends JPanel implements MouseListener {

    protected final CardEventSource cardEventSource = new CardEventSource();

    private boolean reloaded = false;
    private final javax.swing.JLayeredPane cardArea;
    private final javax.swing.JScrollPane scrollPane;
    private int yCardCaptionOffsetPercent = 0; // card caption offset (use for moving card caption view center, below mana icons -- for more good UI)
    private Dimension cardDimension;
    private int verticalCardOffset;

    private int customRenderMode = -1; // custom render mode tests
    private Dimension customCardSize = null; // custom size for tests

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
    }

    public void cleanUp() {
        for (Component comp : cardArea.getComponents()) {
            if (comp instanceof CardPanel) {
                ((CardPanel) comp).cleanUp();
                cardArea.remove(comp);
            }
        }
    }

    public void changeGUISize() {
        setGUISize();
        for (Component component : cardArea.getComponents()) {
            if (component instanceof CardPanel) {
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
        cardArea.revalidate();

        this.revalidate();
        this.repaint();

        fixDialogSize();
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
        for (CardView card : showCards.values()) {
            addCard(card, bigCard, gameId, rectangle);
            rectangle.translate(cardDimension.width, 0);
        }
        cardArea.setPreferredSize(new Dimension(cardDimension.width * showCards.size(), cardDimension.height + verticalCardOffset));
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
        MageCard cardPanel = Plugins.instance.getMageCard(card, bigCard, cardDimension, gameId, true, true,
                customRenderMode != -1 ? customRenderMode : PreferencesDialog.getRenderMode());

        cardPanel.setBounds(rectangle);
        cardPanel.addMouseListener(this);
        cardArea.add(cardPanel);
        cardArea.moveToFront(cardPanel);
        cardPanel.update(card);
        cardPanel.setCardBounds(rectangle.x, rectangle.y, cardDimension.width, cardDimension.height);

        // new card have same settings as current view
        cardPanel.setCardCaptionTopOffset(yCardCaptionOffsetPercent);

        cardPanel.showCardTitle();
    }

    private void loadCardsMany(CardsView showCards, BigCard bigCard, UUID gameId) {
        int rowsOfCards = 20;
        int columns = 1;
        if (showCards != null && !showCards.isEmpty()) {
            Rectangle rectangle = new Rectangle(cardDimension.width, cardDimension.height);
            int count = 0;
            for (CardView card : showCards.values()) {
                addCard(card, bigCard, gameId, rectangle);
                if (count >= rowsOfCards) {
                    rectangle.translate(cardDimension.width, -(rowsOfCards * verticalCardOffset));
                    columns++;
                    count = 0;
                } else {
                    rectangle.translate(0, verticalCardOffset);
                    count++;
                }
            }
        }
        cardArea.setPreferredSize(new Dimension(cardDimension.width * columns, cardDimension.height + (rowsOfCards * verticalCardOffset)));
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

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getClickCount() >= 1 && !e.isConsumed()) {
            Object obj = e.getSource();
            if ((e.getClickCount() & 1) == 0 && (e.getClickCount() > 0)) { // double clicks and repeated double clicks
                e.consume();
                if (obj instanceof Card) {
                    if (e.isAltDown()) {
                        cardEventSource.fireEvent(((Card) obj).getOriginal(), ClientEventType.ALT_DOUBLE_CLICK);
                    } else {
                        cardEventSource.fireEvent(((Card) obj).getOriginal(), ClientEventType.DOUBLE_CLICK);
                    }
                } else if (obj instanceof MageCard) {
                    if (e.isAltDown()) {
                        cardEventSource.fireEvent(((MageCard) obj).getOriginal(), ClientEventType.ALT_DOUBLE_CLICK);
                    } else {
                        cardEventSource.fireEvent(((MageCard) obj).getOriginal(), ClientEventType.DOUBLE_CLICK);
                    }
                }
            }
            if (obj instanceof MageCard) {
                checkMenu(e, ((MageCard) obj).getOriginal());
            } else {
                checkMenu(e, null);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (!e.isConsumed()) {
            Object obj = e.getSource();
            if (obj instanceof MageCard) {
                checkMenu(e, ((MageCard) obj).getOriginal());
            } else {
                checkMenu(e, null);
            }
        } else {
            cardEventSource.fireEvent(ClientEventType.ACTION_CONSUMED);
        }
    }

    private void checkMenu(MouseEvent Me, SimpleCardView card) {
        if (Me.isPopupTrigger()) {
            Me.consume();
            cardEventSource.fireEvent(card, Me.getComponent(), Me.getX(), Me.getY(), ClientEventType.SHOW_POP_UP_MENU);
        }
    }

    public void setCustomRenderMode(int customRenderMode) {
        this.customRenderMode = customRenderMode;
    }

    public void setCustomCardSize(Dimension customCardSize) {
        this.customCardSize = customCardSize;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}
