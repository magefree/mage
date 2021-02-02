
package mage.cards.t;

import java.util.UUID;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.common.ControlsCreatureGreatestPowerCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;

/**
 * @author noxx
 */
public final class TriumphOfFerocity extends CardImpl {

    private static final String ruleText = "At the beginning of your upkeep, draw a card if you control the creature with the greatest power or tied for the greatest power";

    public TriumphOfFerocity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{G}");


        // At the beginning of your upkeep, draw a card if you control the creature with the greatest power or tied for the greatest power.
        TriggeredAbility ability = new BeginningOfUpkeepTriggeredAbility(new DrawCardSourceControllerEffect(1), TargetController.YOU, false);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, ControlsCreatureGreatestPowerCondition.instance, ruleText));
    }

    private TriumphOfFerocity(final TriumphOfFerocity card) {
        super(card);
    }

    @Override
    public TriumphOfFerocity copy() {
        return new TriumphOfFerocity(this);
    }
}
