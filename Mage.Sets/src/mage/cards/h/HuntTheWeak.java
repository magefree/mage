
package mage.cards.h;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class HuntTheWeak extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature you don't control");

    static {
        filter.add(new ControllerPredicate(TargetController.NOT_YOU));
    }

    public HuntTheWeak(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}");

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

    public HuntTheWeak(final HuntTheWeak card) {
        super(card);
    }

    @Override
    public HuntTheWeak copy() {
        return new HuntTheWeak(this);
    }

}
