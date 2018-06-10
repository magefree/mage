
package mage.cards.d;

import java.util.UUID;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import static mage.filter.StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class DarkTriumph extends CardImpl {

    private static final FilterLandPermanent filterSwamp = new FilterLandPermanent("If you control a Swamp");

    static {
        filterSwamp.add(new SubtypePredicate(SubType.SWAMP));
    }

    public DarkTriumph(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{B}");

        // If you control a Swamp, you may sacrifice a creature rather than pay Dark Triumph's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(
                new SacrificeTargetCost(new TargetControlledCreaturePermanent(FILTER_CONTROLLED_CREATURE_SHORT_TEXT)),
                new PermanentsOnTheBattlefieldCondition(filterSwamp), null
        ));

        // Creatures you control get +2/+0 until end of turn.
        this.getSpellAbility().addEffect(new BoostControlledEffect(2, 0, Duration.EndOfTurn));
    }

    public DarkTriumph(final DarkTriumph card) {
        super(card);
    }

    @Override
    public DarkTriumph copy() {
        return new DarkTriumph(this);
    }
}
