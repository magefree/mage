package mage.cards.h;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.RabbitToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HopToIt extends CardImpl {

    public HopToIt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{W}");

        // Create three 1/1 white Rabbit creature tokens.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new RabbitToken(), 3));
    }

    private HopToIt(final HopToIt card) {
        super(card);
    }

    @Override
    public HopToIt copy() {
        return new HopToIt(this);
    }
}
