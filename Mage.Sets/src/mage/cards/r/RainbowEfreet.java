
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.PhaseOutSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author fireshoes
 */
public final class RainbowEfreet extends CardImpl {

    public RainbowEfreet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.EFREET);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // {U}{U}: Rainbow Efreet phases out.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new PhaseOutSourceEffect(), new ManaCostsImpl<>("{U}{U}")));
    }

    private RainbowEfreet(final RainbowEfreet card) {
        super(card);
    }

    @Override
    public RainbowEfreet copy() {
        return new RainbowEfreet(this);
    }
}
