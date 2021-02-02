

package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetControllerEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class ChandrasOutrage extends CardImpl {

    public ChandrasOutrage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{R}{R}");

        // Chandra's Outrage deals 4 damage to target creature and 2 damage to that creature's controller.
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        Effect effect = new DamageTargetControllerEffect(2);
        effect.setText("and 2 damage to that creature's controller");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private ChandrasOutrage(final ChandrasOutrage card) {
        super(card);
    }

    @Override
    public ChandrasOutrage copy() {
        return new ChandrasOutrage(this);
    }

}
