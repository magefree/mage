
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BlocksOrBecomesBlockedSourceTriggeredAbility;
import mage.abilities.common.DealsDamageToOpponentTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.RemoveAllCountersSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.WitherAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

/**
 *
 * @author jeffwadsworth
 */
public final class WitherscaleWurm extends CardImpl {

    public WitherscaleWurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");
        this.subtype.add(SubType.WURM);

        this.power = new MageInt(9);
        this.toughness = new MageInt(9);

        // Whenever Witherscale Wurm blocks or becomes blocked by a creature, that creature gains wither until end of turn.
        Effect effect = new GainAbilityTargetEffect(WitherAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("that creature gains wither until end of turn");
        Ability ability = new BlocksOrBecomesBlockedSourceTriggeredAbility(effect, StaticFilters.FILTER_PERMANENT_CREATURE, false, null, true);
        this.addAbility(ability);

        // Whenever Witherscale Wurm deals damage to an opponent, remove all -1/-1 counters from it.
        this.addAbility(new DealsDamageToOpponentTriggeredAbility(new RemoveAllCountersSourceEffect(CounterType.M1M1), false));

    }

    private WitherscaleWurm(final WitherscaleWurm card) {
        super(card);
    }

    @Override
    public WitherscaleWurm copy() {
        return new WitherscaleWurm(this);
    }
}
