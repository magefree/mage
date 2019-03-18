package mage.cards.q;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BecomesBasicLandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.DependencyType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetLandPermanent;
import mage.target.targetadjustment.TargetAdjuster;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public final class QuicksilverFountain extends CardImpl {

    public QuicksilverFountain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // At the beginning of each player's upkeep, that player puts a flood counter on target non-Island land he or she controls of their choice. That land is an Island for as long as it has a flood counter on it.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new QuicksilverFountainEffect(), TargetController.ANY, false, true);
        ability.addTarget(new TargetLandPermanent());
        ability.setTargetAdjuster(QuicksilverFountainAdjuster.instance);
        this.addAbility(ability);

        // At the beginning of each end step, if all lands on the battlefield are Islands, remove all flood counters from them.
        Condition condition = new AllLandsAreSubtypeCondition(SubType.ISLAND);
        this.addAbility(new BeginningOfEndStepTriggeredAbility(Zone.BATTLEFIELD, new QuicksilverFountainEffect2(), TargetController.ANY, condition, false));
    }

    public QuicksilverFountain(final QuicksilverFountain card) {
        super(card);
    }

    @Override
    public QuicksilverFountain copy() {
        return new QuicksilverFountain(this);
    }
}

enum QuicksilverFountainAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        Player activePlayer = game.getPlayer(game.getActivePlayerId());
        if (activePlayer != null) {
            ability.getTargets().clear();
            FilterLandPermanent filter = new FilterLandPermanent();
            filter.add(Predicates.not(new SubtypePredicate(SubType.ISLAND)));
            filter.add(new ControllerPredicate(TargetController.ACTIVE));
            TargetLandPermanent target = new TargetLandPermanent(1, 1, filter, false);
            target.setTargetController(activePlayer.getId());
            ability.getTargets().add(target);
        }
    }
}

class QuicksilverFountainEffect extends OneShotEffect {

    public QuicksilverFountainEffect() {
        super(Outcome.Neutral);
        staticText = "that player puts a flood counter on target non-Island land they control of their choice. That land is an Island for as long as it has a flood counter on it";
    }

    public QuicksilverFountainEffect(final QuicksilverFountainEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(game.getActivePlayerId());
        if (player != null) {
            Permanent landChosen = game.getPermanent(source.getFirstTarget());
            landChosen.addCounters(CounterType.FLOOD.createInstance(), source, game);
            ContinuousEffect becomesBasicLandTargetEffect = new BecomesBasicLandTargetEffect(Duration.OneUse, SubType.ISLAND);
            becomesBasicLandTargetEffect.addDependencyType(DependencyType.BecomeIsland);
            ConditionalContinuousEffect effect = new ConditionalContinuousEffect(becomesBasicLandTargetEffect, new LandHasFloodCounterCondition(this), staticText);
            this.setTargetPointer(new FixedTarget(landChosen, game));
            effect.setTargetPointer(new FixedTarget(landChosen, game));
            game.addEffect(effect, source);
            return true;
        }
        return false;
    }

    @Override
    public QuicksilverFountainEffect copy() {
        return new QuicksilverFountainEffect(this);
    }
}

class QuicksilverFountainEffect2 extends OneShotEffect {

    public QuicksilverFountainEffect2() {
        super(Outcome.Neutral);
        staticText = "remove all flood counters from them";
    }

    public QuicksilverFountainEffect2(final QuicksilverFountainEffect2 effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent land : game.getBattlefield().getAllActivePermanents(CardType.LAND)) {
            land.removeCounters(CounterType.FLOOD.createInstance(land.getCounters(game).getCount(CounterType.FLOOD)), game);
        }
        return true;
    }

    @Override
    public QuicksilverFountainEffect2 copy() {
        return new QuicksilverFountainEffect2(this);
    }
}

class AllLandsAreSubtypeCondition implements Condition {

    private final SubType subtype;

    public AllLandsAreSubtypeCondition(SubType subtype) {
        this.subtype = subtype;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        FilterLandPermanent filterLand = new FilterLandPermanent();
        filterLand.add(new SubtypePredicate(subtype));
        int landCount = game.getBattlefield().getAllActivePermanents(CardType.LAND).size();
        return game.getBattlefield().getAllActivePermanents(filterLand, game).size() == landCount;
    }

    @Override
    public String toString() {
        return "if all lands on the battlefield are " + subtype + "s";
    }
}

class LandHasFloodCounterCondition implements Condition {

    private final Effect effect;

    public LandHasFloodCounterCondition(Effect effect) {
        this.effect = effect;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(effect.getTargetPointer().getFirst(game, source));
        return permanent != null
                && permanent.getCounters(game).getCount(CounterType.FLOOD) > 0;
    }
}
