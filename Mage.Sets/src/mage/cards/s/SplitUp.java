package mage.cards.s;

import mage.abilities.Mode;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SplitUp extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("tapped creatures");
    private static final FilterPermanent filter2 = new FilterCreaturePermanent("untapped creatures");

    static {
        filter.add(TappedPredicate.TAPPED);
        filter2.add(TappedPredicate.UNTAPPED);
    }

    public SplitUp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{W}{W}");

        // Choose one --
        // * Destroy all tapped creatures.
        this.getSpellAbility().addEffect(new DestroyAllEffect(filter));

        // * Destroy all untapped creatures.
        this.getSpellAbility().addMode(new Mode(new DestroyAllEffect(filter2)));
    }

    private SplitUp(final SplitUp card) {
        super(card);
    }

    @Override
    public SplitUp copy() {
        return new SplitUp(this);
    }
}
