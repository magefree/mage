package mage.cards.k;

import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.TargetsPermanentPredicate;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KeepSafe extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("spell that targets a permanent you control");

    static {
        filter.add(new TargetsPermanentPredicate(StaticFilters.FILTER_CONTROLLED_PERMANENT));
    }

    public KeepSafe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Counter target spell that targets a permanent you control.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell(filter));

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private KeepSafe(final KeepSafe card) {
        super(card);
    }

    @Override
    public KeepSafe copy() {
        return new KeepSafe(this);
    }
}
