package mage.cards.c;

import mage.abilities.condition.common.FerociousCondition;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.hint.common.FerociousHint;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ColossalMajesty extends CardImpl {

    public ColossalMajesty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        // At the beginning of your upkeep, if you control a creature with power 4 or greater, draw a card.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new DrawCardSourceControllerEffect(1))
                .withInterveningIf(FerociousCondition.instance).addHint(FerociousHint.instance));
    }

    private ColossalMajesty(final ColossalMajesty card) {
        super(card);
    }

    @Override
    public ColossalMajesty copy() {
        return new ColossalMajesty(this);
    }
}
