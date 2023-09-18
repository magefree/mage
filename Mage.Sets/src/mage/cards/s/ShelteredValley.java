
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.SacrificeAllCost;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.EnterBattlefieldPayCostOrPutGraveyardEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.NamePredicate;

/**
 *
 * @author anonymous
 */
public final class ShelteredValley extends CardImpl {

    private static final FilterPermanent filterShelteredValley = new FilterPermanent("permanent named Sheltered Valley");

    static {
        filterShelteredValley.add(new NamePredicate("Sheltered Valley"));
    }

    public ShelteredValley(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // If Sheltered Valley would enter the battlefield, instead sacrifice each other permanent named Sheltered Valley you control, then put Sheltered Valley onto the battlefield.
        Effect effect = new EnterBattlefieldPayCostOrPutGraveyardEffect(new SacrificeAllCost(filterShelteredValley));
        effect.setText("If {this} would enter the battlefield, instead sacrifice each other permanent named {this} you control, then put {this} onto the battlefield.");
        Ability ability = new SimpleStaticAbility(Zone.ALL, effect);
        this.addAbility(ability);

        // At the beginning of your upkeep, if you control three or fewer lands, you gain 1 life.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new GainLifeEffect(1), TargetController.YOU, false),
                new PermanentsOnTheBattlefieldCondition(StaticFilters.FILTER_LANDS, ComparisonType.FEWER_THAN, 4),
                "At the beginning of your upkeep, if you control three or fewer lands, you gain 1 life."
        ));
        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
    }

    private ShelteredValley(final ShelteredValley card) {
        super(card);
    }

    @Override
    public ShelteredValley copy() {
        return new ShelteredValley(this);
    }
}
