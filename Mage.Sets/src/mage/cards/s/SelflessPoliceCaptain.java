package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.common.PutSourceCountersOnTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
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
public final class SelflessPoliceCaptain extends CardImpl {

    public SelflessPoliceCaptain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DETECTIVE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // This creature enters with a +1/+1 counter on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                "with a +1/+1 counter on it"
        ));

        // When this creature leaves the battlefield, put its +1/+1 counters on target creature you control.
        Ability ability = new DiesSourceTriggeredAbility(new PutSourceCountersOnTargetEffect(CounterType.P1P1));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private SelflessPoliceCaptain(final SelflessPoliceCaptain card) {
        super(card);
    }

    @Override
    public SelflessPoliceCaptain copy() {
        return new SelflessPoliceCaptain(this);
    }
}
