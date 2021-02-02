
package mage.cards.b;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.effects.common.TapAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author LoneFox

 */
public final class BlindingLight extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nonwhite creatures");

    static {
        filter.add(Predicates.not(new ColorPredicate(ObjectColor.WHITE)));
    }

    public BlindingLight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{W}");

        // Tap all nonwhite creatures.
        this.getSpellAbility().addEffect(new TapAllEffect(filter));
    }

    private BlindingLight(final BlindingLight card) {
        super(card);
    }

    @Override
    public BlindingLight copy() {
        return new BlindingLight(this);
    }
}
