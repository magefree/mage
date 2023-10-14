
package mage.cards.s;

import java.util.UUID;
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
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class SerumPowder extends CardImpl {

    public SerumPowder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
        
        // Any time you could mulligan and Serum Powder is in your hand, you may exile all the cards from your hand, then draw that many cards.
        this.addAbility(new SimpleStaticAbility(Zone.HAND, new SerumPowderReplaceEffect()));
    }

    private SerumPowder(final SerumPowder card) {
        super(card);
    }

    @Override
    public SerumPowder copy() {
        return new SerumPowder(this);
    }
}

class SerumPowderReplaceEffect extends ReplacementEffectImpl {
    SerumPowderReplaceEffect() {
        super(Duration.EndOfGame, Outcome.Detriment);
        staticText = "Any time you could mulligan and {this} is in your hand, you may exile all the cards from your hand, then draw that many cards";
    }

    private SerumPowderReplaceEffect(final SerumPowderReplaceEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        Card sourceCard = game.getCard(source.getSourceId());
        if (controller != null && sourceCard != null) {
            if (!controller.chooseUse(outcome, "Exile all cards from hand and draw that many cards?", source, game)) {
                return false;
            }
            int cardsHand = controller.getHand().size();
            if (cardsHand > 0){
                Cards cards = new CardsImpl(controller.getHand());
                for (Card card: cards.getCards(game)) {
                    card.moveToExile(null, null, source, game);
                }
                controller.drawCards(cardsHand, source, game); // original event is not a draw event, so skip it in params
            }
            game.informPlayers(sourceCard.getLogName() +": " + controller.getLogName() + " exiles hand and draws " + cardsHand + " card(s)");
            return true;
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAN_TAKE_MULLIGAN;
    }
    
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return source.isControlledBy(event.getPlayerId());
    }

    @Override
    public SerumPowderReplaceEffect copy() {
        return new SerumPowderReplaceEffect(this);
    }
}
