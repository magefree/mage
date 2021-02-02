
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author LevelX2
 */
public final class SpitefulBlow extends CardImpl {

    public SpitefulBlow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{B}{B}");

        // Destroy target creature and target land.
        Effect effect = new DestroyTargetEffect(false, true);
        effect.setText("Destroy target creature and target land");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetLandPermanent());
    }

    private SpitefulBlow(final SpitefulBlow card) {
        super(card);
    }

    @Override
    public SpitefulBlow copy() {
        return new SpitefulBlow(this);
    }
}
