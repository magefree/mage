package mage.cards.a;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.game.permanent.token.FractalToken;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author muz
 */
public final class AdditiveEvolution extends CardImpl {

    public AdditiveEvolution(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}{G}");

        // When this enchantment enters, create a 0/0 blue and green Fractal creature token. Put three +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
            FractalToken.getEffect(StaticValue.get(3), ". Put three +1/+1 counters on it")
        ));

        // At the beginning of combat on your turn, put a +1/+1 counter on target creature you control. It gains vigilance until end of turn.
        Ability ability = new BeginningOfCombatTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        ability.addEffect(new GainAbilityTargetEffect(VigilanceAbility.getInstance()).setText("It gains vigilance until end of turn"));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private AdditiveEvolution(final AdditiveEvolution card) {
        super(card);
    }

    @Override
    public AdditiveEvolution copy() {
        return new AdditiveEvolution(this);
    }
}
