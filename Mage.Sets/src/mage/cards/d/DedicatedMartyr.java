
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Zone;

/**
 *
 * @author cbt33
 */
public final class DedicatedMartyr extends CardImpl {

    public DedicatedMartyr(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {W}, Sacrifice Dedicated Martyr: You gain 3 life.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainLifeEffect(3), new ColoredManaCost(ColoredManaSymbol.W));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
        
        
      }
    
    private DedicatedMartyr(final DedicatedMartyr card) {
        super(card);
    }

    @Override
    public DedicatedMartyr copy() {
        return new DedicatedMartyr(this);
    }
}
