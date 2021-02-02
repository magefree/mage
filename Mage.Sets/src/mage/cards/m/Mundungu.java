
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.TargetSpell;

/**
 *
 * @author nigelzor
 */
public final class Mundungu extends CardImpl {

    public Mundungu(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Counter target spell unless its controller pays {1} and 1 life.
        CostsImpl<Cost> costs = new CostsImpl<>();
        costs.add(new GenericManaCost(1));
        costs.add(new PayLifeCost(1));
        costs.setText("{1} and 1 life");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CounterUnlessPaysEffect(costs), new TapSourceCost());
        ability.addTarget(new TargetSpell());
        this.addAbility(ability);
    }

    private Mundungu(final Mundungu card) {
        super(card);
    }

    @Override
    public Mundungu copy() {
        return new Mundungu(this);
    }
}
