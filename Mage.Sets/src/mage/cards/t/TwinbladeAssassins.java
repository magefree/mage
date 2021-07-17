package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.common.MorbidCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.hint.common.MorbidHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TwinbladeAssassins extends CardImpl {

    public TwinbladeAssassins(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // At the beginning of your end step, if a creature died this turn, draw a card.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfEndStepTriggeredAbility(
                        new DrawCardSourceControllerEffect(1), TargetController.YOU, false
                ), MorbidCondition.instance, "At the beginning of your end step, " +
                "if a creature died this turn, draw a card."
        ).addHint(MorbidHint.instance));
    }

    private TwinbladeAssassins(final TwinbladeAssassins card) {
        super(card);
    }

    @Override
    public TwinbladeAssassins copy() {
        return new TwinbladeAssassins(this);
    }
}
