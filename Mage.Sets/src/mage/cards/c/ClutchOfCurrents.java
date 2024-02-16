
package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.AwakenAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class ClutchOfCurrents extends CardImpl {

    public ClutchOfCurrents(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{U}");

        // Return target creature to its owner's hand.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Awaken 3—{4}{U}
        this.addAbility(new AwakenAbility(this, 3, "{4}{U}"));
    }

    private ClutchOfCurrents(final ClutchOfCurrents card) {
        super(card);
    }

    @Override
    public ClutchOfCurrents copy() {
        return new ClutchOfCurrents(this);
    }
}
