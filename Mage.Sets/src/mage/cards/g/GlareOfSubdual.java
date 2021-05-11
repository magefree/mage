
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class GlareOfSubdual extends CardImpl {

    private static final FilterControlledCreaturePermanent filterCost = new FilterControlledCreaturePermanent("untapped creature you control");
    private static final FilterPermanent filterTarget = new FilterPermanent("artifact or creature");

    static {
        filterCost.add(TappedPredicate.UNTAPPED);
        filterTarget.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate()));
    }

    public GlareOfSubdual(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{G}{W}");


        // Tap an untapped creature you control: Tap target artifact or creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new TapTargetEffect(), new TapTargetCost(new TargetControlledCreaturePermanent(1, 1, filterCost, true)));
        ability.addTarget(new TargetPermanent(filterTarget));
        this.addAbility(ability);
    }

    private GlareOfSubdual(final GlareOfSubdual card) {
        super(card);
    }

    @Override
    public GlareOfSubdual copy() {
        return new GlareOfSubdual(this);
    }
}
