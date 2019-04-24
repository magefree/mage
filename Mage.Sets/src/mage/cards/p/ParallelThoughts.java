
package mage.cards.p;

import java.util.Arrays;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.util.CardUtil;
import mage.util.RandomUtil;

/**
 *
 * @author jeffwadsworth
 */
public final class ParallelThoughts extends CardImpl {

    public ParallelThoughts(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}{U}");

        // When Parallel Thoughts enters the battlefield, search your library for seven cards, exile them in a face-down pile, and shuffle that pile. Then shuffle your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ParallelThoughtsSearchEffect()));

        // If you would draw a card, you may instead put the top card of the pile you exiled into your hand.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ParallelThoughtsReplacementEffect()));

    }

    public ParallelThoughts(final ParallelThoughts card) {
        super(card);
    }

    @Override
    public ParallelThoughts copy() {
        return new ParallelThoughts(this);
    }
}

class ParallelThoughtsSearchEffect extends OneShotEffect {

    ParallelThoughtsSearchEffect() {
        super(Outcome.Neutral);
        this.staticText = "search your library for seven cards, exile them in a face-down pile, and shuffle that pile. Then shuffle your library";
    }

    ParallelThoughtsSearchEffect(final ParallelThoughtsSearchEffect effect) {
        super(effect);
    }

    @Override
    public ParallelThoughtsSearchEffect copy() {
        return new ParallelThoughtsSearchEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        Cards cardsInExilePile = new CardsImpl();
        if (controller != null
                && permanent != null) {
            TargetCardInLibrary target = new TargetCardInLibrary(7, new FilterCard());
            if (controller.searchLibrary(target, game)) {
                for (UUID targetId : target.getTargets()) {
                    Card card = controller.getLibrary().getCard(targetId, game);
                    if (card != null) {
                        cardsInExilePile.add(card);
                    }
                }
                // shuffle that exiled pile
                
                UUID[] shuffled = cardsInExilePile.toArray(new UUID[0]);
                for (int n = shuffled.length - 1; n > 0; n--) {
                    int r = RandomUtil.nextInt(n + 1);
                    UUID temp = shuffled[n];
                    shuffled[n] = shuffled[r];
                    shuffled[r] = temp;
                }
                cardsInExilePile.clear();
                cardsInExilePile.addAll(Arrays.asList(shuffled));
                
                // move to exile zone and turn face down
                
                for (Card card : cardsInExilePile.getCards(game)) {
                    controller.moveCardsToExile(card, source, game, false, CardUtil.getCardExileZoneId(game, source), permanent.getLogName());
                    card.setFaceDown(true, game);
                }
                
                // shuffle controller library
                
                controller.shuffleLibrary(source, game);
            }
            return true;
        }
        return false;
    }
}

class ParallelThoughtsReplacementEffect extends ReplacementEffectImpl {

    ParallelThoughtsReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.DrawCard);
        staticText = "If you would draw a card, you may instead put the top card of the pile you exiled with Parallel Thoughts into your hand";
    }

    ParallelThoughtsReplacementEffect(final ParallelThoughtsReplacementEffect effect) {
        super(effect);
    }

    @Override
    public ParallelThoughtsReplacementEffect copy() {
        return new ParallelThoughtsReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null
                && !game.getExile().getExileZone(CardUtil.getCardExileZoneId(game, source)).getCards(game).isEmpty()) {
            Card card = game.getExile().getExileZone(CardUtil.getCardExileZoneId(game, source)).getCards(game).iterator().next();
            if (card != null) {
                controller.moveCards(card, Zone.HAND, source, game);
            }
        }
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DRAW_CARD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return source.isControlledBy(event.getPlayerId());
    }
}
