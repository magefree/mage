

package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author LevelX2
 */
public final class ShatteringBlow extends CardImpl {

    public ShatteringBlow (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{R/W}");


        // Exile target artifact.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetArtifactPermanent());
    }

    private ShatteringBlow(final ShatteringBlow card) {
        super(card);
    }

    @Override
    public ShatteringBlow  copy() {
        return new ShatteringBlow(this);
    }
}
