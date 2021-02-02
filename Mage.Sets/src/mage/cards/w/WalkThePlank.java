
package mage.cards.w;

import java.util.UUID;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author spjspj
 */
public final class WalkThePlank extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("non-Merfolk creature");

    static {
        filter.add(CardType.CREATURE.getPredicate());
        filter.add(Predicates.not(SubType.MERFOLK.getPredicate()));
    }

    public WalkThePlank(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}{B}");

        // Destroy target non-Merfolk creature.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
    }

    private WalkThePlank(final WalkThePlank card) {
        super(card);
    }

    @Override
    public WalkThePlank copy() {
        return new WalkThePlank(this);
    }
}
