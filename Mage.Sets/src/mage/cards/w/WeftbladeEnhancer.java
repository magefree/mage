package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.WarpAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WeftbladeEnhancer extends CardImpl {

    public WeftbladeEnhancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}");

        this.subtype.add(SubType.DRIX);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // When this creature enters, put a +1/+1 counter on each of up to two target creatures.
        Ability ability = new EntersBattlefieldTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        ability.addTarget(new TargetCreaturePermanent(0, 2));
        this.addAbility(ability);

        // Warp {2}{W}
        this.addAbility(new WarpAbility(this, "{2}{W}"));
    }

    private WeftbladeEnhancer(final WeftbladeEnhancer card) {
        super(card);
    }

    @Override
    public WeftbladeEnhancer copy() {
        return new WeftbladeEnhancer(this);
    }
}
