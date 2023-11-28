
package mage.cards.b;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author LoneFox
 */
public final class BattleFrenzy extends CardImpl {

    private static final FilterCreaturePermanent filter1 = new FilterCreaturePermanent("green creatures");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("nongreen creatures");

    static {
        filter1.add(new ColorPredicate(ObjectColor.GREEN));
        filter2.add(Predicates.not(new ColorPredicate(ObjectColor.GREEN)));
    }


    public BattleFrenzy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{R}");

        // Green creatures you control get +1/+1 until end of turn.
        this.getSpellAbility().addEffect(new BoostControlledEffect(1, 1, Duration.EndOfTurn, filter1));
        // Nongreen creatures you control get +1/+0 until end of turn.
        this.getSpellAbility().addEffect(new BoostControlledEffect(1, 0, Duration.EndOfTurn, filter2).concatBy("<br>"));
    }

    private BattleFrenzy(final BattleFrenzy card) {
        super(card);
    }

    @Override
    public BattleFrenzy copy() {
        return new BattleFrenzy(this);
    }
}
