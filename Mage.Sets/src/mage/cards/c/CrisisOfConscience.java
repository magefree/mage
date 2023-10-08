package mage.cards.c;

import mage.abilities.Mode;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TokenPredicate;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class CrisisOfConscience extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("tokens");
    private static final FilterPermanent filter2 = new FilterPermanent("nonland, nontoken permanents");

    static {
        filter.add(TokenPredicate.TRUE);
        filter2.add(Predicates.not(CardType.LAND.getPredicate()));
        filter2.add(TokenPredicate.FALSE);
    }

    public CrisisOfConscience(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{W}{W}");

        // Choose one --
        // * Destroy all tokens.
        this.getSpellAbility().addEffect(new DestroyAllEffect(filter));

        // * Destroy all nonland, nontoken permanents.
        this.getSpellAbility().addMode(new Mode(new DestroyAllEffect(filter2)));
    }

    private CrisisOfConscience(final CrisisOfConscience card) {
        super(card);
    }

    @Override
    public CrisisOfConscience copy() {
        return new CrisisOfConscience(this);
    }
}
