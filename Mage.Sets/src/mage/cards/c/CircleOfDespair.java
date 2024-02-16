package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.PreventNextDamageFromChosenSourceToTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author Cguy7777
 */
public final class CircleOfDespair extends CardImpl {

    public CircleOfDespair(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}{B}");

        // {1}, Sacrifice a creature: The next time a source of your choice would deal damage to any target this turn, prevent that damage.
        Ability ability = new SimpleActivatedAbility(
                Zone.BATTLEFIELD, new PreventNextDamageFromChosenSourceToTargetEffect(Duration.EndOfTurn), new GenericManaCost(1));
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private CircleOfDespair(final CircleOfDespair card) {
        super(card);
    }

    @Override
    public CircleOfDespair copy() {
        return new CircleOfDespair(this);
    }
}
