package mage.cards.p;

import java.util.UUID;

import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldWithCounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author weirddan455
 */
public final class Persist extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("nonlegendary creature card from your graveyard");

    static {
        filter.add(Predicates.not(SuperType.LEGENDARY.getPredicate()));
    }

    public Persist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Return target nonlegendary creature card from your graveyard to the battlefield with a -1/-1 counter on it.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldWithCounterTargetEffect(CounterType.M1M1.createInstance()));
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(filter));
    }

    private Persist(final Persist card) {
        super(card);
    }

    @Override
    public Persist copy() {
        return new Persist(this);
    }
}
