package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.MaxSpeedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.StartYourEnginesAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PerilousSnare extends CardImpl {

    public PerilousSnare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{W}");

        // Start your engines!
        this.addAbility(new StartYourEnginesAbility());

        // When this artifact enters, exile target nonland permanent an opponent controls until this artifact leaves the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileUntilSourceLeavesEffect());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_NON_LAND));
        this.addAbility(ability);

        // Max speed -- {T}: Put a +1/+1 counter on target creature or Vehicle you control. Activate only as a sorcery.
        ability = new ActivateAsSorceryActivatedAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()), new TapSourceCost()
        );
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_CREATURE_OR_VEHICLE));
        this.addAbility(new MaxSpeedAbility(ability));
    }

    private PerilousSnare(final PerilousSnare card) {
        super(card);
    }

    @Override
    public PerilousSnare copy() {
        return new PerilousSnare(this);
    }
}
