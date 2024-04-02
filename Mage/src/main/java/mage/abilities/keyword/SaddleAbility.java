package mage.abilities.keyword;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.SaddledCondition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.hint.HintUtils;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.watchers.common.SaddledMountWatcher;

import java.awt.*;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public class SaddleAbility extends SimpleActivatedAbility {

    private final int value;
    private static final Hint hint = new ConditionHint(SaddledCondition.instance, "This permanent is saddled");

    public SaddleAbility(int value) {
        super(new SaddleEffect(), new SaddleCost(value));
        this.value = value;
        this.addHint(hint);
        this.setTiming(TimingRule.SORCERY);
        this.addWatcher(new SaddledMountWatcher());
    }

    private SaddleAbility(final SaddleAbility ability) {
        super(ability);
        this.value = ability.value;
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

class SaddleEffect extends ContinuousEffectImpl {

    SaddleEffect() {
        super(Duration.EndOfTurn, Layer.RulesEffects, SubLayer.NA, Outcome.Benefit);
    }

    private SaddleEffect(final SaddleEffect effect) {
        super(effect);
    }

    @Override
    public SaddleEffect copy() {
        return new SaddleEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        game.fireEvent(GameEvent.getEvent(
                GameEvent.EventType.MOUNT_SADDLED,
                source.getSourceId(),
                source, source.getControllerId()
        ));
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Optional.ofNullable(source.getSourcePermanentIfItStillExists(game))
                .ifPresent(permanent -> permanent.setSaddled(true));
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
        Target target = new TargetControlledCreaturePermanent(0, Integer.MAX_VALUE, filter, true) {
            @Override
            public String getMessage() {
                // shows selected power
                int selectedPower = this.targets.keySet().stream()
                        .map(game::getPermanent)
                        .filter(Objects::nonNull)
                        .map(MageObject::getPower)
                        .mapToInt(MageInt::getValue)
                        .sum();
                String extraInfo = "(selected power " + selectedPower + " of " + value + ")";
                if (selectedPower >= value) {
                    extraInfo = HintUtils.prepareText(extraInfo, Color.GREEN);
                }
                return super.getMessage() + " " + extraInfo;
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
                        sumPower += permanent.getPower().getValue();
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
            sumPower += Math.max(permanent.getPower().getValue(), 0);
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
}
