
package mage.cards.w;

import java.util.UUID;

import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

/**
 * @author jeffwadsworth
 */
public final class WarleadersHelix extends CardImpl {

    public WarleadersHelix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}{W}");

        // Warleader's Helix deals 4 damage to any target and you gain 4 life.
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        this.getSpellAbility().addEffect(new GainLifeEffect(4).setText("and you gain 4 life"));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private WarleadersHelix(final WarleadersHelix card) {
        super(card);
    }

    @Override
    public WarleadersHelix copy() {
        return new WarleadersHelix(this);
    }
}
