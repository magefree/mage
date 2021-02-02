
package mage.cards.t;
 
import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.UnleashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
 
/**
 *
 * @author LevelX2
 */
public final class ThrillKillAssassin extends CardImpl {
 
    public ThrillKillAssassin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ASSASSIN);
 

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);
 
        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Unleash (You may have this creature enter the battlefield with a +1/+1 counter on it. It can't block as long as it has a +1/+1 counter on it.)
        this.addAbility(new UnleashAbility());
        
    }
 
    private ThrillKillAssassin(final ThrillKillAssassin card) {
        super(card);
    }
 
    @Override
    public ThrillKillAssassin copy() {
        return new ThrillKillAssassin(this);
    }
}