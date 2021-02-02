
package mage.cards.m;

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
public final class MindKnives extends CardImpl {

    public MindKnives(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{B}");

        // Target opponent discards a card at random.
        this.getSpellAbility().addEffect(new DiscardTargetEffect(1, true));
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private MindKnives(final MindKnives card) {
        super(card);
    }

    @Override
    public MindKnives copy() {
        return new MindKnives(this);
    }
}
