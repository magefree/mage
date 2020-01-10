package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.condition.common.FerociousCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StampedeRider extends CardImpl {

    public StampedeRider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.SATYR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // At the beginning of each combat, if you control a creature with power 4 or greater, Stampede Rider gets +1/+1 until end of turn.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfCombatTriggeredAbility(
                        new BoostSourceEffect(1, 1, Duration.EndOfTurn),
                        TargetController.ANY, false
                ), FerociousCondition.instance, "At the beginning of each combat, " +
                "if you control a creature with power 4 or greater, {this} gets +1/+1 until end of turn."
        ));
    }

    private StampedeRider(final StampedeRider card) {
        super(card);
    }

    @Override
    public StampedeRider copy() {
        return new StampedeRider(this);
    }
}
