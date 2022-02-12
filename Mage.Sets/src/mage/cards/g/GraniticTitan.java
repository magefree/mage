package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

public final class GraniticTitan extends CardImpl {

    public GraniticTitan(UUID ownerId, CardSetInfo cardSetInfo){
        super(ownerId, cardSetInfo, new CardType[]{CardType.CREATURE},"{4}{R}{R}");
        subtype.add(SubType.ELEMENTAL);
        power = new MageInt(5);
        toughness = new MageInt(4);

        // Menace
        addAbility(new MenaceAbility(false));

        // Cycling {2}
        addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));

    }

    public GraniticTitan(final GraniticTitan graniticTitan){
        super(graniticTitan);
    }

    public GraniticTitan copy(){
        return new GraniticTitan(this);
    }
}
