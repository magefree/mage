package mage.cards.s;

import mage.MageInt;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

public class ScroungerOfSouls extends CardImpl {

    public ScroungerOfSouls(UUID cardID, CardSetInfo cardSetInfo){
        super(cardID, cardSetInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");
        subtype.add("Horror");

        power = new MageInt(3);
        toughness = new MageInt(4);

        // Lifelink
        addAbility(LifelinkAbility.getInstance());

    }

    public ScroungerOfSouls(final ScroungerOfSouls scroungerOfSouls){
        super(scroungerOfSouls);
    }
    public ScroungerOfSouls copy(){
        return new ScroungerOfSouls(this);
    }
}
