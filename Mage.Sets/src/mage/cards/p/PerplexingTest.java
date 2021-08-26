package mage.cards.p;

import mage.abilities.Mode;
import mage.abilities.effects.common.ReturnToHandFromBattlefieldAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PerplexingTest extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("nontoken creatures");

    static {
        filter.add(TokenPredicate.FALSE);
    }

    public PerplexingTest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}{U}");

        // Choose one —
        // • Return all creature tokens to their owners' hands.
        this.getSpellAbility().addEffect(new ReturnToHandFromBattlefieldAllEffect(StaticFilters.FILTER_CREATURE_TOKENS));

        // • Return all nontoken creatures to their owners' hands.
        this.getSpellAbility().addMode(new Mode(new ReturnToHandFromBattlefieldAllEffect(filter)));
    }

    private PerplexingTest(final PerplexingTest card) {
        super(card);
    }

    @Override
    public PerplexingTest copy() {
        return new PerplexingTest(this);
    }
}
