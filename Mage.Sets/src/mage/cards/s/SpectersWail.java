
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author fireshoes
 */
public final class SpectersWail extends CardImpl {

    public SpectersWail(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{B}");

        // Target player discards a card at random.
        this.getSpellAbility().addEffect(new DiscardTargetEffect(1, true));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private SpectersWail(final SpectersWail card) {
        super(card);
    }

    @Override
    public SpectersWail copy() {
        return new SpectersWail(this);
    }
}
