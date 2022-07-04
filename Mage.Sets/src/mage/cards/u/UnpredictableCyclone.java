package mage.cards.u;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.StackObject;
import mage.players.Player;

import java.util.UUID;
import mage.ApprovingObject;

/**
 * @author TheElk801
 */
public final class UnpredictableCyclone extends CardImpl {

    public UnpredictableCyclone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}{R}");

        // If a cycling ability of another nonland card would cause you to draw a card, instead exile cards from the top of your library until you exile a card that shares a card type with the cycled card. You may cast that card without paying its mana cost. Then put the exiled cards that weren't cast this way on the bottom of your library in a random order.
        this.addAbility(new SimpleStaticAbility(new UnpredictableCycloneReplacementEffect()));

        // Cycling {2}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private UnpredictableCyclone(final UnpredictableCyclone card) {
        super(card);
    }

    @Override
    public UnpredictableCyclone copy() {
        return new UnpredictableCyclone(this);
    }
}

class UnpredictableCycloneReplacementEffect extends ReplacementEffectImpl {

    UnpredictableCycloneReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If a cycling ability of another nonland card would cause you to draw a card, " +
                "instead exile cards from the top of your library until you exile a card " +
                "that shares a card type with the cycled card. You may cast that card without paying its mana cost. " +
                "Then put the exiled cards that weren't cast this way on the bottom of your library in a random order.";
    }

    private UnpredictableCycloneReplacementEffect(final UnpredictableCycloneReplacementEffect effect) {
        super(effect);
    }

    @Override
    public UnpredictableCycloneReplacementEffect copy() {
        return new UnpredictableCycloneReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(event.getPlayerId());
        StackObject stackObject = game.getStack().getStackObject(event.getSourceId());
        if (player == null || stackObject == null) {
            return false;
        }
        Card sourceCard = game.getCard(stackObject.getSourceId());
        if (sourceCard == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        Card toCast = null;
        for (Card card : player.getLibrary().getCards(game)) {
            cards.add(card);
            if (card.getCardType(game).stream().anyMatch(sourceCard.getCardType(game)::contains)) {
                toCast = card;
                break;
            }
        }
        player.moveCards(cards, Zone.EXILED, source, game);
        if (toCast != null && player.chooseUse(outcome, "Cast the exiled card?", source, game)) {
            game.getState().setValue("PlayFromNotOwnHandZone" + toCast.getId(), Boolean.TRUE);
            Boolean cardWasCast = player.cast(player.chooseAbilityForCast(toCast, game, true),
                    game, true, new ApprovingObject(source, game));
            game.getState().setValue("PlayFromNotOwnHandZone" + toCast.getId(), null);
            if (cardWasCast) {
                cards.remove(toCast);
            }
        }
        player.putCardsOnBottomOfLibrary(cards, game, source, false);
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DRAW_CARD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!event.getPlayerId().equals(source.getControllerId())) {
            return false;
        }
        Player player = game.getPlayer(event.getPlayerId());
        StackObject stackObject = game.getStack().getStackObject(event.getSourceId()); // event.getSourceId() will be null for default draws (non effects)
        if (player == null || stackObject == null
                || stackObject.getStackAbility() == null
                || !(stackObject.getStackAbility() instanceof CyclingAbility)) {
            return false;
        }
        Card sourceCard = game.getCard(stackObject.getSourceId());
        return sourceCard != null && !sourceCard.isLand(game);
    }
}
