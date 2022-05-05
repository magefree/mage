package mage.cards.j;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.ChannelAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JukaiPreserver extends CardImpl {

    public JukaiPreserver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Jukai Preserver enters the battlefield, put a +1/+1 counter on target creature you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance())
        );
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);

        // Channel — {2}{G}, Discard Jukai Preserver: Put a +1/+1 counter on each of up to two target creatures you control.
        ability = new ChannelAbility(
                "{2}{G}", new AddCountersTargetEffect(CounterType.P1P1.createInstance())
        );
        ability.addTarget(new TargetPermanent(0, 2, StaticFilters.FILTER_CONTROLLED_CREATURES));
        this.addAbility(ability);
    }

    private JukaiPreserver(final JukaiPreserver card) {
        super(card);
    }

    @Override
    public JukaiPreserver copy() {
        return new JukaiPreserver(this);
    }
}
