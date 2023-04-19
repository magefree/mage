package mage.cards.i;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SiegeAbility;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
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
public final class InvasionOfNewPhyrexia extends CardImpl {

    public InvasionOfNewPhyrexia(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.BATTLE}, "{X}{W}{U}");

        this.subtype.add(SubType.SIEGE);
        this.setStartingDefense(6);
        this.secondSideCardClazz = mage.cards.t.TeferiAkosaOfZhalfir.class;

        // (As a Siege enters, choose an opponent to protect it. You and others can attack it. When it's defeated, exile it, then cast it transformed.)
        this.addAbility(new SiegeAbility());

        // When Invasion of New Phyrexia enters the battlefield, create X 2/2 white and blue Knight creature tokens with vigilance.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new KnightWhiteBlueToken(), ManacostVariableValue.ETB)));
    }

    private InvasionOfNewPhyrexia(final InvasionOfNewPhyrexia card) {
        super(card);
    }

    @Override
    public InvasionOfNewPhyrexia copy() {
        return new InvasionOfNewPhyrexia(this);
    }
}
