package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.VehiclesBecomeArtifactCreatureEffect;
import mage.abilities.keyword.CrewAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SetTargetPointer;

/**
 *
 * @author muz
 */
public final class TheThanosCopter extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent(SubType.VEHICLE, "a Vehicle you control");

    public TheThanosCopter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{6}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // When The Thanos-Copter enters, Vehicles you control become artifact creatures until end of turn.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
            new VehiclesBecomeArtifactCreatureEffect(Duration.EndOfTurn)
        ));

        // Whenever a Vehicle you control deals combat damage to a player, draw a card.
        this.addAbility(new DealsDamageToAPlayerAllTriggeredAbility(
            new DrawCardSourceControllerEffect(1), filter,
            false, SetTargetPointer.NONE, true
        ));

        // Crew 2
        this.addAbility(new CrewAbility(2));

    }

    private TheThanosCopter(final TheThanosCopter card) {
        super(card);
    }

    @Override
    public TheThanosCopter copy() {
        return new TheThanosCopter(this);
    }
}
