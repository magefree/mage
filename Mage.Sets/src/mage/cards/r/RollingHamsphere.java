package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.CrewAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.HamsterToken;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RollingHamsphere extends CardImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(new FilterControlledPermanent(SubType.HAMSTER));

    public RollingHamsphere(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{7}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Rolling Hamsphere gets +1/+1 for each Hamster you control.
        this.addAbility(new SimpleStaticAbility(new BoostSourceEffect(xValue, xValue, Duration.WhileOnBattlefield)));

        // Whenever Rolling Hamsphere attacks, create three 1/1 red Hamster creature tokens, then it deals X damage to any target, where X is the number of Hamsters you control.
        Ability ability = new AttacksTriggeredAbility(new CreateTokenEffect(new HamsterToken(), 3));
        ability.addEffect(new DamageTargetEffect(xValue)
                .setText(", then it deals X damage to any target, where X is the number of Hamsters you control"));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);

        // Crew 3
        this.addAbility(new CrewAbility(3));
    }

    private RollingHamsphere(final RollingHamsphere card) {
        super(card);
    }

    @Override
    public RollingHamsphere copy() {
        return new RollingHamsphere(this);
    }
}
