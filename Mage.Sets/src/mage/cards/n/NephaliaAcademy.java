
package mage.cards.n;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.PermanentCard;
import mage.game.stack.Spell;
import mage.players.Player;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class NephaliaAcademy extends CardImpl {

    public NephaliaAcademy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // If a spell or ability an opponent controls causes you to discard a card, you may reveal that card and put it on top of your library instead of putting it anywhere else.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new NephaliaAcademyEffect()));

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
    }

    private NephaliaAcademy(final NephaliaAcademy card) {
        super(card);
    }

    @Override
    public NephaliaAcademy copy() {
        return new NephaliaAcademy(this);
    }
}

class NephaliaAcademyEffect extends ReplacementEffectImpl {

    private UUID cardId;
    private int zoneChangeCounter;

    public NephaliaAcademyEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If a spell or ability an opponent controls causes you to discard a card, you may reveal that card and put it on top of your library instead of putting it anywhere else.";
    }

    private NephaliaAcademyEffect(final NephaliaAcademyEffect effect) {
        super(effect);
        this.cardId = effect.cardId;
        this.zoneChangeCounter = effect.zoneChangeCounter;
    }

    @Override
    public NephaliaAcademyEffect copy() {
        return new NephaliaAcademyEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DISCARD_CARD
                || event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.DISCARD_CARD) {
            return event.getPlayerId().equals(source.getControllerId());
        }
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE) {
            if (event.getTargetId().equals(cardId) && game.getState().getZoneChangeCounter(event.getTargetId()) == zoneChangeCounter) {
                if (((ZoneChangeEvent) event).getFromZone() == Zone.HAND && ((ZoneChangeEvent) event).getToZone() == Zone.GRAVEYARD) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.DISCARD_CARD) {
            // only save card info if it's an opponent effect
            Card card = game.getCard(event.getTargetId());
            if (card != null) {
                boolean isAnOpponentEffect = false;
                MageObject object = game.getObject(event.getSourceId());
                if (object instanceof PermanentCard) {
                    isAnOpponentEffect = game.getOpponents(source.getControllerId()).contains(((PermanentCard) object).getControllerId());
                }
                else if (object instanceof Spell) {
                    isAnOpponentEffect = game.getOpponents(source.getControllerId()).contains(((Spell) object).getControllerId());
                }
                else if (object instanceof Card) {
                    isAnOpponentEffect = game.getOpponents(source.getControllerId()).contains(((Card) object).getOwnerId());
                }

                if (isAnOpponentEffect) {
                    cardId = card.getId();
                    zoneChangeCounter = game.getState().getZoneChangeCounter(cardId);
                }
            }
            return false;
        }
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE) {
            Player controller = game.getPlayer(source.getControllerId());
            Card card = game.getCard(event.getTargetId());
            if (controller != null && card != null) {
                cardId = null;
                zoneChangeCounter = 0;
                if (controller.chooseUse(outcome, "Put " + card.getIdName() + " on top of your library instead?", source, game)) {

                    Cards cardsToLibrary = new CardsImpl(card);                 
                    // reveal the card then put it on top of your library
                    controller.revealCards(card.getName(), cardsToLibrary, game);
                    controller.putCardsOnTopOfLibrary(cardsToLibrary, game, source, false);
                    return true;
                }
            }
        }
        return false;
    }

}