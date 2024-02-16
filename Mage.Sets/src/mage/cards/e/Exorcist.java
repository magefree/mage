
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author ilcartographer
 */
public final class Exorcist extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("black creature");
    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public Exorcist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}{W}, {tap}: Destroy target black creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, 
                new DestroyTargetEffect(),  
                new ManaCostsImpl<>("{1}{W}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    private Exorcist(final Exorcist card) {
        super(card);
    }

    @Override
    public Exorcist copy() {
        return new Exorcist(this);
    }
}
