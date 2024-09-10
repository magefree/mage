
package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterSpell;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.TargetsPermanentPredicate;
import mage.target.TargetSpell;

/**
 *
 * @author LoneFox
 */
public final class Confound extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("spell that targets a creature");

    static {
        filter.add(new TargetsPermanentPredicate(new FilterCreaturePermanent()));
    }

    public Confound(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Counter target spell that targets one or more creatures.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell(filter));
        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private Confound(final Confound card) {
        super(card);
    }

    @Override
    public Confound copy() {
        return new Confound(this);
    }
}
