
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.keyword.UnleashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
 
/**
 *
 * @author LevelX2
 */
public final class GrimRoustabout extends CardImpl {
 
    public GrimRoustabout(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.SKELETON);
        this.subtype.add(SubType.WARRIOR);
 

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
 
        // Unleash (You may have this creature enter the battlefield with a +1/+1 counter on it. It can't block as long as it has a +1/+1 counter on it.)
        this.addAbility(new UnleashAbility());
        
        // {1}{B}: Regenerate Grim Roustabout.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ManaCostsImpl<>("{1}{B}")));
    }
 
    private GrimRoustabout(final GrimRoustabout card) {
        super(card);
    }
 
    @Override
    public GrimRoustabout copy() {
        return new GrimRoustabout(this);
    }
}