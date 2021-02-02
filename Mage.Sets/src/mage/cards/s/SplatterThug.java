
package mage.cards.s;
 
import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.UnleashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
 
/**
 *
 * @author LevelX2
 */
public final class SplatterThug extends CardImpl {
 
    public SplatterThug(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
 

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
 
        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());
        
        // Unleash (You may have this creature enter the battlefield with a +1/+1 counter on it. It can't block as long as it has a +1/+1 counter on it.)
        this.addAbility(new UnleashAbility());
        
    }
 
    private SplatterThug(final SplatterThug card) {
        super(card);
    }
 
    @Override
    public SplatterThug copy() {
        return new SplatterThug(this);
    }
}