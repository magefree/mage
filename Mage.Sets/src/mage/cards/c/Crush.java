
package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author North
 */
public final class Crush extends CardImpl {

    private static final FilterArtifactPermanent filter = new FilterArtifactPermanent("noncreature artifact");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    public Crush(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{R}");


        this.getSpellAbility().addTarget(new TargetArtifactPermanent(filter));
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
    }

    private Crush(final Crush card) {
        super(card);
    }

    @Override
    public Crush copy() {
        return new Crush(this);
    }
}
