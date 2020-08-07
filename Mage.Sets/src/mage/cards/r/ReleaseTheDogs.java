package mage.cards.r;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.WhiteDogToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ReleaseTheDogs extends CardImpl {

    public ReleaseTheDogs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}");

        // Create four 1/1 white Dog creature tokens.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new WhiteDogToken(), 4));
    }

    private ReleaseTheDogs(final ReleaseTheDogs card) {
        super(card);
    }

    @Override
    public ReleaseTheDogs copy() {
        return new ReleaseTheDogs(this);
    }
}
