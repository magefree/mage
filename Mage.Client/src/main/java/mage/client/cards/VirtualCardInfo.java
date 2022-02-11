package mage.client.cards;

import mage.abilities.icon.CardIconRenderSettings;
import mage.cards.MageCard;
import mage.cards.action.TransferData;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.client.dialog.PreferencesDialog;
import mage.client.plugins.adapters.MageActionCallback;
import mage.client.plugins.impl.Plugins;
import mage.client.util.ClientDefaultSettings;
import mage.view.CardView;

import java.awt.*;
import java.util.UUID;

/**
 * GUI: virtual card component for popup hint (move mouse over card to show a hint)
 * <p>
 * Use case: you don't have a real card but want to show a popup card hint.
 * <p>
 * Howto use:
 * - call "init" on new card;
 * - call "onMouseEntered" to prepare;
 * - call "onMouseMoved" to draw;
 * - call "onMouseExited" to hide;
 * <p>
 * Hints:
 * - for game GUI: you must init with gameId (otherwise you can't see it)
 * - for non-game GUI: no needs in gameId or bigCard (bigCard is a panel with card image)
 * - if you want to show card immediately then use init + onMouseEntered + onMouseMoved
 *
 * @author JayDi85
 */
public class VirtualCardInfo {

    CardView cardView;
    MageCard cardComponent;
    BigCard bigCard;
    MageActionCallback actionCallback;
    TransferData data = new TransferData();
    Dimension cardDimension = null;

    public VirtualCardInfo() {
        super();
    }

    private void clear() {
        this.cardView = null;
        this.cardComponent = null;
    }

    public void init(String cardName, BigCard bigCard, UUID gameId) {
        CardInfo cardInfo = CardRepository.instance.findCards(cardName).stream().findFirst().orElse(null);
        if (cardInfo == null) {
            clear();
            return;
        }

        this.init(new CardView(cardInfo.getCard()), bigCard, gameId);
    }

    public void init(CardView cardView, BigCard bigCard, UUID gameId) {
        clear();

        this.bigCard = bigCard != null ? bigCard : new BigCard();
        this.cardDimension = new Dimension(ClientDefaultSettings.dimensions.getFrameWidth(), ClientDefaultSettings.dimensions.getFrameHeight());
        this.actionCallback = (MageActionCallback) Plugins.instance.getActionCallback();

        this.cardView = cardView;
        this.cardComponent = Plugins.instance.getMageCard(
                this.cardView,
                this.bigCard,
                new CardIconRenderSettings(),
                this.cardDimension,
                null,
                true,
                true,
                PreferencesDialog.getRenderMode(),
                true
        );
        this.cardComponent.update(cardView);

        data.setComponent(this.cardComponent);
        data.setCard(this.cardView);
        data.setGameId(gameId);
    }

    public void setTooltipDelay(int tooltipDelay) {
        data.setTooltipDelay(tooltipDelay);
    }

    public boolean prepared() {
        return this.cardView != null
                && this.cardComponent != null
                && this.actionCallback != null;
    }

    private void updateLocation(Point point) {
        Point newPoint = new Point(point);
        if (this.cardComponent != null) {
            // offset popup
            newPoint.translate(50, 50);
        }
        data.setLocationOnScreen(newPoint);
    }

    public void onMouseEntered() {
        onMouseMoved(null);
    }

    public void onMouseEntered(Point newLocation) {
        if (!prepared()) {
            return;
        }

        if (newLocation != null) {
            updateLocation(newLocation);
        }

        this.actionCallback.mouseEntered(null, this.data);
    }

    public void onMouseMoved() {
        onMouseMoved(null);
    }

    public void onMouseMoved(Point newLocation) {
        if (!prepared()) {
            return;
        }

        if (newLocation != null) {
            updateLocation(newLocation);
        }

        this.actionCallback.mouseMoved(null, this.data);
    }

    public void onMouseExited() {
        if (!prepared()) {
            return;
        }
        this.actionCallback.mouseExited(null, this.data);
    }
}
