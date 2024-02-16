package mage.abilities.effects.keyword;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.Effects;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.ToughnessPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 * @author LevelX2
 */
public class BolsterEffect extends OneShotEffect {

    private final DynamicValue amount;

    private Effects additionalEffects = new Effects();

    public BolsterEffect(int amount) {
        this(StaticValue.get(amount));
    }

    public BolsterEffect(DynamicValue amount) {
        super(Outcome.BoostCreature);
        this.amount = amount;
        this.staticText = setText();
    }

    protected BolsterEffect(final BolsterEffect effect) {
        super(effect);
        this.amount = effect.amount;
        this.additionalEffects = effect.additionalEffects.copy();
    }

    @Override
    public BolsterEffect copy() {
        return new BolsterEffect(this);
    }

    // Text must be set manually
    public BolsterEffect withAdditionalEffect(Effect effect) {
        additionalEffects.add(effect);
        return this;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        if (amount.calculate(game, source, this) <= 0) {
            return true;
        }
        int leastToughness = Integer.MAX_VALUE;
        Permanent selectedCreature = null;
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, controller.getId(), game)) {
            if (leastToughness > permanent.getToughness().getValue()) {
                leastToughness = permanent.getToughness().getValue();
                selectedCreature = permanent; // automatically select if only one
            } else if (leastToughness == permanent.getToughness().getValue()) {
                leastToughness = permanent.getToughness().getValue();
                selectedCreature = null; // more than one so need to manually select
            }
        }
        if (leastToughness == Integer.MAX_VALUE) {
            return false; // no creature found
        }
        if (selectedCreature == null) {
            FilterPermanent filter = new FilterControlledCreaturePermanent("creature you control with toughness " + leastToughness);
            filter.add(new ToughnessPredicate(ComparisonType.EQUAL_TO, leastToughness));
            Target target = new TargetPermanent(1, 1, filter, true);
            if (controller.chooseTarget(outcome, target, source, game)) {
                selectedCreature = game.getPermanent(target.getFirstTarget());
            }
        }
        if (selectedCreature == null) {
            return false;
        }
        Effect effect = new AddCountersTargetEffect(CounterType.P1P1.createInstance(amount.calculate(game, source, this)));
        FixedTarget fixedTarget = new FixedTarget(selectedCreature, game);
        effect.setTargetPointer(fixedTarget);
        effect.apply(game, source);
        if (!additionalEffects.isEmpty()) {
            for (Effect additionalEffect : additionalEffects) {
                additionalEffect.setTargetPointer(fixedTarget);
                if (additionalEffect instanceof OneShotEffect) {
                    additionalEffect.apply(game, source);
                } else {
                    game.addEffect((ContinuousEffect) additionalEffect, source);
                }
            }
        }
        return true;
    }

    private String setText() {
        if (amount instanceof StaticValue) {
            int number = ((StaticValue) amount).getValue();
            return "bolster " + number
                    +". <i>(Choose a creature with the least toughness among creatures you control and put "
                    + CardUtil.numberToText(number, "a") + " +1/+1 counter" + (number == 1 ? "" : "s") + " on it.)</i>";
        } else {
            return "bolster X, where X is the number of " + amount.getMessage()
                    + ". <i>(Choose a creature with the least toughness among creatures you control and put X +1/+1 counters on it.)</i>";
        }
    }
}
