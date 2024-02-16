
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.counters.CounterType;

/**
 *
 * @author Backfir3
 */
public final class PouncingWurm extends CardImpl {

    public PouncingWurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.WURM);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Kicker {2}{G}
        this.addAbility(new KickerAbility("{2}{G}"));
        // If Pouncing Wurm was kicked, it enters the battlefield with three +1/+1 counters on it and with haste.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(3))),
                KickedCondition.ONCE,"If Pouncing Wurm was kicked, it enters the battlefield with three +1/+1 counters on it and with haste.");
		ability.addEffect(new GainAbilitySourceEffect(HasteAbility.getInstance(), Duration.WhileOnBattlefield));
		this.addAbility(ability);
    }

    private PouncingWurm(final PouncingWurm card) {
        super(card);
    }

    @Override
    public PouncingWurm copy() {
        return new PouncingWurm(this);
    }
}
