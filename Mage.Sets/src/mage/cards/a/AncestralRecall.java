
package mage.cards.a;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public final class AncestralRecall extends CardImpl {

    public AncestralRecall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}");


        // Target player draws three cards.
        this.getSpellAbility().addEffect(new DrawCardTargetEffect(3));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private AncestralRecall(final AncestralRecall card) {
        super(card);
    }

    @Override
    public AncestralRecall copy() {
        return new AncestralRecall(this);
    }
}
