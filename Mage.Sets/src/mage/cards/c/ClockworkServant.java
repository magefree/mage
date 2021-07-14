package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.AdamantCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ClockworkServant extends CardImpl {

    public ClockworkServant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");

        this.subtype.add(SubType.GNOME);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Adamant - When Clockwork Servant enters the battlefield, if at least three mana of the same color was spent to cast it, draw a card.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1)),
                AdamantCondition.ANY, "<br><i>Adamant</i> &mdash; When {this} enters the battlefield, " +
                "if at least three mana of the same color was spent to cast it, draw a card."
        ));
    }

    private ClockworkServant(final ClockworkServant card) {
        super(card);
    }

    @Override
    public ClockworkServant copy() {
        return new ClockworkServant(this);
    }
}
