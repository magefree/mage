package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.DisguiseAbility;
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
public final class ForumFamiliar extends CardImpl {

    public ForumFamiliar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.CAT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Disguise {1}{W}
        this.addAbility(new DisguiseAbility(this, new ManaCostsImpl<>("{1}{W}")));

        // When Forum Familiar is turned face up, return another target permanent you control to its owner's hand and put a +1/+1 counter on Forum Familiar.
        Ability ability = new TurnedFaceUpSourceTriggeredAbility(new ReturnToHandTargetEffect());
        ability.addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance()).concatBy("and"));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CONTROLLED_ANOTHER_TARGET_PERMANENT));
        this.addAbility(ability);
    }

    private ForumFamiliar(final ForumFamiliar card) {
        super(card);
    }

    @Override
    public ForumFamiliar copy() {
        return new ForumFamiliar(this);
    }
}
