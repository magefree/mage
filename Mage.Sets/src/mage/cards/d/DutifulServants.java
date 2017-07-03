package mage.cards.d;

import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

public class DutifulServants extends CardImpl {

    public DutifulServants(UUID ownerId, CardSetInfo cardSetInfo){
        super(ownerId, cardSetInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");
        subtype.add("Zombie");
        power = new MageInt(2);
        toughness = new MageInt(5);
    }

    public DutifulServants(final DutifulServants dutifulServants){
        super(dutifulServants);
    }

    public DutifulServants copy(){
        return new DutifulServants(this);
    }
}
