
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.TargetSpell;

/**
 *
 * @author LoneFox
 */
public final class SpiketailDrake extends CardImpl {

    public SpiketailDrake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}{U}");
        this.subtype.add(SubType.DRAKE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Sacrifice Spiketail Drake: Counter target spell unless its controller pays {3}.
         Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CounterUnlessPaysEffect(new ManaCostsImpl<>("{3}")), new SacrificeSourceCost());                                                         ability.addTarget(new TargetSpell());                                                              this.addAbility(ability);
    }

    private SpiketailDrake(final SpiketailDrake card) {
        super(card);
    }

    @Override
    public SpiketailDrake copy() {
        return new SpiketailDrake(this);
    }
}
