
package mage.cards.o;

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
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author KholdFuzion
 */
public final class Opposition extends CardImpl {

    private static final FilterPermanent artifactcreatureorland = new FilterPermanent("artifact, creature, or land");

    static {
        artifactcreatureorland.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate(),
                CardType.LAND.getPredicate()));
    }

    private static final FilterControlledCreaturePermanent untappedcreatureyoucontrol = new FilterControlledCreaturePermanent("untapped creature you control");

    static {
        untappedcreatureyoucontrol.add(TappedPredicate.UNTAPPED);
    }

    public Opposition(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{U}{U}");

        this.color.setBlue(true);

        // Tap an untapped creature you control: Tap target artifact, creature, or land.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new TapTargetEffect(), new TapTargetCost(new TargetControlledPermanent(untappedcreatureyoucontrol)));
        ability.addTarget(new TargetPermanent(artifactcreatureorland));
        this.addAbility(ability);

    }

    private Opposition(final Opposition card) {
        super(card);
    }

    @Override
    public Opposition copy() {
        return new Opposition(this);
    }
}
