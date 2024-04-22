package mage.cards.i;

import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 *
 * @author Plopman
 */
public final class ImpendingDisaster extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(StaticFilters.FILTER_LAND, ComparisonType.OR_GREATER, 7, false);

    public ImpendingDisaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{R}");

        // At the beginning of your upkeep, if there are seven or more lands on the battlefield, sacrifice Impending Disaster and destroy all lands.
        TriggeredAbility ability  = new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new SacrificeSourceEffect(), TargetController.YOU, false);
        ability.addEffect(new DestroyAllEffect(StaticFilters.FILTER_LANDS));
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, condition,
                "At the beginning of your upkeep, if there are seven or more lands on the battlefield, sacrifice {this} and destroy all lands"));
    }

    private ImpendingDisaster(final ImpendingDisaster card) {
        super(card);
    }

    @Override
    public ImpendingDisaster copy() {
        return new ImpendingDisaster(this);
    }
}
