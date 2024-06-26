package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SettlementBlacksmith extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(new FilterControlledPermanent(SubType.EQUIPMENT));
    private static final Hint hint = new ConditionHint(condition, "You control an Equipment");

    public SettlementBlacksmith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Settlement Blacksmith enters the battlefield, if you control an Equipment, draw a card.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1)),
                condition, "When {this} enters the battlefield, if you control an Equipment, draw a card."
        ).addHint(hint));
    }

    private SettlementBlacksmith(final SettlementBlacksmith card) {
        super(card);
    }

    @Override
    public SettlementBlacksmith copy() {
        return new SettlementBlacksmith(this);
    }
}
