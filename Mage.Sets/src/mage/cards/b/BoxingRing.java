package mage.cards.b;

import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TreasureToken;
import mage.target.TargetPermanent;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BoxingRing extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("creature you don't control with the same mana value");

    static {
        filter.add(BoxingRingPredicate.instance);
    }

    public BoxingRing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{G}");

        // Whenever a creature enters the battlefield under your control, it fights up to one target creature you don't control with the same mana value.
        Ability ability = new EntersBattlefieldControlledTriggeredAbility(new BoxingRingFightEffect(), filter);
        ability.addTarget(new TargetPermanent(0, 1, filter));
        this.addAbility(ability);

        // {T}: Create a Treasure token. Activate only if you control a creature that fought this turn.
        this.addAbility(new ConditionalActivatedAbility(
                Zone.BATTLEFIELD, new CreateTokenEffect(new TreasureToken()),
                new TapSourceCost(), BoxingRingCondition.instance
        ), new BoxingRingWatcher());
    }

    private BoxingRing(final BoxingRing card) {
        super(card);
    }

    @Override
    public BoxingRing copy() {
        return new BoxingRing(this);
    }
}

enum BoxingRingPredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        return input
                .getObject()
                .getManaValue()
                == input
                .getSource()
                .getEffects()
                .stream()
                .map(effect -> effect.getValue("permanentEnteringBattlefield"))
                .map(Permanent.class::cast)
                .filter(Objects::nonNull)
                .map(MageObject::getManaValue)
                .findFirst()
                .orElse(-1);
    }
}

class BoxingRingFightEffect extends OneShotEffect {

    BoxingRingFightEffect() {
        super(Outcome.Benefit);
        staticText = "it fights up to one target creature you don't control with the same mana value";
    }

    private BoxingRingFightEffect(final BoxingRingFightEffect effect) {
        super(effect);
    }

    @Override
    public BoxingRingFightEffect copy() {
        return new BoxingRingFightEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = (Permanent) getValue("permanentEnteringBattlefield");
        Permanent creature = game.getPermanent(getTargetPointer().getFirst(game, source));
        return permanent != null && creature != null && permanent.fight(creature, source, game);
    }
}

enum BoxingRingCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return BoxingRingWatcher.checkPlayer(source.getControllerId(), game);
    }

    @Override
    public String toString() {
        return "you control a creature that fought this turn";
    }
}

class BoxingRingWatcher extends Watcher {

    private final Set<MageObjectReference> morSet = new HashSet<>();

    BoxingRingWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.FIGHTED_PERMANENT) {
            morSet.add(new MageObjectReference(game.getPermanent(event.getTargetId()), game));
        }
    }

    @Override
    public void reset() {
        super.reset();
        morSet.clear();
    }

    static boolean checkPlayer(UUID playerId, Game game) {
        return game
                .getState()
                .getWatcher(BoxingRingWatcher.class)
                .morSet
                .stream()
                .filter(mor -> mor.zoneCounterIsCurrent(game))
                .map(MageObjectReference::getSourceId)
                .map(game::getControllerId)
                .anyMatch(playerId::equals);
    }
}
