
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
 * @author North
 */
public final class MindShatter extends CardImpl {

    public MindShatter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{X}{B}{B}");

        // Target player discards X cards at random.
        this.getSpellAbility().addEffect(new DiscardTargetEffect(ManacostVariableValue.REGULAR, true));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private MindShatter(final MindShatter card) {
        super(card);
    }

    @Override
    public MindShatter copy() {
        return new MindShatter(this);
    }
}
