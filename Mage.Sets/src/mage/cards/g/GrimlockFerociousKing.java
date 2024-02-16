
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;

/**
 *
 * @author Saga
 */
public final class GrimlockFerociousKing extends CardImpl{
    
   public GrimlockFerociousKing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);
        this.color.setRed(true);
        this.color.setGreen(true);
        this.color.setWhite(true);

       this.nightCard = true;
        
        // Trample
        this.addAbility(TrampleAbility.getInstance());
        
        // {2}: Grimlock, Ferocious King becomes Grimlock, Dinobot Leader.
        this.addAbility(new TransformAbility());
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new TransformSourceEffect(), new ManaCostsImpl<>("{2}")));
    }

    private GrimlockFerociousKing(final GrimlockFerociousKing card) {
        super(card);
    }

    @Override
    public GrimlockFerociousKing copy() {
        return new GrimlockFerociousKing(this);
    } 
    
}
