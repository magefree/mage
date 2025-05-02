package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

public final class ScroungerOfSouls extends CardImpl {

    public ScroungerOfSouls(UUID cardID, CardSetInfo cardSetInfo){
        super(cardID, cardSetInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");
        subtype.add(SubType.HORROR);

        power = new MageInt(3);
        toughness = new MageInt(4);

        // Lifelink
        addAbility(LifelinkAbility.getInstance());

    }

    private ScroungerOfSouls(final ScroungerOfSouls scroungerOfSouls){
        super(scroungerOfSouls);
    }
    public ScroungerOfSouls copy(){
        return new ScroungerOfSouls(this);
    }
}
