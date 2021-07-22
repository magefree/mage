
package mage.cards.t;

import java.util.UUID;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Quercitron
 */
public final class TidalSurge extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures without flying");

    static {
        filter.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }
    
    public TidalSurge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{U}");


        // Tap up to three target creatures without flying.
        this.getSpellAbility().addEffect(new TapTargetEffect("tap up to three target creatures without flying"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 3, filter, false));
    }

    private TidalSurge(final TidalSurge card) {
        super(card);
    }

    @Override
    public TidalSurge copy() {
        return new TidalSurge(this);
    }
}
