
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.dynamicvalue.common.ControllerLifeDividedValue;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.keyword.DethroneAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class TreasonousOgre extends CardImpl {

    public TreasonousOgre(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Dethrone
        this.addAbility(new DethroneAbility());
        // Pay 3 life: Add {R}.
        
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, 
                new BasicManaEffect(new Mana(ColoredManaSymbol.R), new ControllerLifeDividedValue(3)), 
                new PayLifeCost(3)));
    }

    private TreasonousOgre(final TreasonousOgre card) {
        super(card);
    }

    @Override
    public TreasonousOgre copy() {
        return new TreasonousOgre(this);
    }
}
