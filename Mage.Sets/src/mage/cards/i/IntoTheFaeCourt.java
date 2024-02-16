package mage.cards.i;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.FaerieBlockFliersToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IntoTheFaeCourt extends CardImpl {

    public IntoTheFaeCourt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}{U}");

        // Draw three cards. Create a 1/1 blue Faerie creature token with flying and "This creature can block only creatures with flying."
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(3));
        this.getSpellAbility().addEffect(new CreateTokenEffect(new FaerieBlockFliersToken()));
    }

    private IntoTheFaeCourt(final IntoTheFaeCourt card) {
        super(card);
    }

    @Override
    public IntoTheFaeCourt copy() {
        return new IntoTheFaeCourt(this);
    }
}
