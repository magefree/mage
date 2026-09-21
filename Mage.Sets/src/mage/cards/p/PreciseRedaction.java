package mage.cards.p;

import java.util.UUID;

import mage.ObjectColor;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.TargetSpell;

/**
 *
 * @author muz
 */
public final class PreciseRedaction extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("white or black spell");

    static {
        filter.add(Predicates.or(
            new ColorPredicate(ObjectColor.WHITE),
            new ColorPredicate(ObjectColor.BLACK)
        ));
    }

    public PreciseRedaction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Counter target white or black spell.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell(filter));
    }

    private PreciseRedaction(final PreciseRedaction card) {
        super(card);
    }

    @Override
    public PreciseRedaction copy() {
        return new PreciseRedaction(this);
    }
}
