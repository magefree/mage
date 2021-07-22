

package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.cost.CastWithoutPayingManaCostEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.ServoToken;

/**
 * @author fireshoes
 */
public final class SramsExpertise extends CardImpl {

    public SramsExpertise(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{W}{W}");


        // Create three 1/1 colorless Servo artifact creature tokens.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new ServoToken(), 3));

        // You may cast a card with converted mana cost 3 or less from your hand without paying its mana cost.
        this.getSpellAbility().addEffect(new CastWithoutPayingManaCostEffect(3).concatBy("<br>"));
    }

    private SramsExpertise(final SramsExpertise card) {
        super(card);
    }

    @Override
    public SramsExpertise copy() {
        return new SramsExpertise(this);
    }
}
