package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.TapUntappedPermanentTriggeredAbility;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SolitarySanctuary extends CardImpl {

    public SolitarySanctuary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // When Solitary Sanctuary enters the battlefield, tap target creature an opponent controls and put a stun counter on it.
        Ability ability = new EntersBattlefieldTriggeredAbility(new TapTargetEffect());
        ability.addEffect(new AddCountersTargetEffect(CounterType.STUN.createInstance())
                .setText("and put a stun counter on it"));
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);

        // Whenever you tap an untapped creature an opponent controls, put a +1/+1 counter on target creature you control.
        ability = new TapUntappedPermanentTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()),
                StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE
        );
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private SolitarySanctuary(final SolitarySanctuary card) {
        super(card);
    }

    @Override
    public SolitarySanctuary copy() {
        return new SolitarySanctuary(this);
    }
}
