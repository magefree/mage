package mage.cards.s;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.ExileFromHandCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.ConjureCardEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.WatcherScope;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInHand;
import mage.watchers.Watcher;

import java.util.*;

/**
 * @author TheElk801
 */
public final class ShorelineScout extends CardImpl {

    private static final FilterCard filter = new FilterCard("a Merfolk card or a land card from your hand");

    static {
        filter.add(Predicates.or(
                SubType.MERFOLK.getPredicate(),
                CardType.LAND.getPredicate()
        ));
    }

    public ShorelineScout(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Shoreline Scout enters the battlefield, you may exile a Merfolk card or a land card from your hand. If you do, conjure a Tropical Island card into your hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new DoIfCostPaid(
                        new ConjureCardEffect("Tropical Island"),
                        new ExileFromHandCost(new TargetCardInHand(filter))
                )
        ));

        // As long as another Merfolk or an Island entered the battlefield under your control this turn, Shoreline Scout gets +1/+0.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(1, 0, Duration.WhileOnBattlefield),
                ShorelineScoutCondition.instance, "as long as another Merfolk " +
                "or an Island entered the battlefield under your control this turn, {this} gets +1/+0"
        )), new ShorelineScoutWatcher());
    }

    private ShorelineScout(final ShorelineScout card) {
        super(card);
    }

    @Override
    public ShorelineScout copy() {
        return new ShorelineScout(this);
    }
}

enum ShorelineScoutCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return ShorelineScoutWatcher.checkPlayer(source, game);
    }
}

class ShorelineScoutWatcher extends Watcher {

    private final Set<UUID> islandSet = new HashSet<>();
    private final Map<UUID, Set<MageObjectReference>> merfolkMap = new HashMap<>();
    private static final Set<MageObjectReference> emptySet = Collections.unmodifiableSet(new HashSet<>());

    ShorelineScoutWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.ENTERS_THE_BATTLEFIELD) {
            return;
        }
        Permanent permanent = ((EntersTheBattlefieldEvent) event).getTarget();
        if (permanent == null) {
            return;
        }
        if (permanent.hasSubtype(SubType.ISLAND, game)) {
            islandSet.add(permanent.getControllerId());
        } else if (permanent.hasSubtype(SubType.MERFOLK, game)) {
            merfolkMap
                    .computeIfAbsent(permanent.getControllerId(), x -> new HashSet<>())
                    .add(new MageObjectReference(permanent, game));
        }
    }

    @Override
    public void reset() {
        super.reset();
        islandSet.clear();
        merfolkMap.clear();
    }

    static boolean checkPlayer(Ability source, Game game) {
        ShorelineScoutWatcher watcher = game.getState().getWatcher(ShorelineScoutWatcher.class);
        return watcher
                .islandSet
                .contains(source.getControllerId())
                || watcher
                .merfolkMap
                .getOrDefault(source.getControllerId(), emptySet)
                .stream()
                .anyMatch(mor -> !mor.refersTo(source.getSourceObject(game), game));
    }
}
