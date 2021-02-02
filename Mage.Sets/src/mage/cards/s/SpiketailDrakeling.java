
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
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
public final class SpiketailDrakeling extends CardImpl {

    public SpiketailDrakeling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}{U}");
        this.subtype.add(SubType.DRAKE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Sacrifice Spiketail Drakeling: Counter target spell unless its controller pays {2}.
         Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CounterUnlessPaysEffect(new GenericManaCost(2)), new SacrificeSourceCost());                                                         ability.addTarget(new TargetSpell());
        this.addAbility(ability);

    }

    private SpiketailDrakeling(final SpiketailDrakeling card) {
        super(card);
    }

    @Override
    public SpiketailDrakeling copy() {
        return new SpiketailDrakeling(this);
    }
}
