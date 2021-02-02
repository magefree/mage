
package mage.cards.t;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Loki
 */
public final class Tidings extends CardImpl {

    public Tidings(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{U}{U}");

        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(4));
    }

    private Tidings(final Tidings card) {
        super(card);
    }

    @Override
    public Tidings copy() {
        return new Tidings(this);
    }
}
