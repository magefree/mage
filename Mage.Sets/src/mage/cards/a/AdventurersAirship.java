package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AdventurersAirship extends CardImpl {

    public AdventurersAirship(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When this Vehicle attacks, draw a card then discard a card.
        this.addAbility(new AttacksTriggeredAbility(new DrawDiscardControllerEffect(1, 1)));

        // Crew 2
        this.addAbility(new CrewAbility(2));
    }

    private AdventurersAirship(final AdventurersAirship card) {
        super(card);
    }

    @Override
    public AdventurersAirship copy() {
        return new AdventurersAirship(this);
    }
}
