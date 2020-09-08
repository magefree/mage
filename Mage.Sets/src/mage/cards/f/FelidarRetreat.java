package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.CatBeastToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FelidarRetreat extends CardImpl {

    public FelidarRetreat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");

        // Landfall — Whenever a land enters the battlefield under your control, choose one —
        // • Create a 2/2 white Cat Beast creature token.
        Ability ability = new LandfallAbility(new CreateTokenEffect(new CatBeastToken()));

        // • Put a +1/+1 counter on each creature you control. Those creatures gain vigilance until end of turn.
        Mode mode = new Mode(new AddCountersAllEffect(
                CounterType.P1P1.createInstance(),
                StaticFilters.FILTER_CONTROLLED_CREATURE
        ));
        mode.addEffect(new GainAbilityAllEffect(
                VigilanceAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_CONTROLLED_CREATURE
        ).setText("Those creatures gain vigilance until end of turn"));
        ability.addMode(mode);
        this.addAbility(ability);
    }

    private FelidarRetreat(final FelidarRetreat card) {
        super(card);
    }

    @Override
    public FelidarRetreat copy() {
        return new FelidarRetreat(this);
    }
}
