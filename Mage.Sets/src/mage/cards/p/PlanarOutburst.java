
package mage.cards.p;

import java.util.UUID;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.keyword.AwakenAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;

/**
 *
 * @author fireshoes
 */
public final class PlanarOutburst extends CardImpl {

    private final static FilterCreaturePermanent filter = new FilterCreaturePermanent("nonland creatures");

    static {
        filter.add(Predicates.not(new CardTypePredicate(CardType.LAND)));
    }

    public PlanarOutburst(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{W}{W}");

        // Destroy all nonland creatures.
        this.getSpellAbility().addEffect(new DestroyAllEffect(filter, false));

        // Awaken 4-{5}{W}{W}{W}
        this.addAbility(new AwakenAbility(this, 4, "{5}{W}{W}{W}"));
    }

    public PlanarOutburst(final PlanarOutburst card) {
        super(card);
    }

    @Override
    public PlanarOutburst copy() {
        return new PlanarOutburst(this);
    }
}
