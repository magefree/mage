package mage.cards.d;

import mage.abilities.effects.common.LearnEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.hint.common.OpenSideboardHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.common.FilterSpellOrPermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetSpellOrPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DivideByZero extends CardImpl {

    private static final FilterSpellOrPermanent filter
            = new FilterSpellOrPermanent("spell or permanent with mana value 1 or greater");
    private static final Predicate predicate
            = new ManaValuePredicate(ComparisonType.MORE_THAN, 0);

    static {
        filter.getPermanentFilter().add(predicate);
        filter.getSpellFilter().add(predicate);
    }

    public DivideByZero(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Return target spell or permanent with mana value 1 or greater to its owner's hand.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpellOrPermanent(filter));

        // Learn.
        this.getSpellAbility().addEffect(new LearnEffect().concatBy("<br>"));
        this.getSpellAbility().addHint(OpenSideboardHint.instance);
    }

    private DivideByZero(final DivideByZero card) {
        super(card);
    }

    @Override
    public DivideByZero copy() {
        return new DivideByZero(this);
    }
}
