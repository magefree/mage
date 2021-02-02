
package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.SacrificeControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;

/**
 *
 * @author LevelX2
 */
public final class Dredge extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("creature or land");

    static {
        filter.add(Predicates.or(CardType.CREATURE.getPredicate(), CardType.LAND.getPredicate()));
    }

    public Dredge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{B}");

        // Sacrifice a creature or land.
        this.getSpellAbility().addEffect(new SacrificeControllerEffect(filter, 1, ""));

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));
    }

    private Dredge(final Dredge card) {
        super(card);
    }

    @Override
    public Dredge copy() {
        return new Dredge(this);
    }
}
