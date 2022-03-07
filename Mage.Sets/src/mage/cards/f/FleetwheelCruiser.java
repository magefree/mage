package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.AddCardTypeSourceEffect;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class FleetwheelCruiser extends CardImpl {

    public FleetwheelCruiser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");
        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // When Fleetwheel Cruiser enters the battlefield, it becomes an artifact creature until the end of turn.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AddCardTypeSourceEffect(
                Duration.EndOfTurn, CardType.ARTIFACT, CardType.CREATURE
        ).setText("it becomes an artifact creature until end of turn")));

        // Crew 2
        this.addAbility(new CrewAbility(2));
    }

    private FleetwheelCruiser(final FleetwheelCruiser card) {
        super(card);
    }

    @Override
    public FleetwheelCruiser copy() {
        return new FleetwheelCruiser(this);
    }
}
