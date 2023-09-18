
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author fireshoes
 */
public final class WildfireEmissary extends CardImpl {
    
    public WildfireEmissary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.EFREET);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Protection from white
        this.addAbility(ProtectionAbility.from(ObjectColor.WHITE));
        
        // {1}{R}: Wildfire Emissary gets +1/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1, 0, Duration.EndOfTurn), new ManaCostsImpl<>("{1}{R}")));
    }

    private WildfireEmissary(final WildfireEmissary card) {
        super(card);
    }

    @Override
    public WildfireEmissary copy() {
        return new WildfireEmissary(this);
    }
}