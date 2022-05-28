
package mage.cards.d;

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
public final class Douse extends CardImpl {
    
    private static final FilterSpell filter = new FilterSpell("red spell");

    static {
        filter.add(new ColorPredicate(ObjectColor.RED));
    }

    public Douse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{U}");
        
        // {1}{U}: Counter target red spell.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CounterTargetEffect(), new ManaCostsImpl<>("{1}{U}"));
        ability.addTarget(new TargetSpell(filter));
        this.addAbility(ability);
    }

    private Douse(final Douse card) {
        super(card);
    }

    @Override
    public Douse copy() {
        return new Douse(this);
    }
}
