
package mage.cards.o;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author LevelX2
 */
public final class OrcishCannonade extends CardImpl {

    public OrcishCannonade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{R}{R}");

        // Orcish Cannonade deals 2 damage to any target and 3 damage to you.
        this.getSpellAbility().addEffect(new DamageTargetEffect(2));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        Effect effect = new DamageControllerEffect(3);
        effect.setText("and 3 damage to you");
        this.getSpellAbility().addEffect(effect);
        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private OrcishCannonade(final OrcishCannonade card) {
        super(card);
    }

    @Override
    public OrcishCannonade copy() {
        return new OrcishCannonade(this);
    }
}
