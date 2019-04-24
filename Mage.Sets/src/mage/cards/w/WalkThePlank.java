
package mage.cards.w;

import java.util.UUID;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author spjspj
 */
public final class WalkThePlank extends CardImpl {

    private final static FilterCreaturePermanent filter = new FilterCreaturePermanent("non-Merfolk creature");

    static {
        filter.add(new CardTypePredicate(CardType.CREATURE));
        filter.add(Predicates.not(new SubtypePredicate(SubType.MERFOLK)));
    }

    public WalkThePlank(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}{B}");

        // Destroy target non-Merfolk creature.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
    }

    public WalkThePlank(final WalkThePlank card) {
        super(card);
    }

    @Override
    public WalkThePlank copy() {
        return new WalkThePlank(this);
    }
}
