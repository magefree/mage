
package mage.cards.f;

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
public final class Fugue extends CardImpl {

    public Fugue(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{B}{B}");

        // Target player discards three cards.
        this.getSpellAbility().addEffect(new DiscardTargetEffect(3));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private Fugue(final Fugue card) {
        super(card);
    }

    @Override
    public Fugue copy() {
        return new Fugue(this);
    }
}
