package mage.cards.i;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SiegeAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.game.permanent.token.KnightWhiteBlueToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvasionOfBelenon extends TransformingDoubleFacedCard {

    public InvasionOfBelenon(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new CardType[]{CardType.BATTLE}, new SubType[]{SubType.SIEGE}, "{2}{W}",
                "Belenon War Anthem",
                new CardType[]{CardType.ENCHANTMENT}, new SubType[]{}, "W"
        );
        this.getLeftHalfCard().setStartingDefense(5);

        this.secondSideCardClazz = mage.cards.b.BelenonWarAnthem.class;

        // (As a Siege enters, choose an opponent to protect it. You and others can attack it. When it's defeated, exile it, then cast it transformed.)
        this.getLeftHalfCard().addAbility(new SiegeAbility());

        // When Invasion of Belenon enters the battlefield, create a 2/2 white and blue Knight creature token with vigilance.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new KnightWhiteBlueToken())));

        // Belenon War Anthem
        // Creatures you control get +1/+1.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield)));

        this.finalizeDFC();
    }

    private InvasionOfBelenon(final InvasionOfBelenon card) {
        super(card);
    }

    @Override
    public InvasionOfBelenon copy() {
        return new InvasionOfBelenon(this);
    }
}
