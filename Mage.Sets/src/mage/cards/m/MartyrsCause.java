
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.PreventNextDamageFromChosenSourceToTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author emerald000
 */
public final class MartyrsCause extends CardImpl {

    public MartyrsCause(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // Sacrifice a creature: The next time a source of your choice would deal damage to any target this turn, prevent that damage.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PreventNextDamageFromChosenSourceToTargetEffect(Duration.EndOfTurn),
                new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private MartyrsCause(final MartyrsCause card) {
        super(card);
    }

    @Override
    public MartyrsCause copy() {
        return new MartyrsCause(this);
    }
}
