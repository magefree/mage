package mage.cards.c;

import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.common.FerociousCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.hint.common.FerociousHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ColossalMajesty extends CardImpl {

    public ColossalMajesty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        // At the beginning of your upkeep, if you control a creature with power 4 or greater, draw a card.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(
                        new DrawCardSourceControllerEffect(1),
                        TargetController.YOU, false
                ),
                FerociousCondition.instance,
                "At the beginning of your upkeep, "
                        + "if you control a creature with power 4 or greater, "
                        + "draw a card."
        ).addHint(FerociousHint.instance));
    }

    private ColossalMajesty(final ColossalMajesty card) {
        super(card);
    }

    @Override
    public ColossalMajesty copy() {
        return new ColossalMajesty(this);
    }
}
