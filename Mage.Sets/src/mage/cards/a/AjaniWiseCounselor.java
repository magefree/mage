package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.dynamicvalue.common.ControllerLifeCount;
import mage.abilities.dynamicvalue.common.CreaturesYouControlCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AjaniWiseCounselor extends CardImpl {

    public AjaniWiseCounselor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{W}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.AJANI);
        this.setStartingLoyalty(5);

        // +2: You gain 1 life for each creature you control.
        this.addAbility(new LoyaltyAbility(new GainLifeEffect(CreaturesYouControlCount.instance)
                .setText("you gain 1 life for each creature you control"), 2));

        // −3: Creatures you control get +2/+2 until end of turn.
        this.addAbility(new LoyaltyAbility(
                new BoostControlledEffect(2, 2, Duration.EndOfTurn), -3
        ));

        // −9: Put X +1/+1 counters on target creature, where X is your life total.
        Ability ability = new LoyaltyAbility(new AddCountersTargetEffect(
                CounterType.P1P1.createInstance(),
                ControllerLifeCount.instance
        ).setText("put X +1/+1 counters on target creature, where X is your life total"), -9);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private AjaniWiseCounselor(final AjaniWiseCounselor card) {
        super(card);
    }

    @Override
    public AjaniWiseCounselor copy() {
        return new AjaniWiseCounselor(this);
    }
}
