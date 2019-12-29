package mage.cards.m;

import mage.abilities.effects.common.SacrificeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MireInMisery extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("a creature or enchantment");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.CREATURE),
                new CardTypePredicate(CardType.ENCHANTMENT)
        ));
    }

    public MireInMisery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Each opponent sacrifices a creature or enchantment.
        this.getSpellAbility().addEffect(new SacrificeOpponentsEffect(filter));
    }

    private MireInMisery(final MireInMisery card) {
        super(card);
    }

    @Override
    public MireInMisery copy() {
        return new MireInMisery(this);
    }
}
