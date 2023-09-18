package mage.cards.j;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JuniperOrderRootweaver extends CardImpl {

    public JuniperOrderRootweaver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Kicker {G}
        this.addAbility(new KickerAbility("{G}"));

        // When Juniper Order Rootweaver enters the battlefield, if it was kicked, put a +1/+1 counter on target creature you control.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(
                        new AddCountersTargetEffect(CounterType.P1P1.createInstance())
                ), KickedCondition.ONCE, "When {this} enters the battlefield, " +
                "if it was kicked, put a +1/+1 counter on target creature you control.");
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private JuniperOrderRootweaver(final JuniperOrderRootweaver card) {
        super(card);
    }

    @Override
    public JuniperOrderRootweaver copy() {
        return new JuniperOrderRootweaver(this);
    }
}
