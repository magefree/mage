package mage.cards.s;

import java.util.UUID;

import mage.abilities.Mode;
import mage.abilities.effects.common.SacrificeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;

/**
 * @author TheElk801
 */
public final class SheoldredsEdict extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("nontoken creature");
    private static final FilterPermanent filter2 = new FilterCreaturePermanent("creature token");

    static {
        filter.add(TokenPredicate.FALSE);
        filter2.add(TokenPredicate.TRUE);
    }

    public SheoldredsEdict(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // Choose one
        // * Each opponent sacrifices a nontoken creature.
        this.getSpellAbility().addEffect(new SacrificeOpponentsEffect(filter));

        // * Each opponent sacrifices a creature token.
        this.getSpellAbility().addMode(new Mode(new SacrificeOpponentsEffect(filter2)));

        // * Each opponent sacrifices a planeswalker.
        this.getSpellAbility().addMode(new Mode(new SacrificeOpponentsEffect(StaticFilters.FILTER_PERMANENT_PLANESWALKER)));
    }

    private SheoldredsEdict(final SheoldredsEdict card) {
        super(card);
    }

    @Override
    public SheoldredsEdict copy() {
        return new SheoldredsEdict(this);
    }
}
