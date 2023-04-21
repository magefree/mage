package mage.cards.d;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInExile;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class DimensionalBreach extends CardImpl {

    public DimensionalBreach(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{W}{W}");

        // Exile all permanents. For as long as any of those cards remain exiled, at the beginning of each player's upkeep, that player returns one of the exiled cards they own to the battlefield.
        this.getSpellAbility().addEffect(new DimensionalBreachExileEffect());
    }

    private DimensionalBreach(final DimensionalBreach card) {
        super(card);
    }

    @Override
    public DimensionalBreach copy() {
        return new DimensionalBreach(this);
    }
}

class DimensionalBreachExileEffect extends OneShotEffect {

    DimensionalBreachExileEffect() {
        super(Outcome.Exile);
        staticText = "Exile all permanents. For as long as any of those cards remain exiled, " +
                "at the beginning of each player's upkeep, that player returns " +
                "one of the exiled cards they own to the battlefield.";
    }

    private DimensionalBreachExileEffect(final DimensionalBreachExileEffect effect) {
        super(effect);
    }

    @Override
    public DimensionalBreachExileEffect copy() {
        return new DimensionalBreachExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        game.getBattlefield().getActivePermanents(source.getControllerId(), game).stream().forEach(cards::add);
        player.moveCards(cards, Zone.EXILED, source, game);
        Set<MageObjectReference> morSet = cards
                .getCards(game)
                .stream()
                .filter(c -> game.getState().getZone(c.getId()) == Zone.EXILED)
                .map(c -> new MageObjectReference(c, game))
                .collect(Collectors.toSet());
        game.addDelayedTriggeredAbility(new DimensionalBreachDelayedTriggeredAbility(morSet), source);
        return true;
    }
}

class DimensionalBreachDelayedTriggeredAbility extends DelayedTriggeredAbility {

    private final Set<MageObjectReference> morSet = new HashSet<>();

    DimensionalBreachDelayedTriggeredAbility(Set<MageObjectReference> morSet) {
        super(new DimensionalBreachReturnEffect(morSet), Duration.Custom, false, false);
        this.morSet.addAll(morSet);
    }

    private DimensionalBreachDelayedTriggeredAbility(final DimensionalBreachDelayedTriggeredAbility ability) {
        super(ability);
        this.morSet.addAll(ability.morSet);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UPKEEP_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return morSet.stream().map(mor -> mor.getCard(game)).anyMatch(Objects::nonNull);
    }

    @Override
    public DimensionalBreachDelayedTriggeredAbility copy() {
        return new DimensionalBreachDelayedTriggeredAbility(this);
    }

    @Override
    public boolean isInactive(Game game) {
        return morSet.stream().map(mor -> mor.getCard(game)).noneMatch(Objects::nonNull);
    }

    @Override
    public String getRule() {
        return "For as long as any of those cards remain exiled, at the beginning of each player's upkeep, " +
                "that player returns one of the exiled cards they own to the battlefield.";
    }
}

class DimensionalBreachReturnEffect extends OneShotEffect {

    private final Set<MageObjectReference> morSet = new HashSet<>();

    DimensionalBreachReturnEffect(Set<MageObjectReference> morSet) {
        super(Outcome.PutCardInPlay);
        this.morSet.addAll(morSet);
    }

    private DimensionalBreachReturnEffect(final DimensionalBreachReturnEffect effect) {
        super(effect);
        this.morSet.addAll(effect.morSet);
    }

    @Override
    public DimensionalBreachReturnEffect copy() {
        return new DimensionalBreachReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(game.getActivePlayerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(
                morSet.stream()
                        .map(mor -> mor.getCard(game))
                        .filter(Objects::nonNull)
                        .filter(c -> c.isOwnedBy(game.getActivePlayerId()))
                        .collect(Collectors.toSet())
        );
        if (cards.isEmpty()) {
            return false;
        }
        if (cards.size() > 1) {
            TargetCard target = new TargetCardInExile(StaticFilters.FILTER_CARD);
            target.setNotTarget(true);
            player.choose(outcome, cards, target, source, game);
            cards.clear();
            cards.add(target.getFirstTarget());
        }
        return player.moveCards(cards, Zone.BATTLEFIELD, source, game);
    }
}
