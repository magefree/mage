
package mage.cards.n;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author LoneFox

 */
public final class NocturnalRaid extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("black creatures");
    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public NocturnalRaid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{B}{B}");

        // Black creatures get +2/+0 until end of turn.
        this.getSpellAbility().addEffect(new BoostAllEffect(2, 0, Duration.EndOfTurn, filter, false));
    }

    private NocturnalRaid(final NocturnalRaid card) {
        super(card);
    }

    @Override
    public NocturnalRaid copy() {
        return new NocturnalRaid(this);
    }
}
