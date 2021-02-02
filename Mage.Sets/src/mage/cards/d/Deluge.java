
package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.TapAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;

/**
 *
 * @author cbt33
 */
public final class Deluge extends CardImpl {
    
    static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures without flying");
    
    static {
        filter.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }


    public Deluge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{U}");


        // Tap all creatures without flying.
        this.getSpellAbility().addEffect(new TapAllEffect(filter));
    }

    private Deluge(final Deluge card) {
        super(card);
    }

    @Override
    public Deluge copy() {
        return new Deluge(this);
    }
}
