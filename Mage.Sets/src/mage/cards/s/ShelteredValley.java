package mage.cards.s;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.SacrificeAllCost;
import mage.abilities.effects.common.EnterBattlefieldPayCostOrPutGraveyardEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.predicate.mageobject.NamePredicate;

import java.util.UUID;

/**
 * @author anonymous
 */
public final class ShelteredValley extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("permanent named Sheltered Valley");

    static {
        filter.add(new NamePredicate("Sheltered Valley"));
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            new FilterControlledLandPermanent("you control three or fewer lands"),
            ComparisonType.FEWER_THAN, 4
    );

    public ShelteredValley(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // If Sheltered Valley would enter the battlefield, instead sacrifice each other permanent named Sheltered Valley you control, then put Sheltered Valley onto the battlefield.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new EnterBattlefieldPayCostOrPutGraveyardEffect(new SacrificeAllCost(filter))
                .setText("If {this} would enter, instead sacrifice each other permanent " +
                        "named Sheltered Valley you control, then put {this} onto the battlefield.")
        ));

        // At the beginning of your upkeep, if you control three or fewer lands, you gain 1 life.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new GainLifeEffect(1)).withInterveningIf(condition));

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
