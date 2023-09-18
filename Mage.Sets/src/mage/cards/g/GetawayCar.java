package mage.cards.g;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.AttacksOrBlocksTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.WatcherScope;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.MageObjectReferencePredicate;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.TargetAdjuster;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class GetawayCar extends CardImpl {

    public GetawayCar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Getaway Car attacks or blocks, return up to one target creature that crewed it this turn to its owner's hand.
        this.addAbility(new AttacksOrBlocksTriggeredAbility(
                new ReturnToHandTargetEffect()
                        .setText("return up to one target creature that crewed it this turn to its owner's hand"),
                false
        ).setTargetAdjuster(GetawayCarAdjuster.instance), new GetawayCarWatcher());

        // Crew 1
        this.addAbility(new CrewAbility(1));
    }

    private GetawayCar(final GetawayCar card) {
        super(card);
    }

    @Override
    public GetawayCar copy() {
        return new GetawayCar(this);
    }
}

enum GetawayCarAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        ability.addTarget(new TargetPermanent(
                0, 1, GetawayCarWatcher.makeFilter(ability, game)
        ));
    }
}

class GetawayCarWatcher extends Watcher {

    private final Map<MageObjectReference, Set<MageObjectReference>> crewMap = new HashMap<>();
    private static final FilterPermanent invalidFilter = new FilterPermanent();

    static {
        invalidFilter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, -2));
    }

    GetawayCarWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        Permanent vehicle;
        Permanent crewer;
        switch (event.getType()) {
            case VEHICLE_CREWED:
                vehicle = game.getPermanent(event.getTargetId());
                crewer = null;
                break;
            case CREWED_VEHICLE:
                vehicle = game.getPermanent(event.getSourceId());
                crewer = game.getPermanent(event.getTargetId());
                break;
            default:
                return;
        }
        if (vehicle == null) {
            return;
        }
        crewMap.computeIfAbsent(
                new MageObjectReference(vehicle, game), x -> new HashSet<>()
        ).add(new MageObjectReference(crewer, game));
    }

    @Override
    public void reset() {
        super.reset();
        crewMap.clear();
    }

    public static FilterPermanent makeFilter(Ability source, Game game) {
        Set<MageObjectReferencePredicate> predicates = game
                .getState()
                .getWatcher(GetawayCarWatcher.class)
                .crewMap
                .computeIfAbsent(new MageObjectReference(source), x -> new HashSet<>())
                .stream()
                .filter(mor -> {
                    Permanent permanent = mor.getPermanent(game);
                    return permanent != null && permanent.isCreature(game);
                }).map(MageObjectReferencePredicate::new)
                .collect(Collectors.toSet());
        if (predicates.isEmpty()) {
            return invalidFilter;
        }
        FilterPermanent filterPermanent = new FilterPermanent(
                "creature that crewed " + CardUtil.getSourceName(game, source) + " this turn"
        );
        filterPermanent.add(Predicates.or(predicates));
        return filterPermanent;
    }
}
