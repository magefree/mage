package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BalambGardenAirborne extends CardImpl {

    public BalambGardenAirborne(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);
        this.nightCard = true;

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Balamb Garden attacks, draw a card.
        this.addAbility(new AttacksTriggeredAbility(new DrawCardSourceControllerEffect(1)));

        // Crew 1
        this.addAbility(new CrewAbility(1));
    }

    private BalambGardenAirborne(final BalambGardenAirborne card) {
        super(card);
    }

    @Override
    public BalambGardenAirborne copy() {
        return new BalambGardenAirborne(this);
    }
}
