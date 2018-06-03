
package mage.cards.c;

import java.util.UUID;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author dustinconrad
 */
public final class Crash extends CardImpl {

    private static final FilterPermanent effectFilter = new FilterPermanent("artifact");
    private static final FilterControlledPermanent alternativeCostFilter = new FilterControlledLandPermanent("a Mountain");

    static {
        effectFilter.add(new CardTypePredicate(CardType.ARTIFACT));
        alternativeCostFilter.add(new SubtypePredicate(SubType.MOUNTAIN));
    }

    public Crash(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{R}");


        // You may sacrifice a Mountain rather than pay Crash's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new SacrificeTargetCost(new TargetControlledPermanent(1, 1, alternativeCostFilter, true))));

        // Destroy target artifact.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(effectFilter));
    }

    public Crash(final Crash card) {
        super(card);
    }

    @Override
    public Crash copy() {
        return new Crash(this);
    }
}
