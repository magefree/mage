package mage.cards.c;

import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.common.FatefulHourCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public final class ConvalescentCare extends CardImpl {

    public ConvalescentCare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{W}{W}");

        // At the beginning of your upkeep, if you have 5 or less life, you gain 3 life and draw a card.
        TriggeredAbility ability = new BeginningOfUpkeepTriggeredAbility(new GainLifeEffect(3), TargetController.YOU, false);
        ability.addEffect(new DrawCardSourceControllerEffect(1));
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, FatefulHourCondition.instance,
                "At the beginning of your upkeep, if you have 5 or less life, you gain 3 life and draw a card."));
    }

    private ConvalescentCare(final ConvalescentCare card) {
        super(card);
    }

    @Override
    public ConvalescentCare copy() {
        return new ConvalescentCare(this);
    }
    

}
