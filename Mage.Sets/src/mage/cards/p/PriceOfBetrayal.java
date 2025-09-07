package mage.cards.p;

import mage.abilities.effects.common.RemoveUpToAmountCountersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterOpponent;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterPermanentOrPlayer;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetPermanentOrPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PriceOfBetrayal extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate(),
                CardType.PLANESWALKER.getPredicate()
        ));
    }

    private static final FilterPermanentOrPlayer filter2 = new FilterPermanentOrPlayer("artifact, creature, planeswalker, or opponent", filter, new FilterOpponent());

    public PriceOfBetrayal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}");

        // Remove up to five counters from target artifact, creature, planeswalker or opponent.
        this.getSpellAbility().addEffect(new RemoveUpToAmountCountersEffect(5));
        this.getSpellAbility().addTarget(new TargetPermanentOrPlayer(1, 1, filter2, false));
    }

    private PriceOfBetrayal(final PriceOfBetrayal card) {
        super(card);
    }

    @Override
    public PriceOfBetrayal copy() {
        return new PriceOfBetrayal(this);
    }
}