package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.EmergeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TwistedRiddlekeeper extends CardImpl {

    public TwistedRiddlekeeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{8}");

        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.SPHINX);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Emerge {5}{C}{U}
        this.addAbility(new EmergeAbility(this, "{5}{C}{U}"));

        // When you cast this spell, tap up to two target permanents. Put a stun counter on each of them.
        Ability ability = new CastSourceTriggeredAbility(new TapTargetEffect());
        ability.addEffect(new AddCountersTargetEffect(CounterType.STUN.createInstance()).setText("Put a stun counter on each of them"));
        ability.addTarget(new TargetPermanent(0, 2, StaticFilters.FILTER_PERMANENTS));
        this.addAbility(ability);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
    }

    private TwistedRiddlekeeper(final TwistedRiddlekeeper card) {
        super(card);
    }

    @Override
    public TwistedRiddlekeeper copy() {
        return new TwistedRiddlekeeper(this);
    }
}
