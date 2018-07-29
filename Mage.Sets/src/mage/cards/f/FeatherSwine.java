package mage.cards.f;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 *
 * @author EikePeace
 */

public final class FeatherSwine extends CardImpl {

    public FeatherSwine(UUID ownerId, CardSetInfo setInfo){
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.subtype.add(SubType.BOAR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        //Flying
        this.addAbility(FlyingAbility.getInstance());
    }

    public FeatherSwine (final FeatherSwine card){super(card);}

    @Override
    public FeatherSwine copy() {
        return new FeatherSwine(this);
    }
}
