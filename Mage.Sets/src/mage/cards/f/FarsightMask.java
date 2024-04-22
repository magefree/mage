package mage.cards.f;

import mage.abilities.common.SourceDealsDamageToYouTriggeredAbility;
import mage.abilities.condition.common.SourceTappedCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author North
 */
public final class FarsightMask extends CardImpl {

    private static final String rule = "Whenever a source an opponent controls deals damage to you, if {this} is untapped, you may draw a card.";

    public FarsightMask(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");

        // Whenever a source an opponent controls deals damage to you, if Farsight Mask is untapped, you may draw a card.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(new SourceDealsDamageToYouTriggeredAbility(
                new DrawCardSourceControllerEffect(1), true
        ), SourceTappedCondition.UNTAPPED, rule));
    }

    private FarsightMask(final FarsightMask card) {
        super(card);
    }

    @Override
    public FarsightMask copy() {
        return new FarsightMask(this);
    }
}
