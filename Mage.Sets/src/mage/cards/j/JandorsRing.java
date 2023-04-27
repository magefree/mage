
package mage.cards.j;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CardIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.watchers.Watcher;

/**
 * @author MarcoMarin
 */
public final class JandorsRing extends CardImpl {

    public JandorsRing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{6}");

        Watcher watcher = new JandorsRingWatcher();
        // {2}, {tap}, Discard the last card you drew this turn: Draw a card.
        // TODO: discard has to be a cost not a payment during resolution
        Ability ability = new ConditionalActivatedAbility(Zone.BATTLEFIELD, new JandorsRingEffect(), new ManaCostsImpl<>("{2}"), WatchedCardInHandCondition.instance, "{2}, {T}, Discard the last card you drew this turn: Draw a card.");
        ability.addCost(new TapSourceCost());
        this.addAbility(ability, watcher);
    }

    private JandorsRing(final JandorsRing card) {
        super(card);
    }

    @Override
    public JandorsRing copy() {
        return new JandorsRing(this);
    }
}

class JandorsRingEffect extends OneShotEffect {

    public JandorsRingEffect() {
        super(Outcome.Discard);
        staticText = "Draw a card";
    }

    public JandorsRingEffect(final JandorsRingEffect effect) {
        super(effect);
    }

    @Override
    public JandorsRingEffect copy() {
        return new JandorsRingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        JandorsRingWatcher watcher = game.getState().getWatcher(JandorsRingWatcher.class);
        if (watcher != null) {
            UUID cardId = watcher.getLastDrewCard(source.getControllerId());
            Card card = game.getCard(cardId);
            if (card != null) {
                FilterCard filter = new FilterCard(card.getName());
                filter.add(new CardIdPredicate(card.getId()));
                DiscardCardYouChooseTargetEffect effect = new DiscardCardYouChooseTargetEffect(filter, TargetController.YOU);
                if (effect.apply(game, source)) {//Conditional was already checked, card should be in hand, but if for some weird reason it fails, the card won't be drawn, although the cost will already be paid
                    Player controller = game.getPlayer(source.getControllerId());
                    if(controller != null) {
                        controller.drawCards(1, source, game);
                    }
                }
            }
            return true;
        }
        return false;
    }
}

class JandorsRingWatcher extends Watcher {

    private Map<UUID, UUID> lastDrawnCards = new HashMap<>();

    public JandorsRingWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DREW_CARD) {
            lastDrawnCards.putIfAbsent(event.getPlayerId(), event.getTargetId());
        }
    }

    @Override
    public void reset() {
        super.reset();
        lastDrawnCards.clear();
    }

    public UUID getLastDrewCard(UUID playerId) {
        return lastDrawnCards.get(playerId);
    }
}

enum WatchedCardInHandCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        JandorsRingWatcher watcher = game.getState().getWatcher(JandorsRingWatcher.class);

        return watcher != null
                && game.getPlayer(source.getControllerId()).getHand().contains(watcher.getLastDrewCard(source.getControllerId()));
    }

    @Override
    public String toString() {
        return "if last drawn card is still in hand";
    }

}
