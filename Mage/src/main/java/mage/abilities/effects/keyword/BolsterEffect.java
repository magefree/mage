package mage.abilities.effects.keyword;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
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

/**
 *
 * @author LevelX2
 */
public class BolsterEffect extends OneShotEffect {

    private final DynamicValue amount;

    public BolsterEffect(int amount) {
        this(StaticValue.get(amount));
    }

    public BolsterEffect(DynamicValue amount) {
        super(Outcome.BoostCreature);
        this.amount = amount;
        this.staticText = setText();
    }

    public BolsterEffect(final BolsterEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public BolsterEffect copy() {
        return new BolsterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (amount.calculate(game, source, this) <= 0) {
                return true;
            }
            int leastToughness = Integer.MAX_VALUE;
            Permanent selectedCreature = null;
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, controller.getId(), game)) {
                if (leastToughness > permanent.getToughness().getValue()) {
                    leastToughness = permanent.getToughness().getValue();
                    selectedCreature = permanent;
                } else if (leastToughness == permanent.getToughness().getValue()) {
                    leastToughness = permanent.getToughness().getValue();
                    selectedCreature = null;
                }
            }
            if (leastToughness != Integer.MAX_VALUE) {
                if (selectedCreature == null) {
                    FilterPermanent filter = new FilterControlledCreaturePermanent("creature you control with toughness " + leastToughness);
                    filter.add(new ToughnessPredicate(ComparisonType.EQUAL_TO, leastToughness));
                    Target target = new TargetPermanent(1, 1, filter, true);
                    if (controller.chooseTarget(outcome, target, source, game)) {
                        selectedCreature = game.getPermanent(target.getFirstTarget());
                    }
                }
                if (selectedCreature != null) {
                    Effect effect = new AddCountersTargetEffect(CounterType.P1P1.createInstance(amount.calculate(game, source, this)));
                    effect.setTargetPointer(new FixedTarget(selectedCreature, game));
                    return effect.apply(game, source);
                }
            }
            return true;
        }
        return false;
    }

    private String setText() {
        StringBuilder sb = new StringBuilder("bolster ");
        if (amount instanceof StaticValue) {
            sb.append(amount);
            sb.append(". <i>(Choose a creature with the least toughness or tied with the least toughness among creatures you control. Put ");
            sb.append(amount).append(" +1/+1 counters on it.)</i>");
        } else {
            sb.append("X, where X is the number of ");
            sb.append(amount.getMessage());
            sb.append(". (Choose a creature with the least toughness among creatures you control and put X +1/+1 counters on it.)");
        }
        return sb.toString();
    }
}
