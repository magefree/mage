package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.GainLifeControllerTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedGainedLifeValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.game.permanent.token.FoodToken;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetadjustment.TargetsCountAdjuster;

import java.util.UUID;

/**
 * @author miesma
 */
public final class HeroicFeast extends CardImpl {

    public HeroicFeast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        // When this enchantment enters, create a Food token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new FoodToken())));

        // Whenever you gain life,
        // choose up to that many target creatures you control.
        // Put a +1/+1 counter on each of them.
        Ability heroicFeastAbility = new GainLifeControllerTriggeredAbility(
                new AddCountersTargetEffect(
                        CounterType.P1P1.createInstance()
                ).setText("choose up to that many target creatures you control. " +
                        "Put a +1/+1 counter on each of them"),
                false, false
        );
        heroicFeastAbility.addTarget(new TargetControlledCreaturePermanent(0, 0));
        heroicFeastAbility.setTargetAdjuster(new TargetsCountAdjuster(SavedGainedLifeValue.MANY));
        this.addAbility(heroicFeastAbility);
    }

    private HeroicFeast(final HeroicFeast card) {
        super(card);
    }

    @Override
    public HeroicFeast copy() {
        return new HeroicFeast(this);
    }
}
