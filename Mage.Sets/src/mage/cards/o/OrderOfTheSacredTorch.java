
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.TargetSpell;

/**
 *
 * @author Quercitron
 */
public final class OrderOfTheSacredTorch extends CardImpl {
    
    private static final FilterSpell filter = new FilterSpell("black spell");
    
    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public OrderOfTheSacredTorch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {tap}, Pay 1 life: Counter target black spell.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new mage.abilities.effects.common.CounterTargetEffect(),
                new TapSourceCost());
        ability.addCost(new PayLifeCost(1));
        ability.addTarget(new TargetSpell(filter));
        this.addAbility(ability);
    }

    private OrderOfTheSacredTorch(final OrderOfTheSacredTorch card) {
        super(card);
    }

    @Override
    public OrderOfTheSacredTorch copy() {
        return new OrderOfTheSacredTorch(this);
    }
}
