package mage.cards.i;

import java.util.UUID;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.SamuraiToken;

/**
 *
 * @author Addictiveme
 */
public final class ImperialOath extends CardImpl {

    public ImperialOath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{5}{W}");

        // Create three 2/2 white Samurai creature tokens with vigilance. Scry 3.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new SamuraiToken(), 3));
        this.getSpellAbility().addEffect(new ScryEffect(3));
    }

    private ImperialOath(final ImperialOath card) {
        super(card);
    }

    @Override
    public ImperialOath copy() {
        return new ImperialOath(this);
    }
}
