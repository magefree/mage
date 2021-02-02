package mage.cards.m;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.SpiritWhiteToken;

import java.util.UUID;

/**
 * @author nantuko
 */
public final class MidnightHaunting extends CardImpl {

    public MidnightHaunting(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");


        // Create two 1/1 white Spirit creature tokens with flying.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new SpiritWhiteToken(), 2));
    }

    private MidnightHaunting(final MidnightHaunting card) {
        super(card);
    }

    @Override
    public MidnightHaunting copy() {
        return new MidnightHaunting(this);
    }
}
