
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author North
 */
public final class SerumVisions extends CardImpl {

    public SerumVisions(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{U}");


        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));
        // Scry 2.
        this.getSpellAbility().addEffect(new ScryEffect(2));
    }

    private SerumVisions(final SerumVisions card) {
        super(card);
    }

    @Override
    public SerumVisions copy() {
        return new SerumVisions(this);
    }
}
