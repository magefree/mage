package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class Scourglass extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("permanents except for artifacts and lands");

    static {
        filter.add(Predicates.not(CardType.ARTIFACT.getPredicate()));
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
    }

    public Scourglass(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}{W}{W}");

        // {T}, Sacrifice Scourglass: Destroy all permanents except for artifacts and lands. Activate this ability only during your upkeep.
        Ability ability = new ActivateIfConditionActivatedAbility(
                new DestroyAllEffect(filter), new TapSourceCost(), IsStepCondition.getMyUpkeep()
        );
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
