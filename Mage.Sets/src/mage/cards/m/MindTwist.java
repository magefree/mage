
package mage.cards.m;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public final class MindTwist extends CardImpl {

    public MindTwist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{X}{B}");


        // Target player discards X cards at random.
        this.getSpellAbility().addEffect(new DiscardTargetEffect(ManacostVariableValue.REGULAR, true));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private MindTwist(final MindTwist card) {
        super(card);
    }

    @Override
    public MindTwist copy() {
        return new MindTwist(this);
    }
}
