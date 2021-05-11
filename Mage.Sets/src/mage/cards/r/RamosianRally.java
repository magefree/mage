

package mage.cards.r;

import java.util.UUID;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledPermanent;

/**
 * @author JRHerlehy
 */
public final class RamosianRally extends CardImpl {

    private static final FilterControlledPermanent plainsFilter = new FilterControlledPermanent("If you control a Plains");
    private static final FilterControlledCreaturePermanent creatureFilter = new FilterControlledCreaturePermanent("an untapped creature you control");

    static {
        plainsFilter.add(SubType.PLAINS.getPredicate());
        creatureFilter.add(TappedPredicate.UNTAPPED);
    }

    public RamosianRally(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{W}");

        // If you control a Plains, you may tap an untapped creature you control rather than pay Ramosian Rally's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new TapTargetCost(new TargetControlledPermanent(creatureFilter)), new PermanentsOnTheBattlefieldCondition(plainsFilter)));

        // Creatures you control get +1/+1 until end of turn.
        this.getSpellAbility().addEffect(new BoostControlledEffect(1, 1, Duration.EndOfTurn));
    }

    private RamosianRally(final RamosianRally card) {
        super(card);
    }

    @Override
    public RamosianRally copy() {
        return new RamosianRally(this);
    }
}
