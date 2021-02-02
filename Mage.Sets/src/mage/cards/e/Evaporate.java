
package mage.cards.e;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.effects.common.DamageAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author fireshoes
 */
public final class Evaporate extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("white and/or blue creature");
    
    static {
        filter.add(Predicates.or(new ColorPredicate(ObjectColor.WHITE),
                    new ColorPredicate(ObjectColor.BLUE)));
    }

    public Evaporate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{R}");

        // Evaporate deals 1 damage to each white and/or blue creature.
        this.getSpellAbility().addEffect(new DamageAllEffect(1, filter));
    }

    private Evaporate(final Evaporate card) {
        super(card);
    }

    @Override
    public Evaporate copy() {
        return new Evaporate(this);
    }
}
