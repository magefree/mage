package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.condition.common.FerociousCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.hint.common.FerociousHint;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.game.permanent.token.RaccoonToken;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SylvanScavenging extends CardImpl {

    public SylvanScavenging(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}{G}");

        // At the beginning of your end step, choose one --
        // * Put a +1/+1 counter on target creature you control.
        Ability ability = new BeginningOfEndStepTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        ability.addTarget(new TargetControlledCreaturePermanent());

        // * Create a 3/3 green Raccoon creature token if you control a creature with power 4 or greater.
        ability.addMode(new Mode(new ConditionalOneShotEffect(
                new CreateTokenEffect(new RaccoonToken()), FerociousCondition.instance,
                "create a 3/3 green Raccoon creature token if you control a creature with power 4 or greater"
        )));
        this.addAbility(ability.addHint(FerociousHint.instance));
    }

    private SylvanScavenging(final SylvanScavenging card) {
        super(card);
    }

    @Override
    public SylvanScavenging copy() {
        return new SylvanScavenging(this);
    }
}
