
package mage.cards.s;

import java.util.Iterator;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class SavageStomp extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature you don't control");

    static {
        filter.add(new ControllerPredicate(TargetController.NOT_YOU));
    }

    public SavageStomp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Savage Stomp costs {2} less to cast if it targets a Dinosaur you control.
        this.addAbility(new SimpleStaticAbility(Zone.STACK,
                new SpellCostReductionSourceEffect(2, SavageStompCondition.instance))
                .setRuleAtTheTop(true));

        // Put a +1/+1 counter on target creature you control. Then that creature fights target creature you don't control.
        Effect effect = new AddCountersTargetEffect(CounterType.P1P1.createInstance());
        this.getSpellAbility().addEffect(effect);
        effect = new FightTargetsEffect();
        effect.setText("Then that creature fights target creature you don't control");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        Target target = new TargetCreaturePermanent(filter);
        this.getSpellAbility().addTarget(target);
    }

    public SavageStomp(final SavageStomp card) {
        super(card);
    }

    @Override
    public SavageStomp copy() {
        return new SavageStomp(this);
    }
}

enum SavageStompCondition implements Condition {
    instance;
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Dinosaur you control");

    static {
        filter.add(new SubtypePredicate(SubType.DINOSAUR));
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    @Override
    public boolean apply(Game game, Ability source) {
        StackObject sourceSpell = game.getStack().getStackObject(source.getSourceId());
        if (sourceSpell != null) {
            Iterator<Target> targets = sourceSpell.getStackAbility().getTargets().iterator();
            while (targets.hasNext()) {
                Permanent permanent = game.getPermanentOrLKIBattlefield(targets.next().getFirstTarget());
                if (permanent != null && filter.match(permanent, game) && permanent.isControlledBy(source.getControllerId())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "it targets a Dinosaur you control";
    }

}
