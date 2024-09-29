
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;

/**
 *
 * @author Plopman
 */
public final class Scourglass extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("permanents except for artifacts and lands");

    static {
        filter.add(Predicates.not(Predicates.or(CardType.ARTIFACT.getPredicate(), CardType.LAND.getPredicate())));
    }

    public Scourglass(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}{W}{W}");

        // {T}, Sacrifice Scourglass: Destroy all permanents except for artifacts and lands. Activate this ability only during your upkeep.
        Ability ability = new ConditionalActivatedAbility(Zone.BATTLEFIELD,
                new DestroyAllEffect(filter), new TapSourceCost(), new IsStepCondition(PhaseStep.UPKEEP));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private Scourglass(final Scourglass card) {
        super(card);
    }

    @Override
    public Scourglass copy() {
        return new Scourglass(this);
    }
}
