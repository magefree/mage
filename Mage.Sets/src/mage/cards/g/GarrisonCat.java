package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.HumanSoldierToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GarrisonCat extends CardImpl {

    public GarrisonCat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.CAT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Garrison Cat dies, create a 1/1 white Human Soldier creature token.
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new HumanSoldierToken())));
    }

    private GarrisonCat(final GarrisonCat card) {
        super(card);
    }

    @Override
    public GarrisonCat copy() {
        return new GarrisonCat(this);
    }
}
