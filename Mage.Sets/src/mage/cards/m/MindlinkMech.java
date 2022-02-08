package mage.cards.m;

import mage.MageInt;
import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.MageObjectReferencePredicate;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.util.CardUtil;
import mage.util.functions.CopyApplier;
import mage.watchers.Watcher;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class MindlinkMech extends CardImpl {

    public MindlinkMech(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{U}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Mindlink Mech becomes crewed for the first time each turn, until end of turn, Mindlink Mech becomes a copy of target nonlegendary creature that crewed it this turn, except it's 4/3, it's a Vehicle artifact in addition to its other types, and it has flying.
        this.addAbility(new MindlinkMechTriggeredAbility());

        // Crew 1
        this.addAbility(new CrewAbility(1));
    }

    private MindlinkMech(final MindlinkMech card) {
        super(card);
    }

    @Override
    public MindlinkMech copy() {
        return new MindlinkMech(this);
    }
}

class MindlinkMechTriggeredAbility extends TriggeredAbilityImpl {

    MindlinkMechTriggeredAbility() {
        super(Zone.BATTLEFIELD, new MindlinkMechEffect());
        this.addWatcher(new MindlinkMechWatcher());
    }

    private MindlinkMechTriggeredAbility(final MindlinkMechTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MindlinkMechTriggeredAbility copy() {
        return new MindlinkMechTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.VEHICLE_CREWED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getSourceId().equals(getSourceId()) || !MindlinkMechWatcher.checkVehicle(this, game)) {
            return false;
        }
        this.getTargets().clear();
        this.addTarget(new TargetPermanent(MindlinkMechWatcher.makeFilter(this, game)));
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever {this} becomes crewed for the first time each turn, until end of turn, " +
                "{this} becomes a copy of target nonlegendary creature that crewed it this turn, " +
                "except it's 4/3, it's a Vehicle artifact in addition to its other types, and it has flying.";
    }
}

class MindlinkMechWatcher extends Watcher {

    private final Map<MageObjectReference, Integer> crewCount = new HashMap<>();
    private final Map<MageObjectReference, Set<MageObjectReference>> crewMap = new HashMap<>();
    private static final FilterPermanent invalidFilter = new FilterPermanent();

    static {
        invalidFilter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, -2));
    }

    MindlinkMechWatcher() {
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
        if (crewer == null) {
            crewCount.compute(new MageObjectReference(vehicle, game), (m, i) -> i == null ? 1 : Integer.sum(i, 1));
            return;
        }
        crewMap.computeIfAbsent(new MageObjectReference(vehicle, game), x -> new HashSet<>()).add(new MageObjectReference(crewer, game));
    }

    @Override
    public void reset() {
        super.reset();
        crewCount.clear();
        crewMap.clear();
    }

    public static boolean checkVehicle(Ability source, Game game) {
        return game
                .getState()
                .getWatcher(MindlinkMechWatcher.class)
                .crewCount
                .getOrDefault(new MageObjectReference(source), 0) < 2;
    }

    public static FilterPermanent makeFilter(Ability source, Game game) {
        Set<MageObjectReferencePredicate> predicates = game
                .getState()
                .getWatcher(MindlinkMechWatcher.class)
                .crewMap
                .computeIfAbsent(new MageObjectReference(source), x -> new HashSet<>())
                .stream()
                .filter(mor -> {
                    Permanent permanent = mor.getPermanent(game);
                    return permanent != null && !permanent.isLegendary() && permanent.isCreature(game);
                }).map(MageObjectReferencePredicate::new)
                .collect(Collectors.toSet());
        if (predicates.isEmpty()) {
            return invalidFilter;
        }
        FilterPermanent filterPermanent = new FilterPermanent(
                "nonlegendary creature that crewed " + CardUtil.getSourceName(game, source) + " this turn"
        );
        filterPermanent.add(Predicates.or(predicates));
        return filterPermanent;
    }
}

class MindlinkMechEffect extends OneShotEffect {

    MindlinkMechEffect() {
        super(Outcome.Benefit);
    }

    private MindlinkMechEffect(final MindlinkMechEffect effect) {
        super(effect);
    }

    @Override
    public MindlinkMechEffect copy() {
        return new MindlinkMechEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        Permanent creature = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null || creature == null) {
            return false;
        }
        game.copyPermanent(Duration.EndOfTurn, creature, permanent.getId(), source, new MindlinkMechApplier());
        return true;
    }
}

class MindlinkMechApplier extends CopyApplier {
    @Override
    public boolean apply(Game game, MageObject blueprint, Ability source, UUID targetObjectId) {
        blueprint.getPower().modifyBaseValue(4);
        blueprint.getToughness().modifyBaseValue(3);
        blueprint.addCardType(game, CardType.ARTIFACT);
        blueprint.addSubType(game, SubType.VEHICLE);
        blueprint.getAbilities().add(FlyingAbility.getInstance());
        return true;
    }
}