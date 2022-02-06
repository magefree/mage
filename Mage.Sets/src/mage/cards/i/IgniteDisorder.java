package mage.cards.i;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCreaturePermanentAmount;

/**
 *
 * @author North
 */
public final class IgniteDisorder extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("white and/or blue creatures");

    static {
        filter.add(Predicates.or(
                new ColorPredicate(ObjectColor.WHITE),
                new ColorPredicate(ObjectColor.BLUE)));
    }

    public IgniteDisorder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{R}");

        // Ignite Disorder deals 3 damage divided as you choose among one, two, or three target white and/or blue creatures.
        this.getSpellAbility().addEffect(new DamageMultiEffect(3));
        this.getSpellAbility().addTarget(new TargetCreaturePermanentAmount(3, filter));
    }

    private IgniteDisorder(final IgniteDisorder card) {
        super(card);
    }

    @Override
    public IgniteDisorder copy() {
        return new IgniteDisorder(this);
    }
}
