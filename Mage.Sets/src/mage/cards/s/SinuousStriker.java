package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.EternalizeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

public final class SinuousStriker extends CardImpl {

    private static String rule = "Eternalize - {3}{U}{U}, Discard a card <i>({3}{U}{U}, Discard a card, Exile this card from your graveyard: Create a token that's a copy of it, except it's a 4/4 black Zombie";

    public SinuousStriker(UUID ownerId, CardSetInfo cardSetInfo){
        super(ownerId, cardSetInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");
        subtype.add(SubType.NAGA);
        subtype.add(SubType.WARRIOR);

        power = new MageInt(2);
        toughness = new MageInt(2);

        //U : Sinious Striker gets +1/-1 until end of turn
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(+1, -1, Duration.EndOfTurn), new ManaCostsImpl("{U}")));

        //Eternalize 3UU, Discard a card
        EternalizeAbility ability = new EternalizeAbility(new ManaCostsImpl("{3}{U}{U}"), this, rule);
        ability.addCost(new DiscardCardCost());
        this.addAbility(ability);
    }

    public SinuousStriker(final SinuousStriker sinuousStriker){
        super(sinuousStriker);
    }

    public SinuousStriker copy(){
        return new SinuousStriker(this);
    }
}
