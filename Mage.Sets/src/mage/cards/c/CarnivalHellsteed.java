
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.UnleashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
 
/**
 *
 * @author LevelX2
 */
public final class CarnivalHellsteed extends CardImpl {
 
    public CarnivalHellsteed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}{R}");
        this.subtype.add(SubType.NIGHTMARE);
        this.subtype.add(SubType.HORSE);
 


        this.power = new MageInt(5);
        this.toughness = new MageInt(4);
 
        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());
        
        // Unleash (You may have this creature enter the battlefield with a +1/+1 counter on it. It can't block as long as it has a +1/+1 counter on it.)
        this.addAbility(new UnleashAbility());
        
    }
 
    private CarnivalHellsteed(final CarnivalHellsteed card) {
        super(card);
    }
 
    @Override
    public CarnivalHellsteed copy() {
        return new CarnivalHellsteed(this);
    }
}