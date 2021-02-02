

package mage.cards.m;

import java.util.UUID;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author LokiX
 */
public final class MindRot extends CardImpl {

    public MindRot(UUID ownerId, CardSetInfo setInfo){
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{B}");

        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new DiscardTargetEffect(2));
    }

    private MindRot(final MindRot card) {
        super(card);
    }

    @Override
    public MindRot copy() {
        return new MindRot(this);
    }
}
