
package mage.cards.h;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.condition.common.TargetHasCounterCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 * @author LevelX2
 */
public final class HadanasClimb extends CardImpl {

    public HadanasClimb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);

        this.secondSideCardClazz = mage.cards.w.WingedTempleOfOrazca.class;

        // At the beginning of combat on your turn, put a +1/+1 counter on target creature you control. Then if that creature has three or more +1/+1 counters on it, transform Hadana's Climb.
        this.addAbility(new TransformAbility());
        Ability ability = new BeginningOfCombatTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()), TargetController.YOU, false);
        ability.addEffect(new ConditionalOneShotEffect(new TransformSourceEffect(), new TargetHasCounterCondition(CounterType.P1P1, 3, Integer.MAX_VALUE),
                "Then if that creature has three or more +1/+1 counters on it, transform {this}"));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private HadanasClimb(final HadanasClimb card) {
        super(card);
    }

    @Override
    public HadanasClimb copy() {
        return new HadanasClimb(this);
    }
}
