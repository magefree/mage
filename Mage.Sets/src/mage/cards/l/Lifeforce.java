
package mage.cards.l;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.TargetSpell;

/**
 *
 * @author anonymous
 */
public final class Lifeforce extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("black spell");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }
    
    public Lifeforce(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{G}{G}");

        // {G}{G}: Counter target black spell.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CounterTargetEffect(), new ManaCostsImpl<>("{G}{G}"));
        ability.addTarget(new TargetSpell(filter));
        this.addAbility(ability);
    }

    private Lifeforce(final Lifeforce card) {
        super(card);
    }

    @Override
    public Lifeforce copy() {
        return new Lifeforce(this);
    }
}
