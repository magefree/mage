
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;

/**
 *
 * @author Loki
 */
public final class SeleniaDarkAngel extends CardImpl {

    public SeleniaDarkAngel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.ANGEL);


        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        
        // Flying        
        this.addAbility(FlyingAbility.getInstance());
        
        // Pay 2 life: Return Selenia, Dark Angel to its owner's hand.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new ReturnToHandSourceEffect(true), new PayLifeCost(2)));
    }

    private SeleniaDarkAngel(final SeleniaDarkAngel card) {
        super(card);
    }

    @Override
    public SeleniaDarkAngel copy() {
        return new SeleniaDarkAngel(this);
    }
}
