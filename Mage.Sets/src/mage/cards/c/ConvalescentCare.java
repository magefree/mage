package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.condition.common.FatefulHourCondition;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class ConvalescentCare extends CardImpl {

    public ConvalescentCare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}{W}");

        // At the beginning of your upkeep, if you have 5 or less life, you gain 3 life and draw a card.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new GainLifeEffect(3)).withInterveningIf(FatefulHourCondition.instance);
        ability.addEffect(new DrawCardSourceControllerEffect(1).concatBy("and"));
        this.addAbility(ability);
    }

    private ConvalescentCare(final ConvalescentCare card) {
        super(card);
    }

    @Override
    public ConvalescentCare copy() {
        return new ConvalescentCare(this);
    }


}
