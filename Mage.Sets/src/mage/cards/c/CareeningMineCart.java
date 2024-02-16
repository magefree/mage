package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.CrewAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CareeningMineCart extends CardImpl {

    public CareeningMineCart(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Careening Mine Cart attacks, create a Treasure token.
        this.addAbility(new AttacksTriggeredAbility(new CreateTokenEffect(new TreasureToken())));

        // Crew 1
        this.addAbility(new CrewAbility(1));
    }

    private CareeningMineCart(final CareeningMineCart card) {
        super(card);
    }

    @Override
    public CareeningMineCart copy() {
        return new CareeningMineCart(this);
    }
}
