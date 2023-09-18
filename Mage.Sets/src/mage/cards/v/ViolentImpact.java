
package mage.cards.v;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.CyclingAbility;
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
public final class ViolentImpact extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("artifact or land");

    static {
        filter.add(Predicates.or(CardType.ARTIFACT.getPredicate(),
                CardType.LAND.getPredicate()));
    }

    public ViolentImpact(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}");

        // Destroy target artifact or land.
        getSpellAbility().addEffect(new DestroyTargetEffect());
        getSpellAbility().addTarget(new TargetPermanent(filter));

        // Cycling {2}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));

    }

    private ViolentImpact(final ViolentImpact card) {
        super(card);
    }

    @Override
    public ViolentImpact copy() {
        return new ViolentImpact(this);
    }
}
