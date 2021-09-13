
package mage.cards.a;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.FatefulHourCondition;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.constants.Zone;

/**
 * @author TheElk801
 */
public final class ArguelsBloodFast extends CardImpl {

    public ArguelsBloodFast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");

        addSuperType(SuperType.LEGENDARY);
        this.secondSideCardClazz = mage.cards.t.TempleOfAclazotz.class;

        // {1}{B}, Pay 2 life: Draw a card.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), new ManaCostsImpl("{1}{B}"));
        ability.addCost(new PayLifeCost(2));
        this.addAbility(ability);

        // At the beginning of your upkeep, if you have 5 or less life, you may transform Arguel's Blood Fast.
        this.addAbility(new TransformAbility());
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(new TransformSourceEffect(), TargetController.YOU, true),
                FatefulHourCondition.instance,
                "At the beginning of your upkeep, if you have 5 or less life, you may transform {this}"
        ));
    }

    private ArguelsBloodFast(final ArguelsBloodFast card) {
        super(card);
    }

    @Override
    public ArguelsBloodFast copy() {
        return new ArguelsBloodFast(this);
    }
}
