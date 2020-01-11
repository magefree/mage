package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.dynamicvalue.common.SignInversionDynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThrenodySinger extends CardImpl {

    private static final DynamicValue xValue = new SignInversionDynamicValue(DevotionCount.U);

    public ThrenodySinger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.SIREN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Threnody Singer enters the battlefield, target creature an opponent controls gets -X/-0 until end of turn, where X is your devotion to blue.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BoostTargetEffect(
                xValue, StaticValue.get(0), Duration.EndOfTurn
        ).setText("target creature an opponent controls gets -X/-0 until end of turn, where X is your devotion to blue"));
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        ability.addHint(DevotionCount.U.getHint());
        this.addAbility(ability);
    }

    private ThrenodySinger(final ThrenodySinger card) {
        super(card);
    }

    @Override
    public ThrenodySinger copy() {
        return new ThrenodySinger(this);
    }
}
