package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.Iterator;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TitanicBrawl extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("creature you don't control");

    static {
        filter.add(new ControllerPredicate(TargetController.NOT_YOU));
    }

    public TitanicBrawl(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // This spell costs {1} less to cast if it targets a creature you control with a +1/+1 counter on it.
        this.addAbility(new SimpleStaticAbility(Zone.STACK,
                new SpellCostReductionSourceEffect(1, TitanicBrawlCondition.instance))
                .setRuleAtTheTop(true));

        // Target creature you control fights target creature you don't control.
        this.getSpellAbility().addEffect(new FightTargetsEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
    }

    private TitanicBrawl(final TitanicBrawl card) {
        super(card);
    }

    @Override
    public TitanicBrawl copy() {
        return new TitanicBrawl(this);
    }
}

enum TitanicBrawlCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        StackObject sourceSpell = game.getStack().getStackObject(source.getSourceId());
        if (sourceSpell == null) {
            return false;
        }
        Iterator<Target> targets = sourceSpell.getStackAbility().getTargets().iterator();
        while (targets.hasNext()) {
            Permanent permanent = game.getPermanentOrLKIBattlefield(targets.next().getFirstTarget());
            if (permanent != null && permanent.getCounters(game).containsKey(CounterType.P1P1)
                    && permanent.isControlledBy(source.getControllerId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "it targets a creature you control with a +1/+1 counter on it";
    }

}
