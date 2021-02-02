package mage.cards.f;

import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author Loki
 */
public final class FuelForTheCause extends CardImpl {

    public FuelForTheCause(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}{U}");

        // Counter target spell, then proliferate. (You choose any number of permanents and/or players with counters on them, then give each another counter of a kind already there.)
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addEffect(new ProliferateEffect().concatBy(", then"));
    }

    private FuelForTheCause(final FuelForTheCause card) {
        super(card);
    }

    @Override
    public FuelForTheCause copy() {
        return new FuelForTheCause(this);
    }

}
