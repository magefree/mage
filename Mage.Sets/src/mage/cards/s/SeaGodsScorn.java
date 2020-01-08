package mage.cards.s;

import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SeaGodsScorn extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("creatures and/or enchantments");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.ENCHANTMENT.getPredicate()
        ));
    }

    public SeaGodsScorn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{U}{U}");

        // Return up to three target creatures and/or enchantments to their owners' hands.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(0, 3, filter, false));
    }

    private SeaGodsScorn(final SeaGodsScorn card) {
        super(card);
    }

    @Override
    public SeaGodsScorn copy() {
        return new SeaGodsScorn(this);
    }
}
