package mage.cards.b;

import java.util.UUID;

import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.keyword.VentureIntoTheDungeonEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.target.TargetSpell;

/**
 *
 * @author weirddan455
 */
public final class BarTheGate extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("creature or planeswalker spell");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.PLANESWALKER.getPredicate()
        ));
    }

    public BarTheGate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Counter target creature or planeswalker spell. Venture into the dungeon.
        this.getSpellAbility().addTarget(new TargetSpell(filter));
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addEffect(new VentureIntoTheDungeonEffect());
    }

    private BarTheGate(final BarTheGate card) {
        super(card);
    }

    @Override
    public BarTheGate copy() {
        return new BarTheGate(this);
    }
}
