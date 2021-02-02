
package mage.cards.l;

import java.util.UUID;
import mage.abilities.effects.common.CipherEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Plopman
 */
public final class LastThoughts extends CardImpl {

    public LastThoughts(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{U}");


        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));
        // Cipher
        this.getSpellAbility().addEffect(new CipherEffect());
    }

    private LastThoughts(final LastThoughts card) {
        super(card);
    }

    @Override
    public LastThoughts copy() {
        return new LastThoughts(this);
    }
}
