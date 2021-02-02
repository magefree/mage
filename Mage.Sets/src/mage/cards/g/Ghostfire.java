
package mage.cards.g;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author dustinconrad
 */
public final class Ghostfire extends CardImpl {

    public Ghostfire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{R}");

        // Ghostfire is colorless.
        this.color = new ObjectColor();
        this.getSpellAbility().addEffect(new InfoEffect("{this} is colorless"));

        // Ghostfire deals 3 damage to any target.
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private Ghostfire(final Ghostfire card) {
        super(card);
    }

    @Override
    public Ghostfire copy() {
        return new Ghostfire(this);
    }
}
