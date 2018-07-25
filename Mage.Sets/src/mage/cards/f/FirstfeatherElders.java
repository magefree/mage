package mage.cards.f;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 *
 * @author EikePeace
 */

public final class FirstfeatherElders extends CardImpl {

    public FirstfeatherElders(UUID ownerId, CardSetInfo setInfo){
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        //Flying
        this.addAbility(FlyingAbility.getInstance());

        //Vigilance
        this.addAbility(VigilanceAbility.getInstance());
    }

    public FirstfeatherElders (final FirstfeatherElders card){super(card);}

    @Override
    public FirstfeatherElders copy() {
        return new FirstfeatherElders(this);
    }
}
