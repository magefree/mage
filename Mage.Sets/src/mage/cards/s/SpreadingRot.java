
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.LoseLifeTargetControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author TheElk801
 */
public final class SpreadingRot extends CardImpl {

    public SpreadingRot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}");

        // Destroy target land.  Its controller loses 2 life.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addEffect(new LoseLifeTargetControllerEffect(2));
        this.getSpellAbility().addTarget(new TargetLandPermanent());
    }

    private SpreadingRot(final SpreadingRot card) {
        super(card);
    }

    @Override
    public SpreadingRot copy() {
        return new SpreadingRot(this);
    }
}
