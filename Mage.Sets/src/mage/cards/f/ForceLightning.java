
package mage.cards.f;

import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 *
 * @author Styxo
 */
public final class ForceLightning extends CardImpl {

    public ForceLightning(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{R}{R}");

        // Force Lightning deals X damage to any target.
        this.getSpellAbility().addEffect(new DamageTargetEffect(GetXValue.instance));
        this.getSpellAbility().addTarget(new TargetAnyTarget());

        // Scry X.
        this.getSpellAbility().addEffect(new ScryEffect(GetXValue.instance, false));

    }

    private ForceLightning(final ForceLightning card) {
        super(card);
    }

    @Override
    public ForceLightning copy() {
        return new ForceLightning(this);
    }
}
