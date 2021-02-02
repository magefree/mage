
package mage.cards.r;
 
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
public final class RakdosCackler extends CardImpl {
 
    public RakdosCackler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B/R}");
        this.subtype.add(SubType.DEVIL);
 


        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
 
        // Unleash (You may have this creature enter the battlefield with a +1/+1 counter on it. It can't block as long as it has a +1/+1 counter on it.)
        this.addAbility(new UnleashAbility());
    }
 
    private RakdosCackler(final RakdosCackler card) {
        super(card);
    }
 
    @Override
    public RakdosCackler copy() {
        return new RakdosCackler(this);
    }
}