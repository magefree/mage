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
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.filter.predicate.permanent.CrewedSourceThisTurnPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.util.functions.CopyApplier;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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

    private static final FilterPermanent filter = new FilterCreaturePermanent("nonlegendary creature that crewed it this turn");

    static {
        filter.add(CrewedSourceThisTurnPredicate.instance);
        filter.add(Predicates.not(SuperType.LEGENDARY.getPredicate()));
    }

    MindlinkMechTriggeredAbility() {
        super(Zone.BATTLEFIELD, new MindlinkMechEffect());
        this.addWatcher(new MindlinkMechWatcher());
        this.addTarget(new TargetPermanent(filter));
        this.setTriggerPhrase("Whenever {this} becomes crewed for the first time each turn");
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
        return event.getSourceId().equals(getSourceId()) && MindlinkMechWatcher.checkVehicle(this, game);
    }
}

class MindlinkMechWatcher extends Watcher {

    private final Map<MageObjectReference, Integer> crewCount = new HashMap<>();
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
        if (event.getType() == GameEvent.EventType.VEHICLE_CREWED) {
            vehicle = game.getPermanent(event.getTargetId());
            crewCount.compute(new MageObjectReference(vehicle, game), (m, i) -> i == null ? 1 : Integer.sum(i, 1));
        }
    }

    @Override
    public void reset() {
        super.reset();
        crewCount.clear();
    }

    public static boolean checkVehicle(Ability source, Game game) {
        return game
                .getState()
                .getWatcher(MindlinkMechWatcher.class)
                .crewCount
                .getOrDefault(new MageObjectReference(source), 0) < 2;
    }
}

class MindlinkMechEffect extends OneShotEffect {

    MindlinkMechEffect() {
        super(Outcome.Benefit);
        this.setText("until end of turn, {this} becomes a copy of target nonlegendary creature that crewed it this turn, " +
                "except it's 4/3, it's a Vehicle artifact in addition to its other types, and it has flying.");
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
        blueprint.getPower().setModifiedBaseValue(4);
        blueprint.getToughness().setModifiedBaseValue(3);
        blueprint.addCardType(game, CardType.ARTIFACT);
        blueprint.addSubType(game, SubType.VEHICLE);
        blueprint.getAbilities().add(FlyingAbility.getInstance());
        return true;
    }
}
