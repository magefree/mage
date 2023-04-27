package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.SpectacleCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.abilities.keyword.SpectacleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RafterDemon extends CardImpl {

    public RafterDemon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{R}");

        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Spectacle {3}{B}{R}
        this.addAbility(new SpectacleAbility(this, new ManaCostsImpl<>("{3}{B}{R}")));

        // When Rafter Demon enters the battlefield, if its spectacle cost was paid, each opponent discards a card.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new DiscardEachPlayerEffect(
                        StaticValue.get(1), false, TargetController.OPPONENT
                )), SpectacleCondition.instance,
                "When {this} enters the battlefield, " +
                        "if its spectacle cost was paid, " +
                        "each opponent discards a card."
        ));
    }

    private RafterDemon(final RafterDemon card) {
        super(card);
    }

    @Override
    public RafterDemon copy() {
        return new RafterDemon(this);
    }
}
