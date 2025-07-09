package mage.cards.i;

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
import mage.filter.common.FilterLandPermanent;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class ImpendingDisaster extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            new FilterLandPermanent("there are seven or more lands on the battlefield"),
            ComparisonType.OR_GREATER, 7, false
    );

    public ImpendingDisaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");

        // At the beginning of your upkeep, if there are seven or more lands on the battlefield, sacrifice Impending Disaster and destroy all lands.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new SacrificeSourceEffect()).withInterveningIf(condition);
        ability.addEffect(new DestroyAllEffect(StaticFilters.FILTER_LANDS).concatBy("and"));
        this.addAbility(ability);
    }

    private ImpendingDisaster(final ImpendingDisaster card) {
        super(card);
    }

    @Override
    public ImpendingDisaster copy() {
        return new ImpendingDisaster(this);
    }
}
