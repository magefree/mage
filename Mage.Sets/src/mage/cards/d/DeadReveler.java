
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.UnleashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
 
/**
 *
 * @author LevelX2
 */
public final class DeadReveler extends CardImpl {
 
    public DeadReveler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.ZOMBIE);
 

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);
 
        // Unleash (You may have this creature enter the battlefield with a +1/+1 counter on it. It can't block as long as it has a +1/+1 counter on it.)
        this.addAbility(new UnleashAbility());
    }
 
    private DeadReveler(final DeadReveler card) {
        super(card);
    }
 
    @Override
    public DeadReveler copy() {
        return new DeadReveler(this);
    }
}