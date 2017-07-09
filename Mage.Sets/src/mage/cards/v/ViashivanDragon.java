package mage.cards.v;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Duration;
import mage.constants.Zone;

import java.util.UUID;

public class ViashivanDragon extends CardImpl {

    public ViashivanDragon(UUID cardId, CardSetInfo cardSetInfo) {
        super(cardId, cardSetInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}{G}{G}");
        subtype.add("Dragon");
        color.setGreen(true);
        color.setRed(true);
        power = new MageInt(4);
        toughness = new MageInt(4);

        //Flying
        addAbility(FlyingAbility.getInstance());

        //{R}: Viashivan Dragon gets +1/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1, 0, Duration.EndOfTurn), new ColoredManaCost(ColoredManaSymbol.R)));
        //{G}: Viashivan Dragon gets +/+1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(0, 1, Duration.EndOfTurn), new ColoredManaCost(ColoredManaSymbol.G)));

    }

    public ViashivanDragon(ViashivanDragon other){
        super(other);
    }

    public ViashivanDragon copy(){
        return new ViashivanDragon(this);
    }
}
