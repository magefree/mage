

package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Loki
 */
public final class Divination extends CardImpl {

    public Divination (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{U}");

        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2));
    }

    private Divination(final Divination card) {
        super(card);
    }

    @Override
    public Divination copy() {
        return new Divination(this);
    }

}
