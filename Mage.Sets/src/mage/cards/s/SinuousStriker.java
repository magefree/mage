package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.EternalizeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;

import java.util.UUID;

public class SinuousStriker extends CardImpl {

    public SinuousStriker(UUID ownerId, CardSetInfo cardSetInfo){
        super(ownerId, cardSetInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");
        subtype.add("Naga");
        subtype.add("Warrior");

        power = new MageInt(2);
        toughness = new MageInt(2);

        //U : Sinious Striker gets +1/-1 until end of turn
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(+1, -1, Duration.EndOfTurn), new ManaCostsImpl("{U}")));

        //Eternalize 3UU
        this.addAbility(new EternalizeAbility(new ManaCostsImpl("{3}{U}{U}"), this));
    }

    public SinuousStriker(final SinuousStriker sinuousStriker){
        super(sinuousStriker);
    }

    public SinuousStriker copy(){
        return new SinuousStriker(this);
    }
}
