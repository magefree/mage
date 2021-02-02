
package mage.cards.r;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.effects.common.TapAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author LoneFox
 */
public final class Riptide extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("blue creatures");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLUE));
    }

    public Riptide(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}");

        // Tap all blue creatures.
        this.getSpellAbility().addEffect(new TapAllEffect(filter));
    }

    private Riptide(final Riptide card) {
        super(card);
    }

    @Override
    public Riptide copy() {
        return new Riptide(this);
    }
}
