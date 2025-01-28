package mage.cards.n;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.MaxSpeedGainAbilityEffect;
import mage.abilities.keyword.StartYourEnginesAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.game.permanent.token.ServoToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NestingBot extends CardImpl {

    public NestingBot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.ROBOT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Start your engines!
        this.addAbility(new StartYourEnginesAbility());

        // When this creature dies, create a 1/1 colorless Servo artifact creature token.
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new ServoToken())));

        // Max speed -- This creature gets +1/+0.
        this.addAbility(new SimpleStaticAbility(new MaxSpeedGainAbilityEffect(new BoostSourceEffect(1, 0, Duration.WhileOnBattlefield))));
    }

    private NestingBot(final NestingBot card) {
        super(card);
    }

    @Override
    public NestingBot copy() {
        return new NestingBot(this);
    }
}
