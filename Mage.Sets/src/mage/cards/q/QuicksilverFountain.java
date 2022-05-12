package mage.cards.q;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BecomesBasicLandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetLandPermanent;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class QuicksilverFountain extends CardImpl {

    public QuicksilverFountain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // At the beginning of each player's upkeep, that player puts a flood 
        // counter on target non-Island land they control of their choice. 
        // That land is an Island for as long as it has a flood counter on it.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD,
                new QuicksilverFountainEffect(), TargetController.ANY, false, true);
        ability.addTarget(new TargetLandPermanent());
        ability.setTargetAdjuster(QuicksilverFountainAdjuster.instance);
        this.addAbility(ability);

        // At the beginning of each end step, if all lands on the battlefield are 
        // Islands, remove all flood counters from them.
        // Note: This applies only if Quicksilver Fountain is on the battlefield
        Condition condition = new AllLandsAreSubtypeCondition(SubType.ISLAND);
        this.addAbility(new BeginningOfEndStepTriggeredAbility(Zone.BATTLEFIELD,
                new QuicksilverFountainEffect2(), TargetController.ANY, condition, false));
    }

    private QuicksilverFountain(final QuicksilverFountain card) {
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
            filter.add(Predicates.not(SubType.ISLAND.getPredicate()));
            filter.add(TargetController.ACTIVE.getControllerPredicate());
            TargetLandPermanent target = new TargetLandPermanent(1, 1, filter, false);
            target.setTargetController(activePlayer.getId());
            ability.getTargets().add(target);
        }
    }
}

class QuicksilverFountainEffect extends OneShotEffect {

    public QuicksilverFountainEffect() {
        super(Outcome.Neutral);
        staticText = "that player puts a flood counter on target non-Island land "
                + "they control of their choice. That land is an Island for as "
                + "long as it has a flood counter on it";
    }

    public QuicksilverFountainEffect(final QuicksilverFountainEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(game.getActivePlayerId());
        if (player != null) {
            Permanent landChosen = game.getPermanent(source.getFirstTarget());
            landChosen.addCounters(CounterType.FLOOD.createInstance(), player.getId(), source, game);
            ContinuousEffect becomesBasicLandTargetEffect
                    = new BecomesBasicLandTargetEffect(Duration.Custom, SubType.ISLAND);
            ConditionalContinuousEffect effect
                    = new ConditionalContinuousEffect(becomesBasicLandTargetEffect,
                    new LandHasFloodCounterCondition(), staticText);
            // Bug #6885 Fixed when owner/controller leaves the game the effect still applies
            SimpleStaticAbility gainAbility = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
            gainAbility.setSourceId(landChosen.getId());
            gainAbility.getTargets().add(source.getTargets().get(0));
            game.addEffect(effect, gainAbility);
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
        for (Permanent land : game.getBattlefield().getAllActivePermanents(CardType.LAND, game)) {
            land.removeCounters(CounterType.FLOOD.createInstance(land.getCounters(game).getCount(CounterType.FLOOD)), source, game);
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
        filterLand.add(subtype.getPredicate());
        int landCount = game.getBattlefield().getAllActivePermanents(CardType.LAND, game).size();
        return game.getBattlefield().getAllActivePermanents(filterLand, game).size() == landCount;
    }

    @Override
    public String toString() {
        return "if all lands on the battlefield are " + subtype + "s";
    }
}

class LandHasFloodCounterCondition implements Condition {

    public LandHasFloodCounterCondition() {
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        return permanent != null
                && permanent.getCounters(game).getCount(CounterType.FLOOD) > 0;
    }
}
