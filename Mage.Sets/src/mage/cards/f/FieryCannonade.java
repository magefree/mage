
package mage.cards.f;

import java.util.UUID;
import mage.abilities.effects.common.DamageAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;

/**
 *
 * @author LevelX2
 */
public final class FieryCannonade extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("non-Pirate creature");

    static {
        filter.add(Predicates.not(SubType.PIRATE.getPredicate()));
    }

    public FieryCannonade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // Fiery Cannonade deals 2 damage to each non-Pirate creature.
        this.getSpellAbility().addEffect(new DamageAllEffect(2, filter));
    }

    private FieryCannonade(final FieryCannonade card) {
        super(card);
    }

    @Override
    public FieryCannonade copy() {
        return new FieryCannonade(this);
    }
}
