
package mage.cards.a;

import java.util.UUID;
import mage.abilities.condition.common.TargetHasSuperTypeCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class AncientAnimus extends CardImpl {

    public AncientAnimus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Put a +1/+1 counter on target creature you control if it's legendary. Then it fights target creature an opponent controls.
        Effect effect = new ConditionalOneShotEffect(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()),
                new TargetHasSuperTypeCondition(SuperType.LEGENDARY)
        );
        effect.setText("Put a +1/+1 counter on target creature you control if it's legendary");
        this.getSpellAbility().addEffect(effect);
        effect = new FightTargetsEffect();
        effect.setText("Then it fights target creature an opponent controls. " +
                "<i>(Each deals damage equal to its power to the other.)</i>");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        Target target = new TargetOpponentsCreaturePermanent();
        this.getSpellAbility().addTarget(target);
    }

    private AncientAnimus(final AncientAnimus card) {
        super(card);
    }

    @Override
    public AncientAnimus copy() {
        return new AncientAnimus(this);
    }
}
