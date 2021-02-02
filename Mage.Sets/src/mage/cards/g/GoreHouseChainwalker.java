
package mage.cards.g;

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
public final class GoreHouseChainwalker extends CardImpl {
 
    public GoreHouseChainwalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
 

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
 
        // Unleash (You may have this creature enter the battlefield with a +1/+1 counter on it. It can't block as long as it has a +1/+1 counter on it.)
        this.addAbility(new UnleashAbility());
        
    }
 
    private GoreHouseChainwalker(final GoreHouseChainwalker card) {
        super(card);
    }
 
    @Override
    public GoreHouseChainwalker copy() {
        return new GoreHouseChainwalker(this);
    }
}