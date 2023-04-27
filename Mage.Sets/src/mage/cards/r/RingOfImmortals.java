
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.TargetsPermanentPredicate;
import mage.target.TargetSpell;

/**
 *
 * @author LoneFox
 */
public final class RingOfImmortals extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("instant or Aura spell that targets a permanent you control");

    static {
        filter.add(Predicates.or(CardType.INSTANT.getPredicate(), SubType.AURA.getPredicate()));
        filter.add(new TargetsPermanentPredicate(new FilterControlledPermanent()));
    }

    public RingOfImmortals(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{5}");

        // {3}, {tap}: Counter target instant or Aura spell that targets a permanent you control.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CounterTargetEffect(), new ManaCostsImpl<>("{3}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetSpell(filter));
        this.addAbility(ability);
    }

    private RingOfImmortals(final RingOfImmortals card) {
        super(card);
    }

    @Override
    public RingOfImmortals copy() {
        return new RingOfImmortals(this);
    }
}
