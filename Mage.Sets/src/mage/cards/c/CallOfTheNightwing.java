
package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.common.CipherEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.NightwingHorrorToken;

/**
 *
 * @author LevelX2
 */
public final class CallOfTheNightwing extends CardImpl {

    public CallOfTheNightwing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}{B}");

        // Create a 1/1 blue and black Horror creature token with flying.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new NightwingHorrorToken(), 1));

        // Cipher (Then you may exile this spell card encoded on a creature you control. Whenever that creature deals combat damage to a player, its controller may cast a copy of the encoded card without paying its mana cost.)
        this.getSpellAbility().addEffect(new CipherEffect());

    }

    private CallOfTheNightwing(final CallOfTheNightwing card) {
        super(card);
    }

    @Override
    public CallOfTheNightwing copy() {
        return new CallOfTheNightwing(this);
    }
}
