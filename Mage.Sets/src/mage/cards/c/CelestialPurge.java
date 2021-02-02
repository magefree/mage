
package mage.cards.c;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.TargetPermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class CelestialPurge extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("black or red permanent");

    static {
        filter.add(Predicates.or(
                new ColorPredicate(ObjectColor.BLACK),
                new ColorPredicate(ObjectColor.RED)));
    }

    public CelestialPurge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}");

        // Exile target black or red permanent.
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
        this.getSpellAbility().addEffect(new ExileTargetEffect());
    }

    private CelestialPurge(final CelestialPurge card) {
        super(card);
    }

    @Override
    public CelestialPurge copy() {
        return new CelestialPurge(this);
    }

}
