package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class DrownInShapelessness extends CardImpl {

    public DrownInShapelessness(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Return target creature to its owner's hand.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private DrownInShapelessness(final DrownInShapelessness card) {
        super(card);
    }

    @Override
    public DrownInShapelessness copy() {
        return new DrownInShapelessness(this);
    }
}
