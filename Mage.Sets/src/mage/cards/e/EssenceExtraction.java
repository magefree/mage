
package mage.cards.e;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class EssenceExtraction extends CardImpl {

    public EssenceExtraction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{B}{B}");

        // Essence Extraction deals 3 damage to target creature and you gain 3 life.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        Effect effect = new DamageTargetEffect(3);
        effect.setText("{this} deals 3 damage to target creature");
        this.getSpellAbility().addEffect(effect);
        effect = new GainLifeEffect(3);
        effect.setText("and you gain 3 life");
        this.getSpellAbility().addEffect(effect);
    }

    private EssenceExtraction(final EssenceExtraction card) {
        super(card);
    }

    @Override
    public EssenceExtraction copy() {
        return new EssenceExtraction(this);
    }
}
