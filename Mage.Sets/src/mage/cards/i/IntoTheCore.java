
package mage.cards.i;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterArtifactPermanent;
import mage.target.TargetPermanent;

/**
 *
 * @author North
 */
public final class IntoTheCore extends CardImpl {

    public IntoTheCore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{R}{R}");

        // Exile two target artifacts.
        this.getSpellAbility().addTarget(new TargetPermanent(2, 2, new FilterArtifactPermanent("artifacts"), false));
        Effect effect = new ExileTargetEffect();
        effect.setText("Exile two target artifacts");
        this.getSpellAbility().addEffect(effect);
    }

    private IntoTheCore(final IntoTheCore card) {
        super(card);
    }

    @Override
    public IntoTheCore copy() {
        return new IntoTheCore(this);
    }
}
