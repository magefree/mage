
package mage.cards.d;

import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetControllerEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterArtifactPermanent;
import mage.target.common.TargetArtifactPermanent;
import mage.target.targetadjustment.XManaValueTargetAdjuster;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class Detonate extends CardImpl {

    public Detonate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{R}");

        // Destroy target artifact with converted mana cost X. It can't be regenerated. Detonate deals X damage to that artifact's controller.
        this.getSpellAbility().addEffect(new DestroyTargetEffect(true));
        this.getSpellAbility().addTarget(new TargetArtifactPermanent(new FilterArtifactPermanent("artifact with mana value X")));
        Effect effect = new DamageTargetControllerEffect(ManacostVariableValue.REGULAR);
        effect.setText("{this} deals X damage to that artifact's controller");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().setTargetAdjuster(new XManaValueTargetAdjuster());
        this.getSpellAbility().addTarget(new TargetArtifactPermanent());
    }

    private Detonate(final Detonate card) {
        super(card);
    }

    @Override
    public Detonate copy() {
        return new Detonate(this);
    }
}
