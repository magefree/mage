
package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author Loki
 */
public final class Cremate extends CardImpl {

    public Cremate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{B}");

        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInGraveyard());
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));
    }

    public Cremate(final Cremate card) {
        super(card);
    }

    @Override
    public Cremate copy() {
        return new Cremate(this);
    }
}
