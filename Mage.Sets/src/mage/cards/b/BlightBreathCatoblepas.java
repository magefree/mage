package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.dynamicvalue.common.SignInversionDynamicValue;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
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
public final class BlightBreathCatoblepas extends CardImpl {

    private static final DynamicValue xValue = new SignInversionDynamicValue(DevotionCount.B);

    public BlightBreathCatoblepas(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Blight-Breath Catoblepas enters the battlefield, target creature an opponent controls gets -X/-X until end of turn, where X is your devotion to black.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BoostTargetEffect(
                xValue, xValue, Duration.EndOfTurn
        ).setText("target creature an opponent controls gets -X/-X until end of turn, where X is your devotion to black"));
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        ability.addHint(DevotionCount.B.getHint());
        this.addAbility(ability);
    }

    private BlightBreathCatoblepas(final BlightBreathCatoblepas card) {
        super(card);
    }

    @Override
    public BlightBreathCatoblepas copy() {
        return new BlightBreathCatoblepas(this);
    }
}
