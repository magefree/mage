package mage.cards.i;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SiegeAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.KnightWhiteBlueToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvasionOfBelenon extends CardImpl {

    public InvasionOfBelenon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.BATTLE}, "{2}{W}");

        this.subtype.add(SubType.SIEGE);
        this.setStartingDefense(5);
        this.secondSideCardClazz = mage.cards.b.BelenonWarAnthem.class;

        // (As a Siege enters, choose an opponent to protect it. You and others can attack it. When it's defeated, exile it, then cast it transformed.)
        this.addAbility(new SiegeAbility());

        // When Invasion of Belenon enters the battlefield, create a 2/2 white and blue Knight creature token with vigilance.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new KnightWhiteBlueToken())));
    }

    private InvasionOfBelenon(final InvasionOfBelenon card) {
        super(card);
    }

    @Override
    public InvasionOfBelenon copy() {
        return new InvasionOfBelenon(this);
    }
}
