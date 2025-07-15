package mage.cards.b;

import mage.abilities.effects.common.ExileAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BeyondTheQuiet extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("creatures and Spacecraft");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                SubType.SPACECRAFT.getPredicate()
        ));
    }

    public BeyondTheQuiet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}{W}");

        // Exile all creatures and Spacecraft.
        this.getSpellAbility().addEffect(new ExileAllEffect(filter));
    }

    private BeyondTheQuiet(final BeyondTheQuiet card) {
        super(card);
    }

    @Override
    public BeyondTheQuiet copy() {
        return new BeyondTheQuiet(this);
    }
}
