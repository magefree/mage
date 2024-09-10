

package mage.cards.r;

import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Loki
 */
public final class RendFlesh extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("non-Spirit creature");

    static {
        filter.add(Predicates.not(SubType.SPIRIT.getPredicate()));
    }

    public RendFlesh(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{B}");
        this.subtype.add(SubType.ARCANE);

        // Destroy target non-Spirit creature.
        Target target = new TargetCreaturePermanent(filter);
        this.getSpellAbility().addTarget(target);
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
    }

    private RendFlesh(final RendFlesh card) {
        super(card);
    }

    @Override
    public RendFlesh copy() {
        return new RendFlesh(this);
    }

}
