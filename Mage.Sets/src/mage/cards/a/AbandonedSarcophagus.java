package mage.cards.a;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneAllEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.watchers.Watcher;

/**
 * @author jeffwadsworth
 */
public final class AbandonedSarcophagus extends CardImpl {

    private static final FilterCard filter = new FilterCard("nonland cards with cycling");

    static {
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
        filter.add(new AbilityPredicate(CyclingAbility.class));
    }

    public AbandonedSarcophagus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // You may cast nonland cards with cycling from your graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new PlayFromNotOwnHandZoneAllEffect(filter,
                        Zone.GRAVEYARD, true, TargetController.YOU, Duration.WhileOnBattlefield)
                        .setText("You may cast nonland cards with cycling from your graveyard"))
        );

        // If a card with cycling would be put into your graveyard from anywhere and it wasn't cycled, exile it instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new AbandonedSarcophagusReplacementEffect()), new AbandonedSarcophagusWatcher());

    }

    private AbandonedSarcophagus(final AbandonedSarcophagus card) {
        super(card);
    }

    @Override
    public AbandonedSarcophagus copy() {
        return new AbandonedSarcophagus(this);
    }
}

class AbandonedSarcophagusReplacementEffect extends ReplacementEffectImpl {

    AbandonedSarcophagusReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Exile);
        staticText = "If a card with cycling would be put into your graveyard from anywhere and it wasn't cycled, exile it instead";
    }

    private AbandonedSarcophagusReplacementEffect(final AbandonedSarcophagusReplacementEffect effect) {
        super(effect);
    }

    @Override
    public AbandonedSarcophagusReplacementEffect copy() {
        return new AbandonedSarcophagusReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent != null) {
            return controller.moveCards(permanent, Zone.EXILED, source, game);
        }

        Card card = game.getCard(event.getTargetId());
        if (card != null) {
            return controller.moveCards(card, Zone.EXILED, source, game);
        }

        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!(((ZoneChangeEvent) event).getToZone() == Zone.GRAVEYARD)) {
            return false;
        }

        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        Card card = game.getCard(event.getTargetId());
        if (card == null) {
            return false;
        }
        if (!card.isOwnedBy(controller.getId())) {
            return false;
        }

        AbandonedSarcophagusWatcher watcher = game.getState().getWatcher(AbandonedSarcophagusWatcher.class);
        if (watcher == null) {
            return false;
        }

        boolean cardHasCycling = false;
        for (Ability ability : card.getAbilities(game)) {
            if (ability instanceof CyclingAbility) {
                cardHasCycling = true;
                break;
            }
        }

        Cards cards = watcher.getCardsCycledThisTurn(controller.getId());
        boolean cardWasCycledThisTurn = false;

        for (Card cardCycledThisTurn : cards.getCards(game)) {
            if (cardCycledThisTurn == card) {
                cardWasCycledThisTurn = true;
                watcher.getCardsCycledThisTurn(controller.getId()).remove(card); //remove reference to the card as it is no longer needed
            }
        }

        return !cardWasCycledThisTurn && cardHasCycling;
    }
}

class AbandonedSarcophagusWatcher extends Watcher {

    private final Map<UUID, Cards> cycledCardsThisTurn = new HashMap<>();

    AbandonedSarcophagusWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.CYCLE_CARD) {
            return;
        }

        Card card = game.getCard(event.getSourceId());
        if (card == null) {
            return;
        }

        Player controller = game.getPlayer(event.getPlayerId());
        if (controller == null) {
            return;
        }
        if (!card.isOwnedBy(controller.getId())) {
            return;
        }

        Cards c = getCardsCycledThisTurn(event.getPlayerId());
        c.add(card);
        cycledCardsThisTurn.put(event.getPlayerId(), c);
    }

    public Cards getCardsCycledThisTurn(UUID playerId) {
        return cycledCardsThisTurn.getOrDefault(playerId, new CardsImpl());
    }

    @Override
    public void reset() {
        super.reset();
        cycledCardsThisTurn.clear();
    }
}
