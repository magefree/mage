
package mage.cards.c;

import java.util.UUID;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.common.XorLessLifeCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;

/**
 *
 * @author fireshoes
 */
public final class ConvalescentCare extends CardImpl {

    public ConvalescentCare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{W}{W}");

        // At the beginning of your upkeep, if you have 5 or less life, you gain 3 life and draw a card.
        Effect effect = new DrawCardSourceControllerEffect(1);
        TriggeredAbility ability = new BeginningOfUpkeepTriggeredAbility(new GainLifeEffect(3), TargetController.YOU, false);
        ability.addEffect(effect);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, new XorLessLifeCondition(XorLessLifeCondition.CheckType.CONTROLLER, 5), "At the beginning of your upkeep, if you have 5 or less life, you gain 3 life and draw a card."));
    }

    private ConvalescentCare(final ConvalescentCare card) {
        super(card);
    }

    @Override
    public ConvalescentCare copy() {
        return new ConvalescentCare(this);
    }
    

}
