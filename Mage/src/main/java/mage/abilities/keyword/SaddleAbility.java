package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.common.CrewSaddleIncreasedPowerAbility;
import mage.abilities.common.CrewSaddleWithToughnessAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.SaddledCondition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.hint.HintUtils;
import mage.constants.Outcome;
import mage.constants.TimingRule;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.watchers.common.SaddledMountWatcher;

import java.awt.*;
import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801, Susucr
 */
public class SaddleAbility extends SimpleActivatedAbility {

    private final int value;
    private static final Hint hint = new ConditionHint(SaddledCondition.instance, "This permanent is saddled");

    public SaddleAbility(int value) {
        super(new SaddleEventEffect(), new SaddleCost(value));
        this.value = value;
        this.addHint(hint);
        this.setTiming(TimingRule.SORCERY);
        this.addWatcher(new SaddledMountWatcher());
    }

    private SaddleAbility(final SaddleAbility ability) {
        super(ability);
        this.value = ability.value;
    }

    public static boolean applySaddle(Permanent permanent, Game game) {
        if (permanent == null) {
            return false;
        }
        SaddleAbility saddleAbility = permanent.getAbilities().stream()
                .filter(a -> a instanceof SaddleAbility)
                .map(a -> (SaddleAbility) a)
                .findFirst()
                .orElse(null);
        if (saddleAbility != null) {
            SaddleEventEffect effect = new SaddleEventEffect();
            effect.apply(game, saddleAbility);
            return true;
        }
        return false;
    }

    @Override
    public SaddleAbility copy() {
        return new SaddleAbility(this);
    }

    @Override
    public String getRule() {
        return "Saddle " + value + " <i>(Tap any number of other creatures you control with total power " +
                value + " or more: This Mount becomes saddled until end of turn. Saddle only as a sorcery.)</i>";
    }
}

class SaddleEventEffect extends OneShotEffect {

    SaddleEventEffect() {
        super(Outcome.Benefit);
    }

    private SaddleEventEffect(final SaddleEventEffect effect) {
        super(effect);
    }

    @Override
    public SaddleEventEffect copy() {
        return new SaddleEventEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (source.getSourcePermanentIfItStillExists(game) != null) {
            game.fireEvent(GameEvent.getEvent(
                    GameEvent.EventType.MOUNT_SADDLED,
                    source.getSourceId(),
                    source, source.getControllerId()
            ));
        }
        return true;
    }
}

class SaddleCost extends CostImpl {

    private static final FilterControlledCreaturePermanent filter
            = new FilterControlledCreaturePermanent("another untapped creature you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
        filter.add(AnotherPredicate.instance);
    }

    private final int value;

    SaddleCost(int value) {
        this.value = value;
    }

    private SaddleCost(final SaddleCost cost) {
        super(cost);
        this.value = cost.value;
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Target target = new TargetPermanent(0, Integer.MAX_VALUE, filter, true) {
            @Override
            public String getMessage(Game game) {
                // shows selected power
                int selectedPower = this.targets.keySet().stream()
                        .map(game::getPermanent)
                        .filter(Objects::nonNull)
                        .mapToInt(p -> getSaddlePower(p, game))
                        .sum();
                String extraInfo = "(selected power " + selectedPower + " of " + value + ")";
                if (selectedPower >= value) {
                    extraInfo = HintUtils.prepareText(extraInfo, Color.GREEN);
                }
                return super.getMessage(game) + " " + extraInfo;
            }
        };

        // can cancel
        if (target.choose(Outcome.Tap, controllerId, source.getSourceId(), source, game)) {
            int sumPower = 0;
            for (UUID targetId : target.getTargets()) {
                GameEvent event = new GameEvent(GameEvent.EventType.SADDLE_MOUNT, targetId, source, controllerId);
                if (!game.replaceEvent(event)) {
                    Permanent permanent = game.getPermanent(targetId);
                    if (permanent != null && permanent.tap(source, game)) {
                        sumPower += getSaddlePower(permanent, game);
                    }
                }
            }
            paid = sumPower >= value;
            if (paid) {
                for (UUID targetId : target.getTargets()) {
                    game.fireEvent(GameEvent.getEvent(GameEvent.EventType.SADDLED_MOUNT, targetId, source, controllerId));
                }
            }
        } else {
            return false;
        }

        return paid;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        int sumPower = 0;
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, controllerId, game)) {
            sumPower += Math.max(getSaddlePower(permanent, game), 0);
            if (sumPower >= value) {
                return true;
            }
        }
        return false;
    }

    @Override
    public SaddleCost copy() {
        return new SaddleCost(this);
    }

    private static int getSaddlePower(Permanent permanent, Game game) {
        if (permanent.hasAbility(CrewSaddleWithToughnessAbility.getInstance(), game)) {
            return permanent.getToughness().getValue();
        } else if (permanent.getAbilities(game).containsClass(CrewSaddleIncreasedPowerAbility.class)) {
            return permanent.getPower().getValue() + 2;
        } else {
            return permanent.getPower().getValue();
        }
    }
}
