
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.SoundTheCallToken;

/**
 *
 * @author TheElk801
 */
public final class SoundTheCall extends CardImpl {

    public SoundTheCall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");
        

        // Create a 1/1 green Wolf creature token. It has "This creature gets +1/+1 for each card named Sound the Call in each graveyard."
        this.getSpellAbility().addEffect(new CreateTokenEffect(new SoundTheCallToken()));
    }

    private SoundTheCall(final SoundTheCall card) {
        super(card);
    }

    @Override
    public SoundTheCall copy() {
        return new SoundTheCall(this);
    }
}
