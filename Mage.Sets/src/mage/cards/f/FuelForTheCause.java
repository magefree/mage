

package mage.cards.f;

import java.util.UUID;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetSpell;

/**
 *
 * @author Loki
 */
public final class FuelForTheCause extends CardImpl {

    public FuelForTheCause (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{U}{U}");

        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addEffect(new ProliferateEffect());
    }

    public FuelForTheCause (final FuelForTheCause card) {
        super(card);
    }

    @Override
    public FuelForTheCause copy() {
        return new FuelForTheCause(this);
    }

}
