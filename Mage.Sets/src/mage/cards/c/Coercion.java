
package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetOpponent;

/**
 *
 * @author Plopman
 */
public final class Coercion extends CardImpl {

    public Coercion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{B}");


        // Target opponent reveals their hand. You choose a card from it. That player discards that card.
        this.getSpellAbility().addTarget(new TargetOpponent());
        this.getSpellAbility().addEffect(new DiscardCardYouChooseTargetEffect());
    }

    private Coercion(final Coercion card) {
        super(card);
    }

    @Override
    public Coercion copy() {
        return new Coercion(this);
    }
}
