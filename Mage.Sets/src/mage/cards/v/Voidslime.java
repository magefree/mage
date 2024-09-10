
package mage.cards.v;

import java.util.UUID;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetStackObject;

/**
 *
 * @author emerald000
 */
public final class Voidslime extends CardImpl {

    public Voidslime(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{G}{U}{U}");


        // Counter target spell, activated ability, or triggered ability.
        this.getSpellAbility().addEffect(new CounterTargetEffect().setText("Counter target spell, activated ability, or triggered ability"));
        this.getSpellAbility().addTarget(new TargetStackObject());
    }

    private Voidslime(final Voidslime card) {
        super(card);
    }

    @Override
    public Voidslime copy() {
        return new Voidslime(this);
    }
}
