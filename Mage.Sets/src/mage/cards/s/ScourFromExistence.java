
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPermanent;

/**
 *
 * @author fireshoes
 */
public final class ScourFromExistence extends CardImpl {

    public ScourFromExistence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{7}");

        // Exile target permanent.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent());
    }

    private ScourFromExistence(final ScourFromExistence card) {
        super(card);
    }

    @Override
    public ScourFromExistence copy() {
        return new ScourFromExistence(this);
    }
}
