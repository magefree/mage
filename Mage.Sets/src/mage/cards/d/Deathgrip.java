
package mage.cards.d;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
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
 * @author markedagain
 */
public final class Deathgrip extends CardImpl {
    private static final FilterSpell filter = new FilterSpell("green spell");
    static{
        filter.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public Deathgrip(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{B}{B}");

        // {B}{B}: Counter target green spell.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CounterTargetEffect(), new ManaCostsImpl<>("{B}{B}"));
        ability.addTarget(new TargetSpell(filter));
        this.addAbility(ability);
    }

    private Deathgrip(final Deathgrip card) {
        super(card);
    }

    @Override
    public Deathgrip copy() {
        return new Deathgrip(this);
    }
}
