
package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetOpponent;

/**
 *
 * @author fireshoes
 */
public final class Deception extends CardImpl {

    public Deception(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{B}");

        // Target opponent discards two cards.
        this.getSpellAbility().addTarget(new TargetOpponent());
        this.getSpellAbility().addEffect(new DiscardTargetEffect(2));
    }

    private Deception(final Deception card) {
        super(card);
    }

    @Override
    public Deception copy() {
        return new Deception(this);
    }
}
