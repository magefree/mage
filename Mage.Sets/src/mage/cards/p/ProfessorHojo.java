package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.common.BecomesTargetAnyTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterStackObject;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicate;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.StackObject;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ProfessorHojo extends CardImpl {

    private static final FilterStackObject filter = new FilterStackObject();

    static {
        filter.add(ProfessorHojoPredicate.instance);
    }

    public ProfessorHojo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCIENTIST);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // The first activated ability you activate during your turn that targets a creature you control costs {2} less to activate.
        this.addAbility(new SimpleStaticAbility(new ProfessorHojoEffect()), new ProfessorHojoWatcher());

        // Whenever one or more creatures you control become the target of an activated ability, draw a card. This ability triggers only once each turn.
        this.addAbility(new BecomesTargetAnyTriggeredAbility(
                new DrawCardSourceControllerEffect(1),
                StaticFilters.FILTER_CONTROLLED_CREATURE,
                filter, SetTargetPointer.NONE, false
        ).setTriggerPhrase("Whenever one or more creatures you control " +
                "become the target of an activated ability, ").setTriggersLimitEachTurn(1));
    }

    private ProfessorHojo(final ProfessorHojo card) {
        super(card);
    }

    @Override
    public ProfessorHojo copy() {
        return new ProfessorHojo(this);
    }
}

enum ProfessorHojoPredicate implements Predicate<StackObject> {
    instance;

    @Override
    public boolean apply(StackObject input, Game game) {
        return input instanceof Ability && ((Ability) input).isActivatedAbility();
    }
}

class ProfessorHojoEffect extends CostModificationEffectImpl {

    ProfessorHojoEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "the first activated ability you activate during your turn " +
                "that targets a creature you control costs {2} less to activate";
    }

    private ProfessorHojoEffect(final ProfessorHojoEffect effect) {
        super(effect);
    }

    @Override
    public ProfessorHojoEffect copy() {
        return new ProfessorHojoEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardUtil.reduceCost(abilityToModify, 2);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {

        Set<UUID>  targets;
        if (game.inCheckPlayableState()) {
            targets = CardUtil.getAllPossibleTargets(abilityToModify, game);
        } else {
            targets = CardUtil.getAllSelectedTargets(abilityToModify, game);
        }

        return game.isActivePlayer(source.getControllerId())
                && abilityToModify.isControlledBy(source.getControllerId())
                && abilityToModify.isActivatedAbility()
                && !ProfessorHojoWatcher.checkPlayer(game, source)
                && targets.stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .filter(permanent -> permanent.isCreature(game))
                .map(Controllable::getControllerId)
                .anyMatch(source::isControlledBy);
    }
}

class ProfessorHojoWatcher extends Watcher {

    private final Set<UUID> set = new HashSet<>();

    ProfessorHojoWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.ACTIVATED_ABILITY) {
            return;
        }
        StackObject stackObject = game.getStack().getStackObject(event.getSourceId());
        if (stackObject != null
                && stackObject.getStackAbility() instanceof ActivatedAbility
                && CardUtil
                .getAllSelectedTargets(stackObject.getStackAbility(), game)
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .filter(permanent -> permanent.isCreature(game))
                .map(Controllable::getControllerId)
                .anyMatch(stackObject::isControlledBy)) {
            set.add(stackObject.getControllerId());
        }
    }

    @Override
    public void reset() {
        super.reset();
        set.clear();
    }

    static boolean checkPlayer(Game game, Ability source) {
        return game
                .getState()
                .getWatcher(ProfessorHojoWatcher.class)
                .set
                .contains(source.getControllerId());
    }
}
