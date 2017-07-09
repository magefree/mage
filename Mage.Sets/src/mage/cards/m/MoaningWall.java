package mage.cards.m;

import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

public class MoaningWall extends CardImpl {

    public MoaningWall(UUID ownerId, CardSetInfo cardSetInfo){
        super(ownerId, cardSetInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        subtype.add("Zombie");
        subtype.add("Wall");
        power = new MageInt(0);
        toughness = new MageInt(5);

        // Defender
        addAbility(DefenderAbility.getInstance());

        // Cycling {2}
        addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    public MoaningWall(final MoaningWall moaningWall){
        super(moaningWall);
    }

    public MoaningWall copy(){
        return new MoaningWall(this);
    }
}
