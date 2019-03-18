
package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInASingleGraveyard;

/**
 *
 * @author ilcartographer
 */
public final class Decompose extends CardImpl {

    public Decompose(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{B}");

        // Exile up to three target cards from a single graveyard.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInASingleGraveyard(0, 3, new FilterCard("cards from a single graveyard")));
    }

    public Decompose(final Decompose card) {
        super(card);
    }

    @Override
    public Decompose copy() {
        return new Decompose(this);
    }
}
