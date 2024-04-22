package mage.cards.f;

import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.MercenaryToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FormAPosse extends CardImpl {

    public FormAPosse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{R}{W}");

        // Create X 1/1 red Mercenary creature tokens with "{T}: Target creature you control gets +1/+0 until end of turn. Activate only as a sorcery."
        this.getSpellAbility().addEffect(new CreateTokenEffect(new MercenaryToken(), ManacostVariableValue.REGULAR));
    }

    private FormAPosse(final FormAPosse card) {
        super(card);
    }

    @Override
    public FormAPosse copy() {
        return new FormAPosse(this);
    }
}
