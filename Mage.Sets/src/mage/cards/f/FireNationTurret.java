package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FirebendingAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FireNationTurret extends CardImpl {

    public FireNationTurret(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{R}");

        // At the beginning of combat on your turn, up to one target creature gets +2/+0 and gains firebending 2 until end of turn.
        Ability ability = new BeginningOfCombatTriggeredAbility(new BoostTargetEffect(2, 0)
                .setText("up to one target creature gets +2/+0"));
        ability.addEffect(new GainAbilityTargetEffect(new FirebendingAbility(2))
                .setText("and gains firebending 2 until end of turn"));
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);

        // {R}: Put a charge counter on this artifact.
        this.addAbility(new SimpleActivatedAbility(
                new AddCountersSourceEffect(CounterType.CHARGE.createInstance()), new ManaCostsImpl<>("{R}")
        ));

        // Remove fifty charge counters from this artifact: It deals 50 damage to any target.
        ability = new SimpleActivatedAbility(
                new DamageTargetEffect(50, "it"),
                new RemoveCountersSourceCost(CounterType.CHARGE.createInstance(50))
        );
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private FireNationTurret(final FireNationTurret card) {
        super(card);
    }

    @Override
    public FireNationTurret copy() {
        return new FireNationTurret(this);
    }
}
