
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class BalthorTheStout extends CardImpl {
    
    private static final FilterCreaturePermanent filter1 = new FilterCreaturePermanent("Barbarian creatures");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("another target Barbarian");

    static {
        filter1.add(SubType.BARBARIAN.getPredicate());
        filter2.add(AnotherPredicate.instance);
        filter2.add(SubType.BARBARIAN.getPredicate());
    }
    
    public BalthorTheStout(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}{R}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DWARF, SubType.BARBARIAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Other Barbarian creatures get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(1, 1, Duration.WhileOnBattlefield, filter1, true)));
        
        // {R}: Another target Barbarian creature gets +1/+0 until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostTargetEffect(1, 0, Duration.EndOfTurn),new ManaCostsImpl<>("{R}"));
        ability.addTarget(new TargetCreaturePermanent(1, 1, filter2, true));
        this.addAbility(ability);
    }

    private BalthorTheStout(final BalthorTheStout card) {
        super(card);
    }

    @Override
    public BalthorTheStout copy() {
        return new BalthorTheStout(this);
    }
}
