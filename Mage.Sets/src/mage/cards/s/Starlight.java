
package mage.cards.s;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.dynamicvalue.common.PermanentsTargetOpponentControlsCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LoneFox

 */
public final class Starlight extends CardImpl {


    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("black creature");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public Starlight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{W}");

        // You gain 3 life for each black creature target opponent controls.
        this.getSpellAbility().addEffect(new GainLifeEffect(new PermanentsTargetOpponentControlsCount(filter, 3)));
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private Starlight(final Starlight card) {
        super(card);
    }

    @Override
    public Starlight copy() {
        return new Starlight(this);
    }
}
