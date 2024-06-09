

package mage.cards.w;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ShuffleSpellEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.CatToken;

/**
 *
 * @author Loki
 */
public final class WhiteSunsZenith extends CardImpl {
    public WhiteSunsZenith (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{X}{W}{W}{W}");

        // create X 2/2 white Cat creature tokens. Shuffle White Sun's Zenith into its owner's library.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new CatToken(), ManacostVariableValue.REGULAR));
        this.getSpellAbility().addEffect(ShuffleSpellEffect.getInstance());
    }

    private WhiteSunsZenith(final WhiteSunsZenith card) {
        super(card);
    }

    @Override
    public WhiteSunsZenith copy() {
        return new WhiteSunsZenith(this);
    }
}
