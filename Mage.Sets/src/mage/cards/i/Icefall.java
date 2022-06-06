
package mage.cards.i;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.RecoverAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

/**
 *
 * @author fireshoes
 */
public final class Icefall extends CardImpl {
    
    private static final FilterPermanent filter = new FilterPermanent("artifact or land");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.LAND.getPredicate()));
    }

    public Icefall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{R}{R}");

        // Destroy target artifact or land.
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        
        // Recover {R}{R}
        this.addAbility(new RecoverAbility(new ManaCostsImpl<>("{R}{R}"), this));
    }

    private Icefall(final Icefall card) {
        super(card);
    }

    @Override
    public Icefall copy() {
        return new Icefall(this);
    }
}
