package mage.cards.s;

import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.WasDealtDamageThisTurnPredicate;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SwiftDemise extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("each creature you don't control that was dealt damage this turn");

    static {
        filter.add(TargetController.NOT_YOU.getOwnerPredicate());
        filter.add(WasDealtDamageThisTurnPredicate.instance);
    }

    public SwiftDemise(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}");

        // Swift Demise deals 1 damage to target creature. Then destroy each creature you don't control that was dealt damage this turn.
        this.getSpellAbility().addEffect(new DamageTargetEffect(1));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new DestroyAllEffect(filter).concatBy("Then"));
    }

    private SwiftDemise(final SwiftDemise card) {
        super(card);
    }

    @Override
    public SwiftDemise copy() {
        return new SwiftDemise(this);
    }
}
