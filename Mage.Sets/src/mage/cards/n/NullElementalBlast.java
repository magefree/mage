package mage.cards.n;

import mage.abilities.Mode;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.MulticoloredPredicate;
import mage.target.TargetPermanent;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NullElementalBlast extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("multicolored permanent");

    static {
        filter.add(MulticoloredPredicate.instance);
    }

    public NullElementalBlast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{C}");

        // Choose one --
        // * Counter target multicolored spell.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell(StaticFilters.FILTER_SPELL_MULTICOLORED));

        // * Destroy target multicolored permanent.
        this.getSpellAbility().addMode(new Mode(new DestroyTargetEffect()).addTarget(new TargetPermanent(filter)));
    }

    private NullElementalBlast(final NullElementalBlast card) {
        super(card);
    }

    @Override
    public NullElementalBlast copy() {
        return new NullElementalBlast(this);
    }
}
