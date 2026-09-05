package mage.cards.b;

import mage.abilities.condition.common.TeamworkCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.TeamworkAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author muz
 */
public final class BeastMode extends CardImpl {

    public BeastMode(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Teamwork 1
        this.addAbility(new TeamworkAbility(1));

        // Target creature gets +2/+2 and gains trample until end of turn. Also put a +1/+1 counter on that creature if this spell was cast using teamwork.
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 2));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
            TrampleAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains trample until end of turn"));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
            new AddCountersTargetEffect(CounterType.P1P1.createInstance()),
            TeamworkCondition.instance,
            "Also put a +1/+1 counter on that creature if this spell was cast using teamwork"
        ));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private BeastMode(final BeastMode card) {
        super(card);
    }

    @Override
    public BeastMode copy() {
        return new BeastMode(this);
    }
}
