package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.IslandcyclingAbility;
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
public final class IceFlan extends CardImpl {

    public IceFlan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{U}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.OOZE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // When this creature enters, tap target artifact or creature an opponent controls. Put a stun counter on it.
        Ability ability = new EntersBattlefieldTriggeredAbility(new TapTargetEffect());
        ability.addEffect(new AddCountersTargetEffect(CounterType.STUN.createInstance()).setText("Put a stun counter on it"));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_ARTIFACT_OR_CREATURE));
        this.addAbility(ability);

        // Islandcycling {2}
        this.addAbility(new IslandcyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private IceFlan(final IceFlan card) {
        super(card);
    }

    @Override
    public IceFlan copy() {
        return new IceFlan(this);
    }
}
