
package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetSpell;

/**
 *
 * @author fireshoes
 */
public final class ConfirmSuspicions extends CardImpl {

    public ConfirmSuspicions(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{U}{U}");

        // Counter target spell.
        getSpellAbility().addEffect(new CounterTargetEffect());
        getSpellAbility().addTarget(new TargetSpell());

        // Investigate three times.
        Effect effect = new InvestigateEffect();
        effect.setText("<BR><BR>Investigate three times.");
        getSpellAbility().addEffect(effect);
        effect = new InvestigateEffect();
        effect.setText(null);
        getSpellAbility().addEffect(effect);
        getSpellAbility().addEffect(effect);
    }

    public ConfirmSuspicions(final ConfirmSuspicions card) {
        super(card);
    }

    @Override
    public ConfirmSuspicions copy() {
        return new ConfirmSuspicions(this);
    }
}
