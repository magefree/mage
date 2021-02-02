
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.UnleashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
 
/**
 *
 * @author LevelX2
 */
public final class BloodfrayGiant extends CardImpl {
 
    public BloodfrayGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{R}");
        this.subtype.add(SubType.GIANT);
 

        this.power = new MageInt(4);
        this.toughness = new MageInt(3);
 
        // Deathtouch
        this.addAbility(TrampleAbility.getInstance());

        // Unleash (You may have this creature enter the battlefield with a +1/+1 counter on it. It can't block as long as it has a +1/+1 counter on it.)
        this.addAbility(new UnleashAbility());
        
    }
 
    private BloodfrayGiant(final BloodfrayGiant card) {
        super(card);
    }
 
    @Override
    public BloodfrayGiant copy() {
        return new BloodfrayGiant(this);
    }
}