package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.ControlsCreatureGreatestPowerCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.TargetPermanentPowerCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class ThickestInTheThicket extends CardImpl {

    public ThickestInTheThicket(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}{G}");

        // When Thickest in the Thicket enters, put X +1/+1 counters on target creature, where X is that creature's power.
        Ability ability = new EntersBattlefieldTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance(), TargetPermanentPowerCount.instance)
                .setText("put X +1/+1 counters on target creature, where X is that creature's power")
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // At the beginning of your end step, draw two cards if you control the creature with the greatest power or tied for the greatest power.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new ConditionalOneShotEffect(
                new DrawCardSourceControllerEffect(2), ControlsCreatureGreatestPowerCondition.instance
        ).withConditionTextAtEnd(true)));
    }

    private ThickestInTheThicket(final ThickestInTheThicket card) {
        super(card);
    }

    @Override
    public ThickestInTheThicket copy() {
        return new ThickestInTheThicket(this);
    }
}
