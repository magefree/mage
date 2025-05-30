
package mage.cards.q;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.TargetsCountAdjuster;

import java.util.UUID;

/**
 * @author awjackson
 */
public final class QuarantineField extends CardImpl {

    public QuarantineField(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{X}{X}{W}{W}");

        // Quarantine Field enters the battlefield with X isolation counters on it.
        this.addAbility(new EntersBattlefieldAbility(new EntersBattlefieldWithXCountersEffect(CounterType.ISOLATION.createInstance())));

        // When Quarantine Field enters the battlefield, for each isolation counter on it, exile up to one target nonland permanent an opponent controls until Quarantine Field leaves the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileUntilSourceLeavesEffect()
                .setText("for each isolation counter on it, exile up to one target nonland permanent an opponent controls until {this} leaves the battlefield")
        );
        ability.addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_OPPONENTS_PERMANENT_NON_LAND));
        ability.setTargetAdjuster(new TargetsCountAdjuster(new CountersSourceCount(CounterType.ISOLATION)));
        this.addAbility(ability);
    }

    private QuarantineField(final QuarantineField card) {
        super(card);
    }

    @Override
    public QuarantineField copy() {
        return new QuarantineField(this);
    }
}
