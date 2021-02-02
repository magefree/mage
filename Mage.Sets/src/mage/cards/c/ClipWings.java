
package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.common.SacrificeOpponentsEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;

/**
 *
 * @author fireshoes
 */
public final class ClipWings extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with flying");
    
    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public ClipWings(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{G}");

        // Each opponent sacrifices a creature with flying.
        this.getSpellAbility().addEffect(new SacrificeOpponentsEffect(filter));
    }

    private ClipWings(final ClipWings card) {
        super(card);
    }

    @Override
    public ClipWings copy() {
        return new ClipWings(this);
    }
}
