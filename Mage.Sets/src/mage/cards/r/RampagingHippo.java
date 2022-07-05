package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

public final class RampagingHippo extends CardImpl {

    public RampagingHippo(UUID cardID, CardSetInfo cardSetInfo){
        super(cardID, cardSetInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");
        subtype.add(SubType.HIPPO);
        power = new MageInt(5);
        toughness = new MageInt(6);

        // Trample
        addAbility(TrampleAbility.getInstance());

        // Cycling {2}
        addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    public RampagingHippo(final RampagingHippo rampagingHippo){
        super(rampagingHippo);
    }

    public RampagingHippo copy(){
        return new RampagingHippo(this);
    }
}
