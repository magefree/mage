package mage.cards.d;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.common.CrewIncreasedPowerAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ConjureCardEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author Svyatoslav28
 */
public final class DragonflyPilot extends CardImpl {

    public DragonflyPilot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.FOX);
        this.subtype.add(SubType.PILOT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Dragonfly Pilot enters the battlefield, conjure a card named Dragonfly Suit into your hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ConjureCardEffect("Dragonfly Suit")));
        // Dragonfly Pilot crews Vehicles as though its power were 2 greater.
        this.addAbility(new CrewIncreasedPowerAbility());
    }

    private DragonflyPilot(final DragonflyPilot card) {
        super(card);
    }

    @Override
    public DragonflyPilot copy() {
        return new DragonflyPilot(this);
    }
}
