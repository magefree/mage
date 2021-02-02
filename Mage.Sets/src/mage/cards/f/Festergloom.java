
package mage.cards.f;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 * @author noxx
 */
public final class Festergloom extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Nonblack creatures");

    static {
        filter.add(Predicates.not(new ColorPredicate(ObjectColor.BLACK)));
    }

    public Festergloom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{B}");


        // Nonblack creatures get -1/-1 until end of turn.
        this.getSpellAbility().addEffect(new BoostAllEffect(-1, -1, Duration.EndOfTurn, filter, false));
    }

    private Festergloom(final Festergloom card) {
        super(card);
    }

    @Override
    public Festergloom copy() {
        return new Festergloom(this);
    }
}
