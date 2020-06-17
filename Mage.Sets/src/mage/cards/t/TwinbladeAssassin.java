package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.common.MorbidCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.watchers.common.MorbidWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TwinbladeAssassin extends CardImpl {

    public TwinbladeAssassin(UUID ownerId, CardSetInfo setInfo) {
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
        ), new MorbidWatcher());
    }

    private TwinbladeAssassin(final TwinbladeAssassin card) {
        super(card);
    }

    @Override
    public TwinbladeAssassin copy() {
        return new TwinbladeAssassin(this);
    }
}
