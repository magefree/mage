
package mage.cards.a;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.TargetSpell;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author djbrez
 */
public final class Abjure extends CardImpl {
    
    private static final FilterControlledPermanent filter = new FilterControlledPermanent("a blue permanent");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLUE));
    }

    public Abjure(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}");

        // As an additional cost to cast Abjure, sacrifice a blue permanent.
        this.getSpellAbility().addCost(new SacrificeTargetCost(new TargetControlledPermanent(1,1,filter, true)));
        
        // Counter target spell.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private Abjure(final Abjure card) {
        super(card);
    }

    @Override
    public Abjure copy() {
        return new Abjure(this);
    }
}
