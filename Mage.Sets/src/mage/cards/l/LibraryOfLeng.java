package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class LibraryOfLeng extends CardImpl {

    public LibraryOfLeng(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // You have no maximum hand size.
        Effect effect = new MaximumHandSizeControllerEffect(Integer.MAX_VALUE, Duration.WhileOnBattlefield, MaximumHandSizeControllerEffect.HandSizeModification.SET);
        addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));

        // If an effect causes you to discard a card, discard it, but you may put it on top of your library instead of into your graveyard.
        addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new LibraryOfLengEffect()));

    }

    public LibraryOfLeng(final LibraryOfLeng card) {
        super(card);
    }

    @Override
    public LibraryOfLeng copy() {
        return new LibraryOfLeng(this);
    }
}

class LibraryOfLengEffect extends ReplacementEffectImpl {

    private UUID cardId;
    private int zoneChangeCounter;

    public LibraryOfLengEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If an effect causes you to discard a card, discard it, but you may put it on top of your library instead of into your graveyard";
    }

    public LibraryOfLengEffect(final LibraryOfLengEffect effect) {
        super(effect);
        this.cardId = effect.cardId;
        this.zoneChangeCounter = effect.zoneChangeCounter;
    }

    @Override
    public LibraryOfLengEffect copy() {
        return new LibraryOfLengEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DISCARD_CARD
                || event.getType() == EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        // rules:
        // You can’t use the Library of Leng ability to place a discarded card on top of your library when you discard a card as a cost,
        // because costs aren’t effects. (2004-10-04)
        if (event.getType() == EventType.DISCARD_CARD && event.getFlag()) {
            return event.getPlayerId().equals(source.getControllerId());
        }
        if (event.getType() == EventType.ZONE_CHANGE) {
            if (event.getTargetId().equals(cardId) && game.getState().getZoneChangeCounter(event.getTargetId()) == zoneChangeCounter) {
                return ((ZoneChangeEvent) event).getFromZone() == Zone.HAND && ((ZoneChangeEvent) event).getToZone() == Zone.GRAVEYARD;
            }
        }
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        if (event.getType() == EventType.DISCARD_CARD) {
            // only save card info
            Card card = game.getCard(event.getTargetId());
            if (card != null) {
                cardId = card.getId();
                zoneChangeCounter = game.getState().getZoneChangeCounter(cardId);
            }
            return false;
        }
        if (event.getType() == EventType.ZONE_CHANGE) {
            Player controller = game.getPlayer(source.getControllerId());
            Card card = game.getCard(event.getTargetId());
            if (controller != null && card != null) {
                cardId = null;
                zoneChangeCounter = 0;
                if (controller.chooseUse(outcome, "Put " + card.getIdName() + " on top of your library instead?", source, game)) {
                    Cards cardsToLibrary = new CardsImpl(card);
                    controller.putCardsOnTopOfLibrary(cardsToLibrary, game, source, false);
                    return true;
                }
            }
        }
        return false;
    }

}
