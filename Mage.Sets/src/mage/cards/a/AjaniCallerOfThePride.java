
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.dynamicvalue.common.ControllerLifeCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.Effects;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.game.permanent.token.CatToken;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class AjaniCallerOfThePride extends CardImpl {

    public AjaniCallerOfThePride(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.PLANESWALKER},"{1}{W}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.AJANI);

        this.setStartingLoyalty(4);
        // +1: Put a +1/+1 counter on up to one target creature.
        Effect effect = new AddCountersTargetEffect(CounterType.P1P1.createInstance());
        effect.setText("Put a +1/+1 counter on up to one target creature");
        Ability ability = new LoyaltyAbility(effect, 1);
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);
        // -3: Target creature gains flying and double strike until end of turn.
        Effects effects = new Effects();
        effects.add(new GainAbilityTargetEffect(FlyingAbility.getInstance(), Duration.EndOfTurn).setText("target creature gains flying"));
        effects.add(new GainAbilityTargetEffect(DoubleStrikeAbility.getInstance(), Duration.EndOfTurn).setText("and double strike until end of turn"));
        ability = new LoyaltyAbility(effects, -3);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
        // -8: create X 2/2 white Cat creature tokens, where X is your life total.
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new CatToken(), ControllerLifeCount.instance), -8));
    }

    private AjaniCallerOfThePride(final AjaniCallerOfThePride card) {
        super(card);
    }

    @Override
    public AjaniCallerOfThePride copy() {
        return new AjaniCallerOfThePride(this);
    }
}
