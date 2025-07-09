package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class PlanarCollapse extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            new FilterCreaturePermanent("there are four or more creatures on the battlefield"),
            ComparisonType.MORE_THAN, 3, false
    );

    public PlanarCollapse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        // At the beginning of your upkeep, if there are four or more creatures on the battlefield, sacrifice Planar Collapse and destroy all creatures. They can't be regenerated.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new SacrificeSourceEffect()).withInterveningIf(condition);
        ability.addEffect(new DestroyAllEffect(StaticFilters.FILTER_PERMANENT_CREATURES, true).concatBy("and"));
        this.addAbility(ability);
    }

    private PlanarCollapse(final PlanarCollapse card) {
        super(card);
    }

    @Override
    public PlanarCollapse copy() {
        return new PlanarCollapse(this);
    }
}
