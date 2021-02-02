
package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetStackObject;

/**
 * @author JRHerlehy
 */
public final class Disallow extends CardImpl {

    public Disallow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}{U}");


        // Counter target spell, activated ability, or triggered ability.
        Effect effect = new CounterTargetEffect();
        effect.setText("Counter target spell, activated ability, or triggered ability");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetStackObject());
    }

    private Disallow(final Disallow card) {
        super(card);
    }

    @Override
    public Disallow copy() {
        return new Disallow(this);
    }
}
