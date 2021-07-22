package mage.cards.i;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.InklingToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InklingSummoning extends CardImpl {

    public InklingSummoning(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{W/B}{W/B}");

        this.subtype.add(SubType.LESSON);

        // Create a 2/1 white and black Inkling creature token with flying.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new InklingToken()));
    }

    private InklingSummoning(final InklingSummoning card) {
        super(card);
    }

    @Override
    public InklingSummoning copy() {
        return new InklingSummoning(this);
    }
}
