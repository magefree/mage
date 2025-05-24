package mage.cards.u;

import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.EndTurnEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Ultima extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("artifacts and creatures");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate()
        ));
    }

    public Ultima(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}{W}");

        // Destroy all artifacts and creatures. End the turn.
        this.getSpellAbility().addEffect(new DestroyAllEffect(filter));
        this.getSpellAbility().addEffect(new EndTurnEffect());
    }

    private Ultima(final Ultima card) {
        super(card);
    }

    @Override
    public Ultima copy() {
        return new Ultima(this);
    }
}
