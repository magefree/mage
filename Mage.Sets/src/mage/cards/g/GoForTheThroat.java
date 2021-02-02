
package mage.cards.g;

import java.util.UUID;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class GoForTheThroat extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(Predicates.not(CardType.ARTIFACT.getPredicate()));
    }

    public GoForTheThroat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{B}");


        Target target = new TargetCreaturePermanent(filter);
        target.setTargetName("nonartifact creature");
        this.getSpellAbility().addTarget(target);
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
    }

    private GoForTheThroat(final GoForTheThroat card) {
        super(card);
    }

    @Override
    public GoForTheThroat copy() {
        return new GoForTheThroat(this);
    }
}
