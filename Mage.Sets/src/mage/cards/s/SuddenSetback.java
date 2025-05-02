package mage.cards.s;

import mage.abilities.effects.common.PutOnTopOrBottomLibraryTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterSpellOrPermanent;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetSpellOrPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SuddenSetback extends CardImpl {

    private static final FilterSpellOrPermanent filter = new FilterSpellOrPermanent("spell or nonland permanent");

    static {
        filter.getPermanentFilter().add(Predicates.not(CardType.LAND.getPredicate()));
    }

    public SuddenSetback(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}{U}");

        // The owner of target spell or nonland permanent puts it on the top or bottom of their library.
        this.getSpellAbility().addEffect(new PutOnTopOrBottomLibraryTargetEffect(true));
        this.getSpellAbility().addTarget(new TargetSpellOrPermanent(filter));
    }

    private SuddenSetback(final SuddenSetback card) {
        super(card);
    }

    @Override
    public SuddenSetback copy() {
        return new SuddenSetback(this);
    }
}
